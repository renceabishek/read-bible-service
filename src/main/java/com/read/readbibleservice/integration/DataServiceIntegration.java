package com.read.readbibleservice.integration;

import com.read.readbibleservice.model.BibleData;
import reactor.core.publisher.Mono;

import java.util.HashMap;

public interface DataServiceIntegration {

    Mono<HashMap<String, BibleData>> fetchAllBibleData();

    String createBibleData(BibleData bibleData);

    void updateBibleData(BibleData bibleData, String uniqueId);

    void deleteBibleData(String uniqueId);

}
