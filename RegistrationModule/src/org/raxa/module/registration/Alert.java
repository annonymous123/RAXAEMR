package org.raxa.module.registration;

import java.sql.Time;
import java.sql.Timestamp;


public class Alert {
     private int aid;
     
     private String pid;
     
     private int  alertType;
     
     private int msgId;
     
     private Time scheduleTime;   //Doubt
     
     private Timestamp lastTry;         //Doubt
     
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
     
     
     
     
}
