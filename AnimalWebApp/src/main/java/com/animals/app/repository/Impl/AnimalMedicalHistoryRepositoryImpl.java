package com.animals.app.repository.Impl;

import com.animals.app.domain.AnimalMedicalHistory;
import com.animals.app.domain.AnimalsFilter;
import com.animals.app.repository.AnimalMedicalHistoryRepository;
import com.animals.app.repository.MyBatisConnectionFactory;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import java.util.List;

/**
 * Created by Rostyslav.Viner on 25.08.2015.
 */
public class AnimalMedicalHistoryRepositoryImpl implements AnimalMedicalHistoryRepository {

    private SqlSessionFactory sqlSessionFactory;

    public AnimalMedicalHistoryRepositoryImpl() {
        sqlSessionFactory = new MyBatisConnectionFactory().getSqlSessionFactory();
    }

    /**
     * Returns count of rows selected from DB by method getAllForAdopting
     * @return count of rows selected by getAllForAdopting
     */
    public long getByAnimalIdCount(long id) {
        SqlSession session = sqlSessionFactory.openSession();

        try {
            AnimalMedicalHistoryRepository mapper = session.getMapper(AnimalMedicalHistoryRepository.class);
            return mapper.getByAnimalIdCount(id);
        } finally {
            session.close();
        }
    }

    /**
     * Returns an Animal medical history instance from the database.
     * @param animalId primary key value used for lookup.
     * @return An Animal medical history instance with a primary key value equals to pk. null if there is no matching row.
     */
    public List<AnimalMedicalHistory> getByAnimalId(long animalId, long offset, int limit) {
        SqlSession session = sqlSessionFactory.openSession();

        try {
            AnimalMedicalHistoryRepository mapper = session.getMapper(AnimalMedicalHistoryRepository.class);
            return mapper.getByAnimalId(animalId, offset, limit);
        } finally {
            session.close();
        }
    }
}
