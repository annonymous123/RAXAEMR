/*
 * It will provide patient content of Message that will be played or texted to the patient.
 * 
 * provide information given one of the following info
 * 1.patientId,alertType
 * 2.patientnumber,alertType
 * 3.scheduleTime,AlertType
 */

package org.raxa.module.MedicalInformation;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.hibernate.Query;
import org.hibernate.Session;
import org.raxa.module.database.HibernateUtil;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Iterator;
import java.sql.Time;
import java.util.List;
import java.util.ArrayList;
import org.raxa.module.variables.*;


public class MedicineInformer implements VariableSetter,MedicineInformerConstant {
	static Logger logger = Logger.getLogger(MedicineInformer.class);
	private String pnumber;
	private Time time;
	private List<String> medicineInfo;
	private String pid;
	private int msgId;
		
	public MedicineInformer(){
		pnumber=null;
		time=null;
		medicineInfo=null;
	//	PropertyConfigurator.configure("log4j.properties");
	}
	
	public MedicineInformer(String pnumber,Time time,List<String> medicineInfo,String pid,int msgId){
		this.pnumber=pnumber;
		this.time=time;
		this.medicineInfo=medicineInfo;
		this.pid=pid;
		this.msgId=msgId;
	}
	
	public String getPhoneNumber(){
		
		return pnumber;
	}
	
	public Time getTime(){
		
		return time;
	}
	
	public List<String> getMedicineInformation(){
		
		return medicineInfo;
	}
	
	public String getPatientId(){
		return pid;
	}
	
	public int getMsgId(){
		return msgId;
	}
	/*
	 * This will return MedicineInformer object once user pass uuid and alertType.This will be used to call
	 * via any software which has access to patient id;
	 */
	
	public List<MedicineInformer> getMedicineInfoOnId(String uuid,int alertType){
		
		String hql=null;Session session = HibernateUtil.getSessionFactory().openSession();
		List<MedicineInformer> a=null;
		if(alertType==IVR_TYPE)
			  hql=IVR_MEDICINE_QUERY_UUID;
		if(alertType==SMS_TYPE)
			  hql=SMS_MEDICINE_QUERY_UUID;
		try{
			 session.beginTransaction();
			 Query query=session.createQuery(hql);
			 query.setString("pid",uuid);
			 Iterator results=query.list().iterator();
			 a=getPatientList(results);
			 }
			 catch(Exception ex){
				 logger.error("Error in getPatientListOnTime");
				 return null;
			 }
			 session.close();
			
			 return a;
	}
	
	/*
	 * This will return MedicineInformer object once user pass phone numebr and alertType.This will be used when
	 * any incoming call,SMS is received; 
	 */
	
	public List<MedicineInformer> getMedicineInfoOnNumebr(String pnumber,int alertType){
		String hql=null;Session session = HibernateUtil.getSessionFactory().openSession();
		List<MedicineInformer> a=null;
		if(alertType==IVR_TYPE)
			  hql=IVR_MEDICINE_QUERY_NUMBER;
		if(alertType==SMS_TYPE)
			  hql=SMS_MEDICINE_QUERY_NUMBER;
		try{
			 session.beginTransaction();
			 Query query=session.createQuery(hql);
			 query.setString("pnumber",pnumber);
			 Iterator results=query.list().iterator();
			 a=getPatientList(results);
			 }
			 catch(Exception ex){
				 logger.error("Error in getPatientListOnTime");
				 return null;
			 }
			 session.close();
			
			 return a;
		
	}
	
	/*
	 * 
	 */
	public List<MedicineInformer> getPatientInfoOnTime(Time lowertime,int alertType){
		String hql=null;Session session = HibernateUtil.getSessionFactory().openSession();
		List<MedicineInformer> a=null;
		if(alertType==IVR_TYPE)
			  hql=IVR_MEDICINE_QUERY_DATE;
		if(alertType==SMS_TYPE)
			  hql=SMS_MEDICINE_QUERY_DATE;
		
		Time uppertime=new Time(lowertime.getTime());
		
		uppertime.setMinutes(uppertime.getMinutes()+DATABASE_PINGING_INTERVAL+ACOUNT_DELAY); 
		 try{
		 session.beginTransaction();
		 Query query=session.createQuery(hql);
		 query.setTime("lowerTime",lowertime);
		 query.setTime("upperTime", uppertime);
		 Iterator results=query.list().iterator();
		 a=getPatientList(results);
		 }
		 catch(Exception ex){
			 logger.error("Error in getPatientListOnTime");
			 return null;
		 }
		 session.close();
		
		 return a;
	}
	
	/*
	 * This will return all patient who need to be called given a time and alertType
	 * return null if no tuple found or any error occured
	 */
	
	
	public List<MedicineInformer> getPatientList(Iterator results){
		
		int flag=0;Date date=new Date();int today=date.getDay();
		
		List<MedicineInformer> listOfPatients=new ArrayList<MedicineInformer>();
		if(!(results.hasNext()))
			return null;
		try{
	    	
		  Object[] row=(Object[]) results.next();
		  while(true){
			  
			  while(today==((Timestamp)row[5]).getDate() && (boolean)row[6]){
				  if(results.hasNext())
					  row=(Object[]) results.next();
				  else
					  return listOfPatients;
				  
			  }
			  String pnumber=(String) row[0];
			  Time scheduleTime=(Time) row[1];
			  String pid=(String) row[4];
			  int id=(Integer) row[3];
			  List<String> content=new ArrayList<String>();
			  while(id==(Integer)row[3]){
				  String temp=(String) row[2];
			 	  content.add(temp);
				  if(results.hasNext())
				  row=(Object[]) results.next();
				  else{ flag=1;	break;}						
			  }
			//Creating Object
			 
			 listOfPatients.add(new MedicineInformer(pnumber,scheduleTime,content,pid,id));
			 
			
     		 if(flag==1) break;
		  }
	    }
		
	    catch(Exception ex){
			 logger.error("IN MedicineInformer:getPatientListOnTime:Error Occured");
			 return null;
		}
//		session.close();
		return listOfPatients;
	}
	
}
