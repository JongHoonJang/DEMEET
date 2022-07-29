package com.ssafy.db.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class UserConference {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int ucid;

    @ManyToOne
    @JoinColumn(name = "uid")
    Users user;

    @ManyToOne
    @JoinColumn(name = "cid")
    Conferences conference;
}
