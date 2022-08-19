package com.D210.soojoobback.controller;

import com.D210.soojoobback.dto.publictoilet.PublictoiletDto;
import com.D210.soojoobback.service.PublictoiletService;
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
@RequestMapping(value = "/toilet")
public class PublictoiletController {
    private final PublictoiletService publictoiletService;

    @GetMapping("")
    public ResponseEntity showAll(){
        List<PublictoiletDto> toilets = publictoiletService.showAll();
        return ResponseEntity.ok(toilets);
    }
}
