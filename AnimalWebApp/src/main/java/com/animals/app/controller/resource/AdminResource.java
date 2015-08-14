package com.animals.app.controller.resource;

import com.animals.app.domain.Animal;
import com.animals.app.domain.AnimalsFilter;
import com.animals.app.repository.Impl.AnimalBreedRepositoryImpl;
import com.animals.app.repository.Impl.AnimalRepositoryImpl;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.glassfish.jersey.media.multipart.FormDataBodyPart;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.*;
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
        AnimalRepositoryImpl animalRepository = new AnimalRepositoryImpl();
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
    @Path("animals/paginator")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getAnimalsPaginator(AnimalsFilter animalsFilter) {
        if(animalsFilter == null) {
            return BAD_REQUEST;
        }

        //get count of row according to filter
        AnimalRepositoryImpl animalRepository = new AnimalRepositoryImpl();
        long pages = animalRepository.getAdminAnimalsPaginator(animalsFilter);

        if(pages == 0) {
            return NOT_FOUND;
        }

        String json = "{\"rowsCount\" : " + String.valueOf(pages) + "}";

        return ok(json);
    }

    /**
     * @param animalId id of animal
     * @return return an animal instance from data base
     */
    @GET //http:localhost:8080/webapi/animals/{animalId}
    @Path("animals/{animalId}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getAnimal(@PathParam("animalId") long animalId) {
        if (animalId == 0) {
            return BAD_REQUEST;
        }

        //get animal by id from data base
        AnimalRepositoryImpl animalRepository = new AnimalRepositoryImpl();
        Animal animal = animalRepository.getById(animalId);

        return ok(animal);
    }

    /**
     * Delete animal in data base
     * @param animalId id of animal
     * @return return response with status 200
     */
    @DELETE //http:localhost:8080/webapi/animals/{animalId}
    @Path("animals/{animalId}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response deleteAnimal(@PathParam("animalId") long animalId) {
        if (animalId == 0) {
            return BAD_REQUEST;
        }

        AnimalRepositoryImpl animalRepository = new AnimalRepositoryImpl();
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
    @Path("animals/editor")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateAnimal(Animal animal) {
        if(animal == null) {
            return BAD_REQUEST;
        }
        //check breed, if it new insert it into database
        if ((animal.getBreed() != null) && (animal.getBreed().getId() == null) && (animal.getBreed().getBreedUa() != null)) {
            animal.getBreed().setType(animal.getType());
            new AnimalBreedRepositoryImpl().insert_ua(animal.getBreed());
        }
        //Update animal
        AnimalRepositoryImpl animalRepository = new AnimalRepositoryImpl();
        animalRepository.update(animal);

        return ok();
    }

    /**
     * Update animal image
     * @param animalId id of animal
     * @param uploadedInputStream new image file
     * @param fileDetail file info
     * @return return relative path of new image
     */
    @POST //http:localhost:8080/webapi/animals/editor/upload/animalId
    @Path("animals/editor/upload/{animalId}")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response uploadImage(@PathParam("animalId") long animalId,
                                @FormDataParam("file") InputStream uploadedInputStream,
                                @FormDataParam("file") FormDataContentDisposition fileDetail) {
        if (animalId == 0) {
            return BAD_REQUEST;
        }

        //get animal by id from database
        AnimalRepositoryImpl animalRepository = new AnimalRepositoryImpl();
        Animal animal = animalRepository.getById(animalId);

        if (animal == null) {
            return NOT_FOUND;
        }

        String fileName = fileDetail.getFileName();                                             //file name
        String restPath = httpServlet.getServletContext().getRealPath("/");                     //path to rest root folder
        String httpPath = "images/" + animalId + fileName.substring(fileName.lastIndexOf('.')); //relative path to uploaded file

        //delete old image
        File file = new File(restPath + httpPath);
        if (file.exists()) {
            file.delete();
        }

        // save file to "/images/{animal id}.file extension"
        OutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(file);
            byte[] buffer = new byte[1024];
            int read;
            while ((read = uploadedInputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, read);
            }
        } catch (IOException ex) {
            LOG.error(ex);
            return SERVER_ERROR;
        } finally {
            try {
                outputStream.close();
            } catch (IOException ex) {
                LOG.error(ex);
            }
        }

        //save image path in data base
        animal.setImage(httpPath);
        animalRepository.update(animal);

        //return relative image path to client
        String json = "{\"filePath\":\"" + httpPath + "\"}";

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
