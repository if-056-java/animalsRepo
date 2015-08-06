package com.animals.app.repository.Impl;

import com.animals.app.domain.AnimalBreed;
import com.animals.app.repository.AnimalBreedRepository;
import com.animals.app.repository.MyBatisConnectionFactory;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

/**
 * Created by root on 06.08.2015.
 */
public class AnimalBreedRepositoryImpl {
    private SqlSessionFactory sqlSessionFactory;

    public AnimalBreedRepositoryImpl() {
        sqlSessionFactory = new MyBatisConnectionFactory().getSqlSessionFactory();
    }

    /**
     * Returns an AnimalBreed instance from the database.
     * @param id primary key value used for lookup.
     * @return An AnimalBreed instance with a primary key value equals to pk. null if there is no matching row.
     */
    public AnimalBreed getById(long id){

        SqlSession session = sqlSessionFactory.openSession();

        try {
            AnimalBreedRepository mapper = session.getMapper(AnimalBreedRepository.class);
            return mapper.getById(id);
        } finally {
            session.close();
        }
    }
}
