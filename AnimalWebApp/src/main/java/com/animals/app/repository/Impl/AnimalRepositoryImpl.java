package com.animals.app.repository.Impl;

import com.animals.app.domain.Animal;
import com.animals.app.domain.Pagenator;
import com.animals.app.repository.AnimalRepository;
import com.animals.app.repository.MyBatisConnectionFactory;
import org.apache.ibatis.session.RowBounds;
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
     * Returns the list of all Animal instances from the database.
     * @return the list of all Animal instances from the database.
     */
    public List<Animal> getAllForAdminAnimalsListByPage(Pagenator page) {

        SqlSession session = sqlSessionFactory.openSession();

        try {
            AnimalRepository mapper = session.getMapper(AnimalRepository.class);
            return mapper.getAllForAdminAnimalsListByPage(page);
        } finally {
            session.close();
        }
    }

    /**
     * Created by 41X 08.08.2015
     * Returns count of rows selected from DB by method getAdminAnimalsListByPage
     * @return count of rows selected by getAdminAnimalsListByPage
     */
    public Pagenator getAdminAnimalsListByPageCount() {

        SqlSession session = sqlSessionFactory.openSession();

        try {
            AnimalRepository mapper = session.getMapper(AnimalRepository.class);
            return mapper.getAdminAnimalsListByPageCount();
        } finally {
            session.close();
        }
    }

    /*
     * This method return short information about animals for showing on adopting page.
     * @param pagenator Separating records for a parts.
     * @return the list of all Animal instances from the database.
     */
    public List<Animal> getAllForAdopting(Pagenator pagenator){

        SqlSession session = sqlSessionFactory.openSession();

        try {
            AnimalRepository mapper = session.getMapper(AnimalRepository.class);
            return mapper.getAllForAdopting(pagenator);
        } finally {
            session.close();
        }
    }

    /**
     * Returns count of rows selected from DB by method getAllForAdopting
     * @return count of rows selected by getAllForAdopting
     */
    public Pagenator getAmountListForAdopting() {

        SqlSession session = sqlSessionFactory.openSession();

        try {
            AnimalRepository mapper = session.getMapper(AnimalRepository.class);
            return mapper.getAmountListForAdopting();
        } finally {
            session.close();
        }
    }

    /*
     * This method return short information about animals by UserId for showing on UserCabinet.
     * @return the list of Animal instances from the database with UserId.
     */
    public List<Animal> getAnimalByUserId(int parseId) {
    	
    	SqlSession session = sqlSessionFactory.openSession();

        try {
            AnimalRepository mapper = session.getMapper(AnimalRepository.class);
            return mapper.getAnimalByUserId(parseId);
        } finally {
            session.close();
        }
	}

    /**
     * Returns short information about animal by id.
     * @param id primary key value used for lookup.
     * @return An Animal instance with a primary key value equals to pk. null if there is no matching row.
     */
    public Animal getShortInfoById(long id){
        SqlSession session = sqlSessionFactory.openSession();

        try {
            AnimalRepository mapper = session.getMapper(AnimalRepository.class);
            return mapper.getShortInfoById(id);
        } finally {
            session.close();
        }
    }
}
