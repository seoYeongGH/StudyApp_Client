package com.example.study;

public class Session {
    private static Session instance;
    private static String id;

    public static Session getInstance() {
        if(instance == null) instance = new Session();
        return instance;
    }

    protected void setId(String id){
        this.id = id;
    }

    protected String getId(){
        return id;
    }

    private Session() { }
}
