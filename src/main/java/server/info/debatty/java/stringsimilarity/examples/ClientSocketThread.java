package server.info.debatty.java.stringsimilarity.examples;

import java.io.*;
import java.net.Socket;
import java.util.Date;
import java.util.LinkedList;

import org.json.*;

import com.sun.org.apache.xalan.internal.xsltc.trax.OutputSettings;

import server.info.debatty.java.stringsimilarity.JaroWinkler;
import server.info.debatty.java.stringsimilarity.NGram;
import server.info.debatty.java.stringsimilarity.NormalizedLevenshtein;

public class ClientSocketThread extends Thread {
	private static  String BASE_PATH="/mnt/java/phone/WEB-INF/classes/server/";
	private Socket client;
	private static  String FIRST_ASK_FILE =BASE_PATH+"resources/first_ask.txt";
	private static  String SECOND_ASK_FILE = BASE_PATH+"resources/second_ask.txt";
	private static  String CUS_YES_FILE =BASE_PATH+ "resources/cus_yes.txt";
	private static  String CUS_REFUSE_FILE = BASE_PATH+"resources/cus_refuse.txt";
	private static LinkedList<String> cus_yes_list = new LinkedList<String>();
	private static LinkedList<String> cus_refuse_list = new LinkedList<String>();
	private static LinkedList<String> first_ask_list = new LinkedList<String>();
	private static LinkedList<String> second_ask_list = new LinkedList<String>();

