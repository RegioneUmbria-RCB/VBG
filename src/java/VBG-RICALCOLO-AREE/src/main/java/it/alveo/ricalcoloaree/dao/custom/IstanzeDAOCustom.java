package it.alveo.ricalcoloaree.dao.custom;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import it.alveo.ricalcoloaree.entities.Istanze;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public class IstanzeDAOCustom {

    public static List<Istanze> findIstanzeByFilters(String idcomune, String software, Date dataDA, Date dataA, EntityManager entityManager) {

	// Ottieni il CriteriaBuilder dall'EntityManager
	CriteriaBuilder cb = entityManager.getCriteriaBuilder();
	// Crea una query Criteria per la classe Employee
	CriteriaQuery<Istanze> query = cb.createQuery(Istanze.class);
	// La radice della query (la tabella)
	Root<Istanze> istanze = query.from(Istanze.class);
	List<Predicate> finalPredicates = new ArrayList<Predicate>();
	finalPredicates.add(cb.equal(istanze.get("pkId").get("idcomune"), idcomune));
	if (software != null && !software.trim().isEmpty()) {
	    finalPredicates.add(cb.equal(istanze.get("software"), software));
	}
	if (dataDA != null) {
	    finalPredicates.add(cb.greaterThanOrEqualTo(istanze.get("data"), dataDA));
	}
	if (dataA != null) {
	    finalPredicates.add(cb.lessThanOrEqualTo(istanze.get("data"), dataA));
	}
	Predicate finalPredicate = cb.and(finalPredicates.toArray(new Predicate[0]));
	query.select(istanze).where(finalPredicate).orderBy(cb.desc(istanze.get("pkId").get("idcomune")), cb.desc(istanze.get("codicecomune")));
	// Esegui la query
	return entityManager.createQuery(query).getResultList();
    }
}
