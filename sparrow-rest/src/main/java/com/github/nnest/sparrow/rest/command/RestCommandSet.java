/**
 * 
 */
package com.github.nnest.sparrow.rest.command;

import java.util.Map;
import java.util.Set;

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
import com.google.common.base.Function;
import com.google.common.collect.Iterables;
import com.google.common.collect.Maps;
import com.google.common.reflect.TypeToken;

/**
 * rest command set
 * 
 * @author brad.wu
 * @since 0.0.1
 * @version 0.0.1
 */
@SuppressWarnings("rawtypes")
public class RestCommandSet {
	private static Map<ElasticCommandKind, RestCommand> commands = Maps.newHashMap();
	private static Map<Class<?>, Class<?>> scriptMixins = Maps.newHashMap();

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
		scriptMixins.put(ElasticScript.class, ElasticScriptAnnotations.class);
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
	 * get script mixin class by given script class.<br>
	 * if no mixin class found in pre-defined map, find by its super classes
	 * first, and then interfaces. return null if not found
	 * 
	 * @param scriptClass
	 *            script class
	 * @return script mixin class
	 */
	public static Class<?> getScriptMixinClass(Class<?> scriptClass) {
		Class<?> mixinClass = scriptMixins.get(scriptClass);
		if (mixinClass == null) {
			// find by super class first
			Class<?> superClass = scriptClass.getSuperclass();
			while (superClass != null) {
				mixinClass = scriptMixins.get(superClass);
				if (mixinClass != null) {
					registerScriptMixinClass(scriptClass, mixinClass);
					return mixinClass;
				} else {
					superClass = superClass.getSuperclass();
				}
			}
		}

		if (mixinClass == null) {
			// find by interfaces then
			Set<?> interfaces = TypeToken.of(scriptClass).getTypes().interfaces().rawTypes();
			if (interfaces != null) {
				Iterable<Class<?>> interfaceClasses = Iterables.transform(interfaces, new Function<Object, Class<?>>() {
					/**
					 * (non-Javadoc)
					 * 
					 * @see com.google.common.base.Function#apply(java.lang.Object)
					 */
					@Override
					public Class<?> apply(Object input) {
						return (Class<?>) input;
					}
				});
				for (Class<?> interfaceClass : interfaceClasses) {
					mixinClass = scriptMixins.get(interfaceClass);
					if (mixinClass != null) {
						registerScriptMixinClass(scriptClass, mixinClass);
						return mixinClass;
					}
				}
			}
		}
		if (mixinClass == NoopMixinScript.class) {
			// found, but is noop
			return null;
		} else if (mixinClass == null) {
			// not found
			registerScriptMixinClass(scriptClass, NoopMixinScript.class);
		}

		return mixinClass;
	}

	/**
	 * register script mixin class
	 * 
	 * @param scriptClass
	 *            script class
	 * @param mixinClass
	 *            mixin class
	 */
	public static synchronized void registerScriptMixinClass(Class<?> scriptClass, Class<?> mixinClass) {
		scriptMixins.put(scriptClass, mixinClass);
	}

	/**
	 * no mixin class for script class
	 * 
	 * @author brad.wu
	 * @since 0.0.1
	 * @version 0.0.1
	 */
	public static interface NoopMixinScript {
	}
}
