package com.animals.app.controller.resource;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.animals.app.domain.Animal;
import com.animals.app.domain.User;
import com.animals.app.repository.AnimalRepository;
import com.animals.app.repository.UserRepository;
import com.animals.app.repository.Impl.AnimalBreedRepositoryImpl;
import com.animals.app.repository.Impl.AnimalRepositoryImpl;
import com.animals.app.repository.Impl.UserRepositoryImpl;

import sun.misc.BASE64Decoder;

/**
 * Created by 41X on 8/12/2015.
 */

@Path("users")
@RolesAllowed({"гість", "модератор" , "лікар"})
public class UserResource {
	
	private final Response BAD_REQUEST = Response.status(Response.Status.BAD_REQUEST).build();	
	private final Response NOT_FOUND = Response.status(Response.Status.NOT_FOUND).build();	
	private final Response SERVER_ERROR = Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
	private final Response UNAUTHORIZED = Response.status(Response.Status.UNAUTHORIZED).build();
	
	private UserRepository userRep = new UserRepositoryImpl();
	private AnimalRepository animalRep = new AnimalRepositoryImpl();	
	
	private static Logger LOG = LogManager.getLogger(AdminResource.class);
	
	@GET //http:localhost:8080/webapi/users/user/{userId}
	@Produces(MediaType.APPLICATION_JSON)
	@Path("user/{userId}") 	
	public Response getUserById(@PathParam ("userId") String id) {
				
		int parseId = 0;
		
		try {
            if (id == null)
                return BAD_REQUEST;
            parseId = Integer.parseInt(id);
        } catch (NumberFormatException e){
            return BAD_REQUEST;
        }
		
		try {
			User user = userRep.getById(parseId);
			
			if (user == null) return NOT_FOUND;
			
			return Response.ok().entity(user).build();
			
		} catch (Exception e) {
			return SERVER_ERROR;
		}	
		
	}
	
	@GET //http:localhost:8080/webapi/users/user/{userId}/animals
	@Produces(MediaType.APPLICATION_JSON)
	@Path("user/{userId}/animals") 
	public Response getAnimalsByUserId(@PathParam ("userId") String id) {
				
		int parseId = 0;
		
		try {
            if (id == null)
                return BAD_REQUEST;
            parseId = Integer.parseInt(id);
        } catch (NumberFormatException e){
            return BAD_REQUEST;
        }
		
		try {
			List<Animal> animals = animalRep.getAnimalByUserId(parseId);
			
			GenericEntity<List<Animal>> genericAnimals =
			        new GenericEntity<List<Animal>>(animals) {};

			if(genericAnimals == null)
			    return Response.status(Response.Status.NOT_FOUND).build();

			return Response.status(Response.Status.OK).entity(genericAnimals).build();
			
		} catch (Exception e) {
			return SERVER_ERROR;
		}
		
	}
	
	@POST
	@Path("user")//http:localhost:8080/webapi/users/user/
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response insertUser (User user) {		//registration
		
		if (user==null) return BAD_REQUEST;
		
		try {
			userRep.insert(user);			
		} catch (Exception e) {
			return SERVER_ERROR;
		}
		
		return Response.ok().entity(user).build();
		
	}
	
	
	@PUT 
	@Path("user/{userId}") //http:localhost:8080/webapi/users/user/{userId}
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateUser (User user) {
		
		if (user==null) return BAD_REQUEST;
		
		int id;		
		try {
			id = user.getId();
		} catch (Exception e) {
			return BAD_REQUEST;
		}		
				
		if (userRep.getById(id) == null) return NOT_FOUND;     
		
       	try {
			userRep.update(user);
			
			User updatedUser = userRep.getById(id);
			
			return Response.ok().entity(updatedUser).build();
			
		} catch (Exception e) {
			return SERVER_ERROR;
		}		
	}
	
	@GET //http:localhost:8080/webapi/users/user/{userId}/animals/{animalId}   
    @Path("user/{userId}/animals/{animalId}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getAnimal(@PathParam ("userId") String id,
    						  @PathParam("animalId") long animalId,
    						  @Context HttpServletRequest req) {
        
		if (animalId == 0 || animalId<0) {
            return BAD_REQUEST;
        }
		
		HttpSession session = req.getSession(true);
		
		if (!session.getAttribute("userId").equals(id)){
			return UNAUTHORIZED;
		}

        //get animal by id from data base        
        Animal animal = animalRep.getById(animalId);

        return Response.ok().entity(animal).build();
    }
	
