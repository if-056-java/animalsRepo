package com.animals.app.domain;

import java.io.Serializable;

public class TwitterProfile implements Serializable {
	
	private long twitterId;
	private String name;	
	private String linkPhoto;
	
	
	public long getTwitterId() {
		return twitterId;
	}
	public void setTwitterId(long twitterId) {
		this.twitterId = twitterId;
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
	
	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TwitterProfile twuser = (TwitterProfile) o;

        if (twitterId != twuser.twitterId) return false;
        if (name != null ? !name.equals(twuser.name) : twuser.name != null) return false;        
        if (linkPhoto != null ? !linkPhoto.equals(twuser.linkPhoto) : twuser.linkPhoto != null) return false; 

        return true;
    }
	
	
	@Override
	public String toString() {
		 return "User{" +
				 "twitterId=" + twitterId +
				 ", name='" + name + '\'' +
				 ", linkPhoto='" + linkPhoto + '\'' +				 
				 '}';
	}
	
	

}
