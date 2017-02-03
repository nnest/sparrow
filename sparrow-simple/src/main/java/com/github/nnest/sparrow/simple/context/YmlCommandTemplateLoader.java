/**
 * 
 */
package com.github.nnest.sparrow.simple.context;

import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;

import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import com.github.nnest.sparrow.simple.CommandTemplate;
import com.github.nnest.sparrow.simple.CommandTemplateLoader;

/**
 * YAML command template loader
 * 
 * @author brad.wu
 * @since 0.0.1
 * @version 0.0.1
 */
public class YmlCommandTemplateLoader implements CommandTemplateLoader {
	/**
	 * (non-Javadoc)
	 * 
	 * @see com.github.nnest.sparrow.simple.CommandTemplateLoader#load(java.io.InputStream)
	 */
	@Override
	public CommandTemplate[] load(InputStream stream) {
		Yaml yaml = new Yaml(new Constructor(DefaultCommandTemplate.class));
		Iterable<Object> iterable = yaml.loadAll(stream);
		List<CommandTemplate> templates = new LinkedList<CommandTemplate>();
		for (Object item : iterable) {
			templates.add((CommandTemplate) item);
		}
		return templates.toArray(new CommandTemplate[templates.size()]);
	}
}
