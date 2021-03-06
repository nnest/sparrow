/**
 * 
 */
package com.github.nnest.sparrow;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * default elastic client.
 * 
 * @author brad.wu
 * @since 0.0.1
 * @version 0.0.1
 */
public class DefaultElasticClient implements ElasticClient {
	private ElasticCommandExecutorRepository commandExecutorRepository = null;
	private ElasticDocumentAnalyzer documentAnalyzer = null;

	public DefaultElasticClient(ElasticCommandExecutorRepository commanderRepository,
			ElasticDocumentAnalyzer documentAnalyzer) {
		this.setCommandExecutorRepository(commanderRepository);
		this.setDocumentAnalyzer(documentAnalyzer);
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.github.nnest.sparrow.ElasticClient#execute(com.github.nnest.sparrow.ElasticCommand)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T extends ElasticCommandResult> T execute(ElasticCommand command)
			throws ElasticCommandException, ElasticExecutorException {
		return (T) this.getCommandExecutor().execute(command.analysis(this.getDocumentAnalyzer()));
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.github.nnest.sparrow.ElasticClient#executeAysnc(com.github.nnest.sparrow.ElasticCommand,
	 *      com.github.nnest.sparrow.ElasticCommandResultHandler)
	 */
	@Override
	public void executeAysnc(ElasticCommand command, ElasticCommandResultHandler commandResultHandler) {
		this.getCommandExecutor().executeAsync(command.analysis(this.getDocumentAnalyzer()),
				new AsynchronizedElasticCommandResultHandlerDelegate(this.getCommandExecutor(), commandResultHandler));

	}

	/**
	 * @return the commanderRepository
	 */
	public ElasticCommandExecutorRepository getCommandExecutorRepository() {
		return commandExecutorRepository;
	}

	/**
	 * @param commandExecutorRepository
	 *            the commanderRepository to set
	 */
	public void setCommandExecutorRepository(ElasticCommandExecutorRepository commandExecutorRepository) {
		assert commandExecutorRepository != null : "Command executor repository cannot be null.";

		this.commandExecutorRepository = commandExecutorRepository;
	}

	/**
	 * @return the documentAnalyzer
	 */
	public ElasticDocumentAnalyzer getDocumentAnalyzer() {
		return documentAnalyzer;
	}

	/**
	 * @param documentAnalyzer
	 *            the documentAnalyzer to set
	 */
	public void setDocumentAnalyzer(ElasticDocumentAnalyzer documentAnalyzer) {
		assert documentAnalyzer != null : "Document analyzer cannot be null.";

		this.documentAnalyzer = documentAnalyzer;
	}

	/**
	 * get rest client
	 * 
	 * @return command executor
	 */
	protected ElasticCommandExecutor getCommandExecutor() {
		return this.getCommandExecutorRepository().getCommandExecutor();
	}

	/**
	 * asynchronized elastic command result handler delegate.<br>
	 * close executor anyway after handle result or exception.
	 * 
	 * @author brad.wu
	 * @since 0.0.1
	 * @version 0.0.1
	 */
	public static class AsynchronizedElasticCommandResultHandlerDelegate implements ElasticCommandResultHandler {
		private Logger logger = LoggerFactory.getLogger(getClass());
		private ElasticCommandExecutor commandExecutor = null;
		private ElasticCommandResultHandler commandResultHandler = null;

		public AsynchronizedElasticCommandResultHandlerDelegate(ElasticCommandExecutor commandExecutor,
				ElasticCommandResultHandler commandResultHandler) {
			this.commandExecutor = commandExecutor;
			this.commandResultHandler = commandResultHandler;
		}

		/**
		 * @return the commandExecutor
		 */
		protected ElasticCommandExecutor getCommandExecutor() {
			return commandExecutor;
		}

		/**
		 * @return the commandResultHandler
		 */
		protected ElasticCommandResultHandler getCommandResultHandler() {
			return commandResultHandler;
		}

		/**
		 * (non-Javadoc)
		 * 
		 * @see com.github.nnest.sparrow.ElasticCommandResultHandler#handleSuccess(com.github.nnest.sparrow.ElasticCommandResult)
		 */
		@Override
		public void handleSuccess(ElasticCommandResult result) {
			try {
				this.getCommandResultHandler().handleSuccess(result);
			} finally {
				this.closeCommandExecutor();
			}
		}

		/**
		 * (non-Javadoc)
		 * 
		 * @see com.github.nnest.sparrow.ElasticCommandResultHandler#handleFail(java.lang.Exception)
		 */
		@Override
		public void handleFail(Exception exception) {
			try {
				this.getCommandResultHandler().handleFail(exception);
			} finally {
				this.closeCommandExecutor();
			}
		}

		/**
		 * close command executor
		 */
		protected void closeCommandExecutor() {
			try {
				this.getCommandExecutor().close();
			} catch (ElasticExecutorException e) {
				// never throw this exception
				this.getLogger().warn(String.format("Fail to close command executor[%1$s]", this.getCommandExecutor()),
						e);
			}
		}

		/**
		 * @return the logger
		 */
		protected Logger getLogger() {
			return logger;
		}
	}
}
