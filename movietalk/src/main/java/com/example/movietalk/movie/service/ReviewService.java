package com.example.movietalk.movie.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.movietalk.member.entity.Member;
import com.example.movietalk.movie.dto.ReviewDTO;
import com.example.movietalk.movie.entity.Movie;
import com.example.movietalk.movie.entity.Review;
import com.example.movietalk.movie.repository.MovieRepository;
import com.example.movietalk.movie.repository.ReviewRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@RequiredArgsConstructor
@Log4j2
@Transactional
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final MovieRepository movieRepository;

    public Long insertRow(ReviewDTO dto) {

        Review review = dtoToEntity(dto);

        return reviewRepository.save(review).getRno();
    }

    @Transactional(readOnly = true)
    public ReviewDTO getRow(Long rno) {

        return entityToDto(reviewRepository.findById(rno).orElseThrow());
    }

    @Transactional(readOnly = true)
    public List<ReviewDTO> getList(Long mno) {
        Movie movie = movieRepository.findById(mno).get();

        List<Review> reviews = reviewRepository.findByMovie(movie);

        // entity -> dto
        List<ReviewDTO> result = reviews.stream().map(this::entityToDto).collect(Collectors.toList());

        return result;
    }

    // update
    public Long updateRow(ReviewDTO dto) {

        // update 対象取得
        Review review = reviewRepository.findById(dto.getRno()).get();
        review.setGrade(dto.getGrade());
        review.setText(dto.getText());

        return review.getRno();
    }

    // delete
    public void deleteRow(Long rno) {
        reviewRepository.deleteById(rno);
    }

    private ReviewDTO entityToDto(Review review) {

        ReviewDTO dto = ReviewDTO.builder()
                .rno(review.getRno())
                .grade(review.getGrade())
                .text(review.getText())
                .mno(review.getMovie().getMno())
                .mid(review.getMember().getMid())
                .email(review.getMember().getEmail())
                .nickname(review.getMember().getNickname())
                .createDate(review.getCreateDate())
                .updateDate(review.getUpdateDate())
                .build();

        return dto;
    }

    private Review dtoToEntity(ReviewDTO dto) {

        Review review = Review.builder()
                .grade(dto.getGrade())
                .text(dto.getText())
                .movie(Movie.builder().mno(dto.getMno()).build())
                .member(Member.builder().mid(dto.getMid()).build())
                .build();

        return review;
    }
}
