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
	 * @return document class
	 */
	Class<?> getDocumentClass();

	/**
	 * get index name
	 * 
	 * @return index name
	 */
	String getIndex();

	/**
	 * get type name
	 * 
	 * @return type name
	 */
	String getType();

	/**
	 * get id field
	 * 
	 * @return id field name
	 */
	String getIdField();

	/**
	 * get version field
	 * 
	 * @return version field name
	 */
	String getVersionField();

	/**
	 * get fields. id field is not included, visit id field via
	 * {@linkplain #getIdField()}, never returns null.
	 * 
	 * @return field set
	 */
	Set<String> getFields();
}
