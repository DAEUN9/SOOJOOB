package com.D210.soojoobback.entity;

import com.D210.soojoobback.dto.plogging.PostPloggingReqDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

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

    private Double distance;

    private String startTime;

    private String endTime;

    private Integer stepCount;

    private Integer trashCount;
    private String floggingImg;
    private Double calorie;
    private Double bpm;


    public Plogging(User ploggingUser, Double distance, String startTime, String endTime, Integer stepCount, Integer trashCount, String floggingImg, Double calorie, Double bpm) {
        this.ploggingUser = ploggingUser;
        this.distance = distance;
        this.startTime = startTime;
        this.endTime = endTime;
        this.stepCount = stepCount;
        this.trashCount = trashCount;
        this.floggingImg = floggingImg;
        this.calorie = calorie;
        this.bpm = bpm;
    }

    public Plogging(PostPloggingReqDto requestDto, User user) {
        this.distance = requestDto.getDistance();
        this.startTime = requestDto.getStartTime();
        this.endTime = requestDto.getEndTime();
        this.stepCount = requestDto.getStepCount();
        this.floggingImg = requestDto.getFloggingImg();
        this.calorie = requestDto.getCalorie();
        this.bpm = requestDto.getBpm();
        this.ploggingUser = user;
    }

    public static Plogging of(PostPloggingReqDto requestDto, User user) {
        return new Plogging(requestDto,user);
    }

    public boolean isrunnedBy(User user) {
        return this.ploggingUser.getId().equals(user.getId());
    }
}

