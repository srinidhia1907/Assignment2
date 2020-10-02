package com.goldman.projects.myproject;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan({ "com.goldman.projects" })
public class MyprojectApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(MyprojectApplication.class, args);
		System.out.println("Program started to fetch Longest Palindromic Substring has started ");
	}

}
