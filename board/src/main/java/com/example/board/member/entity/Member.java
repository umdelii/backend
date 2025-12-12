package com.example.board.member.entity;

import java.util.ArrayList;
import java.util.List;

import com.example.board.post.entity.Board;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "board_member")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = "boards")
public class Member {
    @Id
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "writer")
    @Builder.Default
    private List<Board> boards = new ArrayList<>();

}
