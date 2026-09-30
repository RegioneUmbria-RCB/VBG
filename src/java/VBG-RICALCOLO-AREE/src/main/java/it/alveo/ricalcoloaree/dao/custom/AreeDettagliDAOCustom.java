package it.alveo.ricalcoloaree.dao.custom;

import java.math.BigDecimal;
import java.util.List;

import it.alveo.ricalcoloaree.entities.Aree;
import it.alveo.ricalcoloaree.entities.AreeDettagli;
import it.alveo.ricalcoloaree.entities.Stradario;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public class AreeDettagliDAOCustom {

    public static List<AreeDettagli> findByCustomCriteriaCivico(String idcomune, int codiceStradario, int civico, EntityManager entityManager) {

	CriteriaBuilder cb = entityManager.getCriteriaBuilder();
	CriteriaQuery<AreeDettagli> query = cb.createQuery(AreeDettagli.class);
	Root<AreeDettagli> root = query.from(AreeDettagli.class);
	Join<AreeDettagli, Stradario> stradarioJoin = root.join("stradario");
	Join<AreeDettagli, Aree> areeJoin = root.join("aree");
	int idx = 0;
	Predicate[] finalPredicates;
	finalPredicates = new Predicate[5];
	finalPredicates[idx++] = cb.equal(root.get("idcomune"), idcomune);
	finalPredicates[idx++] = cb.equal(root.get("codicestradario"), codiceStradario);
	finalPredicates[idx++] = cb.ge(root.get("civicoA"), civico);
	finalPredicates[idx++] = cb.le(root.get("civicoDa"), civico);
	finalPredicates[idx++] = cb.or(cb.equal(areeJoin.get("comune").get("codicecomune"), stradarioJoin.get("comune").get("codicecomune")),
		cb.isNull(stradarioJoin.get("comune").get("codicecomune")));
	Predicate finalPredicate = cb.and(finalPredicates);
	query.select(root).where(finalPredicate).orderBy(cb.asc(stradarioJoin.get("descrizione")));
	return entityManager.createQuery(query).getResultList();
    }

    public static List<AreeDettagli> findByCustomCriteriaKm(String idcomune, Integer codiceStradario, BigDecimal km, EntityManager entityManager) {

	CriteriaBuilder cb = entityManager.getCriteriaBuilder();
	CriteriaQuery<AreeDettagli> query = cb.createQuery(AreeDettagli.class);
	Root<AreeDettagli> root = query.from(AreeDettagli.class);
	Join<AreeDettagli, Stradario> stradarioJoin = root.join("stradario");
	Join<AreeDettagli, Aree> areeJoin = root.join("aree");
	int idx = 0;
	Predicate[] finalPredicates;
	finalPredicates = new Predicate[5];
	finalPredicates[idx++] = cb.equal(root.get("idcomune"), idcomune);
	finalPredicates[idx++] = cb.equal(root.get("codicestradario"), codiceStradario);
	finalPredicates[idx++] = cb.ge(root.get("kmA"), km);
	finalPredicates[idx++] = cb.le(root.get("kmDa"), km);
	finalPredicates[idx++] = cb.or(cb.equal(areeJoin.get("comune").get("codicecomune"), stradarioJoin.get("comune").get("codicecomune")),
		cb.isNull(stradarioJoin.get("comune").get("codicecomune")));
	Predicate finalPredicate = cb.and(finalPredicates);
	query.select(root).where(finalPredicate).orderBy(cb.asc(stradarioJoin.get("descrizione")));
	return entityManager.createQuery(query).getResultList();
    }

    public static List<AreeDettagli> findByCustomCriteriaStradario(String idcomune, int codiceStradario, EntityManager entityManager) {

	CriteriaBuilder cb = entityManager.getCriteriaBuilder();
	CriteriaQuery<AreeDettagli> query = cb.createQuery(AreeDettagli.class);
	Root<AreeDettagli> root = query.from(AreeDettagli.class);
	Join<AreeDettagli, Stradario> stradarioJoin = root.join("stradario");
	Join<AreeDettagli, Aree> areeJoin = root.join("aree");
	int idx = 0;
	Predicate[] finalPredicates;
	finalPredicates = new Predicate[3];
	finalPredicates[idx++] = cb.equal(root.get("idcomune"), idcomune);
	finalPredicates[idx++] = cb.equal(root.get("codicestradario"), codiceStradario);
	finalPredicates[idx++] = cb.or(cb.equal(areeJoin.get("comune").get("codicecomune"), stradarioJoin.get("comune").get("codicecomune")),
		cb.isNull(stradarioJoin.get("comune").get("codicecomune")));
	Predicate finalPredicate = cb.and(finalPredicates);
	query.select(root).where(finalPredicate).orderBy(cb.asc(stradarioJoin.get("descrizione")));
	return entityManager.createQuery(query).getResultList();
    }
}
