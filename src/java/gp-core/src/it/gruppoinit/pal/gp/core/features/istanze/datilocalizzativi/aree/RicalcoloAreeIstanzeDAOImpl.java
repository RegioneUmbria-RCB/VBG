package it.gruppoinit.pal.gp.core.features.istanze.datilocalizzativi.aree;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.springframework.stereotype.Repository;

import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.dao.impl.BaseDAOImpl;
import it.gruppoinit.pal.gp.core.domain.RicalcoloAreeIstanze;
import it.gruppoinit.pal.gp.core.domain.RicalcoloAreeIstanzeId;
import it.gruppoinit.pal.gp.core.features.istanze.datilocalizzativi.aree.sottoscrittori.StatoElaborazioneEnum;

@Repository
public class RicalcoloAreeIstanzeDAOImpl extends BaseDAOImpl<RicalcoloAreeIstanze, RicalcoloAreeIstanzeId> implements RicalcoloAreeIstanzeDAO {

    @Override
    public Class<RicalcoloAreeIstanze> getEntityClass() {

	return RicalcoloAreeIstanze.class;
    }

    @Override
    public boolean existsRicalcoloInProgressIst(String uuidIstanza) {

	return !findRicalcoloInProgressIstP(uuidIstanza).isEmpty();
    }

    @Override
    public List<RicalcoloAreeIstanze> findRicalcoloInProgressIst(String uuidIstanza) {

	return findRicalcoloInProgressIstP(uuidIstanza);
    }

    @SuppressWarnings("unchecked")
    private List<RicalcoloAreeIstanze> findRicalcoloInProgressIstP(String uuidIstanza) {

	Query q = getSession().createQuery(
		"SELECT a FROM RicalcoloAreeIstanze a, RicalcoloAree b WHERE a.pk.idcomune = b.pk.idcomune AND b.pk.id = a.idRicalcoloAree AND a.pk.idcomune = :idcomune AND a.pk.istanzeUuid = :uuidIstanza AND b.stato = :stato");
	q.setParameter("idcomune", ORMHelper.getIdcomune());
	q.setParameter("uuidIstanza", uuidIstanza);
	q.setParameter("stato", StatoElaborazioneEnum.DA_ESEGUIRE.value());
	return q.list();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<String> getTestateDaRicalcolare() {

	/*
	 * Potremmo anche evitare di fare inner join sulla tabella RICALCOLO_AREE_ISTANZE, ma teniamola per essere sicuri
	 * che stiamo lavorando sulle istanze
	 */
	SQLQuery q = getSession().createSQLQuery("SELECT ID FROM RICALCOLO_AREE WHERE IDCOMUNE = :idcomune AND STATO = :stato");
	q.setParameter("idcomune", ORMHelper.getIdcomune());
	q.setParameter("stato", StatoElaborazioneEnum.DA_ESEGUIRE.value());
	return q.list();
    }

    @Override
    public void aggiungiIstanzaARicalcolo(String uuIdRicalcolo, String uuIdIstanza) {

	if (StringUtils.isBlank(uuIdRicalcolo)) {
	    throw new RuntimeException("Impossibile aggiungere l'istanza al ricalcolo delle aree senza aver passato la testata");
	}
	if (StringUtils.isBlank(uuIdIstanza)) {
	    throw new RuntimeException(
		    "Impossibile aggiungere l'istanza al ricalcolo delle aree senza aver passato l'identificativo UUID dell'istanza");
	}
	RicalcoloAreeIstanze ricalcoloAreeIstanze = new RicalcoloAreeIstanze();
	ricalcoloAreeIstanze.setPk(new RicalcoloAreeIstanzeId(uuIdIstanza));
	ricalcoloAreeIstanze.setIdRicalcoloAree(uuIdRicalcolo);
	this.insert(ricalcoloAreeIstanze);
    }
}
