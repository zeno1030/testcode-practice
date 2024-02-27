package sample.cafekiosk.unit.beverage;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class AmericanoTest {
	@DisplayName("아메리카노 하나를 추가하면 장바구니에 담긴다")
	@Test
	void getName() {
		Americano americano = new Americano();
		assertEquals(americano.getName(),"아메리카노");
		assertThat(americano.getName()).isEqualTo("아메리카노");
	}

	@Test
	void getPrice() {
		Americano americano = new Americano();
		assertThat(americano.getPrice()).isEqualTo(4000);
	}
}