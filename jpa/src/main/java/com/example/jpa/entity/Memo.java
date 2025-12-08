package com.example.jpa.entity;

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
@Table(name = "memotbl")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
public class Memo extends BaseEntity {
    // 테이블(memotbl) 컬럼 : mno, meno_text, create_date, update_date
    // 클래스 필드명과 테이블 컬럼명을 일치시키느냐 아니냐
    // 아니라면?(@Column)

    @Id
    @Column(name = "memo_no")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long mno;

    @Column(nullable = false)
    private String menoText;

    // @CreatedDate
    // private LocalDateTime createDate;

    // @LastModifiedDate
    // private LocalDateTime updateDate;

    // update method 만들기
    public void changeMenoText(String menoText) {
        this.menoText = menoText;
    }
}
