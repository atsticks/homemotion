package org.homemotion.util.impl;

import org.homemotion.util.FileEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.homemotion.util.FileObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Singleton
public class FileObserverImpl implements FileObserver {

	private Map<File, TailRunner> targetFiles = new ConcurrentHashMap<File, TailRunner>();

	private static final Logger LOGGER = LoggerFactory
			.getLogger(FileObserverImpl.class);

	@Inject
	private javax.enterprise.event.Event<FileEvent> filevent;

	public synchronized void addFile(File targetFile, String charset) {
		if (!isFileObserved(targetFile)) {
			LOGGER.info("Adding file to observer: " + targetFile + "...");
			TailRunner thread = new TailRunner();
			thread.targetFile = targetFile;
			thread.charset = charset;
			thread.start();
			this.targetFiles.put(targetFile, thread);
		}
	}

	public synchronized void removeFile(File targetFile) {
		LOGGER.info("Removing file from observer: " + targetFile + "...");
		TailRunner thread = this.targetFiles.remove(targetFile);
		if (thread != null) {
			thread.stopRunning();
		}
	}

	public synchronized void stop() {
		LOGGER.info("Stopping file observer...");
		for (Iterator<Map.Entry<File, TailRunner>> iter = targetFiles
				.entrySet().iterator(); iter.hasNext();) {
			Map.Entry<File, TailRunner> tailEntry = iter.next();
			LOGGER.info("Removing file from observer: " + tailEntry.getKey()
					+ "...");
			tailEntry.getValue().stopRunning();
			iter.remove();
		}
	}

	protected void triggerLine(File file, String line) {
		LOGGER.debug("Line triggered in: " + file + ": " + line);
		filevent.fire(new FileEvent(file, line));
	}

	private final class TailRunner extends Thread {
		private volatile boolean running = false;
		private volatile File targetFile;
		private String charset;

		public void run() {
			running = true;
			BufferedReader input = null;
			try {
				InputStreamReader fileReader = new InputStreamReader(
						new FileInputStream(targetFile),
						Charset.forName(charset)); // "ISO-8859-1"));
				input = new BufferedReader(fileReader);
				String line = null;
				while (running) {
					if ((line = input.readLine()) != null) {
						triggerLine(targetFile, line);
						continue;
					}
					try {
						Thread.sleep(1000L);
					} catch (InterruptedException x) {
						Thread.currentThread().interrupt();
						break;
					}
				}
				input.close();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (input != null) {
					try {
						input.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}

		public void stopRunning() {
			this.running = false;
		}

	}

	public boolean isFileObserved(File file) {
		return this.targetFiles.containsKey(file);
	}

}
