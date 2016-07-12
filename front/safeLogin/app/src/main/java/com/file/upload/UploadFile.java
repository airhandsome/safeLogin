package com.file.upload;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
/**
 * Created by airhandsome on 2016/4/13.
 */
public class UploadFile {
	public String send(String url, String[] filePath, String name, String pass, String type, String usertype)
			throws IOException {
		String result = "";
		/**
		 * 第一部分
		 */
		URL urlObj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) urlObj.openConnection();
		con.setRequestMethod("POST"); // 以Post方式提交表单，默认get方式
		con.setDoInput(true);
		con.setDoOutput(true);
		con.setUseCaches(false); // post方式不能使用缓存

		// 设置请求头信息
		con.setRequestProperty("Connection", "Keep-Alive");
		con.setRequestProperty("Charset", "UTF-8");

		// 设置边界
		String BOUNDARY = "----------" + System.currentTimeMillis();
		con.setRequestProperty("Content-Type", "multipart/form-data; boundary="
				+ BOUNDARY);

		// 加入name
		StringBuilder sb = new StringBuilder();
		sb.append("--");
		sb.append(BOUNDARY);
		sb.append("\r\n");
		sb.append("Content-Disposition: form-data;name=\"name\" " + "\r\n\r\n");
		sb.append(name + "\r\n");

		// 加入password
		sb.append("--");
		sb.append(BOUNDARY);
		sb.append("\r\n");
		sb.append("Content-Disposition: form-data;name=\"pass\" " + "\r\n\r\n");
		sb.append(pass + "\r\n");

		sb.append("--");
		sb.append(BOUNDARY);
		sb.append("\r\n");
		sb.append("Content-Disposition: form-data;name=\"type\" " + "\r\n\r\n");
		sb.append(type + "\r\n");

		sb.append("--");
		sb.append(BOUNDARY);
		sb.append("\r\n");
		sb.append("Content-Disposition: form-data;name=\"usertype\" " + "\r\n\r\n");
		sb.append(usertype + "\r\n");
		byte[] head = sb.toString().getBytes("utf-8");
		System.out.println(sb);
		OutputStream out = new DataOutputStream(con.getOutputStream());
		out.write(head);

		for (int i = 0; i < filePath.length; i++) {
			File file = new File(filePath[i]);
			if (!file.exists() || !file.isFile()) {
				return "-1";
			}
			StringBuilder newsb = new StringBuilder();
			newsb.append("\r\n--"); // ////////必须多两道线
			newsb.append(BOUNDARY);
			newsb.append("\r\n");
			newsb.append("Content-Disposition: form-data;name=\"file\";filename=\""
					+ file.getName() + "\"\r\n");
			newsb.append("Content-Type:application/octet-stream\r\n\r\n");
			head = newsb.toString().getBytes("utf-8");
			out.write(head);
			System.out.println(newsb);
			DataInputStream in = new DataInputStream(new FileInputStream(file));
			int bytes = 0;
			byte[] bufferOut = new byte[1024];
			while ((bytes = in.read(bufferOut)) != -1) {
				out.write(bufferOut, 0, bytes);
			}

			in.close();

		}
		byte[] foot = ("\r\n--" + BOUNDARY + "--\r\n").getBytes("utf-8");// 定义最后数据分隔线
		out.write(foot);
		System.out.println(foot);
		out.flush();
		out.close();

		/**
		 * 读取服务器响应，必须读取,否则提交不成功
		 */
		System.out.println(con.getResponseMessage());

		/**
		 * 下面的方式读取也是可以的
		 */
		String line = null;
		try {
			// 定义BufferedReader输入流来读取URL的响应
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					con.getInputStream()));

			int i=0;

			while ((line = reader.readLine()) != null) {
				System.out.println("我是line "+i+" "+line);
				result = result+" "+line;
				i++;
			}
		} catch (Exception e) {
			System.out.println("发送POST请求出现异常！" + e);
			e.printStackTrace();
		}

		return result;
	}
//	public static void main(String[] args){
//		tests te = new tests();
//		String[] road = { "src/false/acc.txt", "src/false/gyso.txt",
//				"src/false/ori.txt" };
//		String type = "train";
//		String usertype = "true";
//		try {
//			System.out.println("我是返回结果"+te.send("http://localhost:8080/androidH/fileUpload.action", road,
//					"hand", "123", type, usertype));
//		} catch (IOException e) {
//			// TODO 自动生成的 catch 块
//			e.printStackTrace();
//		}
//
//	}
}
