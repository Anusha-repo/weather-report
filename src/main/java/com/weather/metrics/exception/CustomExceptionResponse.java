package com.weather.metrics.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;


@Data
@AllArgsConstructor
public class CustomExceptionResponse extends RuntimeException {
	private final HttpStatus status;

	public CustomExceptionResponse(HttpStatus status, String message) {
		super(message);
		this.status = status;
	}

	public HttpStatus getStatus() {
		return status;
	}
}



