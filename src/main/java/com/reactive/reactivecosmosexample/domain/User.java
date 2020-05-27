package com.reactive.reactivecosmosexample.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Builder
@AllArgsConstructor
@Data
@Document(collection = "UserInformation")
public class User {
    @Id
    private String id;
    private String userId;
    private String firstName;
    private String lastName;
    private String role;
}
