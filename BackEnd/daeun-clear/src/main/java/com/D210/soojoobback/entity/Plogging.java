package com.D210.soojoobback.entity;

import com.D210.soojoobback.dto.plogging.PostPloggingReqDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Blob;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Plogging {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User ploggingUser;

    @Column(nullable = false)
    private Double distance;

//    private String startTime;

//    private String endTime;

    private Integer timeRecord;

    private String dateTime;

    private Integer stepCount;

    @Column(nullable = false)
    private Integer trashCount;

    private String ploggingImg;

//    @Column(nullable = false)
//    private Double calorie;
//    private Double bpm;


    public Plogging(User ploggingUser, Double distance, Integer timeRecord, String dateTime,Integer stepCount, Integer trashCount, String ploggingImg) {
        this.ploggingUser = ploggingUser;
        this.distance = distance;
        this.timeRecord = timeRecord;
        this.dateTime = dateTime;
//        this.startTime = startTime;
//        this.endTime = endTime;
        this.stepCount = stepCount;
        this.trashCount = trashCount;
        this.ploggingImg = ploggingImg;
//        this.calorie = calorie;
//        this.bpm = bpm;
    }

    public Plogging(PostPloggingReqDto requestDto, User user) {
        this.distance = requestDto.getDistance();
//        this.startTime = requestDto.getStartTime();
//        this.endTime = requestDto.getEndTime();
        this.timeRecord = requestDto.getTimeRecord();
        this.dateTime = requestDto.getDateTime();

        this.stepCount = requestDto.getStepCount();
        this.trashCount = requestDto.getTrashCount();
        this.ploggingImg = requestDto.getPloggingImg();
//        this.calorie = requestDto.getCalorie();
//        this.bpm = requestDto.getBpm();
        this.ploggingUser = user;
    }

    public static Plogging of(PostPloggingReqDto requestDto, User user) {
        return new Plogging(requestDto,user);
    }

    public boolean isrunnedBy(User user) {
        return this.ploggingUser.getId().equals(user.getId());
    }
}

