package com.animals.app.repository;

import com.animals.app.domain.*;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by Rostyslav.Viner on 24.07.2015.
 */
public interface AnimalRepository {

    final String SELECT_ALL = "SELECT id, sexTypeId, typeId, sizeId, citesId, sort, transpNumber, tokenNumber, " +
            "dateOfRegister, dateOfBirth, dateOfSterilization, color, userId, addressId, " +
            "isActive, image, serviceId " +
            "FROM animals";
    final String SELECT_BY_ID = "SELECT id, sexTypeId, typeId, sizeId, citesId, sort, transpNumber, tokenNumber, " +
            "dateOfRegister, dateOfBirth, dateOfSterilization, color, userId, addressId, " +
            "isActive, image, serviceId " +
            "FROM animals WHERE id = #{id}";

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
                    one = @One(select = "com.animals.app.repository.CitesTypeRepository.getById")),
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
                    one = @One(select = "com.animals.app.repository.CitesTypeRepository.getById")),
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
    Animal getById(int id);
}
