package org.homemotion.common.message.impl;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import javax.enterprise.event.Observes;
import javax.inject.Named;
import javax.inject.Singleton;

import org.homemotion.common.message.AbstractMessage;
import org.homemotion.common.message.Severity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Anatole
 */
@Singleton
@Named("EventCollector")
public final class MessageCollector {

	private static final Logger LOGGER = LoggerFactory.getLogger(MessageCollector.class);

	private int maxSize = 2000;

	private List<AbstractMessage> events = new LinkedList<AbstractMessage>();

	public void notified(@Observes AbstractMessage event) {
		LOGGER.debug("Event: " + event.toString());
		events.add(event);
		if (events.size() > maxSize) {
			events.remove(0);
		}
	}

	public int getMaxEventCount() {
		return maxSize;
	}

	public List<AbstractMessage> getCurrentEvents() {
		return Collections.unmodifiableList(events);
	}

	public void clear() {
		this.events.clear();
	}

	public void setMaxEventCount(int maxSize) {
		if (maxSize < 0) {
			this.maxSize = maxSize;
		} else {
			throw new IllegalArgumentException("maxSize must be > 0.");
		}
	}

	public int getMaxSize() {
		return maxSize;
	}

	public void setMaxSize(int maxSize) {
		this.maxSize = maxSize;
	}

	public List<AbstractMessage> getEvents() {
		return events;
	}

}
