/**
 * 
 */
package com.github.nnest.sparrow;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * abstract elastic document validator
 * 
 * @author brad.wu
 * @since 0.0.1
 * @version 0.0.1
 */
public abstract class AbstractElasticDocumentValidator implements ElasticDocumentValidator {
	private Map<Class<?>, Boolean> validatedClasses = new ConcurrentHashMap<Class<?>, Boolean>();

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.github.nnest.sparrow.ElasticDocumentValidator#validate(java.lang.Class)
	 */
	@Override
	public void validate(Class<?> documentType) {
		if (validatedClasses.containsKey(documentType)) {
			// already validated, pass again
		} else {
			this.doValidate(documentType);
			validatedClasses.put(documentType, Boolean.TRUE);
		}
	}

	/**
	 * do validation. throws {@linkplain ElasticDocumentValidationException} if
	 * failed.
	 * 
	 * @param documentType
	 *            document type to validate
	 */
	protected abstract void doValidate(Class<?> documentType);
}
