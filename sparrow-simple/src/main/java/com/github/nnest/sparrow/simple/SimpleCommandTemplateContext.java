/**
 * 
 */
package com.github.nnest.sparrow.simple;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * simple command template context
 * 
 * @author brad.wu
 * @since 0.0.1
 * @version 0.0.1
 */
public class SimpleCommandTemplateContext extends AbstractCommandTemplateContext {
	public SimpleCommandTemplateContext() {
		super();
	}

	public SimpleCommandTemplateContext(CommandTemplateContext parentContext, String... configPath) {
		super(parentContext, configPath);
	}

	public SimpleCommandTemplateContext(CommandTemplateContext parentContext, String configPath) {
		super(parentContext, configPath);
	}

	public SimpleCommandTemplateContext(CommandTemplateContext parentContext) {
		super(parentContext);
	}

	public SimpleCommandTemplateContext(String... configPath) {
		super(configPath);
	}

	public SimpleCommandTemplateContext(String configPath) {
		super(configPath);
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.github.nnest.sparrow.simple.AbstractCommandTemplateContext#loadTemplates()
	 */
	@Override
	public void loadTemplates() {
		String[] configPath = this.getConfigPath();
		if (configPath == null || configPath.length == 0) {
			throw new TemplateInitException("No configuration path assigned.");
		}

		for (String path : configPath) {
			this.loadTemplates(path);
		}
	}

	/**
	 * load templates by given path or file
	 * 
	 * @param path
	 *            path
	 */
	protected void loadTemplates(String path) {
		File resource = new File(path);
		if (!resource.exists()) {
			this.getLogger().info(String.format("Path or file[%1$s] doesn't exist, try to use class path.", path));
			InputStream stream = this.getClass().getResourceAsStream(path);
			if (stream == null) {
				throw new TemplateInitException(
						String.format("Resource[%1$s] doesn't exist in class path and file system.", path));
			}
			try {
				this.loadTemplates(stream, path);
			} catch (Exception e) {
				throw new TemplateInitException(String.format("Failed to load template from resource[%1$s]", path), e);
			}
		} else {
			this.loadTemplates(resource);
		}
	}

	/**
	 * load templates by given resource
	 * 
	 * @param resource
	 *            resource
	 */
	protected void loadTemplates(File resource) {
		if (resource.isDirectory()) {
			File[] files = resource.listFiles();
			for (File file : files) {
				this.loadTemplates(file);
			}
		} else if (resource.isFile()) {
			try {
				loadTemplates(new FileInputStream(resource), resource.getAbsolutePath());
			} catch (Exception e) {
				throw new TemplateInitException(
						String.format("Failed to load template from resource[%1$s]", resource.getAbsolutePath()), e);
			}
		}
	}

	/**
	 * load templates, and
	 * 
	 * @param stream
	 *            stream
	 * @param location
	 *            stream location
	 */
	protected void loadTemplates(InputStream stream, String location) {
		try {
			CommandTemplate[] templates = this.getLoader().load(stream);
			if (templates != null) {
				for (CommandTemplate template : templates) {
					this.register(template);
				}
			}
		} finally {
			if (stream != null) {
				try {
					stream.close();
				} catch (IOException e) {
					this.getLogger().warn(String.format("Failed to close file input stream of %1$s", location));
				}
			}
		}
	}
}
