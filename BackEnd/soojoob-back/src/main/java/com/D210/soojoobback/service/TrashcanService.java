package com.D210.soojoobback.service;

import com.D210.soojoobback.dto.trashcan.TrashcanDto;
import com.D210.soojoobback.repository.TrashcanRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.D210.soojoobback.entity.Trashcan;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class TrashcanService {

    private final TrashcanRepository trashcanRepository;

    @Transactional(readOnly = true)
    public List<TrashcanDto> showAll(){
        List<Trashcan> trashcans = trashcanRepository.findAll();

        return trashcans.stream()
                .map(trashcan -> new TrashcanDto(trashcan.getTCno(),
                        trashcan.getTCLatitude(),
                        trashcan.getTClongitude(),
                        trashcan.getTClocation()))
                .collect(Collectors.toList());
    }
}
