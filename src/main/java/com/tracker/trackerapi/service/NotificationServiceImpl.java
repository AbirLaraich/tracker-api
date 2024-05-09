package com.tracker.trackerapi.service;

import com.tracker.trackerapi.api.model.Notification;
import com.tracker.trackerapi.exeption.HttpErrorException;
import com.tracker.trackerapi.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationServiceImpl implements NotificationService {
    public final  NotificationRepository notificationRepository ;

    @Autowired
    public NotificationServiceImpl(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    @Override
    public Notification createNotification(Notification notification ) {
        try {
            notificationRepository.save(notification);
            return notification;
        } catch (Exception e) {
            throw new HttpErrorException("Error occurred while creating the notification.", HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }

    @Override
    public Notification getNotification(long notification_id) {
        try {
            return  notificationRepository.findNotificationByNotif_id(notification_id);
        } catch (Exception e) {
            throw new HttpErrorException("Error occurred while getting the notification.", HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }

    @Override
    public List<Notification> getNotiificationForSupplier(int supplier_id) {
        return  notificationRepository.findNotificationBySupplierId(supplier_id);
    }

    @Override
    public Notification updateNotificationById(long notification_id) {
        Notification notification = getNotification((int) notification_id) ;
        notification.setRead(!notification.isRead());
        notificationRepository.save(notification);
        return notification;
    }

    @Override
    public void deleteNotification(long notification_id) {
        try{
            notificationRepository.deleteNotificationByNotif_id(notification_id);
        } catch (Exception e) {
            throw new HttpErrorException("Error occurred while deleting the notification.", HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }
}
