package sample.cafekiosk.spring.domain.product;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ProductTypeTest {


	@DisplayName("상품 타입이 재고 관련 타입인지를 체크한다.")
	@Test
	void test(){
	    //given
		ProductType givenType = ProductType.BOTTLE;
		//when
		boolean result = ProductType.containsType(givenType);
		//then
		assertThat(result).isTrue();

	  }

}