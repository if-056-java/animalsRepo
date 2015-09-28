package com.animals.app.service;

import java.io.Serializable;

/**
 * Created by Vova on 09.09.2015.
 */
public class ServiceMessage implements Serializable {

    private String name;
    private String email;
    private String tel;
    private String text;
    private String service;
    private String animalId;
    private String gRecaptchaResponse;

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getName() {
        return name;
    }

    public void setName(String signup) {
         this.name = signup;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getAnimalId() {
        return animalId;
    }

    public void setAnimalId(String animalId) {
        this.animalId = animalId;
    }

    public String getgRecaptchaResponse() {
        return gRecaptchaResponse;
    }

    public void setgRecaptchaResponse(String gRecaptchaResponse) {
        this.gRecaptchaResponse = gRecaptchaResponse;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ServiceMessage that = (ServiceMessage) o;

        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (email != null ? !email.equals(that.email) : that.email != null) return false;
        if (text != null ? !text.equals(that.text) : that.text != null) return false;
        if (animalId != null ? !animalId.equals(that.animalId) : that.animalId != null) return false;
        if (service != null ? !service.equals(that.service) : that.service != null) return false;
        return !(gRecaptchaResponse != null ? !gRecaptchaResponse.equals(that.gRecaptchaResponse) : that.gRecaptchaResponse != null);

    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (text != null ? text.hashCode() : 0);
        result = 31 * result + (gRecaptchaResponse != null ? gRecaptchaResponse.hashCode() : 0);
        result = 31 * result + (service != null ? service.hashCode() : 0);
        result = 31 * result + (animalId != null ? animalId.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ServiceMessage{" +
                "signup='" + name + '\'' +
                ", email='" + email + '\'' +
                ", tel='" + tel + '\'' +
                ", text='" + text + '\'' +
                ", service='" + service + '\'' +
                ", animalId='" + animalId + '\'' +
                ", gRecaptchaResponse='" + gRecaptchaResponse + '\'' +
                '}';
    }
}
