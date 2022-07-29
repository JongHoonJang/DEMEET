package com.ssafy.db.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class UserProject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int upid;

    @ManyToOne
    @JoinColumn(name = "uid")
    Users users;

    @ManyToOne
    @JoinColumn(name = "pid")
    Projects projects;

}
