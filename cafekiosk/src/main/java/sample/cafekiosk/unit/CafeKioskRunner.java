package sample.cafekiosk.unit;

import java.time.LocalDateTime;

import sample.cafekiosk.unit.beverage.Americano;
import sample.cafekiosk.unit.beverage.Latte;
import sample.cafekiosk.unit.order.OrderUnit;

public class CafeKioskRunner {

	public static void main(String[] args) {
		CafeKiosk cafeKiosk = new CafeKiosk();
		cafeKiosk.add(new Americano());
		System.out.println(">>>> 아메리카노 추가");

		cafeKiosk.add(new Latte());
		System.out.println(">>>> 라떼 추가");

		int price = cafeKiosk.calculateTotalPrice();
		System.out.println("price = " + price);

		OrderUnit orderUnit = cafeKiosk.createOrder(LocalDateTime.now());
	}
}
