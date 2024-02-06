package sample.sample;

import java.util.Scanner;

public class CalculationRequestReader {
	public CalculationRequest read() {
		Scanner scanner = new Scanner(System.in);
		System.out.println("Enter two numbers and operator");
		String result = scanner.nextLine();
		String[] parts = result.split(" ");
		return new CalculationRequest(parts);
	}
}
