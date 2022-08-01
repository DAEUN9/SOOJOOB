package com.D210.soojoobback.dto.badge;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BadgeListResDto {
    private final Long badgeId;
    private final String badgeName;
    private final String badgeDetail;
    private final String imgUrl;
}
