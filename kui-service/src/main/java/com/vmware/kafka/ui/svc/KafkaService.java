package com.vmware.kafka.ui.svc;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.management.AttributeNotFoundException;
import javax.management.InstanceNotFoundException;
import javax.management.IntrospectionException;
import javax.management.MBeanAttributeInfo;
import javax.management.MBeanException;
import javax.management.MBeanInfo;
import javax.management.MBeanServerConnection;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import javax.management.ReflectionException;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.Config;
import org.apache.kafka.clients.admin.ConfigEntry;
import org.apache.kafka.clients.admin.DescribeConfigsResult;
import org.apache.kafka.clients.admin.KafkaAdminClient;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.admin.TopicDescription;
import org.apache.kafka.common.config.ConfigResource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import com.vmware.kafka.ui.domain.BrokerVO;
import com.vmware.kafka.ui.domain.ConfigEntryVO;
import com.vmware.kafka.ui.domain.PartitionVO;
import com.vmware.kafka.ui.domain.TopicVO;

@Component
@Configuration
public class KafkaService {

    AdminClient adminClient;

    @Value("${kafka.broker.url}")
    String kafkaBrokerUrl;

    @Value("${jmx.broker.url}")
    String jmxBrokerUrl;

    String jmxFormat = "service:jmx:rmi:///jndi/rmi://%s/jmxrmi";

    @PostConstruct
    public void postConstruct() {
        adminClient = KafkaAdminClient.create(buildDefaultClientConfig());
    }

    private Properties buildDefaultClientConfig() {
        Properties props = new Properties();
        props.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaBrokerUrl);
        return props;
    }

    public void createTopic(String topicName, int partition, short replication, Map<String, String> config) {
        try {
            NewTopic newTopic = new NewTopic(topicName, partition, replication);
            if (config != null && !config.isEmpty()) {
                newTopic.configs(config);
            }
            adminClient.createTopics(Collections.singleton(newTopic)).all().get();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<TopicVO> getTopics() {
        try {
            return adminClient.listTopics().listings().get().stream().map(e -> new TopicVO(e.name(), e.isInternal()))
                    .sorted().collect(Collectors.toList());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    public List<ConfigEntryVO> getTopicConfig(String topicName) {
        try {
            List<ConfigEntryVO> configLst = new ArrayList<>();
            ConfigResource resource = new ConfigResource(ConfigResource.Type.TOPIC, topicName);
            DescribeConfigsResult describeConfigsResult = adminClient.describeConfigs(Collections.singleton(resource));
            Map<ConfigResource, Config> config = describeConfigsResult.all().get();
            config.entrySet().stream().forEach((k) -> {
                Collection<ConfigEntry> entry = k.getValue().entries();
                List<ConfigEntryVO> tmpLst = entry.stream().map(e -> new ConfigEntryVO(e.name(), e.value(),
                        e.source().toString(), e.isSensitive(), e.isReadOnly())).collect(Collectors.toList());
                configLst.addAll(tmpLst);
            });
            return configLst;
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    public List<BrokerVO> getBrokers() {
        try {
            return adminClient.describeCluster().nodes().get().stream()
                    .map(e -> new BrokerVO(e.id(), e.idString(), e.host(), e.port(), e.rack()))
                    .collect(Collectors.toList());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    public List<PartitionVO> getTopicPartition(String topicName) {
        try {
            Optional<TopicDescription> topic = adminClient.describeTopics(Collections.singleton(topicName)).all().get()
                    .values().stream().filter(e -> e.name().equals(topicName)).findFirst();
            return topic.get().partitions().stream()
                    .map(e -> new PartitionVO(e.partition(), BrokerVO.converter(e.leader()),
                            BrokerVO.converterLst(e.replicas()), BrokerVO.converterLst(e.isr())))
                    .collect(Collectors.toList());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    public Map<String, String> getTopicMetrics(String topicName, String key) {
        Map<String, String> props = new HashMap<>();
        try {
            JMXServiceURL url = new JMXServiceURL(String.format(jmxFormat, jmxBrokerUrl));
            JMXConnector jmxConnector = JMXConnectorFactory.connect(url);
            MBeanServerConnection mbeanServerConnection = jmxConnector.getMBeanServerConnection();
            ObjectName mbeanName = null;
            if (topicName.isEmpty()) {
                mbeanName = new ObjectName("kafka.server:type=BrokerTopicMetrics" + ",name=" + key);
            } else {
                mbeanName = new ObjectName("kafka.server:type=BrokerTopicMetrics,topic=" + topicName + ",name=" + key);
            }
            MBeanInfo info = mbeanServerConnection.getMBeanInfo(mbeanName);
            for (MBeanAttributeInfo attr : info.getAttributes()) {
                props.put(attr.getName().toLowerCase(),
                        mbeanServerConnection.getAttribute(mbeanName, attr.getName()).toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return props;
    }

}
