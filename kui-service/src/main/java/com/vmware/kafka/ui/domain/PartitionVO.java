package com.vmware.kafka.ui.domain;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PartitionVO{
  private final int partition;
  private final BrokerVO leader;
  private final List<BrokerVO> replicas;
  private final List<BrokerVO> isr;

}