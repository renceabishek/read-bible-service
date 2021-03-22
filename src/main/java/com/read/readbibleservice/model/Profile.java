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
    private String uniqueId;
    private  String name;
    private String role;
    private String picUrl;
    private boolean isLinked;

    public Profile(String uniqueId, String name, String role) {
        this.uniqueId = uniqueId;
        this.name = name;
        this.role = role;
    }
}
