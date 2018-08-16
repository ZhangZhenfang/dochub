package com.the15373.dochub.test;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

public class Start {

    public static void main(String[] args){
        ApplicationContext context =
                new ClassPathXmlApplicationContext("META-INF.spring/spring-context-dubbo.xml");
        System.out.println("Hello world! dochub-service-notice started successfully!");
        while(true){
            try {
                System.in.read();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
