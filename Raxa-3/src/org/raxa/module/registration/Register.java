/*
 * Assumption:The patient with a valid uuid contains name of patient and phone number.
 * Don't use AlertType as 0;
 */
package org.raxa.module.registration;
import org.raxa.module.database.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.raxa.module.variables.VariableSetter;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;


public class Register implements VariableSetter {
	protected Logger logger = Logger.getLogger(this.getClass());
	private Properties prop = new Properties();
	GetJson json;
	
	public Register(){
		json=new GetJson();
	}
	/*
	 * Check if patient is register for the alert.
	 * If no,register pateint for the Alert
	 * Now check if the patient information is available(patient table)
	 * if no,addPatient information;
	 */
	public boolean addReminder(String pid,String preferLanguage,int alertType){
		
		if(checkIfPatientExist(pid,alertType)){
			logger.info("Patient Already Exist");
			return false;
		}
		else{
			if(!addPateintToAlert(pid,alertType))
				return false;
		}
		
		if((getPatient(pid)!=null) || addPatient(pid,preferLanguage)){
			resetReminder(pid,getPatient(pid).getPatientName(),preferLanguage,alertType);
			return true;
		}
		
		return false;
	}
	
	public boolean checkIfPatientExist(String pid,int alertType){
		List list=getpatientAlert(pid,alertType);
		if(list!=null && list.size()>0)
			return true;
		else return false;
	}
	
	public boolean addPateintToAlert(String pid,int alertType){
		try{
			PAlert patientAlert=new PAlert(pid,alertType);
			Session session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			int id=(Integer)session.save(patientAlert);
			patientAlert.setPatientAlertId(id);
			session.getTransaction().commit();
			session.close();
			return true;
		}
		catch(Exception ex){
			logger.error("Unable to add patient in the alert");
			
		}
		return false;
	}
	
	public boolean addPatient(String pid,String preferLanguage){   //set Name Also
		List<String> info=getPatientNameAndNumberFromRest(pid);  //info.get(1) will be name info.get(2) will be   //UNMARK THIS
		Patient patient=null;
		if(!(info.equals(null)&& info.size()>1)){
			try{
				if(info.size()==2)							//if we don't have secondary number
					patient=new Patient(pid,info.get(0),info.get(1),preferLanguage);
				else patient=new Patient(pid,info.get(0),info.get(1),info.get(2),preferLanguage);  //if we have secondary number information of the patient
				Session session = HibernateUtil.getSessionFactory().openSession();
				session.beginTransaction();
				session.save(patient);
				session.getTransaction().commit();
				session.close();
				return true;
			}
			catch(Exception ex){
				logger.error("Unable to add Patient with id "+ pid);
				return false;
			}
		}
		logger.error("Unable to add Patient with id "+ pid);
		return false;
	}
	
	public void resetReminder(String pid,String name,String preferLanguage,int alertType){
		GetJson json=new GetJson();
		if(alertType==IVR_TYPE){
			List<Reminder> reminder=json.getAlert(pid);
			MessageTemplate m=new MessageTemplate();
			if((!(reminder==null)) && reminder.size()>1){
				for(Reminder r:reminder){
					List<String> template=m.templatize(r.getrawmessage(), preferLanguage, name, pid);  //have not implemented the feature to join all alert that occur between 30 minutes interval.Must incl}
					if(!(template.equals(null) && template.size()>1)){
						r.setTemplatizeMessage(template);
						addReminderToDatabase(pid,r,alertType);
					}
				}
			}
	   }
	}
			
