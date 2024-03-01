package sample.cafekiosk.spring.api.service.order.response;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Getter;
import sample.cafekiosk.spring.domain.order.OrderStatus;
import sample.cafekiosk.spring.domain.orderproduct.OrderProduct;
@Getter
public class OrderResponse {

	private Long id;
	private OrderStatus orderStatus;
	private int totalPrice;
	private LocalDateTime registeredDatetime;
	private List<OrderProduct> orderProducts;
}
