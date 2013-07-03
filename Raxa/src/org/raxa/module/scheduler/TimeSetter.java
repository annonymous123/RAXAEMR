/*
 * This Class will ping database after every two hours(DATABASE_PINGING_INTERVAL) and schedule all thread to call 
 * patient where patient scheduleTime falls between that time Zone.Pinging time can be changed easily 
 * by changing DATABASE_PINGING_INTERVAL 
 * 
 */

package org.raxa.module.scheduler;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import org.raxa.module.variables.VariableSetter;


public class TimeSetter implements VariableSetter{

	private static ScheduledExecutorService executor = Executors.newScheduledThreadPool(THREAD_POOL_TIME_SETTER);
	public static void main(String args[]){
		  Runnable callSetter = new CallSetter();
		  try{
		   executor.scheduleWithFixedDelay(callSetter,1,DATABASE_PINGING_INTERVAL*60,TimeUnit.SECONDS);
		   }catch(Exception e){
		   e.printStackTrace();
		  }
	} 
}
