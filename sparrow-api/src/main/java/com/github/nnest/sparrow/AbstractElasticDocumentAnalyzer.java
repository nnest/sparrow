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
	 * @see com.github.nnest.sparrow.ElasticDocumentAnalyzer#analysis(java.lang.Class)
	 */
	@Override
	public ElasticDocumentDescriptor analysis(Class<?> documentType) {
		this.validate(documentType);
		return this.doAnalysis(documentType);
	}

	/**
	 * analysis document type, get document descriptor
	 * 
	 * @param documentType
	 *            document type
	 * @return document descriptor
	 */
	protected abstract ElasticDocumentDescriptor doAnalysis(Class<?> documentType);

	/**
	 * validate document, throw {@linkplain ElasticDocumentValidationException}
	 * if failed.
	 * 
	 * @param document
	 *            document
	 */
	protected void validate(Class<?> documentType) {
		ElasticDocumentValidator validator = this.getValidator();
		if (validator != null) {
			validator.validate(documentType);
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
