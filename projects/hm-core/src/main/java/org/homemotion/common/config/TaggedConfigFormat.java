package org.homemotion.common.config;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TaggedConfigFormat implements ConfigFormat {

	private ConfigSection section;

	private static final String ROW_START = "<row>";
	private static final String ROW_END = "</row>";

	private StringBuilder rowBuilder = new StringBuilder();
	private boolean inRow = false;

	@Override
	public String getId() {
		return "tagged";
	}

	@Override
	public void init(ConfigSection section) {
		this.section = section;
	}

	@Override
	public void parseLine(String line) {
		if (line.trim().isEmpty()) {
			return;
		}
		if (line.trim().startsWith("@")) {
			readHeader(line);
		} else if (line.trim().startsWith("#")) {
			// comment
		} else {
			rowBuilder.append(line);
		}
	}

	public void release() {
		String content = rowBuilder.toString();
		// row<<
		// id:: sayHello
		// strategy:: Java
		// script::
		// System.out.println("Hello World at "+ new java.util.Date() +"!");
		// // now it is finished here...
		// >>
		int rowStartIndex = content.indexOf(ROW_START);
		int rowEndIndex = content.indexOf(ROW_END);
		while (rowStartIndex >= 0) {
			String rowEntry = null;
			if (rowEndIndex > 0) {
				rowEntry = content.substring(
						rowStartIndex + ROW_START.length(), rowEndIndex);
			} else {
				rowEntry = content
						.substring(rowStartIndex + ROW_START.length());
			}
			rowEntry = rowEntry.trim();
			parseRow(rowEntry);
			rowStartIndex = content.indexOf(ROW_START, rowEndIndex);
			rowEndIndex = content.indexOf(ROW_END,
					rowEndIndex + ROW_END.length());
		}
	}

	private void parseRow(String rowContent) {
		Row row = new Row();
		for (String colName : this.section.getColumnNames()) {
			try {
				String startTag = '<' + colName + '>';
				String endTag = "</" + colName + '>';
				int startIndex = rowContent.indexOf(startTag);
				int endIndex = rowContent.indexOf(endTag);
				if (startIndex >= 0 && endIndex > 0) {
					String value = rowContent.substring(
							startIndex + startTag.length(), endIndex);
					row.put(colName, value);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		section.addRow(row);
	}

	private void readHeader(String line) {
		List<String> columnNames = new ArrayList<String>();
		String[] colNames = line.split("@");
		for (int i = 0; i < colNames.length; i++) {
			String colName = colNames[i].trim();
			if (colName.isEmpty()) {
				continue;
			}
			columnNames.add(colName);
		}
		if (!section.getColumnNames().isEmpty()) {
			if (!section.getColumnNames().equals(columnNames)) {
				throw new IllegalArgumentException(
						"Incompatible column names encountered: "
								+ section.getColumnNames() + " and "
								+ columnNames);
			}
		} else {
			section.setColumnNames(columnNames.toArray(new String[columnNames
					.size()]));
		}
	}

}
