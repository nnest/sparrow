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
	 * (non-Javadoc)
	 * 
	 * @see com.github.nnest.sparrow.ElasticClient#index(java.lang.Object)
	 */
	@Override
	public <T> T index(T document) throws ElasticCommandException, ElasticExecutorException {
		return this.executeCommand(document, ElasticCommandKind.INDEX);
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.github.nnest.sparrow.ElasticClient#indexAsync(java.lang.Object,
	 *      com.github.nnest.sparrow.ElasticCommandResultHandler)
	 */
	@Override
	public <T> void indexAsync(T document, ElasticCommandResultHandler commandResultHandler) {
		this.executeCommandAsync(document, ElasticCommandKind.INDEX, commandResultHandler);
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.github.nnest.sparrow.ElasticClient#indexCreateOnly(java.lang.Object)
	 */
	@Override
	public <T> T indexCreateOnly(T document) throws ElasticCommandException, ElasticExecutorException {
		return this.executeCommand(document, ElasticCommandKind.INDEX_CREATE_ONLY);
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.github.nnest.sparrow.ElasticClient#indexCreateOnlyAsync(java.lang.Object,
	 *      com.github.nnest.sparrow.ElasticCommandResultHandler)
	 */
	@Override
	public <T> void indexCreateOnlyAsync(T document, ElasticCommandResultHandler commandResultHandler) {
		this.executeCommandAsync(document, ElasticCommandKind.INDEX_CREATE_ONLY, commandResultHandler);
	}

	/**
	 * execute command asynchronized
	 * 
	 * @param document
	 * @param kind
	 * @param commandResultHandler
	 */
	protected <T> void executeCommandAsync(T document, ElasticCommandKind kind,
			ElasticCommandResultHandler commandResultHandler) {
		ElasticCommand command = this.getDocumentAnalyzer().analysis(kind, document);
		this.executeCommandAsync(command, commandResultHandler);
	}

	/**
	 * execute command asynchronized
	 * 
	 * @param command
	 *            command to execute
	 * @param commandResultHandler
	 *            command execution result handler
	 */
	protected void executeCommandAsync(ElasticCommand command, ElasticCommandResultHandler commandResultHandler) {
		ElasticCommandExecutor commandExecutor = this.getCommandExecutor();
		commandExecutor.executeAsync(command,
				new AsynchronizedElasticCommandResultHandlerDelegate(commandExecutor, commandResultHandler));
	}

	/**
	 * execute command by given document and command kind
	 * 
	 * @param document
	 *            document
	 * @param kind
	 *            command kind
	 * @return ths original document
	 * @throws ElasticCommandException
	 *             command exception
	 * @throws ElasticExecutorException
	 *             executor exception
	 */
	protected <T> T executeCommand(T document, ElasticCommandKind kind)
			throws ElasticCommandException, ElasticExecutorException {
		ElasticCommand command = this.getDocumentAnalyzer().analysis(kind, document);
		return this.executeCommand(command).getOriginalDocument();
	}

	/**
	 * execute command
	 * 
	 * @param command
	 *            command to execute
	 * @return command execution result
	 * @throws ElasticCommandException
	 *             command exception
	 * @throws ElasticExecutorException
	 *             executor exception
	 */
	protected ElasticCommandResult executeCommand(ElasticCommand command)
			throws ElasticCommandException, ElasticExecutorException {
		ElasticCommandExecutor commandExecutor = this.getCommandExecutor();
		ElasticCommandResult result = commandExecutor.execute(command);
		commandExecutor.close();
		return result;
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
