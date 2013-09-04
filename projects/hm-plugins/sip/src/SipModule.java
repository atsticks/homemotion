

import org.homemotion.comm.SIPService;

import com.google.inject.AbstractModule;

public final class SipModule extends AbstractModule {
	
	@Override
	protected void configure() {
		bind(SIPService.class).to(SIPServiceImpl.class);
	}

}
