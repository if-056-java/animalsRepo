package com.animals.app.controller.resource;

import org.apache.oltu.oauth2.client.OAuthClient;
import org.apache.oltu.oauth2.client.URLConnectionClient;
import org.apache.oltu.oauth2.client.request.OAuthBearerClientRequest;
import org.apache.oltu.oauth2.client.request.OAuthClientRequest;
import org.apache.oltu.oauth2.client.response.OAuthJSONAccessTokenResponse;
import org.apache.oltu.oauth2.client.response.OAuthResourceResponse;
import org.apache.oltu.oauth2.common.OAuth;
import org.apache.oltu.oauth2.common.OAuthProviderType;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.types.GrantType;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

/**
 * Created by Rostyslav.Viner on 29.07.2015.
 */
@Path("login")
public class AuthorizationResource {
    private static final String PROTECTED_RESOURCE_URL = "https://www.googleapis.com/oauth2/v1/userinfo?alt=json";
    String apiKey = "878783207064-mcj0npde3n5dcc6oks0q49re50faaoe9.apps.googleusercontent.com";
    String apiSecret = "pcTPf-OFsrvLB72GiBwhaZR_";
    String callbackUrl = "http://localhost:8080/AnimalWebApp/webapi/login/google_token";

    /**
     * Main method that executes OAuth2 Flow for Google
     * @return redirect to Google Authorization page
     */
    @GET
    @Path("google")
    public Response googleLogin() {
        OAuthClientRequest request = null;

        try {
            request = OAuthClientRequest
                    .authorizationProvider(OAuthProviderType.GOOGLE)
                    .setClientId(apiKey)
                    .setRedirectURI(callbackUrl)
                    .setResponseType("code")
                    .setScope("email profile")
                    .buildQueryMessage();
        }catch (OAuthSystemException e) {
            e.printStackTrace();
        }

        if (request == null) {
            return Response.status(404).build();
        }

        return Response.seeOther(UriBuilder.fromUri(request.getLocationUri()).build()).build();
    }

    /**
     * Obtain an access token and user info from the Google Authorization Server.
     * @param token - access token
     */
    @GET
    @Path("google_token")
    public Response getAccessToken(@QueryParam("code") String token) {
        OAuthClientRequest request = null;
        OAuthResourceResponse resourceResponse = null;

        try {
            request = OAuthClientRequest
                    .tokenProvider(OAuthProviderType.GOOGLE)
                    .setGrantType(GrantType.AUTHORIZATION_CODE)
                    .setClientId(apiKey)
                    .setClientSecret(apiSecret)
                    .setRedirectURI(callbackUrl)
                    .setCode(token)
                    .buildBodyMessage();

            OAuthClient oAuthClient = new OAuthClient(new URLConnectionClient());
            OAuthJSONAccessTokenResponse response = oAuthClient.accessToken(request);

            // Use the access token to retrieve the data.
            OAuthClientRequest bearerClientRequest = new OAuthBearerClientRequest(AuthorizationResource.PROTECTED_RESOURCE_URL)
                    .setAccessToken(response.getAccessToken())
                    .buildQueryMessage();

            resourceResponse = oAuthClient.resource(bearerClientRequest, OAuth.HttpMethod.GET, OAuthResourceResponse.class);

            if (resourceResponse.getResponseCode()!=200){
                return Response.status(401).build();
            }
        }catch (OAuthSystemException | OAuthProblemException e) {
            e.printStackTrace();
        }
        

        return Response.status(200).entity(resourceResponse.getBody()).build();
    }
}
