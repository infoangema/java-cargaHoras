package com.angema.hours.app.core.errors;

import com.google.gson.Gson;

import java.util.List;

public class Error {

    public String status;
    public List<String> message;
    public String path;
    public String timestamp;

    @Override
    public String toString(){
        return new Gson().toJson(this);
    }
}
