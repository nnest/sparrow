/**
 * 
 */
package com.github.nnest.sparrow.command.document.query;

/**
 * example of query criteria.<br>
 * all setters are provided by {@code with} or {@code withABC}. assert exception
 * raised when call {@code with} method, means the passing parameter is
 * incorrect. keep it unset, or pass the correct value.<br>
 * attributes which are exclusive are not verified by example command itself,
 * see elastic search document for more information.
 * 
 * @author brad.wu
 * @since 0.0.1
 * @version 0.0.1
 */
public interface Example {
	String ALL_FIELDS = "_all";
}
