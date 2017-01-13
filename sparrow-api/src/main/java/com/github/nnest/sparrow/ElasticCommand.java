/**
 * 
 */
package com.github.nnest.sparrow;

/**
 * elastic command
 * 
 * @author brad.wu
 * @since 0.0.1
 * @version 0.0.1
 */
public interface ElasticCommand {
	/**
	 * get command kind
	 * 
	 * @return command kind
	 */
	ElasticCommandKind getCommandKind();

	/**
	 * analysis current command by given analyzer
	 * 
	 * @param documentAnalyzer
	 *            document analyzer
	 * @return returns myself or a new command if want, according to
	 *         implementation
	 */
	ElasticCommand analysis(ElasticDocumentAnalyzer documentAnalyzer);
}
