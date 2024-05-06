package com.tracker.trackerapi.repository;

import com.tracker.trackerapi.api.model.Lot;
import com.tracker.trackerapi.api.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>  {
    List<Product> getProductsByDistributer_SiretNumber(int siretNumber);
    Product getProductByNumProduct(int numProduct);
    @Query("SELECT p FROM Product p WHERE p.qrCode = ?1")
    Product getProductByQrCode(String qrCode);
    List<Product> getProductsByLot(Lot lot);

}
