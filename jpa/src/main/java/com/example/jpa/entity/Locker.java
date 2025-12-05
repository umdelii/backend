package com.example.jpa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
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
@ToString(exclude = "sportsMember")
public class Locker {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lokcer_id")
    private Long id;

    private String name;

    @OneToOne(mappedBy = "locker")
    private SportsMember sportsMember;
}
