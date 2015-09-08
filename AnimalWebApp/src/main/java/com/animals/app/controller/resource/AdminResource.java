package com.animals.app.controller.resource;

import com.animals.app.domain.Animal;
import com.animals.app.domain.AnimalBreed;
import com.animals.app.domain.AnimalType;
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

    private final String IMAGE_FOLDER = "images/"; //folder for animals images
    //size of fields in data base, table: animals
    private final int LENGTH_TRANSPNUMBER = 15;
    private final int LENGTH_TOKENNUMBER = 12;
    private final int LENGTH_COLOR = 20;
    private final int LENGTH_DESCRIPTION = 100;
    private final int LENGTH_ADDRESS = 120;
    private final int LENGTH_IMAGE = 50;

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

        if ((animalsFilter.getPage() <= 0) || (animalsFilter.getLimit() <= 0)) {
            return BAD_REQUEST;
        }

        //get list of animals from data base
        AnimalRepository animalRepository = new AnimalRepositoryImpl();
        List<Animal> animals = animalRepository.getAdminAnimals(animalsFilter);

        //cast list of animals to generic list
        GenericEntity<List<Animal>> genericAnimals = new GenericEntity<List<Animal>>(animals) {};

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
        if (animalId <= 0) {
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
    public Response deleteAnimal(@Context HttpServletRequest httpServlet, @PathParam("animalId") long animalId) {
        if (animalId <= 0) {
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
    public Response updateAnimal(@Context HttpServletRequest httpServlet, Animal animal) {
        if(!validateAnimal(animal)) {
            return BAD_REQUEST;
        }

        //check breed, if it new insert it into database
        saveNewBreed(animal.getBreed(), animal.getType());

        //save new image if set
        animal.setImage(saveNewImage(httpServlet.getServletContext().getRealPath("/"),
                animal.getImage(), animal.getId()));

        if ((animal.getImage() != null) && (animal.getImage().length() > LENGTH_IMAGE)) {
            return BAD_REQUEST;
        }

        //Update animal
        AnimalRepository animalRepository = new AnimalRepositoryImpl();
        animalRepository.update(animal);

        //return relative image path to client
        String json;
        if (animal.getImage() == null) {
            json = "{\"filePath\":\"\"}";
        } else {
            json = "{\"filePath\":\"" + animal.getImage() + "\"}";
        }

        return ok(json);
    }

    /**
     * Delete animal in data base
     * @param animalId id of animal
     * @return return response with status 200 if ok
     */
    @DELETE //http:localhost:8080/webapi/animals/image/{animalId}
    @RolesAllowed("модератор")
    @Path("animals/image/{animalId}")
    public Response deleteAnimalImage(@Context HttpServletRequest httpServlet, @PathParam("animalId") long animalId) {
        if (animalId <= 0) {
            return BAD_REQUEST;
        }

        AnimalRepository animalRepository = new AnimalRepositoryImpl();
        Animal animal = animalRepository.getById(animalId);

        if ((animal == null) || (animal.getImage() == null)) {
            return ok();
        }

        String restPath = httpServlet.getServletContext().getRealPath("/");         //path to rest root folder
        //delete image
        File file = new File(restPath + IMAGE_FOLDER + animal.getImage());
        if (file.exists()) {
            file.delete();
        }

        animal.setImage("");

        animalRepository.update(animal);

        return ok();
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

    private Boolean validateAnimal(Animal animal) {
        if (animal == null) {
            return false;
        }

        if ((animal.getId() == null) || (animal.getId() <= 0)) {
            return false;
        }

        if (animal.getSex() == null) {
            return false;
        }

        if ((animal.getType() == null) || (animal.getType().getId() == null) || (animal.getType().getId() <= 0)) {
            return false;
        }

        if (animal.getSize() == null) {
            return false;
        }

        if (animal.getBreed() != null) {
            if (animal.getBreed().getId() == null) {
                if ((animal.getBreed().getBreedUa() == null) && (animal.getBreed().getBreedEn() == null)) {
                    return false;
                }
            } else if (animal.getBreed().getId() <= 0) {
                return false;
            }
        }

        if ((animal.getTranspNumber() != null) && (animal.getTranspNumber().length() > LENGTH_TRANSPNUMBER)) {
            return false;
        }

        if ((animal.getTokenNumber() != null) && (animal.getTokenNumber().length() > LENGTH_TOKENNUMBER)) {
            return false;
        }

        if (animal.getDateOfRegister() == null) {
            return false;
        }

        if ((animal.getColor() == null) || (animal.getColor().length() > LENGTH_COLOR)) {
            return false;
        }

        if ((animal.getDescription() != null) && (animal.getDescription().length() > LENGTH_DESCRIPTION)) {
            return false;
        }

        if ((animal.getUser() != null) && (animal.getUser().getId() == null)) {
            return false;
        }

        if ((animal.getAddress() == null) || (animal.getAddress().length() > LENGTH_ADDRESS)) {
            return false;
        }

        if ((animal.getService() == null) || (animal.getService().getId() == null) || (animal.getService().getId() <= 0)) {
            return false;
        }

        return true;
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
