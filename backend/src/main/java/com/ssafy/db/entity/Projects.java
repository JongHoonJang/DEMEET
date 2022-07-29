package com.ssafy.db.entity;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Getter
@Setter
public class Projects {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int pid;
    @Column(nullable = false)
    int ownerId;
    @Column(nullable = false)
    Timestamp pjtStartDate;

    Timestamp pjtEndDate;

    @Column(nullable = false)
    String pjtName;

    String pjtDesc;
    @Column(nullable = false)
    Timestamp totalMeetTime;
    @Column(nullable = false, columnDefinition = "tinyint(1) default 1")
    boolean activation;
}
