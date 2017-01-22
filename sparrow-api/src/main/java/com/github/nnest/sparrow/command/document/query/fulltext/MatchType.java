/**
 * 
 */
package com.github.nnest.sparrow.command.document.query.fulltext;

/**
 * @author brad.wu
 * @since 0.0.1
 * @version 0.0.1
 */
public enum MatchType {
	// single
	SINGLE_MATCH, //
	SINGLE_MATCH_PHRASE, //
	SINGLE_MATCH_PHRASE_PREFIX, //

	// multiple
	MULTI_BEST_FIELDS, //
	MULTI_MOST_FIELDS, //
	MULTI_CROSS_FIELDS, //
	MULTI_PHRASE, //
	MULTI_PHRASE_PREFIX
}
