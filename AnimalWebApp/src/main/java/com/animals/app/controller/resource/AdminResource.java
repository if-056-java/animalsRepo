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

    @POST //http:localhost:8080/webapi/admin/animals
    @Path("animals")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getAnimals(AnimalsFilter animalsFilter) {
        if(animalsFilter == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        if ((animalsFilter.getPage() == 0) || (animalsFilter.getLimit() == 0)) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        AnimalRepositoryImpl animalRepository = new AnimalRepositoryImpl();
        List<Animal> animals = animalRepository.getAdminAnimals(animalsFilter);

        //cast list of animals to generic list
        GenericEntity<List<Animal>> genericAnimals = new GenericEntity<List<Animal>>(animals) {};

        if(genericAnimals == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.status(Response.Status.OK).entity(genericAnimals).build();
    }

    @POST //http:localhost:8080/webapi/animals/paginator
    @Path("animals/paginator")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getAnimalsPaginator(AnimalsFilter animalsFilter) {
        if(animalsFilter == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        AnimalRepositoryImpl animalRepository = new AnimalRepositoryImpl();
        long pages = animalRepository.getAdminAnimalsPaginator(animalsFilter);

        if(pages == 0) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        String str = "{\"rowsCount\" : " + String.valueOf(pages) + "}";

        return Response.status(Response.Status.OK).entity(str).build();
    }

    @GET //http:localhost:8080/webapi/animals/{animalId}
    @Path("animals/{animalId}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getAnimal(@PathParam("animalId") long animalId) {
        if (animalId == 0) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        AnimalRepositoryImpl animalRepository = new AnimalRepositoryImpl();
        Animal animal = animalRepository.getById(animalId);

        return Response.status(Response.Status.OK).entity(animal).build();
    }

    @DELETE //http:localhost:8080/webapi/animals/{animalId}
    @Path("animals/{animalId}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response deleteAnimal(@PathParam("animalId") long animalId) {
        if (animalId == 0) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        AnimalRepositoryImpl animalRepository = new AnimalRepositoryImpl();
        animalRepository.delete(animalId);

        return Response.status(Response.Status.OK).build();
    }

    @POST //http:localhost:8080/webapi/animals/editor
    @Path("animals/editor")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateAnimal(Animal animal) {
        if(animal == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        //Check breed, if it new insert it into database
        if ((animal.getBreed().getId()) == null && (animal.getBreed().getBreedUa() != null)) {
            animal.getBreed().setType(animal.getType());
            new AnimalBreedRepositoryImpl().insert_ua(animal.getBreed());
        }
        //Update animal
        AnimalRepositoryImpl animalRepository = new AnimalRepositoryImpl();
        animalRepository.update(animal);

        return Response.status(Response.Status.OK).build();
    }

    @POST //http:localhost:8080/webapi/animals/editor/upload
    @Path("animals/editor/upload")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response uploadImage(@Context HttpServletRequest httpServlet, @FormDataParam("file") InputStream uploadedInputStream, @FormDataParam("file") FormDataContentDisposition fileDetail) {
        String httpPath = "images/" + fileDetail.getFileName();
        String filePath = httpServlet.getServletContext().getRealPath("/") + httpPath;
        // save the file to the server
        OutputStream os = null;
        try {
            File fileToUpload = new File(filePath);
            os = new FileOutputStream(fileToUpload);
            byte[] b = new byte[1024];
            int length;
            while ((length = uploadedInputStream.read(b)) != -1) {
                os.write(b, 0, length);
            }
        } catch (IOException ex) {
            LOG.error(ex);
        } finally {
            try {
                os.close();
            } catch (IOException ex) {
                LOG.error(ex);
            }
        }

        String json = "{\"filePath\":\"" + httpPath + "\"}";

        return Response.status(200).entity(json).build();

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
