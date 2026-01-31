package com.example;

import com.example.beans.Parrot;
import com.example.config.ProjectConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    static void main() {
        var context = new AnnotationConfigApplicationContext(ProjectConfig.class);

        Parrot p = context.getBean("parrot2", Parrot.class);
        System.out.println(p.getName());

    }
}
