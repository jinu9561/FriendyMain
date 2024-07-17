package web.mvc.service.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import web.mvc.exception.common.GlobalException;
import web.mvc.exception.common.ErrorCode;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class CommonServiceImpl implements CommonService{


    @Override
    public Map<String, String> uploadFile(boolean mainImg, MultipartFile file, String uploadDir) {
        Map<String,String> map = new HashMap<>();

        if(file.isEmpty()){
            throw new GlobalException(ErrorCode.WRONG_IMG);
        }

        String uploadPath = mainImg ? uploadDir : uploadDir + "/detail";

        File uploadFile = new File(uploadPath);
        if(!uploadFile.exists()){
            uploadFile.mkdirs();
        }

        String filename = uploadPath+"/"+file.getOriginalFilename();
        Path path = Paths.get(filename);

        try {
            Files.write(path,file.getBytes());

            // 파일의 형식과 크기를 가져오기
            String imgType = file.getContentType();
            long imgSize = file.getSize();

            // 이미지 형식 검사
            if (!imgType.equals("image/png") && !imgType.equals("image/jpeg")
                    && !imgType.equals("image/gif") && !imgType.equals("image/jpg")){
                throw new GlobalException(ErrorCode.WRONG_IMG);
            }

            map.put("imgType",imgType);
            map.put("imgSize",Long.toString(imgSize));
            map.put("imgSrc",path.toString());
            map.put("imgName",file.getOriginalFilename());

        }catch (IOException e){
            throw new GlobalException(ErrorCode.OVER_FILE);
        }

        log.info("저장된 이미지 경로 : ",path.toString());
        return map;
    }


}
