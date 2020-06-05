package ru.nik.testtask.repository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import ru.nik.testtask.model.Human;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.List;

/**
 * @author nik_aleks
 * @version 1.0
 */

@Slf4j
@Repository
@Transactional
public class CustomHumanRepositoryImpl implements CustomHumanRepository {

    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public List<Human> search(String pattern) {
        String lowerPattern = pattern.toLowerCase();
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Human> humanCriteriaQuery = cb.createQuery(Human.class);
        Root<Human> humanRoot = humanCriteriaQuery.from(Human.class);
        Predicate[] predicates = new Predicate[6];
        predicates[0] = cb.like(cb.lower(humanRoot.get("name")), "%" + lowerPattern);
        predicates[1] = cb.like(cb.lower(humanRoot.get("name")), lowerPattern + "%");
        predicates[2] = cb.like(cb.lower(humanRoot.get("name")), "%" + lowerPattern + "%");
        predicates[3] = cb.like(cb.lower(humanRoot.get("secondName")), "%" + lowerPattern);
        predicates[4] = cb.like(cb.lower(humanRoot.get("secondName")), lowerPattern + "%");
        predicates[5] = cb.like(cb.lower(humanRoot.get("secondName")), "%" + lowerPattern + "%");
        humanCriteriaQuery.select(humanRoot).where(cb.or(predicates));

        return entityManager.createQuery(humanCriteriaQuery).getResultList();
    }
}
