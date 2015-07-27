package com.animals.app.repository;

import com.animals.app.domain.Address;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * Created by oleg on 24.07.2015.
 */
public interface AddressRepository {

    final String INSERT = "INSERT INTO addresses(Country, Region, Town, Street, PostIndex) " +
            "VALUES(#{country}, #{region}, #{town}, #{street}, #{postIndex});";
    final String UPDATE = "UPDATE addresses SET Country = #{country}, Region = #{region}," +
            "Town = #{town}, Street = #{street}, PostIndex = #{postIndex} WHERE Id = #{id};";
    final String DELETE = "DELETE FROM addresses WHERE Id = #{id};";
    final String SELECT_ALL = "SELECT Id, Country, Region, Town, Street, PostIndex FROM addresses";
    final String SELECT_BY_ID = "SELECT Id, Country, Region, Town, Street, PostIndex FROM addresses WHERE Id = #{id}";

    /**
     * Insert a Address instance into the database.
     * @param address Address instance that will be inserted.
     */
    @Insert(INSERT)
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(Address address);

    /**
     * Update a Address instance into the database.
     * @param address Address instance that will be updated.
     */
    @Update(UPDATE)
    void update(Address address);

    /**
     * Delete a Address instance from the database.
     * @param address Address instance that will be deleted.
     */
    @Delete(DELETE)
    void delete(Address address);

    /**
     * Returns a Address instance from the database.
     * @param id primary key value used for lookup.
     * @return A Address instance with a primary key value equals to pk. null if there is no matching row.
     */
    @Select(SELECT_BY_ID)
    @Results(value = {
            @Result(property="id", column="Id"),
            @Result(property="country", column="Country"),
            @Result(property="region", column="Region"),
            @Result(property="town", column="Town"),
            @Result(property="street", column="Street"),
            @Result(property="postIndex", column="PostIndex")
    })
    Address getById(int id);

    /**
     * Returns the list of all Addresses instances from the database.
     * @return the list of all Addresses instances from the database.
     */
    @Select(SELECT_ALL)
    @Results(value = {
            @Result(property="id", column="Id"),
            @Result(property="country", column="Country"),
            @Result(property="region", column="Region"),
            @Result(property="town", column="Town"),
            @Result(property="street", column="Street"),
            @Result(property="postIndex", column="PostIndex")
    })
    List<Address> getAll();

}
