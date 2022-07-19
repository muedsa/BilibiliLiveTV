package com.muedsa.httpjsonclient;

public class SpecialJsonUtil{

    public static String fixDateTime(String json){
        return json.replaceAll("\"0000-00-00 00:00:00\"", "null");
    }
}
