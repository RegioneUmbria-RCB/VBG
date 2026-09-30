package it.gruppoinit.pal.gp.core.features.commissioni;

import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.SQLQuery;
import org.hibernate.transform.IgnoreCaseAliasToBeanResultTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.dao.impl.BaseDAOImpl;
import it.gruppoinit.pal.gp.core.domain.Anagrafe;
import it.gruppoinit.pal.gp.core.domain.CommedilizieAppelloPratiche;
import it.gruppoinit.pal.gp.core.domain.CommedilizieConvocazioni;
import it.gruppoinit.pal.gp.core.domain.CommissioniedilizieR;
import it.gruppoinit.pal.gp.core.domain.CommissioniedilizieT;
import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.Istanzerichiedenti;
import it.gruppoinit.pal.gp.core.features.anagrafe.TipoAnagrafeEnum;
import it.gruppoinit.pal.gp.core.features.commissioni.appello.ICommissioniAppelloDAO;
import it.gruppoinit.pal.gp.core.features.commissioni.dettaglio.models.ElencoSoggettiIstanzaModel;
import it.gruppoinit.pal.gp.core.features.commissioni.dettaglio.models.SoggettoIstanzaModel;
import it.gruppoinit.pal.gp.core.features.commissioni.model.CommissioneListModel;

@SuppressWarnings("rawtypes")
@Repository
public class CommissioniDAOImpl extends BaseDAOImpl implements ICommissioniDAO {

    private ICommissioniAppelloDAO appelloDao;

    @Autowired
    public CommissioniDAOImpl(ICommissioniAppelloDAO appelloDao) {

	this.appelloDao = appelloDao;
    }

