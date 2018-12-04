package com.qingpu.phone.systemManager.phoneSocket;

import com.qingpu.phone.common.utils.BytesUtils;
import org.apache.log4j.Logger;

import java.io.*;

public class TempThread extends Thread {
    private static Logger logger = Logger.getLogger(TempThread.class);

    private TempSocket socket;
    public boolean isRun = true;
    public InputStream inputStream;

    public TempThread(TempSocket socket) {
        this.socket = socket;
    }

    public void run() {
        try {
            inputStream = socket.getInputStream();
            byte[] result = new byte[0];
            int tmp = -1;
            int header = 0;
            int end = 0;
            while ( isRun  && (tmp = inputStream.read()) != -1  ){
                try{
//                        消息体 以@@开头 以&&结尾
//                        logger.info("tmp: "  + tmp);
                    byte b = (byte) tmp;
//                        System.out.print(b);
                    char c = (char) (b);
//                       System.out.print(c);
                    if(b == CallPhoneListener._headByte){
                        if(header < 2){
                            header++;
                        }
                    }else if(header == 1){
                        header = 0;
                    }
                    if(header == 2){
                        if(b == CallPhoneListener._endByte){
                            if(end < 2){
                                end++;
                            }
                        }else if(end == 1){
                            end = 0;
                        }
                    }
                    result = BytesUtils.appendByte(result, b);
                    if(header == 2 && end == 2){
                        //如果既收到头又收到尾，则收到一条完整信息，开始处理
                        header = 0;
                        end = 0;
                        String data = new String(result, "utf-8");
//                            logger.info("收到数据长度：" + data.length());
                        data = data.substring(2, data.length() - 2);
                        socket.receiveMessage(data);
                        // 清空上次接收的数据
                        result = new byte[0];
                    }
                }catch (Exception e){
                    logger.info(" 解析数据失败：" + e.getMessage());
                    result = new byte[0];
                    socket.closeSocket();
                }
            }
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }
}
