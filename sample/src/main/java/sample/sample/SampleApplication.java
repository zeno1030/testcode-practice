package sample.sample;

import java.util.Scanner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// @SpringBootApplication
public class SampleApplication {

	public static void main(String[] args) {
		Calculator cal = new Calculator();
		CalculationRequest request = new CalculationRequestReader().read();
		long answer = cal.calculate(
			request.getNum1(),
			request.getOperator(),
			request.getNum2()
		);

		System.out.print(answer);
	}

}
