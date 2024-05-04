package com.tracker.trackerapi.service;

import com.tracker.trackerapi.api.model.Distributer;
import com.tracker.trackerapi.api.model.dto.DistributerDto;

import java.util.List;

public interface DistributerService {
    public void createDistributer(Distributer distributer);
    public List<DistributerDto> getDistributers();
    public Distributer getDistributerByEmail(String email);
    public Distributer findBySiretNumber(int siretNumber);

}
