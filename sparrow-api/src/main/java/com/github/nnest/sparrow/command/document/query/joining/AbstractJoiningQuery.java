/**
 * 
 */
package com.github.nnest.sparrow.command.document.query.joining;

/**
 * abstract joining query
 * 
 * @author brad.wu
 * @since 0.0.1
 * @version 0.0.1
 */
public abstract class AbstractJoiningQuery<T extends AbstractJoiningQuery<T>> implements JoiningQuery<T> {
	private Boolean ignoreUnmapped = null;

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.github.nnest.sparrow.command.document.query.joining.JoiningQuery#getIgnoreUnmapped()
	 */
	@Override
	public Boolean getIgnoreUnmapped() {
		return ignoreUnmapped;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.github.nnest.sparrow.command.document.query.joining.JoiningQuery#withIgnoreUnmapped(java.lang.Boolean)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public T withIgnoreUnmapped(Boolean ignoreUnmapped) {
		this.ignoreUnmapped = ignoreUnmapped;
		return (T) this;
	}
}
