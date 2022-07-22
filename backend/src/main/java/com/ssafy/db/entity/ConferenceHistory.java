package com.ssafy.db.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Getter
@Setter
public class ConferenceHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int chid;

    @ManyToOne
    @JoinColumn(name ="uid")
    Users user;

    @ManyToOne
    @JoinColumn(name = "cid")
    Conferences conferences;

    @Column(nullable = false)
    int actType;

    @Column(nullable = false)
    Timestamp historyCreateTime;
}
