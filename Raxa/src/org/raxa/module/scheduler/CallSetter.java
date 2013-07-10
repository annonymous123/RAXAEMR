/*
 * Its run method set all threads to run Caller object and provide patient information to the caller object
 * 
 * It will take into account both alertTypes i.e IVR AND SMS
 * 
 */

package org.raxa.module.scheduler;
import org.raxa.module.variables.VariableSetter;
import java.sql.Time;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.Calendar;
import java.util.List;
import org.raxa.module.database.*;
import java.util.Date;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.raxa.module.MedicalInformation.MedicineInformer;
import java.io.IOException;
import java.util.Properties;

public class CallSetter implements Runnable,VariableSetter{
	static Logger logger = Logger.getLogger(CallSetter.class);
	private Date today;
	
	public CallSetter(Date today){
		this.today=today;
	}
	
	public void run(){
		if(!isSameDay()){
			resetDatabase();
			today=new Date();
			System.out.println(today);
		}
		Time currtime=new Time((new Date()).getTime());
		
		List<MedicineInformer> listOfIVRCaller=(new MedicineInformer()).getPatientInfoOnTime(currtime,IVR_TYPE);
		List<MedicineInformer> listOfSMSCaller=(new MedicineInformer()).getPatientInfoOnTime(currtime,SMS_TYPE);
		
		if(listOfIVRCaller!=null)
			setIVRThread(listOfIVRCaller);
		else logger.info("In CallSetter:run-No IVRTuple found for the next interval");
		
		if(listOfSMSCaller!=null)
			setSMSThread(listOfSMSCaller);
		else logger.info("In CallSetter:run-No SMSTuple found for the next interval");
		
	}
	
	public boolean isSameDay(){
		Calendar cal1 = Calendar.getInstance();
		Calendar cal2 = Calendar.getInstance();
		cal1.setTime(today);
		cal2.setTime(new Date());
		boolean sameDay = cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
		                  cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH)&&
		                  cal1.get(Calendar.DAY_OF_MONTH)==cal2.get(Calendar.DAY_OF_MONTH);
		return sameDay;
	}
	
	/*
	 * Update the database and set isExecuted to no and retry_count to false each day(at midnight).
	 */
	public void resetDatabase(){
		Session session = HibernateUtil.getSessionFactory().openSession();
		  session.beginTransaction();
		  String queryString = "update Alert a set a.retryCount=3,a.isExecuted='n'";
		  Query query = session.createQuery(queryString);
		  query.executeUpdate();
		  session.getTransaction().commit();
		  session.close();
	}
	
	public void setIVRThread(List<MedicineInformer> list){
		Properties prop = new Properties();
		int THREAD_POOL_CALLER=50;
		try{
		prop.load(CallSetter.class.getClassLoader().getResourceAsStream("config.properties"));
		THREAD_POOL_CALLER=Integer.parseInt(prop.getProperty("Thread_Pool_Caller"));
		}
		catch (IOException ex) {
    		ex.printStackTrace();
        }
		
		ScheduledExecutorService executor = Executors.newScheduledThreadPool(THREAD_POOL_CALLER);
		int count=0;
	    while(count<list.size()){
			MedicineInformer a;
			a=list.get(count);
			Caller caller=new Caller(a);
			try{
				executor.schedule(caller,1,TimeUnit.SECONDS);
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
		Properties prop = new Properties();
		int THREAD_POOL_MESSAGER=50;
		try{
		prop.load(CallSetter.class.getClassLoader().getResourceAsStream("config.properties"));
		THREAD_POOL_MESSAGER=Integer.parseInt(prop.getProperty("Thread_Pool_Messager"));
		}
		catch (IOException ex) {
    		ex.printStackTrace();
        }
		
		ScheduledExecutorService executor = Executors.newScheduledThreadPool(THREAD_POOL_MESSAGER);
		int count=0;
	    while(count<list.size()){
			MedicineInformer a;
			a=list.get(count);
			Messager messager=new Messager(a);
			try{
				executor.schedule(messager,1,TimeUnit.SECONDS);
			}
			catch(Exception ex){
				logger.error("In function setSMSThread:Error Occured");
			}
			finally{
				count++;
			}
		}
	}

}
		
		
		
		
		
		
		
		
		
	
