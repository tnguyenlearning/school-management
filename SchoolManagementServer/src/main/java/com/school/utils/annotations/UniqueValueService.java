package com.school.utils.annotations;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Service
public class UniqueValueService {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
    public boolean isValueUnique(String fieldValue, String fieldName, Class<?> entityClass) {
        String queryStr = "SELECT COUNT(e) FROM " + entityClass.getSimpleName() + " e WHERE e." + fieldName + " = :fieldValue";
        var query = entityManager.createQuery(queryStr);
        query.setParameter("fieldValue", fieldValue);

        Long count = (Long) query.getSingleResult();
        return count == 0;
    }
}
