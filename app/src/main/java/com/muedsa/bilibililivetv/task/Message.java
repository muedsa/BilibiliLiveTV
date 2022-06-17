package com.muedsa.bilibililivetv.task;

public class Message {
    public MessageType what;
    public Object obj;

    private Message(){

    }

    public static Message obtain(){
        return obtain(MessageType.FAIL);
    }

    public static Message obtain(MessageType what){
        return obtain(what, null);
    }

    public static Message obtain(MessageType what, Object obj){
        Message msg = new Message();
        msg.what = what;
        msg.obj = obj;
        return msg;
    }

    public enum MessageType{
        FAIL, SUCCESS
    }
}
