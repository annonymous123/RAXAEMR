/*
 * Call Manager AMI and place a cal
 */

package org.raxa.module.scheduler;
import org.raxa.module.MedicalInformation.MedicineInformer;
import org.raxa.module.soundConverter.EnglishTTS;
import java.util.List;
import java.io.File;
import org.apache.log4j.Logger;
import org.raxa.module.ami.Outgoing;
import org.raxa.module.database.Alert;
import org.raxa.module.database.HibernateUtil;
import org.apache.log4j.PropertyConfigurator;
import org.raxa.module.variables.VariableSetter;
import org.hibernate.Query;
import org.hibernate.Session;
import org.raxa.module.database.*;
import java.sql.Timestamp;
import java.util.Date;

public class Caller implements Runnable,VariableSetter,schedulerInterface {

	private MedicineInformer patient;
	private String patientDirectory;
	private int msgId;
	
	public Caller(MedicineInformer patient){
		this.patient=patient;
		patientDirectory=MEDICINE_VOICE_FOLDER_LOCATION;
		msgId=patient.getMsgId();
	}
	
	public void run(){
		Logger logger = Logger.getLogger(Caller.class);
		PropertyConfigurator.configure("log4j.properties");
		String downloadLocation=patientDirectory+"/"+String.valueOf(msgId);
		if(checkIfTheVoiceFolderExist(downloadLocation) || downloadVoice(downloadLocation)){
			if(callManager(String.valueOf(msgId)))
				updateRecordAndAlert(patient,true);
			else updateRecordAndAlert(patient,false);
		}
		else logger.error("In org.raxa.module.scheduler.Caller.java:Unable To download voice for Patient with msg ID"+String.valueOf(patient.getMsgId()));
	}
	/*
	 * This will check if the sound folder for that patient exist.
	 * For many Languages it will check database.
	 */
	public boolean checkIfTheVoiceFolderExist(String downloadLocation){
		File f=new File(downloadLocation);
		if(f.exists() && f.isDirectory())
			return true;
		else return false;
	}
	/*
	 * This will download English voice for the medicine and store it in downloadLocation.
	 */
	public boolean downloadVoice(String downloadLocation){
		EnglishTTS obj=new EnglishTTS();
		if(obj.convertToSpeech(patient.getMedicineInformation(),downloadLocation))
			return true;
		else return false;
	}
	
	public boolean callManager(String msgId){
		return (new Outgoing()).callPatient(patient.getPhoneNumber(),msgId);
	}
	
	public void updateRecordAndAlert(MedicineInformer patient,boolean isExecuted){
		Timestamp time=new Timestamp(new Date().getTime());
		Record record=new Record(patient.getPatientId(),IVR_TYPE,msgId,time,isExecuted,"UnKnown");
		
		String hqlAlert=ALERT_UPDATE;
		 
		Session session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();
		 
		Query query=session.createQuery(hqlAlert);
		query.setInteger("msgId", msgId);
		query.setInteger("alertType", IVR_TYPE);
		Alert alert = (Alert)query.list().get(0);
		alert.setIsExecuted(isExecuted);
		alert.setLastTried(time);
		session.update(alert);                        //Update Alert
		 
		
		int id = (Integer) session.save(record);      //insert Record
	    record.setRecordId(id);
	     
	    session.getTransaction().commit();
	    session.close();
	}
	
}
