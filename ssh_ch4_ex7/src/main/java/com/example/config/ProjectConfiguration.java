package com.example.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(
        basePackages = {
                "com.example.proxies",
                "com.example.repositories",
                "com.example.services"}
)
public class ProjectConfiguration {
}
