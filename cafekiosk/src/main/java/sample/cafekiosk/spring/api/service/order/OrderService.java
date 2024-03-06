package sample.cafekiosk.spring.api.service.order;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import sample.cafekiosk.spring.api.controller.order.request.OrderCreateRequest;
import sample.cafekiosk.spring.api.service.order.response.OrderResponse;
import sample.cafekiosk.spring.domain.order.Order;
import sample.cafekiosk.spring.domain.order.OrderRepository;
import sample.cafekiosk.spring.domain.product.Product;
import sample.cafekiosk.spring.domain.product.ProductRepository;
import sample.cafekiosk.spring.domain.product.ProductType;
import sample.cafekiosk.spring.domain.stock.Stock;
import sample.cafekiosk.spring.domain.stock.StockRepository;

@RequiredArgsConstructor
@Service
public class OrderService {

	private final ProductRepository productRepository;
	private final OrderRepository orderRepository;
	private final StockRepository stockRepository;

	@Transactional
	public OrderResponse createOrder(OrderCreateRequest orderCreateRequest, LocalDateTime registerDateTime) {
		List<String> productNumbers = orderCreateRequest.getProductNumbers();
		List<Product> products = findProductsBy(productNumbers);

		//재고 차감 체크가 필요한 상품들 filter
		deductStockQuantity(products);

		Order order = Order.create(products, registerDateTime);
		Order savedOrder = orderRepository.save(order);

		return OrderResponse.of(savedOrder);
	}

	private void deductStockQuantity(List<Product> products) {
		List<String> stockProductNumbers = extractStockProductNumbers(products);

		Map<String, Stock> stockMap = createStockMapBy(stockProductNumbers);
		Map<String, Long> productCountingMap = createProductCountingMapBy(stockProductNumbers);

		for (String stockProductNumber : new HashSet<>(stockProductNumbers)) {
			Stock stock = stockMap.get(stockProductNumber);
			int quantity = productCountingMap.get(stockProductNumber).intValue();
			if (stock.isQuantityLessThan(quantity)) {
				throw new IllegalArgumentException("재고가 부족한 상품이 있습니다.");
			}
			stock.deductQuantity(quantity);

		}
	}

	private static Map<String, Long> createProductCountingMapBy(List<String> stockProductNumbers) {
		return stockProductNumbers.stream()
			.collect(Collectors.groupingBy(p -> p, Collectors.counting()));
	}

	private Map<String, Stock> createStockMapBy(List<String> stockProductNumbers) {
		List<Stock> stocks = stockRepository.findAllByProductNumberIn(stockProductNumbers);
		return stocks.stream().collect(Collectors.toMap(Stock::getProductNumber, s -> s));
	}

	private static List<String> extractStockProductNumbers(List<Product> products) {
		return products.stream()
			.filter(product -> ProductType.containsType(product.getType()))
			.map(Product::getProductNumber)
			.toList();
	}

	private List<Product> findProductsBy(List<String> productNumbers) {
		List<Product> products = productRepository.findAllByProductNumberIn(productNumbers);
		Map<String, Product> productMap = products.stream()
			.collect(Collectors.toMap(Product::getProductNumber, p -> p));

		return productNumbers.stream()
			.map(productMap::get)
			.toList();
	}
}

