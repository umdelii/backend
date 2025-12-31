package com.example.movietalk.movie.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;

import com.example.movietalk.movie.entity.Movie;
import com.example.movietalk.movie.entity.Review;
import java.util.List;
import com.example.movietalk.member.entity.Member;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    // mnoを基準として
    // type = EntityGraphType.FETCH => attributePathsだけEAGER、他はLAZY
    // type = EntityGraphType.LOAD => attributePathsだけEAGER、他はクラスに書いてある通りで
    @EntityGraph(attributePaths = { "member" }, type = EntityGraphType.FETCH)
    List<Review> findByMovie(Movie movie);

    // review投稿者をdeleteしたらreviewも一緒に削除
    @Query("delete from Review r where r.member = :member")
    @Modifying
    void deleteByMember(Member member);

    // 映画をdeleteしたらreviewを削除
    @Query("delete from Review r where r.movie = ?1")
    @Modifying
    void deleteByMovie(Movie movie);
}
