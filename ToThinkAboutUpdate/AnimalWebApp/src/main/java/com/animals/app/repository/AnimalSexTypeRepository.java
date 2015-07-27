package com.animals.app.repository;

import com.animals.app.domain.AnimalSexType;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by Rostyslav.Viner on 24.07.2015.
 */
public interface AnimalSexTypeRepository {

    final String SELECT_ALL = "SELECT id, sex FROM animalsextypes";
    final String SELECT_BY_ID = "SELECT id, sex FROM animalsextypes WHERE id = #{id}";

    /**
     * Returns the list of all Animal sex type instances from the database.
     * @return the list of all Animal sex type instances from the database.
     */
    @Select(SELECT_ALL)
    @Results(value = {
            @Result(property="id", column="id"),
            @Result(property="sex", column="sex")
    })
    List<AnimalSexType> getAll();

    /**
     * Returns an Animal sex type instance from the database.
     * @param id primary key value used for lookup.
     * @return An Animal sex type instance with a primary key value equals to pk. null if there is no matching row.
     */
    @Select(SELECT_BY_ID)
    @Results(value = {
            @Result(property="id", column="id"),
            @Result(property="sex", column="sex")
    })
    AnimalSexType getById(int id);
}
