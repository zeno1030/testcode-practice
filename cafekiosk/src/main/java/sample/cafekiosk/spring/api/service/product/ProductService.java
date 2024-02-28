package sample.cafekiosk.spring.api.service.product;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import sample.cafekiosk.spring.api.service.product.response.ProductResponse;
import sample.cafekiosk.spring.domain.product.ProductRepository;
import sample.cafekiosk.spring.domain.product.ProductSellingStatus;

@Service
@RequiredArgsConstructor
public class ProductService {

	private final ProductRepository productRepository;

	public List<ProductResponse> getSellingProducts() {

		return productRepository.findAllBySellingStatusIn(ProductSellingStatus.forDisplay())
			.stream().map(ProductResponse::toResponse).collect(Collectors.toList());

	}
}
