package com.elifsat.searchengine.dto;

public class ReturnDTO {
	private boolean status;
	private String message;
	private Object result;

	public ReturnDTO() {
		super();
	}

	public ReturnDTO(boolean status, String message) {
		super();
		this.status = status;
		this.message = message;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getResult() {
		return result;
	}

	public void setResult(Object result) {
		this.result = result;
	}

}
