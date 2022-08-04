package com.D210.soojoobback.service;

import com.D210.soojoobback.dto.publictoilet.PublictoiletDto;
import com.D210.soojoobback.dto.trashcan.TrashcanDto;
import com.D210.soojoobback.entity.Publictoilet;
import com.D210.soojoobback.repository.PublictoiletRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class PublictoiletService {

    private final PublictoiletRepository publictoiletRepository;

    @Transactional(readOnly = true)

    public List<PublictoiletDto> showAll(){
        List<Publictoilet> toilets = publictoiletRepository.findAll();

        return toilets.stream()
                .map(toilet -> new PublictoiletDto(toilet.getPTno(),
                        toilet.getPTLatitude(),
                        toilet.getPTlongitude(),
                        toilet.getPTlocation(),
                        toilet.getPToperatingtime()))
                .collect(Collectors.toList());
    }


}
