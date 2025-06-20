package com.gcp.agent25.cases.management.service.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

import java.util.Arrays;

@Getter
public enum TicketStatusEnum {
    OPEN("OPEN"),
    IN_PROGRESS("IN_PROGRESS"),
    RESOLVED("RESOLVED");

    private final String value;

    TicketStatusEnum(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    @JsonCreator
    public static TicketStatusEnum fromValue(String input) {
        for (TicketStatusEnum e : values()) {
            if (e.value.equalsIgnoreCase(input)) {
                return e;
            }
        }
        throw new IllegalArgumentException(
                String.format("Invalid priority: '%s'. Accepted values: %s",
                        input,
                        Arrays.toString(Arrays.stream(TicketStatusEnum.values()).map(TicketStatusEnum::getValue).toArray())
                )
        );
    }

}
