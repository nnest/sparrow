/**
 * 
 */
package com.github.nnest.sparrow;

/**
 * elastic document validation exception.
 * 
 * @author brad.wu
 * @since 0.0.1
 * @version 0.0.1
 */
public class ElasticDocumentValidationException extends RuntimeException implements ErrorCodes {
	private static final long serialVersionUID = -6747881438852023781L;
	private String code = null;

	public ElasticDocumentValidationException(String code) {
		this(code, null, null);
	}

	public ElasticDocumentValidationException(String code, String message, Throwable cause) {
		super(message, cause);
		this.setCode(code);
	}

	public ElasticDocumentValidationException(String code, String message) {
		this(code, message, null);
	}

	public ElasticDocumentValidationException(String code, Throwable cause) {
		this(code, null, cause);
	}

	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param code
	 *            the code to set
	 */
	private void setCode(String code) {
		this.code = code;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Throwable#toString()
	 */
	@Override
	public String toString() {
		String s = getClass().getName();
		String message = getLocalizedMessage();
		return (message != null) ? (s + ": " + code + ": " + message) : s;
	}
}
