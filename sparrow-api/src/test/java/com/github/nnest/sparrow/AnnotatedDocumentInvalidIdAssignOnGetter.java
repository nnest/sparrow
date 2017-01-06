/**
 * 
 */
package com.github.nnest.sparrow;

import java.util.Date;

import com.github.nnest.sparrow.annotation.ElasticDocument;
import com.github.nnest.sparrow.annotation.ElasticId;

/**
 * @author brad.wu
 * @since 0.0.1
 * @version 0.0.1
 */
@ElasticDocument(index = "index", type = "type")
public class AnnotatedDocumentInvalidIdAssignOnGetter {
	@ElasticId
	public Date getId() {
		return null;
	}
}
