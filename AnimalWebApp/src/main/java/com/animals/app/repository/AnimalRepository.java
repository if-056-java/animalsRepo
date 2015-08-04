package com.animals.app.repository;

import com.animals.app.domain.*;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * Created by Rostyslav.Viner on 24.07.2015.
 */
public interface AnimalRepository {

    final String INSERT = "INSERT INTO animals (sexTypeId, typeId, sizeId, citesId, sort, transpNumber, " +
            "tokenNumber, dateOfRegister, dateOfBirth, dateOfSterilization, color, userId, " +
            "addressId, isActive, image, serviceId) " +
            "VALUES (#{sex.id}, #{type.id}, #{size.id}, #{cites.id}, #{sort}, #{transpNumber}, #{tokenNumber}, " +
            "#{dateOfRegister}, #{dateOfBirth}, #{dateOfSterilization}, #{color}, #{user.id}, #{address.id}, " +
            "#{active}, #{image}, #{service.id})";

    final String UPDATE = "UPDATE animals SET sexTypeId=#{sex.id}, typeId=#{type.id}, sizeId=#{size.id}, " +
            "sort=#{sort}, transpNumber=#{transpNumber}, tokenNumber=#{tokenNumber}, " +
            "dateOfRegister=#{dateOfRegister}, dateOfBirth=#{dateOfBirth}, " +
            "dateOfSterilization=#{dateOfSterilization}, color=#{color}, userId=#{user.id}, " +
            "addressId=#{address.id}, isActive=#{active}, image=#{image}, serviceId=#{service.id} " +
            "WHERE id=#{id}";

    final String DELETE = "DELETE FROM animals WHERE id = #{id}";

    final String SELECT_ALL = "SELECT id, sexTypeId, typeId, sizeId, citesId, sort, transpNumber, tokenNumber, " +
            "dateOfRegister, dateOfBirth, dateOfSterilization, color, userId, addressId, " +
            "isActive, image, serviceId " +
            "FROM animals";

    final String SELECT_BY_ID = "SELECT id, sexTypeId, typeId, sizeId, citesId, sort, transpNumber, tokenNumber, " +
            "dateOfRegister, dateOfBirth, dateOfSterilization, color, userId, addressId, " +
            "isActive, image, serviceId " +
            "FROM animals WHERE id = #{id}";

    final String SELECT_LIST_FOR_ADOPTING = "SELECT Id, TypeId, Sort, DateOfBirth " +
            "FROM animals";
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
     * Returns the list of all Animal instances from the database.
     * @return the list of all Animal instances from the database.
     */
    @Select(SELECT_ALL)
    @Results(value = {
            @Result(property="id", column="id"),
            @Result(property="sex", column="sexTypeId", javaType = AnimalSexType.class,
                    one = @One(select = "com.animals.app.repository.AnimalSexTypeRepository.getById")),
            @Result(property="type", column="typeId", javaType = AnimalType.class,
                    one = @One(select = "com.animals.app.repository.AnimalTypeRepository.getById")),
            @Result(property="size", column="sizeId", javaType = AnimalSize.class,
                    one = @One(select = "com.animals.app.repository.AnimalSizeRepository.getById")),
            @Result(property="cites", column="citesId", javaType = AnimalCitesType.class,
                    one = @One(select = "com.animals.app.repository.AnimalCitesTypeRepository.getById")),
            @Result(property="sort", column="sort"),
            @Result(property="transpNumber", column="transpNumber"),
            @Result(property="tokenNumber", column="tokenNumber"),
            @Result(property="dateOfRegister", column="dateOfRegister"),
            @Result(property="dateOfBirth", column="dateOfBirth"),
            @Result(property="dateOfSterilization", column="dateOfSterilization"),
            @Result(property="color", column="color"),
            @Result(property="user", column="userId", javaType = User.class,
                    one = @One(select = "com.animals.app.repository.UserRepository.getById")),
            @Result(property="address", column="addressId", javaType = Address.class,
                    one = @One(select = "com.animals.app.repository.AddressRepository.getById")),
            @Result(property="active", column="isActive"),
            @Result(property="image", column="image"),
            @Result(property="service", column="serviceId", javaType = AnimalService.class,
                    one = @One(select = "com.animals.app.repository.AnimalServiceRepository.getById"))
    })
    List<Animal> getAll();

    /**
     * Returns an Animal instance from the database.
     * @param id primary key value used for lookup.
     * @return An Animal instance with a primary key value equals to pk. null if there is no matching row.
     */
    @Select(SELECT_BY_ID)
    @Results(value = {
            @Result(property="id", column="id"),
            @Result(property="sex", column="sexTypeId", javaType = AnimalSexType.class,
                    one = @One(select = "com.animals.app.repository.AnimalSexTypeRepository.getById")),
            @Result(property="type", column="typeId", javaType = AnimalType.class,
                    one = @One(select = "com.animals.app.repository.AnimalTypeRepository.getById")),
            @Result(property="size", column="sizeId", javaType = AnimalSize.class,
                    one = @One(select = "com.animals.app.repository.AnimalSizeRepository.getById")),
            @Result(property="cites", column="citesId", javaType = AnimalCitesType.class,
                    one = @One(select = "com.animals.app.repository.AnimalCitesTypeRepository.getById")),
            @Result(property="sort", column="sort"),
            @Result(property="transpNumber", column="transpNumber"),
            @Result(property="tokenNumber", column="tokenNumber"),
            @Result(property="dateOfRegister", column="dateOfRegister"),
            @Result(property="dateOfBirth", column="dateOfBirth"),
            @Result(property="dateOfSterilization", column="dateOfSterilization"),
            @Result(property="color", column="color"),
            @Result(property="user", column="userId", javaType = User.class,
                    one = @One(select = "com.animals.app.repository.UserRepository.getById")),
            @Result(property="address", column="addressId", javaType = Address.class,
                    one = @One(select = "com.animals.app.repository.AddressRepository.getById")),
            @Result(property="active", column="isActive"),
            @Result(property="image", column="image"),
            @Result(property="service", column="serviceId", javaType = AnimalService.class,
                    one = @One(select = "com.animals.app.repository.AnimalServiceRepository.getById"))
    })
    Animal getById(long id);


    /**
     * This method return short information about animals for showing on adopting page.
     * @return the list of all Animal instances from the database.
     */
    @Select(SELECT_LIST_FOR_ADOPTING)
    @Results(value = {
            @Result(property="id", column="id"),
            @Result(property="type", column="typeId", javaType = AnimalType.class,
                    one = @One(select = "com.animals.app.repository.AnimalTypeRepository.getById")),
            @Result(property="sort", column="sort"),
            @Result(property="dateOfBirth", column="dateOfBirth"),
            @Result(property="image", column="image"),
    })
    List<Animal> getAllForAdopting();

}
