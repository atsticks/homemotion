package org.homemotion.devices.spi.impl.ips.impl;


import org.homemotion.devices.spi.impl.ips.IPSDeviceLocator;
import org.homemotion.devices.spi.impl.ips.IPSFileEventListener;
import org.homemotion.devices.spi.impl.ips.IPSServer;

import com.google.inject.AbstractModule;

public final class IPSModule extends AbstractModule {

	
	@Override
	protected void configure() {
		bind(IPSFileEventListener.class);
		bind(IPSServer.class).to(IPSServerImpl.class);
		bind(IPSDeviceLocator.class).to(IPSDeviceLocator.class);
		bind(IPSFileEventListener.class).asEagerSingleton();
	}

}
