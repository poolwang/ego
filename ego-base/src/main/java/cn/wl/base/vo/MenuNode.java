package cn.wl.base.vo;

import java.util.List;

public class MenuNode {

private String u;    //目录的链接
	
	private String n;    //目录的名称
	
	private List<?> i;   //当前目录的子目录

	public String getU() {
		return u;
	}

	public void setU(String u) {
		this.u = u;
	}

	public String getN() {
		return n;
	}

	public void setN(String n) {
		this.n = n;
	}

	public List<?> getI() {
		return i;
	}

	public void setI(List<?> i) {
		this.i = i;
	}
}
