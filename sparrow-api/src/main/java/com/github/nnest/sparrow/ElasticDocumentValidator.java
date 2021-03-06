/**
 * 
 */
package com.github.nnest.sparrow;

/**
 * elastic document instance validator
 * 
 * @author brad.wu
 * @since 0.0.1
 * @version 0.0.1
 */
public interface ElasticDocumentValidator {
	/**
	 * validate given document type can be persist to elastic or not. throws
	 * {@linkplain ElasticDocumentValidationException} if not passed.
	 * 
	 * @param documentType
	 *            document type to validate
	 */
	void validate(Class<?> documentType);
}
