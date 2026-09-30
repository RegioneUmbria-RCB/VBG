package it.gruppoinit.pal.gp.core.features.manifestazioni.configurazione;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.dao.impl.BaseDAOImpl;
import it.gruppoinit.pal.gp.core.domain.MercatiD;
import it.gruppoinit.pal.gp.core.domain.MercatiDDisabilitati;
import it.gruppoinit.pal.gp.core.domain.MercatipresenzeT;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.utils.Utilities;

@Repository
public class MercatiDDisabilitatiDAOImpl extends BaseDAOImpl<MercatiDDisabilitati, PkId> implements MercatiDDisabilitatiDAO {

    @Override
    public Class<MercatiDDisabilitati> getEntityClass() {

	return MercatiDDisabilitati.class;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Integer> findByIdGiornata(Integer idGiornata) {

	if (idGiornata == null) {
	    throw new IllegalArgumentException(
		    "Impossibile recuperare la lista dei posteggi disabilitati senza passare il riferimento della giornata");
	}
	String sql = "select " + //
		"  mercati_d_disabilitati.fk_idposteggio " + //
		"from" + //
		"  mercatipresenze_t" + //
		"    inner join mercati_d_disabilitati on" + //
		"      mercatipresenze_t.idcomune = mercati_d_disabilitati.idcomune and" + //
		"      mercatipresenze_t.fkcodicemercato = mercati_d_disabilitati.fk_codicemercato and" + //
		"      mercati_d_disabilitati.dalla_data <= mercatipresenze_t.dataregistrazione and" + //
		"      mercati_d_disabilitati.alla_data >= mercatipresenze_t.dataregistrazione " + //
		"where" + //
		"  mercatipresenze_t.idcomune = ? and" + //
		"  mercatipresenze_t.id = ? " + //
		"order by" + //
		"  mercati_d_disabilitati.fk_idposteggio asc";
	SQLQuery query = getSession().createSQLQuery(sql) //
		.addSynchronizedEntityClass(MercatipresenzeT.class) //
		.addSynchronizedEntityClass(MercatiDDisabilitati.class);
	query.setString(0, ORMHelper.getIdcomune());
	query.setInteger(1, idGiornata);
	query.addScalar("fk_idposteggio", Hibernate.INTEGER);
	return query.list();
    }

    @SuppressWarnings("unchecked")
    @Override
    public MercatiDDisabilitati findByDataAndIdPosteggio(Date dataDiRiferimento, Integer idPosteggio) {

	DetachedCriteria det = getIdcomuneCriteria();
	det.add(Restrictions.le("dallaData", dataDiRiferimento));
	det.add(Restrictions.ge("allaData", dataDiRiferimento));
	det.add(Restrictions.eq("posteggio.id.codice", idPosteggio));
	List<MercatiDDisabilitati> disabilitazioni = getHibernateTemplate().findByCriteria(det);
	if (disabilitazioni.isEmpty()) {
	    return null;
	}
	if (disabilitazioni.size() > 1) {
	    throw new RuntimeException("Sono state trovate più disabilitazioni per il posteggio con id " +
		    idPosteggio +
		    " in data " +
		    Utilities.formatDate(dataDiRiferimento, false));
	}
	return disabilitazioni.get(0);
    }

    @Override
    public void disabilita(MercatipresenzeT giornata, MercatiD posteggio, String note) {

	if (giornata == null || giornata.getId() == null || giornata.getId().getCodice() == null || giornata.getDataRegistrazione() == null) {
	    throw new IllegalArgumentException("Impossibile disabilitare temporaneamente un posteggio senza passare la data di riferimento");
	}
	if (posteggio == null || posteggio.getId() == null || posteggio.getId().getCodice() == null) {
	    throw new IllegalArgumentException("Impossibile disabilitare temporaneamente un posteggio senza passare il posteggio di riferimento");
	}
	//1. Verifico la presenza di una riga per la giornata passata
	MercatiDDisabilitati rigaDisabilitata = this.findByDataAndIdPosteggio(giornata.getDataRegistrazione(), posteggio.getId().getCodice());
	//2. Se presente una disabilitazione per quel periodo, non faccio nulla
	if (rigaDisabilitata != null) {
	    return;
	}
	//3. Inserisco la riga
	MercatiDDisabilitati mdd = new MercatiDDisabilitati();
	mdd.setDallaData(giornata.getDataRegistrazione());
	mdd.setAllaData(giornata.getDataRegistrazione());
	mdd.setMercato(posteggio.getMercati());
	mdd.setPosteggio(posteggio);
	mdd.setNote(note);
	this.insert(mdd);
    }

    @Override
    public void abilita(MercatipresenzeT giornata, MercatiD posteggio) {

	if (giornata == null || giornata.getId() == null || giornata.getId().getCodice() == null || giornata.getDataRegistrazione() == null) {
	    throw new IllegalArgumentException("Impossibile riabilitare un posteggio senza passare la data di riferimento");
	}
	if (posteggio == null || posteggio.getId() == null || posteggio.getId().getCodice() == null) {
	    throw new IllegalArgumentException("Impossibile riabilitare un posteggio senza passare il posteggio di riferimento");
	}
	//1. Verifico la presenza di una riga per la giornata passata
	MercatiDDisabilitati rigaDisabilitata = this.findByDataAndIdPosteggio(giornata.getDataRegistrazione(), posteggio.getId().getCodice());
	//2. Se non è presente una disabilitazione per quel periodo, non faccio nulla
	if (rigaDisabilitata == null) {
	    return;
	}
	//3. Verifico se deve continuare ad essere disabilitato prima della data passata
	if (rigaDisabilitata.getDallaData().before(giornata.getDataRegistrazione())) {
	    MercatiDDisabilitati mdd = new MercatiDDisabilitati();
	    mdd.setDallaData(rigaDisabilitata.getDallaData());
	    Calendar cal = new GregorianCalendar();
	    cal.setTime(giornata.getDataRegistrazione());
	    cal.add(Calendar.DATE, -1);
	    mdd.setAllaData(cal.getTime());
	    mdd.setMercato(rigaDisabilitata.getMercato());
	    mdd.setPosteggio(rigaDisabilitata.getPosteggio());
	    mdd.setNote(rigaDisabilitata.getNote());
	    this.insert(mdd);
	}
	//4. Verifico se deve continuare ad essere disabilitato dopo della data passata
	if (rigaDisabilitata.getAllaData().after(giornata.getDataRegistrazione())) {
	    MercatiDDisabilitati mdd = new MercatiDDisabilitati();
	    Calendar cal = new GregorianCalendar();
	    cal.setTime(giornata.getDataRegistrazione());
	    cal.add(Calendar.DATE, 1);
	    mdd.setDallaData(cal.getTime());
	    mdd.setAllaData(rigaDisabilitata.getAllaData());
	    mdd.setMercato(rigaDisabilitata.getMercato());
	    mdd.setPosteggio(rigaDisabilitata.getPosteggio());
	    mdd.setNote(rigaDisabilitata.getNote());
	    this.insert(mdd);
	}
	//5. Cancello la disabilitazione trovata
	this.delete(rigaDisabilitata);
    }
}
