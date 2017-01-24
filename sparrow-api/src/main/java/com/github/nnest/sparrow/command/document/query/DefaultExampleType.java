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
	MULTI_BEST_FIELDS("MULTI_MATCH", "BEST_FIELDS"), //
	MULTI_MOST_FIELDS("MULTI_MATCH", "MOST_FIELDS"), //
	MULTI_CROSS_FIELDS("MULTI_MATCH", "CROSS_FIELDS"), //
	MULTI_PHRASE("MULTI_MATCH", "PHRASE"), //
	MULTI_PHRASE_PREFIX("MULTI_MATCH", "PHRASE_PREFIX"), //
	COMMON_TERMS("COMMON"), //
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
	private String type = null;

	private DefaultExampleType() {
	}

	private DefaultExampleType(String name) {
		this.name = name;
	}

	private DefaultExampleType(String name, String type) {
		this.name = name;
		this.type = type;
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

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.github.nnest.sparrow.command.document.query.ExampleType#getType()
	 */
	@Override
	public String getType() {
		return type == null ? this.getName() : this.type;
	}
}
