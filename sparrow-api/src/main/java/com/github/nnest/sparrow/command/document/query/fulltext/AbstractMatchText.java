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
public abstract class AbstractMatchText<T extends AbstractMatchText<T>> extends AbstractFullTextQuery<T> {
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
	 * with slop
	 * 
	 * @param slop
	 *            slop
	 * @return this
	 */
	@SuppressWarnings("unchecked")
	public T withSlop(Integer slop) {
		assert slop != null && slop > 0 : "Slop cannot be null, and must be positive.";

		this.slop = slop;
		return (T) this;
	}

	/**
	 * get match type
	 * 
	 * @return type
	 */
	public abstract MatchType getType();
}
