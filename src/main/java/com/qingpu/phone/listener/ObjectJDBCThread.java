package com.qingpu.phone.listener;

public class ObjectJDBCThread extends Thread{

    private ObjectJDBCService objectJDBCService;

    public ObjectJDBCThread(ObjectJDBCService objectJDBCService){
        this.objectJDBCService=objectJDBCService;
    }

    public void run(){
        objectJDBCService.initSaveOrUpdateAllObject();
    }
}
