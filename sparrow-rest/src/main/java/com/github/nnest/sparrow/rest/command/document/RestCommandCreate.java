/**
 * 
 */
package com.github.nnest.sparrow.rest.command.document;

import com.github.nnest.sparrow.ElasticCommandKind;
import com.github.nnest.sparrow.command.document.Create;

/**
 * rest command index create only, {@linkplain ElasticCommandKind#CREATE}<br>
 * id auto generation is not allowed by create command
 * 
 * @author brad.wu
 * @since 0.0.1
 * @version 0.0.1
 */
public class RestCommandCreate extends RestCommandIndex<Create> {
	/**
	 * (non-Javadoc)
	 * 
	 * @see com.github.nnest.sparrow.rest.command.document.RestCommandIndex#getEndpointCommandKind()
	 */
	@Override
	protected String getEndpointCommandKind() {
		return "_create";
	}

	/**
	 * always returns false
	 * 
	 * @see com.github.nnest.sparrow.rest.command.document.RestCommandIndex#isIdAutoCreateionAllowed()
	 */
	@Override
	protected boolean isIdAutoCreateionAllowed() {
		return false;
	}
}
