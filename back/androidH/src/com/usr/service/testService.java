package com.usr.service;

import java.io.File;
import java.io.IOException;

import org.apache.struts2.ServletActionContext;
import com.lib.svm.*;
public class testService {

	public void test(){
		String resultPath ="WebRoot/check";
		
		
		File file1 = new File(resultPath, "range.txt");
		File file2 = new File(resultPath, "train.txt");
		File file3 = new File(resultPath, "L_feature_train_scale.txt");
		File file4 = new File(resultPath, "chuanganqi.txt");
		File file5 = new File(resultPath, "predict.txt");
		File file6 = new File(resultPath, "L_feature_cover.txt");
		File out = new File(resultPath, "outprint.txt");
		File show = new File(resultPath, "show.txt");
		String[] scaleArgs={"-s",file1.toString(),file2.toString()};
		String[] trainArgs={"-s", "0", "-t", "2", "-c", "100", "-b", "1", file3.toString(),file4.toString()};
		String[] scale2Args={"-r", file1.toString(), file5.toString()};
		String[] predictArgs={"-b", "1", file6.toString(),  file4.toString(), out.toString()};
		try {
			svm_scale scale =  new svm_scale();
			scale.run(scaleArgs, file3.toString());
			svm_train.main(trainArgs);
			scale.run(scale2Args, file6.toString());
			svm_predict predict = new svm_predict();
			predict.run(predictArgs,show.toString());
			
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		
		
		
		
	}
	public static void main(String[] args){
		testService te = new testService();
		te.test();
	}
}
