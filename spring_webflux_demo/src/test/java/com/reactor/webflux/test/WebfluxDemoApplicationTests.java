
package com.reactor.webflux.test;

import java.util.Collections;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.reactor.webflux.model.Post;
import com.reactor.webflux.repository.PostRepository;

import reactor.core.publisher.Mono;

/**
 * @author jdeshmukh
 * @date 08-Feb-2020
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:application.properties")
public class WebfluxDemoApplicationTests {

	@Autowired
	private WebTestClient webTestClient;

	@Autowired
	PostRepository postRepository;

	@Test
	public void testCreatepost() {
		Post post = new Post("This is a Test post");

		webTestClient.post().uri("/posts").contentType(MediaType.APPLICATION_JSON_UTF8)
				.accept(MediaType.APPLICATION_JSON_UTF8).body(Mono.just(post), Post.class).exchange().expectStatus()
				.isOk().expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8).expectBody().jsonPath("$.text")
				.isEqualTo("This is a Test post");
	}

	@Test
	public void testGetAllposts() {
		webTestClient.get().uri("/posts").accept(MediaType.APPLICATION_JSON_UTF8).exchange().expectStatus().isOk()
				.expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8).expectBodyList(Post.class);
	}

	@Test
	public void testGetSinglepost() {
		Post post = postRepository.save(new Post("Hello, World!")).block();

		webTestClient.get().uri("/posts/{id}", Collections.singletonMap("id", post.getid())).exchange().expectStatus()
				.isOk().expectBody()
				.consumeWith(response -> Assertions.assertThat(response.getResponseBody()).isNotNull());
	}

	@Test
	public void testUpdatepost() {
		Post post = postRepository.save(new Post("Initial post")).block();

		Post newpostData = new Post("Updated post");

		webTestClient.put().uri("/posts/{id}", Collections.singletonMap("id", post.getid()))
				.contentType(MediaType.APPLICATION_JSON_UTF8).accept(MediaType.APPLICATION_JSON_UTF8)
				.body(Mono.just(newpostData), Post.class).exchange().expectStatus().isOk().expectHeader()
				.contentType(MediaType.APPLICATION_JSON_UTF8).expectBody().jsonPath("$.text").isEqualTo("Updated post");
	}

	@Test
	public void testDeletepost() {
		Post post = postRepository.save(new Post("To be deleted")).block();

		webTestClient.delete().uri("/posts/{id}", Collections.singletonMap("id", post.getid())).exchange()
				.expectStatus().isOk();
	}
}