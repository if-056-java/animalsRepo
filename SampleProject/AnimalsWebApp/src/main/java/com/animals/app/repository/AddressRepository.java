package com.animals.app.repository;

import com.animals.app.domain.Address;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by oleg on 24.07.2015.
 */
public interface AddressRepository {

    final String SELECT_ALL = "SELECT Id, Country, Region, Town, Street, PostIndex FROM Addresses";
    final String SELECT_BY_ID = "SELECT Id, Country, Region, Town, Street, PostIndex FROM Addresses WHERE Id = #{id}";

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
