package com.tracker.trackerapi.repository;

import com.tracker.trackerapi.api.model.Order;
import com.tracker.trackerapi.api.model.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    @Query("SELECT o FROM Order o WHERE o.owner.siretNumber = :siretNumber")
    List<Order> findAllByOwner(@Param("siretNumber") int siretNumber);
    @Query("SELECT o FROM Order o WHERE o.distributer.siretNumber = :siretNumber order by o.id ASC ")
    List<Order> findAllByDistributer(@Param("siretNumber") int siretNumber);

    @Modifying
    @Query("UPDATE Order o SET o.status = :status WHERE o.id = :orderId")
    int updateOrderStatus(@Param("orderId") Long orderId, @Param("status") Status status);

    @Query("SELECT o FROM Order o WHERE o.status = :status AND o.distributer.id = :distributer_id")
    List<Order> getOrderByStatusAndDistributer(Status status , int distributer_id) ;

    @Query("select o From Order o where o.status = :status and o.id = :order_id")
    Order  getOrderByOrderId(Status status , long order_id) ;
    @Query("select o From Order o where o.id = :order_id")
    Order getOrderById(long order_id);
    @Modifying
    @Query("UPDATE Order o SET o.inBlockchain = :inBlockChain WHERE o.id = :orderId")
    Order updateOrderBlockChain(@Param("orderId") Long orderId, @Param("inBlockChain") boolean inBlockChain);
}