	@DELETE //http:localhost:8080/webapi/users/user/{userId}/animals/{animalId} 
    @Path("user/{userId}/animals/{animalId}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response deleteAnimal(@PathParam ("userId") String id,
    							 @PathParam("animalId") long animalId,
    							 @Context HttpServletRequest req) {

		if (animalId == 0 || animalId<0) {
			return BAD_REQUEST;
		}
		
		HttpSession session = req.getSession(true);
		
		if (!session.getAttribute("userId").equals(id)){
			return UNAUTHORIZED;
		}        
   
        String restPath = req.getServletContext().getRealPath("/");				//path to rest root folder   
        
        Animal animal = animalRep.getById(animalId);

        //delete image
        if (animal.getImage() != null) {
            File file = new File(restPath + animal.getImage());
            if (file.exists()) {
                file.delete();
            }
        }

        //delete animal in data base by id
        animalRep.delete(animalId);

        return Response.ok().build();
    }
	
    @POST //http:localhost:8080/webapi/users/user/{userId}/animals/animal
    @Path("user/{userId}/animals/animal")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateAnimal(Animal animal,
    							 @PathParam ("userId") String id,
    							 @Context HttpServletRequest req) {
    	
    	HttpSession session = req.getSession(true);
		
		if (!session.getAttribute("userId").equals(id)){
			return UNAUTHORIZED;
		}  
    	
    	String imageFolder = "images/";

        if(animal == null) {
            return BAD_REQUEST;
        }

        //check breed, if it new insert it into database
        if ((animal.getBreed() != null) && (animal.getBreed().getId() == null) && (animal.getBreed().getBreedUa() != null)) {
            animal.getBreed().setType(animal.getType());
            new AnimalBreedRepositoryImpl().insert_ua(animal.getBreed());
        }

        if ((animal.getImage() != null) && (animal.getImage().length() > (imageFolder.length() + 21))) {
            String fileStr = animal.getImage();
            String fileName = fileStr.substring(0, fileStr.indexOf('\n'));
            fileStr = fileStr.substring(fileStr.indexOf('\n') + 1);
            fileName = new SimpleDateFormat("yyyyMMddHHmmssS").format(new Date()) + fileName.substring(fileName.lastIndexOf('.'));
            String restPath = req.getServletContext().getRealPath("/");         //path to rest root folder
            String httpPath = imageFolder + fileName;                                   //relative path to uploaded file

            //get animal by id from database
            AnimalRepository animalRepository = new AnimalRepositoryImpl();
            Animal oldAnimal = animalRepository.getById(animal.getId());

            //delete old image
            File file = new File(restPath + oldAnimal.getImage());
            if (file.exists()) {
                file.delete();
            }

            byte[] decodedBytes = null;
            BASE64Decoder decoder = new BASE64Decoder();
            try {
                decodedBytes = decoder.decodeBuffer(fileStr);
            } catch (IOException e) {
                e.printStackTrace();
            }

            InputStream is = new ByteArrayInputStream(decodedBytes);

            //Load and save image
            OutputStream out = null;
            try {
                int read = 0;
                byte[] bytes = new byte[1024];

                out = new FileOutputStream(new File(restPath + httpPath));
                while ((read = is.read(bytes)) != -1) {
                    out.write(bytes, 0, read);
                }
                out.flush();
                out.close();
            } catch (IOException e) {
                LOG.error(e);
                return SERVER_ERROR;
            } finally {
                try {
                    out.close();
                } catch (IOException ex) {
                    LOG.error(ex);
                }
            }

            animal.setImage(httpPath);
        }

        //Update animal
        AnimalRepository animalRepository = new AnimalRepositoryImpl();
        animalRepository.update(animal);

        //return relative image path to client
        String json = "{\"filePath\":\"" + animal.getImage() + "\"}";
       
        return Response.ok().entity(json).build();
    }

}
