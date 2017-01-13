/**
 * 
 */
package com.github.nnest.sparrow;

/**
 * elastic command kind
 * 
 * @author brad.wu
 * @since 0.0.1
 * @version 0.0.1
 */
public enum ElasticCommandKind {
	// document command
	INDEX, // index document, create if not exists and update if exists
	CREATE, // create document, fail if exists
	GET, // get particular document by given id
	EXIST, // check document exists or not by given id

	// indices command
	DROP_INDEX, // delete index
}
