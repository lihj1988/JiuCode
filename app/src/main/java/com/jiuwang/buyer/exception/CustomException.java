package com.jiuwang.buyer.exception;

/**
 * 异常类
 * @author dewyze
 *
 */
public class CustomException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CustomException() {
		super();
	}

	public CustomException(String detailMessage, Throwable throwable) {
		super(detailMessage, throwable);
	}

	public CustomException(String detailMessage) {
		super(detailMessage);
	}

	public CustomException(Throwable throwable) {
		super(throwable);
	}

}
