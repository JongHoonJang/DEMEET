package com.ssafy.db.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Getter
@Setter
@ToString
public class ProfileImagePath {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long pipid;

    @ManyToOne
    @JoinColumn(name="uid")
    Users user;

    @Column(nullable = false, unique = true)
    String path;
}
