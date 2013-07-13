package in.crazyme.json;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;


public class JsonPractice {

	
	private String patientId;
	
	public JsonPractice(){
		ApiAuthRest.setURLBase("http://raxaemrtest.elasticbeanstalk.com/ws/rest/v1/");
        ApiAuthRest.setUsername("piyushdane");
        ApiAuthRest.setPassword("Hello123");
	}
	
	public static void main(String[] args) {
		new JsonPractice().getIdFromPhoneNumber("1234567");

	}

	
	public List<String> getIdFromPhoneNumber(String pnumber){
		List<String> pid=new ArrayList<String>();
		try{
		ObjectMapper m = new ObjectMapper();
		String query="patient?q=9818139891&v=full";
		JsonNode rootNode = m.readTree(ApiAuthRest.getRequestGet(query));
		JsonNode results = rootNode.get("results");
		if(!results.isNull()){
			for(JsonNode patient : results){
				pid.add(patient.path("uuid").textValue());
				pid.add(patient.path("person").path("display").textValue());  //takes care of null value itself
				JsonNode attribute=patient.path("person").get("attributes");
				for(int i=0;i<attribute.size();i++){
					
				if((attribute.get(i).path("attributeType").path("uuid").textValue()).equals("raxa00000-0000-0000-0000-000000000010")){
					
					pid.add(attribute.get(i).path("value").textValue());
					
				}
				}
			}
			
		}
		}
		catch(IOException io){
			io.printStackTrace();
			pid=null;
		}
		for(String s:pid){
			System.out.println(s);
		}
		
		return null;
	}
	
	public void registerPatient(String s){
		
	}
	
	public void registerByNumber(String pnumber){

	}
	
	public void numbertoRegister(String pnumber){
		
	}
}
	
	
	

