/**
 * 
 */
package com.github.nnest.sparrow.command.document.query.fulltext;

/**
 * multiple match cross fields
 * 
 * @author brad.wu
 * @since 0.0.1
 * @version 0.0.1
 */
public class MultiMatchCrossFields extends AbstractMultiMatchText<MultiMatchCrossFields> {
	public MultiMatchCrossFields(String exampleText) {
		super(exampleText);
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.github.nnest.sparrow.command.document.query.fulltext.AbstractMatchText#getType()
	 */
	@Override
	public MatchType getType() {
		return MatchType.MULTI_CROSS_FIELDS;
	}
}
