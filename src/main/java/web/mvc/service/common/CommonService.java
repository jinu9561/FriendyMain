package web.mvc.service.common;

import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

public interface CommonService {

    // 파일 등록
    public Map<String,String> uploadFile(boolean mainImg, MultipartFile file, String uploadDir);
}
