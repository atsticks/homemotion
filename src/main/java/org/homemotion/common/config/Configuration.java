package org.homemotion.common.config;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.io.StringReader;
import java.net.URL;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.ServiceLoader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.io.LineReader;

public class Configuration implements Serializable {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 27883158298790551L;

	private static final ConfigFormat DEFAULT_FORMAT = new TabularConfigFormat();

	private static Map<String, Class<ConfigFormat>> FORMATS = new HashMap<String, Class<ConfigFormat>>();

	static {
		ServiceLoader<ConfigFormat> loader = ServiceLoader
				.load(ConfigFormat.class);
		for (ConfigFormat f : loader) {
			FORMATS.put(f.getId(), (Class<ConfigFormat>) f.getClass());
		}
	}

	private static final Logger LOGGER = LoggerFactory.getLogger(Configuration.class);

	private String configId;

	private Map<String, ConfigSection> sections = new HashMap<String, ConfigSection>();

	public Configuration(String configId) {
		this.configId = configId;
		Enumeration<URL> resources;
		String resourceLocation = createBindingLocation(configId);
		try {
			resources = getClass().getClassLoader().getResources(
					resourceLocation);
		} catch (IOException e) {
			LOGGER.info("No configuration found for " + configId
					+ ", path was " + resourceLocation, e);
			return;
		}
		while (resources.hasMoreElements()) {
			URL resource = (URL) resources.nextElement();
			InputStream is = null;
			try {
				is = resource.openStream();
				byte[] inputArray = new byte[1024];
				int read = is.read(inputArray);
				ByteArrayOutputStream bos = new ByteArrayOutputStream();
				while (read > 0) {
					bos.write(inputArray, 0, read);
					read = is.read(inputArray);
				}
				String contents = new String(bos.toByteArray());
				load(contents, resource);
			} catch (Exception e) {
				LOGGER.error("Error reading resource: " + resource, e);
			} finally {
				if (is != null) {
					try {
						is.close();
					} catch (Exception e2) {
						LOGGER.warn("Error closing resource: " + resource, e2);
					}
				}
			}
		}
	}

	protected String createBindingLocation(String configId) {
		return "cfg/" + configId + ".conf";
	}

	protected void load(String contents, URL resource) throws IOException {
		ConfigSection currentSection = null;
		LineReader lr = new LineReader(new StringReader(contents));
		String line = lr.readLine();
		while (line != null) {
			try {
				String trimmedLine = line.trim();
				if (trimmedLine.isEmpty() || trimmedLine.startsWith("#")) {
					continue;
				} else if (trimmedLine.startsWith("[") && trimmedLine.contains("]")) {
					int end = trimmedLine.lastIndexOf("]");
					String sectionId = trimmedLine.substring(1, end);
					String formatId = "tabular";
					String part2 = trimmedLine.substring(end+1);
					if (part2.trim().startsWith("{") && part2.endsWith("}")) {
						formatId = part2.trim().substring(1, part2.length() - 1);
					}
					ConfigFormat format = getFormat(formatId);
					if(currentSection!=null){
						currentSection.getFormat().release();
					}
					currentSection = getSection(sectionId, format, true);
				} else {
					if (currentSection == null) {
						currentSection = getSection("", getDefaultFormat(),
								true);
					}
					currentSection.parseLine(line);
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				line = lr.readLine();
			}
		}
		if(currentSection!=null){
			currentSection.getFormat().release();
		}
	}

	private ConfigFormat getDefaultFormat() {
		return new TabularConfigFormat();
	}

	private ConfigFormat getFormat(String formatId) {
		Class<ConfigFormat> f = FORMATS.get(formatId);
		if (f != null) {
			try {
				return f.newInstance();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return new TabularConfigFormat();
	}

	public String getConfigId() {
		return configId;
	}

	public ConfigSection getSection(String id) {
		return sections.get(id);
	}

	public ConfigSection getSection(String key, ConfigFormat format,
			boolean createIfMissing) {
		ConfigSection section = this.sections.get(key);
		if (section == null && createIfMissing) {
			section = new ConfigSection(key, format);
			this.sections.put(key, section);
		}
		return section;
	}

	public Collection<String> getSectionIds() {
		return this.sections.keySet();
	}

	public Collection<ConfigSection> getSections() {
		return this.sections.values();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((configId == null) ? 0 : configId.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Configuration other = (Configuration) obj;
		if (configId == null) {
			if (other.configId != null)
				return false;
		} else if (!configId.equals(other.configId))
			return false;
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Configuration [id=" + configId + ", sections=" + sections + "]";
	}

}
