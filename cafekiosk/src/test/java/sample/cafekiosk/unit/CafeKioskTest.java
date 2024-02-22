package sample.cafekiosk.unit;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;

import sample.cafekiosk.unit.beverage.Americano;
import sample.cafekiosk.unit.beverage.Latte;

class CafeKioskTest {

	@Test
	void add_manual_test() {
		CafeKiosk cafeKiosk = new CafeKiosk();
		cafeKiosk.add(new Americano());

		System.out.println(">>>담긴 음료수 :" + cafeKiosk.getBeverages().size());
		System.out.println(">>>담긴 음료:" + cafeKiosk.getBeverages().get(0).getName());
	}

	@Test
	void add() {
		CafeKiosk cafeKiosk = new CafeKiosk();
		cafeKiosk.add(new Americano());

		assertThat(cafeKiosk.getBeverages().size()).isEqualTo(1);
		assertThat(cafeKiosk.getBeverages().get(0).getName()).isEqualTo("아메리카노");
		assertThat(cafeKiosk.getBeverages().get(0).getPrice()).isEqualTo(4000);

	}

	@Test
	void remove() {
		CafeKiosk cafeKiosk = new CafeKiosk();
		Americano americano = new Americano();
		cafeKiosk.add(americano);
		cafeKiosk.remove(americano);

		assertThat(cafeKiosk.getBeverages().size()).isEqualTo(0);
	}

	@Test
	void clear() {
		CafeKiosk cafeKiosk = new CafeKiosk();
		Americano americano = new Americano();
		Latte latte = new Latte();
		cafeKiosk.add(americano);
		cafeKiosk.add(latte);
		cafeKiosk.clear();

		assertThat(cafeKiosk.getBeverages()).isEmpty();

	}
}