package com.example.board.post.entity;

import java.util.ArrayList;
import java.util.List;

import com.example.board.member.entity.Member;
import com.example.board.reply.entity.Reply;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "boardtbl")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = { "writer" })
public class Board extends BaseEntity {
    // bno(auto_increasement), 제목(title), 내용(content-1500), 작성자(writer), 작성일, 수정일

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bno;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    @Lob
    private String content;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "email")
    private Member writer;

    // @OneToMany(mappedBy = "board")
    // @Builder.Default
    // private List<Reply> replies = new ArrayList<>();
}
