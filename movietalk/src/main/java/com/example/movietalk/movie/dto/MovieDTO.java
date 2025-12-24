package com.example.movietalk.movie.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MovieDTO {

    private Long mno;

    private String title;

    private Long reviewCnt;

    private double avg;

    private LocalDateTime createDate;
    private LocalDateTime updateDate;

    // 映画のイメージ
    @Builder.Default
    private List<MovieImageDTO> movieImages = new ArrayList<>();
}
