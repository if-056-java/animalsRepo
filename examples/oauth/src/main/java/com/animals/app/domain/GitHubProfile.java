package com.animals.app.domain;

import java.io.Serializable;

public class GitHubProfile implements Serializable {
	
	private long gitHubId;
	private String name;
	private String email;
	private String linkPhoto;
		
	public long getGitHubId() {
		return gitHubId;
	}
	public void setGitHubId(long gitHubId) {
		this.gitHubId = gitHubId;
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
	public String getLinkPhoto() {
		return linkPhoto;
	}
	public void setLinkPhoto(String linkPhoto) {
		this.linkPhoto = linkPhoto;
	}
	
	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GitHubProfile ghuser = (GitHubProfile) o;

        if (gitHubId != ghuser.gitHubId) return false;       
        if (name != null ? !name.equals(ghuser.name) : ghuser.name != null) return false; 
        if (email != null ? !email.equals(ghuser.email) : ghuser.email != null) return false; 
        if (linkPhoto != null ? !linkPhoto.equals(ghuser.linkPhoto) : ghuser.linkPhoto != null) return false; 

        return true;
    }
	
	
	@Override
	public String toString() {
		 return "User{" +
				 "gitHubId=" + gitHubId +
				 ", name='" + name + '\'' +
				 ", email='" + email + '\'' +
				 ", linkPhoto='" + linkPhoto + '\'' +				 
				 '}';
	}	

}
