package com.D210.soojoobback.service;

import com.D210.soojoobback.UserDetailsImpl;
import com.D210.soojoobback.dto.user.UserInfoDetailsDto;
import com.D210.soojoobback.entity.Record;
import com.D210.soojoobback.repository.RecordRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class RecordService {

    private final RecordRepository recordRepository;

    public Record detailsRecordrInfo(UserDetailsImpl userDetails) {
        Long recordId = userDetails.getUser().getId();
        Record record = recordRepository.findById(recordId);

        return record;
    }
}
