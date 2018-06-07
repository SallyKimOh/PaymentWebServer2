package com.metaflow.payment.comm;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class PropUtil {
	public Properties getPropValue() {
		
		Properties prop = new Properties();
		try {
//			prop.load(new FileInputStream(new ConstantsValue().PROPERTIES_PATH));
			String filename = "config.properties";
			System.out.println("===>"+getClass().getResourceAsStream(filename));
//			prop.load(getClass().getClassLoader().getResourceAsStream(filename));
			prop.load(getClass().getResourceAsStream(filename));
//			prop.load(PropUtil.class.getResourceAsStream("/config.properties"));
			
//			InputStream in = getClass().getResourceAsStream(filename);
//			prop.load(in);
//			in.close();			
			
			  
		} catch (IOException ex) {
			ex.printStackTrace();
	    } 
		return prop;
		
	}

//	public int getBatchNum() {
//		PropertiesConfiguration conf = null;
//		int batchNum = 0;
//		try {
//			conf = new PropertiesConfiguration(new ConstantsValue().PROPERTIES_PATH);
//			batchNum = conf.getInt("BATCH_NUM");
//			
//			conf.setProperty("BATCH_NUM", String.valueOf(batchNum+1));
//			conf.save();
//		} catch (ConfigurationException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return batchNum;
//		
//	}
	
	public String getMatchValue(String val, int size) {

		String returnVal  = val;
		
		if (returnVal == null) returnVal = "0";
		if (returnVal.length() < size) {
			while (returnVal.length() < size) {
				returnVal ="0"+returnVal;
			}
		} else if (returnVal.length() > size) {
			returnVal = returnVal.substring(0, size);
		}
		
		return returnVal;
		
	}

	public String getRMatchValue(String val, int size) {

		String returnVal  = val;
		
		if (returnVal == null) returnVal = "0";
		if (returnVal.length() < size) {
			while (returnVal.length() < size) {
				returnVal = returnVal+"0";
			}
		} else if (returnVal.length() > size) {
			returnVal = returnVal.substring(0, size);
		}
		
		return returnVal;
		
	}
	
	public String getMatchNullValue(String val, int size) {

		String returnVal = val;
		if (returnVal == null) returnVal = "";
		if (returnVal.length() < size) {
			while (returnVal.length() < size) {
				returnVal= returnVal + " ";
			}
		} else if (returnVal.length() > size) {
			returnVal = returnVal.substring(0, size);
		}
		
		return returnVal;
		
	}


	/**
	 * Modify Batch Number after create batch files
	 * @param value : new number for next batch files
	 * @throws IOException
	 */
	public void modifyProperty(String value) throws IOException {
//			getPropValue().setProperty("BATCH_NUM",value);
			
//			//Creating properties files from Java program
	        Properties props = new Properties();
	        String path = PropUtil.class.getResource("").getPath();
//	        
//	        props.setProperty("BATCH_NUM", value);
//	        
//	        FileOutputStream fos = new FileOutputStream(path+"config.properties");
//	      
		   	props = new PropUtil().getPropValue();
		   	props.setProperty("BATCH_NUM",value);
		
	        FileOutputStream fos = new FileOutputStream(path+"config.properties");
		   
	        //updating properites into properties file
	        props.store(fos, "Properties file changed");
	      
	        fos.close();		
	}
	
	public JSONObject getJsonObjFromStr(String str) {
		
		JSONParser parser = new JSONParser();
		Object obj;
		JSONObject jsonObject = null;
		try {
			obj = parser.parse(str);
			jsonObject = (JSONObject) obj;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return jsonObject;
	}
	
	
	public static void main(String[] args) {
			Properties prop = new Properties();
			String filename = "config.properties";
			InputStream in = PropUtil.class
		            .getResourceAsStream(filename);
		    try {
		        prop.load(in);
		        in.close();
		    } catch (IOException e) {
		        e.printStackTrace();
		    }
		    System.out.println("path==>"+prop.getProperty("BILL_PATH"));
		    
		 String url = new PropUtil().getPropValue().getProperty("BILL_PATH");
		System.out.println(url);
	}
}
