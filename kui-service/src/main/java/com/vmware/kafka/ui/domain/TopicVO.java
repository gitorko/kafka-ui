package com.vmware.kafka.ui.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TopicVO implements Comparable<TopicVO> {

  private final String name;
  private final boolean internal;

  @Override
  public int compareTo(TopicVO o) {
    return this.name.compareTo(o.name);
  }
}