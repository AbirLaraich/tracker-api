package com.tracker.trackerapi.repository;

import com.tracker.trackerapi.api.model.Notification;
import com.tracker.trackerapi.api.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {

    @Query("SELECT n from Notification n WHERE n.supplier.id = :supplier_id")
    List<Notification> findNotificationBySupplierId(@Param("supplier_id") int supplier_id);

    @Query("SELECT n from Notification n WHERE n.notif_id = :notification_id")
    Notification findNotificationByNotif_id(@Param("notification_id") long notification_id);

    @Query("DELETE FROM Notification n where n.notif_id = :notification_id ")
    Notification deleteNotificationByNotif_id(@Param("notification_id") long notification_id);
}