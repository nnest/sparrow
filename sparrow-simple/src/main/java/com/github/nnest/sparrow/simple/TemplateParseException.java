/**
 * 
 */
package com.github.nnest.sparrow.simple;

/**
 * template parse exception
 * 
 * @author brad.wu
 * @since 0.0.1
 * @version 0.0.1
 */
public class TemplateParseException extends RuntimeException {
	private static final long serialVersionUID = 887501726873686954L;

	public TemplateParseException() {
		super();
	}

	protected TemplateParseException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public TemplateParseException(String message, Throwable cause) {
		super(message, cause);
	}

	public TemplateParseException(String message) {
		super(message);
	}

	public TemplateParseException(Throwable cause) {
		super(cause);
	}
}
