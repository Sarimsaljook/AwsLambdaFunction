package dev.sarim;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class AwsLambdaTest {

    @Test
    public void shouldReturnDynamoData() {
        var sut = new AwsLamda();

        String testID = "70";

        // Create a sample event
        Map<String, Object> event = new HashMap<>();
        Map<String, String> pathParameters = new HashMap<>();
        pathParameters.put("id", testID);
        event.put("routeKey", "POST /");
        event.put("pathParameters", pathParameters);

        // Invoke the Lambda function
        String result = sut.handleRequest(event);

        // Assert the result
        assertEquals("{\"statusCode\": 200, \"body\": \"{\"id\":\""+testID+"\"}\", \"headers\": {Content-Type=application/json}}", result);
        System.out.println(result);

        if(result.equals("{\"statusCode\": 200, \"body\": \"{\"id\":\""+testID+"\"}\", \"headers\": {Content-Type=application/json}}")) {
            System.out.println("Test Case 1 Passed!");
        }
    }
}
