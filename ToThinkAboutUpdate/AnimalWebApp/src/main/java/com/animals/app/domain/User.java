package com.animals.app.domain;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;

/**
 * Created by oleg on 22.07.2015.
 */
public class User implements Serializable{

    private Integer id;
    private String name;
    private String surname;
    private Date registrationDate;
    private String email;
    private String password;
    private String phone;
    private String socialLogin;
    private String organizationName;
    private String organizationInfo;
    private boolean isActive;

    private List<UserRole> userRole;
    private Address address;
    private UserType userType;

    public User() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSocialLogin() {
        return socialLogin;
    }

    public void setSocialLogin(String socialLogin) {
        this.socialLogin = socialLogin;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public String getOrganizationInfo() {
        return organizationInfo;
    }

    public void setOrganizationInfo(String organizationInfo) {
        this.organizationInfo = organizationInfo;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public List<UserRole> getUserRole() {
        return userRole;
    }

    public void setUserRole(List<UserRole> userRole) {
        this.userRole = userRole;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (isActive != user.isActive) return false;
        if (!email.equals(user.email)) return false;
        if (!id.equals(user.id)) return false;
        if (!name.equals(user.name)) return false;
        if (organizationInfo != null ? !organizationInfo.equals(user.organizationInfo) : user.organizationInfo != null)
            return false;
        if (organizationName != null ? !organizationName.equals(user.organizationName) : user.organizationName != null)
            return false;
        if (!password.equals(user.password)) return false;
        if (phone != null ? !phone.equals(user.phone) : user.phone != null) return false;
        if (!registrationDate.equals(user.registrationDate)) return false;
        if (socialLogin != null ? !socialLogin.equals(user.socialLogin) : user.socialLogin != null) return false;
        if (!surname.equals(user.surname)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + name.hashCode();
        result = 31 * result + surname.hashCode();
        result = 31 * result + registrationDate.hashCode();
        result = 31 * result + email.hashCode();
        result = 31 * result + password.hashCode();
        result = 31 * result + (phone != null ? phone.hashCode() : 0);
        result = 31 * result + (socialLogin != null ? socialLogin.hashCode() : 0);
        result = 31 * result + (organizationName != null ? organizationName.hashCode() : 0);
        result = 31 * result + (organizationInfo != null ? organizationInfo.hashCode() : 0);
        result = 31 * result + (isActive ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", registrationDate=" + registrationDate +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", phone='" + phone + '\'' +
                ", socialLogin='" + socialLogin + '\'' +
                ", organizationName='" + organizationName + '\'' +
                ", organizationInfo='" + organizationInfo + '\'' +
                ", isActive=" + isActive +
                ", userRole=" + userRole +
                ", address=" + address +
                ", userType=" + userType +
                '}';
    }
}
