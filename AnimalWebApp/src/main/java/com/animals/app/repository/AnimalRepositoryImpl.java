package com.animals.app.repository;

import com.animals.app.domain.Animal;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import java.util.List;

/**
 * Created by Rostyslav.Viner on 24.07.2015.
 */
public class AnimalRepositoryImpl {
    private SqlSessionFactory sqlSessionFactory;

    public AnimalRepositoryImpl() {
        sqlSessionFactory = new MyBatisConnectionFactory().getSqlSessionFactory();
    }

    /**
     * Returns the list of all Animal instances from the database.
     * @return the list of all Animal instances from the database.
     */
    public List<Animal> getAll(){

        SqlSession session = sqlSessionFactory.openSession();

        try {
            AnimalRepository mapper = session.getMapper(AnimalRepository.class);
            return mapper.getAll();
        } finally {
            session.close();
        }
    }

    /**
     * Returns an Animal instance from the database.
     * @param id primary key value used for lookup.
     * @return An Animal instance with a primary key value equals to pk. null if there is no matching row.
     */
    public Animal getById(int id){

        SqlSession session = sqlSessionFactory.openSession();

        try {
            AnimalRepository mapper = session.getMapper(AnimalRepository.class);
            return mapper.getById(id);
        } finally {
            session.close();
        }
    }
}
