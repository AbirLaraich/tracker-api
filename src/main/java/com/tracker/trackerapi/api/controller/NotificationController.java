package com.tracker.trackerapi.api.controller;
import com.tracker.trackerapi.api.model.*;
import com.tracker.trackerapi.api.model.dto.DistributerDto;
import com.tracker.trackerapi.api.model.dto.NotificationDto;
import com.tracker.trackerapi.api.model.dto.ProductDto;
import com.tracker.trackerapi.service.DistributerService;
import com.tracker.trackerapi.service.NotificationService;
import com.tracker.trackerapi.service.OrderService;
import com.tracker.trackerapi.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api")
public class NotificationController {

    private final NotificationService notificationService;
    private final DistributerService distributerService;
    public final OrderService orderService;

    private final SupplierService supplierService;


    public NotificationController(NotificationService notificationService, DistributerService distributerService, OrderService orderService, SupplierService supplierService) {
        this.notificationService = notificationService;
        this.distributerService = distributerService;
        this.orderService = orderService;
        this.supplierService = supplierService;
    }

    @PostMapping("/notification")
    public ResponseEntity<?> createNotification(@RequestBody NotificationDto notificationDto) {
        try {
            Distributer distributer = distributerService.getDistributerByEmail(notificationDto.getDistributerEmail());
            Supplier supplier = supplierService.getSupplierByEmail(notificationDto.getSupplierEmail());
            Order order = orderService.getById(notificationDto.getOrder_id());

            LocalDateTime currentDateTime = LocalDateTime.now();
            Notification notification = new Notification(distributer, supplier, order, notificationDto.isRead(), currentDateTime);
            Notification createdNotification = notificationService.createNotification(notification);

            notificationDto.setId(createdNotification.getNotif_id());

            return new ResponseEntity<>(notificationDto, HttpStatus.CREATED);

        }catch (Exception exception) {
            return new ResponseEntity<>("Erreur lors de la création du produit", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/notification/{id}")
    public ResponseEntity<NotificationDto> getNotification(@PathVariable("id") long id) {
        try {
            Notification notification = notificationService.getNotification(id);
            if (notification == null)
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);

            NotificationDto notificationDto = new NotificationDto(notification.getNotif_id(),
                    notification.getDistributor().getEmail(),
                    notification.getSupplier().getEmail(),
                    notification.getOrder().getId(),
                    notification.isRead(),
                    notification.getCreateDate()
            );
            return new ResponseEntity<>(notificationDto, HttpStatus.OK);


        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }


    @GetMapping("/notification/distributer/{distributerEmail}")
    public ResponseEntity<List<NotificationDto>> getNotificationsByDistributer(@PathVariable("distributerEmail") String distributerEmail) {
        try {

            List<Notification> notifications  = new ArrayList<>();
            Distributer distributer =  this.distributerService.getDistributerByEmail(distributerEmail);
            if(distributer != null)
                 notifications = this.notificationService.findNotificationByDistributerId(distributer.getId());
            if (notifications.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            List<NotificationDto> notificationsDto = new ArrayList<>();
            for (Notification notification : notifications) {
                NotificationDto notificationDto = new NotificationDto(notification.getNotif_id(),
                        notification.getDistributor().getEmail(),
                        notification.getSupplier().getEmail(),
                        notification.getOrder().getId(),
                        notification.isRead(),
                        notification.getCreateDate()
                );
                notificationsDto.add(notificationDto);
            }

            return new ResponseEntity<>(notificationsDto, HttpStatus.OK);


        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }


    @PutMapping("/notification/update/{id}")
    public ResponseEntity<NotificationDto> updateNotification(@PathVariable("id") long id) {
        try {

            Notification notification = notificationService.updateNotificationById(id);

            NotificationDto notificationsDto = new NotificationDto(notification.getNotif_id(),
                    notification.getDistributor().getEmail(),
                    notification.getSupplier().getEmail(),
                    notification.getOrder().getId(),
                    notification.isRead(),
                    notification.getCreateDate()
            );
            return new ResponseEntity<>(notificationsDto, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNotification(@PathVariable("id") long id) {
        notificationService.deleteNotification(id);
        return ResponseEntity.noContent().build();
    }
}
