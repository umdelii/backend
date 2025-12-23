package com.example.movietalk.movie.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "movie")
@Builder
public class MovieImage {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long inum;

    private String uuid;

    private String path;

    private String imgName;

    private int ord; // イメージ順番

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "mno")
    private Movie movie;
}
