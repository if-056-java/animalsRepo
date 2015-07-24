package com.animals.app.repository;

import com.animals.app.domain.AnimalCitesType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import java.util.List;

/**
 * Created by Rostyslav.Viner on 23.07.2015.
 */
public class AnimalCitesTypeRepositoryImpl {

    private SqlSessionFactory sqlSessionFactory;

    public AnimalCitesTypeRepositoryImpl() {
        sqlSessionFactory = new MyBatisConnectionFactory().getSqlSessionFactory();
    }

    /**
     * Returns the list of all Cites type instances from the database.
     * @return the list of all Cites type instances from the database.
     */
    public List<AnimalCitesType> getAll(){

        SqlSession session = sqlSessionFactory.openSession();

        try {
            AnimalCitesTypeRepository mapper = session.getMapper(AnimalCitesTypeRepository.class);
            return mapper.getAll();
        } finally {
            session.close();
        }
    }

    /**
     * Returns a Cites type instance from the database.
     * @param id primary key value used for lookup.
     * @return A Cites type instance with a primary key value equals to pk. null if there is no matching row.
     */
    public AnimalCitesType getById(int id){

        SqlSession session = sqlSessionFactory.openSession();

        try {
            AnimalCitesTypeRepository mapper = session.getMapper(AnimalCitesTypeRepository.class);
            return mapper.getById(id);
        } finally {
            session.close();
        }
    }
}
