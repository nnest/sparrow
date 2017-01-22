/**
 * 
 */
package com.github.nnest.sparrow.command.document.query.compound;

import java.math.BigDecimal;

import com.github.nnest.sparrow.command.document.query.Example;

/**
 * compound query.<br>
 * {@code IndicesQuery} is deprecated, not implemented.
 * 
 * @author brad.wu
 * @since 0.0.1
 * @version 0.0.1
 */
public interface CompoundQuery<T extends CompoundQuery<T>> extends Example {
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
