package com.animals.app.repository;

import com.animals.app.domain.AnimalStatus;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by Rostyslav.Viner on 25.08.2015.
 */
public interface AnimalStatusLogerRepository {

    final String SELECT_BY_ID = "SELECT id, statusId, animalId, date FROM animalstatusesloger WHERE id = #{id}";

    /**
     * Returns an Animal status loger instance from the database.
     * @param id primary key value used for lookup.
     * @return An Animal status loger instance with a primary key value equals to pk. null if there is no matching row.
     */
    @Select(SELECT_BY_ID)
    @Results(value = {
            @Result(property="id", column="id"),
            @Result(property="status", column="statusId"),
            @Result(property="animal", column="animalId"),
            @Result(property="date", column="date")

    })
    AnimalStatus getById(long id);
}
