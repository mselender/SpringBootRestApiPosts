package com.mselender.springboot;

import com.mselender.springboot.controller.RestApiController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import com.mselender.springboot.model.Post;

import java.util.List;


@SpringBootApplication(scanBasePackages={"com.mselender.springboot"})// same as @Configuration @EnableAutoConfiguration @ComponentScan combined
public class SpringBootRestApiApp {

	//public static final Logger logger = LoggerFactory.getLogger(RestApiController.class);

	public static void main(String[] args) {
		SpringApplication.run(SpringBootRestApiApp.class, args);

	}
	/*
	public static void main(String[] args) {
			ApplicationContext ctx = SpringApplication.run(
				SpringBootRestApiApp.class, args);
		);
	}
	*/
	/*
	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}

	@Bean
	public CommandLineRunner run(RestTemplate restTemplate) throws Exception {
		return args -> {
			List<Post> posts = restTemplate.getForObject(
					"http://jsonplaceholder.typicode.com/posts", List.class);
			logger.info(posts.toString());
		};
	}
	*/

}
