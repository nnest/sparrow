/**
 * 
 */
package com.github.nnest.sparrow.rest.command.mixins.serialize;

import java.util.List;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.BeanPropertyWriter;
import com.fasterxml.jackson.databind.ser.BeanSerializerModifier;
import com.github.nnest.sparrow.rest.command.mixins.wrapper.SingleMatchWrapper.WrappedSingleMatch;

/**
 * single match serialzier modifier
 * 
 * @author brad.wu
 * @since 0.0.1
 * @version 0.0.1
 */
public class SingleMatchSerializerModifier extends BeanSerializerModifier {
	/**
	 * (non-Javadoc)
	 * 
	 * @see com.fasterxml.jackson.databind.ser.BeanSerializerModifier#changeProperties(com.fasterxml.jackson.databind.SerializationConfig,
	 *      com.fasterxml.jackson.databind.BeanDescription, java.util.List)
	 */
	@Override
	public List<BeanPropertyWriter> changeProperties(SerializationConfig config, BeanDescription beanDesc,
			List<BeanPropertyWriter> beanProperties) {
		if (WrappedSingleMatch.class.isAssignableFrom(beanDesc.getBeanClass())) {
			for (int index = 0, count = beanProperties.size(); index < count; index++) {
				BeanPropertyWriter property = beanProperties.get(index);
				if ("match".equals(property.getName())) {
					beanProperties.set(index, new BeanPropertyWriter(property) {
						private static final long serialVersionUID = -3919261800446858573L;

						/**
						 * (non-Javadoc)
						 * 
						 * @see com.fasterxml.jackson.databind.ser.BeanPropertyWriter#serializeAsField(java.lang.Object,
						 *      com.fasterxml.jackson.core.JsonGenerator,
						 *      com.fasterxml.jackson.databind.SerializerProvider)
						 */
						@Override
						public void serializeAsField(Object bean, JsonGenerator gen, SerializerProvider prov)
								throws Exception {
							WrappedSingleMatch match = (WrappedSingleMatch) bean;
							gen.writeFieldName(match.getMatch().getFieldName());
							gen.writeObject(match.getMatch());
						}
					});
				}
			}
		}
		return super.changeProperties(config, beanDesc, beanProperties);
	}
}
