package com.animals.app.repository.Impl;

import com.animals.app.domain.Animal;
import com.animals.app.domain.User;
import com.animals.app.domain.UsersFilter;
import com.animals.app.repository.MyBatisConnectionFactory;
import com.animals.app.repository.UserRepository;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionException;
import org.apache.ibatis.session.SqlSessionFactory;

import java.util.List;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;

/**
 * Created by oleg on 24.07.2015.
 */
public class UserRepositoryImpl implements UserRepository {

    private SqlSessionFactory sqlSessionFactory;

    public UserRepositoryImpl() {
        sqlSessionFactory = new MyBatisConnectionFactory().getSqlSessionFactory();
    }

    /**
     * Insert an instance of User into the database.
     * @param user the instance to be persisted.
     */
    public void insert(User user) {
        SqlSession session = sqlSessionFactory.openSession();

        try {
            session.getMapper(UserRepository.class).insert(user);
            session.commit();
        } finally {
            session.close();
        }
    }

    /**
     * Update an instance of User in the database.
     * @param user the instance to be updated.
     */
    public void update(User user) {
        SqlSession session = sqlSessionFactory.openSession();

        try {
            session.getMapper(UserRepository.class).update(user);
            session.commit();
        } finally {
            session.close();
        }
    }

    /**
     * Delete an instance of User from the database.
     * @param id primary key value of the instance to be deleted.
     */
    public void delete(Integer id) {
        SqlSession session = sqlSessionFactory.openSession();

        try {
            session.getMapper(UserRepository.class).delete(id);
            session.commit();
        } finally {
            session.close();
        }
    }

    /**
     * Returns a User instance from the database.
     * @param id primary key value used for lookup.
     * @return A User instance with a primary key value equals to pk. null if there is no matching row.
     */
    public User getById(int id){
        SqlSession session = sqlSessionFactory.openSession();
        try {
            return session.getMapper(UserRepository.class).getById(id);
        } finally {
            session.close();
        }
    }

    /**
     * Returns a User instance from the database for admin animals list.
     * @param id primary key value used for lookup.
     * @return A User instance with a primary key value equals to pk. null if there is no matching row.
     */
    public User getByIdForAdminAnimalList(int id){
        SqlSession session = sqlSessionFactory.openSession();
        try {
            return session.getMapper(UserRepository.class).getByIdForAdminAnimalList(id);
        } finally {
            session.close();
        }
    }

    /**
     * Returns the list of all Users instances from the database.
     * @return the list of all Users instances from the database.
     */
    public List<User> getAll(){
        SqlSession session = sqlSessionFactory.openSession();
        try {
            return session.getMapper(UserRepository.class).getAll();
        } finally {
            session.close();
        }
    }
    
    /** created 41X
     * Returns a User instance from the database.
     * @param socialLogin and password primary key value used for lookup.
     * @return A User instance with a primary key value equals to pk. null if there is no matching row.
     */
    public User checkIfUserExistInDB(String socialLogin, String password){
        SqlSession session = sqlSessionFactory.openSession();
        try {
            return session.getMapper(UserRepository.class).checkIfUserExistInDB(socialLogin, password);
        } finally {
            session.close();
        }
    } 
    
    /** created 41X
     * Returns a String instance from the database.
     * @param socialLogin primary key value used for lookup.
     * @return A String with value equals to pk. null if there is no matching row.
     */
    public String checkIfUsernameUnique(String socialLogin){
        SqlSession session = sqlSessionFactory.openSession();
        try {
            return session.getMapper(UserRepository.class).checkIfUsernameUnique(socialLogin);
        } finally {
            session.close();
        }
    }
    
    /** 
     * Returns a User instance from the database.
     * @param googleId value used for lookup.
     * @return A User instance with a GoogleId value equals to pk. null if there is no matching row.
     */
    public User getByGoogleId(String googleId){
        SqlSession session = sqlSessionFactory.openSession();
        try {
            return session.getMapper(UserRepository.class).getByGoogleId(googleId);
        } finally {
            session.close();
        }
    }
    
    /** 
     * Returns a User instance from the database.
     * @param facebookId value used for lookup.
     * @return A User instance with a FacebookId value equals to pk. null if there is no matching row.
     */
    public User getByFacebookId(String facebookId){
        SqlSession session = sqlSessionFactory.openSession();
        try {
            return session.getMapper(UserRepository.class).getByFacebookId(facebookId);
        } finally {
            session.close();
        }
    }
    
    /** 
     * Returns a User instance from the database.
     * @param twitterId value used for lookup.
     * @return A User instance with a TwitterId value equals to pk. null if there is no matching row.
     */
    public User getByTwitterId(String twitterId){
        SqlSession session = sqlSessionFactory.openSession();
        try {
            return session.getMapper(UserRepository.class).getByTwitterId(twitterId);
        } finally {
            session.close();
        }
    }

    /**
     * Returns a User instance from the database for admin animals list.
     * @param id primary key value used for lookup.
     * @return A User instance with a primary key value equals to pk. null if there is no matching row.
     */
    public User getByIdMedicalHistory(long id) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            return session.getMapper(UserRepository.class).getByIdMedicalHistory(id);
        } finally {
            session.close();
        }
    }
    
    /** created 41X
     * Returns a User instance from the database.
     * @param socialLogin and emailVerificator primary key value used for lookup.
     * @return A User instance with a primary key value equals to pk. null if there is no matching row.
     */
    public User userVerification(String socialLogin, String emailVerificationString){
        SqlSession session = sqlSessionFactory.openSession();
        try {
            return session.getMapper(UserRepository.class).userVerification(socialLogin, emailVerificationString);
        } finally {
            session.close();
        }
    }

    /**
     * @return the list of all User instances from the database.
     */
    public List<User> getAdminUsers(UsersFilter usersFilter) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            return session.getMapper(UserRepository.class).getAdminUsers(usersFilter);
        } finally {
            session.close();
        }
    }

    /**
     * @return count of rows selected by getAdminUsersPaginator
     */
    public long getAdminUsersPaginator(UsersFilter usersFilter) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            return session.getMapper(UserRepository.class).getAdminUsersPaginator(usersFilter);
        } finally {
            session.close();
        }
    }
    
    /**
     * Returns count of rows selected from DB by method getAnimalByUserIdCount
     *
     * @return count of rows selected by getAnimalByUserIdCount
     */
	public long getAnimalByUserIdCount(long id) throws SqlSessionException{
        SqlSession session = sqlSessionFactory.openSession();
        try {
            return session.getMapper(UserRepository.class).getAnimalByUserIdCount(id);
        } finally {
            session.close();
        }
	}
    
    /**
     * Returns an Animal medical history instance from the database.
     * @param userId primary key value used for lookup.
     * @return An Animal medical history instance with a primary key value equals to pk. null if there is no matching row.
     */
    public List<Animal> getUserAnimals(long userId, long offset, int limit) {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            return session.getMapper(UserRepository.class).getUserAnimals(userId, offset, limit);
        } finally {
            session.close();
        }
    }
}
