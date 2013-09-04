package org.homemotion.common.config;

public interface ConfigFormat {
	public String getId();
	public void init(ConfigSection section);
	public void parseLine(String line);
	public void release();
}
