package org.raxa.module.agi;
import org.asteriskjava.fastagi.AgiChannel;
import org.asteriskjava.fastagi.AgiException;
import org.asteriskjava.fastagi.AgiRequest;
import org.asteriskjava.fastagi.BaseAgiScript;
import org.asteriskjava.fastagi.DefaultAgiServer;
import java.util.Map;

public class Incoming extends BaseAgiScript
{

    public void service(AgiRequest request, AgiChannel channel)
            throws AgiException
    {
    	//initialise();
      answer();
        int count=0;
        System.out.println(count);
		//new testClass().myFunc();
        System.out.println(request.getContext());
        System.out.println(request.getCallerId());
        System.out.println(request.getCallerIdNumber());
        System.out.println(request.getExtension());
        System.out.println(request.getParameter("phone"));
        Map<String,String[]> param=request.getParameterMap();
        request.toString();
        channel.toString();
        channel.getData("phone");
        channel.getFullVariable("phone");
        channel.getVariable("phone");
        
       
		for(int i=1;i<3;i++){
			channel.streamFile("/home/atul/Desktop/Patient/"+i);
			System.out.println(i);
		}
        channel.hangup();
        
        
    }
    
/*
 * Initialise the objects of other class
 */
    	
/*
 * Perform function on Asterisk
 */
    	public void atul(){
    	try{
    	
    	char i=channel.getOption("/home/atul/Desktop/b","12349");
    	System.out.println(i);
    }
    	catch(Exception ex){
    		System.out.println("Fuck You");
    	}
    }
    	
    	
    	
    	
 /*
  * Members
  */
    	public AgiRequest request;
    	public AgiChannel channel;
    	
}
    
    