package com.animals.app.repository;

import com.animals.app.domain.*;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by oleg on 24.07.2015.
 */
public interface UserOperationLoggerRepository {

    final String SELECT_BY_ID = "SELECT Id, Date, UserId, OperationId, AnimalId FROM UserOperationsLogger WHERE Id = #{id};";
    final String SELECT_ALL = "SELECT Id, Date, UserId, OperationId, AnimalId FROM UserOperationsLogger;";

    /**
     * Returns a User operations logger instance from the database.
     * @param id primary key value used for lookup.
     * @return A User operations logger instance with a primary key value equals to pk. null if there is no matching row.
     */
    @Select(SELECT_BY_ID)
    @Results(value = {
            @Result(property="id", column="Id"),
            @Result(property="user", column="userId", javaType = User.class,
                    one = @One(select = "com.animals.app.repository.UserRepository.getById")),
            @Result(property="userOperationType", column="operationId", javaType = UserOperationType.class,
                    one = @One(select = "com.animals.app.repository.UserOperationTypeRepository.getById")),
            @Result(property="animal", column="animalId", javaType = Animal.class,
                    one = @One(select = "com.animals.app.repository.AnimalRepository.getById"))
    })
    UserOperationLogger getById(int id);

    /**
     * Returns the list of all User roles instances from the database.
     * @return the list of all User roles instances from the database.
     */
    @Select(SELECT_ALL)
    @Results(value = {
            @Result(property="id", column="Id"),
            @Result(property="user", column="userId", javaType = User.class,
                    one = @One(select = "com.animals.app.repository.UserRepository.getById")),
            @Result(property="userOperationType", column="operationId", javaType = UserOperationType.class,
                    one = @One(select = "com.animals.app.repository.UserOperationTypeRepository.getById")),
            @Result(property="animal", column="animalId", javaType = Animal.class,
                    one = @One(select = "com.animals.app.repository.AnimalRepository.getById"))
    })
    List<UserOperationLogger> getAll();

}
