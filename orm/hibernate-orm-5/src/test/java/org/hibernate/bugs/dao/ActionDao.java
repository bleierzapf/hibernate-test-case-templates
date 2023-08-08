package org.hibernate.bugs.dao;

import org.hibernate.bugs.entity.Action;
import org.hibernate.bugs.entity.Action_;
import org.hibernate.bugs.entity.Lead_;
import org.hibernate.bugs.entity.Location_;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class ActionDao {

    public List<Action> getActionList(EntityManager entityManager) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Action> query = cb.createQuery(Action.class);
        Root<Action> from = query.from(Action.class);

        from.join(Action_.LOCATION, JoinType.LEFT);
        from.join(Action_.LEAD, JoinType.LEFT);

        List<Predicate> predicates = new ArrayList<>();

        predicates.add(cb.or(
                cb.isTrue(from.get(Action_.location).get(Location_.active)),
                cb.isTrue(from.get(Action_.lead).get(Lead_.active))));

        query.where(predicates.toArray(new Predicate[0]));

        return entityManager.createQuery(query).getResultList();
    }
}
