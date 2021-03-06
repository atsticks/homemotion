package org.homemotion.util;

import java.io.File;
import java.io.Serializable;

public final class FileEvent implements Serializable{

	private static final long serialVersionUID = 1163759363496687970L;
	private File file;
	private String line;
	
	public FileEvent(File file, String line) {
		this.file=file;
		this.line=line;
	}
	
	public File getFile() {
		return file;
	}
	public String getLine() {
		return line;
	}

	@Override
	public String toString() {
		return "FileEvent [file=" + file + ", line=" + line + "]";
	}
	
	
	
}
