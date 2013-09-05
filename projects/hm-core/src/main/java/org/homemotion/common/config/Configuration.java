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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.io.LineReader;

public class Configuration implements Serializable {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 27883158298790551L;

	private static final Logger LOGGER = LoggerFactory
			.getLogger(Configuration.class);

	private String configId;

	private Map<String, ConfigSection> sections = new HashMap<String, ConfigSection>();

	public Configuration(String configId, Map<String, ConfigFormat> formats,
			URL... resourceLocations) {
		this.configId = configId;
		for (URL resource : resourceLocations) {
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
				load(contents, formats, resource);
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

	public Configuration(String configId, Map<String, ConfigFormat> formats,
			String resourceLocation) {
		this.configId = configId;
		Enumeration<URL> resources;
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
				load(contents, formats, resource);
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

	protected void load(String contents, Map<String, ConfigFormat> formats,
			URL resource) throws IOException {
		ConfigSection currentSection = null;
		LineReader lr = new LineReader(new StringReader(contents));
		String line = lr.readLine();
		while (line != null) {
			try {
				String trimmedLine = line.trim();
				if (trimmedLine.isEmpty() || trimmedLine.startsWith("#")) {
					continue;
				} else if (trimmedLine.startsWith("[")
						&& trimmedLine.contains("]")) {
					int end = trimmedLine.lastIndexOf("]");
					String sectionId = trimmedLine.substring(1, end);
					String formatId = "tabular";
					String part2 = trimmedLine.substring(end + 1);
					if (part2.trim().startsWith("{") && part2.endsWith("}")) {
						formatId = part2.trim()
								.substring(1, part2.length() - 1);
					}
					ConfigFormat format = formats.get(formatId);
					if (currentSection != null) {
						currentSection.getFormat().release();
					}
					currentSection = getSection(sectionId, format, true);
				} else {
					if (currentSection == null) {
						currentSection = getSection("", formats.get(""),
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
		if (currentSection != null) {
			currentSection.getFormat().release();
		}
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
