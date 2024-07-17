package web.mvc.service.admin;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import web.mvc.dto.generalBoard.PhotoBoardDTO;
import web.mvc.service.generalBoard.PhotoBoardService;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class AdminPhotoServiceImpl implements AdminPhotoService {

    private final PhotoBoardService photoBoardService;

    @Override
    public List<PhotoBoardDTO> getAllPhotoBoardByDefault() {
        return photoBoardService.getAllPhotoBoardByDefault();
    }

    @Override
    public List<PhotoBoardDTO> getAllPhotoBoardByRegDate() {
        return photoBoardService.getAllPhotoBoardByRegDate();
    }

    @Override
    public List<PhotoBoardDTO> getAllPhotoBoardByUpdate() {
        return photoBoardService.getAllPhotoBoardByUpdate();
    }

    @Override
    public String deletePhotoBoard(Long photoBoardSeq) {
        return photoBoardService.deletePhotoBoard(photoBoardSeq);
    }
}
