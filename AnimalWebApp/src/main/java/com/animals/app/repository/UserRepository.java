package com.animals.app.repository;

import com.animals.app.domain.Address;
import com.animals.app.domain.User;
import com.animals.app.domain.UserType;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * Created by oleg on 24.07.2015.
 */
public interface UserRepository {

    final String INSERT = "<script> " +
            "INSERT INTO users (Name, Surname, DateOfRegistration, " +
            "UserTypeId, UserRoleId, Phone, AddressId, Email, SocialLogin, " +
            "Password, OrganizationName, OrganizationInfo, IsActive) " +
            "VALUES " +
            "<foreach collection='userRole' item='element' index='index' open='(' separator='),(' close=')'> " +
            "#{name}, #{surname}, #{registrationDate}, #{userType.id}, " +
            "#{element.id}, #{phone}, #{address.id}, #{email}, #{socialLogin}, " +
            "#{password}, #{organizationName}, #{organizationInfo}, #{isActive} " +
            "</foreach></script>";

    final String UPDATE = "UPDATE users SET Name=#{name}, Surname=#{surname}, " +
            "DateOfRegistration=#{registrationDate}, UserTypeId=#{userType.id}, " +
            "UserRoleId=#{userRole, typeHandler=com.animals.app.domain.UserRole}, Phone=#{phone}, AddressId=#{address.id}, " +
            "Email=#{email}, SocialLogin=#{socialLogin}, Password=#{password}, " +
            "OrganizationName=#{organizationName}, OrganizationInfo=#{organizationInfo}, " +
            "IsActive=#{isActive} " +
            "WHERE Id=#{id}";

    final String DELETE = "DELETE FROM users WHERE Id = #{id}";

    final String SELECT_BY_ID = "SELECT Id, Name, Surname, DateOfRegistration, " +
            " UserTypeId, UserRoleId, Phone, AddressId, Email, SocialLogin, " +
            " Password, OrganizationName, OrganizationInfo, IsActive " +
            " FROM users WHERE Id = #{id}";
    
    final String SELECT_USERS = "SELECT Id, Name, Surname, DateOfRegistration, " +
            " UserTypeId, UserRoleId, Phone, AddressId, Email, SocialLogin, " +
            " Password, OrganizationName, OrganizationInfo, IsActive " +
            " FROM users";

    /**
     * Insert an instance of User into the database.
     * @param user the instance to be persisted.
     */
    @Insert(INSERT)
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(User user);

    /**
     * Update an instance of User in the database.
     * @param user the instance to be updated.
     */
    @Update(UPDATE)
    void update(User user);

    /**
     * Delete an instance of User from the database.
     * @param id primary key value of the instance to be deleted.
     */
    @Delete(DELETE)
    void delete(Integer id);

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
     * Returns the list of all User instances from the database.
     * @return the list of all User instances from the database.
     */
    @Select(SELECT_USERS)
    @Results(value = {
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
