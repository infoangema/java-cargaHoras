package com.angema.hours.app.core.errors;

import com.google.gson.Gson;
//import lombok.ToString;

//@ToString
public class Error {

    public String status;
    public String message;
    public String path;
    public String timestamp;

    // GSON library for JSON
    @Override
    public String toString(){
        return new Gson().toJson(this);
    }
}
