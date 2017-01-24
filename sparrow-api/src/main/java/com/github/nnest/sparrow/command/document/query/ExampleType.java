/**
 * 
 */
package com.github.nnest.sparrow.command.document.query;

/**
 * example type. which has a name, and has a type if different example type with
 * same name.
 * 
 * @author brad.wu
 * @since 0.0.1
 * @version 0.0.1
 */
public interface ExampleType {
	/**
	 * return type name
	 * 
	 * @return name
	 */
	String getName();

	/**
	 * return type type
	 * 
	 * @return type
	 */
	String getType();
}
