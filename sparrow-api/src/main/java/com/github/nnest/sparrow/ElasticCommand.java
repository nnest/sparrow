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
	 * get original document
	 * 
	 * @return
	 */
	Object getOriginalDocument();

	/**
	 * get kind of command
	 * 
	 * @return
	 */
	ElasticCommandKind getKind();
}
