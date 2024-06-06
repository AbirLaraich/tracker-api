package com.tracker.trackerapi.service;

import com.tracker.trackerapi.api.model.Order;
import com.tracker.trackerapi.api.model.Status;
import com.tracker.trackerapi.api.model.dto.OrderDto;

import java.util.List;

public interface OrderService {
    Order createOrder(Order order);
    List<Order> getByOwner(int siretNumber);
    List<Order> getByDistributeur(int siretNumber);
    Order updateOrderStatus(Long orderId, Status status);
    List<Order> findProcessedOrder(Status status,  int distributer_id ) ;
    Order getById(Long id);
    Order updateOrder(Order order);
    OrderDto getOrderById(long id);
    Order updateOrderInBlockChain(Long orderId, boolean inBlockChain);

}
