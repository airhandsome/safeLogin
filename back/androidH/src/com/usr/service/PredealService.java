package com.usr.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.apache.struts2.ServletActionContext;

import com.mathworks.toolbox.javabuilder.MWClassID;
import com.mathworks.toolbox.javabuilder.MWException;
import com.mathworks.toolbox.javabuilder.MWNumericArray;
import features.Feature;

public class PredealService {

	public static String files[] = { "newgyso", "newacc", "newori" };
	public static String file1[] = { "oldgyso", "oldacc", "oldori"};
	public static String file2[] = { "gyso", "acc", "ori" };
	public static String file3[] = { "prgyso", "pracc", "prori"};
	public String filet[] = null;
	public double matric[][] = null;
	public String filename[] = null;
	public int predeal(String choo, String type){
		PredealService pre = new PredealService();
		if (type.equals("train")) {
			filename = file1; 
			filet = file2;
		}else {
			filename = files;
			filet = file3;
		}
		try {
			for (int i = 0; i < files.length; i++) {
				String filePath = ServletActionContext.getServletContext()  
		                .getRealPath("/upload");
				File file = new File(filePath, filename[i] + ".txt");
				BufferedReader bur = new BufferedReader(new FileReader(file));
				String line = null;
				while ((line = bur.readLine()) != null) {
					line = line.substring(0, line.length() - 2);
					pre.matric = pre.analyse(line);
					MWNumericArray A = new MWNumericArray(pre.matric,
							MWClassID.DOUBLE);
					Feature fea = new Feature();
					fea.tezheng(1, filePath +"/"+ filet[i] + ".txt", A);
					System.out.println("正在执行中...");
				}

				bur.close();
				System.out.println("执行结束"+filePath+"\\"+filet[i]+".txt");
			}

		} catch (FileNotFoundException e1) {
			// TODO 自动生成的 catch 块
			e1.printStackTrace();
		} catch (IOException e2) {
			// TODO 自动生成的 catch 块
			e2.printStackTrace();
		} catch (MWException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		if (choo.equals("false")) 		return -1; 
		else return 1;		
		
	}

	public double[][] analyse(String line) {

		String tmp[] = line.split("; ");
		int len = tmp.length;
		double matri[][] = new double[len][3];
		for (int j = 0; j < tmp.length; j++) {
			String matrix[] = tmp[j].split(", ");
			for (int k = 0; k < matrix.length; k++) {
				double ma = Double.parseDouble(matrix[k]);
				matri[j][k] = ma;
			}
		}
		return matri;
	}
	


}
