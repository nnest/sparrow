/**
 * 
 */
package com.github.nnest.sparrow.command.document.query.fulltext;

import com.github.nnest.sparrow.command.document.query.DefaultExampleType;
import com.github.nnest.sparrow.command.document.query.ExampleType;

/**
 * multiple match cross fields
 * 
 * @author brad.wu
 * @since 0.0.1
 * @version 0.0.1
 */
public class MultiMatchCrossFields extends AbstractMultiMatch<MultiMatchCrossFields> {
	public MultiMatchCrossFields(String exampleText) {
		super(exampleText);
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.github.nnest.sparrow.command.document.query.Example#getExampleType()
	 */
	@Override
	public ExampleType getExampleType() {
		return DefaultExampleType.MULTI_CROSS_FIELDS;
	}
}
