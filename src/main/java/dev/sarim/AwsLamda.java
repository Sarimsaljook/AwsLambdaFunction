package dev.sarim;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;

import java.util.HashMap;
import java.util.Map;

public class AwsLamda {

    private static final String TABLE_NAME = "http-crud-items";

    private static final String ACCESS_KEY = "AKIA4MY5LTISGC7A7DL6";
    private static final String SECRET_KEY = "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";

    public String handleRequest(Map<String, Object> event) {
        String body = "";
        int statusCode = 200;
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");

        try {
            if (event.containsKey("routeKey") && event.get("routeKey").equals("GET /")) {
                String id = ((Map<String, String>) event.get("pathParameters")).get("id");

                AWSCredentialsProvider credentials = new AWSCredentialsProvider() {
                    @Override
                    public AWSCredentials getCredentials() {
                        return new BasicAWSCredentials(ACCESS_KEY, SECRET_KEY);
                    }

                    @Override
                    public void refresh() {}
                };

                AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard()
                        .withRegion(Regions.US_EAST_1)
                        .withCredentials(credentials)
                        .build();
                DynamoDB dynamoDB = new DynamoDB(client);

                // Retrieve the item from DynamoDB
                Item retrievedItem = dynamoDB.getTable(TABLE_NAME).getItem("id", id);
                if (retrievedItem != null) {
                    body = retrievedItem.toJSON();
                } else {
                    body = "No item found with id: " + id;
                }
            }
        } catch (Exception e) {
            statusCode = 400;
            body = e.getMessage();
            e.printStackTrace();
        }

        return "{\"statusCode\": " + statusCode + ", \"body\": \"" + body + "\", \"headers\": " + headers + "}";
    }
}