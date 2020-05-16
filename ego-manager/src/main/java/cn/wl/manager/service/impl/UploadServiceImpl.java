package cn.wl.manager.service.impl;

import java.io.IOException;
import java.util.Calendar;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import cn.wl.base.utils.FtpUtil;
import cn.wl.base.utils.IDUtils;
import cn.wl.base.vo.UploadResult;
import cn.wl.manager.service.UploadService;

@Service
public class UploadServiceImpl implements UploadService {

	@Value("${ftp.hostname}")
	private String hostName;
	@Value("${ftp.port}")
	private int port;
	@Value("${ftp.username}")
	private String username;
	@Value("${ftp.password}")
	private String password;
	@Value("${ftp.basePath}")
	private String basepath;
	@Value("${pic.baseURL}")
	private String baseURL; 
	
	@Override
	public UploadResult upload(MultipartFile uploadFile) throws IOException {
		UploadResult result=new UploadResult();
		
		//构建filepath
		Calendar calendar=Calendar.getInstance();
		String filepath="/"+calendar.get(Calendar.YEAR) //获取当前年
                        +"/"+String.format("%02d", calendar.get(Calendar.MONTH)+1) //获取当前月
                        +"/"+String.format("%02d", calendar.get(Calendar.DAY_OF_MONTH))+"/"; //获取当前日
	
		//图片原来的名称和图片类型
		String originalFilename = uploadFile.getOriginalFilename();
		String type = originalFilename.substring(originalFilename.indexOf("."));
	    //构建图片上传之后的名字
		String remote=IDUtils.genImageName()+type;
		
		boolean upload = FtpUtil.upload(hostName, port, username, password, basepath, filepath, remote, uploadFile.getInputStream());
		    if (!upload) {
			  		result.setError(1);
			  		result.setMessage("上传失败");
		   }else {
			result.setError(0);
			result.setUrl(baseURL+filepath+remote);
		}
		return result;
	}

}
