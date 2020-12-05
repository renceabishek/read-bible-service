package com.read.readbibleservice.model;

import lombok.*;

import java.time.LocalDateTime;

@Data
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    private String username;
    private String password;
    private String role;
    private String name;
    private String email;
    private boolean isEnabled;
    private String confirmationToken;
    private LocalDateTime confirmationDateTime;
}
