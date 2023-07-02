package com.java.springboot.demo.common;

public enum UserType {

    R("ROUTINE"),
    TC("TESTCASE"),
    TS("TESTSTEP"),
    S("SEQUENCE");



    private String action;

    UserType(String action) {
        this.action = action;
    }

    public String getAction(){
     return this.action;
    }
}
