package com.D210.soojoobback.entity;

import lombok.Getter;

import javax.persistence.*;

@Getter
@Entity
@Table(name = "Publictoilet")
public class Publictoilet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long PTno;

    @Column(name = " PTLatitude")
    private String PTLatitude;

    @Column(name = "PTlongitude")
    private String PTlongitude;

    @Column(name = "PTlocation")
    private String PTlocation;

    @Column(name = "PToperatingtime")
    private String PToperatingtime;
}
