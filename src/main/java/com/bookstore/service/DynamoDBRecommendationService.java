package com.bookstore.service;

import com.bookstore.model.Recommendation;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.PutItemRequest;
import software.amazon.awssdk.services.dynamodb.model.QueryRequest;
import software.amazon.awssdk.services.dynamodb.model.QueryResponse;

import java.util.*;

/**
 * Service for storing and retrieving book recommendations in DynamoDB.
 */
public class DynamoDBRecommendationService {

    private final DynamoDbClient dynamoDb;
    private static final String TABLE_NAME = "Recommendations";

    public DynamoDBRecommendationService() {
        this.dynamoDb = DynamoDbClient.create();
    }

    /**
     * Save a recommendation for a given user.
     */
    public void saveRecommendation(String username, Recommendation recommendation) {
        try {
            Map<String, AttributeValue> item = new HashMap<>();
            item.put("username", AttributeValue.builder().s(username).build());
            item.put("title", AttributeValue.builder().s(recommendation.getTitle()).build());
            item.put("score", AttributeValue.builder().n(String.valueOf(recommendation.getScore())).build());
            item.put("createdAt", AttributeValue.builder().s(new Date().toString()).build());

            PutItemRequest request = PutItemRequest.builder()
                    .tableName(TABLE_NAME)
                    .item(item)
                    .build();

            dynamoDb.putItem(request);
            System.out.println("Saved recommendation for user " + username);

        } catch (Exception e) {
            System.err.println("Error saving recommendation: " + e.getMessage());
        }
    }

    /**
     * Get the top 5 recommendations for a user.
     */
    public List<Recommendation> getTop5(String username) {
        List<Recommendation> recommendations = new ArrayList<>();
        try {
            Map<String, String> expressionNames = new HashMap<>();
            expressionNames.put("#u", "username");

            Map<String, AttributeValue> expressionValues = new HashMap<>();
            expressionValues.put(":val", AttributeValue.builder().s(username).build());

            QueryRequest request = QueryRequest.builder()
                    .tableName(TABLE_NAME)
                    .keyConditionExpression("#u = :val")
                    .expressionAttributeNames(expressionNames)
                    .expressionAttributeValues(expressionValues)
                    .scanIndexForward(false)
                    .limit(5)
                    .build();

            QueryResponse response = dynamoDb.query(request);
            response.items().forEach(item -> {
                String title = item.get("title").s();
                double score = Double.parseDouble(item.get("score").n());
                recommendations.add(new Recommendation(title, score));
            });

        } catch (Exception e) {
            System.err.println("Error fetching recommendations: " + e.getMessage());
        }
        return recommendations;
    }
}
