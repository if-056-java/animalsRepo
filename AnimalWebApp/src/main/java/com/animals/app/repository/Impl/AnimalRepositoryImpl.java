package com.animals.app.repository.Impl;

import com.animals.app.domain.Animal;
import com.animals.app.domain.AnimalsFilter;
import com.animals.app.repository.AnimalRepository;
import com.animals.app.repository.MyBatisConnectionFactory;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import java.util.List;

/**
 * Created by Rostyslav.Viner on 24.07.2015.
 */
public class AnimalRepositoryImpl implements AnimalRepository {
    private SqlSessionFactory sqlSessionFactory;

    public AnimalRepositoryImpl() {
        sqlSessionFactory = new MyBatisConnectionFactory().getSqlSessionFactory();
    }

    /**
     * Insert an instance of Animal into the database.
     *
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
     *
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
     * Update date of Twitter publication instance's of Animal in the database.
     *
     * @param animal the instance to be updated.
     */
    public void twitterUpdate(Animal animal) {

        SqlSession session = sqlSessionFactory.openSession();

        try {
            AnimalRepository mapper = session.getMapper(AnimalRepository.class);
            mapper.twitterUpdate(animal);

            session.commit();
        } finally {
            session.close();
        }

    }

    /**
     * Update date of Facebook publication instance's of Animal in the database.
     *
     * @param animal the instance to be updated.
     */
    public void facebookUpdate(Animal animal) {

        SqlSession session = sqlSessionFactory.openSession();

        try {
            AnimalRepository mapper = session.getMapper(AnimalRepository.class);
            mapper.facebookUpdate(animal);

            session.commit();
        } finally {
            session.close();
        }

    }

    /**
     * Delete an instance of Animal from the database.
     *
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
     *
     * @param id primary key value used for lookup.
     * @return An Animal instance with a primary key value equals to pk. null if there is no matching row.
     */
    public Animal getById(long id) {

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
     *
     * @return the list of all Animal instances from the database.
     */
    public List<Animal> getAdminAnimals(AnimalsFilter animalsFilter) {

        SqlSession session = sqlSessionFactory.openSession();

        try {
            AnimalRepository mapper = session.getMapper(AnimalRepository.class);
            return mapper.getAdminAnimals(animalsFilter);
        } finally {
            session.close();
        }
    }

    /**
     * Created by 41X 08.08.2015
     * Returns count of rows selected from DB by method getAdminAnimalsListByPage
     *
     * @return count of rows selected by getAdminAnimalsListByPage
     */
    public long getAdminAnimalsPaginator(AnimalsFilter animalsFilter) {

        SqlSession session = sqlSessionFactory.openSession();

        try {
            AnimalRepository mapper = session.getMapper(AnimalRepository.class);
            return mapper.getAdminAnimalsPaginator(animalsFilter);
        } finally {
            session.close();
        }
    }

    /*
     * This method return short information about animals for showing on adopting page.
     * @param pagenator Separating records for a parts.
     * @return the list of all Animal instances from the database.
     */
    public List<Animal> getAllForAdopting(AnimalsFilter animalsFilter) {

        SqlSession session = sqlSessionFactory.openSession();

        try {
            AnimalRepository mapper = session.getMapper(AnimalRepository.class);
            return mapper.getAllForAdopting(animalsFilter);
        } finally {
            session.close();
        }
    }

    /*
 * This method return short information about animals for showing on found page.
 * @param pagenator Separating records for a parts.
 * @return the list of all Animal instances from the database.
 */
    public List<Animal> getAllFoundAnimals(AnimalsFilter animalsFilter) {

        SqlSession session = sqlSessionFactory.openSession();

        try {
            AnimalRepository mapper = session.getMapper(AnimalRepository.class);
            return mapper.getAllFoundAnimals(animalsFilter);
        } finally {
            session.close();
        }
    }

    /*
 * This method return short information about animals for showing on lost page.
 * @param pagenator Separating records for a parts.
 * @return the list of all Animal instances from the database.
 */
    public List<Animal> getAllLostAnimals(AnimalsFilter animalsFilter) {

        SqlSession session = sqlSessionFactory.openSession();

        try {
            AnimalRepository mapper = session.getMapper(AnimalRepository.class);
            return mapper.getAllLostAnimals(animalsFilter);
        } finally {
            session.close();
        }
    }

    /**
     * Returns count of rows selected from DB by method getAllForAdopting
     *
     * @return count of rows selected by getAllForAdopting
     */
    public long getAmountListForAdopting(AnimalsFilter animalsFilter) {

        SqlSession session = sqlSessionFactory.openSession();

        try {
            AnimalRepository mapper = session.getMapper(AnimalRepository.class);
            return mapper.getAmountListForAdopting(animalsFilter);
        } finally {
            session.close();
        }
    }

    /**
     * Returns count of rows selected from DB by method getAllFoundAnimals
     *
     * @return count of rows selected by getAllFoundAnimals
     */
    public long getAmountListFoundAnimals(AnimalsFilter animalsFilter) {

        SqlSession session = sqlSessionFactory.openSession();

        try {
            AnimalRepository mapper = session.getMapper(AnimalRepository.class);
            return mapper.getAmountListFoundAnimals(animalsFilter);
        } finally {
            session.close();
        }
    }

    /**
     * Returns count of rows selected from DB by method getAllLostAnimals
     *
     * @return count of rows selected by getAllLostAnimals
     */
    public long getAmountListLostAnimals(AnimalsFilter animalsFilter) {

        SqlSession session = sqlSessionFactory.openSession();

        try {
            AnimalRepository mapper = session.getMapper(AnimalRepository.class);
            return mapper.getAmountListLostAnimals(animalsFilter);
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
     *
     * @param id primary key value used for lookup.
     * @return An Animal instance with a primary key value equals to pk. null if there is no matching row.
     */
    public Animal getShortInfoById(long id) {
        SqlSession session = sqlSessionFactory.openSession();

        try {
            AnimalRepository mapper = session.getMapper(AnimalRepository.class);
            return mapper.getShortInfoById(id);
        } finally {
            session.close();
        }
    }
}
