package com.gshy.web.service.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;


public class FileUtils {

	/**
	 * 根据媒体ID获得图片对象的流
	 * 
	 * @param accessToken
	 * @param mediaId
	 * @return
	 */
	public static InputStream getInputStream(String accessToken, String mediaId) {
		InputStream is = null;
		String url = "http://file.api.weixin.qq.com/cgi-bin/media/get?access_token=" + accessToken + "&media_id="
				+ mediaId;
		try {
			URL urlGet = new URL(url);
			HttpURLConnection http = (HttpURLConnection) urlGet.openConnection();
			http.setRequestMethod("GET"); // 必须是get方式请求
			http.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			http.setDoOutput(true);
			http.setDoInput(true);
			System.setProperty("sun.net.client.defaultConnectTimeout", "30000");// 连接超时30秒
			System.setProperty("sun.net.client.defaultReadTimeout", "30000"); // 读取超时30秒
			http.connect();
			// 获取文件转化为byte流
			is = http.getInputStream();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return is;

	}

	/**
	 * 下载图片到本地磁盘
	 * 
	 * @param accessToken
	 * @param mediaId
	 * @param picName
	 * @param picPath
	 * @throws Exception
	 */
	public static void saveImageToDisk(String accessToken, String mediaId, String picName, String picPath)
			throws Exception {
		InputStream inputStream = getInputStream(accessToken, mediaId);
		byte[] data = new byte[10240];
		int len = 0;
		FileOutputStream fileOutputStream = null;
		try {
			File file = new File(picPath);
			if(!file.exists()){
				file.setWritable(true, false);
				file.mkdirs();
			}
			File pic = new File(picPath + picName + ".jpg");
			if(!pic.exists()){
				pic.setWritable(true, false);
				file.createNewFile();
			}
			
			fileOutputStream = new FileOutputStream(picPath + picName + ".jpg");
			while ((len = inputStream.read(data)) != -1) {
				fileOutputStream.write(data, 0, len);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (fileOutputStream != null) {
				try {
					fileOutputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
