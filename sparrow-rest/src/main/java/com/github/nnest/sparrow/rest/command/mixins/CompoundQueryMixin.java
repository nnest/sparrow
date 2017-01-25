/**
 * 
 */
package com.github.nnest.sparrow.rest.command.mixins;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.github.nnest.sparrow.command.document.query.Example;
import com.github.nnest.sparrow.command.document.query.ExampleType;
import com.github.nnest.sparrow.command.document.query.compound.AbstractCompoundQuery;
import com.github.nnest.sparrow.command.document.query.compound.ConstantScore;

/**
 * compound query mixin, see {@linkplain AbstractCompoundQuery}
 * 
 * @author brad.wu
 * @since 0.0.1
 * @version 0.0.1
 */
public interface CompoundQueryMixin {
	/**
	 * get example type
	 * 
	 * @return example type
	 */
	@JsonIgnore
	ExampleType getExampleType();

	/**
	 * get example, from {@linkplain ConstantScore}
	 * 
	 * @return example
	 */
	@JsonInclude
	Example getExample();
}
