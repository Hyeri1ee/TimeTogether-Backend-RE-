package timetogether.calendar.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import timetogether.calendar.dto.response.CalendarViewResponseDto;
import timetogether.calendar.exception.CalendarNotExist;
import timetogether.calendar.service.CalendarViewService;
import timetogether.global.response.BaseResponse;
import timetogether.global.response.BaseResponseService;
import timetogether.jwt.service.JwtService;

import static org.hibernate.query.sqm.tree.SqmNode.log;

@RestController
@RequestMapping("/calendar")
@RequiredArgsConstructor
public class CalendarViewController {
  private final CalendarViewService calendarViewService;
  private final BaseResponseService baseResponseService;
  private final JwtService jwtService;

  /**
   * user의 캘린더 (특정 월) 일정 전체 조회
   *
   * @param year,month
   * @return BaseResponse<Object>
   */
  @GetMapping("/view/{year}/{month}")
  public BaseResponse<Object> viewAllSpecificMonthMeetings(
          @PathVariable(value = "year") int year,
          @PathVariable(value = "month") int month
  ) throws CalendarNotExist {
    // SecurityContext에서 인증된 사용자 정보 가져오기
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String socialId = authentication.getName();  // UserDetails에서 설정한 username(socialId) 가져오기
    log.info("SecurityContext에서 가져온 socialId:");
    log.info(socialId);

    Long calendarId = calendarViewService.putandGetCalendarId(socialId);
    CalendarViewResponseDto calendarViewResponseDto = calendarViewService.getMeetingsYearMonth(calendarId, year, month);
    return baseResponseService.getSuccessResponse(calendarViewResponseDto);
  }
//  @GetMapping("/view/{year}/{month}")
//  public BaseResponse<Object> viewAllSpecificMonthMeetings(
//          @RequestParam(value = "socialId") String socialId, //임시
//          @PathVariable(value = "year") int year,
//          @PathVariable(value = "month") int month
//  ) throws CalendarNotExist {
//
//    Long calendarId = calendarViewService.putandGetCalendarId(socialId);
//    CalendarViewResponseDto calendarViewResponseDto = calendarViewService.getMeetingsYearMonth(calendarId,year,month);
//    return baseResponseService.getSuccessResponse(calendarViewResponseDto);
//
//  }
  /**
   * user의 캘린더 (특정 월,날짜) 일정 전체 조회
   *
   * @param socialId, year, month
   * @return BaseResponse<Object>
   */
  @GetMapping("/view/{year}/{month}/{date}")
  public BaseResponse<Object> viewAllSpecificDateMeetings(
          @RequestParam(value = "socialId") String socialId,
          @PathVariable(value = "year") int year,
          @PathVariable(value = "month") int month,
          @PathVariable(value = "date") int date
  ) throws CalendarNotExist {

    Long calendarId = calendarViewService.putandGetCalendarId(socialId);
    CalendarViewResponseDto calendarViewResponseDto = calendarViewService.getMeetingsYearMonthDate(calendarId,year,month,date);
    return baseResponseService.getSuccessResponse(calendarViewResponseDto);

  }


  /**
   * user의 캘린더 (특정 월,날짜,미팅 아이디) 일정 조회
   *
   * @param socialId, year, month, date, meetingId
   * @return BaseResponse<Object>
   */
  @GetMapping("/view/{meetingId}")
  public BaseResponse<Object> viewSpecificMeeting(
          @RequestParam(value = "socialId") String socialId,//임시
          @PathVariable(value = "meetingId") Long meetingId
  ) throws CalendarNotExist {

    Long calendarId = calendarViewService.putandGetCalendarId(socialId);
    CalendarViewResponseDto calendarViewResponseDto = calendarViewService.getMeeting(calendarId,meetingId);
    return baseResponseService.getSuccessResponse(calendarViewResponseDto);

  }

}
