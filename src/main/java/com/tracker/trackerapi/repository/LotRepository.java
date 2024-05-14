package com.tracker.trackerapi.repository;

import com.tracker.trackerapi.api.model.Lot;
import com.tracker.trackerapi.api.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LotRepository extends JpaRepository<Lot, Integer> {
    @Query("Select l from Lot l where l.numLot = :numLot")
    Lot getLotByNumLot(@Param("numLot") String numLot);
    List<Lot> getLotsBySupplier_SiretNumber(Long num_siret);
    List<Lot> getLotsByOrder(Order order);

    List<Lot> getLotsByDistributer_SiretNumber(Long num_siret);

    @Query("select l from Lot l WHERE l.order.id = :order_id")
    List<Lot> getLotsByOrder(@Param("order_id") long order_id);
}
