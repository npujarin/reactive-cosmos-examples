package com.reactive.reactivecosmosexample;

import com.reactive.reactivecosmosexample.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.util.Arrays;

@SpringBootTest
class ReactiveCosmosExampleApplicationTests {

	@Test
	void contextLoads() {

		WebClient client = WebClient.create("http://localhost:8080");//Base URL

		client.get().uri("/reactiveDemo/findAllUsers")
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
				.retrieve()
				.bodyToFlux(User.class)
				.log("Webclient :: findAllUsers called for list of users");

		client.get().uri("/reactiveDemo/findUserByUserId/{userId}", "JBH1234")
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
				.retrieve()
				.bodyToMono(User.class)
				.log("Webclient :: findUserByUserId called for single user");


		Flux<User> userFlux = Flux.fromIterable(Arrays.asList(
				             new User(null, "JBH1234", "Naveen", "Pujari", "Expert SE"),
				             new User(null, "JBH1235", "Rajaesh", "Ranguri", "Software Engineer")
						    ));

		client.post().uri("/reactiveDemo/create/users")
				.contentType(MediaType.APPLICATION_JSON)
				.body(userFlux, User.class)
				.retrieve()
				.bodyToMono(User.class)
				.log("Webclient :: Create user Post call");

	}



}
