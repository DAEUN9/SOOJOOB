package com.D210.soojoobback.dto.plogging;

import com.D210.soojoobback.entity.Plogging;
import com.D210.soojoobback.entity.User;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PloggingInfoDto {

    private Long id;
    private User ploggingUser;
    private Double distance;
    private String startTime;
    private String endTime;
    private Integer stepCount;
    private Integer trashCount;
    private String floggingImg;
    private Double calorie;
    private Double bpm;


//    @Builder
//    public PloggingInfoDto(Long id, )
    public PloggingInfoDto(Plogging plogging) {
        this.id = plogging.getId();
        this.distance = plogging.getDistance();
        this.startTime = plogging.getStartTime();
        this.endTime = plogging.getEndTime();
        this.trashCount = plogging.getTrashCount();
        this.floggingImg = plogging.getFloggingImg();
        this.calorie = plogging.getCalorie();
        this.bpm = plogging.getBpm();
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
