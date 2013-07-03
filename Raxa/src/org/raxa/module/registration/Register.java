package org.raxa.module.registration;
import org.raxa.module.database.*;
import java.util.HashMap;
import java.util.Map;


public class Register {
	
	private Map<String,Integer> allowedAlert;
	private Patient patient;;
	private Alert alert;
	private IvrMsg ivrMsg;
	private SmsMsg smsMsg;
	private TableStatus tableStatus;
	private int status=0;
	private String username="raxa";
	private String password="raxa";
	
	
	public Register(){
		
		allowedAlert=new HashMap<String,Integer>();
		setAllowedAlert("ivr",1);
		setAllowedAlert("sms",2);
		
	}
	
	public void setAllowedAlert(String alertType,int alertIdentity){
		allowedAlert.put(alertType,alertIdentity);
	
	}
	
	
	public int getAllowedAlert(String alertType){
		return allowedAlert.get(alertType);
	}
	
	
	public boolean checkAlert(String alertType){
		if(!(allowedAlert.containsKey(alertType.toLowerCase()))){
			System.out.println("ERROR:This type of alert not allowed");
			return true;
		}
		return false;
	}

	
	public int setDatabase(String pid,String pnumber,String alertType){
		if(checkAlert(alertType)){
			status=1;
			return status;
		}
		
		if(pid.equals(null))
			pid=getPid(pnumber);
		if(pid.equals(null))
			return status=2;
		
		status=setPatient(pid);
		
		if(status==0)
		status=checkIfAlreadyregistered(pid,getAllowedAlert(alertType));
		
		if(status==0){
			if(1==allowedAlert.get(alertType))
				status=setIvrMsg(pid);
		
			if(2==allowedAlert.get(alertType))
				status=setSmsMsg(pid);
		}
		
		return status;
		
	}
	
	public String getPid(String pnumber){
		//call to get pid(if not found returns null)
		return "";
	}
	
	public int setPatient(String pid){
		return 0;
	}
	
	
	public int checkIfAlreadyregistered(String pid,int alertType){
			
		return 0 ;
	}
	
	public int setIvrMsg(String pid){
		return 0;
	}
	
	public int setSmsMsg(String pid){
		return 0;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/*  1- alertType not defined
	 *  2- patient not in database
	 * 
	 */
	
	
	/*
	 * public void setParameters(String pid,String pname,String pnumber,String snumber,String alertType){
		if(checkAlert(alertType)){
			System.exit(0);
		}
		patient=new Patient(pid,pname,pnumber,snumber);
		
	}
	 */
	
	
}
