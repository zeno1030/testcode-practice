package sample.cafekiosk.spring.api.service.order;

import static org.assertj.core.api.Assertions.*;
import static sample.cafekiosk.spring.domain.product.ProductSellingStatus.*;
import static sample.cafekiosk.spring.domain.product.ProductType.*;

import java.time.LocalDateTime;
import java.util.List;

import org.assertj.core.groups.Tuple;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import sample.cafekiosk.spring.api.controller.order.request.OrderCreateRequest;
import sample.cafekiosk.spring.api.service.order.response.OrderResponse;
import sample.cafekiosk.spring.domain.product.Product;
import sample.cafekiosk.spring.domain.product.ProductRepository;
import sample.cafekiosk.spring.domain.product.ProductType;

@ActiveProfiles("test")
@SpringBootTest
class OrderServiceTest {

	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private OrderService orderService;

	@DisplayName("주문번호 리스트를 받아 주문을 생성한다.")
	@Test
	void createOrder(){
	    //given
		LocalDateTime registeredDate = LocalDateTime.now();

		Product product1 = createProduct(HANDMADE, "001", 4000);
		Product product2 = createProduct(HANDMADE, "002", 4500);
		Product product3 = createProduct(HANDMADE, "003", 7000);

		productRepository.saveAll(List.of(product1, product2, product3));

		OrderCreateRequest request = OrderCreateRequest.builder()
			.productNumbers(List.of("001", "002"))
			.build();

		//when

		OrderResponse orderResponse = orderService.createOrder(request, registeredDate);

	    //then
		assertThat(orderResponse.getId()).isNotNull();
		assertThat(orderResponse)
			.extracting("registeredDate", "totalPrice")
			.contains(registeredDate, 4000);
		assertThat(orderResponse.getProducts()).hasSize(2).
			extracting("productNumber", "price")
			.containsExactlyInAnyOrder(
				Tuple.tuple("001", 1000),
				Tuple.tuple("002", 3000)
			);

	  }

	  private Product createProduct(ProductType type, String productNumber, int price) {
		  return Product.builder()
			  .type(type)
			  .productNumber(productNumber)
			  .price(price)
			  .sellingStatus(SELLING)
			  .name("메뉴 이름")
			  .build();
	  }

}