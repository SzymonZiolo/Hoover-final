package com.yoti.controller;


import com.yoti.HooverApplication;
import com.yoti.model.Request;
import com.yoti.model.Response;
import com.yoti.repository.HooverRepository;
import com.yoti.repository.RequestResponse;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.client.RestTemplate;

import java.net.URL;
import java.util.List;

import static com.yoti.TestUtilis.rqFromString;
import static com.yoti.TestUtilis.rsFromString;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = HooverApplication.class)
@WebAppConfiguration
@IntegrationTest({"server.port=0"})
public class HooverControllerIT {

    @Value("${local.server.port}")
    private int port;

    @Autowired
    private HooverRepository hooverRepository;

    private URL base;
    private RestTemplate template;
    private String sampleRequest;
    private String expectedResponse;

    @Before
    public void setUp() throws Exception {
        this.base = new URL("http://localhost:" + port + "/");
        template = new TestRestTemplate();
        sampleRequest = "{" +
                "  \"roomSize\" : [5, 5]," +
                "  \"coords\" : [1, 2]," +
                "  \"patches\" : [" +
                "    [1, 0]," +
                "    [2, 2]," +
                "    [2, 3]" +
                "  ]," +
                "  \"instructions\" : \"NNESEESWNWW\"" +
                "}";
        expectedResponse = "{\"coords\":[1,3],\"patches\":1}";
    }


    @Test
    public void testSampleScenario() {
        //given
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(sampleRequest, headers);

        //when
        Assert.assertEquals(0, hooverRepository.count());
        ResponseEntity<String> response = template.postForEntity(base.toString(), entity, String.class);

        //then
        Assert.assertEquals(expectedResponse, response.getBody());
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assert.assertEquals("application/json;charset=UTF-8", response.getHeaders().getFirst(HttpHeaders.CONTENT_TYPE));
        testRequestResponseHasBeenPersisted();
    }

    @Test
    public void testInvalidRequest() {
        //given
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>("{\n" +
                "  \"roomSize\" : [5],\n" +
                "  \"coords\" : [ 2],\n" +
                "  \"patches\" : [\n" +
                "  ],\n" +
                "  \"instructions\" : \"BLE\"\n" +
                "}", headers);

        //when
        ResponseEntity<String> response = template.postForEntity(base.toString(), entity, String.class);

        //then
        Assert.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        Assert.assertEquals("application/json;charset=UTF-8", response.getHeaders().getFirst(HttpHeaders.CONTENT_TYPE));
    }

    private void testRequestResponseHasBeenPersisted() {
        List<RequestResponse> allSaved = hooverRepository.findAll();
        Assert.assertEquals(1, allSaved.size());

        Request expectedRq = rqFromString(sampleRequest);
        Response expectedRs = rsFromString(expectedResponse);

        Request actualRq = allSaved.get(0).getRequest();
        Response actualRs = allSaved.get(0).getResponse();

        Assert.assertEquals(expectedRq.getPatches(), actualRq.getPatches());
        Assert.assertEquals(expectedRq.getCoords(), actualRq.getCoords());
        Assert.assertEquals(expectedRq.getRoomSize(), actualRq.getRoomSize());
        Assert.assertEquals(expectedRq.getInstructions(), actualRq.getInstructions());
        Assert.assertEquals(expectedRs.getPatches(), actualRs.getPatches());
        Assert.assertEquals(expectedRs.getCoords(), actualRs.getCoords());
    }


}
