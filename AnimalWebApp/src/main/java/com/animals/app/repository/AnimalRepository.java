package com.animals.app.repository;

import com.animals.app.domain.*;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

/**
 * Created by Rostyslav.Viner on 24.07.2015.
 */
public interface AnimalRepository {

    final String INSERT = "INSERT INTO animals (sex, typeId, size, citesType, breed, transpNumber, " +
            "tokenNumber, dateOfRegister, dateOfBirth, dateOfSterilization, color, userId, " +
            "address, isActive, image, serviceId) " +
            "VALUES (#{sex}, #{type.id}, #{size}, #{cites}, #{breed.id}, #{transpNumber}, #{tokenNumber}, " +
            "#{dateOfRegister}, #{dateOfBirth}, #{dateOfSterilization}, #{color}, #{user.id}, #{address}, " +
            "#{active}, #{image}, #{service.id})";

    final String UPDATE = "UPDATE animals SET sex=#{sex}, typeId=#{type.id}, size=#{size}, " +
            "citesType=#{cites}, breed=#{breed.id}, transpNumber=#{transpNumber}, tokenNumber=#{tokenNumber}, " +
            "dateOfRegister=#{dateOfRegister}, dateOfBirth=#{dateOfBirth}, " +
            "dateOfSterilization=#{dateOfSterilization}, color=#{color}, userId=#{user.id}, " +
            "address=#{address}, isActive=#{active}, image=#{image}, serviceId=#{service.id} " +
            "WHERE id=#{id}";

    final String DELETE = "DELETE FROM animals WHERE id = #{id}";

    final String ADMIN_LIST_SELECT_BY_PAGE = "SELECT id, sex, typeId, breed, transpNumber, dateOfBirth, color " +
            "FROM animals " +
            "WHERE isActive>0 LIMIT #{offset},#{limit}";

    final String ADMIN_LIST_SELECT_BY_PAGE_COUNT = "SELECT count(*) AS count " +
            "FROM animals " +
            "WHERE isActive>0";

    final String SELECT_BY_ID = "SELECT id, sex, typeId, size, citesType, breed, transpNumber, tokenNumber, " +
            "dateOfRegister, dateOfBirth, dateOfSterilization, color, userId, address, " +
            "isActive, image, serviceId " +
            "FROM animals WHERE id = #{id}";

    final String SELECT_LIST_FOR_ADOPTING = "SELECT Id, TypeId, Breed, DateOfBirth, DateOfRegister, ServiceId " +
            "FROM animals " +
            "WHERE (serviceId = 1) AND (isActive = 1) " +
            "ORDER BY DateOfRegister DESC " +
            "LIMIT #{offset}, #{limit};";

    final String SELECT_LIST_FOR_ADOPTING_COUNT = "SELECT count(*) AS count " +
            "FROM animals " +
            "WHERE (isActive = 1) AND (serviceId = 1);";

    final String USERPROFILE_SELECT_BY_USER_ID = "SELECT id, sex, typeId, breed, transpNumber, dateOfBirth, color " +
            "FROM animals " +
            "WHERE userId=#{id}";

    /**
     * Insert an instance of Animal into the database.
     * @param animal the instance to be persisted.
     */
    @Insert(INSERT)
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(Animal animal);

    /**
     * Update an instance of Animal in the database.
     * @param animal the instance to be updated.
     */
    @Update(UPDATE)
    void update(Animal animal);

    /**
     * Delete an instance of Animal from the database.
     * @param id primary key value of the instance to be deleted.
     */
    @Delete(DELETE)
    void delete(long id);

    /**
     * Returns an Animal instance from the database.
     * @param id primary key value used for lookup.
     * @return An Animal instance with a primary key value equals to pk. null if there is no matching row.
     */
    @Select(SELECT_BY_ID)
    @Results(value = {
            @Result(property="id", column="id"),
            @Result(property="sex", column="sex", javaType = Animal.SexType.class),
            @Result(property="type", column="typeId", javaType = AnimalType.class,
                    one = @One(select = "com.animals.app.repository.AnimalTypeRepository.getById")),
            @Result(property="size", column="size", javaType = Animal.SizeType.class),
            @Result(property="cites", column="citesType", javaType = Animal.CitesType.class),
            @Result(property="breed", column="breed", javaType = AnimalBreed.class,
                    one = @One(select = "com.animals.app.repository.AnimalBreedRepository.getById")),
            @Result(property="transpNumber", column="transpNumber"),
            @Result(property="tokenNumber", column="tokenNumber"),
            @Result(property="dateOfRegister", column="dateOfRegister"),
            @Result(property="dateOfBirth", column="dateOfBirth"),
            @Result(property="dateOfSterilization", column="dateOfSterilization"),
            @Result(property="color", column="color"),
            @Result(property="user", column="userId", javaType = User.class,
                    one = @One(select = "com.animals.app.repository.UserRepository.getByIdForAdminAnimalList")),
            @Result(property="address", column="address"),
            @Result(property="active", column="isActive"),
            @Result(property="image", column="image"),
            @Result(property="service", column="serviceId", javaType = AnimalService.class,
                    one = @One(select = "com.animals.app.repository.AnimalServiceRepository.getById"))
    })
    Animal getById(long id);

    /**
     * Returns the list of all Animal instances from the database.
     * @return the list of all Animal instances from the database.
     */
    @Select(ADMIN_LIST_SELECT_BY_PAGE)
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
    List<Animal> getAllForAdminAnimalsListByPage(Pagenator page);

    /**
     * Returns count of rows selected from DB by method getAdminAnimalsListByPage
     * @return count of rows selected by getAdminAnimalsListByPage
     */
    @Select(ADMIN_LIST_SELECT_BY_PAGE_COUNT)
    @Results(value = {
            @Result(property="rowsCount", column="count")
    })
    Pagenator getAdminAnimalsListByPageCount();

    /**
     * This method return short information about animals for showing on adopting page.
     * @param pagenator Separating records for a parts.
     * @return the list of all Animal instances from the database.
     */
    @Select(SELECT_LIST_FOR_ADOPTING)
    @Results(value = {
            @Result(property="id", column="id"),
            @Result(property="type", column="typeId", javaType = AnimalType.class,
                    one = @One(select = "com.animals.app.repository.AnimalTypeRepository.getById")),
            @Result(property="dateOfBirth", column="dateOfBirth"),
            @Result(property="dateOfRegister", column = "dateOfRegister"),
            @Result(property="breed", column="breed", javaType = AnimalBreed.class,
                    one = @One(select = "com.animals.app.repository.AnimalBreedRepository.getById")),
            @Result(property="service", column="serviceId", javaType = AnimalService.class,
                    one = @One(select = "com.animals.app.repository.AnimalServiceRepository.getById")),
            @Result(property="image", column="image"),
    })
    List<Animal> getAllForAdopting(Pagenator pagenator);

    /**
     * Returns count of rows selected from DB by method getAllForAdopting
     * @return count of rows selected by getAllForAdopting
     */
    @Select(SELECT_LIST_FOR_ADOPTING_COUNT)
    @Results(value = {
            @Result(property = "rowsCount", column = "count")
    })
    Pagenator getAmountListForAdopting();
    List<Animal> getAllForAdopting();
    
    @Select(USERPROFILE_SELECT_BY_USER_ID)
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
    List<Animal> getAnimalByUserId(int parseId);
}
