/**
 * 
 */
package com.github.nnest.sparrow;

import java.util.List;

/**
 * default elastic settings
 * 
 * @author brad.wu
 * @since 0.0.1
 * @version 0.0.1
 */
public class DefaultElasticSettings implements ElasticSettings {
	private List<ElasticHost> hosts = null;

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.github.nnest.sparrow.ElasticSettings#getHosts()
	 */
	@Override
	public List<ElasticHost> getHosts() {
		return this.hosts;
	}

	/**
	 * @param hosts
	 *            the hosts to set
	 */
	public void setHosts(List<ElasticHost> hosts) {
		assert hosts != null && hosts.size() != 0 : "Hosts cannot be null or empty.";

		this.hosts = hosts;
	}
}
