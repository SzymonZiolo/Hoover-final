package com.yoti.calculator;

import com.yoti.model.Request;
import com.yoti.model.Response;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static com.yoti.TestUtilis.rqFromString;
import static com.yoti.TestUtilis.rsFromString;


public class HooverCalculatorTest {

    private HooverCalculator hooverCalculator;

    @Before
    public void setUp() {
        hooverCalculator = new BasicHooverCalculator();
    }


    @Test
    public void testExampleScenario() {
        //given
        Request request = rqFromString("{" +
                "  \"roomSize\" : [5, 5]," +
                "  \"coords\" : [1, 2]," +
                "  \"patches\" : [" +
                "    [1, 0]," +
                "    [2, 2]," +
                "    [2, 3]" +
                "  ]," +
                "  \"instructions\" : \"NNESEESWNWW\"" +
                "}");
        Response expectedResponse = rsFromString("{" +
                "  \"coords\" : [1, 3]," +
                "  \"patches\" : 1" +
                "}");
        //when
        Response response = hooverCalculator.calculate(request);


        //then
        testResponse(expectedResponse, response);
    }


    @Test
    public void testDrivingIntoWall() {
        //given
        Request request = rqFromString("{" +
                "  \"roomSize\" : [3, 3]," +
                "  \"coords\" : [0, 0]," +
                "  \"patches\" : [" +
                "    [0, 0]" +
                "  ]," +
                "  \"instructions\" : \"EEEENNNNWWWWSSSS\"" +
                "}");
        Response expectedResponse = rsFromString("{" +
                "  \"coords\" : [0, 0]," +
                "  \"patches\" : 1" +
                "}");
        //when
        Response response = hooverCalculator.calculate(request);


        //then
        testResponse(expectedResponse, response);
    }


    @Test
    public void testHugeRoom() {
        //given
        Request request = rqFromString("{" +
                "  \"roomSize\" : [10000, 10000]," +
                "  \"coords\" : [0, 0]," +
                "  \"patches\" : [" +
                "    [1, 1]" +
                "  ]," +
                "  \"instructions\" : \"NE\"" +
                "}");
        Response expectedResponse = rsFromString("{" +
                "  \"coords\" : [1, 1]," +
                "  \"patches\" : 1" +
                "}");
        //when
        Response response = hooverCalculator.calculate(request);


        //then
        testResponse(expectedResponse, response);
    }

    @Test
    public void testNoInstruction() {
        //given
        Request request = rqFromString("{" +
                "  \"roomSize\" : [3, 7]," +
                "  \"coords\" : [1, 5]," +
                "  \"patches\" : [" +
                "    [1, 0]" +
                "  ]," +
                "  \"instructions\" : \"\"" +
                "}");
        Response expectedResponse = rsFromString("{" +
                "  \"coords\" : [1, 5]," +
                "  \"patches\" : 0" +
                "}");
        //when
        Response response = hooverCalculator.calculate(request);


        //then
        testResponse(expectedResponse, response);
    }


    @Test
    public void testLongInstruction() {
        //given
        Request request = rqFromString("{" +
                "  \"roomSize\" : [33, 22]," +
                "  \"coords\" : [1, 5]," +
                "  \"patches\" : [" +
                "    [1, 0]" +
                "  ]," +
                "  \"instructions\" : \"NNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEE\"" +
                "}");
        Response expectedResponse = rsFromString("{" +
                "  \"coords\" : [32, 21]," +
                "  \"patches\" : 0" +
                "}");
        //when
        Response response = hooverCalculator.calculate(request);


        //then
        testResponse(expectedResponse, response);
    }


    @Test
    public void testNoPatches() {
        //given
        Request request = rqFromString("{" +
                "  \"roomSize\" : [3, 3]," +
                "  \"coords\" : [1, 1]," +
                "  \"patches\" : [" +
                "" +
                "  ]," +
                "  \"instructions\" : \"SW\"" +
                "}");
        Response expectedResponse = rsFromString("{" +
                "  \"coords\" : [0, 0]," +
                "  \"patches\" : 0" +
                "}");
        //when
        Response response = hooverCalculator.calculate(request);


        //then
        testResponse(expectedResponse, response);
    }

    @Test
    public void testAvoidDoubleCount() {
        //given
        Request request = rqFromString("{" +
                "  \"roomSize\" : [2, 2]," +
                "  \"coords\" : [0, 0]," +
                "  \"patches\" : [" +
                "[0,0]," +
                "[0,1]," +
                "[1,0]," +
                "[1,1]" +
                "  ]," +
                "  \"instructions\" : \"NESWNESW\"" +
                "}");
        Response expectedResponse = rsFromString("{" +
                "  \"coords\" : [0, 0]," +
                "  \"patches\" : 4" +
                "}");
        //when
        Response response = hooverCalculator.calculate(request);


        //then
        testResponse(expectedResponse, response);
    }

    private void testResponse(Response expected, Response acutal) {
        Assert.assertEquals(expected.getCoords(), acutal.getCoords());
        Assert.assertEquals(expected.getPatches(), acutal.getPatches());
    }


}