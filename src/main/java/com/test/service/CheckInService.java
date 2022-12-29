package com.test.service;

import com.test.controller.check_in.dto.CheckInRequestDto;
import com.test.controller.check_in.dto.CheckInResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static com.test.validation.CheckInValidator.validateCheckInAction;

@Slf4j
@Service
public class CheckInService {

    public CheckInResponseDto checkIn(CheckInRequestDto dto) {
        validateCheckInAction(dto);

        return new CheckInResponseDto(false);
    }
}
