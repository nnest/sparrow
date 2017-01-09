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
	INDEX, // index document, create if not exists and update if exists
	CREATE_INDEX // create document, fail if exists
}
