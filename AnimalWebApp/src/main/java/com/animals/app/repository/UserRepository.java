package com.animals.app.repository;

import com.animals.app.domain.Address;
import com.animals.app.domain.User;
import com.animals.app.domain.UserRole;
import com.animals.app.domain.UserType;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * Created by oleg on 24.07.2015.
 */
public interface UserRepository {

    final String SELECT_BY_ID = "SELECT Id, Name, Surname, DateOfRegistration, " +
            " UserTypeId, UserRoleId, Phone, AddressId, Email, SocialLogin, " +
            " Password, OrganizationName, OrganizationInfo, IsActive " +
            " FROM Users WHERE Id = #{id};";
    final String SELECT_ALL = "SELECT Id, Name, Surname, DateOfRegistration, " +
            " UserTypeId, UserRoleId, Phone, AddressId, Email, SocialLogin, " +
            " Password, OrganizationName, OrganizationInfo, IsActive " +
            " FROM Users;";

    /**
     * Returns a User instance from the database.
     * @param id primary key value used for lookup.
     * @return A User instance with a primary key value equals to pk. null if there is no matching row.
     */
    @Select(SELECT_BY_ID)
    @Results(value = {
            @Result(property="id", column="Id"),
            @Result(property="name", column="Name"),
            @Result(property="surname", column="Surname"),
            @Result(property="registrationDate", column="DateOfRegistration"),
            @Result(property="userType", column="userTypeId", javaType = UserType.class,
            one = @One(select = "com.animals.app.repository.UserTypeRepository.getById")),
            @Result(property="userRole", column="userRoleId", javaType = List.class,
            many = @Many(select = "com.animals.app.repository.UserRoleRepository.getById")),
            @Result(property="phone", column="Phone"),
            @Result(property="address", column="addressId", javaType = Address.class,
            one = @One(select = "com.animals.app.repository.AddressRepository.getById")),
            @Result(property="email", column="Email"),
            @Result(property="socialLogin", column="SocialLogin"),
            @Result(property="organizationName", column="OrganizationName"),
            @Result(property="organizationInfo", column="OrganizationInfo"),
            @Result(property="isActive", column="IsActive")
    })
    User getById(int id);

    /**
     * Returns the list of all Users instances from the database.
     * @return the list of all Users instances from the database.
     */
    @Select(SELECT_ALL)
    @Results(value = {
            @Result(property="id", column="Id"),
            @Result(property="name", column="Name"),
            @Result(property="surname", column="Surname"),
            @Result(property="registrationDate", column="DateOfRegistration"),
            @Result(property="userType", column="userTypeId", javaType = UserType.class,
                    one = @One(select = "com.animals.app.repository.UserTypeRepository.getById")),
            @Result(property="userRole", column="userRoleId", javaType = List.class,
                    many = @Many(select = "com.animals.app.repository.UserRoleRepository.getById")),
            @Result(property="phone", column="Phone"),
            @Result(property="address", column="addressId", javaType = Address.class,
                    one = @One(select = "com.animals.app.repository.AddressRepository.getById")),
            @Result(property="email", column="Email"),
            @Result(property="socialLogin", column="SocialLogin"),
            @Result(property="organizationName", column="OrganizationName"),
            @Result(property="organizationInfo", column="OrganizationInfo"),
            @Result(property="isActive", column="IsActive")
    })
    List<User> getAll();

}
