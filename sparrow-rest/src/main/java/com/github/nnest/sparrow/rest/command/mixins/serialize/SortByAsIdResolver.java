/**
 * 
 */
package com.github.nnest.sparrow.rest.command.mixins.serialize;

import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;
import com.fasterxml.jackson.databind.jsontype.impl.TypeIdResolverBase;
import com.github.nnest.sparrow.command.document.sort.Sort;
import com.github.nnest.sparrow.command.document.sort.SortBy;
import com.github.nnest.sparrow.command.document.sort.SortByField;
import com.github.nnest.sparrow.command.document.sort.SortByGeoDistance;
import com.github.nnest.sparrow.command.document.sort.SortByScript;

/**
 * sort by id resolver
 * 
 * @author brad.wu
 * @since 0.0.1
 * @version 0.0.1
 */
public class SortByAsIdResolver extends TypeIdResolverBase {
	/**
	 * (non-Javadoc)
	 * 
	 * @see com.fasterxml.jackson.databind.jsontype.TypeIdResolver#idFromValue(java.lang.Object)
	 */
	@Override
	public String idFromValue(Object value) {
		return this.idFromValueAndType(value, value.getClass());
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.fasterxml.jackson.databind.jsontype.TypeIdResolver#idFromValueAndType(java.lang.Object,
	 *      java.lang.Class)
	 */
	@Override
	public String idFromValueAndType(Object value, Class<?> suggestedType) {
		if (!(value instanceof Sort)) {
			throw new IllegalArgumentException("Value must be an instance of " + Sort.class);
		}
		SortBy by = ((Sort) value).getBy();
		if (by instanceof SortByField) {
			return ((SortByField) by).getFieldName();
		} else if (by instanceof SortByScript) {
			return "_script";
		} else if (by instanceof SortByGeoDistance) {
			return "_geo_distance";
		} else {
			throw new IllegalArgumentException(String.format("Sort by[%1$s] is unsupported", by.getClass()));
		}
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.fasterxml.jackson.databind.jsontype.TypeIdResolver#getMechanism()
	 */
	@Override
	public Id getMechanism() {
		return Id.CUSTOM;
	}
}
