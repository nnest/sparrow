/**
 * 
 */
package com.github.nnest.sparrow;

import com.github.nnest.sparrow.annotation.ElasticDocument;
import com.github.nnest.sparrow.annotation.ElasticId;

/**
 * @author brad.wu
 * @since 0.0.1
 * @version 0.0.1
 */
@ElasticDocument(index = "index", type = "type")
public class AnnotatedDocumentDuplicatedId {
	@ElasticId
	private Long id = null;

	@ElasticId
	public String getId1() {
		return null;
	}
}
