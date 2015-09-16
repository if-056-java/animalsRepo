package com.animals.app.repository.Impl;

import com.animals.app.domain.UserOperationType;
import com.animals.app.repository.MyBatisConnectionFactory;
import com.animals.app.repository.UserOperationTypeRepository;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import java.util.List;

/**
 * Created by oleg on 24.07.2015.
 */
public class UserOperationTypeRepositoryImpl {

    private SqlSessionFactory sqlSessionFactory;

    public UserOperationTypeRepositoryImpl() {
        sqlSessionFactory = new MyBatisConnectionFactory().getSqlSessionFactory();
    }

    /**
     * Returns a User operation type instance from the database.
     * @param id primary key value used for lookup.
     * @return A User operation type instance with a primary key value equals to pk. null if there is no matching row.
     */
    public UserOperationType getById(int id){
        SqlSession session = sqlSessionFactory.openSession();

        try{
            return session.getMapper(UserOperationTypeRepository.class).getById(id);
        } finally {
            session.close();
        }
    }

    /**
     * Returns the list of all User operation types instances from the database.
     * @return the list of all User operation types instances from the database.
     */
    public List<UserOperationType> getAll(){
        SqlSession session = sqlSessionFactory.openSession();

        try{
            return session.getMapper(UserOperationTypeRepository.class).getAll();
        } finally {
            session.close();
        }
    }

}
