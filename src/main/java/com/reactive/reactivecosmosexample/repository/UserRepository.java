package com.reactive.reactivecosmosexample.repository;

import com.reactive.reactivecosmosexample.domain.User;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.mongodb.repository.Tailable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserRepository extends ReactiveMongoRepository<User, String> {

    @Query("{ $and :[ { userId :  ?0 } ] }")
    Mono<User> getUserByUserId(String userId);

    @Tailable
    Flux<User> findWithTailableCursorBy();
}
