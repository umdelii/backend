package com.example.movietalk.movie.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.example.movietalk.movie.entity.MovieImage;
import com.example.movietalk.movie.entity.Movie;

public interface MovieImageRepository extends JpaRepository<MovieImage, Long> {

    // // update 映画削除するとイメージ全部削除
    // @Query("delete from MovieImage mi where mi.mno = :mno")
    // void deleteImagesbyMno(Long mno);

    @Query("delete from MovieImage mi where mi.movie = :movie")
    @Modifying
    void deleteByMovie(Movie movie);

    // 過去ファイルのpath取得
    @Query("select mi from MovieImage mi where mi.path = ?1")
    List<MovieImage> getOldFileImages(String path);
}
