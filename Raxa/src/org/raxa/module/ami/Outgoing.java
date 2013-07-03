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
import org.apache.log4j.PropertyConfigurator;
import org.raxa.module.variables.VariableSetter;

public class Outgoing implements VariableSetter
{
    private ManagerConnection managerConnection;
    private String context;
    private String callerId;
    private Long timeOut;
    
    public Outgoing(){
    	   ManagerConnectionFactory factory = new ManagerConnectionFactory(
    			   ASTERISK_SERVER_URL, MANAGER_USERNAME, MANAGER_PASSWORD);

           this.managerConnection = factory.createManagerConnection();
           
           context="outgoing-call";
           
           callerId="Take Medicine";
           
           timeOut=30000L;
    }
    
    public void setContext(String s){
    	context=s;
    }
    
    public void setCallerId(String s){
    	callerId=s;
    }
    
    public void gettimeout(Long l){
    	timeOut=l;
    }
    		
    
    public boolean callPatient(String pnumber,String msgId)
    {	
    	Logger logger = Logger.getLogger(Outgoing.class);
    	pnumber="SIP/1000abc"; //will change to phonenumber once we have outgoing call facility.
    	
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
