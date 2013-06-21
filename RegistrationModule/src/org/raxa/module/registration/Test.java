package org.raxa.module.registration;
import org.hibernate.Session;
import java.util.Date;
import org.hibernate.Transaction;

public class Test {
	public static void main(String[] args) {
		   
		  System.out.println("I am in");
		  
		  Session session = HibernateUtil.getSessionFactory().openSession();
		 
		   session.beginTransaction();
		   
		   Patient p=new Patient("300cv1","81232d3203");
		   java.util.Date date = new Date();
		   java.sql.Time time=new java.sql.Time(date.getTime());
		   java.sql.Timestamp param = new java.sql.Timestamp(date.getTime());
		   
		   Alert a=new Alert("300cv1",5,6,null,null);
		   a.setLastTried(param);
		   a.setScheduleTime(time);
		   session.save(p);
		   session.save(a);
		   session.persist(a);
		  
		   IvrMsg b=new IvrMsg(1,1,"Hurrah");
		   SmsMsg c=new SmsMsg(1,1,"Hurrah");
		  IvrMsg d=new IvrMsg(b.getId(),b.getItemNumber()+1,b.getContent()+"sadada");
		   	TableStatus e=new TableStatus("ivr",1);
		   	TableStatus f=new TableStatus("sms",1);
		  
		   session.persist(p);
		   session.persist(b);
		   session.persist(c);
		   session.persist(d);
		   session.persist(e);
		   session.persist(f);
		   
		    
		   session.getTransaction().commit();
		  
		   session.close();
}
	}
