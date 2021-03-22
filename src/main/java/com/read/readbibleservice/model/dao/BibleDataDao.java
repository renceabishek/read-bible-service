package com.read.readbibleservice.model.dao;

import com.google.firebase.database.Exclude;
import lombok.*;

@Data
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BibleDataDao {

    private String name;
    private String date;
    private String book;
    private int chapter;
    private int fromVerse;
    private int toVerse;
}
