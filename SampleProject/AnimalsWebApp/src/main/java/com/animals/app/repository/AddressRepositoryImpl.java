package com.animals.app.repository;

import com.animals.app.domain.Address;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import java.util.List;

/**
 * Created by oleg on 24.07.2015.
 */
public class AddressRepositoryImpl {

    private SqlSessionFactory sqlSessionFactory;

    public AddressRepositoryImpl() {
        sqlSessionFactory = new MyBatisConnectionFactory().getSqlSessionFactory();
    }

    /**
     * Returns a Address instance from the database.
     * @param id primary key value used for lookup.
     * @return A Address instance with a primary key value equals to pk. null if there is no matching row.
     */
    public Address getById(int id){
        SqlSession sqlSession = sqlSessionFactory.openSession();

        try{
            AddressRepository mapper = sqlSession.getMapper(AddressRepository.class);
            return mapper.getById(id);
        } finally {
            sqlSession.close();
        }
    }

    /**
     * Returns the list of all Addresses instances from the database.
     * @return the list of all Addresses instances from the database.
     */
    public List<Address> getAll(){
        SqlSession sqlSession = sqlSessionFactory.openSession();

        try{
            AddressRepository mapper = sqlSession.getMapper(AddressRepository.class);
            return mapper.getAll();
        } finally {
            sqlSession.close();
        }
    }

}
