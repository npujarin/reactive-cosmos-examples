package com.reactive.reactivecosmosexample;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Arrays;
import java.util.List;

public class MonoAndFluxTest {
    List<String> userList = Arrays.asList("Naveen", "Anindya", "Mahi", "Wynand", "Levi");

    @Test
    public void fluxUsingListTest() {
        Flux<String> stringFlux = Flux.fromStream(userList.stream()).log();
        StepVerifier.create(stringFlux)
                .expectNext("Naveen", "Anindya", "Mahi", "Wynand", "Levi")
                .verifyComplete();  //Stream will start emitting data
    }

    @Test
    public void monoUsingSingleElementTest() {
        Mono<String> mono = Mono.justOrEmpty("SpringWebFlux");

        StepVerifier.create(mono.log())
                .expectNext("SpringWebFlux")
                .verifyComplete();
    }
}
