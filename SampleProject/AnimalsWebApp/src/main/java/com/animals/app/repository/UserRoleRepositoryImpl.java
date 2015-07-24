package com.animals.app.repository;

import com.animals.app.domain.UserRole;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import java.util.List;

/**
 * Created by oleg on 24.07.2015.
 */
public class UserRoleRepositoryImpl {

    private SqlSessionFactory sqlSessionFactory;

    public UserRoleRepositoryImpl() {
        sqlSessionFactory = new MyBatisConnectionFactory().getSqlSessionFactory();
    }

    /**
     * Returns a User role instance from the database.
     * @param id primary key value used for lookup.
     * @return A User role instance with a primary key value equals to pk. null if there is no matching row.
     */
    public UserRole getById(int id){

        SqlSession sqlSession = sqlSessionFactory.openSession();

        try{
            UserRoleRepository mapper = sqlSession.getMapper(UserRoleRepository.class);
            return mapper.getById(id);
        } finally {
            sqlSession.close();
        }
    }

    /**
     * Returns the list of all User roles instances from the database.
     * @return the list of all User roles instances from the database.
     */
    public List<UserRole> getAll(){

        SqlSession sqlSession = sqlSessionFactory.openSession();

        try{
            UserRoleRepository mapper = sqlSession.getMapper(UserRoleRepository.class);
            return mapper.getAll();
        } finally {
            sqlSession.close();
        }
    }


}
