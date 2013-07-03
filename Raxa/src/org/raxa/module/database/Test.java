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
		 
		   session.beginTransaction();
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
			 String pid="123";
			 
			 java.sql.Timestamp a=new java.sql.Timestamp(date.getTime());
			 
			
			 
			/* 
			// System.out.println("select p1.pnumber,i1.content,a1.scheduleTime from Patient p1,Alert a1,IvrMsg i1 where p1.pid=a1.pid and a1.alertType=1 and a1.scheduleTime>="+lowertime+"and a1.scheduleTime <="+uppertime+"and a1.msgId=i1.ivrIdz");
			 String hql="select p1.pnumber,a1.scheduleTime,i1.content,i1.ivrId,a1.isExecuted from Patient p1,Alert a1,IvrMsg i1 where p1.pid=:pid and p1.pid=a1.pid and a1.alertType=1 and a1.msgId=i1.ivrId order by i1.id,i1.itemNumber";
			 Query query=session.createQuery(hql);
			
			  query.setString("pid",pid);
			try{
			    Iterator results=query.list().iterator();
			    List<String> content=new ArrayList<String>();
			    Object[] row = (Object[]) results.next();
			   	String pnumber = (String) row[0];
			   	Time time=(Time) row[2];
			   	String temp=(String) row[1];
			   	content.add(temp);
			   	System.out.println(temp);
			   	while ( results.hasNext() ) {
			   		row = (Object[]) results.next();
			   		content.add((String)row[1]);
			   		System.out.println((boolean)row[4]);
			   	    
			   	}
				System.out.println(pnumber+" "+time);int count=0;
			   	while(count<content.size()){
			   
			   		System.out.println(content.get(count++));
			   	}
			}
			catch(Exception ex){
				System.out.println("Some Error Occured");
			}
			*/
			   	
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