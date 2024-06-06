package com.tracker.trackerapi.repository;

import com.tracker.trackerapi.api.model.Distance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DistanceRepository extends JpaRepository<Distance, Integer> {
   /* @Query ("select d from Distance d where d.supplier.id = :supplierID  and d.distributor.id = :distributerID")
    Distance getDistanceBySupplierIdAndDistributerId(@Param("supplierID") int supplierID, @Param("distributerID") int distributerID);*/

    @Query("SELECT d FROM Distance d WHERE d.supplier.id = :supplier_id AND d.distributor.id = :distributer_id")
    Distance findDistanceBySupplierAndDistributor(@Param("supplier_id") int supplier_id, @Param("distributer_id") int distributer_id);

    @Query("SELECT d FROM Distance d WHERE d.supplier.adresse = :supplierAdresse AND d.distributor.adresse = :distributerAdresse")
    Distance findDistanceByAdresse(@Param("supplierAdresse") String supplierAdresse, @Param("distributerAdresse") String distributerAdresse);

}

