package com.animals.app.domain;

import java.io.Serializable;

@SuppressWarnings("serial")
public class FacebookProfile implements Serializable {
	
	private long facebookId;
	private String name;
	private String linkPhoto;
	private String email;
		

	public long getFacebookId() {
		return facebookId;
	}
	public void setFacebookId(long facebookId) {
		this.facebookId = facebookId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLinkPhoto() {
		return linkPhoto;
	}
	public void setLinkPhoto(String linkPhoto) {
		this.linkPhoto = linkPhoto;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FacebookProfile fbuser = (FacebookProfile) o;

        if (facebookId != fbuser.facebookId) return false;
        if (name != null ? !name.equals(fbuser.name) : fbuser.name != null) return false; 
        if (email != null ? !email.equals(fbuser.email) : fbuser.email != null) return false;        
        if (linkPhoto != null ? !linkPhoto.equals(fbuser.linkPhoto) : fbuser.linkPhoto != null) return false; 

        return true;
    }
	
	
	@Override
	public String toString() {
		 return "User{" +
				 "facebookId=" + facebookId +
				 ", name='" + name + '\'' +
				 ", email='" + email + '\'' +
				 ", linkPhoto='" + linkPhoto + '\'' +				 
				 '}';
	}
}
