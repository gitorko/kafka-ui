package com.vmware.kafka.ui.vo;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BrokerVO {

    private int id;
    private String host;
    private int port;
    private int jmxPort;
    private int version;
    private boolean controller;
    private Date timestamp;
}
