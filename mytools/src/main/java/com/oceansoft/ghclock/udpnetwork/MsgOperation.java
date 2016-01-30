package com.oceansoft.ghclock.udpnetwork;

import java.util.ArrayList;
import java.util.List;

public class MsgOperation {

	 private String command = "";
     private final List<String> parameters = new ArrayList<String>();
     private String spliter = "|";
     /// <summary>
     /// 是否为心跳包
     /// </summary>
     public boolean IsTest;

     public MsgOperation(String msg) {
    	 
    	 String[] array = msg.split(this.spliter);
         
         this.command = array[0];            
        
         for (int i = 1; i < array.length; i++)
         {
             parameters.add(array[i]);
         }
     }
     
     public void MessageHandle()
     {
         if (this.command.length() == 0)
         {
             return;
         }            
         try
         {
             if (this.command.equals(CLIENT.Test)){
            	 LocalVariable.ServerLinked = true;
                 this.IsTest = true;
                 LinkTest(parameters.get(0));
             } else if(this.command.equals(CLIENT.TextMsg)){
            	 /*if (parameters.size() == 3)
                 {
                    MsgManager.ShowMessage(parameters.get(0), parameters.get(1), parameters.get(2));                               
                 }*/
             } else if(this.command.equals(CLIENT.Online)){
            	 
             } else if(this.command.equals(CLIENT.Offline)){
            	 
             } else if(this.command.equals(CLIENT.UpdatePatient)){
            	 if (parameters.get(0) == LocalSession.WardId) {
                    // LocalCache._Update(CacheDataList.BedPatients);
                 }
             } else if(this.command.equals(CLIENT.UpdateAdministrationClass)){
//            	 LocalCache._Update(CacheDataList.AdministrationClass);
             } else if(this.command.equals(CLIENT.UpdateAdministration)){
//            	 LocalCache._Update(CacheDataList.Administration);
             }else if(this.command.equals(CLIENT.UpdateBed)){
//            	 LocalCache._Update(CacheDataList.Patient);
             } else if(this.command.equals(CLIENT.UpdateEvent)){
//            	 LocalCache._Update(CacheDataList.Event);
             } else if(this.command.equals(CLIENT.UpdateUser)){
//            	 LocalCache._Update(CacheDataList.User);
             } else if(this.command.equals(CLIENT.UpdateNursingRecord)){
//            	 LocalCache._Update(CacheDataList.NursingRecord);
             } else if(this.command.equals(CLIENT.UpdateHealthEdu)){
//            	 LocalCache._Update(CacheDataList.HealthEdu);
             } else if(this.command.equals(CLIENT.UpdateVitalSign)){
//            	 LocalCache._Update(CacheDataList.VitalSign);
             } else if(this.command.equals(CLIENT.UpdateAssessment)){
//            	 LocalCache._Update(CacheDataList.Assessment);
             }            
         }
         catch (Exception ex){
             ex.printStackTrace();
         }
     }    

     
     private void LinkTest(String timeStr) {
    	 
         long time = 0;
         
         try{
        	 time = Long.parseLong(timeStr);
        	 long span = time - LocalVariable.Ticks;
             LocalVariable.Ticks = time;
             if (span < 0)
             {
                 span = 0;
             }
             //毫秒
             //1个 tick 代表100纳秒,1毫秒等于10000个ticks
             LocalVariable.SpanMilliseconds = (int)span / 10000 + 5;
         }catch(Exception ex){
        	 ex.printStackTrace();
         }       
     }
     
}
