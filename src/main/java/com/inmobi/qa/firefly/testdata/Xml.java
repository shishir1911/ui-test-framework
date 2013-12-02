package com.inmobi.qa.firefly.testdata;

import java.io.File;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.XMLConfiguration;
import org.testng.Assert;

/**
 * This class helps to read test data from XML files
 * 
 * @author priyadarshi.kunal
 */

public class Xml implements TestDataGetter {
	private XMLConfiguration xmlConfiguration;

	public Xml(File file) {
		try {
			xmlConfiguration = new XMLConfiguration(file);
		} catch (ConfigurationException e) {
			Assert.fail("Error reading XML test-data file: "
					+ file.getAbsolutePath());
		}
	}

	/**
	 * Reads test data and returns the value key is the path to test data nodes
	 * in xml specified in simple hierarchy structure with dots (.) notation.
	 * The dot (.) notation supports indices (e.g. "databases.database(1).url").
	 * For more complex cases XPaths (e.g.
	 * "databases/database[name = 'production']/url") can be used as well. e.g.
	 * "FirebugTest.username" key would return the "username" node value under
	 * "FirebugTest" node.
	 */

	public String get(String key) {
		return xmlConfiguration.getString(key);
	}
}
