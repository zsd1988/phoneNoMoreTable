package com.qingpu.phone.common.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;

/**
 * 通过URL地址创建HTML文件，可将动态页面转换成静态页面
 * 
 * @author bianj
 * @version 1.0.0 2014-5-5
 */
public class HttpRequestToHtml {
	/** GBK编码 */
	public static final String GBK_ENCODING = "GBK";

	/** UTF-8编码 */
	public static final String UTF8_ENCODING = "UTF-8";

	/**
	 * 通过URL地址创建HTML文件，字符编码默认为UTF-8
	 * 
	 * @param url
	 *            URL地址
	 * @param filePath
	 *            要保存的文件目录+文件名.html
	 */
	public static void buildHTML(String url, String filePath) {
		buildHTML(url, filePath, UTF8_ENCODING);
	}

	/**
	 * 通过URL地址创建HTML文件
	 * 
	 * @param urlPath
	 *            URL地址
	 * @param filePath
	 *            要保存的文件目录+文件名.html
	 * @param encoding
	 *            字符编码
	 */
	public static void buildHTML(String urlPath, String filePath,
			String encoding) {
		URL url = null;
		HttpURLConnection httpURLConnection;
		try {
			// 向服务器发送get请求
			url = new URL(urlPath);
			httpURLConnection = (HttpURLConnection) url.openConnection();
			InputStream is = httpURLConnection.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(is, encoding));

			StringBuilder sb = new StringBuilder();
			String readLine = null;
			while ((readLine = br.readLine()) != null) {
				sb.append(readLine + "\n");
			}
			// System.out.println(sb);
			stringToFile(sb.toString(), filePath, encoding);

			is.close();
			br.close();
			httpURLConnection.disconnect();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 将字符串写入指定文件
	 * 
	 * @param str
	 *            原字符串
	 * @param filePath
	 *            文件路径
	 * @param encoding
	 *            字符编码
	 * @return 成功标记
	 */
	public static boolean stringToFile(String str, String filePath,
			String encoding) {
		boolean flag = true;

		File file = new File(filePath);
		if (!file.getParentFile().exists()) {
			file.getParentFile().mkdirs();
		}
		try {
			FileUtils.writeStringToFile(file, str, encoding);
		} catch (IOException e) {
			flag = false;
			e.printStackTrace();
		}

		return flag;
	}
	
	/**
	 * 获得ip
	 * @param request
	 * @return
	 */
	public static String getIpAddress(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("http_client_ip");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		}
		// 如果是多级代理，那么取第一个ip为客户ip
		if (ip != null && ip.indexOf(",") != -1) {
			ip = ip.substring(ip.lastIndexOf(",") + 1, ip.length()).trim();
		}
		return ip;
	}

	/**
	 * 测试
	 */
	public static void main(String[] args) {
		buildHTML("http://www.163.com", "c:/temp/163.html", GBK_ENCODING);
	}

}
