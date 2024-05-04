package com.tracker.trackerapi.repository;

import com.tracker.trackerapi.api.model.Distributer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DistributerRepository extends JpaRepository<Distributer, Integer> {
    Distributer findByEmail(String email);
    Distributer findBySiretNumber(int siretNumber);

}
