package timetogether.global.response;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum BaseResponseStatus {
  // -------- 성공 코드 시작 -------- //
  SUCCESS(HttpStatus.OK, "요청에 성공했습니다."),
  // -------- 성공 코드 종료 -------- //

  // -------- 실패 코드 시작 -------- //
  /**
   * 서버 내부 오류
   */
  INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 내부 오류입니다."),
  /**
   * Calendar
   */
  NOT_FOUND_MEETINGS_IN_CALENDAR(HttpStatus.NOT_FOUND, "등록된 일정이 없습니다."),
  /**
   * User
   */
  NOT_VALID_USER(HttpStatus.NOT_FOUND, "유효한 유저가 아닙니다.");
  // -------- 실패 코드 종료 -------- //
  private HttpStatus httpStatus; //상태
  private String message; //메시지

  BaseResponseStatus(HttpStatus httpStatus, String message) {
    this.httpStatus = httpStatus;
    this.message = message;
  }
  BaseResponseStatus(HttpStatus httpStatus) {
    this.httpStatus = httpStatus;
  }
}