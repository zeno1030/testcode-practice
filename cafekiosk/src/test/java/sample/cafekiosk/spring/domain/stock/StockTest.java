package sample.cafekiosk.spring.domain.stock;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class StockTest {

	@DisplayName("재고의 수량이 제공된 수량보다 적은지 확인한다.")
	@Test
	void isQuantityLessThan() {
		//given
		Stock stock = Stock.create("001", 1);
		int quantity = 2;
		//when
		boolean result = stock.isQuantityLessThan(quantity);

		//then
		assertThat(result).isTrue();

	}

	@DisplayName("재고를 주어진 개수 만큼 차감할 수 있다.")
	@Test
	void deductQuantityTest() {
		//given
		Stock stock = Stock.create("001", 1);
		int quantity = 1;
		//when
		stock.deductQuantity(quantity);

		//then
		assertThat(stock.getQuantity()).isEqualTo(0);

	}

	@DisplayName("재고보다 많은 수의 수량으로 차감 시도하는 경우 예외가 발생한다.")
	@Test
	void deductQuantityExceptionTest() {
		//given
		Stock stock = Stock.create("001", 1);
		int quantity = 2;
		
		//then
		assertThatThrownBy(() -> stock.deductQuantity(quantity))
			.isInstanceOf(IllegalArgumentException.class)
			.hasMessage("남은 재고보다 많은 양의 수량을 주문 했습니다");

	}
}