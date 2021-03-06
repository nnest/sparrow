/**
 * 
 */
package com.github.nnest.sparrow.rest.command.mixins.wrapper;

/**
 * chained query example wrapper
 * 
 * @author brad.wu
 * @since 0.0.1
 * @version 0.0.1
 */
public interface ChainQueryExampleWrapper extends QueryExampleWrapper {
	/**
	 * next query example wrapper
	 * 
	 * @return wrapper
	 */
	ChainQueryExampleWrapper getNext();

	/**
	 * set next wrapper
	 * 
	 * @param nextWrapper
	 *            next wrapper
	 * @return the next wrapper
	 */
	ChainQueryExampleWrapper next(ChainQueryExampleWrapper nextWrapper);

	/**
	 * get previous wrapper
	 * 
	 * @return previous wrapper
	 */
	ChainQueryExampleWrapper getPrevious();

	/**
	 * set previous wrapper
	 * 
	 * @param previousWrapper
	 *            previous wrapper
	 * @return the previous wrapper
	 */
	ChainQueryExampleWrapper previous(ChainQueryExampleWrapper previousWrapper);

	/**
	 * get first wrapper of this chain
	 * 
	 * @return first
	 */
	ChainQueryExampleWrapper first();

	/**
	 * get last wrapper of this chain
	 * 
	 * @return last
	 */
	ChainQueryExampleWrapper last();

	/**
	 * twist a new wrapper into current chain. the new wrapper should be the new
	 * next wrapper of this, and original next wrapper should be very next of
	 * new wrapper.<br>
	 * eg.<br>
	 * Wrapper {@code A} has next {@code B} has next {@code C}, now twist a new
	 * wrapper {@code D} which has next {@code E} has next {@code F}. after
	 * twisting, the chain is change to A-&gt;D-&gt;E-&gt;F-&gt;B-&gt;C.<br>
	 * the purpose is if a wrapper can wrap a super class or interface, but when
	 * a specific instance which implements the super class or interface, find
	 * the original wrapper and twist the new wrapper into its previous wrapper,
	 * then the wrapping behavior is changed.<br>
	 * eg.<br>
	 * Wrapper {@code A} can wrap {@code ObjectA} to {@code WrappedObjectA}, it
	 * has a next wrapper {@code B}, which can wrap {@code ObjectB} to
	 * {@code WrappedObjectB}. here is a {@code ObjectC extends ObjectB}, base
	 * on current wrappers, the {@code ObjectC} should be wrapped to
	 * {@code WrappedObjectB}, but if it is expected to be wrapped to
	 * {@code WrappedObjectC}, twist wrapper {@code C} between {@code A} and
	 * {@code B}, call {@code A#twist(C)}.
	 * 
	 * @param wrapper
	 *            new wrapper
	 */
	void twist(ChainQueryExampleWrapper wrapper);
}
