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
import javax.validation.Valid;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
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

import com.animals.app.domain.UsersFilter;

import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.animals.app.domain.Animal;
import com.animals.app.domain.AnimalBreed;
import com.animals.app.domain.AnimalType;
import com.animals.app.domain.AnimalsFilter;
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
	
	private static Logger LOG = LogManager.getLogger(UserResource.class);
	
	private final Response BAD_REQUEST = Response.status(Response.Status.BAD_REQUEST).build();	
	private final Response NOT_FOUND = Response.status(Response.Status.NOT_FOUND).build();	
	private final Response SERVER_ERROR = Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
	private final Response UNAUTHORIZED = Response.status(Response.Status.UNAUTHORIZED).build();
	
	private final String IMAGE_FOLDER = "images/";
	private final int LENGTH_IMAGE = 50;
	
	private UserRepository userRep = new UserRepositoryImpl();
	private AnimalRepository animalRep = new AnimalRepositoryImpl();		
	
	@GET //http:localhost:8080/webapi/users/user/{userId}
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Path("user/{userId}") 	
	public Response getUserById(@PathParam ("userId") @DecimalMin(value = "1") int id) {
				
		try {
			User user = userRep.getById(id);
			
			if (user == null) return NOT_FOUND;
			
			return Response.ok().entity(user).build();
			
		} catch (Exception e) {
			LOG.error(e);
			return SERVER_ERROR;
		}	
		
	}	
	
	
	@PUT 
	@Path("user/{userId}") //http:localhost:8080/webapi/users/user/{userId}  //User update
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public Response updateUser (@Valid User user) {
		
		if (user==null) return BAD_REQUEST;
		
		int id;		
		try {
			id = user.getId();
		} catch (Exception e) {
			LOG.error(e);
			return BAD_REQUEST;
		}		
				
		if (userRep.getById(id) == null) return NOT_FOUND;     
		
       	try {
			userRep.update(user);
			
			User updatedUser = userRep.getById(id);
			
			return Response.ok().entity(updatedUser).build();
			
		} catch (Exception e) {
			LOG.error(e);
			return SERVER_ERROR;
		}		
	}
	
	@POST  //http:localhost:8080/webapi/users/user/{userId}/animals  
    @Path("user/{userId}/animals")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response geetUserAnimalsByUserId(@PathParam("userId") @DecimalMin(value = "1") long userId, 
    										@Valid @NotNull AnimalsFilter animalsFilter,
    										@Context HttpServletRequest req) {
		
		HttpSession session = req.getSession(true);
		if (!session.getAttribute("userId").equals(Long.toString(userId))){
			return UNAUTHORIZED;
		}
		
		if(animalsFilter == null) return BAD_REQUEST;
       
        if ((animalsFilter.getPage() <= 0) || (animalsFilter.getLimit() <= 0)) {
            return BAD_REQUEST;
        }

        //get list of user animals from data base
        List<Animal> animals = userRep.getUserAnimals(userId, animalsFilter.getOffset(), animalsFilter.getLimit());

        //cast list of animals to generic list
        GenericEntity<List<Animal>> genericAnimals = new GenericEntity<List<Animal>>(animals) {};

        if(genericAnimals == null) {
            return NOT_FOUND;
        }

        return Response.ok().entity(genericAnimals).build();
    }	

	
	@GET //http:localhost:8080/webapi/users/user/{userId}/animals/{animalId}   
    @Path("user/{userId}/animals/{animalId}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getAnimal(@PathParam ("userId") String id,
    						  @PathParam("animalId") @DecimalMin(value = "1") long animalId,
    						  @Context HttpServletRequest req) {
        		
		
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
    public Response deleteAnimal(@PathParam ("userId") @NotNull String id,
    							 @PathParam("animalId") @DecimalMin(value = "1") long animalId,
    							 @Context HttpServletRequest req) {
				
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
    public Response updateAnimal(@Valid Animal animal,
    							 @PathParam ("userId") @NotNull String id,
    							 @Context HttpServletRequest req) {
    	
    	HttpSession session = req.getSession(true);
		
		if (!session.getAttribute("userId").equals(id)){
			return UNAUTHORIZED;
		} 		

        //check breed, if it new insert it into database
        saveNewBreed(animal.getBreed(), animal.getType());

        //save new image if set
        animal.setImage(saveNewImage(req.getServletContext().getRealPath("/"),
                animal.getImage(), animal.getId()));

        if ((animal.getImage() != null) && (animal.getImage().length() > LENGTH_IMAGE)) {
            return BAD_REQUEST;
        }

        //Update animal
        AnimalRepository animalRepository = new AnimalRepositoryImpl();
        try {
            animalRepository.update(animal);
        } catch (PersistenceException e) {
            LOG.error(e);
            return BAD_REQUEST;
        }

        //return relative image path to client
        String json;
        if (animal.getImage() == null) {
            json = "{\"filePath\":\"\"}";
        } else {
            json = "{\"filePath\":\"" + animal.getImage() + "\"}";
        }

       
        return Response.ok().entity(json).build();
    }
    
    @GET //http:localhost:8080/webapi/users/user/{userId}/animals/paginator    
    @Path("user/{userId}/animals/paginator/")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getUserAnimalsPaginator(@PathParam("userId") @DecimalMin(value = "1") long userId) {
        if(userId <= 0) {
            return BAD_REQUEST;
        }

        //get count of row according to filter        
        long pages = userRep.getAnimalByUserIdCount(userId);

        String json = "{\"rowsCount\" : " + String.valueOf(pages) + "}";

        return Response.ok().entity(json).build();
    }    
    

	@POST //http:localhost:8080/webapi/users/admin/users/pagenator
	@Path("admin/users/pagenator")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public Response getAmountListUsersForAdmin(UsersFilter usersFilter) {

		UserRepositoryImpl userRepository = new UserRepositoryImpl();
		long pages = userRepository.getAdminUsersPaginator(usersFilter);

		if (pages == 0)
			return NOT_FOUND;

		String str = "{\"rowsCount\" : " + String.valueOf(pages) + "}";

		return Response.status(Response.Status.OK).entity(str).build();
	}

	@POST //http:localhost:8080/webapi/admin/users
	@Path("admin/users")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public Response getAllUsersForAdmin(UsersFilter usersFilter) {
		if (usersFilter == null) {
			return BAD_REQUEST;
		}
		if ((usersFilter.getPage() == 0) || (usersFilter.getLimit() == 0)) {
			return BAD_REQUEST;
		}

		UserRepositoryImpl userRepository = new UserRepositoryImpl();
		//cast list of animals to generic list
		List<User> users = userRepository.getAdminUsers(usersFilter);
		GenericEntity<List<User>> genericUsers =
				new GenericEntity<List<User>>(users) {
				};

		if (genericUsers == null)
			return NOT_FOUND;

		return Response.ok().entity(genericUsers).build();
	}	
	

    private String saveNewImage(String rootFolder, String image, Long animalId) {
        if ((rootFolder == null) || (image == null) || (animalId == null)) {
            return image;
        }

        if (image.indexOf('\n') < 0) {
            return image;
        }

        String fileName = image.substring(0, image.indexOf('\n'));
        fileName = new SimpleDateFormat("yyyyMMddHHmmssS").format(new Date()) + fileName.substring(fileName.lastIndexOf('.'));
        image = image.substring(image.indexOf('\n') + 1);

        //get animal by id from database
        AnimalRepository animalRepository = new AnimalRepositoryImpl();
        Animal animal = animalRepository.getById(animalId);

        //delete old image
        File file = new File(rootFolder + animal.getImage());
        if (file.exists()) {
            file.delete();
        }

        byte[] decodedBytes = null;
        BASE64Decoder decoder = new BASE64Decoder();
        try {
            decodedBytes = decoder.decodeBuffer(image);
        } catch (IOException e) {
        	LOG.error(e);
            e.printStackTrace();
        }

        InputStream is = new ByteArrayInputStream(decodedBytes);

        //Load and save image
        OutputStream out = null;
        try {
            int read = 0;
            byte[] bytes = new byte[1024];

            out = new FileOutputStream(new File(rootFolder + IMAGE_FOLDER + fileName));
            while ((read = is.read(bytes)) != -1) {
                out.write(bytes, 0, read);
            }

            out.flush();
            out.close();
        } catch (IOException e) {
            LOG.error(e);
            return null;
        } finally {
            try {
                out.close();
            } catch (IOException ex) {
                LOG.error(ex);
                return null;
            }
        }

        return IMAGE_FOLDER + fileName;
    }

    private void saveNewBreed(AnimalBreed animalBreed, AnimalType animalType) {
        if ((animalBreed == null) || (animalType == null) || (animalType.getId() == null)) {
            return;
        }

        if ((animalBreed.getId() == null) &&
                ((animalBreed.getBreedUa() != null) || (animalBreed.getBreedEn() != null))) {
            animalBreed.setType(animalType);
            new AnimalBreedRepositoryImpl().insert_ua(animalBreed);
        }
    }
}
