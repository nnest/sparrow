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
	MULTI_GET, // get particular documents by given ids
	EXIST, // check document exists or not by given id
	DELETE, // delete document by given id
	UPDATE, // update document by given data
	UPDATE_BY_SCRIPT, // update document by given script
	QUERY, // query

	// indices command
	DROP_INDEX, // delete index
}
