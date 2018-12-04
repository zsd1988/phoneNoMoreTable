package server.info.debatty.java.stringsimilarity.examples;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerSocketThread extends Thread{
	private static final int SERVERPORT = 18888;
	private ServerSocket serverSocket;

	public ServerSocketThread() {
		// 在构造函数中初始化一个ServerSocket
		try {
			if (null == serverSocket) {
				this.serverSocket = new ServerSocket(SERVERPORT);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public void run(){
		try {
			while(!this.isInterrupted()){
				//如果主socket没有被中断
				System.out.println("server socket waiting for connect on " + SERVERPORT + ":");
				Socket client = serverSocket.accept();//阻塞等待客户端的连接
				client.setTcpNoDelay(true);//立即发送数据
				client.setKeepAlive(true);//当长时间未能发送数据，服务器主动断开连接
				//创建新的客户端线程处理请求，如果请求鉴权通过就加入到在线客户端列表中，如果不通过则销毁
				ClientSocketThread client_thread = new ClientSocketThread(client);
				//启动子线程
				client_thread.start();
			}
			//关闭serversocket
			closeSocketService();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void closeSocketService() {
		// 关闭socket
		try {
			this.interrupt();
			if(null != serverSocket && !serverSocket.isClosed()){
				serverSocket.close();
			}			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
