package org.raxa.module.scheduler;

public interface schedulerInterface {
	
	public static final String ALERT_UPDATE="Update Alert a1 SET isExecuted=:isExecuted,lastTry=:time where msgId=:msgId and alertType=:alertType";

}
