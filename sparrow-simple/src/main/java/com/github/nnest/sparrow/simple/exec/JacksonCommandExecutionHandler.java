/**
 * 
 */
package com.github.nnest.sparrow.simple.exec;

import com.github.nnest.sparrow.simple.CommandExecutionHandler;

/**
 * Jackson command executor handler
 * 
 * @author brad.wu
 * @since 0.0.1
 * @version 0.0.1
 */
public class JacksonCommandExecutionHandler implements CommandExecutionHandler {

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.github.nnest.sparrow.simple.CommandExecutionHandler#onSuccess(java.lang.Object)
	 */
	@Override
	public void onSuccess(Object response) {
		// TODO Auto-generated method stub

	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.github.nnest.sparrow.simple.CommandExecutionHandler#onFailure(java.lang.Exception)
	 */
	@Override
	public void onFailure(Exception exception) {
		// TODO Auto-generated method stub

	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.github.nnest.sparrow.simple.CommandExecutionHandler#onClientCloseFailure(java.lang.Exception)
	 */
	@Override
	public void onClientCloseFailure(Exception exception) {
		// TODO Auto-generated method stub

	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.github.nnest.sparrow.simple.CommandExecutionHandler#onClientPrepareException(java.lang.Exception)
	 */
	@Override
	public void onClientPrepareException(Exception exception) {
		// TODO Auto-generated method stub

	}

}
