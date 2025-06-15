package com.gcp.agent25.cases.management.service.enums;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static com.gcp.agent25.cases.management.service.enums.TicketStatusEnum.*;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class TicketStatusEnumTest {
    static Stream<Arguments> test_ticketStatusEnum() {
        return Stream.of(
                Arguments.of(OPEN, OPEN.toString()),
                Arguments.of(IN_PROGRESS, IN_PROGRESS.toString()),
                Arguments.of(RESOLVED, RESOLVED.toString())
        );
    }

    @ParameterizedTest
    @MethodSource("test_ticketStatusEnum")
    void test_ticketStatusEnum(TicketStatusEnum expectedValue, String actualValue) {
        assertEquals(expectedValue.getValue(), actualValue);
    }
}
