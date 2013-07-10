package org.raxa.module.agi;

import java.sql.Timestamp;
import java.util.Date;
import org.raxa.module.variables.VariableSetter;
import org.hibernate.Query;
import org.hibernate.Session;
import org.raxa.module.database.*;

public class CallSuccess implements VariableSetter {

	private int aid;
	private int msgId;
	
	public CallSuccess(int msgId,int aid){
		this.msgId=msgId;
		this.aid=aid;
	}
	
	public void updateAlert(){
		try{
		  Session session = HibernateUtil.getSessionFactory().openSession();
		  session.beginTransaction();
		  String queryString = "update Alert a set a.isExecuted=:isExecuted,a.lastTry=:time where aid=:aid";
		  Query query = session.createQuery(queryString);
		  query.setBoolean("isExecuted", true);
		  query.setTimestamp("time", new Date());
		  query.setInteger("aid",aid);
		  query.executeUpdate();
		  System.out.println("Updateing Alert");
		  session.getTransaction().commit();
		  session.close();
		}
		catch(Exception ex){
			System.out.println("Some Error Occured");
		}
	}
	
	public void updateRecord(){
		Timestamp time=new Timestamp(new Date().getTime());
		Session session = HibernateUtil.getSessionFactory().openSession();
	  	session.beginTransaction();
		Alert alert = (Alert) session.get(Alert.class,aid);
		Record record=new Record(alert.getPatientId(),IVR_TYPE,msgId,time,true,"UnKnown");
		int id = (Integer) session.save(record);    
	    record.setRecordId(id);
	    session.getTransaction().commit();
		session.close();
	}

}
