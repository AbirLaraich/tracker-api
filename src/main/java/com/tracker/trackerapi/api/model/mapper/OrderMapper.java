package com.tracker.trackerapi.api.model.mapper;

import com.tracker.trackerapi.api.model.Distributer;
import com.tracker.trackerapi.api.model.Order;
import com.tracker.trackerapi.api.model.dto.DistributerDto;
import com.tracker.trackerapi.api.model.dto.SupplierDto;
import org.springframework.stereotype.Component;

@Component
public class OrderMapper {

    public static DistributerDto mapDistributerDto(Order order) {
        return new DistributerDto(order.getDistributer().getEmail(),
                order.getDistributer().getPassword(),
                order.getDistributer().getAdresse(),
                order.getDistributer().getName(),
                order.getDistributer().getSiretNumber());

    }

    public static SupplierDto mapSupplierDto(Order order) {
        return new SupplierDto(order.getOwner().getEmail(),
                order.getOwner().getPassword(),
                order.getOwner().getAdresse(),
                order.getOwner().getName(),
                order.getOwner().getSiretNumber());

    }
}
