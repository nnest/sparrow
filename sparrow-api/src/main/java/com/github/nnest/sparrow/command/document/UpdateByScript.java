/**
 * 
 */
package com.github.nnest.sparrow.command.document;

import com.github.nnest.sparrow.ElasticCommand;
import com.github.nnest.sparrow.ElasticCommandKind;
import com.github.nnest.sparrow.ElasticDocumentAnalyzer;
import com.github.nnest.sparrow.ElasticDocumentDescriptor;
import com.github.nnest.sparrow.command.script.Script;
import com.google.common.base.Strings;

/**
 * update by script.<br>
 * construct by {@linkplain #UpdateByScript(Class, String)}, then
 * {@linkplain #asUpsert} is {@code false}<br>
 * construct by {@linkplain #UpdateByScript(Object)}, {@linkplain #id} and
 * {@linkplain #version} are get from document automatically if not set.<br>
 * {@linkplain #asUpsert} is only effective when both {@linkplain #document} and
 * {@linkplain #script} exist.
 * 
 * @author brad.wu
 * @since 0.0.1
 * @version 0.0.1
 */
public class UpdateByScript implements ElasticCommand {
	private Object document = null;
	private Class<?> documentType = null;
	private String id = null;
	private String version = null;
	private Script script = null;
	private boolean asUpsert = false;
	private ElasticDocumentDescriptor documentDescriptor = null;

	public UpdateByScript(Object document) {
		assert document != null : "Document cannot be null.";

		this.document = document;
		this.documentType = document.getClass();
	}

	public UpdateByScript(Class<?> documentType, String id) {
		assert documentType != null : "Document type cannot be null.";
		assert Strings.nullToEmpty(id).trim().length() != 0 : "Id cannot be null.";

		this.documentType = documentType;
		this.id = id;
	}

	/**
	 * @return the document
	 */
	public Object getDocument() {
		return document;
	}

	/**
	 * @return the documentType
	 */
	public Class<?> getDocumentType() {
		return documentType;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @return the asUpsert
	 */
	public boolean isAsUpsert() {
		return asUpsert ? (this.getDocument() != null && this.getScript() != null) : asUpsert;
	}

	/**
	 * with as upsert
	 * 
	 * @param asUpsert
	 *            as upsert
	 * @return this
	 */
	public UpdateByScript withAsUpsert(boolean asUpsert) {
		this.asUpsert = asUpsert;
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
	 * @return the version
	 */
	public String getVersion() {
		return version;
	}

	/**
	 * @param version
	 *            the version to set
	 * @return this
	 */
	public UpdateByScript withVersion(String version) {
		this.version = version;
		return this;
	}

	/**
	 * @return the script
	 */
	public Script getScript() {
		return script;
	}

	/**
	 * @param script
	 *            the script to set
	 * @return this
	 */
	public UpdateByScript withScript(Script script) {
		this.script = script;
		return this;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.github.nnest.sparrow.ElasticCommand#getCommandKind()
	 */
	@Override
	public ElasticCommandKind getCommandKind() {
		return ElasticCommandKind.UPDATE_BY_SCRIPT;
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
