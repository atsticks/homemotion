package org.homemotion.common.config;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.enterprise.inject.Instance;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Singleton
public class ConfigurationService {

	private static Map<String, ConfigFormat> FORMATS = new HashMap<String, ConfigFormat>();

	private static final Logger LOGGER = LoggerFactory
			.getLogger(ConfigurationService.class);

	@Inject
	public ConfigurationService(Instance<ConfigFormat> formats) {
		for (ConfigFormat f : formats) {
			try {
				FORMATS.put(f.getId(), (ConfigFormat) f.getClass()
						.newInstance());
			} catch (Exception e) {
				LOGGER.error("Error loading COnfigFOrmat: " + f, e);
			}
		}
	}

	@Produces
	@Named
	public Configuration getInjectableConfig(InjectionPoint ip) {
		Named na = ip.getAnnotated().getAnnotation(Named.class);
		return new Configuration(na.value(), FORMATS);

	}

	public Configuration getConfiguration(String configId) {
		return new Configuration(configId, FORMATS,
				getDefaultResource(configId));
	}

	public Configuration getConfiguration(String configId, String resource) {
		return new Configuration(configId, FORMATS, resource);
	}

	public Configuration getConfiguration(String configId, URL... resources) {
		return new Configuration(configId, FORMATS, resources);
	}

	public String getDefaultResource(String configId) {
		return "cfg/" + configId + ".conf";
	}
}
