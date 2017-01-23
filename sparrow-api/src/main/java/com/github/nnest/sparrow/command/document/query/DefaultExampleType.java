/**
 * 
 */
package com.github.nnest.sparrow.command.document.query;

/**
 * default example type
 * 
 * @author brad.wu
 * @since 0.0.1
 * @version 0.0.1
 */
public enum DefaultExampleType implements ExampleType {
	MATCH_ALL, //
	MATCH_NONE, //

	// full text
	// single
	MATCH, //
	MATCH_PHRASE, //
	MATCH_PHRASE_PREFIX, //

	// multiple
	MULTI_BEST_FIELDS("MULTI_MATCH"), //
	MULTI_MOST_FIELDS("MULTI_MATCH"), //
	MULTI_CROSS_FIELDS("MULTI_MATCH"), //
	MULTI_PHRASE("MULTI_MATCH"), //
	MULTI_PHRASE_PREFIX("MULTI_MATCH"), //
	COMMON_TERMS, //
	QUERY_STRING, //
	SIMPLE_QUERY_STRING, //

	// term
	TERM, //
	TERMS, //
	RANGE, //
	EXISTS, //
	PREFIX, //
	WILDCARD, //
	REGEXP, //

	// joining
	HAS_CHILD, //
	HAS_PARENT, //
	NESTED, //
	PARENT_ID, //

	// compound
	BOOL, //
	BOOSTING, //
	CONSTANT_SCORE, //
	DIS_MAX, //
	FUNCTION_SCORE; //

	private String name = null;

	private DefaultExampleType() {
	}

	private DefaultExampleType(String name) {
		this.name = name;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.github.nnest.sparrow.command.document.query.ExampleType#getName()
	 */
	@Override
	public String getName() {
		return name == null ? this.name() : this.name;
	}
}
