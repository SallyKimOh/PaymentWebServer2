package com.metaflow.payment.resource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@XmlAccessorType(XmlAccessType.NONE)
//@Path("/home")
public class HomeResource {

	
	@GET
    @Path("/")
    @Produces("application/json")
	public String home(){
		System.out.println("home coming");;
		return "home";
		
	}
 }
