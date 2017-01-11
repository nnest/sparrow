/**
 * 
 */
package com.github.nnest.sparrow.rest;

import java.util.List;

import com.github.nnest.sparrow.ElasticCommandKind;

/**
 * rest command index create only,
 * {@linkplain ElasticCommandKind#INDEX_CREATE_ONLY}
 * 
 * @author brad.wu
 * @since 0.0.1
 * @version 0.0.1
 */
public class RestCommandIndexCreateOnly extends RestCommandIndex {
	/**
	 * (non-Javadoc)
	 * 
	 * @see com.github.nnest.sparrow.rest.RestCommandIndex#moreParts(java.util.List)
	 */
	@Override
	protected void moreParts(List<String> parts) {
		parts.add("_create");
	}
}
