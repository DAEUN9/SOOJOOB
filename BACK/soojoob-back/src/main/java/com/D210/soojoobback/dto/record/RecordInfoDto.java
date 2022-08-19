package com.D210.soojoobback.dto.record;

import com.D210.soojoobback.dto.user.UserDTO;
import com.D210.soojoobback.entity.Record;
import com.D210.soojoobback.entity.User;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RecordInfoDto {

    private UserDTO userRecord;

    private Double totalDistance;

    private Integer totalTrashCount;

    //    private Double totalCalorie;
    private Integer totalTimeRecord;

    private Integer badgeCount;

    private Double exp;

    public RecordInfoDto(User user, Record record) {
        this.userRecord = new UserDTO(user);
        this.totalDistance = record.getTotalDistance();
        this.totalTimeRecord = record.getTotalTimeRecord();
        this.totalTrashCount = record.getTotalTrashCount();
        this.badgeCount = record.getBadgeCount();
        this.exp = record.getExp();
    }

}
