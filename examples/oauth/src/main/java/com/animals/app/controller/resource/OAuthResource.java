package com.animals.app.controller.resource;


import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import org.scribe.builder.ServiceBuilder;
import org.scribe.builder.api.FacebookApi;
import org.scribe.builder.api.GitHubApi;
import org.scribe.builder.api.Google2Api;
import org.scribe.builder.api.TwitterApi;
import org.scribe.model.OAuthRequest;
//import org.scribe.model.Response;
import org.scribe.model.Token;
import org.scribe.model.Verb;
import org.scribe.model.Verifier;
import org.scribe.oauth.OAuthService;

import com.animals.app.repository.UserRepositoryImp;

/**
 * Created by 41X on 31.07.2015.
 */

@Path("login")
public class OAuthResource {

	// Google
	private static final String PROTECTED_RESOURCE_URL = "https://www.googleapis.com/oauth2/v1/userinfo?alt=json";
	private static final String SCOPE = "https://mail.google.com/ https://www.googleapis.com/auth/userinfo.email";

	// Facebook
	private static final String PROTECTED_RESOURCE_URL_FB = "https://graph.facebook.com/me";
	private static final String PROTECTED_RESOURCE_URL_FB2 = "https://graph.facebook.com/me?fields=picture.type(small)";

	// Twitter
	private static final String PROTECTED_RESOURCE_URL_TW = "https://api.twitter.com/1.1/account/verify_credentials.json";

	// GitHubb
	private static final String PROTECTED_RESOURCE_URL_GT = "https://api.github.com/user";
	
	// url to redirect after OAuth end
	private String url = "http://localhost:8080/oauth/users.html";
	
	private static final Token EMPTY_TOKEN = null;

	private UserRepositoryImp userRep = new UserRepositoryImp();
	
	// Google keys
	String apiKeyG = "601704593845-8nbsaqf58sk03plpd6cqd5snskc2v6du.apps.googleusercontent.com";
	String apiSecretG = "1OwF1fo-F-Ky5SeBlmNLibhx";
	String callbackUrlG = "http://localhost:8080/oauth/webapi/login/google_token";

	@GET
	@Path("google")
	public Response googleLogin() {

		OAuthService service = null;

		try {
			service = new ServiceBuilder()
					.provider(Google2Api.class)
					.apiKey(apiKeyG)
					.apiSecret(apiSecretG)
					.callback(callbackUrlG)
					.scope(SCOPE)
					.offline(true)
					.build();

		} catch (Exception e) {
			e.printStackTrace();
		}

		if (service == null) {
			return Response.status(404).build();
		}

		String authorizationUrl = service.getAuthorizationUrl(EMPTY_TOKEN);

		System.out.println("url - " + authorizationUrl);

		return Response.temporaryRedirect(UriBuilder.fromUri(authorizationUrl).build()).build();
	}

