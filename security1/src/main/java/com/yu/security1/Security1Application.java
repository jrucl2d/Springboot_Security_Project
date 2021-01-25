package com.yu.security1;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class Security1Application {
	private static final String PROPERTIES = "spring.config.location=classpath:/application.yml"
			+ ",classpath:/oauth2settings.yml";
	public static void main(String[] args) {
		new SpringApplicationBuilder(Security1Application.class).properties(PROPERTIES).run(args);
	}

}
