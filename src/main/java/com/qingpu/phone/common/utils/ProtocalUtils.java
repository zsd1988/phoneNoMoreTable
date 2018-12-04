package com.qingpu.phone.common.utils;

/**
 * @Desc   实现协议使用的工具方法
 * @author Gangyahaidao
 */
public class ProtocalUtils {
	/**
	 * @Desc 替换数据特殊字节
	 * */
	public static byte[] replaceData(byte[] data) {
		byte[] t = new byte[0];
		int total = data.length;//记录转换之后的字节数组长度
		
		for(int i = 0; i < data.length-1; i++){						
			if((data[i] == (byte)0x7d) && (data[i+1] == (byte)0x02)){				
				t = BytesUtils.appendByte(t, (byte)0x7e);
				i++;				
			}else if(data[i] == (byte)0x7d && data[i+1] == (byte)0x01){				
				t = BytesUtils.appendByte(t, (byte)0x7d);
				i++;				
			}else{
				t = BytesUtils.appendByte(t, data[i]);
			}
		}
		t = BytesUtils.appendByte(t, data[total-1]);
		
		return t;	
	}
	/**
	 * @Desc 异或校验收到的数据是否正确
	 * */
	public static boolean checkMessage(byte[] data) {
		byte receivedCheck = data[data.length-2];
		
		byte checkXOR = data[1];
		
		for (int i = 2; i <data.length-2; i++) {  
			checkXOR ^=data[i];  
	    }
		if(checkXOR == receivedCheck)
			return true;
		else 
			return false;
	}
}