	@GET
	@Path("google_token")
	public Response getGoogleAccessToken(@QueryParam("code") String token) {

		boolean refresh = true;
		boolean startOver = true;

		Verifier v = new Verifier(token);

		System.out.println("token - " + token);
		
		OAuthService service2 =null;

		try {
			service2 = new ServiceBuilder()
					.provider(Google2Api.class)
					.apiKey(apiKeyG)
					.apiSecret(apiSecretG)
					.callback(callbackUrlG)
					.scope(SCOPE)
					.offline(true)
					.build();
			
		} catch (Exception e1) {			
			e1.printStackTrace();
		}

		Token accessToken = new Token("ACCESS_TOKEN", "REFRESH_TOKEN");

		if (startOver) {

			// Trade the Request Token and Verfier for the Access Token
			System.out.println("Trading the Request Token for an Access Token...");
			accessToken = service2.getAccessToken(EMPTY_TOKEN, v);
			System.out.println("Got the Access Token!");
			System.out.println("(if your curious it looks like this: " + accessToken + " )");
			System.out.println();
		}

		if (refresh) {
			try {
				// Trade the Refresh Token for a new Access Token
				System.out.println("Trading the Refresh Token for a new Access Token...");
				Token newAccessToken = service2.getAccessToken(accessToken, v);
				System.out.println("Got the Access Token!");
				System.out.println("(if your curious it looks like this: " + newAccessToken + " )");
				System.out.println();
				accessToken = newAccessToken;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		System.out.println("Now we're going to access a protected resource...");
		OAuthRequest request = new OAuthRequest(Verb.GET, PROTECTED_RESOURCE_URL);
		service2.signRequest(accessToken, request);
		org.scribe.model.Response response = request.send();
		System.out.println("Got it! Lets see what we found...");
		System.out.println();
		System.out.println(response.getCode());
		System.out.println(response.getBody());

		System.out.println();
		System.out.println("Thats it man! Go and build something awesome with Scribe! :)");
		
		//save User
		userRep.saveUser(response.getBody());
		
		return Response.temporaryRedirect(UriBuilder.fromUri(url).build()).build();
		
	}

	@GET
	@Path("facebook")
	public Response facebookLogin() {

		OAuthService service = null;

		try {
			service = new ServiceBuilder()
					.provider(FacebookApi.class)
					.apiKey("713360185453475")
					.apiSecret("86b671029fee084da5565eb5721b018c")
					.callback("http://localhost:8080/oauth/webapi/login/facebook_token")
					.build();

		} catch (Exception e) {
			e.printStackTrace();
		}

		if (service == null) {
			return Response.status(404).build();
		}

		String authorizationUrl = service.getAuthorizationUrl(EMPTY_TOKEN);

		System.out.println("url - " + authorizationUrl);

		return Response.temporaryRedirect(UriBuilder.fromUri(authorizationUrl).build()).build();

	}

	@GET
	@Path("facebook_token")
	public Response getFBAccessToken(@QueryParam("code") String token) {

		Verifier v = new Verifier(token);

		System.out.println("token - " + token);
		
		OAuthService service2 = null;

		try {
			service2 = new ServiceBuilder()
					.provider(FacebookApi.class)
					.apiKey("713360185453475")
					.apiSecret("86b671029fee084da5565eb5721b018c")
					.callback("http://localhost:8080/oauth/webapi/login/facebook_token")
					.build();
			
		} catch (Exception e) {
			
			e.printStackTrace();
		}

		Token accessToken = service2.getAccessToken(EMPTY_TOKEN, v);

		System.out.println("accesstoken - " + accessToken);
		
		OAuthRequest request = new OAuthRequest(Verb.GET, PROTECTED_RESOURCE_URL_FB);
		OAuthRequest request2 = new OAuthRequest(Verb.GET, PROTECTED_RESOURCE_URL_FB2);

		service2.signRequest(accessToken, request);
		service2.signRequest(accessToken, request2);

		request.addHeader("GData-Version", "3.0");
		request2.addHeader("GData-Version", "3.0");

		org.scribe.model.Response response = request.send();
		org.scribe.model.Response response2 = request2.send();
		
		System.out.println(response.getCode());
		System.out.println(response.getBody());


		// save user
		userRep.saveUser(response.getBody());
				
		return Response.temporaryRedirect(UriBuilder.fromUri(url).build()).build();
		
		//return Response.status(200).entity(response.getBody() + response2.getBody()).build();

		// we really need redirect URI to page where will be info about
		// registered users
		// String targetURIForRedirection ="http://localhost:8080/oauth/users.html";
		// return Response.temporaryRedirect(UriBuilder.fromUri(targetURIForRedirection).build()).build();

	}

	@GET
	@Path("twitter")
	public Response twiterLogin() {

		OAuthService service = null;

		try {
			service = new ServiceBuilder()
					.provider(TwitterApi.class)
					.apiKey("C9Z3AJ4GVBYxa7exesFpZBZNn")
					.apiSecret("bYQldGWdEhWGVmnGlMnFQ7hYMFSHHGeWq3ANZyexgFZZ2JYPL6")
					.callback("http://localhost:8080/oauth/webapi/login/twitter_token")
					.build();
		} catch (Exception e) {			
			e.printStackTrace();
		}

		Token requestToken = service.getRequestToken();

		String authorizationUrl = service.getAuthorizationUrl(requestToken);

		System.out.println("url - " + authorizationUrl);

		return Response.temporaryRedirect(UriBuilder.fromUri(authorizationUrl).build()).build();

	}

	@GET
	@Path("twitter_token")
	public Response getTwitterAccessToken(@QueryParam("oauth_token") String oauth_token,
			@QueryParam("oauth_verifier") String oauth_verifier) {

		String token = oauth_token;
		String verifier = oauth_verifier;

		System.out.println(token);
		System.out.println(verifier);

		Verifier v = new Verifier(verifier);

		Token requestToken = new Token(token, verifier);

		OAuthService service2 = null;

		try {
			service2 = new ServiceBuilder()
					.provider(TwitterApi.class)
					.apiKey("C9Z3AJ4GVBYxa7exesFpZBZNn")
					.apiSecret("bYQldGWdEhWGVmnGlMnFQ7hYMFSHHGeWq3ANZyexgFZZ2JYPL6")
					.build();
		} catch (Exception e) {
			e.printStackTrace();
		}

		Token accessToken = service2.getAccessToken(requestToken, v);		

		OAuthRequest request = new OAuthRequest(Verb.GET, PROTECTED_RESOURCE_URL_TW);

		service2.signRequest(accessToken, request);

		org.scribe.model.Response response = request.send();

		System.out.println(response.getCode());
		System.out.println(response.getBody());
		
		userRep.saveUser(response.getBody());
		
		return Response.temporaryRedirect(UriBuilder.fromUri(url).build()).build();
//		return Response.status(200).entity(response.getBody()).build();
	}

	@GET
	@Path("github")
	public Response gitHubLogin() {

		OAuthService service = null;

		try {
			service = new ServiceBuilder()
					.provider(GitHubApi.class)
					.apiKey("f20d0aed6930fa765a00")
					.apiSecret("d8f848e3c6b491de2e02204345892de61180e43f")
					.callback("http://localhost:8080/oauth/webapi/login/github_token")
					.build();

		} catch (Exception e) {
			e.printStackTrace();
		}

		if (service == null) {
			return Response.status(404).build();
		}

		String authorizationUrl = service.getAuthorizationUrl(EMPTY_TOKEN);

		System.out.println("url - " + authorizationUrl);

		return Response.temporaryRedirect(UriBuilder.fromUri(authorizationUrl).build()).build();

	}

	@GET
	@Path("github_token")
	public Response getGitHubAccessToken(@QueryParam("code") String token) {

		Verifier v = new Verifier(token);

		OAuthService service2 = null;

		System.out.println("token - " + token);

		try {

			service2 = new ServiceBuilder()
					.provider(GitHubApi.class)
					.apiKey("f20d0aed6930fa765a00")
					.apiSecret("d8f848e3c6b491de2e02204345892de61180e43f")
					.callback("http://localhost:8080/oauth/webapi/login/github_token")
					.build();
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		Token accessToken = service2.getAccessToken(null, v);

		System.out.println("accesstoken - " + accessToken);

		OAuthRequest request = new OAuthRequest(Verb.GET, PROTECTED_RESOURCE_URL_GT);

		service2.signRequest(accessToken, request);

		org.scribe.model.Response response = request.send();
		
		System.out.println(response.getCode());
		System.out.println(response.getBody());
		
		userRep.saveUser(response.getBody());
		
		return Response.temporaryRedirect(UriBuilder.fromUri(url).build()).build();
//		return Response.status(200).entity(response.getBody()).build();

	}

}
