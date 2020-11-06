package br.com.clinica.dto;

public class ResponseDTO<T> {
	
	private T payload;
	
	private String message;
	
	public ResponseDTO() {
		super();
	}

	public ResponseDTO(T payload, String message) {
		super();
		this.payload = payload;
		this.message = message;
	}

	public T getPayload() {
		return payload;
	}

	public void setPayload(T payload) {
		this.payload = payload;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	

}
