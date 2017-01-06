/**
 * 
 */
package com.github.nnest.sparrow;

import java.util.List;

/**
 * chained elastic document validator
 * 
 * @author brad.wu
 * @since 0.0.1
 * @version 0.0.1
 */
public class ChainedElasticDocumentValidator implements ElasticDocumentValidator {
	private List<ElasticDocumentValidator> validators = null;

	public ChainedElasticDocumentValidator(List<ElasticDocumentValidator> validators) {
		this.setValidators(validators);
	}

	/**
	 * @return the validators
	 */
	public List<ElasticDocumentValidator> getValidators() {
		return validators;
	}

	/**
	 * @param validators
	 *            the validators to set
	 */
	private void setValidators(List<ElasticDocumentValidator> validators) {
		assert validators != null && validators.size() != 0 : "Validators cannot be null or empty.";

		this.validators = validators;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.github.nnest.sparrow.ElasticDocumentValidator#validate(java.lang.Object)
	 */
	@Override
	public void validate(Object document) {
		for (ElasticDocumentValidator validator : this.getValidators()) {
			validator.validate(document);
		}
	}
}
