/**
 * 
 */
package com.github.nnest.sparrow.simple;

import java.util.Optional;

/**
 * command template context
 * 
 * @author brad.wu
 * @since 0.0.1
 * @version 0.0.1
 */
public interface CommandTemplateContext {
	/**
	 * find command template by name. never throw exception.
	 * 
	 * @param name
	 *            name of template
	 * @return template or absent optional.
	 */
	Optional<CommandTemplate> find(String name);

	/**
	 * register template
	 * 
	 * @param template
	 *            template
	 */
	void register(CommandTemplate template);
}
