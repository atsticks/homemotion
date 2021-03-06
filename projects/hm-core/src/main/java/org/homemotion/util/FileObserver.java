package org.homemotion.util;

import java.io.File;

public interface FileObserver {

	public final static String EVENT_IDENTIFIER = "observedFileChange";
	
	public void addFile(File targetFile, String charset);
	public void removeFile(File file);
	public boolean isFileObserved(File file);
	
}
