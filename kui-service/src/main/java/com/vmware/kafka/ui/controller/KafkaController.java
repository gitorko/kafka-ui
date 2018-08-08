package com.vmware.kafka.ui.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.vmware.kafka.ui.svc.KafkaService;
import com.vmware.kafka.ui.vo.TopicVO;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api")
@Slf4j
public class KafkaController {
    
    @Autowired
    KafkaService svc;

    @RequestMapping(value = "/topics", method = RequestMethod.GET)
    public List<TopicVO> getTopics() {
        return svc.getTopics();
    }
}
