package com.tracker.trackerapi.repository;

import com.tracker.trackerapi.api.model.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
@Repository
public interface SupplierRepository extends JpaRepository<Supplier, Integer> {
    @Query("SELECT s FROM Supplier s WHERE s.email = :email")
    Supplier findSupplierByEmail(@Param("email") String email);
}
