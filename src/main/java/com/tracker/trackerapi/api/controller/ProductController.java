package com.tracker.trackerapi.api.controller;

import com.tracker.trackerapi.api.model.Distributer;
import com.tracker.trackerapi.api.model.Lot;
import com.tracker.trackerapi.api.model.Product;
import com.tracker.trackerapi.api.model.dto.DistributerDto;
import com.tracker.trackerapi.api.model.dto.ProductDto;
import com.tracker.trackerapi.service.DistributerService;
import com.tracker.trackerapi.service.LotService;
import com.tracker.trackerapi.service.ProductService;
import com.tracker.trackerapi.service.QRCodeGenerationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.google.zxing.WriterException;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class ProductController {
    private final ProductService productService;
    private final DistributerService distributerService;
    private  final QRCodeGenerationService qrCodeGenerationService;
    private final LotService lotService;

    @Autowired
    public ProductController(ProductService productService, DistributerService distributerService, QRCodeGenerationService qrCodeGenerationService, LotService lotService) {
        this.productService = productService;
        this.distributerService = distributerService;
        this.qrCodeGenerationService = qrCodeGenerationService;
        this.lotService = lotService;
    }
    @PostMapping("/product")
    public ResponseEntity<?> createProduct(@RequestBody ProductDto productDto) {
        try {
            Distributer distributer = this.distributerService.getDistributerByEmail(productDto.getDistributer().getEmail());
            Lot lot = this.lotService.getLot(productDto.getNumLot());

            String qrData = "Numéro de produit : " + productDto.getNumProduct() + ", Poids : " + productDto.getWeight();

            byte[] qrCodeBytes = qrCodeGenerationService.generateQRCode(qrData, 200, 200);
            String qrCodeBase64 = Base64.getEncoder().encodeToString(qrCodeBytes);

            Product productToSave = new Product(productDto.getNumProduct(), productDto.getWeight(), lot, distributer);
            productToSave.setQrCode(qrCodeBase64);

            Product savedProduct = productService.createProduct(productToSave);
            ProductDto productResponse = new ProductDto(savedProduct.getNumSiret(), savedProduct.getWeight(), productDto.getNumLot(), productDto.getDistributer(), productDto.getDelivery_date());
            productResponse.setQrCode(savedProduct.getQrCode());
            return new ResponseEntity<>(productResponse, HttpStatus.CREATED);
        } catch (WriterException | IOException e) {
            return new ResponseEntity<>("Erreur lors de la génération du code QR", HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            return new ResponseEntity<>("Erreur lors de la création du produit", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/product/{numSiret}")
    public ResponseEntity<ProductDto> getProduct(@PathVariable int numSiret) {
        try {
            Product product = productService.getProduct(numSiret);
            if (product == null)
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);

            ProductDto productDto = new ProductDto(product.getNumSiret(),
                    product.getWeight(),
                    product.getLot().getNumLot(),
                    new DistributerDto(
                            product.getDistributer().getEmail(),
                            product.getDistributer().getPassword(),
                            product.getDistributer().getAdresse(),
                            product.getDistributer().getName(),
                            product.getDistributer().getSiretNumber()
                    )
                    ,
                    product.getDeliveryDate()
            );
            productDto.setQrCode(product.getQrCode());
            return new ResponseEntity<>(productDto, HttpStatus.OK);


        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/products/{siretNumber}")
    public ResponseEntity<List<ProductDto>> getProductByNumSiret(@PathVariable int siretNumber) {
        try {
            List<Product> products = productService.getProductByDistributerSiretNumber(siretNumber);
            List<ProductDto> productsDto = products.stream()
                    .map(product
                            -> new ProductDto(product.getNumSiret(),
                            product.getWeight(),
                            product.getLot().getNumLot(),
                            new DistributerDto(
                                    product.getDistributer().getEmail(),
                                    product.getDistributer().getPassword(),
                                    product.getDistributer().getAdresse(),
                                    product.getDistributer().getName(),
                                    product.getDistributer().getSiretNumber()
                            ),
                            product.getDeliveryDate(),
                            product.getQrCode()
                    ))
                    .collect(Collectors.toList());
            return new ResponseEntity<>(productsDto, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PutMapping("/update/product")
    public ResponseEntity<ProductDto> updateProductByNumSiret(@RequestParam int siretNumber,
                                                              @RequestParam String delivery_date) {
        try {
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date date = dateFormat.parse(delivery_date);
            Product product = productService.updateProduct(siretNumber, date);
            ProductDto productsDto = new ProductDto(product.getNumSiret(),
                    product.getWeight(),
                    product.getLot().getNumLot(),
                    new DistributerDto(
                            product.getDistributer().getEmail(),
                            product.getDistributer().getPassword(),
                            product.getDistributer().getAdresse(),
                            product.getDistributer().getName(),
                            product.getDistributer().getSiretNumber()
                    ),
                    product.getDeliveryDate(),
                    product.getQrCode()
            );
            return new ResponseEntity<>(productsDto, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/products/lots/{numLot}")
    public ResponseEntity<List<ProductDto>> getProductByLot(@PathVariable String numLot) {
        try {
            Lot existingLot = this.lotService.getLot(numLot);
            List<Product> products = this.productService.getProductsByLot(existingLot);
            List<ProductDto> productsDto = products.stream()
                    .map(product
                            -> new ProductDto(product.getNumSiret(),
                            product.getWeight(),
                            product.getLot().getNumLot(),
                            new DistributerDto(
                                    product.getDistributer().getEmail(),
                                    product.getDistributer().getPassword(),
                                    product.getDistributer().getAdresse(),
                                    product.getDistributer().getName(),
                                    product.getDistributer().getSiretNumber()
                            ),
                            product.getDeliveryDate(),
                            product.getQrCode()
                    ))
                    .collect(Collectors.toList());
            return new ResponseEntity<>(productsDto, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/product/{qrCode}")
    public ResponseEntity<ProductDto> getProductBy(@PathVariable String qrCode) {
        try {
            Product product = productService.getProductBy(qrCode);
            if (product == null)
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);

            ProductDto productDto = new ProductDto(product.getNumSiret(),
                    product.getWeight(),
                    product.getLot().getNumLot(),
                    new DistributerDto(
                            product.getDistributer().getEmail(),
                            product.getDistributer().getPassword(),
                            product.getDistributer().getAdresse(),
                            product.getDistributer().getName(),
                            product.getDistributer().getSiretNumber()
                    )
                    ,
                    product.getDeliveryDate()
            );
            productDto.setQrCode(product.getQrCode());
            return new ResponseEntity<>(productDto, HttpStatus.OK);


        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}