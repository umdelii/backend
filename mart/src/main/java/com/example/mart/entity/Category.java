package com.example.mart.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "mart_category")
// @ToString(exclude = "items")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long id;

    private String name;

    // 다대다 관계 테이블을 jpa에게 직접 실행시킴
    // 단점 : 컬럼 추가 어렵다
    // @ManyToMany
    // @Builder.Default
    // @JoinTable(name = "mart_category_item", joinColumns = @JoinColumn(name =
    // "category_id"), inverseJoinColumns = @JoinColumn(name = "item_id"))
    // private List<Item> items = new ArrayList<>();

    @OneToMany(mappedBy = "category")
    @Builder.Default
    private List<CategoryItem> categoryItems = new ArrayList<>();
}
