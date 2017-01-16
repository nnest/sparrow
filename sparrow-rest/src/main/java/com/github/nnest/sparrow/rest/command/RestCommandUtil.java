/**
 * 
 */
package com.github.nnest.sparrow.rest.command;

import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.nnest.sparrow.ElasticCommandKind;
import com.github.nnest.sparrow.command.script.ElasticScript;
import com.github.nnest.sparrow.rest.RestCommand;
import com.github.nnest.sparrow.rest.command.document.RestCommandCreate;
import com.github.nnest.sparrow.rest.command.document.RestCommandDelete;
import com.github.nnest.sparrow.rest.command.document.RestCommandExist;
import com.github.nnest.sparrow.rest.command.document.RestCommandGet;
import com.github.nnest.sparrow.rest.command.document.RestCommandIndex;
import com.github.nnest.sparrow.rest.command.document.RestCommandUpdate;
import com.github.nnest.sparrow.rest.command.document.RestCommandUpdateByScript;
import com.github.nnest.sparrow.rest.command.indices.RestCommandDropIndex;
import com.google.common.collect.Maps;

/**
 * rest command set
 * 
 * @author brad.wu
 * @since 0.0.1
 * @version 0.0.1
 */
@SuppressWarnings("rawtypes")
public class RestCommandUtil {
	private static Map<ElasticCommandKind, RestCommand> commands = Maps.newHashMap();
	private static ObjectMapper objectMapper = new ObjectMapper();

	static {
		// document
		commands.put(ElasticCommandKind.INDEX, new RestCommandIndex());
		commands.put(ElasticCommandKind.CREATE, new RestCommandCreate());
		commands.put(ElasticCommandKind.GET, new RestCommandGet());
		commands.put(ElasticCommandKind.EXIST, new RestCommandExist());
		commands.put(ElasticCommandKind.DELETE, new RestCommandDelete());
		commands.put(ElasticCommandKind.UPDATE, new RestCommandUpdate());
		commands.put(ElasticCommandKind.UPDATE_BY_SCRIPT, new RestCommandUpdateByScript());

		// indices
		commands.put(ElasticCommandKind.DROP_INDEX, new RestCommandDropIndex());

		// scripts mixin
		objectMapper.addMixIn(ElasticScript.class, ElasticScriptAnnotations.class);
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

	/**
	 * register mixin class
	 * 
	 * @param originalClass
	 *            original class
	 * @param mixinClass
	 *            mixin class
	 */
	public static void registerMixinClass(Class<?> originalClass, Class<?> mixinClass) {
		objectMapper.addMixIn(originalClass, mixinClass);
	}

	/**
	 * get object mapper
	 * 
	 * @return object mapper
	 */
	public static ObjectMapper getObjectMapper() {
		return objectMapper;
	}
}
