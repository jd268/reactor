package com.reactor.webflux.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.reactor.webflux.model.Post;

/**
 * @author jdeshmukh
 * @date 08-Feb-2020
 *
 */
public interface PostRepository extends ReactiveMongoRepository<Post, String>{

	
}
