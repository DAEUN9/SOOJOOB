package com.D210.soojoobback.entity;

import lombok.Getter;

import javax.persistence.*;

@Getter
@Entity
@Table(name = "trashcan")
public class Trashcan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long TCno;

    @Column(name = " TCLatitude")
    private String TCLatitude;

    @Column(name = "TClongitude")
    private String TClongitude;

    @Column(name = "TClocation")
    private String TClocation;
}
