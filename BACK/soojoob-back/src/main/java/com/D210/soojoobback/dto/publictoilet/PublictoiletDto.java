package com.D210.soojoobback.dto.publictoilet;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@RequiredArgsConstructor
public class PublictoiletDto {

    private Long PTno;

    private String PTlatitude;

    private String PTlongitude;

    private String PTlocation;

    private String PToperatingtime;

    @Builder
    public PublictoiletDto(Long PTno, String PTlatitude, String PTlongitude, String PTlocation, String PToperatingtime){
        this.PTno = PTno;
        this.PTlatitude = PTlatitude;
        this.PTlongitude = PTlongitude;
        this.PTlocation = PTlocation;
        this.PToperatingtime = PToperatingtime;
    }
}
