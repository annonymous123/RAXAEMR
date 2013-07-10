package org.raxa.module.database;

import java.sql.Time;
import java.sql.Timestamp;

public class Alert {
     private int aid;
     
     private String pid;
     
     private int  alertType;
     
     private int msgId;
     
     private Time scheduleTime;   
     
     private Timestamp lastTry;
     
     private boolean isExecuted;
     
     private int retryCount;
     
     public Alert(String pid,int alertType,int msgId,Time scheduleTime,Timestamp lastTry){
    	 this.pid=pid;
    	 this.msgId=msgId;
    	 this.scheduleTime=scheduleTime;
    	 this.lastTry=lastTry;
    }
     
     public Alert(){}
     
     public int getAlertId(){
    	 return aid;
     }
     
     public void setAlertId(int id){
    	 aid=id;
     }
     
     public String getPatientId(){
 		return pid;
 	 }
 	
     public void setPatientId(String id){
 		pid=id;
 	 }
     
     public void setAlertType(int type){
    	 alertType=type;
     }
     
     public int getAlertType(){
    	 return alertType;
     }
     
     public void setMessageId(int id){
    	 msgId=id;
     }
     
     public int getMessageId(){
    	 return msgId;
     }
     
    
     public void setScheduleTime(Time time){
    	 scheduleTime=time;
     }
     
     public Time getScheduleTime(){
    	 return scheduleTime;
     }
     
     public Timestamp getLastTried(){
    	 return lastTry;
     }
     
     public void setLastTried(Timestamp datetime){
    	 lastTry=datetime;
     }
     
     public boolean  getIsExecuted(){
    	 return isExecuted;
     }
     
     public void setIsExecuted(boolean  status){
    	 isExecuted=status;
     }
     
     public void setretryCount(int count){
    	 retryCount=count;
     }
     
     public int getretryCount(){
    	 return retryCount;
     }
     
     
}
