package org.homemotion.test;

import org.jboss.weld.environment.se.Weld;
import org.jboss.weld.environment.se.WeldContainer;
import org.junit.BeforeClass;

public abstract class AbstractHMTest {

	private static Weld weld = new Weld();
	private static WeldContainer cdiContainer;
	
	@BeforeClass
	public static void startContainer() throws Exception {
		// start CDI
		synchronized(weld){
			if(cdiContainer==null){
				cdiContainer = weld.initialize();
			}
		}
	}

	public static void stopContainer() throws Exception {
		// stop CDI
		synchronized(weld){
			if(cdiContainer!=null){
				weld.shutdown();
				cdiContainer = null;
			}
		}
	}

	protected <T> T getInstance(Class<T> type) {
		try {
			return cdiContainer.instance().select(type).get();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
