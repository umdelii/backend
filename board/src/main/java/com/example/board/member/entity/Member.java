package com.example.board.member.entity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.example.board.member.entity.constant.MemberRole;
import com.example.board.post.entity.Board;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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

    private boolean fromSocial;

    @ElementCollection(fetch = FetchType.LAZY)
    @Builder.Default
    private Set<MemberRole> roles = new HashSet<>();

    public void addMemberRole(MemberRole role) {
        roles.add(role);
    }

}
