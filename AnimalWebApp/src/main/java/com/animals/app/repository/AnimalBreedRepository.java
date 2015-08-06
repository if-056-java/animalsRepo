package com.animals.app.repository;

import com.animals.app.domain.AnimalBreed;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

/**
 * Created by Rostyslav.Viner on 06.08.2015.
 */
public interface AnimalBreedRepository {
    final String SELECT_BY_ID = "SELECT id FROM animaltypes WHERE id=#{id}";

    /**
     * Returns an AnimalBreed instance from the database.
     * @param id primary key value used for lookup.
     * @return An AnimalBreed instance with a primary key value equals to pk. null if there is no matching row.
     */
    @Select(SELECT_BY_ID)
    @Results(value = {
            @Result(property="id", column="id")
    })
    AnimalBreed getById(long id);
}
