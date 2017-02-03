/**
 * 
 */
package com.github.nnest.sparrow.simple.token;

/**
 * Token
 * 
 * @author brad.wu
 * @since 0.0.1
 * @version 0.0.1
 */
public interface Token {
	/**
	 * get token
	 * 
	 * @return token
	 */
	String getToken();

	/**
	 * get value from given object
	 * 
	 * @param from
	 *            object
	 * @return string value
	 */
	Object getValue(Object from);
}
