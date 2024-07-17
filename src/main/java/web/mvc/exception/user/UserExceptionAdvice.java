package web.mvc.exception.user;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.mail.MessagingException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import web.mvc.exception.common.ErrorCode;
import web.mvc.exception.common.GlobalException;

import java.text.ParseException;
import java.time.LocalDateTime;

@RestControllerAdvice
public class UserExceptionAdvice {

    @ExceptionHandler({GlobalException.class, ParseException.class, MessagingException.class})
    public ProblemDetail exceptionHandler(GlobalException e) {

        ErrorCode errorCode = e.getErrorCode();

        ProblemDetail problemDetail = ProblemDetail.forStatus(errorCode.getStatus());

        problemDetail.setTitle(errorCode.getMessage());
        problemDetail.setProperty("timestamp", LocalDateTime.now());

        /**     ProblemDetail은 다음과 같은 기본 필드를 제공 예외를 자세하게 다룰 수 있는 api

         type: 오류 유형의 URI (일반적으로 오류 유형에 대한 문서를 가리킴).
         title: 간단한 오류 설명.
         status: HTTP 상태 코드.
         detail: 오류에 대한 자세한 설명.
         instance: 오류가 발생한 특정 리소스의 URI.

         따로 성정을 안하면 자동으로 적절하게 매핑을 한다!
         *
         * */

        return  problemDetail;

    }


}
