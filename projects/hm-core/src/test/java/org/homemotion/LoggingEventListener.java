package org.homemotion;

import java.util.Random;

import javax.enterprise.event.Observes;
import javax.inject.Singleton;

import org.homemotion.common.message.AbstractMessage;

@Singleton
public final class LoggingEventListener {

	public void notified(@Observes AbstractMessage event) {
		try {
			Thread.sleep(new Random().nextInt(200));
		} catch (Exception e) {
			e.printStackTrace();
		}
		java.lang.System.err.println("LoggingEventListener -> Event("
				+ Thread.currentThread().getId() + "): " + event);
	}

}
