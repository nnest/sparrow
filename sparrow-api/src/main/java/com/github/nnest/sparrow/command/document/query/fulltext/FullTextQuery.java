/**
 * 
 */
package com.github.nnest.sparrow.command.document.query.fulltext;

import java.math.BigDecimal;

import com.github.nnest.sparrow.command.document.query.Example;

/**
 * full text query
 * 
 * @author brad.wu
 * @since 0.0.1
 * @version 0.0.1
 */
public interface FullTextQuery<T extends FullTextQuery<T>> extends Example {
	/**
	 * get analyzer name
	 * 
	 * @return analyzer name
	 */
	String getAnalyzerName();

	/**
	 * with analyzer name
	 * 
	 * @param analyzerName
	 *            analyzer name
	 * @return this
	 */
	T withAnalyzerName(String analyzerName);

	/**
	 * get boost
	 * 
	 * @return boost
	 */
	BigDecimal getBoost();

	/**
	 * with boost
	 * 
	 * @param boost
	 *            boost
	 * @return this
	 */
	T withBoost(BigDecimal boost);

	/**
	 * get example text
	 * 
	 * @return example text
	 */
	String getExampleText();

	/**
	 * with example text
	 * 
	 * @param exampleText
	 *            example text
	 * @return this
	 */
	T withExampleText(String exampleText);
}
