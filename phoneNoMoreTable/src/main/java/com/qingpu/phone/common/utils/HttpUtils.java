package com.qingpu.phone.common.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

/**
 * @Desc   网络数据发送post+json、get请求相关的方法
 * @author Gangyahaidao
 */
public class HttpUtils {
	/**
	 * @Desc 发送http post请求，参数为请求的json字符串
	 * */
    public static String httpPostJsonStr(String URL, String json) {

        HttpClient client = new DefaultHttpClient();
        HttpPost post = new HttpPost(URL);        
        post.setHeader("Content-Type", "application/json");
        post.addHeader("Authorization", "Basic YWRtaW46");
        String result = "";
        
        try {

            StringEntity s = new StringEntity(json, "utf-8");
            s.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE,
                    "application/json"));
            post.setEntity(s);

            // 发送请求
            HttpResponse httpResponse = client.execute(post);

            // 获取响应输入流
            InputStream inStream = httpResponse.getEntity().getContent();
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    inStream, "utf-8"));
            StringBuilder strber = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null)
                strber.append(line + "\n");
            inStream.close();

            result = strber.toString();
        } catch (Exception e) {
            System.out.println("request exception");
            throw new RuntimeException(e);
        }

        return result;
    }
    
    /**
     * @Desc 发送Http get请求，返回接收的字符串
     * */
    public static String httpGetStr(String URL){
    	HttpGet httpRequst = new HttpGet(URL);
    	String result = "";
    	
    	try {
			HttpResponse httpResponse = new DefaultHttpClient().execute(httpRequst);
			if(httpResponse.getStatusLine().getStatusCode() == 200)  
            {  
                HttpEntity httpEntity = httpResponse.getEntity();  
                result = EntityUtils.toString(httpEntity);//取出应答字符串     
                result.replaceAll("\r", "");//去掉返回结果中的"\r"字符，否则会在结果字符串后面显示一个小方格    
            }  
			else{
				httpRequst.abort();
			}                        
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return result;
    }
    
	/**
	 * 发送指定的code和message到客户端
	 * */
	public static void sendJsonStr(HttpServletResponse response, int code, String message){
		JSONObject ret = new JSONObject();
		ret.put("code", code);
		ret.put("message", message);
		response.setCharacterEncoding("utf-8");
		response.setContentType("application/json; charset=utf-8");
		try {
			PrintWriter writer = response.getWriter();			
			writer.write(ret.toString());
			writer.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
    
	/**
	 * @Desc 发送json字符串到浏览器客户端
	 * */
	public static void sendJsonStr(HttpServletResponse response, String jsonStr){
		response.setCharacterEncoding("utf-8");
		response.setContentType("application/json; charset=utf-8");
		try {
			PrintWriter writer = response.getWriter();			
			writer.write(jsonStr);
			writer.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
