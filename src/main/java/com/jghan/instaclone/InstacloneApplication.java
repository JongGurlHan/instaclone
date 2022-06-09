package com.jghan.instaclone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class InstacloneApplication {

//	public static final String APPLICATION_LOCATIONS = "spring.config.location="
//			+ "classpath:application.properties,"
//			+ "classpath:credentails.yml";

	public static void main(String[] args) {
		SpringApplication.run(InstacloneApplication.class, args);
//		new SpringApplicationBuilder(InstacloneApplication.class)
//				.properties(APPLICATION_LOCATIONS)
//				.run(args);
	}

}
