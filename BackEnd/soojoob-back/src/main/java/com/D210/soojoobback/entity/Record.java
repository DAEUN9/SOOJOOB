package com.D210.soojoobback.entity;


import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@Entity
@NoArgsConstructor
public class Record {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User userRecord;

    private Double totalDistance;

    private Integer totalTrashCount;

//    private Double totalCalorie;
    private Integer totalTimeRecord;

    private Double exp;

    @Builder
    public Record(User userRecord) {
        this.userRecord = userRecord;
        this.totalDistance = 0.0;
//        this.totalCalorie = 0.0;
        this.totalTrashCount = 0;
        this.totalTimeRecord = 0;
        this.exp = 0.0;
    }
}

