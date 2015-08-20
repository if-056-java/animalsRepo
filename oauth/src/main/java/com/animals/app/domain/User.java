package com.animals.app.domain;

import java.io.Serializable;

@SuppressWarnings("serial")
public class User implements Serializable {

	private String id;
	private String name;
	private String email;
	
	private FacebookProfile facebookProfile;
	private GoogleProfile googleProfile;
	private GitHubProfile gitHubProfile;
	private TwitterProfile twitterProfile;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public FacebookProfile getFacebookProfile() {
		return facebookProfile;
	}
	public void setFacebookProfile(FacebookProfile facebookProfile) {
		this.facebookProfile = facebookProfile;
	}
	public GoogleProfile getGoogleProfile() {
		return googleProfile;
	}
	public void setGoogleProfile(GoogleProfile googleProfile) {
		this.googleProfile = googleProfile;
	}
	public GitHubProfile getGitHubProfile() {
		return gitHubProfile;
	}
	public void setGitHubProfile(GitHubProfile gitHubProfile) {
		this.gitHubProfile = gitHubProfile;
	}
	public TwitterProfile getTwitterProfile() {
		return twitterProfile;
	}
	public void setTwitterProfile(TwitterProfile twitterProfile) {
		this.twitterProfile = twitterProfile;
	}
	
	
	
	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (id != user.id) return false;
        if (name != null ? !name.equals(user.name) : user.name != null) return false; 
        if (email != null ? !email.equals(user.email) : user.email != null) return false;  

        return true;
    }
	
	
	@Override
	public String toString() {
		 return "User{" +
				 "id=" + id +
				 ", name='" + name + '\'' +
				 ", email='" + email + '\'' +	                
				 '}';
	}
	
}
