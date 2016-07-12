package com.usr.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.struts2.ServletActionContext;

public class GetResultService {

	public double GetResult() {
		double result = 0.0;
		String resultPath = ServletActionContext.getServletContext()  
                .getRealPath("/check");
		File show = new File(resultPath, "show.txt");
		System.out.println("开始输出文件");
		System.out.println(show);
		if (!show.exists())   return -1;
		else{
			try {
		
				BufferedReader preRead = new BufferedReader(
						new InputStreamReader(new FileInputStream(show)));

				String line = null;
				while ((line = preRead.readLine()) != null){
					System.out.println(line);
					String substring = line.substring(0, 8);
					System.out.println("substring="+substring);
					if (substring.equals("Accuracy")){
						int left = line.indexOf("(");
						int min = line.indexOf("/");
						int right = line.indexOf(")");
						double be = Double.valueOf(line.substring(left+1,min));
						double chu = Double.valueOf(line.substring(min+1, right));
						result = be/chu;
					}
				}
				preRead.close();
			} catch (IOException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
		}	
		return result;
	}
}
