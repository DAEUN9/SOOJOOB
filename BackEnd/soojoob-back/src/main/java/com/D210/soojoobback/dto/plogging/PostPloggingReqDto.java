package com.D210.soojoobback.dto.plogging;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class PostPloggingReqDto {

    private Long userId;
    private Double distance;
    private String startTime;
    private String endTime;
    private Integer stepCount;
    private Integer trashCount;
    private String ploggingImg;
    private Double calorie;
    private Double bpm;


}
