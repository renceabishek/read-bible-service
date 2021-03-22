package com.read.readbibleservice.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.read.readbibleservice.integration.DataServiceIntegration;
import com.read.readbibleservice.model.dao.BibleDataDao;
import com.read.readbibleservice.model.vo.BibleData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DataServiceImp implements DataService {

    @Autowired
    ObjectMapper objectMapper;

    private final DataServiceIntegration dataServiceIntegration;

    public DataServiceImp(DataServiceIntegration dataServiceIntegration) {
        this.dataServiceIntegration = dataServiceIntegration;
    }

    @Override
    public Mono<List<BibleData>> fetchAllBibleData() {
        return dataServiceIntegration.fetchAllBibleData()
                .map(data -> data.entrySet().stream().map(dataEntry -> {
                    BibleData bibleData = objectMapper.convertValue(dataEntry.getValue(), BibleData.class);
                    return new BibleData(dataEntry.getKey(), bibleData.getName(), bibleData.getDate(), bibleData.getBook(),
                            bibleData.getChapter(), bibleData.getFromVerse(), bibleData.getToVerse());
                }).collect(Collectors.toList()));
    }

    @Override
    public String createBibleData(BibleData bibleData) {
        BibleDataDao bibleDataDao = BibleDataDao.builder()
                .name(bibleData.getName())
                .book(bibleData.getBook())
                .chapter(bibleData.getChapter())
                .date(bibleData.getDate())
                .fromVerse(bibleData.getFromVerse())
                .toVerse(bibleData.getToVerse())
                .build();

        return dataServiceIntegration.createBibleData(bibleDataDao);
    }

    @Override
    public void updateBibleData(BibleData bibleData, String uniqueId) {
        dataServiceIntegration.updateBibleData(bibleData, uniqueId);
    }

    @Override
    public void deleteBibleData(String uniqueId) {
        dataServiceIntegration.deleteBibleData(uniqueId);
    }
}
