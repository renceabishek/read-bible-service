package com.read.readbibleservice.integration;

import com.read.readbibleservice.model.dao.BibleDataDao;
import com.read.readbibleservice.model.vo.BibleData;
import reactor.core.publisher.Mono;

import java.util.HashMap;

public interface DataServiceIntegration {

    Mono<HashMap<String, BibleData>> fetchAllBibleData();

    String createBibleData(BibleDataDao bibleData);

    void updateBibleData(BibleData bibleData, String uniqueId);

    void deleteBibleData(String uniqueId);

}
