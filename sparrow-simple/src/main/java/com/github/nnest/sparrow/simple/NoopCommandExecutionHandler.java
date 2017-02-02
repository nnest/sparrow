/**
 * 
 */
package com.github.nnest.sparrow.simple;

/**
 * exactly do nothing but print system
 * 
 * @author brad.wu
 * @since 0.0.1
 * @version 0.0.1
 */
public class NoopCommandExecutionHandler implements CommandExecutionHandler {
	/**
	 * (non-Javadoc)
	 * 
	 * @see com.github.nnest.sparrow.simple.CommandExecutionHandler#onSuccess(java.lang.Object)
	 */
	@Override
	public void onSuccess(Object response) {
		System.out.println(response);
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.github.nnest.sparrow.simple.CommandExecutionHandler#onFailure(java.lang.Exception)
	 */
	@Override
	public void onFailure(Exception exception) {
		exception.printStackTrace();
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.github.nnest.sparrow.simple.CommandExecutionHandler#onClientCloseFailure(java.lang.Exception)
	 */
	@Override
	public void onClientCloseFailure(Exception exception) {
		exception.printStackTrace();
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.github.nnest.sparrow.simple.CommandExecutionHandler#onClientPrepareException(java.lang.Exception)
	 */
	@Override
	public void onClientPrepareException(Exception exception) {
		exception.printStackTrace();
	}
}
