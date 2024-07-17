package web.mvc.controller.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import web.mvc.dto.user.JellyTransactionDTO;
import web.mvc.dto.user.RefundDTO;
import web.mvc.service.user.JellyTransctionService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/jelly")
@Slf4j
public class JellyTransctionController {

    private final JellyTransctionService jellyTransctionService;

    @PostMapping("/{userSeq}")
    public ResponseEntity<?> payment(@PathVariable Long userSeq, @RequestBody JellyTransactionDTO jellyTransactionDTO) {
        log.info(jellyTransactionDTO.toString());
        return ResponseEntity.status(HttpStatus.OK).body(jellyTransctionService.payment(userSeq,jellyTransactionDTO));
    }

    /**
     * 출석 시 젤리 2개
     * */
    @PostMapping("/add/{userSeq}")
    public ResponseEntity<?> addJelly(@PathVariable Long userSeq, @RequestBody JellyTransactionDTO jellyTransactionDTO) {
        log.info("Received add jelly request for userSeq: {}", userSeq);
        log.info("JellyTransactionDTO: {}", jellyTransactionDTO);

        try {
            String result = jellyTransctionService.addJelly(userSeq, jellyTransactionDTO);
            return ResponseEntity.status(HttpStatus.OK).body(result);
        } catch (Exception e) {
            log.error("해당 userSeq에게 젤리 지급 실패: {}", userSeq, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    /**
     * 글 작성 시 2개 지급
     * */
    @PostMapping("/add/board/{userSeq}")
    public ResponseEntity<?> addJellyByWritePost(@PathVariable Long userSeq, @RequestBody JellyTransactionDTO jellyTransactionDTO) {
        log.info("Received add jelly request for userSeq: {}", userSeq);
        log.info("JellyTransactionDTO: {}", jellyTransactionDTO);

        try {
            String result = jellyTransctionService.addJellyByWritePost(userSeq, jellyTransactionDTO);
            return ResponseEntity.status(HttpStatus.OK).body(result);
        } catch (Exception e) {
            log.error("해당 userSeq에게 젤리 지급 실패: {}", userSeq, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    /**
     * 소모임 호스트에게 젤리 20개 지급
     * */
    @PostMapping("/add/host/{userSeq}")
    public ResponseEntity<?> addJellyToHost(@PathVariable Long userSeq, @RequestBody JellyTransactionDTO jellyTransactionDTO) {
        log.info("Received add jelly request for userSeq: {}", userSeq);
        log.info("JellyTransactionDTO: {}", jellyTransactionDTO);

        try {
            String result = jellyTransctionService.addJellyToHost(userSeq, jellyTransactionDTO);
            return ResponseEntity.status(HttpStatus.OK).body(result);
        } catch (Exception e) {
            log.error("해당 userSeq에게 젤리 지급 실패: {}", userSeq, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PostMapping("/use/{userSeq}")
    public ResponseEntity<?> useJelly(@PathVariable Long userSeq, @RequestBody JellyTransactionDTO jellyTransactionDTO) {
        log.info(jellyTransactionDTO.toString());
        return ResponseEntity.status(HttpStatus.OK).body(jellyTransctionService.useJelly(userSeq,jellyTransactionDTO));
    }

    @PutMapping("/refund")
    public ResponseEntity<?> refund(@RequestBody RefundDTO refundDTO) {
        return ResponseEntity.status(HttpStatus.OK).body(jellyTransctionService.refund(refundDTO));
    }
}
