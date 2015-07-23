package com.animals.app.repository;

import com.animals.app.domain.CitesType;
import com.animals.app.controller.MyBatisConnectionFactory;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import java.util.List;

/**
 * Created by Rostyslav.Viner on 23.07.2015.
 */
public class CitesTypeRepositoryImpl {

    private SqlSessionFactory sqlSessionFactory;

    public CitesTypeRepositoryImpl() {
        sqlSessionFactory = new MyBatisConnectionFactory().getSqlSessionFactory();
    }

    /**
     * Returns the list of all Cites type instances from the database.
     * @return the list of all Cites type instances from the database.
     */
    public List<CitesType> query(){

        SqlSession session = sqlSessionFactory.openSession();

        try {
            CitesTypeRepository mapper = session.getMapper(CitesTypeRepository.class);
            return mapper.selectAll();
        } finally {
            session.close();
        }
    }

    /**
     * Returns a Cites type instance from the database.
     * @param id primary key value used for lookup.
     * @return A Cites type instance with a primary key value equals to pk. null if there is no matching row.
     */
    public CitesType query(int id){

        SqlSession session = sqlSessionFactory.openSession();

        try {
            CitesTypeRepository mapper = session.getMapper(CitesTypeRepository.class);
            return mapper.selectById(id);
        } finally {
            session.close();
        }
    }
}
