package com.usr.Action;

import java.io.File;
import java.io.UnsupportedEncodingException;

import org.apache.struts2.ServletActionContext;

import net.sf.json.JSONArray;

import com.usr.service.DealService;
import com.usr.service.ExecuteService;
import com.usr.service.GetResultService;
import com.usr.service.PredealService;
import com.usr.service.UploadService;

public class UploadAction {

	private String type;
	private File[] file;
	private String[] fileFileName;
	private String usertype;
	private JSONArray result;
	private String name;
	private String pass;
	public int resultType = 1;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public JSONArray getResult() {
		return result;
	}

	public void setResult(JSONArray result) {
		this.result = result;
	}

	// 提交过来的file的MIME类型
	private String fileContentType;

	public String getUsertype() {
		return usertype;
	}

	public void setUsertype(String usertype) {
		this.usertype = usertype;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public File[] getFile() {
		return file;
	}

	public void setFile(File[] file) {
		this.file = file;
	}

	public String[] getFileFileName() {
		return fileFileName;
	}

	public void setFileFileName(String[] fileFileName) {
		this.fileFileName = fileFileName;
	}

	public String getFileContentType() {
		return fileContentType;
	}

	public void setFileContentType(String fileContentType) {
		this.fileContentType = fileContentType;
	}

	public String execute() {
		// 基于myFile创建一个文件输入流
		String choo = "true";
		UploadService up = new UploadService();
		JSONArray succ = new JSONArray();
		if (usertype != null)
			choo = usertype;
		if (type.equals("train"))
			resultType = 0; // 0为训练，1为预测
		try {
			ServletActionContext.getRequest().setCharacterEncoding("UTF-8");
			String result = up.upload(file, fileFileName);
			if (result.equals("success")) {
				int tmp = new PredealService().predeal(choo, type);
				DealService de = new DealService();
				de.deal(tmp, resultType);
			}
			ExecuteService exe = new ExecuteService();
			exe.execute(type);

			if (type.equals("predict")) {
				GetResultService re = new GetResultService();
				double resu = re.GetResult();
				if (resu > 0.99)
					succ.add("super");
				else if (resu > 0.75)
					succ.add("success");
				else if (resu > 0.5)
					succ.add("half");
				else
					succ.add("failed");
				succ.add(resu);
			}else
				succ.add("training success");
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
			succ.add("fail get result");
		}
		if(pass.equals("123456789")) succ.add(pass); else succ.add(pass);
		this.setResult(succ);
		return "Success";
	}
}
