package com.metaflow.payment.client;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.hateoas.Resource;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.metaflow.payment.comm.PropUtil;
import com.metaflow.payment.model.BillPayment.BillPayment;


public class PayClient {

	public static void main(String[] args) throws IOException
	{
		
/*		
		ObjectMapper mapper = new ObjectMapper();
		 String test ="{\"code\": 0,\"message\": \"success\","+
			 		"\"test\":[\"msg 1\",\"msg 2\",\"msg 3\"],"+
		 		"\"contact\": {\n" + 
		 		"        \"contact_id\": \"1256987000000125205\",\n" + 
		 				"        \"contact_name\": \"Pierre Lefebvre\"},"+
		 		"    \"bills\": [\n" + 
		 		"        {\n" + 
		 		"            \"bill_id\": \"1256987000000185715\",\n" + 
		 		"            \"vendor_id\": \"1256987000000125143\",\n" + 
		 		"            \"vendor_name\": \"Libi Slepoy\",\n" + 
		 		"        },\n" + 
		 		"        {\n" + 
		 		"            \"bill_id\": \"1256987000000150977\",\n" + 
		 		"            \"vendor_id\": \"1256987000000125143\",\n" + 
		 		"            \"vendor_name\": \"Libi Slepoy\",\n" + 
		 		"        },\n" + 
		 		"        {\n" + 
		 		"            \"bill_id\": \"1256987000000128559\",\n" + 
		 		"            \"vendor_id\": \"1256987000000125205\",\n" + 
		 		"            \"vendor_name\": \"Pierre Lefebvre\",\n" + 
		 		"        }\n" + 
		 		"     ]\n" + 
		 		"}";

		 
		
//		 BankInVo obj = mapper.readValue(test, BankInVo.class);
		 JSONParser parser = new JSONParser();
		 try {
			Object obj = parser.parse(test);
			JSONObject jsonObject = (JSONObject) obj;
            System.out.println(jsonObject);

            System.out.println(jsonObject.get("code"));
            String name = (String) jsonObject.get("message");
            System.out.println(name);
            
            System.out.println(jsonObject.get("contact"));
            JSONObject jsonObject1 = (JSONObject) jsonObject.get("contact");
            
            System.out.println(jsonObject1.get("contact_id"));
            
         // loop array
            JSONArray msg = (JSONArray) jsonObject.get("test");
            Iterator<String> iterator = msg.iterator();
            while (iterator.hasNext()) {
                System.out.println(iterator.next());
            }           
            
            JSONArray msg1 = (JSONArray) jsonObject.get("bills");
            
            System.out.println(msg1);;
            Iterator<JSONObject> iterator1 = msg1.iterator();
            while (iterator1.hasNext()) {
            	JSONObject billInfo = iterator1.next();
            	
                System.out.println(billInfo.get("bill_id"));
                System.out.println(billInfo.get("vendor_id"));
//                System.out.println(iterator1.next().get("bill_id"));
//                System.out.println(iterator1.next());
            }           
            
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		 
*/	 
		 
		
//	    httpGETCollectionExample2();
	    getPayCheckList();
//	    httpGETVendorInfo();
	    
	    
	}
	 
	private static String httpGETCollectionExample2()
	{

		Client client = ClientBuilder.newClient();
		String jsonStr = client
	            .target("http://localhost:8091/billPayment?dueDate=2018-05-15")
	            .request(MediaType.APPLICATION_JSON)
	            .header("Authorization", "Zoho-authtoken 6e16e84f45f7627d0b08f3609241f094")

	            .get(String.class);		
		
		System.out.println(jsonStr);
		return jsonStr;
	}
	

