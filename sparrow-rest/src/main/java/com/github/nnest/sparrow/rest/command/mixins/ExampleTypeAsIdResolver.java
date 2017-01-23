/**
 * 
 */
package com.github.nnest.sparrow.rest.command.mixins;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;
import com.fasterxml.jackson.databind.jsontype.impl.TypeIdResolverBase;
import com.github.nnest.sparrow.command.document.query.Example;

/**
 * example type as id type id resolver. see
 * {@linkplain Example#getExampleType()}
 * 
 * @author brad.wu
 * @since 0.0.1
 * @version 0.0.1
 */
public class ExampleTypeAsIdResolver extends TypeIdResolverBase {
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
		if (value instanceof Example) {
			Example example = (Example) value;
			return example.getExampleType().getName().toLowerCase();
		}
		throw new IllegalArgumentException("Value must be an instance of " + Example.class);
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.fasterxml.jackson.databind.jsontype.TypeIdResolver#getMechanism()
	 */
	@Override
	public Id getMechanism() {
		return JsonTypeInfo.Id.CUSTOM;
	}
}
