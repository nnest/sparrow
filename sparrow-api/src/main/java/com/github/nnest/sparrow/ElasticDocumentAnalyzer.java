/**
 * 
 */
package com.github.nnest.sparrow;

/**
 * Document analyzer. according to document instance and method, analysis
 * command
 * 
 * @author brad.wu
 * @since 0.0.1
 * @version 0.0.1
 */
public interface ElasticDocumentAnalyzer {
	/**
	 * analysis document
	 * 
	 * @param documentType
	 *            document type
	 * @return document descriptor
	 */
	ElasticDocumentDescriptor analysis(Class<?> documentType);
}
