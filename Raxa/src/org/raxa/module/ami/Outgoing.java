/*Call Patient Number and set Extension to magId of the patient;
 * 
 */
package org.raxa.module.ami;
import java.io.IOException;
import org.asteriskjava.manager.AuthenticationFailedException;
import org.asteriskjava.manager.ManagerConnection;
import org.asteriskjava.manager.ManagerConnectionFactory;
import org.asteriskjava.manager.TimeoutException;
import org.asteriskjava.manager.action.OriginateAction;
import org.asteriskjava.manager.response.ManagerResponse;
import org.apache.log4j.Logger;
import org.raxa.module.scheduler.TimeSetter;
import java.util.Properties;


public class Outgoing{
	
    private ManagerConnection managerConnection;
    private String CONTEXT;
    private String CALLERID;
    private Long TIMEOUT;
    private String ASTERISK_SERVER_URL;
    private String MANAGER_USERNAME;
    private String MANAGER_PASSWORD;
    
    public Outgoing(){
    	   setProperties();
    	   
    	   ManagerConnectionFactory factory = new ManagerConnectionFactory(
    			   ASTERISK_SERVER_URL, MANAGER_USERNAME, MANAGER_PASSWORD);

           this.managerConnection = factory.createManagerConnection();
           
    }
    
    public void setProperties(){
    	Logger logger = Logger.getLogger(Outgoing.class);
    	Properties prop = new Properties();
    	//initialising
 	    ASTERISK_SERVER_URL=null;
 	    MANAGER_USERNAME=null;
 	    MANAGER_PASSWORD=null;
 	    CONTEXT=null;
 	    CALLERID=null;
 	    TIMEOUT=null;
 	    
 	   try {
            
    		prop.load(TimeSetter.class.getClassLoader().getResourceAsStream("config.properties"));
    		ASTERISK_SERVER_URL=prop.getProperty("Asterisk_URL");
    		MANAGER_USERNAME=prop.getProperty("Manager_Username");
    		MANAGER_PASSWORD=prop.getProperty("Manager_Password");
    		CONTEXT=prop.getProperty("MedRemind_Context");
     	    CALLERID=prop.getProperty("MedRemind_CallerId");
     	    TIMEOUT=Long.parseLong(prop.getProperty("MedRemind_TimeOut"),10);
    	   } 
    	catch (IOException ex) {
    		ex.printStackTrace();
    		logger.error("Some error occur while retreiving information from config.properties.Unable to forward call to asterisk");
        }
    }
    
    
    public void setContext(String s){
    	CONTEXT=s;
    }
    
    public void setCallerId(String s){
    	CALLERID=s;
    }
    
    public void setTimeout(Long l){
    	TIMEOUT=l;
    }
    		
    
    public boolean callPatient(String pnumber,String msgId,int totalFile,String aid)
    {	
    	Logger logger = Logger.getLogger(Outgoing.class);
    	pnumber="SIP/1000abc"; //will change to phonenumber once we have outgoing call facility.
    	msgId=msgId+"a"+String.valueOf(totalFile)+"a"+aid;
    	try{
        OriginateAction originateAction=new OriginateAction();
        ManagerResponse originateResponse=new ManagerResponse();
        managerConnection.login();
        originateAction = new OriginateAction();
        originateAction.setCallerId(CALLERID);
        originateAction.setChannel(pnumber);
        originateAction.setContext(CONTEXT);
        originateAction.setExten(msgId);
        originateAction.setPriority(new Integer(1));
        originateAction.setTimeout(TIMEOUT);
        originateAction.setAsync(true);
        originateResponse = managerConnection.sendAction(originateAction,10000);
        logger.info(originateResponse.getResponse());
        managerConnection.logoff();
        return true;
        
    	}
    	catch(AuthenticationFailedException ex){
    		logger.error("In org.raxa.module.ami.Outgoing.java:Authentication Failure");
    		return false;
    	}
    	catch(TimeoutException ex){
    		logger.error("In org.raxa.module.ami.Outgoing.java:TimeOut Exception");
    		return false;
    	}
    	catch(Exception ex){
    		logger.error("In org.raxa.module.ami.Outgoing.java:Some Error Occured");
    		return false;
    	}
    	
    
    }
    
    
}
