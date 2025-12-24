package com.example.movietalk.movie.service;

import java.util.List;
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
import com.example.movietalk.movie.repository.MovieRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@RequiredArgsConstructor
@Log4j2
@Transactional
public class MovieService {
    private final MovieRepository movieRepository;

    // 全部
    @Transactional(readOnly = true)
    public PageResultDTO<MovieDTO> getMovieList(PageRequestDTO pageRequestDTO) {
        Pageable pageable = PageRequest.of(pageRequestDTO.getPage(), pageRequestDTO.getSize(),
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

    // ひとつ
    @Transactional(readOnly = true)
    public void getMovie(Long mno) {
        movieRepository.getMovieWithAll(mno);
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
}
