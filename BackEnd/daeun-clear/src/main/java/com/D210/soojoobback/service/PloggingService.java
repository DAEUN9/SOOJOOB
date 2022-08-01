package com.D210.soojoobback.service;


import com.D210.soojoobback.dto.plogging.PloggingInfoDto;
import com.D210.soojoobback.dto.plogging.PostPloggingReqDto;
import com.D210.soojoobback.dto.user.ResponseDto;
import com.D210.soojoobback.dto.user.SignupRequestDto;
import com.D210.soojoobback.dto.user.UserDTO;
import com.D210.soojoobback.entity.Plogging;
import com.D210.soojoobback.entity.User;
import com.D210.soojoobback.exception.CustomErrorException;
import com.D210.soojoobback.repository.PloggingRepository;
import com.D210.soojoobback.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class PloggingService {

    private final PloggingRepository ploggingRepository;
    private final UserRepository userRepository;

    private final UserService userService;


//    @Transactional
//    public Plogging save(PostPloggingReqDto requestDto, Long userId){
//        User ploggingUser = userId;
//        Double distance = requestDto.getDistance();
//        String startTime = requestDto.getStartTime();
//        String endTime = requestDto.getEndTime();
//        Integer stepCount = requestDto.getStepCount();
//        Integer trashCount = requestDto.getTrashCount();
//        String floggingImg = requestDto.getFloggingImg();
//        Double calorie = requestDto.getCalorie();
//        Double bpm = requestDto.getBpm();
//
//
//        Plogging plogging = new Plogging(ploggingUser, distance, startTime, endTime, stepCount,
//                trashCount, floggingImg, calorie, bpm);
//
//        ploggingRepository.save(plogging);
//        return plogging;
//    }

    @Transactional
    public void save(PostPloggingReqDto requestDto, User user) {
        Plogging plogging = Plogging.of(requestDto, user);
        System.out.println("plogging");
        // fetch Lazy 유저를 진짜 유저로 변환
        Long userId = user.getId();
        User ploggingUser = userRepository.findById(userId).orElseThrow(
                () -> new CustomErrorException("유저 정보를 찾을 수 없습니다.")
        );
        //유저에 플로깅 추가
        List<Plogging> ploggingList = ploggingUser.getPloggings();
        ploggingList.add(plogging);

        ploggingRepository.save(plogging);


    }

    public PloggingInfoDto findByPloggingId(Long id){
        Plogging plogging = ploggingRepository.findByPloggingId(id).orElseThrow(() ->  new IllegalArgumentException("해당 플로깅이 없습니다."));
        return new PloggingInfoDto(plogging);
    }

    @Transactional
    public void deletePlogging(Long ploggingId, User user) {
        Plogging plogging = getPloggingById(ploggingId);
        if(plogging.isrunnedBy(user)) {;
        } else {
            ploggingRepository.delete(plogging);
            throw new CustomErrorException("잘못된 사용자 입니다.");
        }
    }




    public Plogging getPloggingById(Long ploggingId) {
        return ploggingRepository.findByPloggingId(ploggingId).orElseThrow(
                () -> new CustomErrorException("플로깅을 찾을 수 없습니다.")
        );
    }
}
