/**
 * 
 */
package com.github.nnest.sparrow;

import java.util.Set;

/**
 * Elastic document descriptor
 * 
 * @author brad.wu
 * @since 0.0.1
 * @version 0.0.1
 */
public interface ElasticDocumentDescriptor {
	/**
	 * get document class
	 * 
	 * @return
	 */
	Class<?> getDocumentClass();

	/**
	 * get index name
	 * 
	 * @return
	 */
	String getIndex();

	/**
	 * get type name
	 * 
	 * @return
	 */
	String getType();

	/**
	 * get id field
	 * 
	 * @return
	 */
	String getIdField();

	/**
	 * get fields. id field is not included, visit id field via
	 * {@linkplain #getIdField()}
	 * 
	 * @return
	 */
	Set<String> getFields();
}
