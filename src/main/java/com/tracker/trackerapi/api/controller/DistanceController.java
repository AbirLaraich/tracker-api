package com.tracker.trackerapi.api.controller;

import com.tracker.trackerapi.api.model.Distance;
import com.tracker.trackerapi.api.model.Distributer;
import com.tracker.trackerapi.api.model.dto.DistanceDto;
import com.tracker.trackerapi.api.model.dto.DistanceDtoV2;
import com.tracker.trackerapi.service.DistanceService;
import com.tracker.trackerapi.service.DistributerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class DistanceController {
    private final DistanceService distanceService;

    public DistanceController(DistanceService distanceService) {
        this.distanceService = distanceService;
    }
    @PostMapping("/distance")
    public ResponseEntity<?> getDistance(@RequestBody DistanceDtoV2 distanceDto) {
        try {
            return new ResponseEntity<>(this.distanceService.getDistanceBy(distanceDto.getSupplierAdresse(), distanceDto.getDistributerAdresse()).getDistance(), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Erreur lors de la création de l'utilisateur", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
