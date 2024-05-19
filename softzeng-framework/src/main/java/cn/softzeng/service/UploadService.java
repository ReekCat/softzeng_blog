package cn.softzeng.service;

import cn.softzeng.domain.ResponseResult;
import org.springframework.web.multipart.MultipartFile;

public interface UploadService {
    ResponseResult uploadImg(MultipartFile img);
}
