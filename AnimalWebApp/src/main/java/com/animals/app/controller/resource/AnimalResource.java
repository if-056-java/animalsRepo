package com.animals.app.controller.resource;

import com.animals.app.domain.*;
import com.animals.app.repository.Impl.AnimalRepositoryImpl;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;

import com.animals.app.repository.Impl.AnimalBreedRepositoryImpl;
import com.animals.app.repository.Impl.AnimalServiceRepositoryImpl;
import com.animals.app.repository.Impl.AnimalTypeRepositoryImpl;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.*;
import java.util.List;

@Path("animals")
public class AnimalResource {

    //logger
    private static Logger LOG = LogManager.getLogger(AnimalResource.class);

    //return response with 400 code
    private final Response BAD_REQUEST = Response.status(Response.Status.BAD_REQUEST).build();

    //return response with 404 code
    private final Response NOT_FOUND = Response.status(Response.Status.NOT_FOUND).build();

    //return response with 500 code
    private final Response SERVER_ERROR = Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();


    AnimalRepositoryImpl animalRepository = new AnimalRepositoryImpl();

    @Context
    private HttpServletRequest httpServlet;

    @POST
    @Path("animal")//http:localhost:8080/AnimalWebApp/webapi/animals/animal
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response insertAnimal(Animal animal) {
        if (animal == null)
            return BAD_REQUEST;

        animalRepository.insert(animal);

        return ok(animal);
    }

    @POST //http:localhost:8080/webapi/adoption/pagenator
    @Path("adoption/pagenator")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getAmountListForAdopting(AnimalsFilter animalsFilter) {

        AnimalRepositoryImpl animalRepository = new AnimalRepositoryImpl();
        long pages = animalRepository.getAmountListForAdopting(animalsFilter);

        if (pages == 0)
            return Response.status(Response.Status.NOT_FOUND).build();

        String str = "{\"rowsCount\" : " + String.valueOf(pages) + "}";

        return Response.status(Response.Status.OK).entity(str).build();
    }

    @POST //http:localhost:8080/webapi/animals/adoption
    @Path("adoption")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getAllAnimalsForAdopting(AnimalsFilter animalsFilter) {
        if (animalsFilter == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        if ((animalsFilter.getPage() == 0) || (animalsFilter.getLimit() == 0)) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        AnimalRepositoryImpl animalRepository = new AnimalRepositoryImpl();
        //cast list of animals to generic list
        List<Animal> animals = animalRepository.getAllForAdopting(animalsFilter);
        GenericEntity<List<Animal>> genericAnimals =
                new GenericEntity<List<Animal>>(animals) {
                };

        if (genericAnimals == null)
            return NOT_FOUND;

        return ok(genericAnimals);
    }

    /**
     * Insert animal image
     * @param uploadedInputStream new image file
     * @param fileDetail          file info
     * @return return relative path of new image
     */
    @POST //http:localhost:8080/webapi/animals/animal/image
    @Path("animal/image")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response uploadImage(@FormDataParam("file") InputStream uploadedInputStream,
                                @FormDataParam("file") FormDataContentDisposition fileDetail) {

        String fileName = (System.currentTimeMillis()) + ("_") + fileDetail.getFileName();
        String uploadedFileLocation = "D:/AGIT/animalsRepo/AnimalWebApp/src/main/webapp/images/" + fileName;

        //Load and save image
        OutputStream out = null;
        try {
            int read = 0;
            byte[] bytes = new byte[1024];

            out = new FileOutputStream(new File(uploadedFileLocation));
            while ((read = uploadedInputStream.read(bytes)) != -1) {
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

        //return relative image path to client
        String json = "{\"filePath\":\"" + ("images/") + fileName + "\"}";

        return ok(json);
    }

    @GET //http:localhost:8080/webapi/animals/{adoption|found|lost}/id
    @Path("{parameter: adoption|found|lost}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getShortInfo(@PathParam("animalId") String id) {

        if (id == null)
            return BAD_REQUEST;

        int idAnimal = (int) Integer.parseInt(id);

        Animal animalShortInfo = animalRepository.getShortInfoById(idAnimal);


        if (animalShortInfo == null)
            return NOT_FOUND;

        return ok(animalShortInfo);
    }

    @GET //http:localhost:8080/webapi/animals/animal_types
    @Path("animal_types")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getAnimalTypes() {
        List<AnimalType> animalTypes = new AnimalTypeRepositoryImpl().getAll();

        GenericEntity<List<AnimalType>> genericAnimalTypes =
                new GenericEntity<List<AnimalType>>(animalTypes) {};

        if (genericAnimalTypes == null) {
            return NOT_FOUND;
        }

        return ok(genericAnimalTypes);
    }

    @GET //http:localhost:8080/webapi/animals/animal_services
    @Path("animal_services")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getAnimalServices() {
        List<AnimalService> animalServices = new AnimalServiceRepositoryImpl().getAll();

        GenericEntity<List<AnimalService>> genericAnimalServices =
                new GenericEntity<List<AnimalService>>(animalServices) {};

        if (genericAnimalServices == null) {
            return NOT_FOUND;
        }

        return ok(genericAnimalServices);
    }

    @GET //http:localhost:8080/webapi/animals/animal_breeds
    @Path("animal_breeds/{id}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getAnimalBreadsByAnimalTypeId(@PathParam("id") int animalTypeId) {
        if (animalTypeId == 0) {
            return BAD_REQUEST;
        }

        List<AnimalBreed> animalBreeds = new AnimalBreedRepositoryImpl().getByTypeId(animalTypeId);

        GenericEntity<List<AnimalBreed>> genericAnimalBreeds =
                new GenericEntity<List<AnimalBreed>>(animalBreeds) {
                };

        if (genericAnimalBreeds == null) {
            return NOT_FOUND;
        }

        return ok(genericAnimalBreeds);
    }

    /**
     * Return response with code 200(OK) and build returned entity
     *
     * @param entity Returned json instance from client
     * @return HTTP code K
     */
    private Response ok(Object entity) {
        return Response.ok().entity(entity).build();
    }

}
