package com.sskim.eatgo.interfaces;

import com.sskim.eatgo.application.RegionService;
import com.sskim.eatgo.domain.Region;
import jdk.nashorn.internal.ir.FunctionNode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.net.URISyntaxException;
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

    @PostMapping("/regions")
    public ResponseEntity<?> create(@RequestBody Region resource) throws URISyntaxException {

        Region regions = regionService.addRegion(resource.getName());
        String url = "/regions/" + regions.getId();

        return ResponseEntity.created(new URI(url)).body("{}");
    }
}