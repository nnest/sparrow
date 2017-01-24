/**
 * 
 */
package com.github.nnest.sparrow.rest.command.mixins;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.nnest.sparrow.command.document.query.term.TermLevelQueryExist;

/**
 * term query exist mixin, see {@linkplain TermLevelQueryExist}
 * 
 * @author brad.wu
 * @since 0.0.1
 * @version 0.0.1
 */
public interface QueryExistMixin {
	/**
	 * get field name
	 * 
	 * @return field name
	 */
	@JsonInclude
	@JsonProperty("field")
	String getFieldName();
}
