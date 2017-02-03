/**
 * 
 */
package com.github.nnest.sparrow.simple;

/**
 * string token
 * 
 * @author brad.wu
 * @since 0.0.1
 * @version 0.0.1
 */
public class StringToken implements Token {
	private String token = null;

	public StringToken(String token) {
		assert token != null : "Token cannot be null.";

		this.token = token;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.github.nnest.sparrow.simple.Token#getToken()
	 */
	@Override
	public String getToken() {
		return this.token;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.github.nnest.sparrow.simple.Token#getValue(java.lang.Object)
	 */
	@Override
	public String getValue(Object from) {
		return this.getToken();
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return super.toString() + " [token=" + token + "]";
	}
}
