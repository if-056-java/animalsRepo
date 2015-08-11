package com.softserve.app.model;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@SuppressWarnings("serial")
@XmlRootElement
public class Owner implements Serializable {

	private String name;
	private String id;
	private String adress;
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	private String type;
	
	public String getAdress() {
		return adress;
	}
	public void setAdress(String adress) {
		this.adress = adress;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
}
