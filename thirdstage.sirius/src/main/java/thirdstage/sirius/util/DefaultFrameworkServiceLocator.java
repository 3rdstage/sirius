package thirdstage.sirius.util;

import org.springframework.beans.factory.annotation.Required;

import thirdstage.sirius.logging.ApplicationLogger;

public class DefaultFrameworkServiceLocator implements FrameworkServiceLocator{
	 
	private ApplicationLogger applicationLogger;
	
	@Required
	public void setApplicationLogger(ApplicationLogger logger){
		this.applicationLogger = logger;
	}
	

	public ApplicationLogger getApplicationLogger() {
		return this.applicationLogger;
	}

}
