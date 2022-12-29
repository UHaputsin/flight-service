package com.test.controller.check_in.v1;

import com.test.controller.check_in.dto.CheckInRequestDto;
import com.test.controller.check_in.dto.CheckInResponseDto;
import com.test.service.CheckInService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(CheckInController.CHECKIN_CONTROLLER_MAPPING_V1)
public class CheckInController {

    public static final String CHECKIN_CONTROLLER_MAPPING_V1 = "/api/v1/check-in";

    private final CheckInService checkInService;

    public CheckInController(CheckInService checkInService) {
        this.checkInService = checkInService;
    }

    /**
     * @param dto must be filled or {@link com.test.exception.BadRequestException} will be thrown
     * @return success of check-in action
     */
    @PostMapping
    public CheckInResponseDto checkIn(@RequestBody CheckInRequestDto dto) {
        return checkInService.checkIn(dto);
    }
}
