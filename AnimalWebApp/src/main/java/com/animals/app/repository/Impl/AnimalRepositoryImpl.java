package com.animals.app.repository.Impl;

import com.animals.app.domain.Animal;
import com.animals.app.repository.AnimalRepository;
import com.animals.app.repository.MyBatisConnectionFactory;
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
     * Insert an instance of Animal into the database.
     * @param animal the instance to be persisted.
     */
    public void insert(Animal animal) {

        SqlSession session = sqlSessionFactory.openSession();

        try {
            AnimalRepository mapper = session.getMapper(AnimalRepository.class);
            mapper.insert(animal);

            session.commit();
        } finally {
            session.close();
        }

    }

    /**
     * Update an instance of Animal in the database.
     * @param animal the instance to be updated.
     */
    public void update(Animal animal) {

        SqlSession session = sqlSessionFactory.openSession();

        try {
            AnimalRepository mapper = session.getMapper(AnimalRepository.class);
            mapper.update(animal);

            session.commit();
        } finally {
            session.close();
        }

    }

    /**
     * Delete an instance of Animal from the database.
     * @param id primary key value of the instance to be deleted.
     */
    public void delete(long id) {

        SqlSession session = sqlSessionFactory.openSession();

        try {
            AnimalRepository mapper = session.getMapper(AnimalRepository.class);
            mapper.delete(id);

            session.commit();
        } finally {
            session.close();
        }

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
    public Animal getById(long id){

        SqlSession session = sqlSessionFactory.openSession();

        try {
            AnimalRepository mapper = session.getMapper(AnimalRepository.class);
            return mapper.getById(id);
        } finally {
            session.close();
        }
    }

    /**
     * This method return short information about animals for showing on adopting page.
     * @return the list of all Animal instances from the database.
     */
    public List<Animal> getAllForAdopting(){

        SqlSession session = sqlSessionFactory.openSession();

        try {
            AnimalRepository mapper = session.getMapper(AnimalRepository.class);
            return mapper.getAllForAdopting();
        } finally {
            session.close();
        }
    }
}
