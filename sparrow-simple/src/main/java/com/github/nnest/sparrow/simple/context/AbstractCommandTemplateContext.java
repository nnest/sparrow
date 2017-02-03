/**
 * 
 */
package com.github.nnest.sparrow.simple.context;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.nnest.sparrow.simple.CommandTemplate;
import com.github.nnest.sparrow.simple.CommandTemplateContext;
import com.github.nnest.sparrow.simple.CommandTemplateLoader;

/**
 * abstract command template context
 * 
 * @author brad.wu
 * @since 0.0.1
 * @version 0.0.1
 */
public abstract class AbstractCommandTemplateContext implements CommandTemplateContext {
	private CommandTemplateContext parentContext = null;
	private String[] configPath = null;
	private Map<String, CommandTemplate> templates = new HashMap<String, CommandTemplate>();

	private CommandTemplateLoader loader = null;

	private Logger logger = LoggerFactory.getLogger(getClass());

	public AbstractCommandTemplateContext() {
	}

	public AbstractCommandTemplateContext(String configPath) {
		this(null, configPath);
	}

	public AbstractCommandTemplateContext(String... configPath) {
		this(null, configPath);
	}

	public AbstractCommandTemplateContext(CommandTemplateContext parentContext) {
		this();
		this.setParentContext(parentContext);
	}

	public AbstractCommandTemplateContext(CommandTemplateContext parentContext, String configPath) {
		this(parentContext, new String[] { configPath });
	}

	public AbstractCommandTemplateContext(CommandTemplateContext parentContext, String... configPath) {
		this.setParentContext(parentContext);
		this.setConfigPath(configPath);
	}

	/**
	 * @return the configPath
	 */
	public String[] getConfigPath() {
		return configPath;
	}

	/**
	 * @param configPath
	 *            the configPath to set
	 */
	public void setConfigPath(String[] configPath) {
		this.configPath = configPath;
	}

	/**
	 * @return the parentContext
	 */
	public CommandTemplateContext getParentContext() {
		return parentContext;
	}

	/**
	 * @param parentContext
	 *            the parentContext to set
	 */
	protected void setParentContext(CommandTemplateContext parentContext) {
		this.parentContext = parentContext;
	}

	/**
	 * @return the loader
	 */
	public CommandTemplateLoader getLoader() {
		if (this.loader == null) {
			synchronized (this) {
				if (this.loader == null) {
					this.loader = this.createLoader();
				}
			}
		}
		return this.loader;
	}

	/**
	 * create loader
	 * 
	 * @return loader
	 */
	protected CommandTemplateLoader createLoader() {
		return new YmlCommandTemplateLoader();
	}

	/**
	 * @param loader
	 *            the loader to set
	 */
	public void setLoader(CommandTemplateLoader loader) {
		this.loader = loader;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.github.nnest.sparrow.simple.CommandTemplateContext#find(java.lang.String)
	 */
	@Override
	public Optional<CommandTemplate> find(String name) {
		CommandTemplate template = this.templates.get(name);
		if (template == null) {
			if (this.getParentContext() == null) {
				return Optional.empty();
			} else {
				return this.getParentContext().find(name);
			}
		} else {
			return Optional.of(template);
		}
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.github.nnest.sparrow.simple.CommandTemplateContext#register(com.github.nnest.sparrow.simple.CommandTemplate)
	 */
	@Override
	public void register(CommandTemplate template) {
		assert template != null : "Template cannot be null.";

		this.templates.put(template.getName(), template);
	}

	/**
	 * register templates
	 * 
	 * @param templates
	 *            templates
	 */
	public void register(CommandTemplate... templates) {
		assert templates != null : "Tempaltes cannot be null.";

		for (CommandTemplate template : templates) {
			this.register(template);
		}
	}

	/**
	 * load templates from configuration path
	 */
	public abstract void loadTemplates();

	/**
	 * @return the logger
	 */
	protected Logger getLogger() {
		return logger;
	}
}
