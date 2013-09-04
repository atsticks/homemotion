package org.homemotion.common.system;

import javax.enterprise.event.Observes;
import javax.inject.Singleton;

import org.jboss.weld.environment.se.events.ContainerInitialized;

@Singleton
public class WeldContainer{

//	@ExtensionManaged
//	@ConversationScoped
//	@Produces
//	@PersistenceUnit(unitName="myPu")
//	EntityManagerFactory emf;

	public void startup(@Observes ContainerInitialized evt){
		System.err.println("*** Container started. ***");
	}

   
   public static void main(String[] args) {
	   org.jboss.weld.environment.se.StartMain.main(args);
	}

}