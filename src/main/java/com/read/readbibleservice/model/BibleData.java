package com.read.readbibleservice.model;

import lombok.*;

import java.time.LocalDate;

@Data
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BibleData {

    private String uniqueId;
    private LocalDate date;
    private String book;
    private int chapter;
    private int fromVerse;
    private int toVerse;
}
