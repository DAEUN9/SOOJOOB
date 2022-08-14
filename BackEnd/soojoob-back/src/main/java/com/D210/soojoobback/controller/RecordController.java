package com.D210.soojoobback.controller;


import com.D210.soojoobback.dto.record.RecordInfoDto;
import com.D210.soojoobback.dto.user.ResponseDto;
import com.D210.soojoobback.entity.Record;
import com.D210.soojoobback.exception.CustomErrorException;
import com.D210.soojoobback.security.UserDetailsImpl;
import com.D210.soojoobback.service.RecordService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/record")
public class RecordController {


    private final RecordService recordService;

    @GetMapping("")
    @ResponseBody
    public ResponseDto userInfoDetails(
            @AuthenticationPrincipal UserDetailsImpl userDetails) {

        checkLogin(userDetails);
        RecordInfoDto record = recordService.detailsRecordrInfo(userDetails);

        return new ResponseDto(200L, "회원 총 기록을 전송했습니다", record);

    }

    private void checkLogin(
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        System.out.println(userDetails);
        if (userDetails == null) {
            throw new CustomErrorException("로그인이 필요합니다.2");
        }
    }

    @GetMapping("/rank")
    private ResponseDto recordRank() {

        List<RecordInfoDto> records = recordService.rankRecords();

        return new ResponseDto(200L, "exp 순위를 전송했습니다", records);
    }
}
