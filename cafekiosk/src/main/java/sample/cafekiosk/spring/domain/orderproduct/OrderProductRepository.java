package sample.cafekiosk.spring.domain.orderproduct;

import org.springframework.data.jpa.repository.JpaRepository;

import lombok.extern.java.Log;

public interface OrderProductRepository extends JpaRepository<OrderProduct, Long> {
}