	public void addReminderToDatabase(String pid,Reminder r,int alertType){
		int msgId=getMsgId();int count=0;			//return the max+1 msgID
		Session session = HibernateUtil.getSessionFactory().openSession();
		Timestamp time=new Timestamp(r.getTime().getTime());
		Alert a=new Alert(r.getAlertId(),pid,alertType,msgId,time,null);a.setretryCount(0);a.setIsExecuted(false);a.setServiceInfo(null);
		session.beginTransaction();
		for(String content:r.getTemplatizeMessage()){
			IvrMsg msg=new IvrMsg(msgId,++count,content);
			int id = (Integer) session.save(msg);
			msg.setId(id);
			session.persist(msg);
		}
		session.save(a);
		session.getTransaction().commit();
		session.close();
	}
	/*
	 * Return max msgId + 1.
	 */
	public int getMsgId(){
		int maxID;
		Session session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();
		String hql="select max(a.ivrId) from IvrMsg a";
		List list = session.createQuery(hql).list(); 
		if((Integer)list.get(0)==null)
			maxID=0;
		else 
			maxID = ( (Integer)list.get(0) ).intValue();
		session.getTransaction().commit();
		session.close();
		return maxID+1;
	}
			
	public boolean deleteReminder(String pid,int alertType){
		boolean deletePatient=false;PAlert patientAlert=null;
		Patient patient=getPatient(pid);
		List list=getpatientAlert(pid,alertType);
		if(list!=null && list.size()>0)
			patientAlert=(PAlert) list.get(0);
		else{
			logger.info("Patient with pid:"+pid+"\t is not register for this alert");
			return false;
		}
		
		List allList=getpatientAllAlert(pid);
		
		if(allList.size()==1 && patient!=null)
			deletePatient=true;
		try{
			Session session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			if(patientAlert!=null){
				session.delete(patientAlert);
				if(deletePatient)
					session.delete(patient);
			}
			session.getTransaction().commit();
			session.close();
			return true;
		}
		catch(Exception ex){
			logger.error("unable to delete patient");
			return false;
		}
	}
	

	/*
	 * have to work while making incoming call
	 * May return many ids
	 * Have to ask patient whom they want to register;
	 */
	public List<String> getpatientId(String pnumber){
		
		return null;
	}
	
	public List<String> getPatientNameAndNumberFromRest(String pid){
		try{
			List<String> a=new ArrayList<String>();
			String query="patient="+pid+"&v=full";
			ObjectMapper m=new ObjectMapper();
			RestCall r=new RestCall();
			JsonNode rootNode = m.readTree(r.getRequestGet(query));
			JsonNode patient = rootNode.get("result");
				
			try{
				a.add(patient.path("person").path("display").textValue());
			}
			catch(Exception ex){
				logger.warn("name not found for patient with uuid "+pid);
				a.add(null);
			}
			
			JsonNode attribute=patient.path("person").get("attributes");
			for(int i=0;i<attribute.size();i++)
				if((attribute.get(i).path("attributeType").path("uuid").textValue()).equals("raxa00000-0000-0000-0000-000000000010"))
					a.add(attribute.get(i).path("value").textValue());
			return a;
		}
		
		catch(Exception ex){
			logger.error("Some error while making rest call on patient with id "+pid);
			return null;
		}
		
	}
	
	public Patient getPatient(String pid){
		Session session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();
		Patient patient = (Patient) session.get(Patient.class,pid);
		session.getTransaction().commit();
		session.close();
		return patient;
	}
	
	public List getpatientAlert(String pid,int alertType){
		Session session = HibernateUtil.getSessionFactory().openSession();
		String hql="from PAlert where pid=:pid and alertType=:alertType";
		session.beginTransaction();
		Query query = session.createQuery(hql);
		query.setString("pid", pid);
		query.setInteger("alertType", alertType);
		List list=query.list();
		session.getTransaction().commit();
		session.close();
		return list;
	}
	
	public List getpatientAllAlert(String pid){
		Session session = HibernateUtil.getSessionFactory().openSession();
		String hql="from PAlert where pid=:pid";
		session.beginTransaction();
		Query query = session.createQuery(hql);
		query.setString("pid", pid);
		List list=query.list();
		session.getTransaction().commit();
		session.close();
		return list;
	}
}