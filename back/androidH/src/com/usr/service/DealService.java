package com.usr.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.struts2.ServletActionContext;
public class DealService {

	public int deal(int tmp, int use){
		int result = 0;
		String[] name2 = { "acc.txt", "ori.txt", "gyso.txt" };
		String[] name1 = {"pracc.txt", "prori.txt", "prgyso.txt"};
		String[] name = null;
		String loc = "true";
		String nn = "predict.txt";
		if (tmp == -1) loc = "false";
		if (use == 0) {
			nn = "train.txt";
			name = name2;
		}else{
			nn = "predict.txt";
			name = name1;
		}
		System.out.println("存放的路径为"+nn);
		try {
			String encoding = "UTF-8";
			
			String path = ServletActionContext.getServletContext()  
		                .getRealPath("/check");
			File out = new File(path,nn);

			if (!out.exists()) {
				out.createNewFile();
			}

			BufferedWriter writer = new BufferedWriter(
					new FileWriter(out, true));
			
			String resultPath = ServletActionContext.getServletContext()  
		                .getRealPath("/upload");  
			InputStreamReader acc = new InputStreamReader(new FileInputStream(new File(resultPath, name[0])), encoding);
			InputStreamReader gyso = new InputStreamReader(new FileInputStream(new File(resultPath, name[1])),encoding);
			InputStreamReader ori = new InputStreamReader(new FileInputStream(new File(resultPath, name[2])),encoding);
			
			BufferedReader Acc = new BufferedReader(acc);
			BufferedReader Gyso = new BufferedReader(gyso);
			BufferedReader Ori = new BufferedReader(ori);
			String accTxt, gysoTxt, oriTxt;
			while ((accTxt = Acc.readLine()) != null && (gysoTxt = Gyso.readLine())!=null && (oriTxt = Ori.readLine())!=null) {
				int count = 0;
				writer.write(tmp+":  ");
				for(int i = 0; i<3; i++){
						String[] line = null;
						if (i==0)   line = accTxt.split("  ");
						else if(i == 1) line = gysoTxt.split("  ");
						else line = oriTxt.split("  ");
						for (int j = 0; j < line.length; j++) {
							++count;
							writer.write(count + ":" + line[j] + " ");
						}
					}
				writer.write("\r\n");
			}
			Acc.close(); 
			Gyso.close();
			Ori.close();
			writer.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
}
