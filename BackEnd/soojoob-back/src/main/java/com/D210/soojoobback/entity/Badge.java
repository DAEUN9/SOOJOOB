package com.D210.soojoobback.entity;

import com.D210.soojoobback.dto.badge.BadgeListResDto;
import lombok.Data;
import lombok.Getter;

import javax.persistence.*;


@Entity
@Data
@Getter
public class Badge {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private User user;

    @Column
    String badgeName;

    @Column
    String badgeDetail;

    @Column
    String imgUrl;

    public BadgeListResDto toBuildBadge() {
        return BadgeListResDto.builder()
                .badgeId(this.id)
                .badgeName(this.badgeName)
                .badgeDetail(this.badgeDetail)
                .imgUrl(this.imgUrl)
                .build();
    }

}
