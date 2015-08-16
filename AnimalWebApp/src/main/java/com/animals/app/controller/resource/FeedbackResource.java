package com.animals.app.controller.resource;

import com.animals.app.service.MailSender;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;


/**
 * Created by aquaneo on 8/13/2015.
 */


@Path("contacts")
public class FeedbackResource {
    private static Logger LOG = LogManager.getLogger(FeedbackResource.class);

    public static final String RECAPTCHA_VERIF_URL = "https://www.google.com/recaptcha/api/siteverify";
    public static final String RECAPTCHA_SERVER_KEY_SECRET = "6LdeAQsTAAAAACdaimkyRdJNUbqGXM3tmxdJe1Ql";

        private final Response BAD_REQUEST = Response.status(Response.Status.BAD_REQUEST).build();

        private final Response NOT_FOUND = Response.status(Response.Status.NOT_FOUND).build();

        @POST
        @Path("mail")//http:localhost:8080/webapi/contacts/mail
        @Consumes(MediaType.APPLICATION_JSON)
        @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
        public Response sendFeedback (Feedback feedback) {
            String from, text, sender;

            from = feedback.getEmail();
            text = feedback.getText();
            sender = feedback.getSignup();

            if (from == null || from.isEmpty() || text == null || text.isEmpty() || sender == null || sender.isEmpty() ) return BAD_REQUEST;
            JsonObject response = validateCaptcha(RECAPTCHA_SERVER_KEY_SECRET, feedback.getgRecaptchaResponse());


            MailSender mail = new MailSender();
            mail.feedbackSend(from, text, sender);

            return ok(response.toString());

        }

    /**
     * Return response with code 200(OK) and build returned entity
     * @param entity Returned json instance from client
     * @return HTTP code K
     */
    private Response ok(Object entity) {
        return Response.ok().entity(entity).build();
    }

    private JsonObject validateCaptcha(String secret, String response)
    {
        JsonObject jsonObject = null;
        URLConnection connection = null;
        InputStream is = null;
        String charset = java.nio.charset.StandardCharsets.UTF_8.name();
        try {
            String query = String.format("secret=%s&response=%s",
                    URLEncoder.encode(secret, charset),
                    URLEncoder.encode(response, charset));

            connection = new URL(RECAPTCHA_VERIF_URL + "?" + query).openConnection();
            is = connection.getInputStream();
            JsonReader rdr = Json.createReader(is);
            jsonObject = rdr.readObject();

        } catch (IOException ex) {

        }
        finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                }

            }
        }
        return jsonObject;
    }
}
