package com.vmware.kafka.ui.svc;

import java.util.Collection;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.KafkaAdminClient;
import org.apache.kafka.clients.admin.ListTopicsResult;
import org.apache.kafka.clients.admin.TopicListing;
import org.springframework.stereotype.Component;
import com.vmware.kafka.ui.vo.TopicVO;

@Component
public class KafkaService {

    AdminClient adminClient;

    public KafkaService() {
        adminClient = KafkaAdminClient.create(buildDefaultClientConfig());
    }

    private Properties buildDefaultClientConfig() {
        Properties props = new Properties();
        props.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        return props;
    }
    
    public List<TopicVO> getTopics(){
        ListTopicsResult listTopics = adminClient.listTopics();
        Collection<TopicListing> topicLst;
        try {
            topicLst = listTopics.listings().get();
            return topicLst.stream().map(e -> new TopicVO(e.name())).collect(Collectors.toList());
        } catch (InterruptedException | ExecutionException e2) {
            throw new RuntimeException("Error...", e2);
        }
    }

}
 