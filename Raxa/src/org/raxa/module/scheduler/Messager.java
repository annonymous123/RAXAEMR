/*
 * Call SMSModule and Provide Info which will thn send message to patient
 */
package org.raxa.module.scheduler;
import org.raxa.module.MedicalInformation.MedicineInformer;
import java.util.List;

public class Messager implements Runnable {

	private MedicineInformer patient;
	
	public Messager(MedicineInformer patient){
		this.patient=patient;
	}
	
	public void run(){
		System.out.println(patient.getMsgId()+" "+patient.getPatientId()+" "+patient.getPhoneNumber());
		int count=0;
		List<String> a=patient.getMedicineInformation();
		while(true){
			if (a.isEmpty()) break;
			System.out.println((a.get(count++)));
		}
	}
}


