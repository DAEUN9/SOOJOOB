package com.D210.soojoobback.dto.badge;


import com.D210.soojoobback.entity.Badge;
import com.D210.soojoobback.entity.User;
import lombok.*;

@Data
@Getter
@Builder
@AllArgsConstructor
public class BadgeListResDto {
    private final Long badgeId;
    private final String badgeName;
    private final String badgeDetail;
    private final String imgUrl;

    public BadgeListResDto(Badge badge){
        this.badgeId = badge.getId();
        this.badgeName = badge.getBadgeName();
        this.badgeDetail = badge.getBadgeDetail();
        this.imgUrl = badge.getImgUrl();
    }
}
