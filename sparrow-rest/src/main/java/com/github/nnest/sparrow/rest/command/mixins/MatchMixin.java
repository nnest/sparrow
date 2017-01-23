/**
 * 
 */
package com.github.nnest.sparrow.rest.command.mixins;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.github.nnest.sparrow.command.document.query.ExampleType;
import com.github.nnest.sparrow.command.document.query.fulltext.Match;

/**
 * Match mixin. all methods signature are same as them in {@linkplain Match}
 * 
 * @author brad.wu
 * @since 0.0.1
 * @version 0.0.1
 * @see Match
 */
@JsonInclude(Include.NON_NULL)
@JsonNaming(SnakeCaseStrategy.class)
public interface MatchMixin {
	/**
	 * get example type
	 * 
	 * @return
	 */
	@JsonIgnore
	ExampleType getExampleType();

	/**
	 * get field name
	 * 
	 * @return
	 */
	@JsonIgnore
	String getFieldName();

	/**
	 * get example text
	 * 
	 * @return
	 */
	@JsonProperty("query")
	String getExampleText();
}
