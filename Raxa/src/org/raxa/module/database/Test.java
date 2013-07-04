package org.raxa.module.database;
import org.hibernate.Query;
import org.hibernate.Session;
import java.util.Date;
import java.util.Iterator;
import java.sql.Time;
import java.util.List;
import java.util.ArrayList;


public class Test {
	public static void main(String[] args) {
		   
		  System.out.println("I am in");
		  
		  Session session = HibernateUtil.getSessionFactory().openSession();
		 
/*
		   	String hql="from Patient,";
		   	Query query = session.createQuery(hql);
		   	List<Patient> results=query.list();
		   
		   	System.out.println((results.get(0)).getPatientName());
	*/
		/*
		   int count=0;
		   String hql="select a1.pid from Patient as p1,Alert as a1 where p1.pid=a1.pid";
		   	Query query = session.createQuery(hql);
		   	List<String> results=query.list();
		  
		   	System.out.println(results.get(2));
		  */
		   
		   /*
		   String p="126";
		   String hql="select p1.pnumber,i1.content,a1.scheduleTime from Patient p1,Alert a1,IvrMsg i1 where p1.pid=:id and p1.pid=a1.pid and a1.alertType=1 and a1.msgId=i1.ivrId";
		   	Query query = session.createQuery(hql);
		   	query.setString("id", p);
		   	Iterator results=query.list().iterator();
		   	
		   	List<String> content=new ArrayList<String>();
		   	Object[] row = (Object[]) results.next();
		   	String pnumber = (String) row[0];
		   	Time time=(Time) row[2];
		   	String temp=(String) row[1];
		   	content.add(temp);
		   	while ( results.hasNext() ) {
		   		row = (Object[]) results.next();
		   		content.add((String)row[1]);
		   	    
		   	}
		   	
		   	System.out.println(pnumber+" "+time);int count=0;
		   	while(count<content.size()){
		   
		   		System.out.println(content.get(count++));
		   	}

		    */
		   
		   java.util.Date date = new Date();
			Time lowertime=new Time(date.getTime());
			lowertime.setHours(lowertime.getHours()-16);
			 System.out.println(lowertime);
			 Time uppertime=new Time(lowertime.getTime());
			 uppertime.setMinutes(uppertime.getMinutes()+58);
			 System.out.println(uppertime);
			 System.out.println(lowertime);
			 String pid="125";
			 boolean isExecuted=true;
			 java.sql.Timestamp a=new java.sql.Timestamp(date.getTime());
			 int msgId=6;
			 int alertType=1;
			 Record record=new Record(pid,alertType,msgId,a,isExecuted,"UnKnown");
			 String hqlAlert="from Alert where msgId=:msgId and alertType=:alertType";
			 session.beginTransaction();
				 
				Query query=session.createQuery(hqlAlert);
				query.setInteger("msgId", msgId);
				query.setInteger("alertType", alertType);
				
				
				 Alert alert = (Alert)query.list().get(0);
				 
				 alert.setIsExecuted(isExecuted);
				 alert.setLastTried(a);
				 session.update(alert);
				 
			       int id = (Integer) session.save(record);
			       record.setRecordId(id);
			     
			    session.getTransaction().commit();
			    session.close();
			 
			
}
	}



/*
 * 
 *public List<MedicineInformer> getPatientList(Time lowertime,int alertType){
Session session = HibernateUtil.getSessionFactory().openSession();

//	Time uppertime=new Time(lowertime.getTime());
	
	//uppertime.setMinutes(uppertime.getMinutes()+IVR_PINGING_INTERVAL+ACOOUNT_DELAY);   //3 minutes more to keep account of processing
	
	
	//session.beginTransaction();
	
//	if(alertType==IVR_TYPE)
//		  hql=IVR_MEDICINE_QUERY_DATE;
//	if(alertType==SMS_TYPE)
	//	  hql=SMS_MEDICINE_QUERY_DATE;
		  
   // Query query=session.createQuery(hql);
    //query.setTime("lowerTime",lowertime);
    //query.setTime("upperTime", uppertime);

*/