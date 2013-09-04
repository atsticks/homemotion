package org.homemotion.common.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class ConfigSection implements Iterable<Row> {
	private String id;
	private ConfigFormat format;
	private List<String> columnNames = new ArrayList<String>(6);
	private List<Row> rows = new ArrayList<Row>();

	public ConfigSection(String id, ConfigFormat format) {
		this.id = id;
		format.init(this);
		this.format = format;
	}

	public String getId() {
		return this.id;
	}

	public ConfigFormat getFormat() {
		return this.format;
	}

	public List<String> getColumnNames() {
		return Collections.unmodifiableList(this.columnNames);
	}

	@Override
	public Iterator<Row> iterator() {
		return Collections.unmodifiableList(rows).iterator();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ConfigSection [id=" + id + ", columnNames=" + columnNames
				+ ", rows=" + rows + "]";
	}

	public void parseLine(String line) {
		format.parseLine(line);
	}

	public void addColumn(String colName) {
		this.columnNames.add(colName);
	}

	public void addRow(Row row) {
		this.rows.add(row);
	}

	public void setColumnNames(String... columnNames) {
		this.columnNames = Arrays.asList(columnNames);
	}

	public String getColumn(int index) {
		return this.columnNames.get(index);
	}

}
