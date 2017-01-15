/**
 * 
 */
package com.github.nnest.sparrow.command.document;

import com.github.nnest.sparrow.ElasticCommandException;

/**
 * Elastic incorrect version exception.
 * 
 * @author brad.wu
 * @since 0.0.1
 * @version 0.0.1
 */
public class ElasticDocumentIncorrectVersionException extends ElasticCommandException {
	private static final long serialVersionUID = 1255159416482859322L;

	public ElasticDocumentIncorrectVersionException() {
		super();
	}

	public ElasticDocumentIncorrectVersionException(String message, Throwable cause) {
		super(message, cause);
	}

	public ElasticDocumentIncorrectVersionException(String message) {
		super(message);
	}

	public ElasticDocumentIncorrectVersionException(Throwable cause) {
		super(cause);
	}
}
