package com.D210.soojoobback.dto.trashcan;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class TrashcanDto {

    private Long TCno;
    private String TClatitude;
    private String TClongitude;
    private String TClocation;

    @Builder
    public TrashcanDto(Long TCno, String TClatitude, String TClongitude, String TClocation){
        this.TCno = TCno;
        this.TClatitude = TClatitude;
        this.TClongitude = TClongitude;
        this.TClocation = TClocation;
    }
}
