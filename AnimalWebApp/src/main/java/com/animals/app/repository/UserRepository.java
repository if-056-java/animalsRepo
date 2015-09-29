package com.animals.app.repository;

import com.animals.app.domain.*;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface UserRepository {

    final String INSERT = "<script> " +
            "INSERT INTO users (Name, Surname, DateOfRegistration, " +
            "UserTypeId, UserRoleId, Phone, Address, Email, SocialLogin, " +
            "Password, OrganizationName, OrganizationInfo, IsActive, GoogleId, FacebookId, TwitterId, SocialPhoto, EmailVerificationString) " +
            "VALUES " +
            "<foreach collection='userRole' item='element' index='index' open='(' separator='),(' close=')'> " +
            "#{name}, #{surname}, #{registrationDate}, #{userType.id}, " +
            "#{element.id}, #{phone}, #{address}, #{email}, #{socialLogin}, " +
            "#{password}, #{organizationName}, #{organizationInfo}, #{isActive}," +
            " #{googleId}, #{facebookId}, #{twitterId}, #{socialPhoto}, #{emailVerificator} " +
            "</foreach></script>";

    final String UPDATE = "UPDATE users SET Name=#{name}, Surname=#{surname}, " +
            "DateOfRegistration=#{registrationDate}, UserTypeId=#{userType.id}, " +
            "UserRoleId=#{userRole, typeHandler=com.animals.app.domain.UserRole}, Phone=#{phone}, Address=#{address}, " +
            "Email=#{email}, SocialLogin=#{socialLogin}, Password=#{password}, " +
            "OrganizationName=#{organizationName}, OrganizationInfo=#{organizationInfo}, " +
            "IsActive=#{isActive}, GoogleId=#{googleId}, FacebookId=#{facebookId}, TwitterId=#{twitterId}, SocialPhoto=#{socialPhoto} " +
            "WHERE Id=#{id}";
    
    final String UPDATE_RESTRICTED = "UPDATE users SET Name=#{name}, Surname=#{surname}, " +
            "DateOfRegistration=#{registrationDate}, Phone=#{phone}, Address=#{address}, " +
            "Email=#{email}, Password=#{password}, OrganizationName=#{organizationName}, OrganizationInfo=#{organizationInfo}" +
            "WHERE Id=#{id}";

    final String DELETE = "DELETE FROM users WHERE Id = #{id}";

    final String SELECT_BY_ID = "SELECT Id, Name, Surname, DateOfRegistration, " +
            " UserTypeId, UserRoleId, Phone, Address, Email, SocialLogin, " +
            " Password, OrganizationName, OrganizationInfo, IsActive, GoogleId, FacebookId, TwitterId, SocialPhoto" +
            " FROM users WHERE Id = #{id}";

    final String SELECT_BY_ID_FOR_ADMIN_ANIMAL_LIST = "SELECT id, name, surname, userTypeId, phone, email  " +
            "FROM users WHERE Id = #{id}";
    
    final String SELECT_USERS = "SELECT Id, Name, Surname, DateOfRegistration, " +
            " UserTypeId, UserRoleId, Phone, Address, Email, SocialLogin, " +
            " Password, OrganizationName, OrganizationInfo, IsActive " +
            " FROM users";
    
    final String SELECT_UNIQUE_USERNAME = "SELECT SocialLogin " +		
            "FROM users WHERE SocialLogin = #{socialLogin}";
    
    final String SELECT_UNIQUE_EMAIL = "SELECT Email " +       
            "FROM users WHERE Email = #{email}";
    
    final String SELECT_USER_AUTHENTICATION =  "SELECT Id, Name, Surname, DateOfRegistration, " +
            " UserTypeId, UserRoleId, Phone, Address, Email, SocialLogin, " +
            " Password, OrganizationName, OrganizationInfo, IsActive, GoogleId, SocialPhoto" +
            " FROM users WHERE (SocialLogin = #{socialLogin} AND Password = #{password})" ;
    
    final String SELECT_BY_GOOGLE_ID = "SELECT Id, Name, Surname, DateOfRegistration, " +
            " UserTypeId, UserRoleId, Phone, Address, Email, SocialLogin, " +
            " Password, OrganizationName, OrganizationInfo, IsActive, GoogleId, SocialPhoto" +
            " FROM users WHERE GoogleId = #{googleId}";
    
    final String SELECT_BY_FACEBOOK_ID = "SELECT Id, Name, Surname, DateOfRegistration, " +
            " UserTypeId, UserRoleId, Phone, Address, Email, SocialLogin, " +
            " Password, OrganizationName, OrganizationInfo, IsActive, FacebookId, SocialPhoto" +
            " FROM users WHERE FacebookId = #{facebookId}";
    
    final String SELECT_BY_TWITTER_ID = "SELECT Id, Name, Surname, DateOfRegistration, " +
            " UserTypeId, UserRoleId, Phone, Address, Email, SocialLogin, " +
            " Password, OrganizationName, OrganizationInfo, IsActive, TwitterId, SocialPhoto" +
            " FROM users WHERE TwitterId = #{twitterId}";

    final String SELECT_BY_ID_MEDICAL_HISTORY = "SELECT Id, Name, Surname" +
            " FROM users WHERE Id = #{id}";
    
    final String SELECT_USER_VERIFICATION =  "SELECT Id, Name, Surname, DateOfRegistration, " +
            " UserTypeId, UserRoleId, Phone, Address, Email, SocialLogin, " +
            " Password, OrganizationName, OrganizationInfo, IsActive, GoogleId, SocialPhoto" +
            " FROM users WHERE (SocialLogin = #{socialLogin} AND EmailVerificationString = #{emailVerificationString})" ;

    final String ADMIN_ANIMALS = "<script> SELECT id, sex, typeId, breed, transpNumber, dateOfBirth, dateOfFacebook, dateOfTwitter, color " +
            "FROM animals " +
            "WHERE id>0 " +
            "<if test = \"animal != null\">" +
            "<if test = \"animal.type != null\"> " +
            "<if test = \"animal.type.id != null\"> AND typeId=#{animal.type.id} </if> " +
            "</if>" +
            "<if test = \"animal.breed != null\"> " +
            "<if test = \"animal.breed.id != null\"> AND breed=#{animal.breed.id} </if> " +
            "</if>" +
            "<if test = \"animal.transpNumber != null\"> AND transpNumber=#{animal.transpNumber} </if> " +
            "<if test = \"animal.dateOfRegister != null\"> AND dateOfRegister=#{animal.dateOfRegister} </if> " +
            "<if test = \"animal.sex != null\"> AND sex=#{animal.sex} </if> " +
            "<if test = \"animal.active != null\"> " +
            "<if test = \"animal.active == true\"> AND isActive=1 </if> " +
            "<if test = \"animal.active == false\"> AND isActive=0 </if> " +
            "</if>" +
            "<if test = \"animal.service != null\"> " +
            "<if test = \"animal.service.id != null\"> AND serviceId=#{animal.service.id} </if> " +
            "</if>" +
            "</if> " +
            "LIMIT #{offset},#{limit}</script>";

    final String SELECT_USER_LIST_FOR_MODERATOR = "<script> " +
            "SELECT Id, Name, Surname, Email, DateOfRegistration, IsActive " +
            "FROM users " +
            "WHERE id>0 " +
            "<if test = \"user != null\">" +
                "<if test = \"user.userType != null\"> " +
                    "<if test = \"user.userType.id != null\"> AND userTypeId=#{user.userType.id} </if> " +
                "</if>" +
                "<if test = \"user.userRole != null\"> " +
                        " AND userRoleId IN " +
                    "<foreach item=\"element\" index=\"index\" collection=\"user.userRole\" open=\"(\" separator=\",\" close=\")\">" +
                        "#{element.id}" +
                    "</foreach>" +
                "</if>" +
                "<if test = \"user.isActive != null\"> " +
                    "<if test = \"user.isActive == true\"> AND isActive=1 </if> " +
                    "<if test = \"user.isActive == false\"> AND isActive=0 </if> " +
                "</if>" +
            "</if> " +
            "ORDER BY DateOfRegistration " +
            "LIMIT #{offset}, #{limit} </script>";

    final String SELECT_USER_LIST_FOR_MODERATOR_PAGINATOR = "<script>" +
            "SELECT count(*) AS count FROM users " +
            "WHERE id>0" +
            "<if test = \"user != null\">" +
            "<if test = \"user.userType != null\"> " +
                "<if test = \"user.userType.id != null\"> AND userTypeId=#{user.userType.id} </if> " +
            "</if>" +
            "<if test = \"user.userRole != null\"> " +
                    " AND userRoleId IN " +
                "<foreach item=\"element\" index=\"index\" collection=\"user.userRole\" open=\"(\" separator=\",\" close=\")\">" +
                    "#{element.id}" +
                "</foreach>" +
            "</if>" +
            "<if test = \"user.isActive != null\"> " +
                "<if test = \"user.isActive == true\"> AND isActive=1 </if> " +
                "<if test = \"user.isActive == false\"> AND isActive=0 </if> " +
            "</if>" +
            "</if> " +
            "</script> ";
    
    final String SELECT_ANIMAL_BY_USER_ID_PAGINATOR = "SELECT count(*) AS count " +
            "FROM animals WHERE userId=#{id}";
    
    final String SELECT_ANIMALS_BY_USER_ID = "SELECT id, sex, typeId, breed, transpNumber, dateOfBirth, color " +
            "FROM animals " +
            "WHERE userId=#{id} LIMIT #{offset},#{limit}";
    
    final String SELECT_USER_BY_EMAIL = "SELECT Id, Name, Surname, DateOfRegistration, " +
            " UserTypeId, UserRoleId, Phone, Address, Email, SocialLogin, " +
            " Password, OrganizationName, OrganizationInfo, IsActive, GoogleId, SocialPhoto" +
            " FROM users WHERE Email = #{email}";
    

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
     * Update an instance of User in the database.(for userRole=guest)
     * @param user the instance to be updated.
     */
    @Update(UPDATE_RESTRICTED)
    void updateRestricted(User user);

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
            @Result(property="address", column="address"),
            @Result(property="email", column="Email"),
            @Result(property="socialLogin", column="SocialLogin"),
            @Result(property="organizationName", column="OrganizationName"),
            @Result(property="organizationInfo", column="OrganizationInfo"),
            @Result(property="isActive", column="IsActive"),
            @Result(property="googleId", column="GoogleId"),
            @Result(property="facebookId", column="FacebookId"),
            @Result(property="twitterId", column="TwitterId"),
            @Result(property="socialPhoto", column="SocialPhoto")
    })
    User getById(int id);

    /**
     * Returns a User instance from the database for admin animals list.
     * @param id primary key value used for lookup.
     * @return A User instance with a primary key value equals to pk. null if there is no matching row.
     */
    @Select(SELECT_BY_ID_FOR_ADMIN_ANIMAL_LIST)
    @Results(value = {
            @Result(property="id", column="id"),
            @Result(property="name", column="name"),
            @Result(property="surname", column="surname"),
            @Result(property="userType", column="userTypeId", javaType = UserType.class,
                    one = @One(select = "com.animals.app.repository.UserTypeRepository.getById")),
            @Result(property="phone", column="phone"),
            @Result(property="email", column="email")
    })
    User getByIdForAdminAnimalList(int id);

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
            @Result(property="address", column="address"),
            @Result(property="email", column="Email"),
            @Result(property="socialLogin", column="SocialLogin"),
            @Result(property="organizationName", column="OrganizationName"),
            @Result(property="organizationInfo", column="OrganizationInfo"),
            @Result(property="isActive", column="IsActive")
    })
    List<User> getAll();
    
    /** 
     * Returns a String instance from the database.
     * @param username primary key value used for lookup.
     * @return A String with value equals to pk. null if there is no matching row.
     */
    @Select(SELECT_UNIQUE_USERNAME)
    @Results(value = {
    		@Result(property="socialLogin", column="SocialLogin")
    })    
    String checkIfUsernameUnique(String username);
    
    /** 
     * Returns a String instance from the database.
     * @param email primary key value used for lookup.
     * @return A String with value equals to pk. null if there is no matching row.
     */  
    @Select(SELECT_UNIQUE_EMAIL)
    @Results(value = {
            @Result(property="email", column="Email")
    }) 
    String checkIfEmailUnique(String email);
    
    /** 
     * Returns a User instance from the database.
     * @param socialLogin and password primary key value used for lookup.
     * @return A User instance with a primary key value equals to pk. 
     * null if there is no matching row.
     */
    @Select(SELECT_USER_AUTHENTICATION)
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
            @Result(property="address", column="address"),
            @Result(property="email", column="Email"),
            @Result(property="socialLogin", column="SocialLogin"),
            @Result(property="organizationName", column="OrganizationName"),
            @Result(property="organizationInfo", column="OrganizationInfo"),
            @Result(property="isActive", column="IsActive"),
            @Result(property="googleId", column="GoogleId"),
            @Result(property="socialPhoto", column="SocialPhoto")    		
    })
    User checkIfUserExistInDB(@Param("socialLogin") String socialLogin, @Param("password") String password);
    
    /** 
     * Returns a User instance from the database.
     * @param googleId value used for lookup.
     * @return A User instance with a GoogleId value equals to pk. null if there is no matching row.
     */
    @Select(SELECT_BY_GOOGLE_ID)
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
            @Result(property="address", column="address"),
            @Result(property="email", column="Email"),
            @Result(property="socialLogin", column="SocialLogin"),
            @Result(property="organizationName", column="OrganizationName"),
            @Result(property="organizationInfo", column="OrganizationInfo"),
            @Result(property="isActive", column="IsActive"),
            @Result(property="googleId", column="GoogleId"),
            @Result(property="socialPhoto", column="SocialPhoto")
    })
    User getByGoogleId(String googleId);
    
    /** 
     * Returns a User instance from the database.
     * @param facebookId value used for lookup.
     * @return A User instance with a FacebookId value equals to pk. null if there is no matching row.
     */
    @Select(SELECT_BY_FACEBOOK_ID)
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
            @Result(property="address", column="address"),
            @Result(property="email", column="Email"),
            @Result(property="socialLogin", column="SocialLogin"),
            @Result(property="organizationName", column="OrganizationName"),
            @Result(property="organizationInfo", column="OrganizationInfo"),
            @Result(property="isActive", column="IsActive"),
            @Result(property="facebookId", column="FacebookId"),
            @Result(property="socialPhoto", column="SocialPhoto")
    })
    User getByFacebookId(String facebookId);
    
    /** 
     * Returns a User instance from the database.
     * @param twitterId value used for lookup.
     * @return A User instance with a TwitterId value equals to pk. null if there is no matching row.
     */
    @Select(SELECT_BY_TWITTER_ID)
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
            @Result(property="address", column="address"),
            @Result(property="email", column="Email"),
            @Result(property="socialLogin", column="SocialLogin"),
            @Result(property="organizationName", column="OrganizationName"),
            @Result(property="organizationInfo", column="OrganizationInfo"),
            @Result(property="isActive", column="IsActive"),
            @Result(property="twitterId", column="TwitterId"),
            @Result(property="socialPhoto", column="SocialPhoto")
    })
    User getByTwitterId(String twitterId);

    /**
     * Returns a User instance from the database for admin animals list.
     * @param id primary key value used for lookup.
     * @return A User instance with a primary key value equals to pk. null if there is no matching row.
     */
    @Select(SELECT_BY_ID_MEDICAL_HISTORY)
    @Results(value = {
            @Result(property="id", column="id"),
            @Result(property="name", column="name"),
            @Result(property="surname", column="surname")
    })
    User getByIdMedicalHistory(long id);
    
    /** 
     * Returns a User instance from the database.
     * @param socialLogin and emailVerificator primary key value used for lookup.
     * @return A User instance with a primary key value equals to pk. null if there is no matching row.
     */
    @Select(SELECT_USER_VERIFICATION)
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
            @Result(property="address", column="address"),
            @Result(property="email", column="Email"),
            @Result(property="socialLogin", column="SocialLogin"),
            @Result(property="organizationName", column="OrganizationName"),
            @Result(property="organizationInfo", column="OrganizationInfo"),
            @Result(property="isActive", column="IsActive"),
            @Result(property="googleId", column="GoogleId"),
            @Result(property="socialPhoto", column="SocialPhoto")
    })
    User userVerification(@Param("socialLogin") String socialLogin, 
                          @Param("emailVerificationString") String emailVerificationString);


    /**
     * @return the list of all User instances from the database.
     */
    @Select(SELECT_USER_LIST_FOR_MODERATOR)
    @Results(value = {
            @Result(property="id", column="id"),
            @Result(property="name", column="name"),
            @Result(property="surname", column="surname"),
            @Result(property="email", column="email"),
            @Result(property="registrationDate", column="dateOfRegistration"),
            @Result(property="isActive", column="isActive")
    })
    List<User> getAdminUsers(UsersFilter usersFilter);

    /**
     * @return count of rows selected by getAdminUsersPaginator
     */
    @Select(SELECT_USER_LIST_FOR_MODERATOR_PAGINATOR)
    long getAdminUsersPaginator(UsersFilter usersFilter);
    
    /**
     * Returns count of rows selected from DB by method getAnimalByUserIdCount
     * @return count of rows selected by getAnimalByUserIdCount
     */
    @Select(SELECT_ANIMAL_BY_USER_ID_PAGINATOR)
    long getAnimalByUserIdCount(long id);

    /**
     * Returns an Animal medical history instance from the database.
     * @param id primary key value used for lookup.
     * @return An Animal medical history instance with a primary key value equals to pk. null if there is no matching row.
     */
    @Select(SELECT_ANIMALS_BY_USER_ID)
    @Results(value = {
            @Result(property="id", column="id"),
            @Result(property="sex", column="sex", javaType = Animal.SexType.class),
            @Result(property="type", column="typeId", javaType = AnimalType.class,
                    one = @One(select = "com.animals.app.repository.AnimalTypeRepository.getById")),
            @Result(property="breed", column="breed", javaType = AnimalBreed.class,
                    one = @One(select = "com.animals.app.repository.AnimalBreedRepository.getById")),
            @Result(property="transpNumber", column="transpNumber"),
            @Result(property="dateOfBirth", column="dateOfBirth"),
            @Result(property="color", column="color")
    })    
    @Options(useCache=true)
	List<Animal> getUserAnimals(@Param("id") long id, @Param("offset") long offset, @Param("limit") int limit);

    /** 
     * Returns a User instance from the database.
     * @param String email value used for lookup.
     * @return A User instance with a email value equals to pk. null if there is no matching row.
     */
    @Select(SELECT_USER_BY_EMAIL)
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
            @Result(property="address", column="address"),
            @Result(property="email", column="Email"),
            @Result(property="socialLogin", column="SocialLogin"),
            @Result(property="password", column="password"),
            @Result(property="organizationName", column="OrganizationName"),
            @Result(property="organizationInfo", column="OrganizationInfo"),
            @Result(property="isActive", column="IsActive"),
            @Result(property="googleId", column="GoogleId"),
            @Result(property="socialPhoto", column="SocialPhoto")
    })
	User findUserByEmail(String email);
}
