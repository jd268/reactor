package com.reactor.webflux.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.reactor.webflux.model.Post;
import com.reactor.webflux.repository.PostRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author jdeshmukh
 * @date 08-Feb-2020
 *
 */
public class PostController {

	@Autowired
    private PostRepository postRepository;

    /**
     * @return 
     * get all posts in the form of publisher type flux
     */
    @GetMapping("/posts")
    public Flux<Post> getAllPosts() {
        return postRepository.findAll();
    }

    /**
     * @param post
     * @return
     * create new post and return it in the form of publisher type mono
     */
    @PostMapping("/posts")
    public Mono<Post> createPost(@Valid @RequestBody Post post) {
        return postRepository.save(post);
    }

    /**
     * @param postId
     * @return
     * get specific post by its id in the form of publisher type mono
     */
    @GetMapping("/posts/{id}")
    public Mono<ResponseEntity<Post>> getPostById(@PathVariable(value = "id") String postId) {
		return postRepository.findById(postId)
				.map(post -> ResponseEntity.ok(post))
				.defaultIfEmpty(ResponseEntity.notFound().build());
    }

    /**
     * @param postId
     * @param post
     * @return
     * update specific post with new post
     */
    @PutMapping("/posts/{id}")
    public Mono<ResponseEntity<Post>> updatePost(@PathVariable(value = "id") String postId,
                                                   @Valid @RequestBody Post post) {
        return postRepository.findById(postId)
                .flatMap(existingPost -> {
                	existingPost.setText(post.getText());
                    return postRepository.save(existingPost);
                })
                .map(updatedPost -> new ResponseEntity<>(updatedPost, HttpStatus.OK))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * @param postId
     * @return
     * deletes a specific post with given post id
     */
    @DeleteMapping("/posts/{id}")
    public Mono<ResponseEntity<Void>> deleteTweet(@PathVariable(value = "id") String postId) {

        return postRepository.findById(postId)
                .flatMap(existingPost ->
                        postRepository.delete(existingPost)
                            .then(Mono.just(new ResponseEntity<Void>(HttpStatus.OK)))
                )
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    
    /**
     * @return
     * Send to the client as Server Sent Events
     */
    @GetMapping(value = "/stream/tweets", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Post> streamAllTweets() {
        return postRepository.findAll();
    }
}