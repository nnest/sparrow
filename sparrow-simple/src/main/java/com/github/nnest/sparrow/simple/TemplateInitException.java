/**
 * 
 */
package com.github.nnest.sparrow.simple;

/**
 * template initial exception
 * 
 * @author brad.wu
 * @since 0.0.1
 * @version 0.0.1
 */
public class TemplateInitException extends RuntimeException {
	private static final long serialVersionUID = 887501726873686954L;

	public TemplateInitException() {
		super();
	}

	protected TemplateInitException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public TemplateInitException(String message, Throwable cause) {
		super(message, cause);
	}

	public TemplateInitException(String message) {
		super(message);
	}

	public TemplateInitException(Throwable cause) {
		super(cause);
	}
}
