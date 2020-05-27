package com.reactive.reactivecosmosexample.configuration;

import com.mongodb.MongoClientSettings;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;

import java.util.Collections;
import java.util.concurrent.TimeUnit;

@Slf4j
@Configuration
public class CosmosConfig  {
    private MongoClient mongoClient;

    @Value("${spring.data.mongodb.host}")
    private String host;

    @Value("${spring.data.mongodb.database}")
    private String database;

    @Value("${spring.data.mongodb.user}")
    private String username;

    @Value("${spring.data.mongodb.password}")
    private String password;

    @Value("${spring.data.mongodb.port}")
    private String port;

    public MongoClient mongoClient() {
        MongoCredential credentials = MongoCredential.createCredential(username, database, password.toCharArray());
        MongoClientSettings settings = MongoClientSettings.builder().credential(credentials)
                .applyToSslSettings(builder ->
                        builder.enabled(true))
                .applyToClusterSettings(builder -> builder.hosts(Collections.singletonList(new ServerAddress(host + ":" + port))))
                .applyToConnectionPoolSettings(builder -> builder.maxConnectionIdleTime(300000, TimeUnit.MILLISECONDS))
                .applyToServerSettings(builder -> builder.heartbeatFrequency(5000,TimeUnit.MILLISECONDS))
                .retryWrites(false)
                .build();
        mongoClient = MongoClients.create(settings);
        return mongoClient;
    }
    @Bean
    public ReactiveMongoTemplate reactiveMongoTemplate() {
        return new ReactiveMongoTemplate(mongoClient(), database);
    }
}
