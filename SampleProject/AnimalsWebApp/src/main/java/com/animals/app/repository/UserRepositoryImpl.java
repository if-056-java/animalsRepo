package com.animals.app.repository;

import com.animals.app.domain.User;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

/**
 * Created by oleg on 24.07.2015.
 */
public class UserRepositoryImpl {

    private SqlSessionFactory sqlSessionFactory;

    public UserRepositoryImpl() {
        sqlSessionFactory = new MyBatisConnectionFactory().getSqlSessionFactory();
    }

    /**
     * Returns a User instance from the database.
     * @param id primary key value used for lookup.
     * @return A User instance with a primary key value equals to pk. null if there is no matching row.
     */
    public User getById(int id){

        SqlSession sqlSession =  sqlSessionFactory.openSession();

        try{
            UserRepository mapper = sqlSession.getMapper(UserRepository.class);
            return mapper.getById(id);
        } finally {
            sqlSession.close();
        }
    }
    
    public List<User> getAllUsers(){
    	
    	SqlSession sqlSession =  sqlSessionFactory.openSession();

        try{
            UserRepository mapper = sqlSession.getMapper(UserRepository.class);
            return mapper.getAllUsers();
        } finally {
            sqlSession.close();
        }    	 	
    }

}
