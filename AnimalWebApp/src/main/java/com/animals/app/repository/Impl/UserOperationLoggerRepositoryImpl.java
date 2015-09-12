package com.animals.app.repository.Impl;

import com.animals.app.domain.UserOperationLogger;
import com.animals.app.repository.MyBatisConnectionFactory;
import com.animals.app.repository.UserOperationLoggerRepository;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import java.util.List;

/**
 * Created by oleg on 24.07.2015.
 */
public class UserOperationLoggerRepositoryImpl {

    private SqlSessionFactory sqlSessionFactory;

    public UserOperationLoggerRepositoryImpl() {
        sqlSessionFactory = new MyBatisConnectionFactory().getSqlSessionFactory();
    }

    /**
     * Returns a User operations logger instance from the database.
     * @param id primary key value used for lookup.
     * @return A User operations logger instance with a primary key value equals to pk. null if there is no matching row.
     */
    public UserOperationLogger getById(int id){
        SqlSession session = sqlSessionFactory.openSession();

        try{
            return session.getMapper(UserOperationLoggerRepository.class).getById(id);
        } finally {
            session.close();
        }
    }

    /**
     * Returns the list of all User operations logger instances from the database.
     * @return the list of all User operations logger instances from the database.
     */
    public List<UserOperationLogger> getAll(){
        SqlSession session = sqlSessionFactory.openSession();

        try{
            return session.getMapper(UserOperationLoggerRepository.class).getAll();
        } finally {
            session.close();
        }
    }

}
