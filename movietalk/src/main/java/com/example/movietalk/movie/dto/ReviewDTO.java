package com.example.movietalk.movie.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewDTO {

    private Long rno;

    private int grade;

    private String text;

    // Movie.mno
    private Long mno;

    // Member
    private Long mid;

    private String email;

    private String nickname;

    // BaseEntity
    private LocalDateTime createDate;

    private LocalDateTime updateDate;

}
