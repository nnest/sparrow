/**
 * 
 */
package com.github.nnest.sparrow.rest.command.mixins;

import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.github.nnest.sparrow.command.document.query.ExampleType;
import com.github.nnest.sparrow.command.document.query.attrs.rewrite.Rewrite;
import com.github.nnest.sparrow.command.document.query.term.AbstractTermLevelQuery;
import com.github.nnest.sparrow.command.document.query.term.Terms;
import com.github.nnest.sparrow.command.document.query.term.TermsLookupExternal;
import com.github.nnest.sparrow.command.document.query.term.TermsLookupExternal.ExternalDocumentTerm;
import com.github.nnest.sparrow.rest.command.mixins.serialize.RewriteSerializer;

/**
 * terms mixin, see {@linkplain Terms} and {@linkplain TermsLookupExternal}
 * 
 * @author brad.wu
 * @since 0.0.1
 * @version 0.0.1
 */
public interface TermsMixin {
	/**
	 * get example texts
	 * 
	 * @return example texts
	 */
	@JsonIgnore
	Set<String> getExampleTexts();

	/**
	 * get example type
	 * 
	 * @return example type
	 */
	@JsonIgnore
	ExampleType getExampleType();

	/**
	 * get field name.<br>
	 * must be included by {@linkplain JsonInclude}, since property in mixin
	 * {@linkplain TermLevelQueryMixin} of super class
	 * {@linkplain AbstractTermLevelQuery} of {@linkplain Terms} is annotated as
	 * {@linkplain JsonIgnore}
	 * 
	 * @return field name
	 */
	@JsonInclude
	String getFieldName();

	/**
	 * get rewrite
	 * 
	 * @return rewrite
	 */
	@JsonProperty("rewrite")
	@JsonSerialize(using = RewriteSerializer.class)
	Rewrite getRewrite();

	/**
	 * get external document terms
	 * 
	 * @return
	 */
	@JsonIgnore
	List<ExternalDocumentTerm> getTerms();
}
