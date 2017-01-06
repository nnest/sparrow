/**
 * 
 */
package com.github.nnest.sparrow.rest;

import com.github.nnest.sparrow.AbstractElasticCommandExecutor;
import com.github.nnest.sparrow.ElasticCommand;
import com.github.nnest.sparrow.ElasticCommandResult;
import com.github.nnest.sparrow.ElasticCommandResultHanlder;
import com.github.nnest.sparrow.ElasticSettings;

/**
 * abstract rest elastic command executor
 * 
 * @author brad.wu
 * @since 0.0.1
 * @version 0.0.1
 */
public class RestElasticCommandExecutor extends AbstractElasticCommandExecutor {
	public RestElasticCommandExecutor(ElasticSettings settings) {
		super(settings);
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.github.nnest.sparrow.ElasticCommander#execute(com.github.nnest.sparrow.ElasticCommand)
	 */
	@Override
	public ElasticCommandResult execute(ElasticCommand command) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.github.nnest.sparrow.ElasticCommander#executeAsync(com.github.nnest.sparrow.ElasticCommand,
	 *      com.github.nnest.sparrow.ElasticCommandResultHanlder)
	 */
	@Override
	public void executeAsync(ElasticCommand command, ElasticCommandResultHanlder commandResultHandler) {
		// TODO Auto-generated method stub
	}
}
