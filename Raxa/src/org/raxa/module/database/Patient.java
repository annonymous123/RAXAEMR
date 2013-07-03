package org.raxa.module.database;

public class Patient {
      
	private String pid;
	private String pname;
	private String pnumber;
	private String snumber;
	
	public Patient(String pid,String pname,String pnumber,String snumber){
		this.pid=pid;
		this.pname=pname;
		this.pnumber=pnumber;
		this.snumber=snumber;
	}
	
	public Patient(){}
	
	public Patient(String pid,String pname,String pnumber){
		this(pid,pname,pnumber,null);
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
		
	}
}
