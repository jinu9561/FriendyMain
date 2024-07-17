package web.mvc.service.generalBoard;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import web.mvc.entity.generalBoard.PhotoBoard;
import web.mvc.entity.generalBoard.PhotoBoardLike;
import web.mvc.entity.user.Users;
import web.mvc.exception.common.ErrorCode;
import web.mvc.exception.common.GlobalException;
import web.mvc.repository.generalBoard.PhotoBoardLikeRepository;
import web.mvc.repository.generalBoard.PhotoBoardRepository;
import web.mvc.repository.user.UserRepository;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class PhotoBoardLikeServiceImpl implements PhotoBoardLikeService {
    private final PhotoBoardRepository photoBoardRepository;
    private final PhotoBoardLikeRepository photoBoardLikeRepository;
    private final UserRepository userRepository;

    // 좋아요 등록
    @Override
    public int insertLike(Long photoBoardSeq, Long userSeq) {
        PhotoBoard photoBoard = photoBoardRepository.findById(photoBoardSeq).orElseThrow(()->new GlobalException(ErrorCode.NOTFOUND_PHOTOBOARD));
        Users user = userRepository.findById(userSeq).orElseThrow(()->new GlobalException(ErrorCode.NOTFOUND_ID));

        int countLike = photoBoard.getPhotoBoardLike();

        if(!this.isCheck(photoBoardSeq,userSeq)){

            photoBoard.setPhotoBoardLike(countLike+1);
            PhotoBoardLike photoBoardLike = PhotoBoardLike.builder()
                    .photoBoard(photoBoard)
                    .user(user)
                    .build();
            photoBoardLikeRepository.save(photoBoardLike);

        }else{
            photoBoard.setPhotoBoardLike(countLike);
        }

        return countLike;

    }

    // 좋아요 삭제
    @Override
    public int deleteLike(Long photoBoardSeq, Long userSeq) {
        PhotoBoard photoBoard = photoBoardRepository.findById(photoBoardSeq).orElseThrow(()->new GlobalException(ErrorCode.NOTFOUND_PHOTOBOARD));


        int countLike = photoBoard.getPhotoBoardLike();


        if(this.isCheck(photoBoardSeq,userSeq)){
            PhotoBoardLike photoBoardLike = photoBoardLikeRepository.findByUserSeq(photoBoardSeq,userSeq);
            photoBoardLikeRepository.delete(photoBoardLike);
            if(countLike >0){
                photoBoard.setPhotoBoardLike(countLike-1);
            }
        }else{
            photoBoard.setPhotoBoardLike(countLike);
        }
        return countLike;

    }


    // 좋아요 검사
    @Override
    public boolean isCheck(Long photoBoardSeq, Long userSeq){
        PhotoBoardLike photoBoardLike = photoBoardLikeRepository.findByUserSeq(photoBoardSeq,userSeq);
        if(photoBoardLike != null){
            //해당 내용이 있다 true
            return true;
        }
        return false;

    }
}
