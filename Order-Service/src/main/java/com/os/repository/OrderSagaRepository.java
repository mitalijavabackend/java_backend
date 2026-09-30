package com.os.repository;

import com.os.entity.OrderSaga;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderSagaRepository extends JpaRepository<OrderSaga,String> {
}
