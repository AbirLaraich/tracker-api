package com.tracker.trackerapi.service;

import com.tracker.trackerapi.api.model.Distance;
import com.tracker.trackerapi.api.model.Distributer;
import com.tracker.trackerapi.api.model.Supplier;
import com.tracker.trackerapi.repository.DistanceRepository;
import org.springframework.stereotype.Service;

@Service
public class DistanceServiceImpl implements DistanceService{
    private final DistanceRepository distanceRepository;

    public DistanceServiceImpl(DistanceRepository distanceRepository) {
        this.distanceRepository = distanceRepository;
    }

    @Override
    public Distance getDistanceBy(String supplier, String distributer) {
        return this.distanceRepository.findDistanceByAdresse(supplier, distributer);
    }
}
