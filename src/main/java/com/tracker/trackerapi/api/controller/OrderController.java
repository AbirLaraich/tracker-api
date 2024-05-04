package com.tracker.trackerapi.api.controller;

import com.tracker.trackerapi.api.model.*;
import com.tracker.trackerapi.api.model.dto.*;
import com.tracker.trackerapi.service.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.tracker.trackerapi.api.model.mapper.OrderMapper.mapDistributerDto;
import static com.tracker.trackerapi.api.model.mapper.OrderMapper.mapSupplierDto;

@RestController
@RequestMapping("/api")
public class OrderController {
    private final OrderService orderService;
    private final SupplierService supplierService;
    private final DistributerService distributerService;
    private final ProductService productService;
    private final LotService lotService;
    public OrderController(OrderService orderService, SupplierService supplierService, DistributerService distributerService, ProductService productService, LotService lotService) {
        this.orderService = orderService;
        this.supplierService = supplierService;
        this.distributerService = distributerService;
        this.productService = productService;
        this.lotService = lotService;
    }

    @PostMapping("/order")
    public ResponseEntity<?> create(@RequestBody OrderDto orderDto) {
        try {
            Supplier supplier = this.supplierService.getSupplierByEmail(orderDto.getOwner().getEmail());
            Distributer distributer = this.distributerService.getDistributerByEmail(orderDto.getDistributer().getEmail());
            LocalDateTime currentDateTime = LocalDateTime.now();
            Date orderCreationDate = Date.from(currentDateTime.atZone(ZoneId.systemDefault()).toInstant());
            Order order = new Order(distributer, supplier, orderCreationDate, orderDto.getStatus());
            Order savedOrder = this.orderService.createOrder(order);
            List<Lot> lots = orderDto.getLots().stream()
                    .map(lotDto -> this.lotService.getLot(lotDto.getNumLot()))
                    .toList();
            lots.forEach((lotToUpdate) -> {
                    lotToUpdate.setOrder(savedOrder);
                    lotToUpdate.setDistributer(distributer);
                    this.lotService.updateLot(lotToUpdate);
            });
            return new ResponseEntity<>(orderDto, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Erreur lors de la création de la commande", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/orders/{siretNumber}")
    public ResponseEntity<List<OrderDto>> getByOwner(@PathVariable int siretNumber) {
        try {
            List<Order> orders = this.orderService.getByOwner(siretNumber);
            if (orders.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            List<OrderDto> ordersDto = new ArrayList<>();
            for (Order order : orders) {
                List<Lot> lots = this.lotService.getLotsByOrder(order);
                OrderDto orderDto = new OrderDto(order.getId(),
                        new DistributerDto(order.getDistributer().getEmail(),
                                order.getDistributer().getPassword(),
                                order.getDistributer().getAdresse(),
                                order.getDistributer().getName(),
                                order.getDistributer().getSiretNumber()),
                        new SupplierDto(order.getOwner().getEmail(),
                                order.getOwner().getPassword(),
                                order.getOwner().getAdresse(),
                                order.getOwner().getName(),
                                order.getOwner().getSiretNumber()),
                        order.getStatus()
                );
                List<LotDto> lotsDto = lots.stream()
                        .map(lot -> new LotDto(
                                lot.getNumLot(),
                                lot.getName(),
                                new SupplierDto(
                                        lot.getSupplier().getEmail(),
                                        lot.getSupplier().getPassword(),
                                        lot.getSupplier().getAdresse(),
                                        lot.getSupplier().getName(),
                                        lot.getSupplier().getSiretNumber()
                                ),
                                new DistributerDto(
                                        lot.getDistributer().getEmail(),
                                        lot.getDistributer().getPassword(),
                                        lot.getDistributer().getAdresse(),
                                        lot.getDistributer().getName(),
                                        lot.getDistributer().getSiretNumber()
                                ),
                                lot.getCreation_date()
                        ))
                        .collect(Collectors.toList());

                orderDto.setLots(lotsDto);
                ordersDto.add(orderDto);
            }
            return new ResponseEntity<>(ordersDto, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/orders/distributer/{siretNumber}")
    public ResponseEntity<List<OrderDto>> getByDistributer(@PathVariable int siretNumber) {
        try {

            List<Order> orders = this.orderService.getByDistributeur(siretNumber);
            if (orders.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            List<OrderDto> ordersDto = new ArrayList<>();
            for (Order order : orders) {
                OrderDto orderDto = new OrderDto(order.getId(),
                        new DistributerDto(order.getDistributer().getEmail(),
                                order.getDistributer().getPassword(),
                                order.getDistributer().getAdresse(),
                                order.getDistributer().getName(),
                                order.getDistributer().getSiretNumber()),
                        new SupplierDto(order.getOwner().getEmail(),
                                order.getOwner().getPassword(),
                                order.getOwner().getAdresse(),
                                order.getOwner().getName(),
                                order.getOwner().getSiretNumber()),
                        order.getStatus()
                );
                ordersDto.add(orderDto);
            }

            return new ResponseEntity<>(ordersDto, HttpStatus.OK);


        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }
    @PutMapping("/order/{orderId}/cancel")
    @Transactional
    public ResponseEntity<String> updateOrderStatus(@PathVariable Long orderId) {
        try {
            this.orderService.updateOrderStatus(orderId, Status.CANCELED);
            return new ResponseEntity<>("Annulation avec Succès", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PutMapping("/order/{orderId}/processed")
    @Transactional
    public ResponseEntity<String> sendOrder(@PathVariable Long orderId) {
        try {
            this.orderService.updateOrderStatus(orderId, Status.PROCESSED);
            return new ResponseEntity<>("Envoie de commande avec Succès", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/processedOrder/{siret_number}")
    public ResponseEntity<List<OrderDto>> findProcessedOrder(@PathVariable int siret_number) {
        try {
            Distributer distributer =  distributerService.findBySiretNumber(siret_number);
            List<Order>  orders =  this.orderService.findProcessedOrder(Status.PROCESSED, distributer.getId());

            List<OrderDto> ordersDto = new ArrayList<>();
            for (Order order : orders) {
                List<Lot> lots = this.lotService.getLotsByOrder(order);
                OrderDto orderDto = new OrderDto(order.getId(),
                        mapDistributerDto(order),
                        mapSupplierDto(order),
                        order.getStatus()
                );
                List<LotDto> lotsDto = lots.stream()
                        .map(lot -> new LotDto(
                                lot.getNumLot(),
                                lot.getName(),
                                new SupplierDto(
                                        lot.getSupplier().getEmail(),
                                        lot.getSupplier().getPassword(),
                                        lot.getSupplier().getAdresse(),
                                        lot.getSupplier().getName(),
                                        lot.getSupplier().getSiretNumber()
                                ),
                                new DistributerDto(
                                        lot.getDistributer().getEmail(),
                                        lot.getDistributer().getPassword(),
                                        lot.getDistributer().getAdresse(),
                                        lot.getDistributer().getName(),
                                        lot.getDistributer().getSiretNumber()
                                ),
                                lot.getCreation_date()
                        ))
                        .collect(Collectors.toList());

                orderDto.setLots(lotsDto);
                ordersDto.add(orderDto);
            }
            return new ResponseEntity<>(ordersDto, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PutMapping("/order/{orderId}")
    public ResponseEntity<?> update(@PathVariable Long orderId, @RequestBody OrderDto updatedOrderDto) {
        try {
            Distributer distributer = this.distributerService.getDistributerByEmail(updatedOrderDto.getDistributer().getEmail());

            Order existingOrder = this.orderService.getById(orderId);
            if (existingOrder == null) {
                return new ResponseEntity<>("La commande spécifiée n'a pas été trouvée", HttpStatus.NOT_FOUND);
            }

            existingOrder.setStatus(updatedOrderDto.getStatus());

            List<Lot> existingLots = this.lotService.getLotsByOrder(existingOrder);
            List<LotDto> lotsDto = updatedOrderDto.getLots();
            List<Lot> lotsToRemoveFromOrder = new ArrayList<>();
            List<LotDto> lotsToAddToOrderDto = new ArrayList<>();

            for (Lot existingLot : existingLots) {
                boolean found = false;

                for (LotDto lotDto : lotsDto) {
                    if (existingLot.getNumLot().equals(lotDto.getNumLot())) {
                        found = true;
                        break;
                    }
                }

                if (!found) {
                    lotsToRemoveFromOrder.add(existingLot);
                }
            }

            for (LotDto lotDto : lotsDto) {
                boolean found = false;

                for (Lot existingLot : existingLots) {
                    if (existingLot.getNumLot().equals(lotDto.getNumLot())) {
                        found = true;
                        break;
                    }
                }

                if (!found) {
                    lotsToAddToOrderDto.add(lotDto);
                }
            }
            Order updatedOrder = this.orderService.updateOrder(existingOrder);

            List<Lot> updatedLotsToAdd = lotsToAddToOrderDto.stream()
                    .map(lotDto -> {
                        Lot existingLot = this.lotService.getLot(lotDto.getNumLot());
                        if (existingLot != null) {
                            existingLot.setOrder(updatedOrder);
                            existingLot.setDistributer(distributer);
                            return this.lotService.updateLot(existingLot);
                        } else {
                            return null;
                        }
                    })
                    .filter(Objects::nonNull)
                    .toList();

            List<Lot> updatedLotsToRemove = lotsToRemoveFromOrder.stream()
                    .map(lotDto -> {
                        Lot existingLot = this.lotService.getLot(lotDto.getNumLot());
                        if (existingLot != null) {
                            existingLot.setOrder(null);
                            existingLot.setDistributer(null);
                            return this.lotService.updateLot(existingLot);
                        } else {
                            return null;
                        }
                    })
                    .filter(Objects::nonNull)
                    .toList();

            return new ResponseEntity<>(updatedOrderDto, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Erreur lors de la mise à jour de la commande", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}