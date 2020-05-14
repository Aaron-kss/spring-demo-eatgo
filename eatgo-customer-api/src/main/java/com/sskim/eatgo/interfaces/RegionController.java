package com.sskim.eatgo.interfaces;

import com.sskim.eatgo.application.RegionService;
import com.sskim.eatgo.domain.Region;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
public class RegionController {

    private final RegionService regionService;

    public RegionController(RegionService regionService){
        this.regionService = regionService;
    }

    @GetMapping("/regions")
    public List<Region> list(){
        List<Region> regions = regionService.getRegions();

        return Optional.ofNullable(regions).orElse(Collections.emptyList());
    }
}