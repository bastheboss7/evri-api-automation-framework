package com.matschie.general.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Thread-safe utility class for loading and accessing properties files.
 * Properties are loaded once at class initialization for optimal performance.
 */
public class PropertiesHandlers {
	
	private static final Properties CONFIG_PROPERTIES = loadProperties("config.properties", true);
	private static final Properties SECRET_PROPERTIES = loadProperties("secret.properties", false);

	/**
	 * Get configuration property value by key.
	 * @param key property key
	 * @return property value or null if not found
	 */
	public static String config(String key) {
		return CONFIG_PROPERTIES.getProperty(key);
	}

	/**
	 * Get secret property value by key.
	 * @param key property key
	 * @return property value or null if not found
	 */
	public static String secret(String key) {
		return SECRET_PROPERTIES.getProperty(key);
	}

	/**
	 * Load properties file from classpath.
	 * @param filename properties filename (e.g., "config.properties")
	 * @param required if true, throws exception if file not found; if false, returns empty Properties
	 * @return loaded Properties object
	 * @throws RuntimeException if file cannot be loaded and required is true
	 */
	private static Properties loadProperties(String filename, boolean required) {
		Properties properties = new Properties();
		try (InputStream inputStream = PropertiesHandlers.class.getClassLoader()
				.getResourceAsStream(filename)) {
			if (inputStream == null) {
				if (required) {
					throw new RuntimeException("Unable to find required '" + filename + "' file in classpath");
				}
				// Return empty properties for optional files
				return properties;
			}
			properties.load(inputStream);
		} catch (IOException e) {
			throw new RuntimeException("Failed to load '" + filename + "': " + e.getMessage(), e);
		}
		return properties;
	}

}