/**
 * 
 */
package com.github.nnest.sparrow.command.document.query;

import java.math.BigDecimal;

/**
 * match given example text and other parameters
 * 
 * @author brad.wu
 * @since 0.0.1
 * @version 0.0.1
 */
public interface MatchText extends Example {
	/**
	 * get analyzer name
	 * 
	 * @return analyzer name
	 */
	String getAnalyzerName();

	/**
	 * get boost
	 * 
	 * @return boost
	 */
	BigDecimal getBoost();

	/**
	 * get example text
	 * 
	 * @return example text
	 */
	String getExampleText();

	/**
	 * get slop
	 * 
	 * @return slop
	 */
	Integer getSlop();

	/**
	 * get match type
	 * 
	 * @return type
	 */
	MatchType getType();
}
