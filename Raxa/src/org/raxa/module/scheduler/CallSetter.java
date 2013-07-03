/*
 * Its run method set all threads to run Caller object and provide patient information to the caller object
 * 
 * It will take into account both alertTypes i.e IVR AND SMS
 * 
 */

package org.raxa.module.scheduler;
import org.raxa.module.variables.VariableSetter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.List;
import java.sql.Time;
import java.util.Date;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import org.raxa.module.MedicalInformation.MedicineInformer;

public class CallSetter implements Runnable,VariableSetter{
	static Logger logger = Logger.getLogger(CallSetter.class);
	
	public void run(){
		Time lowertime=new Time((new Date()).getTime());
		
		List<MedicineInformer> listOfIVRCaller=(new MedicineInformer()).getPatientInfoOnTime(lowertime,IVR_TYPE);
		List<MedicineInformer> listOfSMSCaller=(new MedicineInformer()).getPatientInfoOnTime(lowertime,SMS_TYPE);
		
		if(listOfIVRCaller!=null)
			setIVRThread(listOfIVRCaller);
		else logger.info("In CallSetter:run-No IVRTuple found for the next hour");
		
		if(listOfSMSCaller!=null)
			setSMSThread(listOfSMSCaller);
		else logger.info("In CallSetter:run-No SMSTuple found for the next interval");
		
		
			
	}
	
	public void setIVRThread(List<MedicineInformer> list){
		PropertyConfigurator.configure("log4j.properties");
		ScheduledExecutorService executor = Executors.newScheduledThreadPool(THREAD_POOL_CALL_SETTER);
		int count=0;
		DateFormat df = new SimpleDateFormat("hh:mm:ss");
	    while(count<list.size()){
			MedicineInformer a;
			a=list.get(count);
			Caller caller=new Caller(a);
			try{
				Date preTime=df.parse(df.format(a.getTime()));
				Date sysTime = df.parse(df.format(new Date()));
			    long diff=preTime.getTime()-sysTime.getTime();
				executor.schedule(caller,diff,TimeUnit.MILLISECONDS);
			}
			catch(Exception ex){
				logger.error("In function setIVRThread:Error Occured");
			}
			finally{
				count++;
			}
		}
	}
		
	public void setSMSThread(List<MedicineInformer> list){
		ScheduledExecutorService executor = Executors.newScheduledThreadPool(THREAD_POOL_CALL_SETTER);
		int count=0;
		DateFormat df = new SimpleDateFormat("hh:mm:ss");
		while(count<list.size()){
			MedicineInformer a;
			a=list.get(count);
			Messager messager=new Messager(a);
			try{
				Date preTime=df.parse(df.format(a.getTime()));
				Date sysTime = df.parse(df.format(new Date()));
			    long diff=preTime.getTime()-sysTime.getTime();
				executor.schedule(messager,diff,TimeUnit.MILLISECONDS);
			}
			catch(Exception ex){
				logger.error("In function setSMSThread:Error Occured");
			}
		}
	}

}
		
		
		
		
		
		
		
		
		
	
