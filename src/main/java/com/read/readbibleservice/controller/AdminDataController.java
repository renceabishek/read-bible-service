package com.read.readbibleservice.controller;

import com.read.readbibleservice.model.vo.BibleData;
import com.read.readbibleservice.service.DataService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("api/admin/data")
public class AdminDataController {

    private final DataService dataService;

    public AdminDataController(DataService dataService) {
        this.dataService = dataService;
    }

    @GetMapping
    public Mono<List<BibleData>> getAlltheBibleData() {
        return dataService.fetchAllBibleData();
    }

    @PostMapping
    public String createBibleData(@RequestBody BibleData bibleData) {
        return dataService.createBibleData(bibleData);
    }

    @PutMapping("/{uniqueId}")
    public void updateBibleData(@RequestBody BibleData bibleData, @PathVariable("uniqueId") String uniqueId) {
        dataService.updateBibleData(bibleData, uniqueId);
    }

    @DeleteMapping("{uniqueId}")
    public void deleteBibleData(@PathVariable("uniqueId") String uniqueId) {
        dataService.deleteBibleData(uniqueId);
    }

}
