/**
 * 
 */
package com.github.nnest.sparrow.command.document.query.term;

import com.github.nnest.sparrow.command.document.query.DefaultExampleType;
import com.github.nnest.sparrow.command.document.query.ExampleType;

/**
 * Exist
 * 
 * @author brad.wu
 * @since 0.0.1
 * @version 0.0.1
 */
public class Exist extends AbstractTermLevelQuery<Exist> {
	public Exist(String fieldName) {
		super(fieldName);
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.github.nnest.sparrow.command.document.query.Example#getExampleType()
	 */
	@Override
	public ExampleType getExampleType() {
		return DefaultExampleType.EXISTS;
	}
}
