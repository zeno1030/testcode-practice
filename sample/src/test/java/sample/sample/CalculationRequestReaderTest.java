package sample.sample;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import ch.qos.logback.core.util.PropertySetterException;

class CalculationRequestReaderTest {

	@Test
	void System_in으로_데이터를_읽어들일_수_있다() {
		//given
		CalculationRequestReader reader = new CalculationRequestReader();

		//when
		System.setIn(new ByteArrayInputStream("2 + 3".getBytes()));
		CalculationRequest result = reader.read();

		assertThat("2").isEqualTo(result.getNum1());
		assertThat("+").isEqualTo(result.getOperator());
		assertThat("3").isEqualTo(result.getNum2());

	}
}