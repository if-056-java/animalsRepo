package com.animals.app.repository.Impl;

import com.animals.app.domain.AnimalType;
import com.animals.app.repository.AnimalTypeRepository;
import com.animals.app.repository.MyBatisConnectionFactory;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import java.util.List;

/**
 * Created by Rostyslav.Viner on 23.07.2015.
 */
public class AnimalTypeRepositoryImpl implements AnimalTypeRepository {
    private SqlSessionFactory sqlSessionFactory;

    public AnimalTypeRepositoryImpl() {
        sqlSessionFactory = new MyBatisConnectionFactory().getSqlSessionFactory();
    }

    /**
     * Returns the list of all Animal type instances from the database.
     * @return the list of all Animal type instances from the database.
     */
    public List<AnimalType> getAll(){

        SqlSession session = sqlSessionFactory.openSession();

        try {
            AnimalTypeRepository mapper = session.getMapper(AnimalTypeRepository.class);
            return mapper.getAll();
        } finally {
            session.close();
        }
    }

    /**
     * Returns a Animal type instance from the database.
     * @param id primary key value used for lookup.
     * @return A Animal type instance with a primary key value equals to pk. null if there is no matching row.
     */
    public AnimalType getById(long id){

        SqlSession session = sqlSessionFactory.openSession();

        try {
            AnimalTypeRepository mapper = session.getMapper(AnimalTypeRepository.class);
            return mapper.getById(id);
        } finally {
            session.close();
        }
    }
}
