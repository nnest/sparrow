/**
 * 
 */
package com.github.nnest.sparrow.rest.command;

import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.github.nnest.sparrow.ElasticCommandKind;
import com.github.nnest.sparrow.command.document.query.Example;
import com.github.nnest.sparrow.command.document.query.fulltext.AbstractSingleMatch;
import com.github.nnest.sparrow.command.script.ElasticScript;
import com.github.nnest.sparrow.rest.RestCommand;
import com.github.nnest.sparrow.rest.command.document.RestCommandCreate;
import com.github.nnest.sparrow.rest.command.document.RestCommandDelete;
import com.github.nnest.sparrow.rest.command.document.RestCommandExist;
import com.github.nnest.sparrow.rest.command.document.RestCommandGet;
import com.github.nnest.sparrow.rest.command.document.RestCommandIndex;
import com.github.nnest.sparrow.rest.command.document.RestCommandMultiGet;
import com.github.nnest.sparrow.rest.command.document.RestCommandQuery;
import com.github.nnest.sparrow.rest.command.document.RestCommandUpdate;
import com.github.nnest.sparrow.rest.command.document.RestCommandUpdateByScript;
import com.github.nnest.sparrow.rest.command.indices.RestCommandDropIndex;
import com.github.nnest.sparrow.rest.command.mixins.AbstractSingleMatchMixin;
import com.github.nnest.sparrow.rest.command.mixins.ElasticScriptMixin;
import com.github.nnest.sparrow.rest.command.mixins.serialize.SingleMatchSerializerModifier;
import com.github.nnest.sparrow.rest.command.mixins.wrapper.QueryExampleWrapper;
import com.github.nnest.sparrow.rest.command.mixins.wrapper.SingleMatchWrapper;
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
	private static QueryExampleWrapper exampleWrapper = null;

	static {
		// document
		commands.put(ElasticCommandKind.INDEX, new RestCommandIndex());
		commands.put(ElasticCommandKind.CREATE, new RestCommandCreate());
		commands.put(ElasticCommandKind.GET, new RestCommandGet());
		commands.put(ElasticCommandKind.MULTI_GET, new RestCommandMultiGet());
		commands.put(ElasticCommandKind.EXIST, new RestCommandExist());
		commands.put(ElasticCommandKind.DELETE, new RestCommandDelete());
		commands.put(ElasticCommandKind.UPDATE, new RestCommandUpdate());
		commands.put(ElasticCommandKind.UPDATE_BY_SCRIPT, new RestCommandUpdateByScript());
		commands.put(ElasticCommandKind.QUERY, new RestCommandQuery());

		// indices
		commands.put(ElasticCommandKind.DROP_INDEX, new RestCommandDropIndex());

		// object mapper settings
		objectMapper.addMixIn(ElasticScript.class, ElasticScriptMixin.class);
		objectMapper.addMixIn(AbstractSingleMatch.class, AbstractSingleMatchMixin.class);
		objectMapper.registerModule(new SimpleModule().setSerializerModifier(new SingleMatchSerializerModifier()));

		exampleWrapper = new SingleMatchWrapper();
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
	 * @return the exampleWrapper
	 */
	public static QueryExampleWrapper getExampleWrapper() {
		return exampleWrapper;
	}

	/**
	 * @param exampleWrapper
	 *            the exampleWrapper to set
	 */
	public static void setExampleWrapper(QueryExampleWrapper exampleWrapper) {
		RestCommandUtil.exampleWrapper = exampleWrapper;
	}

	/**
	 * get object mapper
	 * 
	 * @return object mapper
	 */
	public static ObjectMapper getObjectMapper() {
		return objectMapper;
	}

	/**
	 * wrap example
	 * 
	 * @param example
	 *            original example
	 * @return wrapped example
	 */
	@SuppressWarnings("unchecked")
	public static Example wrapExample(Example example) {
		return getExampleWrapper().wrap(example);
	}
}
