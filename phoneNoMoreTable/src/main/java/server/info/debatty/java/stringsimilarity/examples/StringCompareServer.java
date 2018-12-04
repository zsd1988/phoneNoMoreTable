package server.info.debatty.java.stringsimilarity.examples;

public class StringCompareServer {

	public static void main(String[] args) {
		System.out.println("string similarity init start: ");
		ServerSocketThread serverSocket = new ServerSocketThread();
		serverSocket.start();
		while(true){
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("string similarity exit");
			}			
		}		
	}

}
