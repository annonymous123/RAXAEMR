package org.raxa.module.agi;

import java.sql.Timestamp;
import java.util.Date;
import org.raxa.module.variables.VariableSetter;
import org.hibernate.Query;
import org.hibernate.Session;
import org.raxa.module.database.*;
import org.apache.log4j.Logger;

public class CallSuccess implements VariableSetter {

	private int aid;
	private int msgId;
	private boolean isExecuted;
	private String serviceInfo;
	
	public CallSuccess(int msgId,int aid,boolean isExecuted,String serviceInfo){
		this.msgId=msgId;
		this.aid=aid;
		this.isExecuted=isExecuted;
		this.serviceInfo=serviceInfo;
	}
	
	public void updateAlert(){
		 Logger logger = Logger.getLogger(CallSuccess.class);
		try{
		  logger.info("Updating Alert");
		  Session session = HibernateUtil.getSessionFactory().openSession();
		  session.beginTransaction();
		  String queryString = "update Alert a set a.isExecuted=:isExecuted,a.lastTry=:time where aid=:aid";
		  Query query = session.createQuery(queryString);
		  query.setBoolean("isExecuted", isExecuted);
		  query.setTimestamp("time", new Date());
		  query.setInteger("aid",aid);
		  query.executeUpdate();
		  session.getTransaction().commit();
		  session.close();
		}
		catch(Exception ex){
			logger.error("Couldnot update Alert");
		}
	}
	
	public void updateRecord(){
		Logger logger = Logger.getLogger(CallSuccess.class);
		try{
		Timestamp time=new Timestamp(new Date().getTime());
		Session session = HibernateUtil.getSessionFactory().openSession();
	  	session.beginTransaction();
		Alert alert = (Alert) session.get(Alert.class,aid);
		Record record=new Record(alert.getPatientId(),IVR_TYPE,msgId,time,isExecuted,serviceInfo);
		int id = (Integer) session.save(record);    
	    record.setRecordId(id);
	    session.getTransaction().commit();
		session.close();
		}
		catch(Exception ex){
			logger.error("Couldnot update Record");
		}
	}

}
