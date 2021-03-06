package org.raxa.module.database;

public class Patient {
      
	private String pid;
	private String pname;
	private String pnumber;
	private String snumber;
	private String preferLanguage;
	
	
	public Patient(String pid,String pname,String pnumber,String snumber,String preferLanguage){
		this.pid=pid;
		this.pname=pname;
		this.pnumber=pnumber;
		this.snumber=snumber;
		this.preferLanguage=preferLanguage;
	}
	
	public Patient(){}
	
	public Patient(String pid,String pname,String pnumber,String preferLanguage){
		this(pid,pname,pnumber,null,preferLanguage);
	}
	
	public Patient(String pid,String pnumber){
		this(pid,null,pnumber,null);
	}
	
	public String getPatientId(){
		return pid;
	}
	
	public String getPatientName(){
		return pname;
	}
	
	public String getPatientNumber(){
		return pnumber;
	}
	
	public String getPatientSecondaryNumber(){
		return snumber;
	}
	
	public String getPatientPreferredLanguage(){
		return preferLanguage;
	}
	
	public void setPatientPreferredLanguage(String option){
		preferLanguage=option;
	}
	
	public void setPatientId(String id){
		pid=id;
	}
	
	public void setPatientName(String name){
		pname=name;
	}
	
	public void setPatientNumber(String number){
		pnumber=number;
	}
	
	public void setPatientSecondaryNumber(String number){
		snumber=number;
	}
	
}
