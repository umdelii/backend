package com.example.jpa.entity;

// import java.time.LocalDateTime;

// import org.springframework.data.annotation.CreatedDate;
// import org.springframework.data.annotation.LastModifiedDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "boardtbl")
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Board extends BaseEntity {
    // id(auto_increasement), 제목(title), 내용(content-1500), 작성자(writer), 작성일, 수정일

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, length = 1500)
    private String content;

    @Column(nullable = false, length = 20)
    private String writer;

    // @CreatedDate
    // private LocalDateTime createTime;

    // @LastModifiedDate
    // private LocalDateTime updateTime;

    // 각 엔티티에 공통된 칼럼들이 있다면 그걸 따로 클래스로 분리시켜서 불러오는건(상속) 어떨까?

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
