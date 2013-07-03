package org.raxa.module.MedicalInformation;

public interface MedicineInformerConstant {
	

		
		
		public static final String IVR_MEDICINE_QUERY_DATE="select p1.pnumber,a1.scheduleTime,i1.content,i1.ivrId,p1.pid,a1.lastTry,a1.isExecuted from Patient p1,Alert a1,IvrMsg i1 where p1.pid=a1.pid and a1.alertType=1 and a1.scheduleTime>=:lowerTime and a1.scheduleTime < :upperTime and a1.msgId=i1.ivrId order by i1.id,i1.itemNumber";
		
		public static final String SMS_MEDICINE_QUERY_DATE="select p1.pnumber,a1.scheduleTime,s1.content,s1.smsId,p1.pid,a1.lastTry,a1.isExecuted from Patient p1,Alert a1,SmsMsg s1 where p1.pid=a1.pid and a1.alertType=1 and a1.scheduleTime>=:lowerTime and a1.scheduleTime < :upperTime and a1.msgId=s1.smsId order by s1.id,s1.itemNumber";
	
		public static final String IVR_MEDICINE_QUERY_UUID="select p1.pnumber,a1.scheduleTime,i1.content,i1.ivrId,p1.pid,a1.lastTry,a1.isExecuted from Patient p1,Alert a1,IvrMsg i1 where p1.pid=:pid and p1.pid=a1.pid and a1.alertType=1 and a1.msgId=i1.ivrId order by i1.id,i1.itemNumber";
		
		public static final String SMS_MEDICINE_QUERY_UUID="select p1.pnumber,a1.scheduleTime,s1.content,s1.smsId,p1.pid,a1.lastTry,a1.isExecuted from Patient p1,Alert a1,SMSMsg s1 where p1.pid=:pid and p1.pid=a1.pid and a1.alertType=1 and a1.msgId=s1.smsId order by s1.id,s1.itemNumber";
		
		public static final String IVR_MEDICINE_QUERY_NUMBER="select p1.pnumber,a1.scheduleTime,i1.content,i1.ivrId,p1.pid,a1.lastTry,a1.isExecuted from Patient p1,Alert a1,IvrMsg i1 where p1.pnumber=:pnumber and p1.pid=a1.pid and a1.alertType=1 and a1.msgId=i1.ivrId order by i1.id,i1.itemNumber";
		
		public static final String SMS_MEDICINE_QUERY_NUMBER="select p1.pnumber,a1.scheduleTime,s1.content,s1.smsId,p1.pid,a1.lastTry,a1.isExecuted from Patient p1,Alert a1,SMSMsg s1 where p1.pnumber=:pnumber and p1.pid=a1.pid and a1.alertType=1 and a1.msgId=s1.smsId order by s1.id,s1.itemNumber";
		
		
}
