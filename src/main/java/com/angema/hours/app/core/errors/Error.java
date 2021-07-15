package com.angema.hours.app.core.errors;

import com.google.gson.Gson;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Error implements Serializable {

    public String timestamp;
    public String status;
    public String path;
    public List<String> message = new ArrayList<String>();
    //public Object object;

    @Override
    public String toString(){
        return new Gson().toJson(this);
    }
}
