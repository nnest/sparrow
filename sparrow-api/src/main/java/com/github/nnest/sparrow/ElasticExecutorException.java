/**
 * 
 */
package com.github.nnest.sparrow;

/**
 * Elastic executor exception
 * 
 * @author brad.wu
 * @since 0.0.1
 * @version 0.0.1
 */
public class ElasticExecutorException extends Exception {
	private static final long serialVersionUID = 3370223516514331236L;

	public ElasticExecutorException() {
		super();
	}

	public ElasticExecutorException(String message, Throwable cause) {
		super(message, cause);
	}

	public ElasticExecutorException(String message) {
		super(message);
	}

	public ElasticExecutorException(Throwable cause) {
		super(cause);
	}
}
