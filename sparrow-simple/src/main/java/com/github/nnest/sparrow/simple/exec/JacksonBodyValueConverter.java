/**
 * 
 */
package com.github.nnest.sparrow.simple.exec;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.github.nnest.sparrow.simple.TemplateParseException;

/**
 * jackson body value converter
 * 
 * @author brad.wu
 * @since 0.0.1
 * @version 0.0.1
 */
public class JacksonBodyValueConverter implements BodyValueConverter {
	private ObjectMapper objectMapper = new ObjectMapper() //
			.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.github.nnest.sparrow.simple.exec.BodyValueConverter#convert(java.lang.Object)
	 */
	@Override
	public String convert(Object value) {
		if (value == null) {
			return null;
		}

		try {
			return objectMapper.writeValueAsString(value);
		} catch (JsonProcessingException e) {
			throw new TemplateParseException("Failed to write value by jackson.", e);
		}
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.github.nnest.sparrow.simple.exec.BodyValueConverter#accept(java.lang.Object)
	 */
	@Override
	public boolean accept(Object value) {
		return true;
	}
}
