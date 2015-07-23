package com.animals.app.repository;

import com.animals.app.domain.AnimalSize;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import java.util.List;

/**
 * Created by Rostyslav.Viner on 23.07.2015.
 */
public class AnimalSizeRepositoryImpl {

    private SqlSessionFactory sqlSessionFactory;

    public AnimalSizeRepositoryImpl() {
        sqlSessionFactory = new MyBatisConnectionFactory().getSqlSessionFactory();
    }

    /**
     * Returns the list of all Animal size instances from the database.
     * @return the list of all Animal size instances from the database.
     */
    public List<AnimalSize> getAll(){

        SqlSession session = sqlSessionFactory.openSession();

        try {
            AnimalSizeRepository mapper = session.getMapper(AnimalSizeRepository.class);
            return mapper.getAll();
        } finally {
            session.close();
        }
    }

    /**
     * Returns a Animal size instance from the database.
     * @param id primary key value used for lookup.
     * @return An Animal size type instance with a primary key value equals to pk. null if there is no matching row.
     */
    public AnimalSize getById(int id){

        SqlSession session = sqlSessionFactory.openSession();

        try {
            AnimalSizeRepository mapper = session.getMapper(AnimalSizeRepository.class);
            return mapper.getById(id);
        } finally {
            session.close();
        }
    }
}
