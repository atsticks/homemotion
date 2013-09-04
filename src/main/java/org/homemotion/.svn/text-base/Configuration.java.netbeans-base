package org.homemotion;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public final class Configuration {

	private static final Logger LOG = LoggerFactory.getLogger(Configuration.class);

	private static final Map<String, Configuration> configs = new ConcurrentHashMap<String, Configuration>();

	static {
		// auto load
		Configuration loadConfig = new Configuration("autoload",
				"META-INF/autoload.conf");
		ConfigSection section = loadConfig.getDefaultSection();
		for (Map.Entry<String, String> loadEntry : section.getAsMap()
				.entrySet()) {
			String key = loadEntry.getKey();
			String resource = loadEntry.getValue();
			try {
				configs.put(key, new Configuration(key, resource));
			} catch (Exception e) {
				LOG.error("Error preloading configuration '" + key + "' from '"
						+ resource + "'.", e);
			}
		}
	}

	private String name;

	private Map<String, ConfigSection> sections = new HashMap<String, ConfigSection>();

	private Configuration(String name, String resource) {
		if (name == null) {
			throw new IllegalArgumentException("Name must not be null.");
		}
		this.name = name;
		ConfigSection section = new ConfigSection("");
		this.sections.put("", section);
		Enumeration<URL> configurations;
		try {
			configurations = getClass().getClassLoader().getResources(resource);
		} catch (IOException e1) {
			LOG.error("Could not read config.");
			return;
		}
		String sectionName = "";
		while (configurations.hasMoreElements()) {
			BufferedReader reader = null;
			URL url = null;
			try {
				url = configurations.nextElement();
				reader = new BufferedReader(new InputStreamReader(
						url.openStream()));
				String line = reader.readLine();
				while (line != null) {
					line = line.trim();
					if (line.startsWith("[") && line.endsWith("]")) {
						sectionName = line.substring(1, line.length() - 1);
						if (section == null
								|| !section.getName().equals(sectionName)) {
							section = sections.get(sectionName);
							if (section == null) {
								section = new ConfigSection(sectionName);
								sections.put(sectionName, section);
							}
						}
					} else {
						section.readLine(line);
					}
					line = reader.readLine();
				}
			} catch (Exception e) {
				LOG.error("Could not load configuration " + url, e);
			} finally {
				if (reader != null) {
					try {
						reader.close();
					} catch (IOException e) {
						LOG.warn("Error closing resource " + url, e);
					}
				}
			}
		}
	}

	public String getName() {
		return name;
	}

	public ConfigSection getDefaultSection() {
		return getSection("");
	}

	public ConfigSection getSection(String name) {
		return sections.get(name);
	}

	public static Configuration getConfiguration(String name) {
		return configs.get(name);
	}

	public static Configuration loadConfiguration(String name, String resource) {
		return loadConfiguration(name, resource, false);
	}
	
	public static Configuration loadConfiguration(String name, String resource, boolean persist) {
		if (configs.containsKey(name)) {
			throw new IllegalStateException("Config already loaded: " + name);
		}
		Configuration cfg = new Configuration(name, resource);
		if(persist){
			configs.put(name, new Configuration(name, resource));
		}
		return cfg;
	}

	public static void main(String[] args) {
		Configuration conf;
	}

	public Collection<ConfigSection> getSections(String expression) {
		List<ConfigSection> result = new ArrayList<ConfigSection>();
		for(ConfigSection current: sections.values()){
			if(current.getName().matches(expression)){
				result.add(current);
			}
		}
		return result;
	}
	
	public Collection<ConfigSection> getSections() {
		return Collections.unmodifiableCollection(sections.values());
	}

}
