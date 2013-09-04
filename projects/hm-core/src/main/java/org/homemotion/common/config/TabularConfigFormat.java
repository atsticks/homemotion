package org.homemotion.common.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

public class TabularConfigFormat implements ConfigFormat {

	private ConfigSection section;

	@Override
	public String getId() {
		return "tabular";
	}

	@Override
	public void init(ConfigSection section) {
		this.section = section;
	}

	@Override
	public void release(){
		// empty
	}
	
	@Override
	public void parseLine(String line) {
		if (line.trim().isEmpty()) {
			return;
		}
		line = line.trim();
		if (line.startsWith("@")) {
			readHeader(line);
			return;
		} else if (line.startsWith("#") || line.trim().isEmpty()) {
			// comment
			return;
		} else {
			StringTokenizer tokenizer = new StringTokenizer(line, "\t ", false);
			String[] values = new String[tokenizer.countTokens()];
			if (section.getColumnNames().isEmpty()) {
				for (int i = 0; i < values.length; i++) {
					section.addColumn("col" + i);
				}
			}
			for (int i = 0; i < values.length; i++) {
				String token = (String) tokenizer.nextElement();
				if ("-".equals(token)) {
					values[i] = "";
				}
				else if ("\\-".equals(values[i])) {
					values[i] = "-";
				} else {
					values[i] = token;
				}
			}
			if (section.getColumnNames().size() < values.length) {
				throw new IllegalArgumentException(
						"Invalid configuration: column count does not match.");
			}
			Row row = new Row();
			for(int i=0; i<values.length;i++){
				row.put(section.getColumn(i), values[i]);
			}
			this.section.addRow(row);
		}
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
