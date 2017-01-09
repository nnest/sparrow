/**
 * 
 */
package com.github.nnest.sparrow;

import java.util.List;

/**
 * elastic settings, describe how to connect the elastic server
 * 
 * @author brad.wu
 * @since 0.0.1
 * @version 0.0.1
 */
public interface ElasticSettings {
	/**
	 * get hosts
	 * 
	 * @return host list
	 */
	List<ElasticHost> getHosts();
}
