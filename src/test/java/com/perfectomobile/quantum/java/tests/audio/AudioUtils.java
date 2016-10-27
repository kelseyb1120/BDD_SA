package com.perfectomobile.quantum.java.tests.audio;
 
import org.openqa.selenium.remote.RemoteWebDriver;
 
 
import java.util.HashMap;
import java.util.Map;

public class AudioUtils  {

	public static String startRecord(RemoteWebDriver driver){
 		Map<String, String> params = new HashMap<String, String>();
		params.put("Action","START");		
		Object result = driver.executeScript("mobile:Audio:Record", params);
 		return result.toString();
	}
	

	public static String StopRecord(RemoteWebDriver driver){
 		Map<String, String> params = new HashMap<String, String>();
		params.put("Action","STOP");		
		Object result = driver.executeScript("mobile:Audio:Stop", params);
 		return result.toString();
	}
	
	public static String speech2text(RemoteWebDriver driver,String file){
 		Map<String, String> params = new HashMap<String, String>();
		params.put("AudiofileName",file);		
		params.put("Action","speech2textjava");		
		Object result = driver.executeScript("mobile:Audio:speech2textJ", params);
 		return result.toString();
	}
	// command = am start -S com.android.settings/.Settings
	public static String adb(RemoteWebDriver driver,String command){
 		Map<String, String> params = new HashMap<String, String>();
		params.put("commande",command);		
 		Object result = driver.executeScript("mobile:handset:shell", params);
 		return result.toString();
	}

	public static void sleep(long mSec){
 		 try {
			Thread.sleep(mSec);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

} 