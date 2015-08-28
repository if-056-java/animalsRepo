package com.animals.app.controller.resource;

import com.animals.app.domain.Animal;
import com.animals.app.domain.AnimalsFilter;
import com.animals.app.repository.AnimalRepository;
import com.animals.app.repository.Impl.AnimalBreedRepositoryImpl;
import com.animals.app.repository.Impl.AnimalRepositoryImpl;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import sun.misc.BASE64Decoder;

import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Path("admin")
public class AdminResource {
    private static Logger LOG = LogManager.getLogger(AdminResource.class);

    //return response with 400 code
    private final Response BAD_REQUEST = Response.status(Response.Status.BAD_REQUEST).build();

    //return response with 404 code
    private final Response NOT_FOUND = Response.status(Response.Status.NOT_FOUND).build();

    //return response with 500 code
    private final Response SERVER_ERROR = Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();

    @Context
    private HttpServletRequest httpServlet;

    /**
     * @param animalsFilter instance used for lookup.
     * @return list of animals.
     */
    @POST //http:localhost:8080/webapi/admin/animals
    @RolesAllowed({"модератор", "лікар"})
    @Path("animals")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getAnimals(AnimalsFilter animalsFilter) {
        if(animalsFilter == null) {
            return BAD_REQUEST;
        }
        if ((animalsFilter.getPage() == 0) || (animalsFilter.getLimit() == 0)) {
            return BAD_REQUEST;
        }

        //get list of animals from data base
        AnimalRepository animalRepository = new AnimalRepositoryImpl();
        List<Animal> animals = animalRepository.getAdminAnimals(animalsFilter);

        //cast list of animals to generic list
        GenericEntity<List<Animal>> genericAnimals = new GenericEntity<List<Animal>>(animals) {};

        if(genericAnimals == null) {
            return NOT_FOUND;
        }

        return ok(genericAnimals);
    }

    /**
     * @param animalsFilter instance used for lookup.
     * @return count of rows for pagination.
     */
    @POST //http:localhost:8080/webapi/animals/paginator
    @RolesAllowed({"модератор", "лікар"})
    @Path("animals/paginator")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getAnimalsPaginator(AnimalsFilter animalsFilter) {
        if(animalsFilter == null) {
            return BAD_REQUEST;
        }

        //get count of row according to filter
        AnimalRepository animalRepository = new AnimalRepositoryImpl();
        long pages = animalRepository.getAdminAnimalsPaginator(animalsFilter);

        String json = "{\"rowsCount\" : " + String.valueOf(pages) + "}";

        return ok(json);
    }

    /**
     * @param animalId id of animal
     * @return return an animal instance from data base
     */
    @GET //http:localhost:8080/webapi/animals/{animalId}
    @RolesAllowed({"модератор", "лікар"})
    @Path("animals/{animalId}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getAnimal(@PathParam("animalId") long animalId) {
        if (animalId == 0) {
            return BAD_REQUEST;
        }

        //get animal by id from data base
        AnimalRepository animalRepository = new AnimalRepositoryImpl();
        Animal animal = animalRepository.getById(animalId);

        return ok(animal);
    }

    /**
     * Delete animal in data base
     * @param animalId id of animal
     * @return return response with status 200
     */
    @DELETE //http:localhost:8080/webapi/animals/{animalId}
    @RolesAllowed("модератор")
    @Path("animals/{animalId}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response deleteAnimal(@PathParam("animalId") long animalId) {
        if (animalId == 0) {
            return BAD_REQUEST;
        }

        AnimalRepository animalRepository = new AnimalRepositoryImpl();
        String restPath = httpServlet.getServletContext().getRealPath("/"); //path to rest root folder
        Animal animal = animalRepository.getById(animalId);

        //delete image
        if (animal.getImage() != null) {
            File file = new File(restPath + animal.getImage());
            if (file.exists()) {
                file.delete();
            }
        }

        //delete animal in data base by id
        animalRepository.delete(animalId);

        return ok();
    }

    /**
     * Update animal info in data base
     * @param animal instance to be updated
     * @return return response with status 200
     */
    @POST //http:localhost:8080/webapi/animals/editor
    @RolesAllowed("модератор")
    @Path("animals/editor")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateAnimal(Animal animal) {
        String imageFolder = "images/";

        if(animal == null) {
            return BAD_REQUEST;
        }

        //check breed, if it new insert it into database
        if ((animal.getBreed() != null) && (animal.getBreed().getId() == null) && (animal.getBreed().getBreedUa() != null)) {
            animal.getBreed().setType(animal.getType());
            new AnimalBreedRepositoryImpl().insert_ua(animal.getBreed());
        }

        if (animal.getImage().length() > (imageFolder.length() + 21)) {
            String fileStr = animal.getImage();
            String fileName = fileStr.substring(0, fileStr.indexOf('\n'));
            fileStr = fileStr.substring(fileStr.indexOf('\n') + 1);
            fileName = new SimpleDateFormat("yyyyMMddHHmmssS").format(new Date()) + fileName.substring(fileName.lastIndexOf('.'));
            String restPath = httpServlet.getServletContext().getRealPath("/");         //path to rest root folder
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

        return ok(json);
    }

    /**
     * Return response with code 200(OK) and build returned entity
     * @return HTTP code K
     */
    private Response ok() {
        return Response.ok().build();
    }

    /**
     * Return response with code 200(OK) and build returned entity
     * @param entity Returned json instance from client
     * @return HTTP code K
     */
    private Response ok(Object entity) {
        return Response.ok().entity(entity).build();
    }
}
