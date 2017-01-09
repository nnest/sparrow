/**
 * 
 */
package com.github.nnest.sparrow;

/**
 * abstract elastic document analyzer. a validator can be set, its optional.
 * 
 * @author brad.wu
 * @since 0.0.1
 * @version 0.0.1
 */
public abstract class AbstractElasticDocumentAnalyzer implements ElasticDocumentAnalyzer {
	private ElasticDocumentValidator validator = null;

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.github.nnest.sparrow.ElasticDocumentAnalyzer#analysis(com.github.nnest.sparrow.ElasticCommandKind,
	 *      java.lang.Object)
	 */
	@Override
	public ElasticCommand analysis(ElasticCommandKind commandKind, Object document) {
		this.validate(document);
		return this.doAnalysis(commandKind, document);
	}

	/**
	 * analysis document, implements by sub classes.
	 * 
	 * @param commandKind
	 *            command kind
	 * @param document
	 *            document
	 * @return command to execute
	 */
	protected ElasticCommand doAnalysis(ElasticCommandKind commandKind, Object document) {
		ElasticDocumentDescriptor descriptor = this.getDocumentDescriptor(commandKind, document);
		return new DefaultElasticCommand(commandKind, document, descriptor);
	}

	/**
	 * get document descriptor by given command kind and document instance
	 * 
	 * @param commandKind
	 *            command kind
	 * @param document
	 *            document
	 * @return document descriptor
	 */
	protected abstract ElasticDocumentDescriptor getDocumentDescriptor(ElasticCommandKind commandKind, Object document);

	/**
	 * validate document, throw {@linkplain ElasticDocumentValidationException}
	 * if failed.
	 * 
	 * @param document
	 *            document
	 */
	protected void validate(Object document) {
		ElasticDocumentValidator validator = this.getValidator();
		if (validator != null) {
			validator.validate(document);
		}
	}

	/**
	 * get document validator
	 * 
	 * @return document validator
	 */
	public ElasticDocumentValidator getValidator() {
		return validator;
	}

	/**
	 * set document validator
	 * 
	 * @param validator
	 *            the validator to set
	 */
	public void setValidator(ElasticDocumentValidator validator) {
		this.validator = validator;
	}
}
