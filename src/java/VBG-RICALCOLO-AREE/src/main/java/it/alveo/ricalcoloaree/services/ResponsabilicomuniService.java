package it.alveo.ricalcoloaree.services;

import java.util.List;

import org.springframework.stereotype.Service;

import it.alveo.ricalcoloaree.entities.Responsabili;
import it.alveo.ricalcoloaree.entities.Responsabilicomuni;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;

@Service
public class ResponsabilicomuniService {

    public List<Responsabilicomuni> findByOperatore(Responsabili responsabile, EntityManager em) {

	String qry = "SELECT p FROM Responsabilicomuni p " + "WHERE p.id.idcomune = :idcomune " + "AND p.id.codiceresponsabile = :codiceResponsabile";
	Query query = em.createQuery(qry);
	query.setParameter("idcomune", responsabile.getIdcomune());
	query.setParameter("codiceResponsabile", responsabile.getCodiceresponsabile());
	return query.getResultList();
	//return responsabilicomuniDAO.findByOperatore(responsabile);
	//return responsabiliComuniRepository.findByOnIdComuneAndCodiceResponsabile(responsabile.getIdcomune(),responsabile.getCodiceresponsabile());
    }
}
