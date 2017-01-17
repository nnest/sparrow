/**
 * 
 */
package com.github.nnest.sparrow.command.document;

import java.util.Set;

import com.github.nnest.sparrow.ElasticCommand;
import com.github.nnest.sparrow.ElasticCommandKind;
import com.github.nnest.sparrow.ElasticDocumentAnalyzer;
import com.github.nnest.sparrow.ElasticDocumentDescriptor;
import com.google.common.base.Strings;
import com.google.common.collect.Sets;

/**
 * get document
 * 
 * @author brad.wu
 * @since 0.0.1
 * @version 0.0.1
 */
public class Get implements ElasticCommand {
	private Class<?> documentType = null;
	private String id = null;
	private ElasticDocumentDescriptor documentDescriptor = null;
	private Set<String> includes = null;
	private Set<String> excludes = null;

	public Get() {
	}

	public Get(Class<?> documentType, String id) {
		this.withDocumentType(documentType).withId(id);
	}

	/**
	 * @return the documentType
	 */
	public Class<?> getDocumentType() {
		return documentType;
	}

	/**
	 * with document type
	 * 
	 * @param documentType
	 *            document type
	 * @return this
	 */
	public Get withDocumentType(Class<?> documentType) {
		assert documentType != null : "Document type cannot be null.";

		this.documentType = documentType;
		return this;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * with id
	 * 
	 * @param id
	 *            id
	 * @return this
	 */
	public Get withId(String id) {
		assert Strings.nullToEmpty(id).trim().length() != 0 : "Id cannot be null.";

		this.id = id;
		return this;
	}

	/**
	 * @return the documentDescriptor
	 */
	public ElasticDocumentDescriptor getDocumentDescriptor() {
		return documentDescriptor;
	}

	/**
	 * @param documentDescriptor
	 *            the documentDescriptor to set
	 */
	protected void setDocumentDescriptor(ElasticDocumentDescriptor documentDescriptor) {
		this.documentDescriptor = documentDescriptor;
	}

	/**
	 * @return the includes
	 */
	public Set<String> getIncludes() {
		return includes;
	}

	/**
	 * with includes
	 * 
	 * @param includes
	 *            the includes to set
	 * @return this
	 */
	public Get withIncludes(String... includes) {
		return this.withIncludes(Sets.newHashSet(includes));
	}

	/**
	 * @param includes
	 *            the includes to set
	 * @return this
	 */
	public Get withIncludes(Set<String> includes) {
		this.includes = includes;
		return this;
	}

	/**
	 * @return the excludes
	 */
	public Set<String> getExcludes() {
		return excludes;
	}

	/**
	 * with excludes
	 * 
	 * @param excludes
	 *            the excludes to set
	 * @return this
	 */
	public Get withExcludes(String... excludes) {
		return this.withExcludes(Sets.newHashSet(excludes));
	}

	/**
	 * @param excludes
	 *            the excludes to set
	 * @return this
	 */
	public Get withExcludes(Set<String> excludes) {
		this.excludes = excludes;
		return this;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.github.nnest.sparrow.ElasticCommand#getCommandKind()
	 */
	@Override
	public ElasticCommandKind getCommandKind() {
		return ElasticCommandKind.GET;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.github.nnest.sparrow.ElasticCommand#analysis(com.github.nnest.sparrow.ElasticDocumentAnalyzer)
	 */
	@Override
	public ElasticCommand analysis(ElasticDocumentAnalyzer documentAnalyzer) {
		this.setDocumentDescriptor(documentAnalyzer.analysis(this.getDocumentType()));
		return this;
	}
}
