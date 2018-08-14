package com.vmware.kafka.ui.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ConfigEntryVO {

    private final String name;
    private final String value;
    private final String source;
    private final boolean isSensitive;
    private final boolean isReadOnly;
}
