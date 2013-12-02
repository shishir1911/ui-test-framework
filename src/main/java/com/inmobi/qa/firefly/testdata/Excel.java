package com.inmobi.qa.firefly.testdata;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.testng.Assert;

public class Excel implements TestDataGetter {
	private HSSFWorkbook workbook;

	public Excel(File file) {
		try {
			InputStream inputStream = new FileInputStream(file);
			workbook = new HSSFWorkbook(inputStream);
		} catch (FileNotFoundException e) {
			Assert.fail("Excel test-data file not found: "
					+ file.getAbsolutePath());
		} catch (IOException e) {
			Assert.fail("IOException reading test-data file: "
					+ file.getAbsolutePath());
		}
	}

	private int getColumnIndexFromHeader(HSSFSheet worksheet, String paramName) {
		HSSFRow row = worksheet.getRow(0);
		for (int i = 0; i <= row.getLastCellNum(); i++) {
			if (row.getCell(i).getStringCellValue().equals(paramName))
				return i;
		}
		throw new RuntimeException(worksheet.getSheetName()
				+ " does not have a parameter named " + paramName);
	}

	/**
	 * key is the path to test data nodes in excel specified in simple hierarchy
	 * structure with dots (.) notation. e.g. "UserPageTest.username" would
	 * return username column data from UserPageTest sheet.
	 * <p/>
	 * The indices are also supported to facilitate easy iteration. e.g.
	 * "UserPageTest.username.5" would return username column data for 5th row
	 * (0th row is header) from UserPageTest sheet.
	 */

	public String get(String key) {

		String[] keyFrags = key.split("\\.", 3);

		String sheetName = keyFrags[0];
		String paramName = keyFrags[1];
		Integer rowIndex;
		if (keyFrags.length == 3)
			rowIndex = Integer.parseInt(keyFrags[2]);
		else
			rowIndex = 1;

		HSSFSheet worksheet = workbook.getSheet(sheetName);
		int columnIndex = getColumnIndexFromHeader(worksheet, paramName);
		return worksheet.getRow(rowIndex).getCell(columnIndex)
				.getStringCellValue();
	}
}