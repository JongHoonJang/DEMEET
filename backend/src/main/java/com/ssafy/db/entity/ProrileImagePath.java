package com.ssafy.db.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class ProrileImagePath {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int pipid;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="uid")
    Users user;

    @Column(nullable = false, unique = true)
    String path;
}
