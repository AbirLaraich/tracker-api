package com.tracker.trackerapi.service;

import com.tracker.trackerapi.api.model.*;
import com.tracker.trackerapi.api.model.dto.DistributerDto;
import com.tracker.trackerapi.api.model.dto.OrderDto;
import com.tracker.trackerapi.api.model.dto.SupplierDto;
import com.tracker.trackerapi.exeption.HttpErrorException;
import com.tracker.trackerapi.repository.DistanceRepository;
import com.tracker.trackerapi.repository.OrderRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.io.IOException;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final DistanceRepository distanceRepository;

    private final LotService lotService;
    private final RestTemplate restTemplate = new RestTemplate();

    public OrderServiceImpl(OrderRepository orderRepository, DistanceRepository distanceRepository, LotService lotService) {
        this.orderRepository = orderRepository;
        this.distanceRepository = distanceRepository;
        this.lotService =  lotService;
    }

    @Override
    public Order createOrder(Order order) {
        try {
            return this.orderRepository.save(order);
        } catch (Exception e) {
            throw new HttpErrorException("Error occurred while saving the user.", HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }

    @Override
    public List<Order> getByOwner(int siretNumber) {
        return this.orderRepository.findAllByOwner(siretNumber);
    }

    @Override
    public List<Order> getByDistributeur(int siretNumber) {
        return this.orderRepository.findAllByDistributer(siretNumber);
    }


    public String distanceCalculation(Location start, Location end) {
        String startLon =  start.getLon();
        String startLat =  start.getLat();
        String endLon =  end.getLon();
        String endLat =  end.getLat();
        String url = "http://router.project-osrm.org/route/v1/driving/"+ startLon+ "," + startLat +";"+ endLon +","+ endLat + "?steps=false&geometries=geojson";

        String routeJson =  restTemplate.getForObject(url, String.class);
        try{
            RouteResponse routeResponse =  RouteResponse.fromJson(routeJson);
            if (routeResponse.getRoutes().length > 0) {
                return routeResponse.getRoutes()[0].getDistance();
            }else{
                return null;
            }
        }catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


    private Location geocodeAddress(String address) {
        String url = "https://nominatim.openstreetmap.org/search?format=json&q=" + address;
        String jsonResponse = restTemplate.getForObject(url, String.class);

        try {
            Location[] locations = Location.fromJson(jsonResponse);
            if (locations.length > 0) {
                Location location = locations[0];
                return location;
            } else {
                return null;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Order updateOrderStatus(Long orderId, Status status) {
        try {
            Order order = getById(orderId);
            Distributer distributer = order.getDistributer();
            Supplier owner = order.getOwner();

            if (distributer != null && owner != null) {
                Distance distanceInDB = distanceRepository.findDistanceBySupplierAndDistributor(owner.getId(), distributer.getId());
                if (distanceInDB == null) {
                    String distributerAdresse = distributer.getAdresse();
                    String supplierAdress = owner.getAdresse();
                    Location startCoords = geocodeAddress(distributerAdresse);
                    Location endCoords = geocodeAddress(supplierAdress);

                    if (startCoords != null && endCoords != null) {
                        String distance = distanceCalculation(startCoords, endCoords);
                        try {
                            double distanceKm = Double.parseDouble(distance) / 1000;
                            distanceRepository.save(new Distance(distributer, owner, distanceKm));
                        } catch (NumberFormatException e) {
                            e.printStackTrace();
                            throw new HttpErrorException("Error parsing distance to double", 200);
                        }
                    }
                }
            }

            return this.orderRepository.getOrderById(this.orderRepository.updateOrderStatus(orderId, status));
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("error: " + e);
            throw new HttpErrorException("An error occurred while updating the order status", 1);
        }
    }

    @Override
    public List<Order> findProcessedOrder(Status status, int distributer_id) {
        try {
            List<Order> orders = orderRepository.getOrderByStatusAndDistributer(status, distributer_id);
            return orders;

        } catch (Exception e) {
            throw new HttpErrorException("Error in findProcessedOrder. " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }

    @Override
    public Order getById(Long id) {
        return this.orderRepository.findById(id).orElse(null);
    }

    @Override
    public Order updateOrder(Order order) {
        return this.orderRepository.save(order);
    }

    @Override
    public OrderDto getOrderById(long id) {
        Status status = Status.PROCESSED;
        Order order = orderRepository.getOrderByOrderId(status,id);
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
                order.getStatus());

        long orderId  =  orderDto.getId();
        orderDto.setLots(lotService.getLotsByOrder(orderId));
         return orderDto;
    }


}

