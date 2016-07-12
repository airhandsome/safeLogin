package com.usr.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.struts2.ServletActionContext;

public class UploadService {

	public String upload(File[] file, String[] fileFileName) throws Exception{
		String result = "success";
		int len = file.length;
		ServletActionContext.getRequest().setCharacterEncoding("UTF-8");
		for(int i = 0; i<len; i++){
			System.out.println("下列文件是"+file[i]);
			System.out.println("名字是"+fileFileName[i]);
		File fi = file[i];
        InputStream is = new FileInputStream(fi);  
          
        // 设置上传文件目录  
        String uploadPath = ServletActionContext.getServletContext()  
                .getRealPath("/upload");  
        File fold = new File(uploadPath);
        if(!fold.exists() && fold.isDirectory())
        	 fold.mkdir();    
        // 设置目标文件  
        File toFile = new File(uploadPath, fileFileName[i]);  
        int k = 0;
        while(toFile.exists()){
        	k++;
        	toFile = new File(uploadPath, fileFileName[i]+"("+k+").txt");
        }
        	
        
        if(!toFile.exists()){
        	toFile.createNewFile();
        }
        // 创建一个输出流  
        OutputStream os = new FileOutputStream(toFile);  
  
        //设置缓存  
        byte[] buffer = new byte[1024];  
  
        int length = 0;  
  
        //读取myFile文件输出到toFile文件中  
        while ((length = is.read(buffer)) > 0) {  
            os.write(buffer, 0, length);  
        }  
        //关闭输入流  
        is.close();  
          
        //关闭输出流  
        os.close();  
		}  
		return result;
	}
}
