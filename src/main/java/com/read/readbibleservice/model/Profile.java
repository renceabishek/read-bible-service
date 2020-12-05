package com.read.readbibleservice.model;

import lombok.*;

@Data
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Profile {
    private  String name;
    private String role;
}
