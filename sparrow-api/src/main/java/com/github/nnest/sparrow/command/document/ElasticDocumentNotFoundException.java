/**
 * 
 */
package com.github.nnest.sparrow.command.document;

import com.github.nnest.sparrow.ElasticCommandException;

/**
 * Elastic document not found exception
 * 
 * @author brad.wu
 * @since 0.0.1
 * @version 0.0.1
 */
public class ElasticDocumentNotFoundException extends ElasticCommandException {
	private static final long serialVersionUID = -7078760253956283063L;

	public ElasticDocumentNotFoundException() {
		super();
	}

	public ElasticDocumentNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public ElasticDocumentNotFoundException(String message) {
		super(message);
	}

	public ElasticDocumentNotFoundException(Throwable cause) {
		super(cause);
	}
}
