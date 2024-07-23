package web.mvc.controller.generalBoard;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import web.mvc.dto.generalBoard.CommunityBoardDTO;
import web.mvc.service.generalBoard.CommunityBoardService;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/community-boards")
@Slf4j
public class CommunityBoardController {

    private final CommunityBoardService communityBoardService;

    @GetMapping
    public ResponseEntity<List<CommunityBoardDTO>> getAllCommunityBoards() {
        return ResponseEntity.ok(communityBoardService.getAllCommunityBoards());
    }

    @GetMapping("/realname")
    public ResponseEntity<List<CommunityBoardDTO>> getAllRealNameCommunityBoards() {
        return ResponseEntity.ok(communityBoardService.getAllRealNameCommunityBoards());
    }

    @GetMapping("/anonymous")
    public ResponseEntity<List<CommunityBoardDTO>> getAllAnonymousCommunityBoards() {
        return ResponseEntity.ok(communityBoardService.getAllAnonymousCommunityBoards());
    }

    @PostMapping
    public ResponseEntity<CommunityBoardDTO> createCommunityBoard(@RequestBody CommunityBoardDTO communityBoardDTO) {
        return ResponseEntity.ok(communityBoardService.createCommunityBoard(communityBoardDTO));
    }

    @GetMapping("/{commBoardSeq}")
    public ResponseEntity<CommunityBoardDTO> getCommunityBoardById(@PathVariable Long commBoardSeq) {
        return ResponseEntity.ok(communityBoardService.getCommunityBoardById(commBoardSeq));
    }

    @PutMapping("/{commBoardSeq}")
    public ResponseEntity<CommunityBoardDTO> updateCommunityBoard(@PathVariable Long commBoardSeq, @RequestBody CommunityBoardDTO communityBoardDTO) {
        return ResponseEntity.ok(communityBoardService.updateCommunityBoard(commBoardSeq, communityBoardDTO));
    }

    @DeleteMapping("/{commBoardSeq}")
    public String deleteCommunityBoard(@PathVariable Long commBoardSeq) {
        return communityBoardService.deleteCommunityBoard(commBoardSeq);
    }

    @GetMapping("/search")
    public ResponseEntity<List<CommunityBoardDTO>> searchByKeyword(@RequestParam int boardType, @RequestParam String keyword) {
        return ResponseEntity.ok(communityBoardService.searchCommunityBoards(boardType, keyword));
    }

    @PostMapping("/{commBoardSeq}/like")
    public ResponseEntity<Void> likeCommunityBoard(@PathVariable Long commBoardSeq, Long userSeq) {
        System.out.println("@@@@@");
        System.out.println("userSeq ==" + userSeq);
        System.out.println("commBoardSeq == " + commBoardSeq);
        boolean success = communityBoardService.likeCommunityBoard(commBoardSeq, userSeq);
        System.out.println("success == " + success);
        return success ? ResponseEntity.ok().build() : ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    @PostMapping("/{commBoardSeq}/dislike")
    public ResponseEntity<Void> dislikeCommunityBoard(@PathVariable Long commBoardSeq, Long userSeq) {
        System.out.println("@@1111@@@");
        System.out.println("userSeq ==" + userSeq);
        System.out.println("commBoardSeq == " + commBoardSeq);
        boolean success = communityBoardService.dislikeCommunityBoard(commBoardSeq, userSeq);
        System.out.println("success == " + success);
        return success ? ResponseEntity.ok().build() : ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }
}
