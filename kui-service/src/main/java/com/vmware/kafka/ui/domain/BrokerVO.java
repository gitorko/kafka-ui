package com.vmware.kafka.ui.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.kafka.common.Node;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BrokerVO {
  private final int id;
  private final String idString;
  private final String host;
  private final int port;
  private final String rack;

  public static BrokerVO converter(Node node){
    return new BrokerVO(node.id(),node.idString(),node.host(),node.port(),node.rack());
  }
  public static List<BrokerVO> converterLst(List<Node> nodes){
    return nodes.stream().map(e -> BrokerVO.converter(e)).collect(Collectors.toList());
  }
}