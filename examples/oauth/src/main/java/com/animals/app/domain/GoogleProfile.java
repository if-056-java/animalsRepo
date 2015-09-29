package com.animals.app.domain;

import java.io.Serializable;

@SuppressWarnings("serial")
public class GoogleProfile implements Serializable {
	
	private String googleId;
	private String name;
	private String email;
	private String linkPhoto;
	
	
	
	public String getGoogleId() {
		return googleId;
	}
	public void setGoogleId(String googleId) {
		this.googleId = googleId;
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

        GoogleProfile guser = (GoogleProfile) o;

        if (googleId != guser.googleId) return false;
        if (name != null ? !name.equals(guser.name) : guser.name != null) return false; 
        if (email != null ? !email.equals(guser.email) : guser.email != null) return false; 
        if (linkPhoto != null ? !linkPhoto.equals(guser.linkPhoto) : guser.linkPhoto != null) return false; 

        return true;
    }
	
	
	@Override
	public String toString() {
		 return "User{" +
				 "googleId=" + googleId +
				 ", name='" + name + '\'' +
				 ", email='" + email + '\'' +
				 ", linkPhoto='" + linkPhoto + '\'' +				 
				 '}';
	}

}
