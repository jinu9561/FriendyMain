package web.mvc.exception.partyBoard;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import web.mvc.exception.common.ErrorCode;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {


//    @ExceptionHandler(HttpMessageNotReadableException.class)
//    public ResponseEntity<?> exception(HttpMessageNotReadableException e) {
//        System.out.println("메세지 테스트"+ErrorCode.WRONG_TYPE.getMessage());
//
//        HttpHeaders resHeaders = new HttpHeaders();
//        resHeaders.add("Content-Type", "application/json;charset=UTF-8");
//
//        return new ResponseEntity<>(ErrorCode.WRONG_TYPE.getMessage(), resHeaders,HttpStatus.BAD_REQUEST);
//
//
////        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body();
//    }


}
