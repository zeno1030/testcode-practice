package sample.cafekiosk.spring.domain.stock;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sample.cafekiosk.spring.domain.BaseEntity;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Stock extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String productNumber;

	private int quantity;

	@Builder
	public Stock(Long id, String productNumber, int quantity) {
		this.id = id;
		this.productNumber = productNumber;
		this.quantity = quantity;
	}

	public static Stock create(String productNumber, int quantity) {
		return Stock.builder()
			.productNumber(productNumber)
			.quantity(quantity)
			.build();
	}

	public boolean isQuantityLessThan(int quantity) {
		return this.quantity < quantity;
	}

	public void deductQuantity(int quantity) {
		if (isQuantityLessThan(quantity)) {
			throw new IllegalArgumentException("남은 재고보다 많은 양의 수량을 주문 했습니다");
		}
		this.quantity -= quantity;
	}
}
