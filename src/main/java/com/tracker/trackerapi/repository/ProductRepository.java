package com.tracker.trackerapi.repository;

import com.tracker.trackerapi.api.model.Lot;
import com.tracker.trackerapi.api.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>  {
    List<Product> getProductsByDistributer_SiretNumber(int siretNumber);
    Product getProductByNumProduct(int numProduct);
    List<Product> getProductsByLot(Lot lot);

}
