package com.github.nnest.sparrow.rest.command.mixins;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.github.nnest.sparrow.command.script.Script;

/**
 * annotations for jackson object mapper<br>
 * there is no specific jackson annotation for details of
 * {@linkplain #getParamsObject}
 * 
 * @author brad.wu
 * @since 0.0.1
 * @version 0.0.1
 */
public interface ElasticScriptMixin extends Script {
	/**
	 * (non-Javadoc)
	 * 
	 * @see com.github.nnest.sparrow.command.script.Script#getScript()
	 */
	@Override
	@JsonProperty("inline")
	String getScript();

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.github.nnest.sparrow.command.script.Script#getLanguage()
	 */
	@Override
	@JsonProperty("lang")
	String getLanguage();

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.github.nnest.sparrow.command.script.Script#getParamsObject()
	 */
	@Override
	@JsonProperty("params")
	@JsonInclude(Include.NON_NULL)
	Object getParamsObject();
}