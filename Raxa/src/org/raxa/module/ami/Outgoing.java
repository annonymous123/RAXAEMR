/*Call Patient Number and set Extension to msgId of the patient;
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
    private String context;
    private String callerId;
    private Long timeOut;
    private String ASTERISK_SERVER_URL;
    private String MANAGER_USERNAME;
    private String MANAGER_PASSWORD;
    
    public Outgoing(){
    	   setProperties();
    	   
    	   ManagerConnectionFactory factory = new ManagerConnectionFactory(
    			   ASTERISK_SERVER_URL, MANAGER_USERNAME, MANAGER_PASSWORD);

           this.managerConnection = factory.createManagerConnection();
           
           context="outgoing-call";
           
           callerId="Take Medicine";
           
           timeOut=30000L;
    }
    
    public void setProperties(){
    	Properties prop = new Properties();
 	    ASTERISK_SERVER_URL="127.0.0.1";
 	    MANAGER_USERNAME="manager";
 	    MANAGER_PASSWORD="squirrel";
 	   try {
            
    		prop.load(TimeSetter.class.getClassLoader().getResourceAsStream("config.properties"));
    		ASTERISK_SERVER_URL=prop.getProperty("Asterisk_URL");
    		MANAGER_USERNAME=prop.getProperty("Manager_Username");
    		MANAGER_PASSWORD=prop.getProperty("Manager_Password");
    	   } 
    	catch (IOException ex) {
    		ex.printStackTrace();
        }
    }
    
    
    public void setContext(String s){
    	context=s;
    }
    
    public void setCallerId(String s){
    	callerId=s;
    }
    
    public void setTimeout(Long l){
    	timeOut=l;
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
        originateAction.setCallerId(callerId);
        originateAction.setChannel(pnumber);
        originateAction.setContext(context);
        originateAction.setExten(msgId);
        originateAction.setPriority(new Integer(1));
        originateAction.setTimeout(timeOut);
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
