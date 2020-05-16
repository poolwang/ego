package cn.wl.base.vo;

public class UploadResult {

private int error;   //0 表示成功   1表示失败
	
	private String url;   //成功时，图片的访问地址
	 
	private String message;  //失败时，错误信息

	public int getError() {
		return error;
	}

	public void setError(int error) {
		this.error = error;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
