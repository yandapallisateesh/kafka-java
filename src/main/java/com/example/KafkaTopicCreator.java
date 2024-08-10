package com.example;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.NewTopic;

import java.util.Collections;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

public class KafkaTopicCreator {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Properties config = new Properties();
        config.put("bootstrap.servers", "localhost:9092");

        try (AdminClient adminClient = AdminClient.create(config)) {
            NewTopic newTopic = new NewTopic("quickstart-events", 1, (short) 1);
            adminClient.createTopics(Collections.singleton(newTopic)).all().get();
            System.out.println("Topic created successfully.");

            System.out.println(adminClient.listTopics());
        }


    }
}
