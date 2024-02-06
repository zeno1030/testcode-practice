package sample.sample;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CalculatorTest {

	@Test
	@DisplayName("덧셈연산")
	void 덧셈연산을_할_수있다() {
		//given
		long num1 = 1;
		long num2 = 3;
		String operator = "+";
		//when
		Calculator calc = new Calculator();
		long answer = calc.calculate(num1, operator, num2);

		//then
		assertEquals(4, answer); //junit assertion
		assertThat(answer).isEqualTo(4); //assertj assertion
	}

	@Test
	@DisplayName("곱셈연산")
	void 곱셈연산을_할_수있다() {
		//given
		long num1 = 1;
		long num2 = 3;
		String operator = "*";
		//when
		Calculator calc = new Calculator();
		long answer = calc.calculate(num1, operator, num2);

		//then
		assertEquals(3, answer); //junit assertion
		assertThat(answer).isEqualTo(3); //assertj assertion
	}

	@Test
	@DisplayName("뺄셈연산")
	void 뺄셈연산을_할_수있다() {
		//given
		long num1 = 1;
		long num2 = 3;
		String operator = "-";
		//when
		Calculator calc = new Calculator();
		long answer = calc.calculate(num1, operator, num2);

		//then
		assertEquals(-2, answer); //junit assertion
		assertThat(answer).isEqualTo(-2); //assertj assertion
	}

	@Test
	@DisplayName("나눗셈연산")
	void 나눗셈연산을_할_수있다() {
		//given
		long num1 = 3;
		long num2 = 1;
		String operator = "/";
		//when
		Calculator calc = new Calculator();
		long answer = calc.calculate(num1, operator, num2);

		//then
		assertEquals(3, answer); //junit assertion
		assertThat(answer).isEqualTo(3); //assertj assertion
	}

	@Test
	@DisplayName("잘못된 연산")
	void 잘못된_연산() {
		//given
		long num1 = 3;
		long num2 = 1;
		String operator = "[]";
		//when
		Calculator calc = new Calculator();

		assertThrows(InvalidOperationException.class, () -> {
			calc.calculate(num1, operator, num2);

		});
	}
}