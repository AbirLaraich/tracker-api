package com.tracker.trackerapi.api.controller;

import com.tracker.trackerapi.api.model.*;
import com.tracker.trackerapi.api.model.dto.DistributerDto;
import com.tracker.trackerapi.api.model.dto.LotDto;
import com.tracker.trackerapi.api.model.dto.SupplierDto;
import com.tracker.trackerapi.service.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static com.tracker.trackerapi.api.model.mapper.DistributerMapper.mapDistributerDtoByLot;
import static com.tracker.trackerapi.api.model.mapper.SuppplierMapper.mapSupplierDtoByLot;

@RestController
@RequestMapping("/api")
public class LotController {

    public final LotService lotService;
    private final SupplierService supplierService;
    public final ProductService productService;
    public final DistributerService distributerService;
    public final OrderService orderService;
    public LotController(LotService lotService, SupplierService supplierService, ProductService productService, DistributerService distributerService, OrderService orderService) {
        this.lotService = lotService;
        this.supplierService = supplierService;
        this.productService = productService;
        this.distributerService = distributerService;
        this.orderService = orderService;
    }

    @PostMapping("/lot")
    public ResponseEntity<?> createLot(@RequestBody LotDto lotDto) {
        try {
            Supplier supplier = this.supplierService.getSupplierByEmail(lotDto.getSupplier().getEmail());
            Lot lotToSave = new Lot(lotDto.getNumLot(), lotDto.getName(), supplier, null, lotDto.getCreation_date());
            Lot savedLot = this.lotService.createLot(lotToSave);

            LotDto lotResponse = new LotDto(savedLot.getNumLot(), savedLot.getName(), lotDto.getSupplier(), null, lotDto.getCreation_date());
            return new ResponseEntity<>(lotResponse, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Erreur lors de la création du lot", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/lot/{num_siret}")
    public ResponseEntity<List<LotDto>> getLot(@PathVariable Long num_siret) {
        try {
            List<LotDto> lotDtoList = this.lotService.getLotsBySupplierSiretNumber(num_siret);
            return new ResponseEntity<>(lotDtoList, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }

    @GetMapping("/lot/no_Order/{num_siret}")
    public ResponseEntity<List<LotDto>> getLotWithoutOrder(@PathVariable Long num_siret) {
        try {
            List<LotDto> lotDtoList = this.lotService.getLotsWithoutOrderBySupplierSiretNumber(num_siret);
            return new ResponseEntity<>(lotDtoList, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }

//    @GetMapping("/lot/distributer/{num_siret}")
//    public ResponseEntity<List<LotDto>> getLotByDistributerAndSiretNumber(@PathVariable Long num_siret) {
//        try {
//            List<LotDto> lotDtoList  = this.lotService.getLotsByDistributerAndSiretNumber(num_siret);
//            return new ResponseEntity<>(lotDtoList, HttpStatus.OK);
//
//        } catch (Exception e) {
//            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//
//        }
//}


    @GetMapping("/order/lot/{order_id}")
    public ResponseEntity<List<LotDto>> getLotsByOrder(@PathVariable int order_id) {
        try {
            List<LotDto> lotDtoList = this.lotService.getLotsByOrder(order_id);
            return new ResponseEntity<>(lotDtoList, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }

    @GetMapping("/lot/order/{orderId}")
    public ResponseEntity<List<LotDto>> getLotsByOrder(@PathVariable Long orderId) {
        try {
            Order existingOrder = this.orderService.getById(orderId);
            List<Lot> lots  = this.lotService.getLotsByOrder(existingOrder);
            List<LotDto> lotsDto = lots.stream()
                    .map(lot -> {
                        SupplierDto supplierDto = mapSupplierDtoByLot(lot);
                        DistributerDto distributerDto = mapDistributerDtoByLot(lot);
                        return new LotDto(
                                lot.getNumLot(),
                                lot.getName(),
                                supplierDto,
                                distributerDto,
                                lot.getCreation_date(),
                                lot.getOrder().getId()
                        );
                    }).collect(Collectors.toList());
            return new ResponseEntity<>(lotsDto, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }
}
