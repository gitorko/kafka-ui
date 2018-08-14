package com.vmware.kafka.ui.domain;

import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateTopicVO {
    String topicName;
    int partition;
    short replication;
    Map<String, String> config;
}
