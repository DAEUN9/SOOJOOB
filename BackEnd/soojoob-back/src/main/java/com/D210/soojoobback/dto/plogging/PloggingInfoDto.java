package com.D210.soojoobback.dto.plogging;

import com.D210.soojoobback.entity.Plogging;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PloggingInfoDto {

    private Long id;
    private Long ploggingUser;
    private Double distance;
//    private String startTime;
//    private String endTime;
    private Integer timeRecord;
    private String dateTime;
    private Integer stepCount;
    private Integer trashCount;
    private String ploggingImg;
//    private Double calorie;
//    private Double bpm;


//
//    public PloggingInfoDto(Long id, )
    @Builder
    public PloggingInfoDto(Plogging plogging) {
        this.id = plogging.getId();
        this.ploggingUser = plogging.getPloggingUser().getId();
        this.distance = plogging.getDistance();
//        this.startTime = plogging.getStartTime();
//        this.endTime = plogging.getEndTime();
        this.timeRecord = plogging.getTimeRecord();
        this.dateTime = plogging.getDateTime();
        this.trashCount = plogging.getTrashCount();
        this.ploggingImg = plogging.getPloggingImg();
//        this.calorie = plogging.getCalorie();
//        this.bpm = plogging.getBpm();
    }

//    public Plogging toEntity(){
//        return Plogging.builder()
//                .id(id)
//                .username(username)
//                .email(email)
//                .password(password)
//                .build();
//    }


}
