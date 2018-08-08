package com.vmware.kafka.ui;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ExecutionException;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.DescribeClusterResult;
import org.apache.kafka.clients.admin.DescribeTopicsResult;
import org.apache.kafka.clients.admin.KafkaAdminClient;
import org.apache.kafka.clients.admin.ListTopicsResult;
import org.apache.kafka.clients.admin.TopicDescription;
import org.apache.kafka.clients.admin.TopicListing;
import org.apache.kafka.common.Node;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

@SpringBootApplication
public class Main {

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @Component
    class MainRunner implements ApplicationRunner {

        @Override
        public void run(ApplicationArguments args) throws Exception {
            AdminClient adminClient = KafkaAdminClient.create(buildDefaultClientConfig());
            DescribeClusterResult describeCluster = adminClient.describeCluster();
            Collection<Node> clusterNodes = describeCluster.nodes().get();
            clusterNodes.forEach(System.out::println);
            
            System.out.println("-------------------------");
            ListTopicsResult listTopics = adminClient.listTopics();
            Collection<TopicListing> topicLst = listTopics.listings().get();
            topicLst.forEach(e -> {
                DescribeTopicsResult describeTopicsResult = adminClient.describeTopics(Collections.singleton(e.name()));
                try {
                    Map<String, TopicDescription> topicMap = describeTopicsResult.all().get();
                    topicMap.entrySet().forEach(v -> {
                        System.out.println("Key: " + v.getKey() + " : "+ topicMap.get(v.getKey()));
                    });
                    
                   
                } catch (InterruptedException | ExecutionException e1) {
                    throw new RuntimeException("Error...", e1);

                }
                System.out.println();
            });
        }

        private Properties buildDefaultClientConfig() {
            Properties props = new Properties();
            props.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
            return props;
        }

    }
}
