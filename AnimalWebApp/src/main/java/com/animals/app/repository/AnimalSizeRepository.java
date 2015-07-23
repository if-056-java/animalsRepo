package com.animals.app.repository;

import com.animals.app.domain.AnimalSize;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by Rostyslav.Viner on 23.07.2015.
 */
public interface AnimalSizeRepository {

    final String SELECT_ALL = "SELECT id, size FROM animalsizes";
    final String SELECT_BY_ID = "SELECT id, size FROM animalsizes WHERE id = #{id}";

    /**
     * Returns the list of all Animal size instances from the database.
     * @return the list of all Animal size instances from the database.
     */
    @Select(SELECT_ALL)
    @Results(value = {
            @Result(property="id", column="id"),
            @Result(property="size", column="size")
    })
    List<AnimalSize> selectAll();

    /**
     * Returns a Animal size instance from the database.
     * @param id primary key value used for lookup.
     * @return An Animal size instance with a primary key value equals to pk. null if there is no matching row.
     */
    @Select(SELECT_BY_ID)
    @Results(value = {
            @Result(property="id", column="id"),
            @Result(property="size", column="size")
    })
    AnimalSize selectById(int id);
}
