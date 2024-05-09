package com.tracker.trackerapi.service;


import com.tracker.trackerapi.api.model.Notification;

import java.util.List;

public interface NotificationService {

    Notification createNotification(Notification notification);
    Notification getNotification(long notification_id );
    List<Notification> getNotiificationForSupplier(int supplier_id);
    Notification updateNotificationById(long notification_id );

    void deleteNotification(long notification_id);
}

