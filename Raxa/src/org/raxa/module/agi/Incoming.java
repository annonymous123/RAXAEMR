package org.raxa.module.agi;
import org.asteriskjava.fastagi.AgiChannel;
import org.asteriskjava.fastagi.AgiException;
import org.asteriskjava.fastagi.AgiRequest;
import org.asteriskjava.fastagi.BaseAgiScript;
import org.raxa.module.variables.VariableSetter;


public class Incoming extends BaseAgiScript implements VariableSetter
{
	public AgiRequest request;
	public AgiChannel channel;

    public void service(AgiRequest request, AgiChannel channel)
            throws AgiException
    {
    	answer();
    	System.out.println("in");
    	this.request=request;
    	this.channel=channel;
        if(request.getContext().equals("outgoing-call"))
        	provideMedicalInfo();
        hangup();
        
    }
   
    void provideMedicalInfo(){
    		try{
    			String[] extension=request.getExtension().split("a");
    			System.out.println(Integer.parseInt(extension[1]));
    			System.out.println(extension[0]);
    			for(int i=1;i<=Integer.parseInt(extension[1]);i++){
    				channel.streamFile(MEDICINE_VOICE_FOLDER_LOCATION+"/"+extension[0]+"/"+i);
    			}
    		}
        	catch(Exception ex){
        		System.out.println("Some Error Occured");
        	}
     }
}
    
    