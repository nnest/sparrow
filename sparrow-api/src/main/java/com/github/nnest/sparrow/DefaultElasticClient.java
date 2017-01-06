/**
 * 
 */
package com.github.nnest.sparrow;

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
	 * @return
	 */
	protected ElasticCommandExecutor getCommandExecutor() {
		return this.getCommandExecutorRepository().getCommandExecutor();
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @throws ElasticCommandException
	 */
	@Override
	public <T> T index(T document) throws ElasticCommandException {
		ElasticCommand command = this.getDocumentAnalyzer().analysis(ElasticCommandKind.INDEX, document);
		return this.executeCommand(command).getResultObject();
	}

	/**
	 * execute command
	 * 
	 * @param command
	 * @return
	 */
	protected ElasticCommandResult executeCommand(ElasticCommand command) throws ElasticCommandException {
		ElasticCommandExecutor commandExecutor = this.getCommandExecutor();
		ElasticCommandResult result = commandExecutor.execute(command);
		commandExecutor.close();
		return result;
	}
}
