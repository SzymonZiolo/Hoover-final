package com.yoti;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.yoti.model.Request;
import com.yoti.model.Response;

import java.io.IOException;

public class TestUtilis {

    private static ObjectMapper mapper = new ObjectMapper();

    public static Response rsFromString(String s) {
        try {
            return mapper.readValue(s, Response.class);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage(), e);
        }
    }


    public static Request rqFromString(String s) {
        try {
            return mapper.readValue(s, Request.class);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage(), e);
        }
    }
}
