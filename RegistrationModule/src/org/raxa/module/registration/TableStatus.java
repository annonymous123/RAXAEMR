package org.raxa.module.registration;
/*
 * This will keep record of max ivrid and smsid so that we can increment it by 1 the next time.
 * Auto Increment cannot be used as the mentioned column are not a key.
 */
public class TableStatus {
   private String tableName;
   private int idStatus;
   
   public TableStatus(String name,int status){
	   tableName=name;
	   idStatus=status;
   }
   
   public TableStatus(){}
   
   public String getTableName(){
	   return tableName;
   }
   
   public void setTableName(String name){
	   tableName=name;
   }
   
   public int getIdStatus(){
	   return idStatus;
   }
   
   public void setIdStatus(int name){
	   idStatus=name;
   }
}
