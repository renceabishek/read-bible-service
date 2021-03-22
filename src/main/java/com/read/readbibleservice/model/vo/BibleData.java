package com.read.readbibleservice.model.vo;

import lombok.*;

@Data
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BibleData {

    private String uniqueId;
    private String name;
    private String date;
    private String book;
    private int chapter;
    private int fromVerse;
    private int toVerse;
}
