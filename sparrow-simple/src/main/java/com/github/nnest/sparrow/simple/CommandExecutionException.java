/**
 * 
 */
package com.github.nnest.sparrow.simple;

/**
 * command execution exception
 * 
 * @author brad.wu
 * @since 0.0.1
 * @version 0.0.1
 */
public class CommandExecutionException extends RuntimeException {
	private static final long serialVersionUID = -1723152271247412886L;

	public CommandExecutionException() {
		super();
	}

	protected CommandExecutionException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public CommandExecutionException(String message, Throwable cause) {
		super(message, cause);
	}

	public CommandExecutionException(String message) {
		super(message);
	}

	public CommandExecutionException(Throwable cause) {
		super(cause);
	}
}
