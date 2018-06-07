package com.metaflow.payment.resource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

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

    
/* 
    @GET
    @Path("/{id}")
    @Produces("application/json")
    public Response getUserById(@PathParam("id") int id) throws URISyntaxException
    {
        User user = DB.get(id);
        if(user == null) {
            return Response.status(404).build();
        }
        return Response
                .status(200)
                .entity(user)
                .contentLocation(new URI("/user-management/"+id)).build();
    }
 
    @PUT
    @Path("/{id}")
    @Consumes("application/json")
    @Produces("application/json")
    public Response updateUser(@PathParam("id") int id, User user) throws URISyntaxException
    {
        User temp = DB.get(id);
        if(user == null) {
            return Response.status(404).build();
        }
        temp.setFirstName(user.getFirstName());
        temp.setLastName(user.getLastName());
        DB.put(temp.getId(), temp);
        return Response.status(200).entity(temp).build();
    }
 
    @DELETE
    @Path("/{id}")
    public Response deleteUser(@PathParam("id") int id) throws URISyntaxException {
        User user = DB.get(id);
        if(user != null) {
            DB.remove(user.getId());
            return Response.status(200).build();
        }
        return Response.status(404).build();
    }
 
    @GET
    @Path("/pdf")
    public Response downloadPdfFile()
    {
        StreamingOutput fileStream =  new StreamingOutput()
        {
            @Override
            public void write(java.io.OutputStream output) throws IOException, WebApplicationException
            {
                try
                {
                    java.nio.file.Path path = Paths.get("C:\\project\\download\\bill\\2018-01-31_20180129132805.txt");
                    byte[] data = Files.readAllBytes(path);
                    output.write(data);
                    output.flush();
                }
                catch (Exception e)
                {
                    throw new WebApplicationException("File Not Found !!");
                }
            }
        };
        return Response
                .ok(fileStream, MediaType.APPLICATION_OCTET_STREAM)
                .header("content-disposition","attachment; filename = myfile.txt")
                .build();
    }    
    @GET
    @Path("/download")
    public Response downloadBatchFile()
    {

    	File file = new File("C:\\project\\download\\bill\\2018-01-31_20180129132805.txt");

    	return Response
                .ok(file, MediaType.APPLICATION_OCTET_STREAM)
                .header("content-disposition","attachment; filename = myfile.txt")
                .build();
    }    
    
    static
    {
        User user1 = new User();
        user1.setId(1);
        user1.setFirstName("John");
        user1.setLastName("Wick");
        user1.setUri("/user-management/1");
 
        User user2 = new User();
        user2.setId(2);
        user2.setFirstName("Harry");
        user2.setLastName("Potter");
        user2.setUri("/user-management/2");
         
        DB.put(user1.getId(), user1);
        DB.put(user2.getId(), user2);
    }
    */
}
