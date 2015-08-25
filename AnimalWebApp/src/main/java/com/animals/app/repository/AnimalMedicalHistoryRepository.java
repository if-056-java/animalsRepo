package com.animals.app.repository;

import com.animals.app.domain.*;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * Created by Rostyslav.Viner on 25.08.2015.
 */
public interface AnimalMedicalHistoryRepository {

    final String SELECT_BY_ANIMAL_ID_PAGINATOR = "SELECT count(*) AS count " +
            "FROM animalstatusesloger WHERE animalId = #{id}";

    final String SELECT_BY_ANIMAL_ID = "SELECT id, statusId, animalId, userId, date " +
            "FROM animalstatusesloger WHERE animalId = #{id} LIMIT #{offset},#{limit}";

    /**
     * Returns count of rows selected from DB by method getAllForAdopting
     * @return count of rows selected by getAllForAdopting
     */
    @Select(SELECT_BY_ANIMAL_ID_PAGINATOR)
    long getByAnimalIdCount(long id);

    /**
     * Returns an Animal medical history instance from the database.
     * @param id primary key value used for lookup.
     * @return An Animal medical history instance with a primary key value equals to pk. null if there is no matching row.
     */
    @Select(SELECT_BY_ANIMAL_ID)
    @Results(value = {
            @Result(property="id", column="id"),
            @Result(property="status", column="statusId", javaType = AnimalStatus.class,
                    one = @One(select = "com.animals.app.repository.AnimalStatusRepository.getById")),
            @Result(property="animalId", column="animalId"),
            @Result(property="user", column="userId", javaType = User.class,
                    one = @One(select = "com.animals.app.repository.UserRepository.getByIdMedicalHistory")),
            @Result(property="date", column="date")
    })
    List<AnimalMedicalHistory> getByAnimalId(@Param("id") long id, @Param("offset") long offset, @Param("limit") int limit);
}
