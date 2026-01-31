package com.example.beans;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

@Component
public class Parrot {

    private String name;

    @PostConstruct
    void init() {
        System.out.println("Parrot initialized");
        this.name = "Koko";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