	public static List<String> getPayCheckList() throws JsonParseException, JsonMappingException, IOException {
		
		List<String> list = new ArrayList<String>();
		
		PayClient mgr = new PayClient();

		String jsonResult = mgr.httpGETCollectionExample2();

		ObjectMapper mapper = new ObjectMapper();

//			DefaultList<Subject<List<Paycheck<Paging<Links>>>>,Paging<Links>> listCar = mapper.readValue(jsonResult, new TypeReference<DefaultList<Subject<List<Paycheck<Paging<Links>>>>,Paging<Links>>>(){});
//			listCar.get_embedded().getPaycheck().forEach(item -> {
//				list.add(item.get_links().getSelf().getHref());
//			});

		
//		TypeReference<List<Resource<BillPayment>>> typeReference = new TypeReference<List<Resource<BillPayment>>>() {};
		TypeReference<Resource<BillPayment>> typeReference = new TypeReference<Resource<BillPayment>>() {};
		
//			List<BillPayment> bills = mapper.readValue(jsonResult, typeReference);
		
		System.out.println("==>"+mapper.readValue(jsonResult, typeReference));
 
		PropUtil util = new PropUtil();
		JSONObject jsonObject = (JSONObject) util.getJsonObjFromStr(jsonResult).get("_embedded");
        JSONArray msg1 = (JSONArray)jsonObject.get("billPayments");
        
        System.out.println(msg1);;
        Iterator<JSONObject> iterator1 = msg1.iterator();
        while (iterator1.hasNext()) {
        	JSONObject billInfo = iterator1.next();
        	
            System.out.println("BillId ==>"+billInfo.get("billId"));
            System.out.println("TransType==>"+billInfo.get("transType"));
            
            String jsonInfo = mgr.httpGETCollectionExampleInfo((String) billInfo.get("billId"));
            JSONObject jsonBillInfo = (JSONObject) util.getJsonObjFromStr(jsonInfo);
            
            System.out.println("result==>"+jsonBillInfo);
        }           

		return list;
		
	}
 
	
	private static String httpGETCollectionExampleInfo(String billId)
	{

		Client client = ClientBuilder.newClient();
		String jsonStr = client
	            .target("http://localhost:8091/billPayment/"+billId)
	            .request(MediaType.APPLICATION_JSON)
	            .header("Authorization", "Zoho-authtoken 6e16e84f45f7627d0b08f3609241f094")

	            .get(String.class);		
		
		System.out.println(jsonStr);
		return jsonStr;
	}
	
	
	
	
	
	private static void httpGETCollectionExample()
	{

		Client client = ClientBuilder.newClient();
		String jsonStr = client
	            .target("https://books.zoho.com/api/v3/bills?organization_id=664786411&due_date=2018-05-15")
	            .request(MediaType.APPLICATION_JSON)
	            .header("Authorization", "Zoho-authtoken 6e16e84f45f7627d0b08f3609241f094")

	            .get(String.class);		
		
		System.out.println(jsonStr);
		
	}
	
	private static void httpGETVendorInfo()
	{

		Client client = ClientBuilder.newClient();
		String jsonStr = client
	            .target("https://books.zoho.com/api/v3/contacts/1256987000000125205?organization_id=664786411")
	            .request(MediaType.APPLICATION_JSON)
	            .header("Authorization", "Zoho-authtoken 6e16e84f45f7627d0b08f3609241f094")

	            .get(String.class);		
		
		
		System.out.println(jsonStr);
		
		 JSONParser parser = new JSONParser();
		 try {
			Object obj = parser.parse(jsonStr);
			JSONObject jsonObject = (JSONObject) obj;
           System.out.println("object:"+jsonObject);

           Long code = (Long) jsonObject.get("code");
           System.out.println("code:"+code);
           
           String bankInstitutionNum = "";
           String bankBranchNum = "";
           String bankAccountNum = "";
           
           if (code == 0) {
        	   JSONObject jsonObjectContact = (JSONObject) jsonObject.get("contact");
        	   bankInstitutionNum = (String)jsonObjectContact.get("cf_bank_institution_number");
        	   bankBranchNum = (String)jsonObjectContact.get("cf_bank_branch_number");
        	   bankAccountNum = (String)jsonObjectContact.get("cf_bank_account_number");
        	   
           }
           
           System.out.println("bankInstitutionNum:"+bankInstitutionNum);
           System.out.println("bankBranchNum:"+bankBranchNum);
           System.out.println("bankAccountNum:"+bankAccountNum);
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 

		
	}	
}