	public ClientSocketThread(Socket client) {
		// 客户端线程构造函数
		this.client = client;
		//从文本中读取字符串添加到list中
		try {
			if(cus_yes_list != null){
				cus_yes_list = readResourceFile(CUS_YES_FILE, cus_yes_list);
			}
			if(cus_refuse_list != null){
				cus_refuse_list = readResourceFile(CUS_REFUSE_FILE, cus_refuse_list);
			}
			/*if(first_ask_list != null){
				first_ask_list = readResourceFile(FIRST_ASK_FILE, first_ask_list);
			}
			if(second_ask_list != null){
				second_ask_list = readResourceFile(SECOND_ASK_FILE, second_ask_list);
			}*/
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * 向数组中添加一个字节
	 * */
	private byte[] appendByte(byte[] a, byte b){
		int length = a.length;
		byte[] resultBytes = new byte[length+1];
		System.arraycopy(a,  0,  resultBytes, 0, length);
		resultBytes[length] = b;
		return resultBytes;
	}
	@Override
	public void run() {
		System.out.println("--连接客户端信息: ip = " + client.getInetAddress() + ", port = " + client.getPort() 
				+ ", time = " + new Date() + ", thread = " + this.getName());
		try {
			InputStream in = client.getInputStream();
			byte[] result = new byte[0];
			int tmp = -1;
			boolean header = false;
			boolean tailer = false;

			while (!this.isInterrupted()) {
				while ((tmp = in.read()) != -1) {
					byte b = (byte) tmp;
					if(b == '{'){
						if(!header){
							//收到消息开始字节
							header = true;
						}								
					}else if(b == '}'){
						//收到结束字节
						tailer = true;
					}
					result = this.appendByte(result, b);
					if(header &&  tailer){
						//如果既收到头又收到尾，则收到一条完整信息，开始处理
						header = false;
						tailer = false;
						String ret_str = handleReceivedData(result);
						//发送到客户端
						if(this.client != null && !this.client.isOutputShutdown() && ret_str != null){
							OutputStream out = client.getOutputStream();
							out.write(ret_str.getBytes("UTF8"));
							out.flush();
						}
						// 清空上次接收的数据
						result = new byte[0];
					}					
				}
			}			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("--get this.isInterrupted() signal, clear thread  = " + e.getMessage());			
		}
	}
	
	private String handleReceivedData(byte[] result) {
		//解析json数据
		//JSONObject root = new JSONObject(new String(result));
		//String source_str = root.getString("asr_result");
		String source_str = new String(result);		
		source_str = source_str.substring(1, source_str.length()-1);
		System.out.println("receive str = " + source_str);
		String ret_str = null;
		String final_str = null;
		double max = 0.0;
		if(source_str != "exit"){
			//NGram ngram = new NGram(2);
			//JaroWinkler ngram = new JaroWinkler();
			NormalizedLevenshtein ngram = new NormalizedLevenshtein();			
			double ret = 0.0, refuse_max_ret = 0.0, first_max_ret = 0.0, second_max_ret = 0.0, yes_max_ret = 0.0;
			//1.遍历客户否定问答文件				
			for (String refuse : cus_refuse_list) {				
				ret = 1-ngram.distance(refuse, source_str);
				if(ret > refuse_max_ret){
					refuse_max_ret = ret;
				}
			}
			//2.遍历客服第一句话
			/*for (String first : first_ask_list) {
				ret = 1-ngram.distance(first, source_str);
				if(ret > first_max_ret){
					first_max_ret = ret;
				}
			}*/
			//3.遍历肯定的文件
			for (String yes : cus_yes_list) {				
				ret = 1-ngram.distance(yes, source_str);
				if(ret > yes_max_ret){
					yes_max_ret = ret;
				}
			}
			//4.遍历客服第二次问答
		/*	for (String second : second_ask_list) {
				ret = 1-ngram.distance(second, source_str);
				if(ret > second_max_ret){
					second_max_ret = ret;
				}
			}*/
			System.out.println("refuse_max_ret = " + refuse_max_ret);
			System.out.println("first_max_ret = " + first_max_ret);
			System.out.println("yes_max_ret = " + yes_max_ret);
			System.out.println("second_max_ret = " + second_max_ret);
			//1.初始化--拒绝
			max = refuse_max_ret;
			ret_str = "refuse";
			//2.客服第一次问答
			/*if(first_max_ret > max){
				max = first_max_ret;
				ret_str = "first";
			}else if(first_max_ret == max){
				System.out.println("first_max_ret == max");
				ret_str = "none";//比较不出来，转人工
			}*/
			//3.客户肯定回答
			if(yes_max_ret > max){
				max = yes_max_ret;
				ret_str = "yes";
			}else if(yes_max_ret == max){
				System.out.println("yes_max_ret == max");
				ret_str = "none";
			}
			//4.客服第二次问答
		/*	if(second_max_ret > max){
				max = second_max_ret;
				ret_str = "second";
			}else if(second_max_ret == max){
				System.out.println("second_max_ret == max");
				ret_str = "none";
			}*/
			//如果最大值太小则设置为none
			if(max < 0.3){
				ret_str = "none";
			}
			final_str = "{\"ret_str\":\""+ret_str+"\",\"max\":"+max+"}";
		}else{
			//断开连接
			closeClient();
		}		
		return final_str;
	}
	
    private LinkedList<String> readResourceFile(String file, LinkedList<String> list) throws IOException {
		System.out.println("file = " + file);
        InputStream stream = new FileInputStream(file);
        //Thread.currentThread().getContextClassLoader().getResourceAsStream(file);
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        //StringBuilder string_builder = new StringBuilder();
        //String ls = System.getProperty("line.separator");
        String line = null;
        while (( line = reader.readLine() ) != null ) {        	
            list.add(line);
        }
        
        return list;
    }
	/**
	 * @Desc 关闭连接socket和销毁线程
	 * */
	public void closeClient() {
		try {
			this.interrupt();
			if (client != null) {
				if(!client.isClosed()){
					client.getInputStream().close();
				}
				if(!client.isClosed()){
					client.getOutputStream().close();
				}				
				if(!client.isClosed()){
					client.close();
				}											
			}			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("--client closed");
	}
	
	public Socket getClient() {
		return client;
	}
	public void setClient(Socket client) {
		this.client = client;
	}
}
