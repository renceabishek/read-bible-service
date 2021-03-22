package com.read.readbibleservice.service;

import com.read.readbibleservice.model.vo.BibleData;
import reactor.core.publisher.Mono;

import java.util.List;

public interface DataService {
    Mono<List<BibleData>> fetchAllBibleData();

    String createBibleData(BibleData bibleData);

    void updateBibleData(BibleData bibleData, String uniqueId);

    void deleteBibleData(String uniqueId);
}
