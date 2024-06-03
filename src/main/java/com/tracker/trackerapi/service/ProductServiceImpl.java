package com.tracker.trackerapi.service;

import com.tracker.trackerapi.api.model.Lot;
import com.tracker.trackerapi.api.model.Product;
import com.tracker.trackerapi.api.model.dto.DistributerDto;
import com.tracker.trackerapi.api.model.dto.LotDto;
import com.tracker.trackerapi.api.model.dto.ProductDto;
import com.tracker.trackerapi.api.model.dto.SupplierDto;
import com.tracker.trackerapi.exeption.HttpErrorException;
import com.tracker.trackerapi.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Product createProduct(Product product) {
        try {
            return productRepository.save(product);
        } catch (Exception e) {
            throw new HttpErrorException("Error occurred while saving the product.", HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }

    @Override
    public List<ProductDto> getProducts() {
        List<Product> products = productRepository.findAll();
        return products.stream().map(product -> new ProductDto(product.getNumSiret(), product.getWeight(), product.getLot().getNumLot(), new DistributerDto(product.getDistributer().getEmail(), product.getDistributer().getPassword(), product.getDistributer().getAdresse(), product.getDistributer().getName(), product.getDistributer().getSiretNumber()), product.getDeliveryDate())).collect(Collectors.toList());
    }


    @Override
    public List<Product> getProductByDistributerSiretNumber(int siretNumber) {
        return productRepository.getProductsByDistributer_SiretNumber(siretNumber);
    }

    @Override
    public Product getProductByNumProduct(int numProduct) {
        return productRepository.getProductByNumProduct(numProduct);
    }

    @Override
    public Product updateProduct(int numProduct, Date deliveryDate) {
        Product product = getProduct(numProduct);
        if (product != null) {
            product.setDeliveryDate(deliveryDate);
            productRepository.save(product);
            return product;
        }

        return null;
    }

    @Override
    public Product getProduct(int numProduct) {
        return productRepository.getProductByNumProduct(numProduct);
    }

    @Override
    public Product getProductBy(String qrCode) {
        return productRepository.getProductByQrCode(qrCode);
    }

    @Override
    public List<Product> getProductsByLot(Lot lot) {
        return this.productRepository.getProductsByLot(lot);
    }

    @Override
    public Product addBlockChainDataToProduct(Product product) {
        return this.productRepository.save(product);
    }


}
