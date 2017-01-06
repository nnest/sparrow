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
	 * @see com.github.nnest.sparrow.ElasticDocumentValidator#validate(java.lang.Object)
	 */
	@Override
	public void validate(Object document) {
		if (document != null) {
			Class<?> documentClass = document.getClass();
			if (validatedClasses.containsKey(documentClass)) {
				// already validated, pass again
			} else {
				this.doValidate(document);
				validatedClasses.put(documentClass, Boolean.TRUE);
			}
		}
	}

	/**
	 * do validation. throws {@linkplain ElasticDocumentValidationException} if
	 * failed.
	 * 
	 * @param document
	 */
	protected abstract void doValidate(Object document);
}
