package com.gcp.agent25.cases.management.service.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

import java.util.Arrays;

@Getter
public enum PriorityEnum {
    CRITICAL("CRITICAL"),
    HIGH("HIGH"),
    MEDIUM("MEDIUM"),
    LOW("LOW");

    private final String value;

    PriorityEnum(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    @JsonCreator
    public static PriorityEnum fromValue(String input) {
        for (PriorityEnum e : values()) {
            if (e.value.equalsIgnoreCase(input)) {
                return e;
            }
        }
        throw new IllegalArgumentException(
                String.format("Invalid priority: '%s'. Accepted values: %s",
                        input,
                        Arrays.toString(Arrays.stream(PriorityEnum.values()).map(PriorityEnum::getValue).toArray())
                )
        );
    }

}
