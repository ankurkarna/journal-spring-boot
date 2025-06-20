package com.karna.ankur.Journal.Repository;

import com.karna.ankur.Journal.Entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

public class UserRepositoryImpl {
    @Autowired
    private MongoTemplate mongoTemplate;

    public List<UserEntity> getUserForSA() {

        Query query = new Query();
//    Criteria criteria = new Criteria();
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        query.addCriteria(Criteria.where("email").regex(emailRegex));
        query.addCriteria(Criteria.where("sentimentAnalysis").is(true));
        List<UserEntity> userEntities = mongoTemplate.find(query, UserEntity.class);
        return userEntities;

    }
}
