package sample.sample;

public class BadRequestException extends RuntimeException {
	public BadRequestException() {
		super("연산의 길이가 너무 깁니다");
	}
}
