package sample.sample;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class CalculatorTest {

	@Test
	void 덧셈연산을_할_수있다() {
		//given
		long num1 = 1;
		long num2 = 3;
		String operator = "+";

		Calculator calc = new Calculator();
		long answer = calc.calculate(num1, operator, num2);
		assertEquals(4, answer);
		assertThat(answer).isEqualTo(4);
	}
}