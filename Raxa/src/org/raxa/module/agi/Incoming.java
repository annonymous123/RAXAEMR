package org.raxa.module.agi;
import java.io.IOException;
import java.util.Properties;
import org.apache.log4j.Logger;
import org.asteriskjava.fastagi.AgiChannel;
import org.asteriskjava.fastagi.AgiException;
import org.asteriskjava.fastagi.AgiRequest;
import org.asteriskjava.fastagi.BaseAgiScript;
import org.raxa.module.scheduler.Caller;
import org.raxa.module.variables.VariableSetter;


public class Incoming extends BaseAgiScript implements VariableSetter
{
	private AgiRequest request;
	private AgiChannel channel;
	private String VOICE_FILE_LOCATION;
	private Logger logger = Logger.getLogger(CallSuccess.class);
	
	public Incoming(){
		setVoiceFileLocation();
	}
	
	public void setVoiceFileLocation(){
		VOICE_FILE_LOCATION=null;
		Properties prop = new Properties();
		try{
		prop.load(Caller.class.getClassLoader().getResourceAsStream("config.properties"));
		VOICE_FILE_LOCATION=prop.getProperty("Voice_Directory");
		}
		catch (IOException ex) {
    		ex.printStackTrace();
    		logger.error("Some error in getting voice file from Voice File Location");
        }
	}
	

    public void service(AgiRequest request, AgiChannel channel)
            throws AgiException
    {
    	answer();
    	this.request=request;
    	this.channel=channel;
        if(request.getContext().equals("outgoing-call"))
        	provideMedicalInfo();
        if(request.getContext().equals("incoming-call"))
        	handleIncomingCall();
        hangup();
        
    }
    
    void handleIncomingCall(){      //will be implemneted once succesfully updated the database.
    	//request.get
    }
   
    void provideMedicalInfo(){
    		String serviceInfo="unknown";   //Doubt
			String[] extension;
			extension=(request.getExtension()).split("a");   //"a" was used as a joing between string in org.raxa.module.ami.Outgoing.java
			int aid=Integer.parseInt(extension[2]);
			int msgId=Integer.parseInt(extension[0]);
    		try{
				for(int i=1;i<=Integer.parseInt(extension[1]);i++){
				channel.streamFile(VOICE_FILE_LOCATION+"/"+msgId+"/"+i);
				}
    			CallSuccess update=new CallSuccess(msgId,aid,true,serviceInfo);
    			update.updateAlert();
    			update.updateRecord();
    		}
        	catch(Exception ex){
        		logger.error("Some Error Occured while playing voice file of msgId "+msgId);
        	}
     }
}
    
    