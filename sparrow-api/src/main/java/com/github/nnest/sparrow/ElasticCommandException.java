/**
 * 
 */
package com.github.nnest.sparrow;

/**
 * elastic command exception
 * 
 * @author brad.wu
 * @since 0.0.1
 * @version 0.0.1
 */
public class ElasticCommandException extends Exception {
	private static final long serialVersionUID = -5112736177018262487L;

	public ElasticCommandException() {
		super();
	}

	public ElasticCommandException(String message, Throwable cause) {
		super(message, cause);
	}

	public ElasticCommandException(String message) {
		super(message);
	}

	public ElasticCommandException(Throwable cause) {
		super(cause);
	}
}
