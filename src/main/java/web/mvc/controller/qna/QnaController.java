package web.mvc.controller.qna;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import web.mvc.service.qna.QnaService;

@RestController
@RequestMapping("/api/chatbot")
@CrossOrigin(origins = "http://localhost:3000")
public class QnaController {

    @Autowired
    private QnaService qnaService;

    @PostMapping("/qnaDesc")
    public String askQuestion(@RequestBody String question) {

        return qnaService.getQnaReply(question);
    }

}
