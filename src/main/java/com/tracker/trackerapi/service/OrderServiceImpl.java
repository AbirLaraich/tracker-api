package com.tracker.trackerapi.service;

import com.tracker.trackerapi.api.model.*;
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
    private final RestTemplate restTemplate = new RestTemplate();

    public OrderServiceImpl(OrderRepository orderRepository, DistanceRepository distanceRepository) {
        this.orderRepository = orderRepository;
        this.distanceRepository = distanceRepository;
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
    public void updateOrderStatus(Long orderId, Status status) {
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
                        distanceRepository.save(new Distance(distributer,owner,distanceKm));

                    } catch (Exception e) {
                        e.printStackTrace();

                    }
                }
            }
        }


        this.orderRepository.updateOrderStatus(orderId, status);
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

}

