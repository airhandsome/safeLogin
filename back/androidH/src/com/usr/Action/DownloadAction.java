package com.usr.Action;

import java.io.InputStream;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

public class DownloadAction extends ActionSupport{

	public InputStream getDownloadFile() {
		return ServletActionContext.getServletContext().getResourceAsStream(
				"upload/通讯录2012年9月4日.txt");
	}

	@Override
	public String execute() throws Exception {
		return "Success";
	}

}
