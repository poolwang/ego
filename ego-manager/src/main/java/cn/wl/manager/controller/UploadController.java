package cn.wl.manager.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import cn.wl.base.vo.UploadResult;
import cn.wl.manager.service.UploadService;

@Controller
public class UploadController {

	@Autowired
	private UploadService uploadService;
	
	@RequestMapping(value="/pic/upload",method=RequestMethod.POST)
	@ResponseBody
	public UploadResult uploadResult(MultipartFile uploadFile) {
		UploadResult result=null;
		try {
			result = uploadService.upload(uploadFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}
}
