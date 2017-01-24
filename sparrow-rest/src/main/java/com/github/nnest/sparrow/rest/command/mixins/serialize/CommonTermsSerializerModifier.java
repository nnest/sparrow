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
import com.github.nnest.sparrow.command.document.query.attrs.shouldmatch.MinimumShouldMatch;
import com.github.nnest.sparrow.command.document.query.fulltext.CommonTerms;
import com.github.nnest.sparrow.rest.command.mixins.wrapper.CommonTermsWrapper.WrappedCommonTerms;

/**
 * single match serialzier modifier
 * 
 * @author brad.wu
 * @since 0.0.1
 * @version 0.0.1
 */
public class CommonTermsSerializerModifier extends BeanSerializerModifier {
	/**
	 * (non-Javadoc)
	 * 
	 * @see com.fasterxml.jackson.databind.ser.BeanSerializerModifier#changeProperties(com.fasterxml.jackson.databind.SerializationConfig,
	 *      com.fasterxml.jackson.databind.BeanDescription, java.util.List)
	 */
	@Override
	public List<BeanPropertyWriter> changeProperties(SerializationConfig config, BeanDescription beanDesc,
			List<BeanPropertyWriter> beanProperties) {
		this.withWrappedCommonTerms(beanDesc, beanProperties);
		this.withCommonTerms(beanDesc, beanProperties);
		return super.changeProperties(config, beanDesc, beanProperties);
	}

	/**
	 * with {@linkplain CommonTerms}
	 * 
	 * @param beanDesc
	 *            bean description
	 * @param beanProperties
	 *            bean properties
	 */
	protected void withCommonTerms(BeanDescription beanDesc, List<BeanPropertyWriter> beanProperties) {
		if (!CommonTerms.class.isAssignableFrom(beanDesc.getBeanClass())) {
			return;
		}

		for (int index = 0, count = beanProperties.size(); index < count; index++) {
			BeanPropertyWriter property = beanProperties.get(index);
			if ("high_minimum_should_match".equals(property.getName())) {
				beanProperties.set(index, new BeanPropertyWriter(property) {
					private static final long serialVersionUID = 2853469889605071769L;

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
						CommonTerms terms = (CommonTerms) bean;
						MinimumShouldMatch high = terms.getHighMinimumShouldMatch();
						MinimumShouldMatch low = terms.getLowMinimumShouldMatch();
						if (high != null && low == null) {
							gen.writeStringField("minimum_should_match", high.asString());
						} else if (low != null) {
							gen.writeFieldName("minimum_should_match");
							gen.writeStartObject();
							gen.writeStringField("low_freq", low.asString());
							if (high != null) {
								gen.writeStringField("high_freq", high.asString());
							}
							gen.writeEndObject();
						}
					}
				});
			}
		}
	}

	/**
	 * with {@linkplain WrappedCommonTerms}
	 * 
	 * @param beanDesc
	 *            bean description
	 * @param beanProperties
	 *            bean properties
	 */
	protected void withWrappedCommonTerms(BeanDescription beanDesc, List<BeanPropertyWriter> beanProperties) {
		if (!WrappedCommonTerms.class.isAssignableFrom(beanDesc.getBeanClass())) {
			return;
		}

		for (int index = 0, count = beanProperties.size(); index < count; index++) {
			BeanPropertyWriter property = beanProperties.get(index);
			if ("terms".equals(property.getName())) {
				beanProperties.set(index, new BeanPropertyWriter(property) {
					private static final long serialVersionUID = 2825024206856662517L;

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
						WrappedCommonTerms terms = (WrappedCommonTerms) bean;
						gen.writeFieldName(terms.getTerms().getFieldName());
						gen.writeObject(terms.getTerms());
					}
				});
			}
		}
	}
}
