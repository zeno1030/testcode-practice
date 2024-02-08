package sample.sample;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class CalculationRequestTest {
	@Test
	public void 유효한_숫자를_파싱할_수_있다() {
		//given
		String[] parts = {"2", "+", "3"};

		//when
		CalculationRequest request = new CalculationRequest(parts);
		//then
		assertEquals(2, request.getNum1());
		assertEquals("+", request.getOperator());
		assertEquals(3, request.getNum2());
	}
}