package it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.abbonamento;

import java.math.BigDecimal;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Hibernate;
import org.hibernate.SQLQuery;
import org.hibernate.type.BigDecimalType;
import org.hibernate.type.IntegerType;
import org.hibernate.type.StringType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.paevolution.ws.pagamenti_types.StatoPagamentoType;

import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.dao.impl.BaseDAOImpl;
import it.gruppoinit.pal.gp.core.domain.Autorizzazioni;
import it.gruppoinit.pal.gp.core.domain.Borsellino;
import it.gruppoinit.pal.gp.core.domain.BorsellinoAutorizzazioni;
import it.gruppoinit.pal.gp.core.domain.BorsellinoAutorizzazioniId;
import it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.abbonamento.exceptions.BorsellinoException;
import it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.abbonamento.movimenti.TipoEnum;
import it.gruppoinit.pal.gp.core.features.manifestazioni.rest.ambulanti.model.EsitoOperazioneAggiornamento;
import it.gruppoinit.pal.gp.core.features.nodopagamenti.StatiPosizioniDebitorieConverter;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;

@Repository
public class BorsellinoAutorizzazioniDAOImpl extends BaseDAOImpl<BorsellinoAutorizzazioni, BorsellinoAutorizzazioniId>
	implements IBorsellinoAutorizzazioniDAO {

    private StatiPosizioniDebitorieConverter c = new StatiPosizioniDebitorieConverter();
    private IBorsellinoDAO borsellinoDAO;

    @Autowired
    public void setBorsellinoDAO(IBorsellinoDAO borsellinoDAO) {

	this.borsellinoDAO = borsellinoDAO;
    }

    @Override
    public Class<BorsellinoAutorizzazioni> getEntityClass() {

	return BorsellinoAutorizzazioni.class;
    }

    @Override
    public boolean isBorsellinoAttivo(Integer idAutorizzazione) {

	if (idAutorizzazione == null) {
	    throw new IllegalArgumentException(
		    "Impossibile verificare la presenza di un borsellino senza passare il riferimento dell'autorizzazione coinvolta");
	}
	String sql = "select" + //
		" count(*) as conteggio " + //
		"from" + //
		" borsellino_autorizzazioni" + //
		"   inner join borsellino on " + //
		"     borsellino_autorizzazioni.idcomune = borsellino.idcomune and " + //
		"     borsellino_autorizzazioni.fkid_borsellino = borsellino.id and " + //
		"     borsellino.stato = ? " + //
		"where" + //
		" borsellino_autorizzazioni.idcomune = ? and" + //
		" borsellino_autorizzazioni.fkid_autorizzazioni = ?";
	SQLQuery q = getSession().createSQLQuery(sql);
	q.setParameter(0, StatoBorsellinoEnum.ATTIVO.toString(), new StringType());
	q.setParameter(1, ORMHelper.getIdcomune(), new StringType());
	q.setParameter(2, idAutorizzazione, new IntegerType());
	q.addScalar("conteggio", Hibernate.INTEGER);
	return Integer.parseInt(q.list().get(0).toString()) > 0;
    }

    @Override
    public Integer findBorsellinoAttivo(Integer idAutorizzazione) {

	if (idAutorizzazione == null) {
	    throw new IllegalArgumentException(
		    "Impossibile risalire al borsellino attivo senza passare il riferimento dell'autorizzazione coinvolta");
	}
	String sql = "select" + //
		" borsellino.id " + //
		"from" + //
		" borsellino_autorizzazioni" + //
		"   inner join borsellino on " + //
		"     borsellino_autorizzazioni.idcomune = borsellino.idcomune and " + //
		"     borsellino_autorizzazioni.fkid_borsellino = borsellino.id and " + //
		"     borsellino.stato = ? " + //
		"where" + //
		" borsellino_autorizzazioni.idcomune = ? and" + //
		" borsellino_autorizzazioni.fkid_autorizzazioni = ?";
	SQLQuery q = getSession().createSQLQuery(sql);
	q.setParameter(0, StatoBorsellinoEnum.ATTIVO.toString(), new StringType());
	q.setParameter(1, ORMHelper.getIdcomune(), new StringType());
	q.setParameter(2, idAutorizzazione, new IntegerType());
	q.addScalar("id", Hibernate.INTEGER);
	if (q.list().isEmpty()) {
	    return null;
	}
	if (q.list().size() > 1) {
	    throw new RuntimeException(
		    "Situazione anomala: ci sono " + q.list().size() + " borsellini attivi per l'autorizzazione con id " + idAutorizzazione);
	}
	return Integer.parseInt(q.list().get(0).toString());
    }

    @Override
    public BigDecimal proiezione(Integer idBorsellino, BigDecimal importo) {

	if (idBorsellino == null) {
	    throw new IllegalArgumentException("Impossibile effettuare una proiezione senza passare il borsellino di riferimento");
	}
	String sql = "SELECT" + //
		"  coalesce(SUM(IMPORTO),0) - ? as residuo " + //
		" FROM" + //
		"  (" + //
		"    " + //  RICARICHE CON POSIZIONE DEBITORIA" + //
		"  SELECT" + //
		"      TIPO," + //
		"      borsellino_movimenti.IMPORTO" + //
		"    FROM" + //
		"      borsellino_movimenti" + //
		"      INNER JOIN DETT_POSIZIONE_DEBITORIA ON DETT_POSIZIONE_DEBITORIA.IDCOMUNE = borsellino_movimenti.IDCOMUNE" + //
		"      AND DETT_POSIZIONE_DEBITORIA.ID = borsellino_movimenti.FKID_DETTPOSIZIONEDEBITORIA" + //
		"    WHERE" + //
		"      borsellino_movimenti.idcomune = ?" + //
		"      AND borsellino_movimenti.FKID_BORSELLINO = ?" + //
		"      AND borsellino_movimenti.TIPO = ?" + // 'RICARICA'
		"      AND DETT_POSIZIONE_DEBITORIA.STATO IN(" + //
		"        ?," + //
		"        ?," + //
		"        ?" + //
		"      )" + //
		"     " + //
		"    UNION ALL" + //
		"    " + // RICARICHE SENZA POSIZIONE DEBITORIA (DA BACKOFICE)" + //
		"    SELECT" + //
		"      TIPO," + //
		"      borsellino_movimenti.IMPORTO" + //
		"    FROM" + //
		"      borsellino_movimenti" + //
		"    WHERE" + //	      
		"      borsellino_movimenti.idcomune = ?" + //
		"      AND borsellino_movimenti.FKID_BORSELLINO = ?" + //
		"      AND borsellino_movimenti.TIPO = ?" + // 'RICARICA'
		"      AND Borsellino_movimenti.FKID_DETTPOSIZIONEDEBITORIA IS NULL" + //
		"     " + //
		"    UNION ALL" + //
		"   " + //
		"    " + // MOVIMENTI USCITA / STORNO" + //
		"   " + //
		"    SELECT" + //
		"      TIPO," + //
		"      borsellino_movimenti.IMPORTO" + //
		"    FROM" + //
		"      borsellino_movimenti" + //
		"    WHERE" + //
		"      borsellino_movimenti.idcomune = ?" + //
		"      AND borsellino_movimenti.FKID_BORSELLINO = ?" + //
		"      AND borsellino_movimenti.TIPO IN (?, ?, ?)" + // USCITA/STORNO/RIMBORSO
		"   " + //
		"  ) T1 ";
	SQLQuery q = getSession().createSQLQuery(sql);
	q.setParameter(0, importo, new BigDecimalType());
	q.setParameter(1, ORMHelper.getIdcomune(), new StringType());
	q.setParameter(2, idBorsellino, new IntegerType());
	q.setParameter(3, TipoEnum.RICARICA.name(), new StringType());
	int pos = 4;
	// stato ??? PAGATO_OFFLINE_ANNULLATO NOTIFICATO_DA_PSP RENDICONTATO_DA_IC
	for (StatoPagamentoType statoPagamentoType : c.getStatiPosizioniChiusePositivamente()) {
	    q.setParameter(pos++, statoPagamentoType.name(), new StringType());
	}
	q.setParameter(pos++, ORMHelper.getIdcomune(), new StringType());
	q.setParameter(pos++, idBorsellino, new IntegerType());
	q.setParameter(pos++, TipoEnum.RICARICA.name(), new StringType());
	//		idcomune
	//		idborsellino
	//		tipo USCITA/STORNO
	q.setParameter(pos++, ORMHelper.getIdcomune(), new StringType());
	q.setParameter(pos++, idBorsellino, new IntegerType());
	q.setParameter(pos++, TipoEnum.USCITA.name(), new StringType());
	q.setParameter(pos++, TipoEnum.STORNO.name(), new StringType());
	q.setParameter(pos++, TipoEnum.RIMBORSO.name(), new StringType());
	q.addScalar("residuo", Hibernate.BIG_DECIMAL);
	List<BigDecimal> list = q.list();
	if (list.isEmpty()) {
	    return BigDecimal.ZERO;
	}
	return list.get(0);
    }

    @Override
    public List<BorsellinoAutorizzazioni> findAutorizzazioniByBorsellino(Integer idBorsellino) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("id.fkIdBorsellino", idBorsellino, Integer.class));
	ft.addRestriction(fr);
	return findByFilterTable(ft);
    }

    @Override
    public List<BorsellinoAutorizzazioni> findByIdAutorizzazione(Integer idAutorizzazione) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("id.fkIdAutorizzazioni", idAutorizzazione, Integer.class));
	ft.addRestriction(fr);
	return findByFilterTable(ft);
    }

    @Override
    public EsitoOperazioneAggiornamento collegaAutorizzazione(String uuidBorsellino, Integer idAutorizzazione) throws BorsellinoException {

	if (StringUtils.isBlank(uuidBorsellino)) {
	    throw new IllegalArgumentException("Impossibile collegare un'autorizzazione al borsellino senza passare l'identificativo del borsellino");
	}
	if (idAutorizzazione == null) {
	    throw new IllegalArgumentException(
		    "Impossibile collegare un'autorizzazione al borsellino senza passare l'identificativo dell'autorizzazione");
	}
	Borsellino b = this.borsellinoDAO.findByUuid(uuidBorsellino);
	borsellinoDAO.refreshEntity(b);
	BorsellinoAutorizzazioniId bautId = new BorsellinoAutorizzazioniId(ORMHelper.getIdcomune(), b.getId().getCodice(), idAutorizzazione);
	BorsellinoAutorizzazioni autDaAggiungere = this.findById(bautId);
	if (autDaAggiungere != null) {
	    return new EsitoOperazioneAggiornamento(false, "Autorizzazione gia' presente");
	}
	Integer idBorsellinoAttivo = this.findBorsellinoAttivo(idAutorizzazione);
	if (idBorsellinoAttivo != null && !idBorsellinoAttivo.equals(b.getId().getCodice())) {
	    return new EsitoOperazioneAggiornamento(false, "Autorizzazione gia' presente in altro Borsellino");
	}
	BorsellinoAutorizzazioni aut = new BorsellinoAutorizzazioni();
	aut.setId(bautId);
	aut.setAutorizzazione(new Autorizzazioni(idAutorizzazione));
	aut.setBorsellino(b);
	this.insert(aut);
	return new EsitoOperazioneAggiornamento(true, "Autorizzazione aggiunta");
    }

    @Override
    public void scollegaAutorizzazione(Integer idAutorizzazione) {

	if (idAutorizzazione == null) {
	    throw new IllegalArgumentException("Impossibile scollegare un'autorizzazione dal borsellino senza passare l'id dell'autorizzazione");
	}
	String sql = "delete from borsellino_autorizzazioni where idcomune = ? and fkid_autorizzazioni = ?";
	SQLQuery query = getSession().createSQLQuery(sql).addSynchronizedEntityClass(Borsellino.class)
		.addSynchronizedEntityClass(BorsellinoAutorizzazioni.class);
	query.setString(0, ORMHelper.getIdcomune());
	query.setInteger(1, idAutorizzazione);
	query.executeUpdate();
    }
}
