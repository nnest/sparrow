/**
 * 
 */
package com.github.nnest.sparrow.rest;

import java.util.HashMap;
import java.util.Map;

import com.github.nnest.sparrow.ElasticCommandKind;

/**
 * rest command set
 * 
 * @author brad.wu
 * @since 0.0.1
 * @version 0.0.1
 */
public class RestCommandSet {
	private static Map<ElasticCommandKind, RestCommand> commands = new HashMap<ElasticCommandKind, RestCommand>();

	static {
		commands.put(ElasticCommandKind.INDEX, new RestCommandIndex());
	}

	/**
	 * get rest command
	 * 
	 * @param commandKind
	 * @return
	 */
	public static RestCommand get(ElasticCommandKind commandKind) {
		return commands.get(commandKind);
	}

	/**
	 * register given command for given kind
	 * 
	 * @param commandKind
	 * @param command
	 */
	public static void register(ElasticCommandKind commandKind, RestCommand command) {
		commands.put(commandKind, command);
	}
}
