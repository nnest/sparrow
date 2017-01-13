/**
 * 
 */
package com.github.nnest.sparrow;

/**
 * default elastic command result
 * 
 * @author brad.wu
 * @since 0.0.1
 * @version 0.0.1
 */
public class DefaultElasticCommandResult implements ElasticCommandResult {
	private ElasticCommand command = null;
	private ElasticCommandResultData resultData = null;

	public DefaultElasticCommandResult(ElasticCommand command, ElasticCommandResultData resultData) {
		this.command = command;
		this.resultData = resultData;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.github.nnest.sparrow.ElasticCommandResult#getCommand()
	 */
	@Override
	public ElasticCommand getCommand() {
		return this.command;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.github.nnest.sparrow.ElasticCommandResult#getResultData()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T extends ElasticCommandResultData> T getResultData() {
		return (T) this.resultData;
	}
}
