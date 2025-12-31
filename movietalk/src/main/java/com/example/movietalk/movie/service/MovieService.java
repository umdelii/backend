package com.example.movietalk.movie.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.movietalk.movie.dto.MovieDTO;
import com.example.movietalk.movie.dto.MovieImageDTO;
import com.example.movietalk.movie.dto.PageRequestDTO;
import com.example.movietalk.movie.dto.PageResultDTO;
import com.example.movietalk.movie.entity.Movie;
import com.example.movietalk.movie.entity.MovieImage;
import com.example.movietalk.movie.repository.MovieImageRepository;
import com.example.movietalk.movie.repository.MovieRepository;
import com.example.movietalk.movie.repository.ReviewRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@RequiredArgsConstructor
@Log4j2
@Transactional
public class MovieService {
    private final MovieRepository movieRepository;
    private final MovieImageRepository movieImageRepository;
    private final ReviewRepository reviewRepository;

    // cascade前
    // public Long create(MovieDTO dto) {
    // Map<String, Object> map = dtoToEntity(dto);
    // // 映画
    // Movie movie = (Movie) map.get("movie");
    // movieRepository.save(movie);
    // // 映画のイメージ
    // MovieImage movieImage = (MovieImage) map.get("imageLists");
    // movieImageRepository.save(movieImage);

    // return movie.getMno();
    // }

    // CASCADE後
    public String create(MovieDTO dto) {
        Movie movie = dtoToEntity(dto);
        movieRepository.save(movie);
        return movie.getTitle();
    }

    // read
    // ひとつ
    @Transactional(readOnly = true)
    public MovieDTO getRow(Long mno) {
        List<Object[]> result = movieRepository.getMovieWithAll(mno);
        // Movie 配列の一番目の映画の情報だけ取得
        Movie movie = (Movie) result.get(0)[0];

        List<MovieImage> movieImages = result.stream().map(en -> (MovieImage) en[1]).collect(Collectors.toList());

        // reivew数 / 評価
        Long reviewCnt = (Long) result.get(0)[2];
        Double avg = (Double) result.get(0)[3];

        return entityToDto(movie, movieImages, reviewCnt, avg);
    }

    // 全部
    @Transactional(readOnly = true)
    public PageResultDTO<MovieDTO> getMovieList(PageRequestDTO pageRequestDTO) {
        Pageable pageable = PageRequest.of(pageRequestDTO.getPage() - 1, pageRequestDTO.getSize(),
                Sort.by("mno").descending());
        Page<Object[]> result = movieRepository.getListPage(pageable);

        // entity => dto
        // List<MovieDTO> dtolist = new ArrayList<>();
        // result.forEach(obj -> {
        // MovieDTO dto = entityToDto((Movie) obj[0], List.of((MovieImage) obj[1]),
        // (Long) obj[2], (Double) obj[3]);
        // dtolist.add(dto);
        // });

        Function<Object[], MovieDTO> function = (obj -> entityToDto((Movie) obj[0], List.of((MovieImage) obj[1]),
                (Long) obj[2], (Double) obj[3]));

        List<MovieDTO> dtolist = result.stream().map(function).collect(Collectors.toList());

        Long totalCount = result.getTotalElements();

        return PageResultDTO.<MovieDTO>withAll()
                .dtoList(dtolist)
                .totalCount(totalCount)
                .pageRequestDTO(pageRequestDTO)
                .build();
    }

    private MovieDTO entityToDto(Movie movie, List<MovieImage> movieImages, Long reviewCnt, Double avg) {
        MovieDTO dto = MovieDTO.builder()
                .mno(movie.getMno())
                .title(movie.getTitle())
                .reviewCnt(reviewCnt)
                .avg(avg)
                .createDate(movie.getCreateDate())
                .build();

        // List<MovieImage> => List<MovieImageDTO>
        List<MovieImageDTO> imagesDTOs = movieImages.stream().map(movieImage -> {
            return MovieImageDTO.builder()
                    .inum(movieImage.getInum())
                    .uuid(movieImage.getUuid())
                    .path(movieImage.getPath())
                    .imgName(movieImage.getImgName())
                    .build();
        }).collect(Collectors.toList());

        dto.setMovieImages(imagesDTOs);

        return dto;
    }

    // cascade前
    // private Map<String, Object> dtoToEntity(MovieDTO dto) {

    // Map<String, Object> entityMap = new HashMap<>();

    // Movie movie = Movie.builder()
    // .mno(dto.getMno())
    // .title(dto.getTitle())
    // .build();

    // entityMap.put("movie", movie);

    // List<MovieImageDTO> imagesDTOs = dto.getMovieImages();
    // if (imagesDTOs != null && imagesDTOs.size() > 0) {

    // List<MovieImage> imageLists = imagesDTOs.stream().map(movieImage -> {
    // return MovieImage.builder()
    // .inum(movieImage.getInum())
    // .uuid(movieImage.getUuid())
    // .path(movieImage.getPath())
    // .imgName(movieImage.getImgName())
    // .movie(movie)
    // .build();
    // }).collect(Collectors.toList());

    // entityMap.put("imageLists", imageLists);
    // }
    // return entityMap;
    // }

    // CASCADE後
    public Movie dtoToEntity(MovieDTO dto) {
        Movie movie = Movie.builder()
                .mno(dto.getMno())
                .title(dto.getTitle())
                .build();

        List<MovieImageDTO> imageDTOs = dto.getMovieImages();
        if (imageDTOs != null && imageDTOs.size() > 0) {

            imageDTOs.stream().forEach(movieImage -> {
                MovieImage image = MovieImage.builder()
                        .inum(movieImage.getInum())
                        .imgName(movieImage.getImgName())
                        .uuid(movieImage.getUuid())
                        .path(movieImage.getPath())
                        .movie(movie)
                        .build();
                movie.addImage(image);
            });
        }

        return movie;
    }

    public Long updateRow(MovieDTO dto) {

        // title 変更
        Movie movie = movieRepository.findById(dto.getMno()).get();
        movie.setTitle(dto.getTitle());

        // 映画のイメージ削除
        movieImageRepository.deleteByMovie(movie);

        // 新しく image 追加
        movie = dtoToEntity(dto);
        movie.getMovieImages().forEach(movieImageRepository::save);

        return movie.getMno();

    }

    // 削除
    public void deleteRow(Long mno) {

        // delete image
        Movie movie = movieRepository.findById(mno).get();
        movieImageRepository.deleteByMovie(movie);

        // review 削除
        reviewRepository.deleteByMovie(movie);

        // movie
        movieRepository.delete(movie);
    }
}
