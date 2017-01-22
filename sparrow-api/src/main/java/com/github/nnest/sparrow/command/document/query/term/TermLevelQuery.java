/**
 * 
 */
package com.github.nnest.sparrow.command.document.query.term;

import java.math.BigDecimal;

import com.github.nnest.sparrow.command.document.query.Example;
import com.github.nnest.sparrow.command.document.query.fulltext.Match;

/**
 * Term level query<br>
 * implementation of term level query should implement this interface. but
 * {@code FuzzyQuery}, {@code TypeQuery} and {@code IdsQuery} are not
 * implemented. since<br>
 * 1. {@code FuzzyQuery} are deprecated in 5.0.0, and use
 * {@linkplain Match#with(com.github.nnest.sparrow.command.document.query.attrs.fuzzy.Fuzziness)}
 * instead,<br>
 * 2. {@code TypeQuery} and {@code IdsQuery} seems not popular.
 * 
 * @author brad.wu
 * @since 0.0.1
 * @version 0.0.1
 */
public interface TermLevelQuery<T extends TermLevelQuery<T>> extends Example {
	/**
	 * get field name
	 * 
	 * @return field name
	 */
	String getFieldName();

	/**
	 * with field name
	 * 
	 * @param fieldName
	 *            field name
	 * @return this
	 */
	T withFieldName(String fieldName);

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
}
