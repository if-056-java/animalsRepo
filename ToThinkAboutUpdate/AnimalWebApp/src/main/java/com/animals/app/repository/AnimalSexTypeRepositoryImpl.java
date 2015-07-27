package com.animals.app.repository;

import com.animals.app.domain.AnimalSexType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import java.util.List;

/**
 * Created by Rostyslav.Viner on 24.07.2015.
 */
public class AnimalSexTypeRepositoryImpl {
    private SqlSessionFactory sqlSessionFactory;

    public AnimalSexTypeRepositoryImpl() {
        sqlSessionFactory = new MyBatisConnectionFactory().getSqlSessionFactory();
    }

    /**
     * Returns the list of all Animal sex type instances from the database.
     * @return the list of all Animal sex type instances from the database.
     */
    public List<AnimalSexType> getAll(){

        SqlSession session = sqlSessionFactory.openSession();

        try {
            AnimalSexTypeRepository mapper = session.getMapper(AnimalSexTypeRepository.class);
            return mapper.getAll();
        } finally {
            session.close();
        }
    }

    /**
     * Returns an Animal sex type instance from the database.
     * @param id primary key value used for lookup.
     * @return An Animal sex type instance with a primary key value equals to pk. null if there is no matching row.
     */
    public AnimalSexType getById(int id){

        SqlSession session = sqlSessionFactory.openSession();

        try {
            AnimalSexTypeRepository mapper = session.getMapper(AnimalSexTypeRepository.class);
            return mapper.getById(id);
        } finally {
            session.close();
        }
    }
}
