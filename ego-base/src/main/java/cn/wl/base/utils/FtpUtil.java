package cn.wl.base.utils;

import java.io.IOException;

import java.io.InputStream;
import java.net.SocketException;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

public class FtpUtil {

	public static boolean upload(String hostName, int port, String username, String password, 
			String basepath, String filepath, String remote, InputStream in)  {
		FTPClient client=new FTPClient();
		try {
			client.connect(hostName, port);
			client.login(username, password);
			client.setFileType(FTP.BINARY_FILE_TYPE);
			client.enterLocalPassiveMode(); //被动模式
			boolean flag = client.changeWorkingDirectory(basepath+filepath);
			//如果不存在该路径，则循环拼接然后新增
			if(!flag) {
				String[] split = filepath.split("/");
				String tmppath=basepath;
				for (String string : split) {
					tmppath=tmppath+"/"+string;
					client.makeDirectory(tmppath);
				}
				client.changeWorkingDirectory(tmppath);
			}
			client.storeFile(remote, in);
			client.logout();
			client.disconnect();
			return true;
			
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		 return false;
		
	}
}
