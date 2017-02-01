/**
 * 
 */
package com.github.nnest.sparrow.command.script;

import java.util.Map;

import com.google.common.collect.Maps;

/**
 * default elastic script.<br>
 * any object can be set as {@linkplain #paramsObject}, and there is a shortcut
 * to set params as map via {@linkplain #withParam(String, Object)}
 * 
 * @author brad.wu
 * @since 0.0.1
 * @version 0.0.1
 */
public class DefaultScript<T extends Script> implements Script {
	private String script = null;
	private String language = null;
	private Object paramsObject = null;

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.github.nnest.sparrow.command.script.Script#getScript()
	 */
	@Override
	public String getScript() {
		return this.script;
	}

	/**
	 * @param script
	 *            the script to set
	 * @return this
	 */
	@SuppressWarnings("unchecked")
	public T withScript(String script) {
		this.script = script;
		return (T) this;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.github.nnest.sparrow.command.script.Script#getLanguage()
	 */
	@Override
	public String getLanguage() {
		return this.language;
	}

	/**
	 * @param language
	 *            the language to set
	 * @return this
	 */
	@SuppressWarnings("unchecked")
	public T withLanguage(String language) {
		this.language = language;
		return (T) this;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.github.nnest.sparrow.command.script.Script#getParamsObject()
	 */
	@Override
	public Object getParamsObject() {
		return this.paramsObject;
	}

	/**
	 * @param paramsObject
	 *            the paramsObject to set
	 * @return this
	 */
	@SuppressWarnings("unchecked")
	public T withParamsObject(Object paramsObject) {
		this.paramsObject = paramsObject;
		return (T) this;
	}

	/**
	 * with param as a map, let the {@linkplain #paramsObject} as a map
	 * 
	 * @param key
	 *            key of parameter
	 * @param value
	 *            value of parameter
	 * @return this
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public T withParam(String key, Object value) {
		if (this.paramsObject == null || !(this.paramsObject instanceof Map)) {
			this.withParamsObject(Maps.newHashMap());
		}
		((Map) this.paramsObject).put(key, value);
		return (T) this;
	}
}
