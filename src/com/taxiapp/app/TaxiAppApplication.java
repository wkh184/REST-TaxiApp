package com.taxiapp.app;
import org.glassfish.jersey.server.ResourceConfig;

public class TaxiAppApplication extends ResourceConfig {
	public TaxiAppApplication() {
		packages("com.taxiapp.resources");
		register(JacksonFeature.class);
		register(BasicAuthenticationFilter.class);
	}
}