    @Override
    public Class getEntityClass() {

	return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public ElencoSoggettiIstanzaModel getElencoSoggettiIstanza(int idRiga) {

	CommissioniedilizieR riga = (CommissioniedilizieR) this.getById(CommissioniedilizieR.class, idRiga);
	Integer idCommissione = riga.getCommissioniedilizieT().getId().getCodice();
	Istanze istanza = riga.getMovimento().getIstanza();
	ElencoSoggettiIstanzaModel model = new ElencoSoggettiIstanzaModel();
	model.setCodiceIstanza(istanza.getId().getCodice());
	model.setData(istanza.getData());
	model.setDataProtocollo(istanza.getDataprotocollo());
	model.setNumeroIstanza(istanza.getNumeroistanza());
	model.setNumeroProtocollo(istanza.getNumeroprotocollo());
	//Richiedente
	String qualifica = istanza.getTipisoggetto() != null ? istanza.getTipisoggetto().getTiposoggetto() : "RICHIEDENTE";
	this.addRichiedenteAElencoIstanza(idCommissione, idRiga, istanza.getRichiedente(), qualifica, model);
	//Intermediario ( solo se pf )
	qualifica = "PROFESSIONISTA\\INTERMEDIARIO";
	this.addRichiedenteAElencoIstanza(idCommissione, idRiga, istanza.getProfessionista(), qualifica, model);
	//Soggetti collegati ( solo se pf )
	for (Istanzerichiedenti soggettoCollegato : istanza.getIstanzerichiedentis()) {
	    qualifica = soggettoCollegato.getTiposoggetto() != null ? soggettoCollegato.getTiposoggetto().getTiposoggetto()
		    : soggettoCollegato.getDescrsoggetto();
	    this.addRichiedenteAElencoIstanza(idCommissione, idRiga, soggettoCollegato.getRichiedente(), qualifica, model);
	}
	return model;
    }

    private void addRichiedenteAElencoIstanza(int idCommissione, int idRigaCommissione, Anagrafe anagrafica, String qualifica,
	    ElencoSoggettiIstanzaModel model) {

	if (anagrafica == null || !TipoAnagrafeEnum.F.value().equals(anagrafica.getTipoanagrafe())) {
	    return;
	}
	SoggettoIstanzaModel richiedente = new SoggettoIstanzaModel();
	richiedente.setCodiceAnagrafe(anagrafica.getId().getCodice());
	richiedente.setSoggetto(anagrafica.getDescrizioneRichiedente());
	richiedente.setQualifica(qualifica);
	richiedente.setAssociatoAPratica(this.soggettoGiaAssociatoAPratica(idRigaCommissione, richiedente.getCodiceAnagrafe()));
	if (!richiedente.isAssociatoAPratica()) {
	    Integer idAppello = this.appelloDao.soggettoPresenteInAppello(idCommissione, richiedente.getCodiceAnagrafe());
	    richiedente.setPresenteInAppello(idAppello != null);
	} else {
	    richiedente.setPresenteInAppello(true);
	}
	Integer idcarica = appelloDao.collegaCaricaByAppello(richiedente.getCodiceAnagrafe(), idCommissione);
	richiedente.setIdCarica(idcarica);
	model.getSoggetti().add(richiedente);
    }

    private boolean soggettoGiaAssociatoAPratica(int idRigaCommissione, int codiceAnagrafe) {

	String sql = "select " + "  count(commedilizie_appello_pratiche.id) as conteggio " + "from " + "  commedilizie_appello_pratiche " +
		     "    inner join commedilizie_appello on " + "      commedilizie_appello_pratiche.idcomune = commedilizie_appello.idcomune and " +
		     "      commedilizie_appello_pratiche.fk_appello = commedilizie_appello.id and " +
		     "      commedilizie_appello.codiceanagrafe = ? " + "where " + "  commedilizie_appello_pratiche.idcomune = ? and " +
		     "  commedilizie_appello_pratiche.fk_commedilizier = ?";
	SQLQuery query = getSession().createSQLQuery(sql).addSynchronizedEntityClass(CommedilizieAppelloPratiche.class);
	query.setInteger(0, codiceAnagrafe);
	query.setString(1, ORMHelper.getIdcomune());
	query.setInteger(2, idRigaCommissione);
	query.addScalar("conteggio", Hibernate.INTEGER);
	return new Integer(query.uniqueResult().toString()) > 0;
    }

    @Override
    public void updateConvocazione(Integer codiceCommissione, Integer codiceConvocazione) {

	String sql = "update commissioniedilizie_t set idconvocazione = ? where idcomune = ? and codicecommissione = ?";
	SQLQuery query = getSession().createSQLQuery(sql).addSynchronizedEntityClass(CommissioniedilizieT.class);
	query.setInteger(0, codiceConvocazione);
	query.setString(1, ORMHelper.getIdcomune());
	query.setInteger(2, codiceCommissione);
	query.executeUpdate();
    }

    @SuppressWarnings("unchecked")
    @Override
    public void updateDataOraByConvocazione(Integer codiceCommissione, Integer codiceConvocazione) {

	CommedilizieConvocazioni convocazione = (CommedilizieConvocazioni) this.getById(CommedilizieConvocazioni.class, codiceConvocazione);
	CommissioniedilizieT commissione = (CommissioniedilizieT) this.getById(CommissioniedilizieT.class, codiceCommissione);
	commissione.setData(convocazione.getDataconvocazione());
	commissione.setOrainizio(convocazione.getOraconvocazione());
	this.update(commissione);
    }

    @Override
    public List<CommissioneListModel> listaCommissioniPerOperatore(Integer codiceOperatore, Integer firstResult, Integer maxResults) {

	String sql = "select " + //
		     " codicecommissione as id " + //
		     " ,data as data " + //
		     " ,numprotocollo as numeroprotocollo " + //
		     " ,commissioniedilizie_t.descrizione as descrizione " + //
		     " ,coalesce(flagaperta,0) as aperta " + //
		     " ,commedilizie_tipologie.codcommtipologia as codicetipologia " + //
		     " ,commedilizie_tipologie.descrizione as tipologia " + //
		     " from  " + //
		     " commissioniedilizie_t  " + //
		     " left join commedilizie_tipologie on " + //
		     " commedilizie_tipologie.idcomune=commissioniedilizie_t.idcomune and " + //
		     " commedilizie_tipologie.codcommtipologia=commissioniedilizie_t.codcommtipologia " + //
		     "  " + //
		     " where  " + //
		     " commissioniedilizie_t.idcomune=? " + //
		     " and  " + //
		     " ( " + //
		     " exists " + //
		     "  (select 1    " + //
		     "    from commedilizie_tipol_ruoli " + //
		     "    where commedilizie_tipol_ruoli.fk_commeditipo_id = commissioniedilizie_t.codcommtipologia " + //
		     "    and commedilizie_tipol_ruoli.fk_ruoli_id in ( " + //
		     " select idruolo from responsabiliruoli  " + //
		     " where " + //
		     " responsabiliruoli.idcomune=commissioniedilizie_t.idcomune and " + //
		     " responsabiliruoli.codiceresponsabile=? " + //
		     "  " + //
		     "    ) " + //
		     "  ) " + //
		     " or not exists  " + //
		     " 	(select 1 " + //
		     "    from commedilizie_tipol_ruoli " + //
		     "    where  " + //
		     " commedilizie_tipol_ruoli.idcomune = commissioniedilizie_t.idcomune and " + //
		     " commedilizie_tipol_ruoli.fk_commeditipo_id = commissioniedilizie_t.codcommtipologia " + //
		     "  ) " + //
		     " ) " + //
		     " order by data desc";
	SQLQuery query = getSession().createSQLQuery(sql).addSynchronizedEntityClass(CommissioniedilizieT.class);
	query.setString(0, ORMHelper.getIdcomune());
	query.setInteger(1, codiceOperatore);
	query.addScalar("id", Hibernate.INTEGER);
	query.addScalar("data", Hibernate.DATE);
	query.addScalar("numeroprotocollo", Hibernate.STRING);
	query.addScalar("descrizione", Hibernate.STRING);
	query.addScalar("aperta", Hibernate.BOOLEAN);
	query.addScalar("codicetipologia", Hibernate.INTEGER);
	query.addScalar("tipologia", Hibernate.STRING);
	query.setResultTransformer(new IgnoreCaseAliasToBeanResultTransformer(CommissioneListModel.class));
	if (firstResult != null) {
	    query.setFirstResult(firstResult);
	}
	if (maxResults != null) {
	    query.setMaxResults(maxResults);
	}
	return query.list();
    }
}
