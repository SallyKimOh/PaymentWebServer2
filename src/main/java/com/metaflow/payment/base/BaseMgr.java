package com.metaflow.payment.base;

import java.io.IOException;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;

import com.metaflow.payment.comm.PropUtil;

 
 
public class BaseMgr {
	public String getConnectServer(String path) throws IOException {

		String url = new PropUtil().getPropValue().getProperty("SERVER_URL");
		String authKey = new PropUtil().getPropValue().getProperty("AUTH_KEY");
		
		System.out.println("url ==>"+ url+path);
		
		Client client = ClientBuilder.newClient();
		String jsonStr = client
	            .target(url+path)
	            .request(MediaType.APPLICATION_JSON)
	            .header("Authorization", "Zoho-authtoken "+authKey)
	            .get(String.class);		
		
		System.out.println(jsonStr);
		return jsonStr;

	}	
	

}
