/**
 * 
 */
package com.github.nnest.sparrow.rest.command.mixins.serialize;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.github.nnest.sparrow.command.document.query.attrs.rewrite.Rewrite;

/**
 * rewrite serializer
 * 
 * @author brad.wu
 * @since 0.0.1
 * @version 0.0.1
 */
public class RewriteSerializer extends JsonSerializer<Rewrite> {
	/**
	 * (non-Javadoc)
	 * 
	 * @see com.fasterxml.jackson.databind.JsonSerializer#serialize(java.lang.Object,
	 *      com.fasterxml.jackson.core.JsonGenerator,
	 *      com.fasterxml.jackson.databind.SerializerProvider)
	 */
	@Override
	public void serialize(Rewrite value, JsonGenerator gen, SerializerProvider serializers)
			throws IOException, JsonProcessingException {
		gen.writeString(value.asString());
	}
}
