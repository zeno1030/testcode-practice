package sample.cafekiosk.unit;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import sample.cafekiosk.unit.beverage.Beverage;
import sample.cafekiosk.unit.order.Order;
@Getter
public class CafeKiosk {
	private static final LocalTime SHOP_OPEN_TIME = LocalTime.of(10, 0);
	private static final LocalTime SHOP_CLOSE_TIME = LocalTime.of(22, 0);

	private final List<Beverage> beverages = new ArrayList<>();

	public void add(Beverage beverage) {
		beverages.add(beverage);
	}

	public void add(Beverage beverage, int count) {
		if (count <= 0) {
			throw new IllegalArgumentException("음료는 1잔 이상 주문하실 수 있습니다.");
		}
		for (int i = 0; i < count; i++) {
			beverages.add(beverage);
		}
	}

	public void remove(Beverage beverage) {
		beverages.remove(beverage);
	}

	public void clear() {
		beverages.clear();
	}

	public int calculateTotalPrice() {
		int totalPrice = 0;
		for (Beverage beverage : beverages) {
			totalPrice += beverage.getPrice();
		}
		return totalPrice;
	}

	public Order createOrder() {
		LocalDateTime currentDateTime = LocalDateTime.now();
		LocalTime now = currentDateTime.toLocalTime();
		if (now.isBefore(SHOP_OPEN_TIME) || now.isAfter(SHOP_CLOSE_TIME)) {
			throw new IllegalArgumentException("가게 문이 닫았습니다");
		} else {
			return new Order(currentDateTime, beverages);
		}
	}
	public Order createOrder(LocalDateTime currentDateTime) {
		LocalTime now = currentDateTime.toLocalTime();
		if (now.isBefore(SHOP_OPEN_TIME) || now.isAfter(SHOP_CLOSE_TIME)) {
			throw new IllegalArgumentException("가게 문이 닫았습니다");
		} else {
			return new Order(currentDateTime, beverages);
		}
	}
}


