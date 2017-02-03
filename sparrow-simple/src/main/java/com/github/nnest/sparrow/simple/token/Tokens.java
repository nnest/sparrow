/**
 * 
 */
package com.github.nnest.sparrow.simple.token;

import java.util.List;

/**
 * Tokens
 * 
 * @author brad.wu
 * @since 0.0.1
 * @version 0.0.1
 */
public interface Tokens {
	/**
	 * get tokens
	 * 
	 * @return tokens
	 */
	List<Token> getTokens();

	/**
	 * get token count
	 * 
	 * @return count
	 */
	int tokenCount();
}
