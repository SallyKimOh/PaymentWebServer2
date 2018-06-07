package com.metaflow.payment.resource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Path("/test")
public class Test {
    @GET
    @Path("/hello")
    @Produces("application/json")
	public String testMethod() {
		return "TEST";
	}
/*	
	@XmlAccessorType(XmlAccessType.NONE)
	@XmlRootElement(name="users")
	@Path("/users")
	public class ZohoResource {
	    
	    @GET
	    @Path("/hello")
	    @Produces("application/json")
	    public String hello() {
	        
	        return "hello world";
	    }

*/	
	
}
