package org.raxa.module.MedicalInformation;

import java.util.Date;
import java.sql.Time;
import java.util.List;



public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		 	java.util.Date date = new Date();
			Time lowertime=new Time(date.getTime());
			List<MedicineInformer> list=(new MedicineInformer()).getMedicineInfoOnId("124", 1);
			
			int count=0;
			System.out.println(list.size());
			while(count<list.size()){
				MedicineInformer a=list.get(count++);
				int count2=0;
				System.out.println(a.getPhoneNumber()+" "+a.getTime()+" " +a.getMsgId()+" "+a.getPatientId());
				List<String> b=a.getMedicineInformation();
					while(count2<b.size()){
						System.out.println(b.get(count2));
						count2++;
					}
				System.out.println("**************************************");
			}
	

}
}
