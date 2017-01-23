/**
 * 
 */
package com.github.nnest.sparrow.command.document;

import java.util.LinkedList;
import java.util.List;

import com.github.nnest.sparrow.ElasticCommand;
import com.github.nnest.sparrow.ElasticCommandKind;
import com.github.nnest.sparrow.ElasticDocumentAnalyzer;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;

/**
 * multiple get. contains a get command set, each inner get is same as
 * {@linkplain Get}, but can carry ids not one id.
 * 
 * @author brad.wu
 * @since 0.0.1
 * @version 0.0.1
 */
public class MultiGet implements ElasticCommand {
	private List<Get> innerCommands = new LinkedList<Get>();

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.github.nnest.sparrow.ElasticCommand#getCommandKind()
	 */
	@Override
	public ElasticCommandKind getCommandKind() {
		return ElasticCommandKind.MULTI_GET;
	}

	/**
	 * @return the gets
	 */
	public List<Get> getInnerCommands() {
		return innerCommands;
	}

	/**
	 * @param innerCommands
	 *            the innerCommands to set
	 * @return this
	 */
	public MultiGet withInnerCommands(List<Get> innerCommands) {
		assert innerCommands != null && innerCommands.size() > 0 : "Inner commands cannot be null or empty.";

		this.innerCommands = innerCommands;
		return this;
	}

	/**
	 * push get into command
	 * 
	 * @param command
	 *            inner command
	 * @return this
	 */
	public MultiGet withCommand(Get command) {
		this.getInnerCommands().add(command);
		return this;
	}

	/**
	 * push get into command
	 * 
	 * @param documentType
	 *            document type
	 * @param ids
	 *            ids
	 * @return this
	 */
	public MultiGet withCommand(Class<?> documentType, String... ids) {
		if (ids == null || ids.length == 0) {
			// do nothing
			return this;
		}

		for (String id : ids) {
			this.withCommand(new Get(documentType, id));
		}
		return this;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.github.nnest.sparrow.ElasticCommand#analysis(com.github.nnest.sparrow.ElasticDocumentAnalyzer)
	 */
	@Override
	public ElasticCommand analysis(ElasticDocumentAnalyzer documentAnalyzer) {
		Iterables.all(this.getInnerCommands(), new Predicate<Get>() {
			/**
			 * (non-Javadoc)
			 * 
			 * @see com.google.common.base.Predicate#apply(java.lang.Object)
			 */
			@Override
			public boolean apply(Get input) {
				input.setDocumentDescriptor(documentAnalyzer.analysis(input.getDocumentType()));
				return true;
			}
		});
		return this;
	}
}
