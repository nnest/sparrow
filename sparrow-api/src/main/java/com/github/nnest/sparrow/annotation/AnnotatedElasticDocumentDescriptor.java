/**
 * 
 */
package com.github.nnest.sparrow.annotation;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.github.nnest.sparrow.ElasticDocumentDescriptor;

/**
 * Annotated elastic document descriptor
 * 
 * @author brad.wu
 * @since 0.0.1
 * @version 0.0.1
 */
public class AnnotatedElasticDocumentDescriptor implements ElasticDocumentDescriptor {
	private Class<?> documentClass = null;
	private ElasticDocument document = null;
	private String idField = null;
	private Set<String> fields = new HashSet<String>();
	private Set<String> ignoredFields = new HashSet<String>();
	private Map<String, ElasticField> fieldDescriptors = new HashMap<String, ElasticField>();

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.github.nnest.sparrow.ElasticDocumentDescriptor#getDocumentClass()
	 */
	@Override
	public Class<?> getDocumentClass() {
		return this.documentClass;
	}

	/**
	 * @param documentClass
	 *            the documentClass to set
	 */
	public void setDocumentClass(Class<?> documentClass) {
		this.documentClass = documentClass;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.github.nnest.sparrow.ElasticDocumentDescriptor#getIndex()
	 */
	@Override
	public String getIndex() {
		return this.document.index();
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.github.nnest.sparrow.ElasticDocumentDescriptor#getType()
	 */
	@Override
	public String getType() {
		return this.document.type();
	}

	/**
	 * @param document
	 *            the document to set
	 */
	public void setDocument(ElasticDocument document) {
		this.document = document;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.github.nnest.sparrow.ElasticDocumentDescriptor#getIdField()
	 */
	@Override
	public String getIdField() {
		return this.idField;
	}

	/**
	 * @param idField
	 *            the idField to set
	 */
	public void setIdField(String idField) {
		this.idField = idField;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.github.nnest.sparrow.ElasticDocumentDescriptor#getFields()
	 */
	@Override
	public Set<String> getFields() {
		return fields;
	}

	/**
	 * register field
	 * 
	 * @param name
	 *            field name
	 * @param field
	 *            field annotation
	 */
	public synchronized void registerField(String name, ElasticField field) {
		this.fieldDescriptors.put(name, field);
		if (!this.ignoredFields.contains(name) && !this.fields.contains(name) && !this.idField.equals(name)) {
			this.fields.add(name);
		}
	}

	/**
	 * register field as ignored
	 * 
	 * @param field
	 *            field name
	 */
	public synchronized void registerAsIgnored(String field) {
		ignoredFields.add(field);
		if (this.fields.contains(field)) {
			this.fields.remove(field);
		}
	}
}
