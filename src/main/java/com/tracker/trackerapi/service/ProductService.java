package com.tracker.trackerapi.service;

import com.tracker.trackerapi.api.model.Lot;
import com.tracker.trackerapi.api.model.Product;
import com.tracker.trackerapi.api.model.dto.ProductDto;

import java.util.Date;
import java.util.List;

public interface ProductService {

    public Product createProduct(Product product);
    public List<ProductDto> getProducts();
    public List<Product> getProductByDistributerSiretNumber(int siretNumber);
    public Product getProductByNumProduct(int numProduct);

    public Product updateProduct(int numProduct, Date delivery_date);

    public Product getProduct(int numProduct);
    public Product getProductBy(String qrCode);
    public List<Product> getProductsByLot(Lot lot) ;
    public Product addBlockChainDataToProduct(Product product);

}
