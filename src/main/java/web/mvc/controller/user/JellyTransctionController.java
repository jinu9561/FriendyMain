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


    @PutMapping("/refund")
    public ResponseEntity<?> refund(@RequestBody RefundDTO refundDTO) {
        return ResponseEntity.status(HttpStatus.OK).body(jellyTransctionService.refund(refundDTO));
    }
}
