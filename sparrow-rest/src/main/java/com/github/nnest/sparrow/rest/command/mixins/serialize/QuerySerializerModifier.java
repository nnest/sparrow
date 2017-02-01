/**
 * 
 */
package com.github.nnest.sparrow.rest.command.mixins.serialize;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.BeanPropertyWriter;
import com.fasterxml.jackson.databind.ser.BeanSerializerModifier;
import com.github.nnest.sparrow.command.document.geo.Coordinate;
import com.github.nnest.sparrow.command.document.query.Example;
import com.github.nnest.sparrow.command.document.query.attrs.score.DecayFunction;
import com.github.nnest.sparrow.command.document.query.attrs.score.FieldValueFactorFunction;
import com.github.nnest.sparrow.command.document.query.attrs.score.NestedScoreFunction;
import com.github.nnest.sparrow.command.document.query.attrs.score.RandomScoreFunction;
import com.github.nnest.sparrow.command.document.query.attrs.score.ScoreFunction;
import com.github.nnest.sparrow.command.document.query.attrs.score.ScriptScoreFunction;
import com.github.nnest.sparrow.command.document.query.attrs.score.WeightFunction;
import com.github.nnest.sparrow.command.document.query.attrs.shouldmatch.MinimumShouldMatch;
import com.github.nnest.sparrow.command.document.query.compound.Bool;
import com.github.nnest.sparrow.command.document.query.compound.Boosting;
import com.github.nnest.sparrow.command.document.query.compound.ConstantScore;
import com.github.nnest.sparrow.command.document.query.compound.DisMax;
import com.github.nnest.sparrow.command.document.query.compound.FunctionScore;
import com.github.nnest.sparrow.command.document.query.fulltext.CommonTerms;
import com.github.nnest.sparrow.command.document.query.joining.AbstractExampleJoiningQuery;
import com.github.nnest.sparrow.command.document.query.term.Range;
import com.github.nnest.sparrow.command.document.query.term.Terms;
import com.github.nnest.sparrow.command.document.query.term.TermsLookupExternal;
import com.github.nnest.sparrow.command.document.query.term.TermsLookupExternal.ExternalDocumentTerm;
import com.github.nnest.sparrow.command.document.sort.Sort;
import com.github.nnest.sparrow.command.document.sort.SortBy;
import com.github.nnest.sparrow.command.document.sort.SortByField;
import com.github.nnest.sparrow.command.document.sort.SortByGeoDistance;
import com.github.nnest.sparrow.command.document.sort.SortByScript;
import com.github.nnest.sparrow.rest.command.RestCommandUtil;
import com.github.nnest.sparrow.rest.command.mixins.wrapper.CommonTermsWrapper.WrappedCommonTerms;
import com.github.nnest.sparrow.rest.command.mixins.wrapper.SingleMatchWrapper.WrappedSingleMatch;
import com.github.nnest.sparrow.rest.command.mixins.wrapper.TermLevelQueryWrapper.WrappedTermLevelQuery;
import com.google.common.base.Predicate;
import com.google.common.base.Strings;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

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
		this.withCompoundQuery(beanDesc, beanProperties);
		// function score
		this.withFunctionScore(beanDesc, beanProperties);
		this.withNestedScoreFunction(beanDesc, beanProperties);

		// joining query
		this.withExampleJoiningQuery(beanDesc, beanProperties);

		// sort
		this.withSort(beanDesc, beanProperties);

		return super.changeProperties(config, beanDesc, beanProperties);
	}

	/**
	 * with sort
	 * 
	 * @param beanDesc
	 *            bean description
	 * @param beanProperties
	 *            bean properties
	 */
	protected void withSort(BeanDescription beanDesc, List<BeanPropertyWriter> beanProperties) {
		this.withNestedExampleThings(beanDesc, beanProperties, Lists.newArrayList(
				new NestedExampleThing(Sort.class, "nested_filter", "nested_filter", new NestedExampleVisitor() {
					/**
					 * (non-Javadoc)
					 * 
					 * @see com.github.nnest.sparrow.rest.command.mixins.serialize.QuerySerializerModifier.NestedExampleVisitor#get(java.lang.Object)
					 */
					public Object get(Object bean) {
						return ((Sort) bean).getNestedFilter();
					}
				})));

		if (!Sort.class.isAssignableFrom(beanDesc.getBeanClass())) {
			return;
		}
		for (int index = 0, count = beanProperties.size(); index < count; index++) {
			BeanPropertyWriter property = beanProperties.get(index);
			if ("by".equals(property.getName())) {
				beanProperties.set(index, new BeanPropertyWriter(property) {
					private static final long serialVersionUID = 47887587807232352L;

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
						Sort sort = (Sort) bean;
						SortBy by = sort.getBy();
						if (by instanceof SortByField) {
							// write nothing
						} else if (by instanceof SortByScript) {
							SortByScript script = (SortByScript) by;
							if (script.getType() != null) {
								gen.writeStringField("type", script.getType().name().toLowerCase());
							}
							gen.writeObjectField("script", script.getScript());
						} else if (by instanceof SortByGeoDistance) {
							SortByGeoDistance geo = (SortByGeoDistance) by;
							if (geo.getUnit() != null) {
								gen.writeStringField("unit", geo.getUnit().name().toLowerCase());
							}
							if (geo.getDistanceType() != null) {
								gen.writeStringField("distance_type", geo.getDistanceType().name().toLowerCase());
							}
							gen.writeFieldName(geo.getFieldName());
							Set<Coordinate> coordinates = geo.getCoordinates();
							gen.writeStartArray();
							for (Coordinate coordinate : coordinates) {
								gen.writeStartArray();
								gen.writeNumber(coordinate.getLongitude());
								gen.writeNumber(coordinate.getLatitude());
								gen.writeEndArray();
							}
							gen.writeEndArray();
						} else {
							throw new IllegalArgumentException(
									String.format("Sort by[%1$s] is unsupported", by.getClass()));
						}
					}
				});
			}
		}
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
	 * with compound query
	 * 
	 * @param beanDesc
	 *            bean description
	 * @param beanProperties
	 *            bean properties
	 */
	protected void withCompoundQuery(BeanDescription beanDesc, List<BeanPropertyWriter> beanProperties) {
		// constant score
		this.withNestedExampleThings(beanDesc, beanProperties, Lists.newArrayList( //
				new NestedExampleThing(ConstantScore.class, "example", "filter", new NestedExampleVisitor() {
					/**
					 * (non-Javadoc)
					 * 
					 * @see com.github.nnest.sparrow.rest.command.mixins.serialize.QuerySerializerModifier.NestedExampleVisitor#get(java.lang.Object)
					 */
					public Object get(Object bean) {
						return ((ConstantScore) bean).getExample();
					}
				}), new NestedExampleThing(Boosting.class, "negative", "negative", new NestedExampleVisitor() {
					/**
					 * (non-Javadoc)
					 * 
					 * @see com.github.nnest.sparrow.rest.command.mixins.serialize.QuerySerializerModifier.NestedExampleVisitor#get(java.lang.Object)
					 */
					public Object get(Object bean) {
						return ((Boosting) bean).getNegative();
					}
				}), new NestedExampleThing(Boosting.class, "positive", "positive", new NestedExampleVisitor() {
					/**
					 * (non-Javadoc)
					 * 
					 * @see com.github.nnest.sparrow.rest.command.mixins.serialize.QuerySerializerModifier.NestedExampleVisitor#get(java.lang.Object)
					 */
					public Object get(Object bean) {
						return ((Boosting) bean).getPositive();
					}
				}), new NestedExampleThing(Bool.class, "must", "must", new NestedExampleVisitor() {
					/**
					 * (non-Javadoc)
					 * 
					 * @see com.github.nnest.sparrow.rest.command.mixins.serialize.QuerySerializerModifier.NestedExampleVisitor#get(java.lang.Object)
					 */
					public Object get(Object bean) {
						return ((Bool) bean).getMust();
					}
				}), new NestedExampleThing(Bool.class, "must_not", "must_not", new NestedExampleVisitor() {
					/**
					 * (non-Javadoc)
					 * 
					 * @see com.github.nnest.sparrow.rest.command.mixins.serialize.QuerySerializerModifier.NestedExampleVisitor#get(java.lang.Object)
					 */
					public Object get(Object bean) {
						return ((Bool) bean).getMustNot();
					}
				}), new NestedExampleThing(Bool.class, "should", "should", new NestedExampleVisitor() {
					/**
					 * (non-Javadoc)
					 * 
					 * @see com.github.nnest.sparrow.rest.command.mixins.serialize.QuerySerializerModifier.NestedExampleVisitor#get(java.lang.Object)
					 */
					public Object get(Object bean) {
						return ((Bool) bean).getShould();
					}
				}), new NestedExampleThing(Bool.class, "filter", "filter", new NestedExampleVisitor() {
					/**
					 * (non-Javadoc)
					 * 
					 * @see com.github.nnest.sparrow.rest.command.mixins.serialize.QuerySerializerModifier.NestedExampleVisitor#get(java.lang.Object)
					 */
					public Object get(Object bean) {
						return ((Bool) bean).getFilter();
					}
				}), new NestedExampleThing(DisMax.class, "examples", "queries", new NestedExampleVisitor() {
					/**
					 * (non-Javadoc)
					 * 
					 * @see com.github.nnest.sparrow.rest.command.mixins.serialize.QuerySerializerModifier.NestedExampleVisitor#get(java.lang.Object)
					 */
					public Object get(Object bean) {
						return ((DisMax) bean).getExamples();
					}
				}), new NestedExampleThing(FunctionScore.class, "example", "query", new NestedExampleVisitor() {
					/**
					 * (non-Javadoc)
					 * 
					 * @see com.github.nnest.sparrow.rest.command.mixins.serialize.QuerySerializerModifier.NestedExampleVisitor#get(java.lang.Object)
					 */
					public Object get(Object bean) {
						return ((FunctionScore) bean).getExample();
					}
				}) //
		));
	}

	/**
	 * with function score
	 * 
	 * @param beanDesc
	 *            bean description
	 * @param beanProperties
	 *            bean properties
	 */
	protected void withFunctionScore(BeanDescription beanDesc, List<BeanPropertyWriter> beanProperties) {
		if (!FunctionScore.class.isAssignableFrom(beanDesc.getBeanClass())) {
			return;
		}

		for (int index = 0, count = beanProperties.size(); index < count; index++) {
			BeanPropertyWriter property = beanProperties.get(index);
			if ("function".equals(property.getName())) {
				beanProperties.set(index, //
						new BeanPropertyWriter(property) {
							private static final long serialVersionUID = 5278959579760436028L;

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
								writeScoreFunctionAsField(((FunctionScore) bean).getFunction(), gen, prov);
							}
						});
			}
		}
	}

	/**
	 * with compound query
	 * 
	 * @param beanDesc
	 *            bean description
	 * @param beanProperties
	 *            bean properties
	 */
	protected void withNestedScoreFunction(BeanDescription beanDesc, List<BeanPropertyWriter> beanProperties) {
		new NestedExampleThing(NestedScoreFunction.class, "examples", "filter", new NestedExampleVisitor() {
			/**
			 * (non-Javadoc)
			 * 
			 * @see com.github.nnest.sparrow.rest.command.mixins.serialize.QuerySerializerModifier.NestedExampleVisitor#get(java.lang.Object)
			 */
			public Object get(Object bean) {
				return ((NestedScoreFunction) bean).getExamples();
			}
		}).withThing(beanDesc, beanProperties);

		if (!NestedScoreFunction.class.isAssignableFrom(beanDesc.getBeanClass())) {
			return;
		}

		for (int index = 0, count = beanProperties.size(); index < count; index++) {
			BeanPropertyWriter property = beanProperties.get(index);
			if ("functions".equals(property.getName())) {
				beanProperties.set(index, //
						new BeanPropertyWriter(property) {
							private static final long serialVersionUID = 686705320310582411L;

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
								for (ScoreFunction func : ((NestedScoreFunction) bean).getFunctions()) {
									writeScoreFunctionAsField(func, gen, prov);
								}
							}
						});
			}
		}
	}

	/**
	 * write score function as a field
	 * 
	 * @param func
	 *            score function
	 * @param gen
	 *            generator
	 * @param prov
	 *            provider
	 * @throws Exception
	 *             exception
	 */
	protected void writeScoreFunctionAsField(ScoreFunction func, JsonGenerator gen, SerializerProvider prov)
			throws Exception {
		if (func instanceof WeightFunction) {
			gen.writeNumberField("weight", ((WeightFunction) func).getValue());
		} else if (func instanceof RandomScoreFunction) {
			gen.writeObjectField("random_score", func);
		} else if (func instanceof FieldValueFactorFunction) {
			gen.writeObjectField("field_value_factor", func);
		} else if (func instanceof ScriptScoreFunction) {
			gen.writeObjectField("script_score", func);
		} else if (func instanceof DecayFunction) {
			DecayFunction decay = (DecayFunction) func;
			gen.writeFieldName(decay.getType().name().toLowerCase());
			gen.writeStartObject();
			gen.writeObjectField(decay.getFieldName(), decay);
			gen.writeEndObject();
		}
	}

	/**
	 * with example joining query
	 * 
	 * @param beanDesc
	 *            bean description
	 * @param beanProperties
	 *            bean properties
	 */
	protected void withExampleJoiningQuery(BeanDescription beanDesc, List<BeanPropertyWriter> beanProperties) {
		this.withNestedExampleThings(beanDesc, beanProperties,
				Lists.newArrayList(new NestedExampleThing(AbstractExampleJoiningQuery.class, "example", "query",
						new NestedExampleVisitor() {
							/**
							 * (non-Javadoc)
							 * 
							 * @see com.github.nnest.sparrow.rest.command.mixins.serialize.QuerySerializerModifier.NestedExampleVisitor#get(java.lang.Object)
							 */
							@SuppressWarnings("rawtypes")
							public Object get(Object bean) {
								return ((AbstractExampleJoiningQuery) bean).getExample();
							}
						})));
	}

	/**
	 * with nested example things
	 * 
	 * @param beanDesc
	 *            bean description
	 * @param beanProperties
	 *            bean properties
	 * @param things
	 *            things
	 */
	protected void withNestedExampleThings(BeanDescription beanDesc, List<BeanPropertyWriter> beanProperties,
			List<NestedExampleThing> things) {
		Iterables.all(things, new Predicate<NestedExampleThing>() {
			/**
			 * (non-Javadoc)
			 * 
			 * @see com.google.common.base.Predicate#apply(java.lang.Object)
			 */
			@Override
			public boolean apply(NestedExampleThing input) {
				input.withThing(beanDesc, beanProperties);
				return true;
			}
		});
	}

	/**
	 * nested example thing
	 * 
	 * @author brad.wu
	 * @since 0.0.1
	 * @version 0.0.1
	 */
	public static class NestedExampleThing {
		private Class<?> thingClass = null;
		// use name after jackson transformed
		private String propertyName = null;
		// the property name in final JSON
		private String targetPropertyName = null;
		private NestedExampleVisitor nestedExampleVisitor = null;

		public NestedExampleThing(Class<?> thingClass, String propertyName, String targetPropertyName,
				NestedExampleVisitor nestedExampleVisitor) {
			super();
			this.thingClass = thingClass;
			this.propertyName = propertyName;
			this.targetPropertyName = targetPropertyName;
			this.nestedExampleVisitor = nestedExampleVisitor;
		}

		/**
		 * @return the thingClass
		 */
		public Class<?> getThingClass() {
			return thingClass;
		}

		/**
		 * @return the propertyName
		 */
		public String getPropertyName() {
			return propertyName;
		}

		/**
		 * @return the targetPropertyName
		 */
		public String getTargetPropertyName() {
			return targetPropertyName;
		}

		/**
		 * @return the nestedExampleVisitor
		 */
		public NestedExampleVisitor getNestedExampleVisitor() {
			return nestedExampleVisitor;
		}

		/**
		 * with thing
		 * 
		 * @param beanDesc
		 *            bean description
		 * @param beanProperties
		 *            bean properties
		 */
		public void withThing(BeanDescription beanDesc, List<BeanPropertyWriter> beanProperties) {
			if (!this.getThingClass().isAssignableFrom(beanDesc.getBeanClass())) {
				return;
			}

			for (int index = 0, count = beanProperties.size(); index < count; index++) {
				BeanPropertyWriter property = beanProperties.get(index);
				if (this.getPropertyName().equals(property.getName())) {
					beanProperties.set(index, //
							new DefaultNestedExampleBeanPropertyWriter(property, //
									this.getTargetPropertyName(), //
									this.getNestedExampleVisitor()) //
					);
				}
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
		@SuppressWarnings("rawtypes")
		@Override
		public void serializeAsField(Object bean, JsonGenerator gen, SerializerProvider prov) throws Exception {
			Object nestedExample = this.getNestedExample(bean);
			if (nestedExample == null) {
				return;
			}

			gen.writeFieldName(this.getFieldName());
			if (nestedExample instanceof Example) {
				// a single example
				this.writeExample(gen, (Example) nestedExample);
			} else if (nestedExample instanceof Iterable) {
				// an example iterable
				gen.writeStartArray();
				Iterator nestedExamples = ((Iterable) nestedExample).iterator();
				while (nestedExamples.hasNext()) {
					this.writeExample(gen, (Example) nestedExamples.next());
				}
				gen.writeEndArray();
			} else if (nestedExample.getClass().isArray()) {
				gen.writeStartArray();
				Object[] examples = (Object[]) nestedExample;
				for (Object example : examples) {
					this.writeExample(gen, (Example) example);
				}
				gen.writeEndArray();
			}
		}

		protected void writeExample(JsonGenerator gen, Example example) throws IOException {
			gen.writeStartObject();
			Example wrappedExample = RestCommandUtil.wrapExample(example);
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
		protected abstract Object getNestedExample(Object bean);
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
		protected Object getNestedExample(Object bean) {
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
		Object get(Object bean);
	}
}
