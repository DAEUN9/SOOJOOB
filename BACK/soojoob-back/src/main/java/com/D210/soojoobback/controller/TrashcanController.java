package com.D210.soojoobback.controller;

import com.D210.soojoobback.dto.trashcan.TrashcanDto;
import com.D210.soojoobback.dto.user.ResponseDto;
import com.D210.soojoobback.service.TrashcanService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/trashcan")
public class TrashcanController {
    private final TrashcanService trashcanService;

    @GetMapping("")
    public ResponseEntity showAll(){
        List<TrashcanDto> trashcans = trashcanService.showAll();
        return ResponseEntity.ok(trashcans);
    }
}
