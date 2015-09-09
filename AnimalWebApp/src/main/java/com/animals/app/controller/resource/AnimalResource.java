package com.animals.app.controller.resource;

import com.animals.app.domain.*;
import com.animals.app.repository.Impl.*;

import javax.annotation.security.PermitAll;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;

import com.animals.app.service.CreateAnimalImage;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("animals")
@PermitAll
public class AnimalResource {
    private static Logger LOG = LogManager.getLogger(AnimalResource.class);

    private final Response BAD_REQUEST  = Response.status(Response.Status.BAD_REQUEST).build();
    private final Response NOT_FOUND    = Response.status(Response.Status.NOT_FOUND).build();
    private final Response SERVER_ERROR = Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();

    //path to target folder
    @Context
    private HttpServletRequest httpServlet;

    //animal repository instance
    private AnimalRepositoryImpl animalRepository = new AnimalRepositoryImpl();

    @POST
    @Path("animal")//http:localhost:8080/AnimalWebApp/webapi/animals
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response insertAnimal(Animal animal) {
        if (animal == null)
            return BAD_REQUEST;

        animal.setImage(getImageURL(animal.getImage()));

        insertBreed(animal);

        animalRepository.insert(animal);
        return ok(animal);
    }

    @POST
    @Path("adoption/pagenator")//http:localhost:8080/webapi/adoption/pagenator
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getAmountListForAdopting(AnimalsFilter animalsFilter) {
        long pages = animalRepository.getAmountListForAdopting(animalsFilter);

        if (pages == 0)
            return Response.status(Response.Status.NOT_FOUND).build();

        return Response.status(Response.Status.OK).entity(generateRowsCount(pages)).build();
    }


    @POST
    @Path("adoption")//http:localhost:8080/webapi/animals/adoption
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getAllAnimalsForAdopting(AnimalsFilter animalsFilter) {
        if (!animalsFilter.isAnimalFilterNotEmpty())
            return BAD_REQUEST;

        List<Animal> animals = animalRepository.getAllForAdopting(animalsFilter);

        //cast list of animals to generic list
        GenericEntity<List<Animal>> genericAnimals =
                new GenericEntity<List<Animal>>(animals) {
                };

        if (genericAnimals == null)
            return NOT_FOUND;

        return ok(genericAnimals);
    }

    @POST
    @Path("found/pagenator")//http:localhost:8080/webapi/found/pagenator
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getAmountListFoundAnimals(AnimalsFilter animalsFilter) {
        long pages = animalRepository.getAmountListFoundAnimals(animalsFilter);

        if (pages == 0)
            return NOT_FOUND;

        return Response.status(Response.Status.OK).entity(generateRowsCount(pages)).build();
    }

    @POST
    @Path("found")//http:localhost:8080/webapi/animals/found
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getAllFoundAnimals(AnimalsFilter animalsFilter) {
        if (!animalsFilter.isAnimalFilterNotEmpty())
            return BAD_REQUEST;

        List<Animal> animals = animalRepository.getAllFoundAnimals(animalsFilter);

        //cast list of animals to generic list
        GenericEntity<List<Animal>> genericAnimals =
                new GenericEntity<List<Animal>>(animals) {
                };

        if (genericAnimals == null)
            return NOT_FOUND;

        return ok(genericAnimals);
    }

    @POST
    @Path("lost/pagenator")//http:localhost:8080/webapi/lost/pagenator
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getAmountLostAnimals(AnimalsFilter animalsFilter) {
        long pages = animalRepository.getAmountListLostAnimals(animalsFilter);

        if (pages == 0)
            return NOT_FOUND;

        return Response.status(Response.Status.OK).entity(generateRowsCount(pages)).build();
    }

    @POST
    @Path("lost")//http:localhost:8080/webapi/animals/lost
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getAllLostAnimals(AnimalsFilter animalsFilter) {
        if (!animalsFilter.isAnimalFilterNotEmpty())
            return BAD_REQUEST;

        List<Animal> animals = animalRepository.getAllLostAnimals(animalsFilter);

        //cast list of animals to generic list
        GenericEntity<List<Animal>> genericAnimals =
                new GenericEntity<List<Animal>>(animals) {
                };

        if (genericAnimals == null)
            return NOT_FOUND;

        return ok(genericAnimals);
    }

    @GET
    @Path("service/{animalId}")//http:localhost:8080/webapi/animals/service/id
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getShortInfo(@PathParam("animalId") String id) {

        if (id == null)
            return BAD_REQUEST;

        Animal animalShortInfo = animalRepository.getShortInfoById(Integer.parseInt(id));

        if (animalShortInfo == null)
            return NOT_FOUND;

        return ok(animalShortInfo);
    }

    @GET
    @Path("animal_types")//http:localhost:8080/webapi/animals/animal_types
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getAnimalTypes() {
        List<AnimalType> animalTypes = new AnimalTypeRepositoryImpl().getAll();

        GenericEntity<List<AnimalType>> genericAnimalTypes =
                new GenericEntity<List<AnimalType>>(animalTypes) {};

        if (genericAnimalTypes == null)
            return NOT_FOUND;

        return ok(genericAnimalTypes);
    }

    @GET
    @Path("animal_services")//http:localhost:8080/webapi/animals/animal_services
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getAnimalServices() {
        List<AnimalService> animalServices = new AnimalServiceRepositoryImpl().getAll();

        GenericEntity<List<AnimalService>> genericAnimalServices =
                new GenericEntity<List<AnimalService>>(animalServices) {};

        if (genericAnimalServices == null)
            return NOT_FOUND;

        return ok(genericAnimalServices);
    }

    @GET
    @Path("animal_breeds/{id}")//http:localhost:8080/webapi/animals/animal_breeds
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

    @GET
    @Path("medical_history/types")//http:localhost:8080/webapi/animals/mdedical_history/types
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getAnimalMedicalHistoryTypes() {
        List<AnimalStatus> animalStatuses = new AnimalStatusRepositoryImpl().getAll();

        GenericEntity<List<AnimalStatus>> genericAnimalStatuses =
                new GenericEntity<List<AnimalStatus>>(animalStatuses) {};

        if (genericAnimalStatuses == null) {
            return NOT_FOUND;
        }

        return ok(genericAnimalStatuses);
    }

    /**
     * Keep image URL for inserting into database
     * @param image Image of bytes
     * @return Image URL
     */
    private String getImageURL(String image){
        return "images/" + CreateAnimalImage.createAnimalImage(image, httpServlet.getServletContext().getRealPath("/") + "images/");
    }

    /**
     * Check if breed is exist in database, if not, insert new breed
     * @param animal Instance of animal class
     * @return Actual instance of animal class
     */
    private Animal insertBreed(Animal animal){
        if(animal.checkNewBreed(animal.getBreed())) {
            animal.getBreed().setType(animal.getType());
            new AnimalBreedRepositoryImpl().insert_ua(animal.getBreed());
        }
        return animal;
    }

    /**
     * Generate JSON that contain amount rows some instance from database
     * @param pages Amount records into database
     * @return JSON object of amount rows
     */
    private String generateRowsCount(long pages){
        return "{\"rowsCount\" : " + String.valueOf(pages) + "}";
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