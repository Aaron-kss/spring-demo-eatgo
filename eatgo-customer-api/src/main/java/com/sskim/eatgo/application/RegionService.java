package com.sskim.eatgo.application;

import com.sskim.eatgo.domain.Region;
import com.sskim.eatgo.domain.RegionRepository;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class RegionService {

    private final RegionRepository regionRepository;

    public RegionService(RegionRepository regionRepository){
        this.regionRepository = regionRepository;
    }

    public List<Region> getRegions(){
        List<Region> regions = regionRepository.findAll();

        return Optional.ofNullable(regions).orElse(Collections.emptyList());
    }
}