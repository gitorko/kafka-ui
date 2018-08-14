package com.vmware.kafka.ui.controller;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.vmware.kafka.ui.domain.BrokerVO;
import com.vmware.kafka.ui.domain.ConfigEntryVO;
import com.vmware.kafka.ui.domain.CreateTopicVO;
import com.vmware.kafka.ui.domain.PartitionVO;
import com.vmware.kafka.ui.domain.TopicVO;
import com.vmware.kafka.ui.svc.KafkaService;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api")
@Slf4j
public class KafkaController {

    @Autowired
    KafkaService svc;
    
    @PostMapping(value = "/topic")
    public void createTopic(@RequestBody CreateTopicVO topic) {
        log.info("Creating topic : {}",topic.getTopicName());
        svc.createTopic(topic.getTopicName(), topic.getPartition(), topic.getReplication(), topic.getConfig());
    }

    @GetMapping(value = "/topic")
    public List<TopicVO> getTopics() {
        return svc.getTopics();
    }

    @GetMapping(value = "/topic/{topicName}/config")
    public List<ConfigEntryVO> getTopicConfig(@PathVariable("topicName") String topicName) {
        return svc.getTopicConfig(topicName);
    }
    
    @GetMapping(value = "/topic/{topicName}/metrics/{key}")
    public Map<String,String> getTopicMetrics(@PathVariable("topicName") String topicName,@PathVariable("key") String key) {
        return svc.getTopicMetrics(topicName,key);
    }
    
    @GetMapping(value = "/topic/metrics/{key}")
    public Map<String,String> getTopicMetrics(@PathVariable("key") String key) {
        return svc.getTopicMetrics("",key);
    }


    @GetMapping(value = "/broker")
    public List<BrokerVO> getBroker() { 
        return svc.getBrokers();
    }

    @GetMapping(value = "/topic/{topicName}/partition")
    public List<PartitionVO> getTopicPartition(@PathVariable("topicName") String topicName) {
        return svc.getTopicPartition(topicName);
    }
}
