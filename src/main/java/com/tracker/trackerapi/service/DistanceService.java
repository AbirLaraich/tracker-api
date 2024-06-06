package com.tracker.trackerapi.service;

import com.tracker.trackerapi.api.model.Distance;
import com.tracker.trackerapi.api.model.Distributer;
import com.tracker.trackerapi.api.model.Supplier;

public interface DistanceService {
    public Distance getDistanceBy(String supplier, String distributer);
}
