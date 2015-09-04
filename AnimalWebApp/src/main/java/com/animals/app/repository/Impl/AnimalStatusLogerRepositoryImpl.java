package com.animals.app.repository.Impl;

import com.animals.app.domain.AnimalStatus;
import com.animals.app.repository.AnimalStatusLogerRepository;
import com.animals.app.repository.AnimalStatusRepository;
import com.animals.app.repository.MyBatisConnectionFactory;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import java.util.List;

/**
 * Created by Rostyslav.Viner on 25.08.2015.
 */
public class AnimalStatusLogerRepositoryImpl implements AnimalStatusLogerRepository {

    private SqlSessionFactory sqlSessionFactory;

    public AnimalStatusLogerRepositoryImpl() {
        sqlSessionFactory = new MyBatisConnectionFactory().getSqlSessionFactory();
    }

    /**
     * Returns an Animal status loger instance from the database.
     * @param id primary key value used for lookup.
     * @return An Animal status loger instance with a primary key value equals to pk. null if there is no matching row.
     */
    public AnimalStatus getById(long id) {
        SqlSession session = sqlSessionFactory.openSession();

        try {
            AnimalStatusLogerRepository mapper = session.getMapper(AnimalStatusLogerRepository.class);
            return mapper.getById(id);
        } finally {
            session.close();
        }
    }
}
