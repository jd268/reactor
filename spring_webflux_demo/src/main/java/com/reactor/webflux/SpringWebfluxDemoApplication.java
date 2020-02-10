package com.reactor.webflux;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

/**
 * @author jdeshmukh
 * @date 08-Feb-2020
 *
 */
@SpringBootApplication
@EnableReactiveMongoRepositories
public class SpringWebfluxDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringWebfluxDemoApplication.class, args);
	}

}
