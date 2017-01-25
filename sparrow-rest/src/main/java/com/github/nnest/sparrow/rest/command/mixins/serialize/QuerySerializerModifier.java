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
import com.github.nnest.sparrow.command.document.query.Example;
import com.github.nnest.sparrow.command.document.query.attrs.shouldmatch.MinimumShouldMatch;
import com.github.nnest.sparrow.command.document.query.compound.ConstantScore;
import com.github.nnest.sparrow.command.document.query.fulltext.CommonTerms;
import com.github.nnest.sparrow.command.document.query.term.Range;
import com.github.nnest.sparrow.command.document.query.term.Terms;
import com.github.nnest.sparrow.command.document.query.term.TermsLookupExternal;
import com.github.nnest.sparrow.command.document.query.term.TermsLookupExternal.ExternalDocumentTerm;
import com.github.nnest.sparrow.rest.command.RestCommandUtil;
import com.github.nnest.sparrow.rest.command.mixins.wrapper.CommonTermsWrapper.WrappedCommonTerms;
import com.github.nnest.sparrow.rest.command.mixins.wrapper.SingleMatchWrapper.WrappedSingleMatch;
import com.github.nnest.sparrow.rest.command.mixins.wrapper.TermLevelQueryWrapper.WrappedTermLevelQuery;
import com.google.common.base.Strings;

/**
 * query serialzier modifier
 * 
 * @author brad.wu
 * @since 0.0.1
 * @version 0.0.1
 */
public class QuerySerializerModifier extends BeanSerializerModifier {
	/**
	 * (non-Javadoc)
	 * 
	 * @see com.fasterxml.jackson.databind.ser.BeanSerializerModifier#changeProperties(com.fasterxml.jackson.databind.SerializationConfig,
	 *      com.fasterxml.jackson.databind.BeanDescription, java.util.List)
	 */
	@Override
	public List<BeanPropertyWriter> changeProperties(SerializationConfig config, BeanDescription beanDesc,
			List<BeanPropertyWriter> beanProperties) {
		// full text query
		// single match
		this.withWrappedSingleMatch(beanDesc, beanProperties);
		// common terms
		this.withCommonTerms(beanDesc, beanProperties);
		this.withWrappedCommonTerms(beanDesc, beanProperties);

		// term level query
		this.withWrappedTermLevelQuery(beanDesc, beanProperties);
		// range
		this.withRange(beanDesc, beanProperties);
		// terms
		this.withTerms(beanDesc, beanProperties);
		// terms lookup external
		this.withTermsLookupExternal(beanDesc, beanProperties);

		// compound query
		this.withConstantScore(beanDesc, beanProperties);

		return super.changeProperties(config, beanDesc, beanProperties);
	}

	/**
	 * with wrapped single match
	 * 
	 * @param beanDesc
	 *            bean description
	 * @param beanProperties
	 *            bean properties
	 */
	protected void withWrappedSingleMatch(BeanDescription beanDesc, List<BeanPropertyWriter> beanProperties) {
		if (!WrappedSingleMatch.class.isAssignableFrom(beanDesc.getBeanClass())) {
			return;
		}

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

	/**
	 * with wrapped term
	 * 
	 * @param beanDesc
	 *            bean description
	 * @param beanProperties
	 *            bean properties
	 */
	protected void withWrappedTermLevelQuery(BeanDescription beanDesc, List<BeanPropertyWriter> beanProperties) {
		if (!WrappedTermLevelQuery.class.isAssignableFrom(beanDesc.getBeanClass())) {
			return;
		}

		for (int index = 0, count = beanProperties.size(); index < count; index++) {
			BeanPropertyWriter property = beanProperties.get(index);
			if ("term".equals(property.getName())) {
				beanProperties.set(index, new BeanPropertyWriter(property) {
					private static final long serialVersionUID = -1337355152019983272L;

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
						WrappedTermLevelQuery term = (WrappedTermLevelQuery) bean;
						gen.writeFieldName(term.getTerm().getFieldName());
						gen.writeObject(term.getTerm());
					}
				});
			}
		}
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

	/**
	 * with terms
	 * 
	 * @param beanDesc
	 *            bean description
	 * @param beanProperties
	 *            bean properties
	 */
	protected void withTerms(BeanDescription beanDesc, List<BeanPropertyWriter> beanProperties) {
		if (!Terms.class.isAssignableFrom(beanDesc.getBeanClass())) {
			return;
		}

		for (int index = 0, count = beanProperties.size(); index < count; index++) {
			BeanPropertyWriter property = beanProperties.get(index);
			if ("field_name".equals(property.getName())) {
				beanProperties.set(index, new BeanPropertyWriter(property) {
					private static final long serialVersionUID = -1337355152019983272L;

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
						Terms terms = (Terms) bean;
						gen.writeFieldName(terms.getFieldName());
						gen.writeStartArray();
						for (String exampleText : terms.getExampleTexts()) {
							gen.writeString(exampleText);
						}
						gen.writeEndArray();
					}
				});
			}
		}
	}

