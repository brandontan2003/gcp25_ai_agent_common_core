package com.gcp.agent25.common.core;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import static com.gcp.agent25.common.core.constant.CommonConstant.BASE_PACKAGE;

@SpringBootApplication
@ComponentScan(BASE_PACKAGE)
public class Application {
    public static void main(String[] args) {
    }
}