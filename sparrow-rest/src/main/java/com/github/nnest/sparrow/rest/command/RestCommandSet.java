/**
 * 
 */
package com.github.nnest.sparrow.rest.command;

import java.util.HashMap;
import java.util.Map;

import com.github.nnest.sparrow.ElasticCommandKind;
import com.github.nnest.sparrow.rest.RestCommand;
import com.github.nnest.sparrow.rest.command.document.RestCommandCreate;
import com.github.nnest.sparrow.rest.command.document.RestCommandDelete;
import com.github.nnest.sparrow.rest.command.document.RestCommandExist;
import com.github.nnest.sparrow.rest.command.document.RestCommandGet;
import com.github.nnest.sparrow.rest.command.document.RestCommandIndex;
import com.github.nnest.sparrow.rest.command.document.RestCommandUpdate;
import com.github.nnest.sparrow.rest.command.indices.RestCommandDropIndex;

/**
 * rest command set
 * 
 * @author brad.wu
 * @since 0.0.1
 * @version 0.0.1
 */
@SuppressWarnings("rawtypes")
public class RestCommandSet {
	private static Map<ElasticCommandKind, RestCommand> commands = new HashMap<ElasticCommandKind, RestCommand>();

	static {
		// document
		commands.put(ElasticCommandKind.INDEX, new RestCommandIndex());
		commands.put(ElasticCommandKind.CREATE, new RestCommandCreate());
		commands.put(ElasticCommandKind.GET, new RestCommandGet());
		commands.put(ElasticCommandKind.EXIST, new RestCommandExist());
		commands.put(ElasticCommandKind.DELETE, new RestCommandDelete());
		commands.put(ElasticCommandKind.UPDATE, new RestCommandUpdate());

		// indices
		commands.put(ElasticCommandKind.DROP_INDEX, new RestCommandDropIndex());
	}

	/**
	 * get rest command
	 * 
	 * @param commandKind
	 *            command kind
	 * @return command
	 */
	public static RestCommand get(ElasticCommandKind commandKind) {
		return commands.get(commandKind);
	}

	/**
	 * register given command for given kind
	 * 
	 * @param commandKind
	 *            command kind
	 * @param command
	 *            command
	 */
	public static void register(ElasticCommandKind commandKind, RestCommand command) {
		commands.put(commandKind, command);
	}
}
