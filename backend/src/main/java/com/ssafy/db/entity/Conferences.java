package com.ssafy.db.entity;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@ToString(of = {"cid", "sessionName", "uniqueSessionName", "confStartTime", "confEndTime", "activation"})
public class Conferences {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long cid;

    @Column(nullable = false)
    String sessionName;
    @Column(nullable = false)
    String uniqueSessionName;

    @Column(nullable = false)
    LocalDateTime confStartTime;

    LocalDateTime confEndTime;

    @Column(nullable = false, columnDefinition = "tinyint(1) default 1")
    boolean activation;

    @OneToMany(mappedBy ="user", cascade = CascadeType.ALL)
    List<DrawingImgPath> drawingImgPathList = new ArrayList<DrawingImgPath>();

}
