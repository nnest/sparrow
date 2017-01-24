/**
 * 
 */
package com.github.nnest.sparrow.rest.command.mixins;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.github.nnest.sparrow.command.document.query.ExampleType;
import com.github.nnest.sparrow.command.document.query.attrs.RegexpFlag;
import com.github.nnest.sparrow.command.document.query.attrs.rewrite.Rewrite;
import com.github.nnest.sparrow.command.document.query.term.AbstractTermLevelQuery;
import com.github.nnest.sparrow.command.document.query.term.Range;
import com.github.nnest.sparrow.command.document.query.term.Regexp;
import com.github.nnest.sparrow.rest.command.mixins.serialize.RegexpFlagsSerializer;
import com.github.nnest.sparrow.rest.command.mixins.serialize.RewriteSerializer;

/**
 * term level query mixin, see {@linkplain AbstractTermLevelQuery}
 * 
 * @author brad.wu
 * @since 0.0.1
 * @version 0.0.1
 */
@JsonInclude(Include.NON_NULL)
@JsonNaming(SnakeCaseStrategy.class)
public interface TermLevelQueryMixin {
	/**
	 * get example text
	 * 
	 * @return example text
	 */
	@JsonProperty("value")
	String getExampleText();

	/**
	 * get example type
	 * 
	 * @return example type
	 */
	@JsonIgnore
	ExampleType getExampleType();

	/**
	 * get field name
	 * 
	 * @return field name
	 */
	@JsonIgnore
	String getFieldName();

	/**
	 * is max include, from {@linkplain Range}
	 * 
	 * @return max include
	 */
	@JsonIgnore
	boolean isMaxInclude();

	/**
	 * is min include, from {@linkplain Range}
	 * 
	 * @return min include
	 */
	@JsonIgnore
	boolean isMinInclude();

	/**
	 * get rewrite
	 * 
	 * @return rewrite
	 */
	@JsonProperty("rewrite")
	@JsonSerialize(using = RewriteSerializer.class)
	Rewrite getRewrite();

	/**
	 * get regexp flags, from {@linkplain Regexp}
	 * 
	 * @return regexp flags
	 */
	@JsonSerialize(using = RegexpFlagsSerializer.class)
	Set<RegexpFlag> getFlags();
}
