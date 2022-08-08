package com.D210.soojoobback.entity;

import com.D210.soojoobback.dto.badge.BadgeListResDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
@Data
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Badge {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @OneToMany(mappedBy = "badge")
    @JsonIgnore
    private List<UserBadge> userBadges = new ArrayList<>();

    @Column
    String badgeName;

    @Column
    String badgeDetail;

    @Column
    String imgUrl;

//    public BadgeListResDto toBuildBadge() {
//        return BadgeListResDto.builder()
//                .badgeId(this.id)
//                .badgeName(this.badgeName)
//                .badgeDetail(this.badgeDetail)
//                .imgUrl(this.imgUrl)
//                .build();
//    }

}
