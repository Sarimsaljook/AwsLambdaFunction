package dev.sarim;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AwsLambdaTest {


    @Test
    public void shouldReturnHelloMessage() {
        var sut = new AwsLamda();
        assertEquals("Hello, AWS Lambda!", sut.handleRequest());
    }

}
