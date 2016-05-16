package org.opentelecoms.camelsiptest;

import org.apache.camel.Predicate;
import org.apache.camel.PropertyInject;
import org.apache.camel.spring.SpringRouteBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RouteBuilder extends SpringRouteBuilder {
	
	private Logger logger = LoggerFactory.getLogger(RouteBuilder.class);
	
	// FIXME - bug CAMEL-8125
	//@PropertyInject("local.country")
	private String localCountry = "CH";
	
	//@PropertyInject("smsc.country")
	private String smscCountry = "UK";
	
	//@PropertyInject("throttle.timePeriodMillis")
	long throttleTimePeriodMillis = 1000;
	
	//@PropertyInject("throttle.maximumRequestsPerPeriod")
	int throttleRequestsPerPeriod = 1;
		
	@Override
	public void configure() throws Exception {
		
		/**
		 * Log some information about the configuration.
		 */
		logger.info("Parsing locally supplied numbers "
			+ "using context country: {}", localCountry);
		logger.info("Parsing SMSC supplied numbers using "
			+ "context country: {}", smscCountry);
		logger.info("Throttling allows {} request(s) per {}ms",
			throttleRequestsPerPeriod, throttleTimePeriodMillis);
		
		/**
		 * Create some strings that will be used in the Camel routes
		 */
		String log = "log:org.opentelecoms.camelsiptest?level=INFO";
		String logWarn = "log:org.opentelecoms.camelsiptest?level=WARN";
		
		/**
		 * This Camel routes handles messages from JMS going out to the SIP world
		 */
		from("activemq:sip-message.outbox")
			.to("sip: FIXME");
		
		/**
		 * This Camel route handles messages coming to us from the SIP world
		 */
		from("sip: FIXME")
			.to(log)   // Log a copy of the message
			.to("activemq:sip-message.inbox");  // put it in a JMS queue
	}

}
