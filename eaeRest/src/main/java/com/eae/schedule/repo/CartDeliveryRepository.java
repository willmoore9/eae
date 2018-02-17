package com.eae.schedule.repo;


import org.springframework.data.jpa.repository.JpaRepository;

import com.eae.schedule.model.CartDelivery;
import com.eae.schedule.model.CartDeliveryKey;

public interface CartDeliveryRepository extends JpaRepository<CartDelivery, CartDeliveryKey> {
}
