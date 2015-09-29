package com.animals.app.domain;

import java.io.Serializable;

import javax.validation.constraints.DecimalMin;

import org.hibernate.validator.constraints.Length;

public class UserType implements Serializable{

	@DecimalMin(value = "1")
    private Integer id;
	
	@Length(max = 19, message = "The type length must be less than {max}.")
    private String type;

    @Length(max = 19, message = "The UserTypeUa length must be less than {max}.")
    private String typeUa;

    public UserType() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTypeUa() {
        return typeUa;
    }

    public void setTypeUa(String typeUa) {
        this.typeUa = typeUa;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserType userType = (UserType) o;

        if (id != null ? !id.equals(userType.id) : userType.id != null) return false;
        if (type != null ? !type.equals(userType.type) : userType.type != null) return false;
        return !(typeUa != null ? !typeUa.equals(userType.typeUa) : userType.typeUa != null);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (typeUa != null ? typeUa.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "UserType{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", typeUa='" + typeUa + '\'' +
                '}';
    }
}
