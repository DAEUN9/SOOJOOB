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

    @Column(nullable = false)
    private Double distance;

    private String startTime;

    private String endTime;

    private Integer stepCount;

    @Column(nullable = false)
    private Integer trashCount;
    private String ploggingImg;
    @Column(nullable = false)
    private Double calorie;
    private Double bpm;


    public Plogging(User ploggingUser, Double distance, String startTime, String endTime, Integer stepCount, Integer trashCount, String ploggingImg, Double calorie, Double bpm) {
        this.ploggingUser = ploggingUser;
        this.distance = distance;
        this.startTime = startTime;
        this.endTime = endTime;
        this.stepCount = stepCount;
        this.trashCount = trashCount;
        this.ploggingImg = ploggingImg;
        this.calorie = calorie;
        this.bpm = bpm;
    }

    public Plogging(PostPloggingReqDto requestDto, User user) {
        this.distance = requestDto.getDistance();
        this.startTime = requestDto.getStartTime();
        this.endTime = requestDto.getEndTime();
        this.stepCount = requestDto.getStepCount();
        this.ploggingImg = requestDto.getPloggingImg();
        this.calorie = requestDto.getCalorie();
        this.bpm = requestDto.getBpm();
        this.trashCount = requestDto.getTrashCount();
        this.ploggingUser = user;
    }

    public static Plogging of(PostPloggingReqDto requestDto, User user) {
        return new Plogging(requestDto,user);
    }

    public boolean isrunnedBy(User user) {
        return this.ploggingUser.getId().equals(user.getId());
    }
}

