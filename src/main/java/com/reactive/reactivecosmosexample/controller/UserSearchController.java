package com.reactive.reactivecosmosexample.controller;

import com.reactive.reactivecosmosexample.domain.User;
import com.reactive.reactivecosmosexample.repository.UserRepository;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.List;

@RestController
@RequestMapping("/reactiveDemo/")
public class UserSearchController {

    private UserRepository userRepository;

    public UserSearchController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/findUserByUserId/{userId}")
    public Mono<User> findUserByUserId(@PathVariable String userId) {
        return userRepository.getUserByUserId(userId);
    }

    @GetMapping("/findAllUsers")
    public Flux<User> findAllUsers() {
        return userRepository.findAll();
    }

    @GetMapping(value = "/users", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<User> findUsers() {
        return userRepository.findWithTailableCursorBy().delayElements(Duration.ofMillis(2500));
    }

    @PostMapping(value = "/create/users")
    public void createUsers(@RequestBody List<User> userList) {
                     Flux.fromIterable(userList)
					.flatMap(userRepository::save).delayElements(Duration.ofMillis(1000))
					.subscribe(System.out::println);
    }

    @DeleteMapping(value = "/dropusers")
    public void deleteUsers() {
        userRepository.deleteAll().subscribe(System.out::println);
    }
}
