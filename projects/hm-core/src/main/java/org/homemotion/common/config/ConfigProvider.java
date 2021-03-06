package org.homemotion.common.config;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Enumeration;
//
//public final class ConfigProvider {
//
//	private ConfigProvider() {
//		// Singleton
//	}
//
//	public Configuration ofBinding(String binding) {
//		return ofResource(binding, "/cfg/" + binding + ".conf");
//	}
//
//	public static Configuration ofResource(String id, String resource) {
//		Configuration config = new Configuration(id);
//		BufferedReader input = null;
//		try {
//			Enumeration<URL> urls = ConfigProvider.class.getClassLoader()
//					.getResources(resource);
//			while (urls.hasMoreElements()) {
//				URL url = (URL) urls.nextElement();
//				try {
//					InputStreamReader fileReader = new InputStreamReader(
//							url.openStream(), Charset.forName("UTF-8"));
//					input = new BufferedReader(fileReader);
//					String line = null;
//					String key = null;
//					ConfigSection section = null;
//					while ((line = input.readLine()) != null) {
//						line = line.trim();
//						if (line.startsWith("[") && line.endsWith("]")) {
//							key = line.substring(1, line.length() - 1);
//							if (key != null) {
//								section = config.getSection(key, true);
//							}
//							else{
//								if(section==null){
//									section = config.getSection("", true);
//								}
//								section.parseLine(line);
//							}
//						} else {
//							section.parseLine(line);
//						}
//					}
//				} catch (IOException e) {
//					e.printStackTrace();
//				} finally {
//					if (input != null) {
//						try {
//							input.close();
//						} catch (IOException e) {
//							e.printStackTrace();
//						}
//					}
//				}
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return config;
//	}
//
//	public static Configuration ofURL(String id, URL url) {
//		Configuration config = new Configuration(id);
//		BufferedReader input = null;
//		try {
//			InputStreamReader fileReader = new InputStreamReader(
//					url.openStream(), Charset.forName("UTF-8"));
//			input = new BufferedReader(fileReader);
//			String line = null;
//			String key = null;
//			ConfigSection section = null;
//			while ((line = input.readLine()) != null) {
//				line = line.trim();
//				if (line.startsWith("[") && line.endsWith("]")) {
//					key = line.substring(1, line.length() - 1);
//					if (key != null) {
//						section = config.getSection(key, true);
//					}
//					else{
//						if(section==null){
//							section = config.getSection("", true);
//						}
//						section.parseLine(line);
//					}
//				} else {
//					section.parseLine(line);
//				}
//			}
//		} catch (IOException e) {
//			e.printStackTrace();
//		} finally {
//			if (input != null) {
//				try {
//					input.close();
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//			}
//		}
//		return config;
//	}
//}
