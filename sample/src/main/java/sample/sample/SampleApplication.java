package sample.sample;

import java.util.Scanner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// @SpringBootApplication
public class SampleApplication {

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		Calculator cal = new Calculator();

		System.out.println("Enter two numbers and operator");
		String result = scanner.nextLine();
		String[] parts = result.split(" ");
		long num1 = Long.parseLong(parts[0]);
		long num2 = Long.parseLong(parts[2]);
		String operator = parts[1];

		long answer = cal.calculate(num1, operator, num2);

		System.out.print(answer);
	}

}
