/**
 * 
 */
package com.github.nnest.sparrow.simple;

import java.io.InputStream;

/**
 * command template loader
 * 
 * @author brad.wu
 * @since 0.0.1
 * @version 0.0.1
 */
public interface CommandTemplateLoader {
	/**
	 * load configuration file to templates
	 * 
	 * @param stream
	 *            input stream
	 * @return command template
	 */
	CommandTemplate[] load(InputStream stream);
}
