package com.inmobi.qa.firefly.testdata;

import java.io.File;

import com.inmobi.qa.firefly.enums.FileType;

public class TestData {
	private TestDataGetter testDataGetter;

	public TestData(String filePath, FileType fileType) {
		if (fileType.equals(FileType.EXCEL)) {
			testDataGetter = new Excel(new File(filePath));
		} else {
			testDataGetter = new Xml(new File(filePath));
		}
	}

	public String get(String key) {
		return testDataGetter.get(key);
	}
}
