package com.ssafy.db.entity;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Conferences {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long cid;

    @Column(nullable = false)
    String sessionName;

    @Column(nullable = false)
    Timestamp confStartTime;

    Timestamp confEndTime;

    @Column(nullable = false, columnDefinition = "tinyint(1) default 1")
    boolean activation;

    @OneToMany(mappedBy ="user", cascade = CascadeType.ALL)
    List<DrawingImgPath> drawingImgPathList = new ArrayList<DrawingImgPath>();

}
