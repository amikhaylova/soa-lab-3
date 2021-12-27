package service.oriented.architecture.lab2_refactored.repository;

import org.hibernate.Session;
import service.oriented.architecture.lab2_refactored.entity.Person;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class PersonRepositoryImpl implements PersonRepositoryCustom {
    @PersistenceContext
    private EntityManager em;

    private Session getSession() {
        return em.unwrap(Session.class);
    }


    @Override
    public List<Person> getPersonsLessThan(Integer id) {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        javax.persistence.Query singleQuery = em.createQuery("SELECT c FROM Person c WHERE c.id = ?1", Person.class);
        Person person = (Person) singleQuery.setParameter(1, id).getSingleResult();
        CriteriaQuery<Person> criteriaQueryPerson = criteriaBuilder.createQuery(Person.class);
        Root<Person> root = criteriaQueryPerson.from(Person.class);
        criteriaQueryPerson.select(root).where(criteriaBuilder.lessThan(root.get("name"), person.getName()));
        org.hibernate.query.Query<Person> query = getSession().createQuery(criteriaQueryPerson);
        return query.getResultList();
    }
}
