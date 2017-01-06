/**
 * 
 */
package com.github.nnest.sparrow.annotation;

import com.github.nnest.sparrow.AbstractElasticDocumentAnalyzer;
import com.github.nnest.sparrow.DefaultElasticCommand;
import com.github.nnest.sparrow.ElasticCommand;
import com.github.nnest.sparrow.ElasticCommandKind;

/**
 * annotated elastic document analyzer
 * 
 * @author brad.wu
 * @since 0.0.1
 * @version 0.0.1
 */
public class AnnotatedElasticDocumentAnalyzer extends AbstractElasticDocumentAnalyzer {
	/**
	 * (non-Javadoc)
	 * 
	 * @see com.github.nnest.sparrow.AbstractElasticDocumentAnalyzer#doAnalysis(com.github.nnest.sparrow.ElasticCommandKind,
	 *      java.lang.Object)
	 */
	@Override
	protected ElasticCommand doAnalysis(ElasticCommandKind commandKind, Object document) {
		return new DefaultElasticCommand(commandKind, document);
	}
}
