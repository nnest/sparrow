/**
 * 
 */
package com.github.nnest.sparrow.command.document.query.joining;

import com.github.nnest.sparrow.command.document.query.Example;

/**
 * abstract joining query with example
 * 
 * @author brad.wu
 * @since 0.0.1
 * @version 0.0.1
 */
public class AbstractExampleJoiningQuery<T extends AbstractExampleJoiningQuery<T>> extends AbstractJoiningQuery<T>
		implements ExampleJoiningQuery<T> {
	private Example example = null;

	public AbstractExampleJoiningQuery(Example example) {
		this.with(example);
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.github.nnest.sparrow.command.document.query.joining.ExampleJoiningQuery#getExample()
	 */
	public Example getExample() {
		return example;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.github.nnest.sparrow.command.document.query.joining.ExampleJoiningQuery#with(com.github.nnest.sparrow.command.document.query.Example)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public T with(Example example) {
		assert example != null : "Example cannot be null.";

		this.example = example;
		return (T) this;
	}
}
