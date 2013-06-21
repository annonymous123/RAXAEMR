package org.raxa.module.registration;
import java.sql.Timestamp;

public class Record {
	
	private int rid;
	
	private String pid;
	
	private int alertType;
	
	private int msgId;
	
	private Timestamp serviceTime;
	
	private boolean isExecuted;
	
	private String serviceInfo;
	
	
	public Record(int rid,String pid,int alertType,int msgId,Timestamp serviceTime,boolean isExecuted,String serviceUsed){
    	 this.rid=rid;
    	 this.pid=pid;
    	 this.alertType=alertType;
    	 this.msgId=msgId;
    	 this.serviceTime=serviceTime;
    	 this.isExecuted=isExecuted;
    	 this.serviceInfo=serviceUsed;
     }
	
	public Record(){}
	
	//more constructors
	
	 public int getRecordId(){
    	 return rid;
     }
     
     public void setRecordId(int id){
    	 rid=id;
     }
     
     public String getPatientId(){
 		return pid;
 	 }
 	
     public void setPatientId(String id){
 		pid=id;
 	 }
     
     public void setMessageId(int id){
    	 msgId=id;
     }
     
     public int getMessageId(int id){
    	 return msgId;
     }
     
     public void setAlertType(int type){
    	 alertType=type;
     }
     
     public int getAlertType(){
    	 return alertType;
     }
     
     public Timestamp getServiceTime(){
    	 return serviceTime;
     }
     
     public void setScheduleTime(Timestamp time){
    	 serviceTime=time;
     }
     
     public boolean getIsExecuted(){
    	 return isExecuted;
     }
     
     public String getServiceInfo(){
    	 return serviceInfo;
     }
     
     public void setScheduleInfo(String info){
    	 serviceInfo=info;
     }
     
}
