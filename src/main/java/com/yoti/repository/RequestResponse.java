package com.yoti.repository;


import com.yoti.model.Request;
import com.yoti.model.Response;
import org.springframework.data.annotation.Id;

public class RequestResponse {


    @Id
    private String  id;

    private Request request;
    private Response response;

    public RequestResponse() {
    }

    public RequestResponse(Request request, Response response) {
        this.request = request;
        this.response = response;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Request getRequest() {
        return request;
    }

    public void setRequest(Request request) {
        this.request = request;
    }

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }
}
