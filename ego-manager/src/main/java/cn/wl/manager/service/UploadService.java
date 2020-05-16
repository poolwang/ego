package cn.wl.manager.service;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import cn.wl.base.vo.UploadResult;

public interface UploadService {

	UploadResult upload(MultipartFile uploadFile) throws IOException;
}
