package com.yoti.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.yoti.calculator.HooverCalculator;
import com.yoti.model.Request;
import com.yoti.model.Response;
import com.yoti.repository.HooverRepository;
import com.yoti.repository.RequestResponse;

@RestController
public class HooverController {

    @Autowired
    private HooverCalculator hooverCalculator;

    @Autowired
    private HooverRepository hooverRepository;

    @RequestMapping(value = "/", method = RequestMethod.POST)
    @ResponseBody
    public Response calculate(@Valid @RequestBody Request request) {
        Response response = hooverCalculator.calculate(request);
        RequestResponse requestResponse = new RequestResponse(request, response);
        hooverRepository.save(requestResponse);
        return response;
    }
}
