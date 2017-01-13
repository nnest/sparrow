/**
 * 
 */
package com.github.nnest.sparrow.command.document;

import com.github.nnest.sparrow.ElasticCommandKind;

/**
 * create document only
 * 
 * @author brad.wu
 * @since 0.0.1
 * @version 0.0.1
 */
public class Create extends Index {
	public Create(Object document) {
		super(document);
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.github.nnest.sparrow.command.document.Index#getCommandKind()
	 */
	@Override
	public ElasticCommandKind getCommandKind() {
		return ElasticCommandKind.CREATE;
	}
}
