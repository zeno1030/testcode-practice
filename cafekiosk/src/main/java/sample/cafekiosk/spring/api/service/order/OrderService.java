package sample.cafekiosk.spring.api.service.order;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import lombok.RequiredArgsConstructor;
import sample.cafekiosk.spring.api.controller.order.request.OrderCreateRequest;
import sample.cafekiosk.spring.api.service.order.response.OrderResponse;
import sample.cafekiosk.spring.domain.product.ProductRepository;

@RequiredArgsConstructor
@Service
public class OrderService {

	private final ProductRepository productRepository;
	public OrderResponse createOrder(OrderCreateRequest orderCreateRequest) {
		List<String> productNumbers = orderCreateRequest.getProductNumbers();
	}
}