	/**
	 * with terms lookup external
	 * 
	 * @param beanDesc
	 *            bean description
	 * @param beanProperties
	 *            bean properties
	 */
	protected void withTermsLookupExternal(BeanDescription beanDesc, List<BeanPropertyWriter> beanProperties) {
		if (!TermsLookupExternal.class.isAssignableFrom(beanDesc.getBeanClass())) {
			return;
		}

		for (int index = 0, count = beanProperties.size(); index < count; index++) {
			BeanPropertyWriter property = beanProperties.get(index);
			if ("field_name".equals(property.getName())) {
				beanProperties.set(index, new BeanPropertyWriter(property) {
					private static final long serialVersionUID = -2051519030274728285L;

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
						TermsLookupExternal terms = (TermsLookupExternal) bean;
						gen.writeFieldName(terms.getFieldName());
						gen.writeStartArray();
						for (ExternalDocumentTerm externalDocument : terms.getTerms()) {
							gen.writeObject(externalDocument);
						}
						gen.writeEndArray();
					}
				});
			}
		}
	}

	/**
	 * with range
	 * 
	 * @param beanDesc
	 *            bean description
	 * @param beanProperties
	 *            bean properties
	 */
	protected void withRange(BeanDescription beanDesc, List<BeanPropertyWriter> beanProperties) {
		if (!Range.class.isAssignableFrom(beanDesc.getBeanClass())) {
			return;
		}

		for (int index = 0, count = beanProperties.size(); index < count; index++) {
			BeanPropertyWriter property = beanProperties.get(index);
			if ("max".equals(property.getName())) {
				beanProperties.set(index, new BeanPropertyWriter(property) {
					private static final long serialVersionUID = 6716659206362127484L;

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
						Range range = (Range) bean;
						gen.writeStringField(range.isMaxInclude() ? "lte" : "lt", range.getMax());
					}
				});
			} else if ("min".equals(property.getName())) {
				beanProperties.set(index, new BeanPropertyWriter(property) {
					private static final long serialVersionUID = 192617909820749901L;

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
						Range range = (Range) bean;
						gen.writeStringField(range.isMaxInclude() ? "gte" : "gt", range.getMin());
					}
				});
			}
		}
	}

	/**
	 * with constant score
	 * 
	 * @param beanDesc
	 *            bean description
	 * @param beanProperties
	 *            bean properties
	 */
	protected void withConstantScore(BeanDescription beanDesc, List<BeanPropertyWriter> beanProperties) {
		if (!ConstantScore.class.isAssignableFrom(beanDesc.getBeanClass())) {
			return;
		}

		for (int index = 0, count = beanProperties.size(); index < count; index++) {
			BeanPropertyWriter property = beanProperties.get(index);
			if ("example".equals(property.getName())) {
				beanProperties.set(index,
						new DefaultNestedExampleBeanPropertyWriter(property, "filter", new NestedExampleVisitor() {
							public Example get(Object bean) {
								return ((ConstantScore) bean).getExample();
							}
						}));
			}
		}
	}

	/**
	 * abstract nested example bean property writer
	 * 
	 * @author brad.wu
	 * @since 0.0.1
	 * @version 0.0.1
	 */
	public static abstract class AbstractNestedExampleBeanPropertyWriter extends BeanPropertyWriter {
		private static final long serialVersionUID = -7502094720805148977L;

		public AbstractNestedExampleBeanPropertyWriter(BeanPropertyWriter propertyWriter) {
			super(propertyWriter);
		}

		/**
		 * (non-Javadoc)
		 * 
		 * @see com.fasterxml.jackson.databind.ser.BeanPropertyWriter#serializeAsField(java.lang.Object,
		 *      com.fasterxml.jackson.core.JsonGenerator,
		 *      com.fasterxml.jackson.databind.SerializerProvider)
		 */
		@Override
		public void serializeAsField(Object bean, JsonGenerator gen, SerializerProvider prov) throws Exception {
			gen.writeFieldName(this.getFieldName());
			gen.writeStartObject();
			Example wrappedExample = RestCommandUtil.wrapExample(this.getNestedExample(bean));
			gen.writeObjectField(wrappedExample.getExampleType().getType().toLowerCase(), wrappedExample);
			gen.writeEndObject();
		}

		/**
		 * get field name
		 * 
		 * @return field name
		 */
		protected abstract String getFieldName();

		/**
		 * get nested example
		 * 
		 * @param bean
		 *            original bean
		 * @return nested example
		 */
		protected abstract Example getNestedExample(Object bean);
	}

	/**
	 * default nested example bean property writer
	 * 
	 * @author brad.wu
	 * @since 0.0.1
	 * @version 0.0.1
	 */
	public static class DefaultNestedExampleBeanPropertyWriter extends AbstractNestedExampleBeanPropertyWriter {
		private static final long serialVersionUID = 7022392025716751830L;

		private String fieldName = null;
		private NestedExampleVisitor nestedExampleVisitor = null;

		public DefaultNestedExampleBeanPropertyWriter(BeanPropertyWriter propertyWriter, String fieldName,
				NestedExampleVisitor nestedExampleVisitor) {
			super(propertyWriter);

			assert Strings.nullToEmpty(fieldName).trim().length() != 0 : "Field name cannot be null or blank.";
			assert nestedExampleVisitor != null : "Nested example visitor cannot be null.";

			this.fieldName = fieldName;
			this.nestedExampleVisitor = nestedExampleVisitor;
		}

		/**
		 * (non-Javadoc)
		 * 
		 * @see com.github.nnest.sparrow.rest.command.mixins.serialize.QuerySerializerModifier.AbstractNestedExampleBeanPropertyWriter#getFieldName()
		 */
		@Override
		protected String getFieldName() {
			return this.fieldName;
		}

		/**
		 * (non-Javadoc)
		 * 
		 * @see com.github.nnest.sparrow.rest.command.mixins.serialize.QuerySerializerModifier.AbstractNestedExampleBeanPropertyWriter#getNestedExample(java.lang.Object)
		 */
		@Override
		protected Example getNestedExample(Object bean) {
			return this.nestedExampleVisitor.get(bean);
		}
	}

	/**
	 * nested example visitor
	 * 
	 * @author brad.wu
	 * @since 0.0.1
	 * @version 0.0.1
	 */
	public static interface NestedExampleVisitor {
		/**
		 * get nested example from given bean
		 * 
		 * @param bean
		 *            bean
		 * @return nested example
		 */
		Example get(Object bean);
	}
}
