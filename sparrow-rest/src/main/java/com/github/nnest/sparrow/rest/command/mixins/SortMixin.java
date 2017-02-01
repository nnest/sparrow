/**
 * 
 */
package com.github.nnest.sparrow.rest.command.mixins;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.As;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;
import com.fasterxml.jackson.databind.PropertyNamingStrategy.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.annotation.JsonTypeIdResolver;
import com.github.nnest.sparrow.command.document.query.Example;
import com.github.nnest.sparrow.command.document.sort.Sort;
import com.github.nnest.sparrow.command.document.sort.SortBy;
import com.github.nnest.sparrow.command.document.sort.SortMode;
import com.github.nnest.sparrow.command.document.sort.SortOrder;
import com.github.nnest.sparrow.command.document.type.DataType;
import com.github.nnest.sparrow.rest.command.mixins.serialize.DataTypeSerializer;
import com.github.nnest.sparrow.rest.command.mixins.serialize.ExampleTypeAsIdResolver;
import com.github.nnest.sparrow.rest.command.mixins.serialize.SortByAsIdResolver;
import com.github.nnest.sparrow.rest.command.mixins.serialize.SortModeSerializer;
import com.github.nnest.sparrow.rest.command.mixins.serialize.SortOrderSerializer;

/**
 * sort mixin, see {@linkplain Sort}
 * 
 * @author brad.wu
 * @since 0.0.1
 * @version 0.0.1
 */
@JsonInclude(Include.NON_NULL)
@JsonNaming(SnakeCaseStrategy.class)
@JsonTypeInfo(include = As.WRAPPER_OBJECT, use = Id.CUSTOM)
@JsonTypeIdResolver(SortByAsIdResolver.class)
public interface SortMixin {
	/**
	 * @return the by
	 */
	@JsonProperty
	SortBy getBy();

	/**
	 * get order
	 * 
	 * @return order
	 */
	@JsonSerialize(using = SortOrderSerializer.class)
	SortOrder getOrder();

	/**
	 * get mode
	 * 
	 * @return mode
	 */
	@JsonSerialize(using = SortModeSerializer.class)
	SortMode getMode();

	/**
	 * get nested filter
	 * 
	 * @return nest filter
	 */
	@JsonTypeInfo(include = As.WRAPPER_OBJECT, use = Id.CUSTOM)
	@JsonTypeIdResolver(ExampleTypeAsIdResolver.class)
	Example getNestedFilter();

	/**
	 * @return the missingValue
	 */
	@JsonProperty("missing")
	String getMissingValue();

	/**
	 * @return the unmappedType
	 */
	@JsonSerialize(using = DataTypeSerializer.class)
	DataType getUnmappedType();
}
