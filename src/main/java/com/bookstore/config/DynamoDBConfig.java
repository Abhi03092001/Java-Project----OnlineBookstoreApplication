package com.bookstore.config;

import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.Properties;

public class DynamoDBConfig {

    private static DynamoDbClient client;

    public static DynamoDbClient getClient() {
        if (client == null) {
            try (InputStream input = DynamoDBConfig.class.getClassLoader().getResourceAsStream("application.properties")) {
                Properties props = new Properties();
                if (input != null) {
                    props.load(input);
                }

                String endpoint = props.getProperty("dynamodb.endpoint", "http://localhost:8000");
                String region = props.getProperty("dynamodb.region", "us-east-1");
                String accessKey = props.getProperty("dynamodb.accessKey", "dummy");
                String secretKey = props.getProperty("dynamodb.secretKey", "dummy");

                client = DynamoDbClient.builder()
                        .region(Region.of(region))
                        .endpointOverride(URI.create(endpoint))
                        .credentialsProvider(StaticCredentialsProvider.create(
                                AwsBasicCredentials.create(accessKey, secretKey)))
                        .build();

                System.out.println("✅ DynamoDB Local connected successfully!");
            } catch (IOException e) {
                System.out.println("⚠️ Failed to load application.properties for DynamoDB config");
                e.printStackTrace();
            } catch (Exception e) {
                System.out.println("⚠️ DynamoDB initialization failed. Recommendations may not work.");
                e.printStackTrace();
            }
        }
        return client;
    }
}
