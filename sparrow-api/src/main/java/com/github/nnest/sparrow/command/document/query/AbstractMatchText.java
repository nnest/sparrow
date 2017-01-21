/**
 * 
 */
package com.github.nnest.sparrow.command.document.query;

/**
 * abstract match text
 * 
 * @author brad.wu
 * @since 0.0.1
 * @version 0.0.1
 */
public abstract class AbstractMatchText<T extends AbstractMatchText<T>> extends AbstractFullTextQuery<T>
		implements MatchText<T> {
	private Integer slop = null;

	public AbstractMatchText(String exampleText) {
		super(exampleText);
	}

	/**
	 * @return the slop
	 */
	public Integer getSlop() {
		return slop;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.github.nnest.sparrow.command.document.query.MatchText#withSlop(java.lang.Integer)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public T withSlop(Integer slop) {
		assert slop != null && slop > 0 : "Slop cannot be null, and must be positive.";

		this.slop = slop;
		return (T) this;
	}
}
