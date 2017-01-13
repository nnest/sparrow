/**
 * 
 */
package com.github.nnest.sparrow.command.indices;

import com.github.nnest.sparrow.ElasticCommand;
import com.github.nnest.sparrow.ElasticCommandKind;
import com.github.nnest.sparrow.ElasticDocumentAnalyzer;
import com.google.common.base.Strings;

/**
 * delete index
 * 
 * @author brad.wu
 * @since 0.0.1
 * @version 0.0.1
 */
public class DropIndex implements ElasticCommand {
	private String indexName = null;

	public DropIndex(String indexName) {
		assert Strings.nullToEmpty(indexName).trim().length() != 0 : "Index name cannot be null or blank.";
		
		this.indexName = indexName;
	}

	/**
	 * @return the indexName
	 */
	public String getIndexName() {
		return indexName;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.github.nnest.sparrow.ElasticCommand#getCommandKind()
	 */
	@Override
	public ElasticCommandKind getCommandKind() {
		return ElasticCommandKind.DROP_INDEX;
	}

	/**
	 * analysis is not need, return itself directly
	 * 
	 * @see com.github.nnest.sparrow.ElasticCommand#analysis(com.github.nnest.sparrow.ElasticDocumentAnalyzer)
	 */
	@Override
	public ElasticCommand analysis(ElasticDocumentAnalyzer documentAnalyzer) {
		return this;
	}
}
