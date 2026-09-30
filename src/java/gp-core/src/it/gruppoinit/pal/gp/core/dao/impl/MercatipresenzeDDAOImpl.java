package it.gruppoinit.pal.gp.core.dao.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.engine.SessionFactoryImplementor;
import org.hibernate.transform.IgnoreCaseAliasToBeanResultTransformer;
import org.hibernate.transform.Transformers;
import org.hibernate.type.Type;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import it.gruppoinit.pal.gp.core.dao.MercatiConfigurazioneDAO;
import it.gruppoinit.pal.gp.core.dao.MercatipresenzeDDAO;
import it.gruppoinit.pal.gp.core.dao.MercatipresenzeTDAO;
import it.gruppoinit.pal.gp.core.dao.helper.ListaAutorizzazioniPerPeriodo;
import it.gruppoinit.pal.gp.core.dao.helper.MercatiPresenzeDTO;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Autorizzazioni;
import it.gruppoinit.pal.gp.core.domain.BorsellinoMovimenti;
import it.gruppoinit.pal.gp.core.domain.DettPosizioneDebitoria;
import it.gruppoinit.pal.gp.core.domain.Mercati;
import it.gruppoinit.pal.gp.core.domain.MercatiConfigurazione;
import it.gruppoinit.pal.gp.core.domain.MercatiD;
import it.gruppoinit.pal.gp.core.domain.MercatiUso;
import it.gruppoinit.pal.gp.core.domain.MercatipresenzeD;
import it.gruppoinit.pal.gp.core.domain.MercatipresenzeT;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.helper.MercatipresenzeDDTO;
import it.gruppoinit.pal.gp.core.domain.helper.MercatipresenzeDPagamentiDTO;
import it.gruppoinit.pal.gp.core.domain.web.ChiaveValoreBean;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.model.PosteggioPerAutBean;
import it.gruppoinit.pal.gp.core.features.bollettazione.gestione.ValoriLivelloServizio;
import it.gruppoinit.pal.gp.core.features.bollettazione.gestione.dao.QueryMercatiDLivelliServizioHelper;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.manifestazioni.model.DettaglioPresenzaComunicazioneModel;
import it.gruppoinit.pal.gp.core.features.database.DialettoEnum;
import it.gruppoinit.pal.gp.core.features.manifestazioni.calendario.MercatiPresenzeConPosDebBean;
import it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.abbonamento.movimenti.TipoEnum;
import it.gruppoinit.pal.gp.core.features.manifestazioni.presenze.MercatipresenzeDBean;
import it.gruppoinit.pal.gp.core.features.manifestazioni.utils.PresenzeNonPagateBean;
import it.gruppoinit.pal.gp.core.service.exception.BusinessValidationException;
import it.gruppoinit.pal.gp.core.service.helper.PresenzeDaConsolidareHelper;
import it.gruppoinit.pal.gp.core.utils.Utilities;

@Repository
public class MercatipresenzeDDAOImpl extends BaseDAOImpl<MercatipresenzeD, PkId> implements MercatipresenzeDDAO {

    private static final Logger log = LoggerFactory.getLogger(MercatipresenzeDDAOImpl.class);
    @Autowired
    private MercatipresenzeTDAO mercatipresenzeTDAO;
    @Autowired
    private MercatiConfigurazioneDAO mercatiConfigurazioneDAO;

    @Override
    public Class<MercatipresenzeD> getEntityClass() {

	return MercatipresenzeD.class;
    }

    @Override
    @SuppressWarnings("unchecked")
    public MercatipresenzeD findByMercatiPresenzeTAndPosteggio(Integer idGiornaMercato, Integer idPosteggio) {

	DetachedCriteria criteria = getIdcomuneCriteria();
	criteria.add(Restrictions.eq("mercatiPresenzeT.id.codice", idGiornaMercato));
	criteria.add(Restrictions.eq("posteggio.id.codice", idPosteggio));
	List<MercatipresenzeD> list = getHibernateTemplate().findByCriteria(criteria, 0, 1);
	if (list.isEmpty()) {
	    return null;
	}
	return list.get(0);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<MercatipresenzeD> findListaPresentiSenzaPosteggio(MercatipresenzeT giorno) {

	DetachedCriteria criteria = getIdcomuneCriteria();
	// pongo le condizioni di where
	criteria.add(Restrictions.eq("mercatiPresenzeT.id.codice", giorno.getId().getCodice()));
	// left join con MercatiD
	criteria.createAlias("posteggio", "_posteggio", Criteria.LEFT_JOIN);
	// no posteggio
	criteria.add(Restrictions.isNull("_posteggio.id.codice"));
	List<MercatipresenzeD> list = getHibernateTemplate().findByCriteria(criteria);
	return list;
    }

    @SuppressWarnings("unchecked")
    private List<MercatipresenzeDDTO> _findListaPosteggi(Integer idGiornata, List<Integer> identificativiPosteggio) {

	DetachedCriteria criteria = getIdcomuneCriteria();
	// pongo le condizioni di where
	criteria.add(Restrictions.eq("mercatiPresenzeT.id.codice", idGiornata));
	//
	criteria.createAlias("posteggio", "_posteggio");
	if (identificativiPosteggio != null && identificativiPosteggio.size() > 0) {
	    Integer[] itemsArray = new Integer[identificativiPosteggio.size()];
	    itemsArray = identificativiPosteggio.toArray(itemsArray);
	    criteria.add(Restrictions.in("_posteggio.id.codice", itemsArray));
	}
	// solo gli abilitati
	criteria.add(Restrictions.ne("_posteggio.disabilitato", Boolean.TRUE));
	criteria.createAlias("_posteggio.tipoSpazio", "_posteggioTipoSpazio", Criteria.LEFT_JOIN);
	criteria.createAlias("_posteggio.posteggiSettori", "_posteggiSettori", Criteria.LEFT_JOIN);
	//
	criteria.createAlias("posteggioRinunciato", "_posteggioRinunciato", Criteria.LEFT_JOIN);
	//
	criteria.createAlias("occupante", "_occupante", Criteria.LEFT_JOIN);
	criteria.createAlias("_occupante.formagiuridica", "_occupanteFormaGiuridica", Criteria.LEFT_JOIN);
	//
	criteria.createAlias("concessionario", "_concessionario", Criteria.LEFT_JOIN);
	criteria.createAlias("_concessionario.formagiuridica", "_concessionarioFormaGiuridica", Criteria.LEFT_JOIN);
	//
	criteria.createAlias("autorizzazioni", "_aut", Criteria.LEFT_JOIN);
	criteria.createAlias("_aut.autorizcomune", "_autAutorizComune", Criteria.LEFT_JOIN);
	criteria.createAlias("_aut.tipologiaregistro", "_autTipologiaRegistro", Criteria.LEFT_JOIN);
	// dati anagrafe autorizzazione
	criteria.createAlias("_aut.anagrafe", "_titAut", Criteria.LEFT_JOIN);
	criteria.createAlias("_aut.occupante", "_occupAut", Criteria.LEFT_JOIN);
	// left join aut_csi
	criteria.createAlias("_aut.autorizzazioniCsis", "_autorizzazioniCsis", Criteria.LEFT_JOIN);
	criteria.createAlias("_autorizzazioniCsis.anagrafe", "_gerente", Criteria.LEFT_JOIN);
	criteria.createAlias("_autorizzazioniCsis.autPrecedenteComune", "_autPrecCom", Criteria.LEFT_JOIN);
	criteria.createAlias("_gerente.formagiuridica", "_gerenteformagiuridica", Criteria.LEFT_JOIN);
	//
	criteria.createAlias("autorizzazioneConcessionarioAssente", "_autConcAssente", Criteria.LEFT_JOIN);
	criteria.createAlias("_autConcAssente.autorizcomune", "_autConcAssenteAutorizComune", Criteria.LEFT_JOIN);
	criteria.createAlias("_autConcAssente.tipologiaregistro", "_autConcAssenteTipologiaRegistro", Criteria.LEFT_JOIN);
	// dati anagrafe concessione
	criteria.createAlias("_autConcAssente.anagrafe", "_titConc", Criteria.LEFT_JOIN);
	criteria.createAlias("_autConcAssente.occupante", "_occupConc", Criteria.LEFT_JOIN);
	// left join comune_aut_originale ( singolo campo della tabella autororizzazione)
	criteria.createAlias("_autConcAssente.autorigComune", "_autorigComune", Criteria.LEFT_JOIN);
	//.
	// left join aut_csi concessionari
	criteria.createAlias("_autConcAssente.autorizzazioniCsis", "_autConcCsis", Criteria.LEFT_JOIN);
	criteria.createAlias("_autConcCsis.anagrafe", "_gerenteConc", Criteria.LEFT_JOIN);
	criteria.createAlias("_gerenteConc.formagiuridica", "_gerenteConformagiuridica", Criteria.LEFT_JOIN);
	// .. left join collaboratore (FK_CODICECOLLABORATORE)
	criteria.createAlias("collaboratore", "_collaboratore", Criteria.LEFT_JOIN);
	criteria.createAlias("_collaboratore.formagiuridica", "_collaboratoreFormaGiuridica", Criteria.LEFT_JOIN);
	//..
	criteria.createAlias("mercatiSpunte", "_mercatiSpunte", Criteria.LEFT_JOIN);
	// GERENTI DELLO SPUNTISTA E DEL CONCESSIONARIO DELLA GIORNATA
	criteria.createAlias("gerenteSpuntista", "_gerentePresD", Criteria.LEFT_JOIN);
	criteria.createAlias("_gerentePresD.formagiuridica", "_gerentePresDFormaGiuridica", Criteria.LEFT_JOIN);
	criteria.createAlias("gerenteConcessionario", "_gerenteConcPresD", Criteria.LEFT_JOIN);
	criteria.createAlias("_gerenteConcPresD.formagiuridica", "_gerenteConcPresDFormaGiuridica", Criteria.LEFT_JOIN);
	// order by codiceposteggio
	criteria.addOrder(Order.asc("_posteggio.codiceposteggio"));
	//
	ProjectionList plist = Projections.projectionList();
	plist.add(Projections.property("id.codice"), "ID_CODICE");
	plist.add(Projections.property("flagAssenzaGiust"), "FLAGASSENZAGIUST");
	plist.add(Projections.property("catMerc"), "CATMERC");
	plist.add(Projections.property("spuntista"), "SPUNTISTA");
	plist.add(Projections.property("motivazione"), "MOTIVAZIONE");
	plist.add(Projections.property("proprietario"), "PROPRIETARIO");
	//
	plist.add(Projections.property("numeropresenze"), "NUMEROPRESENZE");
	//
	plist.add(Projections.property("_posteggio.id.codice"), "POSTEGGIO_ID_CODICE");
	plist.add(Projections.property("_posteggio.codiceposteggio"), "POSTEGGIO_CODICEPOSTEGGIO");
	plist.add(Projections.property("_posteggio.note"), "POSTEGGIO_NOTE");
	plist.add(Projections.property("_posteggio.superficie"), "POSTEGGIO_SUPERFICIE");
	plist.add(Projections.property("_posteggio.lunghezza"), "POSTEGGIO_LUNGHEZZA");
	plist.add(Projections.property("_posteggio.larghezza"), "POSTEGGIO_LARGHEZZA");
	plist.add(Projections.property("importo"), "IMPORTO");
	plist.add(Projections.property("flagRinunciaPresenza"), "FLAGRINUNCIAPRESENZA");
	plist.add(Projections.property("_posteggioTipoSpazio.tipospazio"), "POSTEGGIO_TIPOSPAZIO");
	plist.add(Projections.property("_posteggioTipoSpazio.tipospazio"), "POSTEGGIO_TIPOSPAZIO");
	plist.add(Projections.property("_posteggio.identificativoPercorso"), "POSTEGGIO_IDENTIFICATIVOPERCORSO");
	plist.add(Projections.property("_posteggiSettori.codicesettore"), "POSTEGGIO_CODICESETTORE");
	plist.add(Projections.property("_posteggiSettori.settore"), "POSTEGGIO_DESCRIZIONESETTORE");
	plist.add(Projections.property("flagRinunciaPresenza"), "FLAGRINUNCIAPRESENZA");
	//
	plist.add(Projections.property("_posteggioRinunciato.codiceposteggio"), "CODPOSTRINUNCIATO");
	//
	plist.add(Projections.property("_occupante.id.codice"), "OCCUPANTE_ID_CODICE");
	plist.add(Projections.property("_occupante.tipoanagrafe"), "OCCUPANTE_TIPOANAGRAFE");
	plist.add(Projections.property("_occupante.nominativo"), "OCCUPANTE_NOMINATIVO");
	plist.add(Projections.property("_occupante.nome"), "OCCUPANTE_NOME");
	plist.add(Projections.property("_occupante.codicefiscale"), "OCCUPANTE_CODICEFISCALE");
	plist.add(Projections.property("_occupante.email"), "OCCUPANTE_EMAIL");
	plist.add(Projections.property("_occupanteFormaGiuridica.formagiuridica"), "OCCUPANTE_FORMAGIURIDICA");
	plist.add(Projections.property("_occupante.partitaiva"), "OCCUPANTE_PARTITAIVA");
	plist.add(Projections.property("_occupante.tipologia"), "OCCUPANTE_TIPOLOGIA");
	plist.add(Projections.property("_occupante.flagDisabilitato"), "OCCUPANTE_FLAGDISABILITATO");
	// i
	plist.add(Projections.property("_occupante.indirizzo"), "OCCUPANTE_INDIRIZZO");
	plist.add(Projections.property("_occupante.cap"), "OCCUPANTE_CAP");
	plist.add(Projections.property("_occupante.citta"), "OCCUPANTE_CITTA");
	plist.add(Projections.property("_occupante.provincia"), "OCCUPANTE_PROVINCIA");
	plist.add(Projections.property("_occupante.numiscrrea"), "OCCUPANTE_NUMISCRREA");
	plist.add(Projections.property("_occupante.dataiscrrea"), "OCCUPANTE_DATAISCRREA");
	plist.add(Projections.property("_occupante.telefono"), "OCCUPANTE_TELEFONO");
	//
	plist.add(Projections.property("_concessionario.id.codice"), "CONCESSIONARIO_ID_CODICE");
	plist.add(Projections.property("_concessionario.tipoanagrafe"), "CONCESSIONARIO_TIPOANAGRAFE");
	plist.add(Projections.property("_concessionario.nominativo"), "CONCESSIONARIO_NOMINATIVO");
	plist.add(Projections.property("_concessionario.nome"), "CONCESSIONARIO_NOME");
	plist.add(Projections.property("_concessionario.codicefiscale"), "CONCESSIONARIO_CODICEFISCALE");
	plist.add(Projections.property("_concessionario.email"), "CONCESSIONARIO_EMAIL");
	plist.add(Projections.property("_concessionarioFormaGiuridica.formagiuridica"), "CONCESSIONARIO_FORMAGIURIDICA");
	plist.add(Projections.property("_concessionario.partitaiva"), "CONCESSIONARIO_PARTITAIVA");
	plist.add(Projections.property("_concessionario.tipologia"), "CONCESSIONARIO_TIPOLOGIA");
	plist.add(Projections.property("_concessionario.flagDisabilitato"), "CONCESSIONARIO_FLAGDISABILITATO");
	// i
	plist.add(Projections.property("_concessionario.indirizzo"), "CONCESSIONARIO_INDIRIZZO");
	plist.add(Projections.property("_concessionario.cap"), "CONCESSIONARIO_CAP");
	plist.add(Projections.property("_concessionario.citta"), "CONCESSIONARIO_CITTA");
	plist.add(Projections.property("_concessionario.provincia"), "CONCESSIONARIO_PROVINCIA");
	plist.add(Projections.property("_concessionario.numiscrrea"), "CONCESSIONARIO_NUMISCRREA");
	plist.add(Projections.property("_concessionario.dataiscrrea"), "CONCESSIONARIO_DATAISCRREA");
	plist.add(Projections.property("_concessionario.telefono"), "CONCESSIONARIO_TELEFONO");
	//
	//
	plist.add(Projections.property("_aut.id.codice"), "AUTORIZZAZIONI_ID_CODICE");
	plist.add(Projections.property("_aut.autoriznumero"), "AUTORIZZAZIONI_AUTORIZNUMERO");
	plist.add(Projections.property("_aut.autorizdata"), "AUTORIZZAZIONI_AUTORIZDATA");
	plist.add(Projections.property("_aut.note"), "AUTORIZZAZIONI_NOTE");
	plist.add(Projections.property("_aut.noteSistema"), "AUTORIZZAZIONI_NOTESISTEMA");
	plist.add(Projections.property("_autAutorizComune.comune"), "AUTORIZZAZIONI_AUTORIZCOMUNE");
	plist.add(Projections.property("_autTipologiaRegistro.trDescrizione"), "AUTORIZZAZIONI_TIPOLOGIAREGISTRO");
	//
	plist.add(Projections.property("_autConcAssente.id.codice"), "AUTORIZZAZIONECONCESSIONARIOASSENTE_ID_CODICE");
	plist.add(Projections.property("_autConcAssente.autoriznumero"), "AUTORIZZAZIONECONCESSIONARIOASSENTE_AUTORIZNUMERO");
	plist.add(Projections.property("_autConcAssente.autorizdata"), "AUTORIZZAZIONECONCESSIONARIOASSENTE_AUTORIZDATA");
	plist.add(Projections.property("_autConcAssente.dataCessazione"), "AUTORIZZAZIONECONCESSIONARIOASSENTE_DATACESSAZIONE");
	plist.add(Projections.property("_autConcAssenteAutorizComune.comune"), "AUTORIZZAZIONECONCESSIONARIOASSENTE_AUTORIZCOMUNE");
	plist.add(Projections.property("_autConcAssenteTipologiaRegistro.trDescrizione"), "AUTORIZZAZIONECONCESSIONARIOASSENTE_TIPOLOGIAREGISTRO");
	plist.add(Projections.property("_autConcAssente.note"), "AUTORIZZAZIONECONCESSIONARIOASSENTE_NOTE");
	plist.add(Projections.property("_autConcAssente.noteSistema"), "AUTORIZZAZIONECONCESSIONARIOASSENTE_NOTESISTEMA");
	// campi aut originaria del concessionario
	/**
	 * AUTORIGNUMERO, AUTORIGDATA, AUTORIGCOMUNE
	 */
	plist.add(Projections.property("_autConcAssente.autorigNumero"), "AUTORIZZAZIONECONCESSIONARIOASSENTE_AUTORIGNUMERO");
	plist.add(Projections.property("_autConcAssente.autorigData"), "AUTORIZZAZIONECONCESSIONARIOASSENTE_AUTORIGDATA");
	plist.add(Projections.property("_autorigComune.comune"), "AUTORIZZAZIONECONCESSIONARIOASSENTE_AUTORIGCOMUNE");
	//
	plist.add(Projections.property("_mercatiSpunte.descrizione"), "FASESPUNTA");
	plist.add(Projections.property("flagPagato"), "FLAGPAGATO");
	//
	plist.add(Projections.property("_gerente.id.codice"), "GERENTE_ID_CODICE");
	plist.add(Projections.property("_gerente.tipoanagrafe"), "GERENTE_TIPOANAGRAFE");
	plist.add(Projections.property("_gerente.nominativo"), "GERENTE_NOMINATIVO");
	plist.add(Projections.property("_gerente.nome"), "GERENTE_NOME");
	plist.add(Projections.property("_gerente.codicefiscale"), "GERENTE_CODICEFISCALE");
	plist.add(Projections.property("_gerente.email"), "GERENTE_EMAIL");
	plist.add(Projections.property("_gerenteformagiuridica.formagiuridica"), "GERENTE_FORMAGIURIDICA");
	plist.add(Projections.property("_gerente.partitaiva"), "GERENTE_PARTITAIVA");
	plist.add(Projections.property("_gerente.tipologia"), "GERENTE_TIPOLOGIA");
	plist.add(Projections.property("_gerente.flagDisabilitato"), "GERENTE_FLAGDISABILITATO");
	// i 
	plist.add(Projections.property("_gerente.indirizzo"), "GERENTE_INDIRIZZO");
	plist.add(Projections.property("_gerente.cap"), "GERENTE_CAP");
	plist.add(Projections.property("_gerente.citta"), "GERENTE_CITTA");
	plist.add(Projections.property("_gerente.provincia"), "GERENTE_PROVINCIA");
	plist.add(Projections.property("_gerente.numiscrrea"), "GERENTE_NUMISCRREA");
	plist.add(Projections.property("_gerente.dataiscrrea"), "GERENTE_DATAISCRREA");
	plist.add(Projections.property("_gerente.telefono"), "GERENTE_TELEFONO");
	//
	plist.add(Projections.property("_autorizzazioniCsis.statoAutorizzazione"), "STATOAUTORIZZAZIONE");
	plist.add(Projections.property("_autorizzazioniCsis.statoWarning"), "STATOWARNING");
	plist.add(Projections.property("_autorizzazioniCsis.dataSospDa"), "DATASOSPDA");
	plist.add(Projections.property("_autorizzazioniCsis.dataSospA"), "DATASOSPA");
	plist.add(Projections.property("_autorizzazioniCsis.dataFineGerenza"), "DATAFINEGERENZA");
	plist.add(Projections.property("_autorizzazioniCsis.causaleSospensione"), "CAUSALESOSPENSIONE");
	plist.add(Projections.property("_autorizzazioniCsis.validaSpunta"), "VALIDASPUNTA");
	plist.add(Projections.property("_autorizzazioniCsis.dataInizioGerenza"), "DATAINIZIOGERENZA");
	plist.add(Projections.property("_autorizzazioniCsis.autPrecedenteNumero"), "AUTPRECEDENTENUMERO");
	plist.add(Projections.property("_autorizzazioniCsis.autPrecedenteData"), "AUTPRECEDENTEDATA");
	plist.add(Projections.property("_autPrecCom.comune"), "AUTPRECEDENTECOMUNE");
	plist.add(Projections.property("_autorizzazioniCsis.protocollo"), "PROTOCOLLOAUT");
	plist.add(Projections.property("_autorizzazioniCsis.dataProtocollo"), "DATAPROTOCOLLOAUT");
	//. projection su autorizzazione csi concessionario
	plist.add(Projections.property("_gerenteConc.id.codice"), "GERENTECON_ID_CODICE");
	plist.add(Projections.property("_gerenteConc.tipoanagrafe"), "GERENTECON_TIPOANAGRAFE");
	plist.add(Projections.property("_gerenteConc.nominativo"), "GERENTECON_NOMINATIVO");
	plist.add(Projections.property("_gerenteConc.nome"), "GERENTECON_NOME");
	plist.add(Projections.property("_gerenteConc.codicefiscale"), "GERENTECON_CODICEFISCALE");
	plist.add(Projections.property("_gerenteConc.email"), "GERENTECON_EMAIL");
	plist.add(Projections.property("_gerenteformagiuridica.formagiuridica"), "GERENTECON_FORMAGIURIDICA");
	plist.add(Projections.property("_gerenteConc.partitaiva"), "GERENTECON_PARTITAIVA");
	plist.add(Projections.property("_gerenteConc.tipologia"), "GERENTECON_TIPOLOGIA");
	plist.add(Projections.property("_gerenteConc.flagDisabilitato"), "GERENTECON_FLAGDISABILITATO");
	// i 
	plist.add(Projections.property("_gerenteConc.indirizzo"), "GERENTECON_INDIRIZZO");
	plist.add(Projections.property("_gerenteConc.cap"), "GERENTECON_CAP");
	plist.add(Projections.property("_gerenteConc.citta"), "GERENTECON_CITTA");
	plist.add(Projections.property("_gerenteConc.provincia"), "GERENTECON_PROVINCIA");
	plist.add(Projections.property("_gerenteConc.numiscrrea"), "GERENTECON_NUMISCRREA");
	plist.add(Projections.property("_gerenteConc.dataiscrrea"), "GERENTECON_DATAISCRREA");
	plist.add(Projections.property("_gerenteConc.telefono"), "GERENTECON_TELEFONO");
	plist.add(Projections.property("_autConcCsis.statoAutorizzazione"), "STATOAUTORIZZAZIONECONC");
	plist.add(Projections.property("_autConcCsis.statoWarning"), "STATOWARNINGCON");
	plist.add(Projections.property("_autConcCsis.dataSospDa"), "DATASOSPDACON");
	plist.add(Projections.property("_autConcCsis.dataSospA"), "DATASOSPACON");
	plist.add(Projections.property("_autConcCsis.dataFineGerenza"), "DATAFINEGERENZACON");
	plist.add(Projections.property("_autConcCsis.causaleSospensione"), "CAUSALESOSPENSIONECON");
	plist.add(Projections.property("_autConcCsis.validaSpunta"), "VALIDASPUNTACON");
	plist.add(Projections.property("_autConcCsis.dataInizioGerenza"), "DATAINIZIOGERENZACON");
	plist.add(Projections.property("_autConcCsis.protocollo"), "PROTOCOLLOAUTCON");
	plist.add(Projections.property("_autConcCsis.dataProtocollo"), "DATAPROTOCOLLOAUTCON");
	// projection collaboratore
	//
	plist.add(Projections.property("_collaboratore.id.codice"), "COLLABORATORE_ID_CODICE");
	plist.add(Projections.property("_collaboratore.tipoanagrafe"), "COLLABORATORE_TIPOANAGRAFE");
	plist.add(Projections.property("_collaboratore.nominativo"), "COLLABORATORE_NOMINATIVO");
	plist.add(Projections.property("_collaboratore.nome"), "COLLABORATORE_NOME");
	plist.add(Projections.property("_collaboratore.codicefiscale"), "COLLABORATORE_CODICEFISCALE");
	plist.add(Projections.property("_collaboratore.email"), "COLLABORATORE_EMAIL");
	plist.add(Projections.property("_collaboratoreFormaGiuridica.formagiuridica"), "COLLABORATORE_FORMAGIURIDICA");
	plist.add(Projections.property("_collaboratore.partitaiva"), "COLLABORATORE_PARTITAIVA");
	plist.add(Projections.property("_collaboratore.tipologia"), "COLLABORATORE_TIPOLOGIA");
	plist.add(Projections.property("_collaboratore.flagDisabilitato"), "COLLABORATORE_FLAGDISABILITATO");
	// i
	plist.add(Projections.property("_collaboratore.indirizzo"), "COLLABORATORE_INDIRIZZO");
	plist.add(Projections.property("_collaboratore.cap"), "COLLABORATORE_CAP");
	plist.add(Projections.property("_collaboratore.citta"), "COLLABORATORE_CITTA");
	plist.add(Projections.property("_collaboratore.provincia"), "COLLABORATORE_PROVINCIA");
	plist.add(Projections.property("_collaboratore.numiscrrea"), "COLLABORATORE_NUMISCRREA");
	plist.add(Projections.property("_collaboratore.dataiscrrea"), "COLLABORATORE_DATAISCRREA");
	plist.add(Projections.property("_collaboratore.telefono"), "COLLABORATORE_TELEFONO");
	//
	// gerente spuntista della giornata
	plist.add(Projections.property("_gerentePresD.id.codice"), "GERENTEPRESD_ID_CODICE");
	plist.add(Projections.property("_gerentePresD.tipoanagrafe"), "GERENTEPRESD_TIPOANAGRAFE");
	plist.add(Projections.property("_gerentePresD.nominativo"), "GERENTEPRESD_NOMINATIVO");
	plist.add(Projections.property("_gerentePresD.nome"), "GERENTEPRESD_NOME");
	plist.add(Projections.property("_gerentePresD.codicefiscale"), "GERENTEPRESD_CODICEFISCALE");
	plist.add(Projections.property("_gerentePresD.email"), "GERENTEPRESD_EMAIL");
	plist.add(Projections.property("_gerentePresDFormaGiuridica.formagiuridica"), "GERENTEPRESD_FORMAGIURIDICA");
	plist.add(Projections.property("_gerentePresD.partitaiva"), "GERENTEPRESD_PARTITAIVA");
	plist.add(Projections.property("_gerentePresD.tipologia"), "GERENTEPRESD_TIPOLOGIA");
	plist.add(Projections.property("_gerentePresD.flagDisabilitato"), "GERENTEPRESD_FLAGDISABILITATO");
	plist.add(Projections.property("_gerentePresD.indirizzo"), "GERENTEPRESD_INDIRIZZO");
	plist.add(Projections.property("_gerentePresD.cap"), "GERENTEPRESD_CAP");
	plist.add(Projections.property("_gerentePresD.citta"), "GERENTEPRESD_CITTA");
	plist.add(Projections.property("_gerentePresD.provincia"), "GERENTEPRESD_PROVINCIA");
	plist.add(Projections.property("_gerentePresD.numiscrrea"), "GERENTEPRESD_NUMISCRREA");
	plist.add(Projections.property("_gerentePresD.dataiscrrea"), "GERENTEPRESD_DATAISCRREA");
	plist.add(Projections.property("_gerentePresD.telefono"), "GERENTEPRESD_TELEFONO");
	// gerente concessionario della giornata
	plist.add(Projections.property("_gerenteConcPresD.id.codice"), "GERENTECONCPRESD_ID_CODICE");
	plist.add(Projections.property("_gerenteConcPresD.tipoanagrafe"), "GERENTECONCPRESD_TIPOANAGRAFE");
	plist.add(Projections.property("_gerenteConcPresD.nominativo"), "GERENTECONCPRESD_NOMINATIVO");
	plist.add(Projections.property("_gerenteConcPresD.nome"), "GERENTECONCPRESD_NOME");
	plist.add(Projections.property("_gerenteConcPresD.codicefiscale"), "GERENTECONCPRESD_CODICEFISCALE");
	plist.add(Projections.property("_gerenteConcPresD.email"), "GERENTECONCPRESD_EMAIL");
	plist.add(Projections.property("_gerenteConcPresDFormaGiuridica.formagiuridica"), "GERENTECONCPRESD_FORMAGIURIDICA");
	plist.add(Projections.property("_gerenteConcPresD.partitaiva"), "GERENTECONCPRESD_PARTITAIVA");
	plist.add(Projections.property("_gerenteConcPresD.tipologia"), "GERENTECONCPRESD_TIPOLOGIA");
	plist.add(Projections.property("_gerenteConcPresD.flagDisabilitato"), "GERENTECONCPRESD_FLAGDISABILITATO");
	plist.add(Projections.property("_gerenteConcPresD.indirizzo"), "GERENTECONCPRESD_INDIRIZZO");
	plist.add(Projections.property("_gerenteConcPresD.cap"), "GERENTECONCPRESD_CAP");
	plist.add(Projections.property("_gerenteConcPresD.citta"), "GERENTECONCPRESD_CITTA");
	plist.add(Projections.property("_gerenteConcPresD.provincia"), "GERENTECONCPRESD_PROVINCIA");
	plist.add(Projections.property("_gerenteConcPresD.numiscrrea"), "GERENTECONCPRESD_NUMISCRREA");
	plist.add(Projections.property("_gerenteConcPresD.dataiscrrea"), "GERENTECONCPRESD_DATAISCRREA");
	plist.add(Projections.property("_gerenteConcPresD.telefono"), "GERENTECONCPRESD_TELEFONO");
	plist.add(Projections.property("dettPosizioneDebitoriaId"), "DETTPOSIZIONEDEBITORIAID");
	//
	//
	// titolare autorizzazione
	plist.add(Projections.property("_titAut.id.codice"), "AUTORIZZAZIONI_CODICETITOLAREAUT");
	plist.add(Projections.property("_titAut.nome"), "AUTORIZZAZIONI_NOMETITOLAREAUT");
	plist.add(Projections.property("_titAut.nominativo"), "AUTORIZZAZIONI_COGNOMETITOLAREAUT");
	plist.add(Projections.property("_titAut.codicefiscale"), "AUTORIZZAZIONI_CFTITOLAREAUT");
	plist.add(Projections.property("_titAut.partitaiva"), "AUTORIZZAZIONI_PIVATITOLAREAUT");
	// titolare autorizzazione
	plist.add(Projections.property("_occupAut.id.codice"), "AUTORIZZAZIONI_CODICEOCCUPAUT");
	plist.add(Projections.property("_occupAut.nome"), "AUTORIZZAZIONI_NOMEOCCUPAUT");
	plist.add(Projections.property("_occupAut.nominativo"), "AUTORIZZAZIONI_COGNOMEOCCUPAUT");
	plist.add(Projections.property("_occupAut.codicefiscale"), "AUTORIZZAZIONI_CFOCCUPAUT");
	plist.add(Projections.property("_occupAut.partitaiva"), "AUTORIZZAZIONI_PIVAOCCUPAUT");
	//
	//
	// titolare concessione su posteggio
	plist.add(Projections.property("_titConc.id.codice"), "AUTORIZZAZIONECONCESSIONARIOASSENTE_CODICETITOLAREAUT");
	plist.add(Projections.property("_titConc.nome"), "AUTORIZZAZIONECONCESSIONARIOASSENTE_NOMETITOLAREAUT");
	plist.add(Projections.property("_titConc.nominativo"), "AUTORIZZAZIONECONCESSIONARIOASSENTE_COGNOMETITOLAREAUT");
	plist.add(Projections.property("_titConc.codicefiscale"), "AUTORIZZAZIONECONCESSIONARIOASSENTE_CFTITOLAREAUT");
	plist.add(Projections.property("_titConc.partitaiva"), "AUTORIZZAZIONECONCESSIONARIOASSENTE_PIVATITOLAREAUT");
	// titolare concessione su posteggio
	plist.add(Projections.property("_occupConc.id.codice"), "AUTORIZZAZIONECONCESSIONARIOASSENTE_CODICEOCCUPAUT");
	plist.add(Projections.property("_occupConc.nome"), "AUTORIZZAZIONECONCESSIONARIOASSENTE_NOMEOCCUPAUT");
	plist.add(Projections.property("_occupConc.nominativo"), "AUTORIZZAZIONECONCESSIONARIOASSENTE_COGNOMEOCCUPAUT");
	plist.add(Projections.property("_occupConc.codicefiscale"), "AUTORIZZAZIONECONCESSIONARIOASSENTE_CFOCCUPAUT");
	plist.add(Projections.property("_occupConc.partitaiva"), "AUTORIZZAZIONECONCESSIONARIOASSENTE_PIVAOCCUPAUT");
	criteria.setProjection(plist);
	//
	criteria.setResultTransformer(new IgnoreCaseAliasToBeanResultTransformer(MercatipresenzeDDTO.class));
	List<MercatipresenzeDDTO> list = getHibernateTemplate().findByCriteria(criteria);
	return list;
    }

    @Override
    public List<MercatipresenzeDDTO> findListaPosteggi(MercatipresenzeT giorno) {

	return _findListaPosteggi(giorno.getId().getCodice(), null);
    }

    @SuppressWarnings("unchecked")
    @Override
    public MercatipresenzeDDTO findByIdLazy(Integer codicePresenza) {

	DetachedCriteria criteria = getIdcomuneCriteria();
	criteria.add(Restrictions.eq("id.codice", codicePresenza));
	//
	criteria.createAlias("posteggio", "_posteggio");
	criteria.createAlias("_posteggio.tipoSpazio", "_posteggioTipoSpazio", Criteria.LEFT_JOIN);
	criteria.createAlias("_posteggio.posteggiSettori", "_posteggiSettori", Criteria.LEFT_JOIN);
	//
	criteria.createAlias("posteggioRinunciato", "_posteggioRinunciato", Criteria.LEFT_JOIN);
	//
	criteria.createAlias("occupante", "_occupante", Criteria.LEFT_JOIN);
	criteria.createAlias("_occupante.formagiuridica", "_occupanteFormaGiuridica", Criteria.LEFT_JOIN);
	//
	criteria.createAlias("concessionario", "_concessionario", Criteria.LEFT_JOIN);
	criteria.createAlias("_concessionario.formagiuridica", "_concessionarioFormaGiuridica", Criteria.LEFT_JOIN);
	//
	criteria.createAlias("autorizzazioni", "_aut", Criteria.LEFT_JOIN);
	criteria.createAlias("_aut.autorizcomune", "_autAutorizComune", Criteria.LEFT_JOIN);
	criteria.createAlias("_aut.tipologiaregistro", "_autTipologiaRegistro", Criteria.LEFT_JOIN);
	// left join aut_csi
	criteria.createAlias("_aut.autorizzazioniCsis", "_autorizzazioniCsis", Criteria.LEFT_JOIN);
	criteria.createAlias("_autorizzazioniCsis.anagrafe", "_gerente", Criteria.LEFT_JOIN);
	criteria.createAlias("_autorizzazioniCsis.autPrecedenteComune", "_autPrecCom", Criteria.LEFT_JOIN);
	criteria.createAlias("_gerente.formagiuridica", "_gerenteformagiuridica", Criteria.LEFT_JOIN);
	//
	criteria.createAlias("autorizzazioneConcessionarioAssente", "_autConcAssente", Criteria.LEFT_JOIN);
	criteria.createAlias("_autConcAssente.autorizcomune", "_autConcAssenteAutorizComune", Criteria.LEFT_JOIN);
	criteria.createAlias("_autConcAssente.tipologiaregistro", "_autConcAssenteTipologiaRegistro", Criteria.LEFT_JOIN);
	// left join comune_aut_originale ( singolo campo della tabella autororizzazione)
	criteria.createAlias("_autConcAssente.autorigComune", "_autorigComune", Criteria.LEFT_JOIN);
	//.
	// left join aut_csi concessionari
	criteria.createAlias("_autConcAssente.autorizzazioniCsis", "_autConcCsis", Criteria.LEFT_JOIN);
	criteria.createAlias("_autConcCsis.anagrafe", "_gerenteConc", Criteria.LEFT_JOIN);
	criteria.createAlias("_gerenteConc.formagiuridica", "_gerenteConformagiuridica", Criteria.LEFT_JOIN);
	// .. left join collaboratore (FK_CODICECOLLABORATORE)
	criteria.createAlias("collaboratore", "_collaboratore", Criteria.LEFT_JOIN);
	criteria.createAlias("_collaboratore.formagiuridica", "_collaboratoreFormaGiuridica", Criteria.LEFT_JOIN);
	//..
	criteria.createAlias("mercatiSpunte", "_mercatiSpunte", Criteria.LEFT_JOIN);
	//
	// GERENTI DELLO SPUNTISTA E DEL CONCESSIONARIO DELLA GIORNATA
	criteria.createAlias("gerenteSpuntista", "_gerentePresD", Criteria.LEFT_JOIN);
	criteria.createAlias("_gerentePresD.formagiuridica", "_gerentePresDFormaGiuridica", Criteria.LEFT_JOIN);
	criteria.createAlias("gerenteConcessionario", "_gerenteConcPresD", Criteria.LEFT_JOIN);
	criteria.createAlias("_gerenteConcPresD.formagiuridica", "_gerenteConcPresDFormaGiuridica", Criteria.LEFT_JOIN);
	//
	ProjectionList plist = Projections.projectionList();
	plist.add(Projections.property("id.codice"), "ID_CODICE");
	plist.add(Projections.property("flagAssenzaGiust"), "FLAGASSENZAGIUST");
	plist.add(Projections.property("catMerc"), "CATMERC");
	plist.add(Projections.property("spuntista"), "SPUNTISTA");
	plist.add(Projections.property("motivazione"), "MOTIVAZIONE");
	plist.add(Projections.property("proprietario"), "PROPRIETARIO");
	//
	plist.add(Projections.property("numeropresenze"), "NUMEROPRESENZE");
	//
	//
	plist.add(Projections.property("_posteggio.id.codice"), "POSTEGGIO_ID_CODICE");
	plist.add(Projections.property("_posteggio.codiceposteggio"), "POSTEGGIO_CODICEPOSTEGGIO");
	plist.add(Projections.property("_posteggio.note"), "POSTEGGIO_NOTE");
	plist.add(Projections.property("_posteggio.superficie"), "POSTEGGIO_SUPERFICIE");
	plist.add(Projections.property("_posteggio.lunghezza"), "POSTEGGIO_LUNGHEZZA");
	plist.add(Projections.property("_posteggio.larghezza"), "POSTEGGIO_LARGHEZZA");
	plist.add(Projections.property("_posteggioTipoSpazio.tipospazio"), "POSTEGGIO_TIPOSPAZIO");
	plist.add(Projections.property("_posteggio.identificativoPercorso"), "POSTEGGIO_IDENTIFICATIVOPERCORSO");
	plist.add(Projections.property("_posteggiSettori.codicesettore"), "POSTEGGIO_CODICESETTORE");
	plist.add(Projections.property("_posteggiSettori.settore"), "POSTEGGIO_DESCRIZIONESETTORE");
	plist.add(Projections.property("importo"), "IMPORTO");
	plist.add(Projections.property("flagRinunciaPresenza"), "FLAGRINUNCIAPRESENZA");
	//
	plist.add(Projections.property("_posteggioRinunciato.codiceposteggio"), "CODPOSTRINUNCIATO");
	//
	plist.add(Projections.property("_occupante.id.codice"), "OCCUPANTE_ID_CODICE");
	plist.add(Projections.property("_occupante.tipoanagrafe"), "OCCUPANTE_TIPOANAGRAFE");
	plist.add(Projections.property("_occupante.nominativo"), "OCCUPANTE_NOMINATIVO");
	plist.add(Projections.property("_occupante.nome"), "OCCUPANTE_NOME");
	plist.add(Projections.property("_occupante.codicefiscale"), "OCCUPANTE_CODICEFISCALE");
	plist.add(Projections.property("_occupante.email"), "OCCUPANTE_EMAIL");
	plist.add(Projections.property("_occupanteFormaGiuridica.formagiuridica"), "OCCUPANTE_FORMAGIURIDICA");
	plist.add(Projections.property("_occupante.partitaiva"), "OCCUPANTE_PARTITAIVA");
	plist.add(Projections.property("_occupante.tipologia"), "OCCUPANTE_TIPOLOGIA");
	plist.add(Projections.property("_occupante.flagDisabilitato"), "OCCUPANTE_FLAGDISABILITATO");
	// i
	plist.add(Projections.property("_occupante.indirizzo"), "OCCUPANTE_INDIRIZZO");
	plist.add(Projections.property("_occupante.cap"), "OCCUPANTE_CAP");
	plist.add(Projections.property("_occupante.citta"), "OCCUPANTE_CITTA");
	plist.add(Projections.property("_occupante.provincia"), "OCCUPANTE_PROVINCIA");
	plist.add(Projections.property("_occupante.numiscrrea"), "OCCUPANTE_NUMISCRREA");
	plist.add(Projections.property("_occupante.dataiscrrea"), "OCCUPANTE_DATAISCRREA");
	plist.add(Projections.property("_occupante.telefono"), "OCCUPANTE_TELEFONO");
	//
	plist.add(Projections.property("_concessionario.id.codice"), "CONCESSIONARIO_ID_CODICE");
	plist.add(Projections.property("_concessionario.tipoanagrafe"), "CONCESSIONARIO_TIPOANAGRAFE");
	plist.add(Projections.property("_concessionario.nominativo"), "CONCESSIONARIO_NOMINATIVO");
	plist.add(Projections.property("_concessionario.nome"), "CONCESSIONARIO_NOME");
	plist.add(Projections.property("_concessionario.codicefiscale"), "CONCESSIONARIO_CODICEFISCALE");
	plist.add(Projections.property("_concessionario.email"), "CONCESSIONARIO_EMAIL");
	plist.add(Projections.property("_concessionarioFormaGiuridica.formagiuridica"), "CONCESSIONARIO_FORMAGIURIDICA");
	plist.add(Projections.property("_concessionario.partitaiva"), "CONCESSIONARIO_PARTITAIVA");
	plist.add(Projections.property("_concessionario.tipologia"), "CONCESSIONARIO_TIPOLOGIA");
	plist.add(Projections.property("_concessionario.flagDisabilitato"), "CONCESSIONARIO_FLAGDISABILITATO");
	// i
	plist.add(Projections.property("_concessionario.indirizzo"), "CONCESSIONARIO_INDIRIZZO");
	plist.add(Projections.property("_concessionario.cap"), "CONCESSIONARIO_CAP");
	plist.add(Projections.property("_concessionario.citta"), "CONCESSIONARIO_CITTA");
	plist.add(Projections.property("_concessionario.provincia"), "CONCESSIONARIO_PROVINCIA");
	plist.add(Projections.property("_concessionario.numiscrrea"), "CONCESSIONARIO_NUMISCRREA");
	plist.add(Projections.property("_concessionario.dataiscrrea"), "CONCESSIONARIO_DATAISCRREA");
	plist.add(Projections.property("_concessionario.telefono"), "CONCESSIONARIO_TELEFONO");
	//
	plist.add(Projections.property("_aut.id.codice"), "AUTORIZZAZIONI_ID_CODICE");
	plist.add(Projections.property("_aut.autoriznumero"), "AUTORIZZAZIONI_AUTORIZNUMERO");
	plist.add(Projections.property("_aut.autorizdata"), "AUTORIZZAZIONI_AUTORIZDATA");
	//
	plist.add(Projections.property("_aut.note"), "AUTORIZZAZIONI_NOTE");
	plist.add(Projections.property("_aut.noteSistema"), "AUTORIZZAZIONI_NOTESISTEMA");
	//
	plist.add(Projections.property("_autAutorizComune.comune"), "AUTORIZZAZIONI_AUTORIZCOMUNE");
	plist.add(Projections.property("_autTipologiaRegistro.trDescrizione"), "AUTORIZZAZIONI_TIPOLOGIAREGISTRO");
	plist.add(Projections.property("_autConcAssente.id.codice"), "AUTORIZZAZIONECONCESSIONARIOASSENTE_ID_CODICE");
	plist.add(Projections.property("_autConcAssente.autoriznumero"), "AUTORIZZAZIONECONCESSIONARIOASSENTE_AUTORIZNUMERO");
	plist.add(Projections.property("_autConcAssente.autorizdata"), "AUTORIZZAZIONECONCESSIONARIOASSENTE_AUTORIZDATA");
	plist.add(Projections.property("_autConcAssente.dataCessazione"), "AUTORIZZAZIONECONCESSIONARIOASSENTE_DATACESSAZIONE");
	plist.add(Projections.property("_autConcAssenteAutorizComune.comune"), "AUTORIZZAZIONECONCESSIONARIOASSENTE_AUTORIZCOMUNE");
	plist.add(Projections.property("_autConcAssenteTipologiaRegistro.trDescrizione"), "AUTORIZZAZIONECONCESSIONARIOASSENTE_TIPOLOGIAREGISTRO");
	plist.add(Projections.property("_autConcAssente.note"), "AUTORIZZAZIONECONCESSIONARIOASSENTE_NOTE");
	plist.add(Projections.property("_autConcAssente.noteSistema"), "AUTORIZZAZIONECONCESSIONARIOASSENTE_NOTESISTEMA");
	// campi aut originaria del concessionario
	/**
	 * AUTORIGNUMERO, AUTORIGDATA, AUTORIGCOMUNE
	 */
	plist.add(Projections.property("_autConcAssente.autorigNumero"), "AUTORIZZAZIONECONCESSIONARIOASSENTE_AUTORIGNUMERO");
	plist.add(Projections.property("_autConcAssente.autorigData"), "AUTORIZZAZIONECONCESSIONARIOASSENTE_AUTORIGDATA");
	plist.add(Projections.property("_autorigComune.comune"), "AUTORIZZAZIONECONCESSIONARIOASSENTE_AUTORIGCOMUNE");
	//.
	plist.add(Projections.property("_mercatiSpunte.descrizione"), "FASESPUNTA");
	plist.add(Projections.property("flagPagato"), "FLAGPAGATO");
	plist.add(Projections.property("_gerente.id.codice"), "GERENTE_ID_CODICE");
	plist.add(Projections.property("_gerente.tipoanagrafe"), "GERENTE_TIPOANAGRAFE");
	plist.add(Projections.property("_gerente.nominativo"), "GERENTE_NOMINATIVO");
	plist.add(Projections.property("_gerente.nome"), "GERENTE_NOME");
	plist.add(Projections.property("_gerente.codicefiscale"), "GERENTE_CODICEFISCALE");
	plist.add(Projections.property("_gerente.email"), "GERENTE_EMAIL");
	plist.add(Projections.property("_gerenteformagiuridica.formagiuridica"), "GERENTE_FORMAGIURIDICA");
	plist.add(Projections.property("_gerente.partitaiva"), "GERENTE_PARTITAIVA");
	plist.add(Projections.property("_gerente.tipologia"), "GERENTE_TIPOLOGIA");
	plist.add(Projections.property("_gerente.flagDisabilitato"), "GERENTE_FLAGDISABILITATO");
	// i 
	plist.add(Projections.property("_gerente.indirizzo"), "GERENTE_INDIRIZZO");
	plist.add(Projections.property("_gerente.cap"), "GERENTE_CAP");
	plist.add(Projections.property("_gerente.citta"), "GERENTE_CITTA");
	plist.add(Projections.property("_gerente.provincia"), "GERENTE_PROVINCIA");
	plist.add(Projections.property("_gerente.numiscrrea"), "GERENTE_NUMISCRREA");
	plist.add(Projections.property("_gerente.dataiscrrea"), "GERENTE_DATAISCRREA");
	plist.add(Projections.property("_gerente.telefono"), "GERENTE_TELEFONO");
	//
	plist.add(Projections.property("_autorizzazioniCsis.statoAutorizzazione"), "STATOAUTORIZZAZIONE");
	plist.add(Projections.property("_autorizzazioniCsis.statoWarning"), "STATOWARNING");
	plist.add(Projections.property("_autorizzazioniCsis.dataSospDa"), "DATASOSPDA");
	plist.add(Projections.property("_autorizzazioniCsis.dataSospA"), "DATASOSPA");
	plist.add(Projections.property("_autorizzazioniCsis.dataFineGerenza"), "DATAFINEGERENZA");
	plist.add(Projections.property("_autorizzazioniCsis.causaleSospensione"), "CAUSALESOSPENSIONE");
	plist.add(Projections.property("_autorizzazioniCsis.validaSpunta"), "VALIDASPUNTA");
	plist.add(Projections.property("_autorizzazioniCsis.dataInizioGerenza"), "DATAINIZIOGERENZA");
	plist.add(Projections.property("_autorizzazioniCsis.autPrecedenteNumero"), "AUTPRECEDENTENUMERO");
	plist.add(Projections.property("_autorizzazioniCsis.autPrecedenteData"), "AUTPRECEDENTEDATA");
	plist.add(Projections.property("_autPrecCom.comune"), "AUTPRECEDENTECOMUNE");
	plist.add(Projections.property("_autorizzazioniCsis.protocollo"), "PROTOCOLLOAUT");
	plist.add(Projections.property("_autorizzazioniCsis.dataProtocollo"), "DATAPROTOCOLLOAUT");
	//. projection su autorizzazione csi concessionario
	plist.add(Projections.property("_gerenteConc.id.codice"), "GERENTECON_ID_CODICE");
	plist.add(Projections.property("_gerenteConc.tipoanagrafe"), "GERENTECON_TIPOANAGRAFE");
	plist.add(Projections.property("_gerenteConc.nominativo"), "GERENTECON_NOMINATIVO");
	plist.add(Projections.property("_gerenteConc.nome"), "GERENTECON_NOME");
	plist.add(Projections.property("_gerenteConc.codicefiscale"), "GERENTECON_CODICEFISCALE");
	plist.add(Projections.property("_gerenteConc.email"), "GERENTECON_EMAIL");
	plist.add(Projections.property("_gerenteformagiuridica.formagiuridica"), "GERENTECON_FORMAGIURIDICA");
	plist.add(Projections.property("_gerenteConc.partitaiva"), "GERENTECON_PARTITAIVA");
	plist.add(Projections.property("_gerenteConc.tipologia"), "GERENTECON_TIPOLOGIA");
	plist.add(Projections.property("_gerenteConc.flagDisabilitato"), "GERENTECON_FLAGDISABILITATO");
	// i 
	plist.add(Projections.property("_gerenteConc.indirizzo"), "GERENTECON_INDIRIZZO");
	plist.add(Projections.property("_gerenteConc.cap"), "GERENTECON_CAP");
	plist.add(Projections.property("_gerenteConc.citta"), "GERENTECON_CITTA");
	plist.add(Projections.property("_gerenteConc.provincia"), "GERENTECON_PROVINCIA");
	plist.add(Projections.property("_gerenteConc.numiscrrea"), "GERENTECON_NUMISCRREA");
	plist.add(Projections.property("_gerenteConc.dataiscrrea"), "GERENTECON_DATAISCRREA");
	plist.add(Projections.property("_gerenteConc.telefono"), "GERENTECON_TELEFONO");
	plist.add(Projections.property("_autConcCsis.statoAutorizzazione"), "STATOAUTORIZZAZIONECONC");
	plist.add(Projections.property("_autConcCsis.statoWarning"), "STATOWARNINGCON");
	plist.add(Projections.property("_autConcCsis.dataSospDa"), "DATASOSPDACON");
	plist.add(Projections.property("_autConcCsis.dataSospA"), "DATASOSPACON");
	plist.add(Projections.property("_autConcCsis.dataFineGerenza"), "DATAFINEGERENZACON");
	plist.add(Projections.property("_autConcCsis.causaleSospensione"), "CAUSALESOSPENSIONECON");
	plist.add(Projections.property("_autConcCsis.validaSpunta"), "VALIDASPUNTACON");
	plist.add(Projections.property("_autConcCsis.dataInizioGerenza"), "DATAINIZIOGERENZACON");
	plist.add(Projections.property("_autConcCsis.protocollo"), "PROTOCOLLOAUTCON");
	plist.add(Projections.property("_autConcCsis.dataProtocollo"), "DATAPROTOCOLLOAUTCON");
	// projection collaboratore
	//
	plist.add(Projections.property("_collaboratore.id.codice"), "COLLABORATORE_ID_CODICE");
	plist.add(Projections.property("_collaboratore.tipoanagrafe"), "COLLABORATORE_TIPOANAGRAFE");
	plist.add(Projections.property("_collaboratore.nominativo"), "COLLABORATORE_NOMINATIVO");
	plist.add(Projections.property("_collaboratore.nome"), "COLLABORATORE_NOME");
	plist.add(Projections.property("_collaboratore.codicefiscale"), "COLLABORATORE_CODICEFISCALE");
	plist.add(Projections.property("_collaboratore.email"), "COLLABORATORE_EMAIL");
	plist.add(Projections.property("_collaboratoreFormaGiuridica.formagiuridica"), "COLLABORATORE_FORMAGIURIDICA");
	plist.add(Projections.property("_collaboratore.partitaiva"), "COLLABORATORE_PARTITAIVA");
	plist.add(Projections.property("_collaboratore.tipologia"), "COLLABORATORE_TIPOLOGIA");
	plist.add(Projections.property("_collaboratore.flagDisabilitato"), "COLLABORATORE_FLAGDISABILITATO");
	// i
	plist.add(Projections.property("_collaboratore.indirizzo"), "COLLABORATORE_INDIRIZZO");
	plist.add(Projections.property("_collaboratore.cap"), "COLLABORATORE_CAP");
	plist.add(Projections.property("_collaboratore.citta"), "COLLABORATORE_CITTA");
	plist.add(Projections.property("_collaboratore.provincia"), "COLLABORATORE_PROVINCIA");
	plist.add(Projections.property("_collaboratore.numiscrrea"), "COLLABORATORE_NUMISCRREA");
	plist.add(Projections.property("_collaboratore.dataiscrrea"), "COLLABORATORE_DATAISCRREA");
	plist.add(Projections.property("_collaboratore.telefono"), "COLLABORATORE_TELEFONO");
	//
	// gerente spuntista della giornata
	plist.add(Projections.property("_gerentePresD.id.codice"), "GERENTEPRESD_ID_CODICE");
	plist.add(Projections.property("_gerentePresD.tipoanagrafe"), "GERENTEPRESD_TIPOANAGRAFE");
	plist.add(Projections.property("_gerentePresD.nominativo"), "GERENTEPRESD_NOMINATIVO");
	plist.add(Projections.property("_gerentePresD.nome"), "GERENTEPRESD_NOME");
	plist.add(Projections.property("_gerentePresD.codicefiscale"), "GERENTEPRESD_CODICEFISCALE");
	plist.add(Projections.property("_gerentePresD.email"), "GERENTEPRESD_EMAIL");
	plist.add(Projections.property("_gerentePresDFormaGiuridica.formagiuridica"), "GERENTEPRESD_FORMAGIURIDICA");
	plist.add(Projections.property("_gerentePresD.partitaiva"), "GERENTEPRESD_PARTITAIVA");
	plist.add(Projections.property("_gerentePresD.tipologia"), "GERENTEPRESD_TIPOLOGIA");
	plist.add(Projections.property("_gerentePresD.flagDisabilitato"), "GERENTEPRESD_FLAGDISABILITATO");
	plist.add(Projections.property("_gerentePresD.indirizzo"), "GERENTEPRESD_INDIRIZZO");
	plist.add(Projections.property("_gerentePresD.cap"), "GERENTEPRESD_CAP");
	plist.add(Projections.property("_gerentePresD.citta"), "GERENTEPRESD_CITTA");
	plist.add(Projections.property("_gerentePresD.provincia"), "GERENTEPRESD_PROVINCIA");
	plist.add(Projections.property("_gerentePresD.numiscrrea"), "GERENTEPRESD_NUMISCRREA");
	plist.add(Projections.property("_gerentePresD.dataiscrrea"), "GERENTEPRESD_DATAISCRREA");
	plist.add(Projections.property("_gerentePresD.telefono"), "GERENTEPRESD_TELEFONO");
	// gerente concessionario della giornata
	plist.add(Projections.property("_gerenteConcPresD.id.codice"), "GERENTECONCPRESD_ID_CODICE");
	plist.add(Projections.property("_gerenteConcPresD.tipoanagrafe"), "GERENTECONCPRESD_TIPOANAGRAFE");
	plist.add(Projections.property("_gerenteConcPresD.nominativo"), "GERENTECONCPRESD_NOMINATIVO");
	plist.add(Projections.property("_gerenteConcPresD.nome"), "GERENTECONCPRESD_NOME");
	plist.add(Projections.property("_gerenteConcPresD.codicefiscale"), "GERENTECONCPRESD_CODICEFISCALE");
	plist.add(Projections.property("_gerenteConcPresD.email"), "GERENTECONCPRESD_EMAIL");
	plist.add(Projections.property("_gerenteConcPresDFormaGiuridica.formagiuridica"), "GERENTECONCPRESD_FORMAGIURIDICA");
	plist.add(Projections.property("_gerenteConcPresD.partitaiva"), "GERENTECONCPRESD_PARTITAIVA");
	plist.add(Projections.property("_gerenteConcPresD.tipologia"), "GERENTECONCPRESD_TIPOLOGIA");
	plist.add(Projections.property("_gerenteConcPresD.flagDisabilitato"), "GERENTECONCPRESD_FLAGDISABILITATO");
	plist.add(Projections.property("_gerenteConcPresD.indirizzo"), "GERENTECONCPRESD_INDIRIZZO");
	plist.add(Projections.property("_gerenteConcPresD.cap"), "GERENTECONCPRESD_CAP");
	plist.add(Projections.property("_gerenteConcPresD.citta"), "GERENTECONCPRESD_CITTA");
	plist.add(Projections.property("_gerenteConcPresD.provincia"), "GERENTECONCPRESD_PROVINCIA");
	plist.add(Projections.property("_gerenteConcPresD.numiscrrea"), "GERENTECONCPRESD_NUMISCRREA");
	plist.add(Projections.property("_gerenteConcPresD.dataiscrrea"), "GERENTECONCPRESD_DATAISCRREA");
	plist.add(Projections.property("_gerenteConcPresD.telefono"), "GERENTECONCPRESD_TELEFONO");
	//
	criteria.setProjection(plist);
	criteria.setResultTransformer(new IgnoreCaseAliasToBeanResultTransformer(MercatipresenzeDDTO.class));
	List<Object> list = getHibernateTemplate().findByCriteria(criteria);
	MercatipresenzeDDTO presenze = new MercatipresenzeDDTO();
	if (!list.isEmpty()) {
	    presenze = (MercatipresenzeDDTO) list.get(0);
	}
	return presenze;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<MercatipresenzeDDTO> findByMercatipresenzaT(Integer codiceMercatopresenzaT) {

	DetachedCriteria criteria = getIdcomuneCriteria();
	// pongo le condizioni di where
	criteria.add(Restrictions.eq("mercatiPresenzeT.id.codice", codiceMercatopresenzaT));
	//
	criteria.createAlias("posteggio", "_posteggio", Criteria.LEFT_JOIN);
	criteria.createAlias("_posteggio.tipoSpazio", "_posteggioTipoSpazio", Criteria.LEFT_JOIN);
	criteria.createAlias("_posteggio.posteggiSettori", "_posteggiSettori", Criteria.LEFT_JOIN);
	//
	criteria.createAlias("posteggioRinunciato", "_posteggioRinunciato", Criteria.LEFT_JOIN);
	//
	criteria.createAlias("occupante", "_occupante", Criteria.LEFT_JOIN);
	criteria.createAlias("_occupante.formagiuridica", "_occupanteFormaGiuridica", Criteria.LEFT_JOIN);
	//
	criteria.createAlias("concessionario", "_concessionario", Criteria.LEFT_JOIN);
	criteria.createAlias("_concessionario.formagiuridica", "_concessionarioFormaGiuridica", Criteria.LEFT_JOIN);
	//
	criteria.createAlias("autorizzazioni", "_aut", Criteria.LEFT_JOIN);
	criteria.createAlias("_aut.autorizcomune", "_autAutorizComune", Criteria.LEFT_JOIN);
	criteria.createAlias("_aut.tipologiaregistro", "_autTipologiaRegistro", Criteria.LEFT_JOIN);
	// left join aut_csi
	criteria.createAlias("_aut.autorizzazioniCsis", "_autorizzazioniCsis", Criteria.LEFT_JOIN);
	criteria.createAlias("_autorizzazioniCsis.anagrafe", "_gerente", Criteria.LEFT_JOIN);
	criteria.createAlias("_autorizzazioniCsis.autPrecedenteComune", "_autPrecCom", Criteria.LEFT_JOIN);
	criteria.createAlias("_gerente.formagiuridica", "_gerenteformagiuridica", Criteria.LEFT_JOIN);
	//
	criteria.createAlias("autorizzazioneConcessionarioAssente", "_autConcAssente", Criteria.LEFT_JOIN);
	criteria.createAlias("_autConcAssente.autorizcomune", "_autConcAssenteAutorizComune", Criteria.LEFT_JOIN);
	criteria.createAlias("_autConcAssente.tipologiaregistro", "_autConcAssenteTipologiaRegistro", Criteria.LEFT_JOIN);
	// left join comune_aut_originale ( singolo campo della tabella autororizzazione)
	criteria.createAlias("_autConcAssente.autorigComune", "_autorigComune", Criteria.LEFT_JOIN);
	//.
	// left join aut_csi concessionari
	criteria.createAlias("_autConcAssente.autorizzazioniCsis", "_autConcCsis", Criteria.LEFT_JOIN);
	criteria.createAlias("_autConcCsis.anagrafe", "_gerenteConc", Criteria.LEFT_JOIN);
	criteria.createAlias("_gerenteConc.formagiuridica", "_gerenteConformagiuridica", Criteria.LEFT_JOIN);
	// .. left join collaboratore (FK_CODICECOLLABORATORE)
	criteria.createAlias("collaboratore", "_collaboratore", Criteria.LEFT_JOIN);
	criteria.createAlias("_collaboratore.formagiuridica", "_collaboratoreFormaGiuridica", Criteria.LEFT_JOIN);
	//..
	criteria.createAlias("mercatiSpunte", "_mercatiSpunte", Criteria.LEFT_JOIN);
	// GERENTI DELLO SPUNTISTA E DEL CONCESSIONARIO DELLA GIORNATA
	criteria.createAlias("gerenteSpuntista", "_gerentePresD", Criteria.LEFT_JOIN);
	criteria.createAlias("_gerentePresD.formagiuridica", "_gerentePresDFormaGiuridica", Criteria.LEFT_JOIN);
	criteria.createAlias("gerenteConcessionario", "_gerenteConcPresD", Criteria.LEFT_JOIN);
	criteria.createAlias("_gerenteConcPresD.formagiuridica", "_gerenteConcPresDFormaGiuridica", Criteria.LEFT_JOIN);
	// order by codiceposteggio
	criteria.addOrder(Order.asc("_posteggio.codiceposteggio"));
	//
	ProjectionList plist = Projections.projectionList();
	plist.add(Projections.property("id.codice"), "ID_CODICE");
	plist.add(Projections.property("flagAssenzaGiust"), "FLAGASSENZAGIUST");
	plist.add(Projections.property("catMerc"), "CATMERC");
	plist.add(Projections.property("spuntista"), "SPUNTISTA");
	plist.add(Projections.property("motivazione"), "MOTIVAZIONE");
	plist.add(Projections.property("proprietario"), "PROPRIETARIO");
	//
	plist.add(Projections.property("numeropresenze"), "NUMEROPRESENZE");
	//
	//
	plist.add(Projections.property("_posteggio.id.codice"), "POSTEGGIO_ID_CODICE");
	plist.add(Projections.property("_posteggio.codiceposteggio"), "POSTEGGIO_CODICEPOSTEGGIO");
	plist.add(Projections.property("_posteggio.note"), "POSTEGGIO_NOTE");
	plist.add(Projections.property("_posteggio.superficie"), "POSTEGGIO_SUPERFICIE");
	plist.add(Projections.property("_posteggio.lunghezza"), "POSTEGGIO_LUNGHEZZA");
	plist.add(Projections.property("_posteggio.larghezza"), "POSTEGGIO_LARGHEZZA");
	plist.add(Projections.property("_posteggioTipoSpazio.tipospazio"), "POSTEGGIO_TIPOSPAZIO");
	plist.add(Projections.property("_posteggio.identificativoPercorso"), "POSTEGGIO_IDENTIFICATIVOPERCORSO");
	plist.add(Projections.property("_posteggiSettori.codicesettore"), "POSTEGGIO_CODICESETTORE");
	plist.add(Projections.property("_posteggiSettori.settore"), "POSTEGGIO_DESCRIZIONESETTORE");
	plist.add(Projections.property("importo"), "IMPORTO");
	plist.add(Projections.property("flagRinunciaPresenza"), "FLAGRINUNCIAPRESENZA");
	//
	plist.add(Projections.property("_posteggioRinunciato.codiceposteggio"), "CODPOSTRINUNCIATO");
	//
	plist.add(Projections.property("_occupante.id.codice"), "OCCUPANTE_ID_CODICE");
	plist.add(Projections.property("_occupante.tipoanagrafe"), "OCCUPANTE_TIPOANAGRAFE");
	plist.add(Projections.property("_occupante.nominativo"), "OCCUPANTE_NOMINATIVO");
	plist.add(Projections.property("_occupante.nome"), "OCCUPANTE_NOME");
	plist.add(Projections.property("_occupante.codicefiscale"), "OCCUPANTE_CODICEFISCALE");
	plist.add(Projections.property("_occupante.email"), "OCCUPANTE_EMAIL");
	plist.add(Projections.property("_occupanteFormaGiuridica.formagiuridica"), "OCCUPANTE_FORMAGIURIDICA");
	plist.add(Projections.property("_occupante.partitaiva"), "OCCUPANTE_PARTITAIVA");
	plist.add(Projections.property("_occupante.tipologia"), "OCCUPANTE_TIPOLOGIA");
	plist.add(Projections.property("_occupante.flagDisabilitato"), "OCCUPANTE_FLAGDISABILITATO");
	// i
	plist.add(Projections.property("_occupante.indirizzo"), "OCCUPANTE_INDIRIZZO");
	plist.add(Projections.property("_occupante.cap"), "OCCUPANTE_CAP");
	plist.add(Projections.property("_occupante.citta"), "OCCUPANTE_CITTA");
	plist.add(Projections.property("_occupante.provincia"), "OCCUPANTE_PROVINCIA");
	plist.add(Projections.property("_occupante.numiscrrea"), "OCCUPANTE_NUMISCRREA");
	plist.add(Projections.property("_occupante.dataiscrrea"), "OCCUPANTE_DATAISCRREA");
	plist.add(Projections.property("_occupante.telefono"), "OCCUPANTE_TELEFONO");
	//
	plist.add(Projections.property("_concessionario.id.codice"), "CONCESSIONARIO_ID_CODICE");
	plist.add(Projections.property("_concessionario.tipoanagrafe"), "CONCESSIONARIO_TIPOANAGRAFE");
	plist.add(Projections.property("_concessionario.nominativo"), "CONCESSIONARIO_NOMINATIVO");
	plist.add(Projections.property("_concessionario.nome"), "CONCESSIONARIO_NOME");
	plist.add(Projections.property("_concessionario.codicefiscale"), "CONCESSIONARIO_CODICEFISCALE");
	plist.add(Projections.property("_concessionario.email"), "CONCESSIONARIO_EMAIL");
	plist.add(Projections.property("_concessionarioFormaGiuridica.formagiuridica"), "CONCESSIONARIO_FORMAGIURIDICA");
	plist.add(Projections.property("_concessionario.partitaiva"), "CONCESSIONARIO_PARTITAIVA");
	plist.add(Projections.property("_concessionario.tipologia"), "CONCESSIONARIO_TIPOLOGIA");
	plist.add(Projections.property("_concessionario.flagDisabilitato"), "CONCESSIONARIO_FLAGDISABILITATO");
	// i
	plist.add(Projections.property("_concessionario.indirizzo"), "CONCESSIONARIO_INDIRIZZO");
	plist.add(Projections.property("_concessionario.cap"), "CONCESSIONARIO_CAP");
	plist.add(Projections.property("_concessionario.citta"), "CONCESSIONARIO_CITTA");
	plist.add(Projections.property("_concessionario.provincia"), "CONCESSIONARIO_PROVINCIA");
	plist.add(Projections.property("_concessionario.numiscrrea"), "CONCESSIONARIO_NUMISCRREA");
	plist.add(Projections.property("_concessionario.dataiscrrea"), "CONCESSIONARIO_DATAISCRREA");
	plist.add(Projections.property("_concessionario.telefono"), "CONCESSIONARIO_TELEFONO");
	//
	plist.add(Projections.property("_aut.id.codice"), "AUTORIZZAZIONI_ID_CODICE");
	plist.add(Projections.property("_aut.autoriznumero"), "AUTORIZZAZIONI_AUTORIZNUMERO");
	plist.add(Projections.property("_aut.autorizdata"), "AUTORIZZAZIONI_AUTORIZDATA");
	plist.add(Projections.property("_aut.note"), "AUTORIZZAZIONI_NOTE");
	plist.add(Projections.property("_aut.noteSistema"), "AUTORIZZAZIONI_NOTESISTEMA");
	plist.add(Projections.property("_autAutorizComune.comune"), "AUTORIZZAZIONI_AUTORIZCOMUNE");
	plist.add(Projections.property("_autTipologiaRegistro.trDescrizione"), "AUTORIZZAZIONI_TIPOLOGIAREGISTRO");
	//
	plist.add(Projections.property("_autConcAssente.id.codice"), "AUTORIZZAZIONECONCESSIONARIOASSENTE_ID_CODICE");
	plist.add(Projections.property("_autConcAssente.autoriznumero"), "AUTORIZZAZIONECONCESSIONARIOASSENTE_AUTORIZNUMERO");
	plist.add(Projections.property("_autConcAssente.autorizdata"), "AUTORIZZAZIONECONCESSIONARIOASSENTE_AUTORIZDATA");
	plist.add(Projections.property("_autConcAssente.dataCessazione"), "AUTORIZZAZIONECONCESSIONARIOASSENTE_DATACESSAZIONE");
	plist.add(Projections.property("_autConcAssenteAutorizComune.comune"), "AUTORIZZAZIONECONCESSIONARIOASSENTE_AUTORIZCOMUNE");
	plist.add(Projections.property("_autConcAssenteTipologiaRegistro.trDescrizione"), "AUTORIZZAZIONECONCESSIONARIOASSENTE_TIPOLOGIAREGISTRO");
	plist.add(Projections.property("_autConcAssente.note"), "AUTORIZZAZIONECONCESSIONARIOASSENTE_NOTE");
	plist.add(Projections.property("_autConcAssente.noteSistema"), "AUTORIZZAZIONECONCESSIONARIOASSENTE_NOTESISTEMA");
	// campi aut originaria del concessionario
	/**
	 * AUTORIGNUMERO, AUTORIGDATA, AUTORIGCOMUNE
	 */
	plist.add(Projections.property("_autConcAssente.autorigNumero"), "AUTORIZZAZIONECONCESSIONARIOASSENTE_AUTORIGNUMERO");
	plist.add(Projections.property("_autConcAssente.autorigData"), "AUTORIZZAZIONECONCESSIONARIOASSENTE_AUTORIGDATA");
	plist.add(Projections.property("_autorigComune.comune"), "AUTORIZZAZIONECONCESSIONARIOASSENTE_AUTORIGCOMUNE");
	//
	plist.add(Projections.property("_mercatiSpunte.descrizione"), "FASESPUNTA");
	plist.add(Projections.property("flagPagato"), "FLAGPAGATO");
	//
	plist.add(Projections.property("_gerente.id.codice"), "GERENTE_ID_CODICE");
	plist.add(Projections.property("_gerente.tipoanagrafe"), "GERENTE_TIPOANAGRAFE");
	plist.add(Projections.property("_gerente.nominativo"), "GERENTE_NOMINATIVO");
	plist.add(Projections.property("_gerente.nome"), "GERENTE_NOME");
	plist.add(Projections.property("_gerente.codicefiscale"), "GERENTE_CODICEFISCALE");
	plist.add(Projections.property("_gerente.email"), "GERENTE_EMAIL");
	plist.add(Projections.property("_gerenteformagiuridica.formagiuridica"), "GERENTE_FORMAGIURIDICA");
	plist.add(Projections.property("_gerente.partitaiva"), "GERENTE_PARTITAIVA");
	plist.add(Projections.property("_gerente.tipologia"), "GERENTE_TIPOLOGIA");
	plist.add(Projections.property("_gerente.flagDisabilitato"), "GERENTE_FLAGDISABILITATO");
	// i 
	plist.add(Projections.property("_gerente.indirizzo"), "GERENTE_INDIRIZZO");
	plist.add(Projections.property("_gerente.cap"), "GERENTE_CAP");
	plist.add(Projections.property("_gerente.citta"), "GERENTE_CITTA");
	plist.add(Projections.property("_gerente.provincia"), "GERENTE_PROVINCIA");
	plist.add(Projections.property("_gerente.numiscrrea"), "GERENTE_NUMISCRREA");
	plist.add(Projections.property("_gerente.dataiscrrea"), "GERENTE_DATAISCRREA");
	plist.add(Projections.property("_gerente.telefono"), "GERENTE_TELEFONO");
	//
	plist.add(Projections.property("_autorizzazioniCsis.statoAutorizzazione"), "STATOAUTORIZZAZIONE");
	plist.add(Projections.property("_autorizzazioniCsis.statoWarning"), "STATOWARNING");
	plist.add(Projections.property("_autorizzazioniCsis.dataSospDa"), "DATASOSPDA");
	plist.add(Projections.property("_autorizzazioniCsis.dataSospA"), "DATASOSPA");
	plist.add(Projections.property("_autorizzazioniCsis.dataFineGerenza"), "DATAFINEGERENZA");
	plist.add(Projections.property("_autorizzazioniCsis.causaleSospensione"), "CAUSALESOSPENSIONE");
	plist.add(Projections.property("_autorizzazioniCsis.validaSpunta"), "VALIDASPUNTA");
	plist.add(Projections.property("_autorizzazioniCsis.dataInizioGerenza"), "DATAINIZIOGERENZA");
	plist.add(Projections.property("_autorizzazioniCsis.autPrecedenteNumero"), "AUTPRECEDENTENUMERO");
	plist.add(Projections.property("_autorizzazioniCsis.autPrecedenteData"), "AUTPRECEDENTEDATA");
	plist.add(Projections.property("_autPrecCom.comune"), "AUTPRECEDENTECOMUNE");
	plist.add(Projections.property("_autorizzazioniCsis.protocollo"), "PROTOCOLLOAUT");
	plist.add(Projections.property("_autorizzazioniCsis.dataProtocollo"), "DATAPROTOCOLLOAUT");
	//. projection su autorizzazione csi concessionario
	plist.add(Projections.property("_gerenteConc.id.codice"), "GERENTECON_ID_CODICE");
	plist.add(Projections.property("_gerenteConc.tipoanagrafe"), "GERENTECON_TIPOANAGRAFE");
	plist.add(Projections.property("_gerenteConc.nominativo"), "GERENTECON_NOMINATIVO");
	plist.add(Projections.property("_gerenteConc.nome"), "GERENTECON_NOME");
	plist.add(Projections.property("_gerenteConc.codicefiscale"), "GERENTECON_CODICEFISCALE");
	plist.add(Projections.property("_gerenteConc.email"), "GERENTECON_EMAIL");
	plist.add(Projections.property("_gerenteformagiuridica.formagiuridica"), "GERENTECON_FORMAGIURIDICA");
	plist.add(Projections.property("_gerenteConc.partitaiva"), "GERENTECON_PARTITAIVA");
	plist.add(Projections.property("_gerenteConc.tipologia"), "GERENTECON_TIPOLOGIA");
	plist.add(Projections.property("_gerenteConc.flagDisabilitato"), "GERENTECON_FLAGDISABILITATO");
	// i 
	plist.add(Projections.property("_gerenteConc.indirizzo"), "GERENTECON_INDIRIZZO");
	plist.add(Projections.property("_gerenteConc.cap"), "GERENTECON_CAP");
	plist.add(Projections.property("_gerenteConc.citta"), "GERENTECON_CITTA");
	plist.add(Projections.property("_gerenteConc.provincia"), "GERENTECON_PROVINCIA");
	plist.add(Projections.property("_gerenteConc.numiscrrea"), "GERENTECON_NUMISCRREA");
	plist.add(Projections.property("_gerenteConc.dataiscrrea"), "GERENTECON_DATAISCRREA");
	plist.add(Projections.property("_gerenteConc.telefono"), "GERENTECON_TELEFONO");
	plist.add(Projections.property("_autConcCsis.statoAutorizzazione"), "STATOAUTORIZZAZIONECONC");
	plist.add(Projections.property("_autConcCsis.statoWarning"), "STATOWARNINGCON");
	plist.add(Projections.property("_autConcCsis.dataSospDa"), "DATASOSPDACON");
	plist.add(Projections.property("_autConcCsis.dataSospA"), "DATASOSPACON");
	plist.add(Projections.property("_autConcCsis.dataFineGerenza"), "DATAFINEGERENZACON");
	plist.add(Projections.property("_autConcCsis.causaleSospensione"), "CAUSALESOSPENSIONECON");
	plist.add(Projections.property("_autConcCsis.validaSpunta"), "VALIDASPUNTACON");
	plist.add(Projections.property("_autConcCsis.dataInizioGerenza"), "DATAINIZIOGERENZACON");
	plist.add(Projections.property("_autConcCsis.protocollo"), "PROTOCOLLOAUTCON");
	plist.add(Projections.property("_autConcCsis.dataProtocollo"), "DATAPROTOCOLLOAUTCON");
	// projection collaboratore
	//
	plist.add(Projections.property("_collaboratore.id.codice"), "COLLABORATORE_ID_CODICE");
	plist.add(Projections.property("_collaboratore.tipoanagrafe"), "COLLABORATORE_TIPOANAGRAFE");
	plist.add(Projections.property("_collaboratore.nominativo"), "COLLABORATORE_NOMINATIVO");
	plist.add(Projections.property("_collaboratore.nome"), "COLLABORATORE_NOME");
	plist.add(Projections.property("_collaboratore.codicefiscale"), "COLLABORATORE_CODICEFISCALE");
	plist.add(Projections.property("_collaboratore.email"), "COLLABORATORE_EMAIL");
	plist.add(Projections.property("_collaboratoreFormaGiuridica.formagiuridica"), "COLLABORATORE_FORMAGIURIDICA");
	plist.add(Projections.property("_collaboratore.partitaiva"), "COLLABORATORE_PARTITAIVA");
	plist.add(Projections.property("_collaboratore.tipologia"), "COLLABORATORE_TIPOLOGIA");
	plist.add(Projections.property("_collaboratore.flagDisabilitato"), "COLLABORATORE_FLAGDISABILITATO");
	// i
	plist.add(Projections.property("_collaboratore.indirizzo"), "COLLABORATORE_INDIRIZZO");
	plist.add(Projections.property("_collaboratore.cap"), "COLLABORATORE_CAP");
	plist.add(Projections.property("_collaboratore.citta"), "COLLABORATORE_CITTA");
	plist.add(Projections.property("_collaboratore.provincia"), "COLLABORATORE_PROVINCIA");
	plist.add(Projections.property("_collaboratore.numiscrrea"), "COLLABORATORE_NUMISCRREA");
	plist.add(Projections.property("_collaboratore.dataiscrrea"), "COLLABORATORE_DATAISCRREA");
	plist.add(Projections.property("_collaboratore.telefono"), "COLLABORATORE_TELEFONO");
	// gerente spuntista della giornata
	plist.add(Projections.property("_gerentePresD.id.codice"), "GERENTEPRESD_ID_CODICE");
	plist.add(Projections.property("_gerentePresD.tipoanagrafe"), "GERENTEPRESD_TIPOANAGRAFE");
	plist.add(Projections.property("_gerentePresD.nominativo"), "GERENTEPRESD_NOMINATIVO");
	plist.add(Projections.property("_gerentePresD.nome"), "GERENTEPRESD_NOME");
	plist.add(Projections.property("_gerentePresD.codicefiscale"), "GERENTEPRESD_CODICEFISCALE");
	plist.add(Projections.property("_gerentePresD.email"), "GERENTEPRESD_EMAIL");
	plist.add(Projections.property("_gerentePresDFormaGiuridica.formagiuridica"), "GERENTEPRESD_FORMAGIURIDICA");
	plist.add(Projections.property("_gerentePresD.partitaiva"), "GERENTEPRESD_PARTITAIVA");
	plist.add(Projections.property("_gerentePresD.tipologia"), "GERENTEPRESD_TIPOLOGIA");
	plist.add(Projections.property("_gerentePresD.flagDisabilitato"), "GERENTEPRESD_FLAGDISABILITATO");
	plist.add(Projections.property("_gerentePresD.indirizzo"), "GERENTEPRESD_INDIRIZZO");
	plist.add(Projections.property("_gerentePresD.cap"), "GERENTEPRESD_CAP");
	plist.add(Projections.property("_gerentePresD.citta"), "GERENTEPRESD_CITTA");
	plist.add(Projections.property("_gerentePresD.provincia"), "GERENTEPRESD_PROVINCIA");
	plist.add(Projections.property("_gerentePresD.numiscrrea"), "GERENTEPRESD_NUMISCRREA");
	plist.add(Projections.property("_gerentePresD.dataiscrrea"), "GERENTEPRESD_DATAISCRREA");
	plist.add(Projections.property("_gerentePresD.telefono"), "GERENTEPRESD_TELEFONO");
	// gerente concessionario della giornata
	plist.add(Projections.property("_gerenteConcPresD.id.codice"), "GERENTECONCPRESD_ID_CODICE");
	plist.add(Projections.property("_gerenteConcPresD.tipoanagrafe"), "GERENTECONCPRESD_TIPOANAGRAFE");
	plist.add(Projections.property("_gerenteConcPresD.nominativo"), "GERENTECONCPRESD_NOMINATIVO");
	plist.add(Projections.property("_gerenteConcPresD.nome"), "GERENTECONCPRESD_NOME");
	plist.add(Projections.property("_gerenteConcPresD.codicefiscale"), "GERENTECONCPRESD_CODICEFISCALE");
	plist.add(Projections.property("_gerenteConcPresD.email"), "GERENTECONCPRESD_EMAIL");
	plist.add(Projections.property("_gerenteConcPresDFormaGiuridica.formagiuridica"), "GERENTECONCPRESD_FORMAGIURIDICA");
	plist.add(Projections.property("_gerenteConcPresD.partitaiva"), "GERENTECONCPRESD_PARTITAIVA");
	plist.add(Projections.property("_gerenteConcPresD.tipologia"), "GERENTECONCPRESD_TIPOLOGIA");
	plist.add(Projections.property("_gerenteConcPresD.flagDisabilitato"), "GERENTECONCPRESD_FLAGDISABILITATO");
	plist.add(Projections.property("_gerenteConcPresD.indirizzo"), "GERENTECONCPRESD_INDIRIZZO");
	plist.add(Projections.property("_gerenteConcPresD.cap"), "GERENTECONCPRESD_CAP");
	plist.add(Projections.property("_gerenteConcPresD.citta"), "GERENTECONCPRESD_CITTA");
	plist.add(Projections.property("_gerenteConcPresD.provincia"), "GERENTECONCPRESD_PROVINCIA");
	plist.add(Projections.property("_gerenteConcPresD.numiscrrea"), "GERENTECONCPRESD_NUMISCRREA");
	plist.add(Projections.property("_gerenteConcPresD.dataiscrrea"), "GERENTECONCPRESD_DATAISCRREA");
	plist.add(Projections.property("_gerenteConcPresD.telefono"), "GERENTECONCPRESD_TELEFONO");
	//
	criteria.setProjection(plist);
	//
	criteria.setResultTransformer(new IgnoreCaseAliasToBeanResultTransformer(MercatipresenzeDDTO.class));
	List<MercatipresenzeDDTO> list = getHibernateTemplate().findByCriteria(criteria);
	return list;
    }

    @Override
    @SuppressWarnings("unchecked")
    public MercatipresenzeD findByMercatiPosteggio(MercatiD posteggio) {

	DetachedCriteria criteria = getIdcomuneCriteria();
	criteria.add(Restrictions.eq("posteggio.id.codice", posteggio.getId().getCodice()));
	List<MercatipresenzeD> list = getHibernateTemplate().findByCriteria(criteria);
	if (list.isEmpty()) {
	    return null;
	}
	return list.get(0);
    }

    @SuppressWarnings("unchecked")
    @Override
    public MercatiPresenzeDTO findSommaDellePresenzeDaiCalendari(Autorizzazioni autorizzazione, Mercati mercato, MercatiUso uso, MercatiD posteggio,
	    String catMerc, Integer anno, MercatipresenzeT giorno, boolean sommaAssenzeGiustificate) {

	DetachedCriteria criteria = getIdcomuneCriteria();
	criteria.createCriteria("mercatiPresenzeT", "_mercatiPresenzeT", DetachedCriteria.INNER_JOIN);
	criteria.createCriteria("_mercatiPresenzeT.mercato", "_mercato", DetachedCriteria.INNER_JOIN);
	if (uso != null) {
	    criteria.createCriteria("_mercatiPresenzeT.mercatoUso", "_uso", DetachedCriteria.INNER_JOIN);
	}
	criteria.add(Restrictions.eq("_mercatiPresenzeT.software.codice", ORMHelper.getSoftware()));
	criteria.add(Restrictions.eq("_mercato.id.codice", mercato.getId().getCodice()));
	if (uso != null) {
	    criteria.add(Restrictions.eq("_uso.id.codice", uso.getId().getCodice()));
	}
	if (sommaAssenzeGiustificate) {
	    criteria.add(Restrictions.eq("autorizzazioneConcessionarioAssente.id.codice", autorizzazione.getId().getCodice()));
	    criteria.add(Restrictions.eq("flagAssenzaGiust", true));
	} else {
	    criteria.add(Restrictions.eq("autorizzazioni.id.codice", autorizzazione.getId().getCodice()));
	}
	if (StringUtils.isNotBlank(catMerc)) {
	    criteria.add(Restrictions.eq("catMerc", catMerc));
	}
	if (anno != null && anno.shortValue() > 0) {
	    criteria.add(Restrictions.eq("_mercatiPresenzeT.anno", anno));
	}
	if (giorno != null) {
	    criteria.add(Restrictions.eq("_mercatiPresenzeT.dataRegistrazione", giorno.getDataRegistrazione()));
	}
	if (posteggio != null) {
	    criteria.add(Restrictions.eq("posteggio.id.codice", posteggio.getId().getCodice()));
	}
	criteria.add(Restrictions.eq("_mercatiPresenzeT.flagConteggiaPresAss", Boolean.TRUE));
	ProjectionList projectionList = Projections.projectionList();
	projectionList.add(Projections.sqlProjection("sum(proprietario) as presenzeComeProprietario", new String[] { "presenzeComeProprietario" },
		new Type[] { Hibernate.INTEGER }));
	projectionList
		.add(Projections.sqlProjection("sum(numeropresenze) as presenze", new String[] { "presenze" }, new Type[] { Hibernate.INTEGER }));
	criteria.setProjection(projectionList);
	criteria.setResultTransformer(Transformers.aliasToBean(MercatiPresenzeDTO.class));
	List<Object> list = getHibernateTemplate().findByCriteria(criteria);
	MercatiPresenzeDTO presenze = new MercatiPresenzeDTO();
	if (!list.isEmpty()) {
	    presenze = (MercatiPresenzeDTO) list.get(0);
	}
	return presenze;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<PresenzeDaConsolidareHelper> findPresenzeDaConsolidarePerAnno(Integer codiceMercato, Integer anno) {

	Session session = this.getSession(false);
	SessionFactoryImplementor sessimpl = (SessionFactoryImplementor) getSessionFactory().getCurrentSession().getSessionFactory();
	String schema = StringUtils.defaultIfEmpty(sessimpl.getSettings().getDefaultSchemaName(), "");
	StringBuilder sql = new StringBuilder();
	sql.append("select sum(numeropresenze) as numeropresenze, ");
	sql.append("  sum(md.proprietario) as numeropresenzeproprietario, ");
	sql.append("  codiceanagrafe as codiceanagrafe, ");
	sql.append("  md.fk_autorizzazioni_id as codiceautorizzazione ");
	sql.append("from ").append(schema).append(".mercatipresenze_d md ");
	sql.append("inner join ").append(schema).append(".mercatipresenze_t mt ");
	sql.append("on md.idcomune=mt.idcomune ");
	sql.append("and md.fkidtestata=mt.id ");
	sql.append("where mt.idcomune=? ");
	sql.append("and mt.fkcodicemercato=? ");
	sql.append("and mt.anno=? ");
	sql.append("and mt.flag_conteggia_pres_ass=? ");
	sql.append("and (not md.fk_autorizzazioni_id is null )");
	sql.append("and (not md.codiceanagrafe is null )");
	sql.append("group by codiceanagrafe, ");
	sql.append("  md.fk_autorizzazioni_id");
	SQLQuery q = session.createSQLQuery(sql.toString());
	q.setString(0, ORMHelper.getIdcomune());
	q.setInteger(1, codiceMercato);
	q.setInteger(2, anno);
	q.setInteger(3, 1); // flag_conteggia_pres_ass=true
	q.addScalar("numeropresenze", Hibernate.INTEGER);
	q.addScalar("numeropresenzeproprietario", Hibernate.INTEGER);
	q.addScalar("codiceanagrafe", Hibernate.INTEGER);
	q.addScalar("codiceautorizzazione", Hibernate.INTEGER);
	q.setResultTransformer(Transformers.aliasToBean(PresenzeDaConsolidareHelper.class));
	return (List<PresenzeDaConsolidareHelper>) q.list();
    }

    @SuppressWarnings("unchecked")
    @Override
    public MercatipresenzeD findUltimaPresenzaPerMercatoAndAutorizzazione(Integer codiceMercato, Integer anno, Integer codiceAutorizzazione) {

	DetachedCriteria criteria = getIdcomuneCriteria();
	criteria.createAlias("occupante", "_occupante");
	criteria.createAlias("autorizzazioni", "_autorizzazioni");
	criteria.createAlias("mercatiPresenzeT", "_mercatiPresenzeT");
	criteria.createAlias("_mercatiPresenzeT.mercato", "_mercato");
	criteria.add(Restrictions.eq("_mercato.id.codice", codiceMercato));
	criteria.add(Restrictions.eq("_mercatiPresenzeT.anno", anno));
	criteria.add(Restrictions.eq("_autorizzazioni.id.codice", codiceAutorizzazione));
	criteria.add(Restrictions.eq("_mercatiPresenzeT.flagConteggiaPresAss", Boolean.TRUE));// flagConteggiaPresAss=true
	criteria.addOrder(Order.desc("_mercatiPresenzeT.dataRegistrazione"));
	List<MercatipresenzeD> list = getHibernateTemplate().findByCriteria(criteria, 0, 3);
	if (list.isEmpty()) {
	    return null;
	}
	return list.get(0);
    }

    @Override
    public void updateAggiornaAZeroTutteLePresenze(Integer codiceMercato, Integer anno) {

	Session session = this.getSession(false);
	SessionFactoryImplementor sessimpl = (SessionFactoryImplementor) getSessionFactory().getCurrentSession().getSessionFactory();
	String schema = StringUtils.defaultIfEmpty(sessimpl.getSettings().getDefaultSchemaName(), "");
	StringBuilder sql = new StringBuilder();
	sql.append("update ").append(schema).append(".mercatipresenze_d md ");
	sql.append("set md.numeropresenze=0, ");
	sql.append("  md.proprietario=0 ");
	sql.append("where md.idcomune=? ");
	sql.append("and fkidtestata in ");
	sql.append("  (select id ");
	sql.append("  from ").append(schema).append(".mercatipresenze_t mt ");
	sql.append("  where mt.idcomune=? ");
	sql.append("  and mt.fkcodicemercato=? ");
	sql.append("  and mt.anno =? ");
	sql.append("  ) ");
	sql.append("and ((not md.fk_autorizzazioni_id is null ) ");
	sql.append("and (not md.codiceanagrafe is null ))");
	SQLQuery q = session.createSQLQuery(sql.toString());
	q.setString(0, ORMHelper.getIdcomune());
	q.setString(1, ORMHelper.getIdcomune());
	q.setInteger(2, codiceMercato);
	q.setInteger(3, anno);
	q.executeUpdate();
    }

    @SuppressWarnings("unchecked")
    @Override
    public void updateAzzeraPresenzeByAutorizzazioneAndMercato(Integer codiceAutorizzazione, Integer codiceMercato, Integer codiceuso) {

	DetachedCriteria criteria = getIdcomuneCriteria();
	// alias
	criteria.createAlias("mercatiPresenzeT", "_mercatiPresenzeT");
	criteria.createAlias("_mercatiPresenzeT.mercato", "_mercati");
	criteria.createAlias("_mercatiPresenzeT.mercatoUso", "_mercatiuso");
	criteria.createAlias("autorizzazioni", "_autorizzazioni");
	// Projection
	ProjectionList plist = Projections.projectionList();
	plist.add(Projections.property("id.codice"), "ID_CODICE");
	criteria.setProjection(plist);
	// condizioni di where
	criteria.add(Restrictions.eq("_mercati.id.codice", codiceMercato));
	criteria.add(Restrictions.eq("_mercatiuso.id.codice", codiceuso));
	criteria.add(Restrictions.eq("_autorizzazioni.id.codice", codiceAutorizzazione));
	log.debug("updateAzzeraPresenzeByAutorizzazioneAndMercato# Recupero presenze da annullare per aut = {}, mercato = {}, giorno = {}",
		new Object[] { codiceAutorizzazione, codiceMercato, codiceuso });
	List<Integer> list = (List<Integer>) getHibernateTemplate().findByCriteria(criteria);
	log.debug("updateAzzeraPresenzeByAutorizzazioneAndMercato# Metto a zero il valore presenza per i record trovati (Annullo presneza) ");
	for (Integer integer : list) {
	    updatePresenza(integer, false);
	}
	log.debug("updateAzzeraPresenzeByAutorizzazioneAndMercato# Recupero le presenze storico aut = {}, mercato = {}, giorno = {}",
		new Object[] { codiceAutorizzazione, codiceMercato, codiceuso });
	//
    }

    private void updatePresenza(Integer codiceMercatopresenzaD, boolean presente) {

	Integer assenteOrPresente = BooleanUtils.toInteger(presente);
	if (codiceMercatopresenzaD == null) {
	    throw new RuntimeException("updatePresenza: il parametro codiceMercatopresenzaD passato è nullo");
	}
	if (codiceMercatopresenzaD != null) {
	    String hql = "update MercatipresenzeD set numeropresenze = ? where id.idcomune = ? and id.codice=?";
	    int i = getHibernateTemplate().bulkUpdate(hql, new Object[] { assenteOrPresente, ORMHelper.getIdcomune(), codiceMercatopresenzaD });
	    if (i != 1) {
		throw new RuntimeException("La query di aggiornamento della presenza di MercatopresenzaD   :[" +
			codiceMercatopresenzaD +
			"] con valore [" +
			assenteOrPresente +
			"] ha influito su " +
			i +
			" record");
	    }
	}
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<MercatipresenzeDPagamentiDTO> findPagamentiByCf(String cfOccupante) {

	DetachedCriteria criteria = getIdcomuneCriteria();
	// pongo le condizioni di where
	criteria.add(Restrictions.eq("_occupante.codicefiscale", cfOccupante));
	criteria.add(Restrictions.gt("importo", BigDecimal.ZERO));
	criteria.add(Restrictions.eq("_mercati.software.codice", ORMHelper.getSoftware()));
	//
	criteria.createAlias("mercatiPresenzeT", "_mercatiPresenzeT", Criteria.INNER_JOIN);
	criteria.createAlias("_mercatiPresenzeT.mercato", "_mercati", Criteria.INNER_JOIN);
	criteria.createAlias("_mercatiPresenzeT.mercatoUso", "_mercatiuso", Criteria.INNER_JOIN);
	criteria.createAlias("posteggio", "_posteggio", Criteria.LEFT_JOIN);
	criteria.createAlias("_posteggio.tipoSpazio", "_posteggioTipoSpazio", Criteria.LEFT_JOIN);
	criteria.createAlias("_posteggio.posteggiSettori", "_posteggiSettori", Criteria.LEFT_JOIN);
	//
	criteria.createAlias("posteggioRinunciato", "_posteggioRinunciato", Criteria.LEFT_JOIN);
	//
	criteria.createAlias("tipimodalitapagamento", "_tipimodalitapagamento", Criteria.LEFT_JOIN);
	//
	criteria.createAlias("occupante", "_occupante", Criteria.LEFT_JOIN);
	criteria.createAlias("_occupante.formagiuridica", "_occupanteFormaGiuridica", Criteria.LEFT_JOIN);
	//
	criteria.createAlias("concessionario", "_concessionario", Criteria.LEFT_JOIN);
	criteria.createAlias("_concessionario.formagiuridica", "_concessionarioFormaGiuridica", Criteria.LEFT_JOIN);
	//
	criteria.createAlias("autorizzazioni", "_aut", Criteria.LEFT_JOIN);
	criteria.createAlias("_aut.autorizcomune", "_autAutorizComune", Criteria.LEFT_JOIN);
	criteria.createAlias("_aut.tipologiaregistro", "_autTipologiaRegistro", Criteria.LEFT_JOIN);
	//
	criteria.createAlias("autorizzazioneConcessionarioAssente", "_autConcAssente", Criteria.LEFT_JOIN);
	criteria.createAlias("_autConcAssente.autorizcomune", "_autConcAssenteAutorizComune", Criteria.LEFT_JOIN);
	criteria.createAlias("_autConcAssente.tipologiaregistro", "_autConcAssenteTipologiaRegistro", Criteria.LEFT_JOIN);
	// order by codiceposteggio
	criteria.addOrder(Order.asc("_posteggio.codiceposteggio"));
	//
	ProjectionList plist = Projections.projectionList();
	plist.add(Projections.property("id.codice"), "ID_CODICE");
	plist.add(Projections.property("flagAssenzaGiust"), "FLAGASSENZAGIUST");
	plist.add(Projections.property("catMerc"), "CATMERC");
	plist.add(Projections.property("spuntista"), "SPUNTISTA");
	plist.add(Projections.property("motivazione"), "MOTIVAZIONE");
	plist.add(Projections.property("proprietario"), "PROPRIETARIO");
	//
	plist.add(Projections.property("_posteggio.id.codice"), "POSTEGGIO_ID_CODICE");
	plist.add(Projections.property("_posteggio.codiceposteggio"), "POSTEGGIO_CODICEPOSTEGGIO");
	plist.add(Projections.property("_posteggio.note"), "POSTEGGIO_NOTE");
	plist.add(Projections.property("_posteggio.superficie"), "POSTEGGIO_SUPERFICIE");
	plist.add(Projections.property("_posteggio.lunghezza"), "POSTEGGIO_LUNGHEZZA");
	plist.add(Projections.property("_posteggio.larghezza"), "POSTEGGIO_LARGHEZZA");
	plist.add(Projections.property("_posteggioTipoSpazio.tipospazio"), "POSTEGGIO_TIPOSPAZIO");
	plist.add(Projections.property("_posteggio.identificativoPercorso"), "POSTEGGIO_IDENTIFICATIVOPERCORSO");
	plist.add(Projections.property("_posteggiSettori.codicesettore"), "POSTEGGIO_CODICESETTORE");
	plist.add(Projections.property("_posteggiSettori.settore"), "POSTEGGIO_DESCRIZIONESETTORE");
	plist.add(Projections.property("importo"), "IMPORTO");
	plist.add(Projections.property("flagRinunciaPresenza"), "FLAGRINUNCIAPRESENZA");
	//
	plist.add(Projections.property("_posteggioRinunciato.codiceposteggio"), "CODPOSTRINUNCIATO");
	//
	plist.add(Projections.property("_occupante.id.codice"), "OCCUPANTE_ID_CODICE");
	plist.add(Projections.property("_occupante.tipoanagrafe"), "OCCUPANTE_TIPOANAGRAFE");
	plist.add(Projections.property("_occupante.nominativo"), "OCCUPANTE_NOMINATIVO");
	plist.add(Projections.property("_occupante.nome"), "OCCUPANTE_NOME");
	plist.add(Projections.property("_occupante.codicefiscale"), "OCCUPANTE_CODICEFISCALE");
	plist.add(Projections.property("_occupante.email"), "OCCUPANTE_EMAIL");
	plist.add(Projections.property("_occupanteFormaGiuridica.formagiuridica"), "OCCUPANTE_FORMAGIURIDICA");
	plist.add(Projections.property("_occupante.partitaiva"), "OCCUPANTE_PARTITAIVA");
	plist.add(Projections.property("_occupante.tipologia"), "OCCUPANTE_TIPOLOGIA");
	plist.add(Projections.property("_occupante.flagDisabilitato"), "OCCUPANTE_FLAGDISABILITATO");
	// i
	plist.add(Projections.property("_occupante.indirizzo"), "OCCUPANTE_INDIRIZZO");
	plist.add(Projections.property("_occupante.cap"), "OCCUPANTE_CAP");
	plist.add(Projections.property("_occupante.citta"), "OCCUPANTE_CITTA");
	plist.add(Projections.property("_occupante.provincia"), "OCCUPANTE_PROVINCIA");
	plist.add(Projections.property("_occupante.numiscrrea"), "OCCUPANTE_NUMISCRREA");
	plist.add(Projections.property("_occupante.dataiscrrea"), "OCCUPANTE_DATAISCRREA");
	plist.add(Projections.property("_occupante.telefono"), "OCCUPANTE_TELEFONO");
	//
	plist.add(Projections.property("_concessionario.id.codice"), "CONCESSIONARIO_ID_CODICE");
	plist.add(Projections.property("_concessionario.tipoanagrafe"), "CONCESSIONARIO_TIPOANAGRAFE");
	plist.add(Projections.property("_concessionario.nominativo"), "CONCESSIONARIO_NOMINATIVO");
	plist.add(Projections.property("_concessionario.nome"), "CONCESSIONARIO_NOME");
	plist.add(Projections.property("_concessionario.codicefiscale"), "CONCESSIONARIO_CODICEFISCALE");
	plist.add(Projections.property("_concessionario.email"), "CONCESSIONARIO_EMAIL");
	plist.add(Projections.property("_concessionarioFormaGiuridica.formagiuridica"), "CONCESSIONARIO_FORMAGIURIDICA");
	plist.add(Projections.property("_concessionario.partitaiva"), "CONCESSIONARIO_PARTITAIVA");
	plist.add(Projections.property("_concessionario.tipologia"), "CONCESSIONARIO_TIPOLOGIA");
	plist.add(Projections.property("_concessionario.flagDisabilitato"), "CONCESSIONARIO_FLAGDISABILITATO");
	// i
	plist.add(Projections.property("_concessionario.indirizzo"), "CONCESSIONARIO_INDIRIZZO");
	plist.add(Projections.property("_concessionario.cap"), "CONCESSIONARIO_CAP");
	plist.add(Projections.property("_concessionario.citta"), "CONCESSIONARIO_CITTA");
	plist.add(Projections.property("_concessionario.provincia"), "CONCESSIONARIO_PROVINCIA");
	plist.add(Projections.property("_concessionario.numiscrrea"), "CONCESSIONARIO_NUMISCRREA");
	plist.add(Projections.property("_concessionario.dataiscrrea"), "CONCESSIONARIO_DATAISCRREA");
	plist.add(Projections.property("_concessionario.telefono"), "CONCESSIONARIO_TELEFONO");
	//
	plist.add(Projections.property("_aut.id.codice"), "AUTORIZZAZIONI_ID_CODICE");
	plist.add(Projections.property("_aut.autoriznumero"), "AUTORIZZAZIONI_AUTORIZNUMERO");
	plist.add(Projections.property("_aut.autorizdata"), "AUTORIZZAZIONI_AUTORIZDATA");
	plist.add(Projections.property("_autAutorizComune.comune"), "AUTORIZZAZIONI_AUTORIZCOMUNE");
	//
	plist.add(Projections.property("_aut.note"), "AUTORIZZAZIONI_NOTE");
	plist.add(Projections.property("_aut.noteSistema"), "AUTORIZZAZIONI_NOTESISTEMA");
	//
	plist.add(Projections.property("_autTipologiaRegistro.trDescrizione"), "AUTORIZZAZIONI_TIPOLOGIAREGISTRO");
	//
	plist.add(Projections.property("_autConcAssente.id.codice"), "AUTORIZZAZIONECONCESSIONARIOASSENTE_ID_CODICE");
	plist.add(Projections.property("_autConcAssente.autoriznumero"), "AUTORIZZAZIONECONCESSIONARIOASSENTE_AUTORIZNUMERO");
	plist.add(Projections.property("_autConcAssente.autorizdata"), "AUTORIZZAZIONECONCESSIONARIOASSENTE_AUTORIZDATA");
	plist.add(Projections.property("_autConcAssenteAutorizComune.comune"), "AUTORIZZAZIONECONCESSIONARIOASSENTE_AUTORIZCOMUNE");
	plist.add(Projections.property("_autConcAssenteTipologiaRegistro.trDescrizione"), "AUTORIZZAZIONECONCESSIONARIOASSENTE_TIPOLOGIAREGISTRO");
	plist.add(Projections.property("flagPagato"), "FLAGPAGATO");
	plist.add(Projections.property("importo"), "IMPORTO");
	plist.add(Projections.property("riferimentiPagamento"), "RIFERIMENTIPAGAMENTO");
	plist.add(Projections.property("_tipimodalitapagamento.mpDescrestesa"), "MODALITAPAGAMENTO");
	plist.add(Projections.property("_mercati.id.codice"), "MERCATI_ID_CODICE");
	plist.add(Projections.property("_mercati.descrizione"), "MERCATI_DESCRIZIONE");
	plist.add(Projections.property("_mercatiPresenzeT.dataRegistrazione"), "DATAREGISTRAZIONE");
	plist.add(Projections.property("_mercatiuso.id.codice"), "GIORNO_ID_CODICE");
	plist.add(Projections.property("_mercatiuso.descrizione"), "GIORNO_DESCRIZIONE");
	criteria.setProjection(plist);
	criteria.addOrder(Order.asc("_mercati.descrizione"));
	criteria.addOrder(Order.asc("_mercatiPresenzeT.dataRegistrazione"));
	//
	criteria.setResultTransformer(new IgnoreCaseAliasToBeanResultTransformer(MercatipresenzeDPagamentiDTO.class));
	List<MercatipresenzeDPagamentiDTO> list = getHibernateTemplate().findByCriteria(criteria);
	return list;
    }

    @Override
    public ListaAutorizzazioniPerPeriodo findPresenzeConcessionariRestHelper(Integer idGiornata, Integer codiceMercato, Integer codiceUso,
	    Date inizioMese, Date fineMese) {

	List<ChiaveValoreBean<BigDecimal, String>> findPresenzeRestHelper = findPresenzeRestHelper(idGiornata, codiceMercato, codiceUso, inizioMese,
		fineMese, 0);
	return new ListaAutorizzazioniPerPeriodo(findPresenzeRestHelper);
    }

    @Override
    public ListaAutorizzazioniPerPeriodo findPresenzeSpuntistiRestHelper(Integer idGiornata, Integer codiceMercato, Integer codiceUso,
	    Date inizioMese, Date fineMese) {

	List<ChiaveValoreBean<BigDecimal, String>> findPresenzeRestHelper = findPresenzeRestHelper(idGiornata, codiceMercato, codiceUso, inizioMese,
		fineMese, 1);
	return new ListaAutorizzazioniPerPeriodo(findPresenzeRestHelper);
    }

    @SuppressWarnings("unchecked")
    private List<ChiaveValoreBean<BigDecimal, String>> findPresenzeRestHelper(Integer idGiornata, Integer codiceMercato, Integer codiceUso,
	    Date inizioMese, Date fineMese, int valoreSpuntista) {

	Date dataGiornataPrecedente = mercatipresenzeTDAO.findDataGiornataPrecedenteMercato(idGiornata);
	if (dataGiornataPrecedente == null) {
	    Calendar dataNonValida = Calendar.getInstance();
	    dataNonValida.set(Calendar.YEAR, 1900);
	    dataGiornataPrecedente = dataNonValida.getTime();
	}
	MercatiConfigurazione mConfigurazione = mercatiConfigurazioneDAO.findConfigurazione();
	Mercati m = mercatipresenzeTDAO.getById(Mercati.class, new PkId(codiceMercato));
	Integer tipoManifestazione = m.getManifestazione().getComportamentoPresenze();
	boolean isConfigurazioneSpuntistiMercatiAttiva = MercatiConfigurazione.checkConfigurazioneGradSpuntisti(tipoManifestazione, mConfigurazione);
	Session session = this.getSession(false);
	SQLQuery q = session.createSQLQuery(getSqlSpuntisti(isConfigurazioneSpuntistiMercatiAttiva));
	int pos = 0;
	//	    autorizzazioni.idcomune = ? " + //
	//		    "    AND   autorizzazioni.flag_attiva = ? " + //
	//		    "    AND   presenze_UM.spuntista = ? " + //
	//		    "    AND   presenze_UM.fkcodicemercato = ?" + //
	//		    "    AND   presenze_UM.fkidmercatiuso = ?" + //
	//		    "    AND   presenze_UM.dataregistrazione = ?" + //
	//	    
	q.setString(pos++, ORMHelper.getIdcomune());
	q.setInteger(pos++, 1);
	q.setInteger(pos++, valoreSpuntista);
	q.setInteger(pos++, codiceMercato);
	q.setInteger(pos++, codiceUso);
	q.setDate(pos++, dataGiornataPrecedente);
	if (!isConfigurazioneSpuntistiMercatiAttiva) {
	    // oggi
	    q.setString(pos++, ORMHelper.getIdcomune());
	    // BEGIN Re: mercato-issues | 2024-IM112135 - WO1 - Merc@TO : App vigili - visualizzazione autorizzazione se cessata (#7)
	    // PER LA GIORNATA DI MERCATO NON DEVO ESCLUDERE LE AUTORIZZAZIONI NON ATTIVE
	    // ALTRIMENTI NELL'APP VIGILI NON VEDO GLI SPUNTISTI REGISTRATI NELLE GIORNATE STORICHE
	    // q.setInteger(pos++, 1);
	    // END Re: mercato-issues | 2024-IM112135 - WO1 - Merc@TO : App vigili - visualizzazione autorizzazione se cessata (#7)
	    q.setInteger(pos++, valoreSpuntista);
	    q.setInteger(pos++, idGiornata);
	}
	// anno scorso
	q.setString(pos++, ORMHelper.getIdcomune());
	q.setInteger(pos++, 1);
	q.setInteger(pos++, codiceMercato);
	q.setInteger(pos++, codiceUso);
	q.setInteger(pos++, valoreSpuntista);
	q.setDate(pos++, inizioMese);
	q.setDate(pos++, fineMese);
	// almeno uno
	q.setString(pos++, ORMHelper.getIdcomune());
	q.setInteger(pos++, 1);
	q.setInteger(pos++, codiceMercato);
	q.setInteger(pos++, codiceUso);
	q.setInteger(pos++, valoreSpuntista);
	// storico
	q.setString(pos++, ORMHelper.getIdcomune());
	q.setInteger(pos++, 1);
	q.setInteger(pos++, codiceMercato);
	q.setInteger(pos++, codiceUso);
	if (isConfigurazioneSpuntistiMercatiAttiva) {
	    //	    sql.append(" WHERE  spuntisti_mercati.idcomune=? AND ");
	    //	    sql.append(" spuntisti_mercati.fk_mercato=? AND ");
	    //	    sql.append(" spuntisti_mercati.fk_mercato_uso=? AND ");
	    //	    sql.append(" AND spuntisti_mercati.flg_attivo = ?");
	    q.setString(pos++, ORMHelper.getIdcomune());
	    q.setInteger(pos++, codiceMercato);
	    q.setInteger(pos++, codiceUso);
	    q.setInteger(pos++, 1);
	    // oggi
	    q.setString(pos++, ORMHelper.getIdcomune());
	    q.setInteger(pos++, 1);
	    q.setInteger(pos++, valoreSpuntista);
	    q.setInteger(pos++, idGiornata);
	}
	// 
	q.addScalar("chiave", Hibernate.BIG_DECIMAL);
	q.addScalar("valore", Hibernate.STRING);
	ChiaveValoreBean<BigDecimal, String> c = new ChiaveValoreBean<BigDecimal, String>();
	q.setResultTransformer(Transformers.aliasToBean(c.getClass()));
	List<ChiaveValoreBean<BigDecimal, String>> l = (List<ChiaveValoreBean<BigDecimal, String>>) q.list();
	return l;
    }

    private String getSqlSpuntisti(boolean isConfigurazioneMercatiSpuntistiAttiva) {

	StringBuilder sql = new StringBuilder("");
	if (isConfigurazioneMercatiSpuntistiAttiva) {
	    sql.append("SELECT ");
	    sql.append("fk_autorizzazione AS chiave ");
	    sql.append(",COALESCE(tipo,NULL,'ALMENO_UNA') AS valore FROM spuntisti_mercati LEFT JOIN (");
	} else {
	    sql.append("SELECT idautorizzazione as chiave,tipo as valore ");
	    sql.append("FROM (");
	}
	sql.append("SELECT ");
	sql.append(" autorizzazioni.id AS idautorizzazione, ");
	sql.append(" 'ULTIMO_MERCATO' AS tipo ");
	sql.append(" FROM ");
	sql.append(" autorizzazioni ");
	sql.append(" INNER JOIN mercatipresenze_d presenze_UM ON presenze_UM.idcomune = autorizzazioni.idcomune ");
	sql.append(" AND presenze_UM.fk_autorizzazioni_id = autorizzazioni.id ");
	sql.append(" INNER JOIN mercatipresenze_t MPTUM ON ");
	sql.append(" MPTUM.IDCOMUNE = autorizzazioni.IDCOMUNE AND MPTUM.ID = presenze_UM.FKIDTESTATA ");
	sql.append(" WHERE ");
	sql.append(" autorizzazioni.idcomune = ? ");
	sql.append(" AND autorizzazioni.flag_attiva = ? ");
	sql.append(" AND presenze_UM.spuntista = ? ");
	sql.append(" AND MPTUM.fkcodicemercato = ?");
	sql.append(" AND MPTUM.fkidmercatiuso = ?");
	sql.append(" AND MPTUM.dataregistrazione = ?");
	sql.append(" GROUP BY autorizzazioni.id, 'ULTIMO_MERCATO' ");
	if (!isConfigurazioneMercatiSpuntistiAttiva) {
	    sql.append(" UNION ");
	    sql.append(" SELECT");
	    sql.append(" autorizzazioni.id AS idautorizzazione,");
	    sql.append(" 'OGGI' AS tipo");
	    sql.append(" FROM");
	    sql.append(" autorizzazioni");
	    sql.append(" INNER JOIN mercatipresenze_d presenze_oggi ON presenze_oggi.idcomune = autorizzazioni.idcomune");
	    sql.append(" AND presenze_oggi.fk_autorizzazioni_id = autorizzazioni.id");
	    sql.append(" WHERE");
	    sql.append(" autorizzazioni.idcomune = ?");
	    // BEGIN Re: mercato-issues | 2024-IM112135 - WO1 - Merc@TO : App vigili - visualizzazione autorizzazione se cessata (#7)
	    // PER LA GIORNATA DI MERCATO NON DEVO ESCLUDERE LE AUTORIZZAZIONI NON ATTIVE
	    // ALTRIMENTI NELL'APP VIGILI NON VEDO GLI SPUNTISTI REGISTRATI NELLE GIORNATE STORICHE
	    // s q l . a p p e n d (" AND autorizzazioni.flag_attiva = ?") ;
	    // END Re: mercato-issues | 2024-IM112135 - WO1 - Merc@TO : App vigili - visualizzazione autorizzazione se cessata (#7)
	    sql.append(" AND presenze_oggi.spuntista = ?");
	    sql.append(" AND presenze_oggi.fkidtestata = ?");
	    sql.append(" GROUP BY");
	    sql.append(" autorizzazioni.id,");
	    sql.append(" 'OGGI'");
	}
	sql.append(" UNION");
	sql.append(" SELECT");
	sql.append(" autorizzazioni.id AS idautorizzazione,");
	sql.append(" 'ANNO_SCORSO' AS tipo");
	sql.append(" FROM");
	sql.append(" autorizzazioni");
	sql.append(" INNER JOIN mercatipresenze_d presenze_anno_scorso ON presenze_anno_scorso.idcomune = autorizzazioni.idcomune");
	sql.append(" AND presenze_anno_scorso.fk_autorizzazioni_id = autorizzazioni.id");
	sql.append(" INNER JOIN mercatipresenze_t anno_scorso ON presenze_anno_scorso.idcomune = anno_scorso.idcomune");
	sql.append(" AND presenze_anno_scorso.fkidtestata = anno_scorso.id");
	sql.append(" WHERE");
	sql.append(" autorizzazioni.idcomune = ?");
	sql.append(" AND autorizzazioni.flag_attiva = ?");
	sql.append(" AND anno_scorso.fkcodicemercato = ?");
	sql.append(" AND anno_scorso.fkidmercatiuso = ?");
	sql.append(" AND presenze_anno_scorso.spuntista = ?");
	sql.append(" AND ( anno_scorso.dataregistrazione BETWEEN ? AND ? )");
	sql.append(" GROUP BY");
	sql.append(" autorizzazioni.id,");
	sql.append(" 'ANNO_SCORSO'");
	sql.append(" UNION");
	sql.append(" SELECT");
	sql.append(" autorizzazioni.id AS idautorizzazione,");
	sql.append(" 'ALMENO_UNA' AS tipo");
	sql.append(" FROM");
	sql.append(" autorizzazioni");
	sql.append(" INNER JOIN mercatipresenze_d presenze_tutti_anni ON presenze_tutti_anni.idcomune = autorizzazioni.idcomune");
	sql.append(" AND presenze_tutti_anni.fk_autorizzazioni_id = autorizzazioni.id");
	sql.append(" INNER JOIN mercatipresenze_t tutti_anni ON presenze_tutti_anni.idcomune = tutti_anni.idcomune");
	sql.append(" AND presenze_tutti_anni.fkidtestata = tutti_anni.id");
	sql.append(" WHERE");
	sql.append(" autorizzazioni.idcomune = ?");
	sql.append(" AND autorizzazioni.flag_attiva = ?");
	sql.append(" AND tutti_anni.fkcodicemercato = ?");
	sql.append(" AND tutti_anni.fkidmercatiuso = ?");
	sql.append(" AND presenze_tutti_anni.spuntista = ?");
	sql.append(" GROUP BY");
	sql.append(" autorizzazioni.id,");
	sql.append(" 'ALMENO_UNA'");
	sql.append(" UNION");
	sql.append(" SELECT");
	sql.append(" autorizzazioni.id AS idautorizzazione,");
	sql.append(" 'STORICO' AS tipo");
	sql.append(" FROM");
	sql.append(" autorizzazioni");
	sql.append(" INNER JOIN mercatipresenze_storico storico ON storico.idcomune = autorizzazioni.idcomune");
	sql.append(" AND storico.fk_autorizzazioni_id = autorizzazioni.id");
	sql.append(" WHERE");
	sql.append(" autorizzazioni.idcomune = ?");
	sql.append(" AND autorizzazioni.flag_attiva = ?");
	sql.append(" AND storico.fkcodicemercato = ?");
	sql.append(" AND storico.fkidmercatiuso = ?");
	sql.append(" GROUP BY");
	sql.append(" autorizzazioni.id,");
	sql.append(" 'STORICO'");
	sql.append(" ) autorizzazioni "); //
	if (isConfigurazioneMercatiSpuntistiAttiva) {
	    sql.append(" ON spuntisti_mercati.FK_AUTORIZZAZIONE=autorizzazioni.idautorizzazione ");
	    sql.append(" WHERE  spuntisti_mercati.idcomune=? AND ");
	    sql.append(" spuntisti_mercati.fk_mercato=? AND ");
	    sql.append(" spuntisti_mercati.fk_mercato_uso=? ");
	    sql.append(" AND spuntisti_mercati.flg_attivo = ?");
	    sql.append(" UNION ");
	    sql.append(" SELECT");
	    sql.append(" autorizzazioni.id AS idautorizzazione,");
	    sql.append(" 'OGGI' AS tipo");
	    sql.append(" FROM");
	    sql.append(" autorizzazioni");
	    sql.append(" INNER JOIN mercatipresenze_d presenze_oggi ON presenze_oggi.idcomune = autorizzazioni.idcomune");
	    sql.append(" AND presenze_oggi.fk_autorizzazioni_id = autorizzazioni.id");
	    sql.append(" WHERE");
	    sql.append(" autorizzazioni.idcomune = ?");
	    sql.append(" AND autorizzazioni.flag_attiva = ?");
	    sql.append(" AND presenze_oggi.spuntista = ?");
	    sql.append(" AND presenze_oggi.fkidtestata = ?");
	    sql.append(" GROUP BY");
	    sql.append(" autorizzazioni.id,");
	    sql.append(" 'OGGI' ");
	}
	return sql.toString();
    }

    @Override
    public List<MercatipresenzeDDTO> findByMercatiPresenzeTAndPosteggi(Integer idGiornata, List<Long> idPosteggi) {

	List<Integer> listaId = new ArrayList<Integer>();
	for (Long l : idPosteggi) {
	    if (l != null) {
		listaId.add(l.intValue());
	    }
	}
	return _findListaPosteggi(idGiornata, listaId);
    }

    @SuppressWarnings("unchecked")
    public List<ValoriLivelloServizio> findServiziConfiguratiByGiornataAndPosteggioAndUso(Date dataGiornata, Integer idPosteggio, Integer codiceUso) {

	log.debug("findServiziConfiguratiByGiornataAndPosteggioAndUso: recupero la sessionfactory e la casto a SessionFactoryImplementor");
	SessionFactoryImplementor sessimpl = (SessionFactoryImplementor) getSessionFactory().getCurrentSession().getSessionFactory();
	QueryMercatiDLivelliServizioHelper queryHelper = new QueryMercatiDLivelliServizioHelper(sessimpl, dataGiornata, idPosteggio, codiceUso);
	String sql = queryHelper.buildQuery();
	SQLQuery q = getSession().createSQLQuery(sql);
	queryHelper.setFilterValues(q);
	queryHelper.setScalarProperties(q);
	q.setResultTransformer(Transformers.aliasToBean(ValoriLivelloServizio.class));
	List<ValoriLivelloServizio> result = (List<ValoriLivelloServizio>) q.list();
	return result;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<ValoriLivelloServizio> livelliDiServizioConfiguratiPerGiornataAndPosteggioAndUso(Date dataGiornata, Integer idPosteggio,
	    Integer idUso) {

	log.debug("livelliDiServizioConfiguratiPerGiornata: recupero la sessionfactory e la casto a SessionFactoryImplementor");
	SessionFactoryImplementor sessimpl = (SessionFactoryImplementor) getSessionFactory().getCurrentSession().getSessionFactory();
	QueryMercatiDLivelliServizioHelper queryHelper = new QueryMercatiDLivelliServizioHelper(sessimpl, dataGiornata, idPosteggio, idUso);
	String sql = queryHelper.buildQuery();
	SQLQuery q = getSession().createSQLQuery(sql);
	queryHelper.setFilterValues(q);
	queryHelper.setScalarProperties(q);
	q.setResultTransformer(Transformers.aliasToBean(ValoriLivelloServizio.class));
	List<ValoriLivelloServizio> result = (List<ValoriLivelloServizio>) q.list();
	return result;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Integer> findPresenzeConcessionariSenzaPosizioniDebitorie(Date dataDellaGiornata) {

	String hql = "select " + // 
		"mpd.id as id " + // 
		" from " + // 
		" mercatipresenze_d mpd " + // 
		" inner join " +
		" mercatiPresenze_t mpt " + // 
		" on mpd.idcomune=mpt.idcomune and mpd.fkidtestata=mpt.id " + //
		" inner join " + //
		" mercati mercato " + //
		" on mercato.idcomune=mpt.idcomune and mercato.codicemercato=mpt.fkcodicemercato " + //
		" left join " + //
		" dett_posizione_debitoria " + // 
		" on mpd.idcomune = dett_posizione_debitoria.idcomune AND mpd.fk_pay_pos_deb = dett_posizione_debitoria.id  " + //
		" where " + // 
		" mpt.idcomune = ? " + // 
		" and mpt.dataregistrazione = ? " + // 
		" and mercato.software = ? " + // 
		" and mercato.flag_attiva_nodo_pagam = ? " + // 
		" and mpd.spuntista = ? " + // 
		" and mpd.aut_concessionario is not null " + // 
		" and mpd.aut_concessionario=mpd.fk_autorizzazioni_id " + // 
		" and mpd.fkidposteggio is not null " + // 
		" and dett_posizione_debitoria.iuv is null " + // 
		" and not exists" + //
		" (" + //
		"  select 1" + //
		"  from" + //
		"   borsellino_movimenti" + //
		"  where" + //
		"   borsellino_movimenti.idcomune = mpd.idcomune and " + //
		"   borsellino_movimenti.fkid_mercatipresenzet = mpd.fkidtestata and " + //
		"   borsellino_movimenti.fkid_autorizzazioni = mpd.fk_autorizzazioni_id and " + //
		"   borsellino_movimenti.fkid_mercatid = mpd.fkidposteggio and" +		
		"   borsellino_movimenti.tipo = ? and" + //
		"   borsellino_movimenti.fkid_movimentostorno is null" + //
		" )" + //		
		" and mpt.flag_popola_concessionari=? ";
	SQLQuery q = getSession().createSQLQuery(hql);
	q.setString(0, ORMHelper.getIdcomune());
	q.setDate(1, dataDellaGiornata);
	q.setString(2, ORMHelper.getSoftware());
	q.setInteger(3, 1);
	q.setInteger(4, 0);
	q.setString(5, TipoEnum.USCITA.name());
	q.setInteger(6, 1);
	q.addScalar("id", Hibernate.INTEGER);
	return q.list();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<MercatipresenzeD> findByIdDettPosizioneDebitoria(Integer idDettPosizioneDebitoria) {

	if (idDettPosizioneDebitoria == null) {
	    throw new BusinessValidationException("Il parametro idDettPosizioneDebitoria non può essere nullo");
	}
	DetachedCriteria criteria = getIdcomuneCriteria();
	criteria.add(Restrictions.eq("dettPosizioneDebitoriaId", idDettPosizioneDebitoria));
	return getHibernateTemplate().findByCriteria(criteria);
    }

    @SuppressWarnings({ "unchecked", "unused" })
    @Override
    public List<PresenzeNonPagateBean> findPresenzeNonAssociateAMetodoDiPagamento(Date dalladata, Date alladata, String[] codiceIstat) {

	Session session = this.getSession(false);
	String sql = "" + // 
		"select" + // 
		" mercatipresenze_t.id as idgiornata," + //
		" mercatipresenze_t.dataregistrazione as dataregistrazione," + //
		" mercatipresenze_t.descrizione as descrizionegiornata," + //
		" mercatipresenze_d.id as idpresenza," + //
		" mercatipresenze_d.fkidposteggio as idposteggio," + //
		" mercatipresenze_d.numeropresenze as numeropresenze," + //
		" mercatipresenze_d.codiceanagrafe as codiceanagrafe," + //
		" mercatipresenze_d.spuntista as spuntista," + //
		" mercatipresenze_d.flag_assenza_giust as flagassenzagiustiticata," + //
		" mercatipresenze_d.codiceconcessionario as codiceconcessionario," + //
		" mercatipresenze_d.fk_autorizzazioni_id as fkautorizzazioniid," + //
		" mercatipresenze_d.aut_concessionario as autconcessionario," + //
		" mercatipresenze_d.fk_codiceistat as fkcodiceistat " + //
		"from" + //
		" mercatipresenze_t" + //
		"  inner join mercatipresenze_d on" + //
		"   mercatipresenze_d.idcomune=mercatipresenze_t.idcomune and" + //
		"   mercatipresenze_d.fkidtestata=mercatipresenze_t.id" + //
		"  inner join mercati on" + //
		"   mercati.idcomune=mercatipresenze_t.idcomune and" + //
		"   mercati.codicemercato=mercatipresenze_t.fkcodicemercato" + //
		"  left join borsellino_autorizzazioni on" + //
		"   mercatipresenze_d.idcomune = borsellino_autorizzazioni.idcomune and" + //
		"   mercatipresenze_d.fk_autorizzazioni_id = borsellino_autorizzazioni.fkid_autorizzazioni " + //
		"where" + //
		" mercatipresenze_t.idcomune=? and" + //
		" mercatipresenze_t.software=? and" + //
		" mercatipresenze_t.dataregistrazione between ? and ? and" + //
		" mercatipresenze_d.fkidposteggio is not null and" + //
		" mercatipresenze_d.fk_pay_pos_deb is null and" + //
		" mercatipresenze_d.fk_autorizzazioni_id is not null and" + //
		" (" + //
		"  mercati.flag_attiva_nodo_pagam=? or borsellino_autorizzazioni.fkid_autorizzazioni is not null" + //
		" ) and" + //
		" not exists" + //
		" (" + //
		"  select 1" + //
		"  from" + //
		"   borsellino_movimenti" + //
		"  where" + //
		"   borsellino_movimenti.idcomune = borsellino_autorizzazioni.idcomune and" + //
		"   borsellino_movimenti.fkid_autorizzazioni = borsellino_autorizzazioni.fkid_autorizzazioni and" + //
		"   borsellino_movimenti.idcomune = mercatipresenze_d.idcomune and" + //
		"   borsellino_movimenti.fkid_mercatid = mercatipresenze_d.fkidposteggio and" +
		"   borsellino_movimenti.idcomune = mercatipresenze_t.idcomune and" + //
		"   borsellino_movimenti.fkid_mercatipresenzet = mercatipresenze_t.id and" + //
		"   borsellino_movimenti.tipo = ? and" + //
		"   borsellino_movimenti.fkid_movimentostorno is null" + //
		" )";
	if (codiceIstat != null && codiceIstat.length > 0) {
	    sql += " and mercatipresenze_d.fk_codiceistat in (? ";
	    for (String cod : codiceIstat) {
		sql += ", ?";
	    }
	    sql += " )";
	}
	SQLQuery q = session.createSQLQuery(sql);
	int pos = 0;
	q.setString(pos++, ORMHelper.getIdcomune());
	q.setString(pos++, ORMHelper.getSoftware());
	q.setDate(pos++, dalladata);
	q.setDate(pos++, alladata);
	q.setInteger(pos++, 1);
	q.setString(pos++, TipoEnum.USCITA.name());
	if (codiceIstat != null && codiceIstat.length > 0) {
	    for (String cod : codiceIstat) {
		q.setString(pos++, cod);
	    }
	    q.setString(pos++, "ATTIVITA_NON_ESISTENTE");
	}
	q.addScalar("idgiornata", Hibernate.INTEGER);
	q.addScalar("dataregistrazione", Hibernate.DATE);
	q.addScalar("descrizionegiornata", Hibernate.STRING);
	q.addScalar("idpresenza", Hibernate.INTEGER);
	q.addScalar("idposteggio", Hibernate.INTEGER);
	q.addScalar("numeropresenze", Hibernate.INTEGER);
	q.addScalar("codiceanagrafe", Hibernate.INTEGER);
	q.addScalar("spuntista", Hibernate.BOOLEAN);
	q.addScalar("flagassenzagiustiticata", Hibernate.BOOLEAN);
	q.addScalar("codiceconcessionario", Hibernate.INTEGER);
	q.addScalar("fkautorizzazioniid", Hibernate.INTEGER);
	q.addScalar("fkcodiceistat", Hibernate.STRING);
	q.setResultTransformer(Transformers.aliasToBean(PresenzeNonPagateBean.class));
	List<PresenzeNonPagateBean> l = (List<PresenzeNonPagateBean>) q.list();
	return l;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<MercatiPresenzeConPosDebBean> findPresenzeConInfoPosDeb(Integer[] idAutorizzazioni, String[] stati,
	    Date consideraIPagamentiDallaData) {

	String sql = "SELECT " + //
		"  mercatipresenze_d.id, " + //
		"  dett_posizione_debitoria.id AS iddettposdeb, " + //
		"  dett_posizione_debitoria.stato AS statopostdeb, " + //
		"  mercatipresenze_t.fkcodicemercato AS codicemercato, " + //
		"  mercatipresenze_t.fkidmercatiuso AS codiceuso " + //
		"FROM " + //
		"  mercatipresenze_d " + //
		"  INNER JOIN dett_posizione_debitoria ON dett_posizione_debitoria.idcomune = mercatipresenze_d.idcomune " + //
		"  AND dett_posizione_debitoria.id = mercatipresenze_d.fk_pay_pos_deb " + //
		"  INNER JOIN mercatipresenze_t ON mercatipresenze_t.idcomune = mercatipresenze_d.idcomune " + //
		"  AND mercatipresenze_t.id = mercatipresenze_d.fkidtestata " + //
		"WHERE " + //
		"  mercatipresenze_d.idcomune = ? " + //
		"  AND mercatipresenze_d.fk_autorizzazioni_id IN (LISTA_AUTORIZZAZIONI) " + //
		"  AND mercatipresenze_d.fkidposteggio IS NOT NULL " + //
		"  AND dett_posizione_debitoria.stato IN (LISTA_STATI) " + //
		"  AND mercatipresenze_t.software = ? "; //
	if (consideraIPagamentiDallaData != null) {
	    sql += "  AND mercatipresenze_t.dataregistrazione >= ? "; //
	}
	sql += "ORDER BY " + //
		"  mercatipresenze_t.dataregistrazione";
	String listaAutorizzazioni = "";
	for (int i = 0; i < idAutorizzazioni.length; i++) {
	    listaAutorizzazioni += ",?";
	}
	if (idAutorizzazioni.length > 0) {
	    listaAutorizzazioni = listaAutorizzazioni.substring(1);
	}
	sql = sql.replace("LISTA_AUTORIZZAZIONI", listaAutorizzazioni);
	String listaStati = "";
	for (int i = 0; i < stati.length; i++) {
	    listaStati += ",?";
	}
	if (stati.length > 0) {
	    listaStati = listaStati.substring(1);
	}
	sql = sql.replace("LISTA_STATI", listaStati);
	SQLQuery query = getSession().createSQLQuery(sql).addSynchronizedEntityClass(MercatipresenzeD.class)
		.addSynchronizedEntityClass(DettPosizioneDebitoria.class);
	query.setString(0, ORMHelper.getIdcomune());
	int pos = 1;
	for (Integer aut : idAutorizzazioni) {
	    query.setInteger(pos++, aut);
	}
	for (String stato : stati) {
	    query.setString(pos++, stato);
	}
	query.setString(pos++, ORMHelper.getSoftware());
	if (consideraIPagamentiDallaData != null) {
	    query.setDate(pos++, consideraIPagamentiDallaData);
	}
	query.addScalar("id", Hibernate.INTEGER);
	query.addScalar("iddettposdeb", Hibernate.INTEGER);
	query.addScalar("statopostdeb", Hibernate.STRING);
	query.addScalar("codicemercato", Hibernate.INTEGER);
	query.addScalar("codiceuso", Hibernate.INTEGER);
	query.setResultTransformer(Transformers.aliasToBean(MercatiPresenzeConPosDebBean.class));
	return query.list();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<DettaglioPresenzaComunicazioneModel> findPresenzeScalateDalBorsellino(int idGiornata) {

	String sql = "select" + //
		" borsellino.fkid_anagrafe as codiceAnagrafe," + //
		" mercatipresenze_d.id as idPresenza," + //
		" anagrafe.email," + //
		" anagrafe.pec " + //
		"from" + //
		" borsellino_movimenti" + //
		"  inner join borsellino on borsellino_movimenti.idcomune = borsellino.idcomune and borsellino_movimenti.fkid_borsellino = borsellino.id" + //
		"  inner join anagrafe on borsellino.idcomune = anagrafe.idcomune and borsellino.fkid_anagrafe = anagrafe.codiceanagrafe " + //
		"  inner join mercatipresenze_d on borsellino_movimenti.idcomune = mercatipresenze_d.idcomune and borsellino_movimenti.fkid_mercatipresenzet = mercatipresenze_d.fkidtestata and borsellino_movimenti.fkid_mercatid = mercatipresenze_d.fkidposteggio " + // 
		"where" + // 
		" borsellino_movimenti.idcomune = ? and" + //
		" borsellino_movimenti.fkid_mercatipresenzet = ? and" + //
		" borsellino_movimenti.tipo = ? and" + //
		" borsellino_movimenti.fkid_movimentostorno is null";
	SQLQuery query = getSession().createSQLQuery(sql.toString()).addSynchronizedEntityClass(BorsellinoMovimenti.class);
	query.setString(0, ORMHelper.getIdcomune());
	query.setInteger(1, idGiornata);
	query.setString(2, TipoEnum.USCITA.name());
	query.addScalar("codiceAnagrafe", Hibernate.INTEGER);
	query.addScalar("idPresenza", Hibernate.INTEGER);
	query.addScalar("email", Hibernate.STRING);
	query.addScalar("pec", Hibernate.STRING);
	query.setResultTransformer(Transformers.aliasToBean(DettaglioPresenzaComunicazioneModel.class));
	return query.list();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<MercatipresenzeDBean> findPresenzePerAutorizzazioneDallaData(Integer idAutConc, Date dataCessazioneParam) {

	Date dataCess = Utilities.dateWithoutTime(dataCessazioneParam);
	StringBuilder sql = new StringBuilder();
	sql.append(" select mercatipresenze_d.id as idpresenza ") //
		.append(" ,mercatipresenze_d.fkidtestata as idgiornata ") //
		.append(" ,mercatipresenze_d.fkidposteggio as idposteggio ") //
		.append(" ,mercatipresenze_d.numeropresenze as numeropresenze ") //
		.append(" ,mercatipresenze_d.codiceanagrafe as codiceoccupante ") //
		.append(" ,coalesce(mercatipresenze_d.spuntista,0) as spuntista ") //
		.append(" ,mercatipresenze_d.codiceconcessionario as codiceconcessionario ") //
		.append(" ,mercatipresenze_d.fk_autorizzazioni_id as idautpresenza ") //
		.append(" ,mercatipresenze_d.aut_concessionario as idautconcessionario ") //
		.append(" ,mercatipresenze_d.fk_pay_pos_deb as idposizionedebitoria ") //
		.append(" ,occupante.nominativo as cognomeocc ") //
		.append(" ,occupante.nome as nomeocc ") //
		.append(" ,occupante.codicefiscale as cfocc ") //
		.append(" ,occupante.partitaiva as pivaocc ") //
		.append(" ,concessionario.nominativo as cognomeconc ") //
		.append(" ,concessionario.nome as nomeconc ") //
		.append(" ,concessionario.codicefiscale as cfconc ") //
		.append(" ,concessionario.partitaiva as pivaconc ") //
		.append(" ,mercatipresenze_t.descrizione AS descrizionegiorno ") //
		.append(" ,mercatipresenze_t.dataregistrazione AS datagiornata ") //
		.append(" ,mercati_d.codiceposteggio AS codiceposteggio ") //
		.append(" ,dett_posizione_debitoria.stato AS statoposdeb ") //
		.append(" from mercatipresenze_d inner join ") //
		.append(" mercatipresenze_t on ") //
		.append(" mercatipresenze_t.idcomune=mercatipresenze_d.idcomune and ") //
		.append(" mercatipresenze_t.id=mercatipresenze_d.fkidtestata ") //
		.append(" left join mercati_d on ") //
		.append(" mercati_d.idcomune=mercatipresenze_d.idcomune and ") //
		.append(" mercati_d.idposteggio=mercatipresenze_d.fkidposteggio ") //
		.append(" left join anagrafe occupante on ") //
		.append(" occupante.idcomune=mercatipresenze_d.idcomune and ") //
		.append(" occupante.codiceanagrafe=mercatipresenze_d.codiceanagrafe ") //
		.append(" left join anagrafe concessionario on ") //
		.append(" concessionario.idcomune=mercatipresenze_d.idcomune and ") //
		.append(" concessionario.codiceanagrafe=mercatipresenze_d.codiceconcessionario ") //
		.append(" left join dett_posizione_debitoria on ") //
		.append(" dett_posizione_debitoria.idcomune=mercatipresenze_d.idcomune and  ") //
		.append(" dett_posizione_debitoria.id=mercatipresenze_d.fk_pay_pos_deb ") //
		.append(" where ") //
		.append(" mercatipresenze_t.idcomune=:idcomune ") //
		.append(" and mercatipresenze_t.dataregistrazione>=:data_cessazione ") //
		.append(" and ( mercatipresenze_d.fk_autorizzazioni_id=:id_aut or mercatipresenze_d.aut_concessionario=:id_aut_conc) ")
		.append(" order by mercatipresenze_t.dataregistrazione,mercatipresenze_t.descrizione  ");
	SQLQuery query = getSession().createSQLQuery(sql.toString()).addSynchronizedEntityClass(MercatipresenzeD.class)
		.addSynchronizedEntityClass(Autorizzazioni.class);
	query.setString("idcomune", ORMHelper.getIdcomune());
	query.setDate("data_cessazione", dataCess);
	query.setInteger("id_aut", idAutConc);
	query.setInteger("id_aut_conc", idAutConc);
	query.addScalar("idpresenza", Hibernate.INTEGER);
	query.addScalar("idgiornata", Hibernate.INTEGER);
	query.addScalar("idposteggio", Hibernate.INTEGER);
	query.addScalar("numeropresenze", Hibernate.INTEGER);
	query.addScalar("codiceoccupante", Hibernate.INTEGER);
	query.addScalar("spuntista", Hibernate.BOOLEAN);
	query.addScalar("codiceconcessionario", Hibernate.INTEGER);
	query.addScalar("idautpresenza", Hibernate.INTEGER);
	query.addScalar("idautconcessionario", Hibernate.INTEGER);
	query.addScalar("idposizionedebitoria", Hibernate.INTEGER);
	query.addScalar("cognomeocc", Hibernate.STRING);
	query.addScalar("nomeocc", Hibernate.STRING);
	query.addScalar("cfocc", Hibernate.STRING);
	query.addScalar("pivaocc", Hibernate.STRING);
	query.addScalar("cognomeconc", Hibernate.STRING);
	query.addScalar("nomeconc", Hibernate.STRING);
	query.addScalar("cfconc", Hibernate.STRING);
	query.addScalar("pivaconc", Hibernate.STRING);
	query.addScalar("descrizionegiorno", Hibernate.STRING);
	query.addScalar("datagiornata", Hibernate.DATE);
	query.addScalar("codiceposteggio", Hibernate.STRING);
	query.addScalar("statoposdeb", Hibernate.STRING);
	query.setResultTransformer(Transformers.aliasToBean(MercatipresenzeDBean.class));
	return query.list();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<PosteggioPerAutBean> findAutorizzazioniSganciateDaSIAP(Set<Integer> autSenzaPosteggi) {

	String idx = "MYSQL_INDEX";
	String sql = "select " + //
		" aut_concessionario as idaut " + //
		" ,mercati.descrizione as mercato" + //
		" ,mercati_d.codiceposteggio as codiceposteggio" + //
		" ,mercati_uso.descrizione as uso" + //
		" ,giornisettimana.gs_descrizione as giorno" + //
		" ,manifestazioni.descrizione as tipomanif " + //
		",max(mercatipresenze_t.DATAREGISTRAZIONE) as ultimapresenza " + //
		" from mercatipresenze_d  " +
		idx + //
		" inner join mercati_d on mercati_d.idcomune = mercatipresenze_d.idcomune" + //
		" and mercati_d.idposteggio = mercatipresenze_d.fkidposteggio " + //
		" inner join mercatipresenze_t on " + //
		" mercatipresenze_t.idcomune=mercatipresenze_d.idcomune and " + //
		" mercatipresenze_t.id=mercatipresenze_d.FKIDTESTATA " + //		     
		" inner join mercati on mercati_d.idcomune = mercati.idcomune" + //
		" and mercati_d.fkcodicemercato = mercati.codicemercato" + //
		" inner join mercati_uso on mercati_uso.idcomune = mercati.idcomune" + //
		" and mercati_uso.fkcodicemercato = mercati.codicemercato" + //
		" left join giornisettimana on " + //
		" mercati_uso.fkgsid=giornisettimana.gs_id" + //
		" inner join manifestazioni on " + //
		" manifestazioni.codice=mercati.TIPO_MANIFEST " + // 
		" where mercatipresenze_d.idcomune = ? " + //
		" and aut_concessionario in" + //
		" (?"; //
	for (Integer aut : autSenzaPosteggi) {
	    sql += ",? ";
	}
	sql += " )" + //
		" group by aut_concessionario" + //
		" ,mercati.descrizione" + //
		" ,mercati_d.codiceposteggio" + //
		" ,mercati_uso.descrizione" + //
		" ,fkgsid" + //
		" , giornisettimana.gs_descrizione" + //
		" ,manifestazioni.descrizione" + // 
		" order by aut_concessionario, fkgsid"; //
	String idxQuery = "";
	SessionFactoryImplementor sessimpl = (SessionFactoryImplementor) getSessionFactory().getCurrentSession().getSessionFactory();
	if (DialettoEnum.MYSQL.equals(DialettoEnum.fromHibernateDialect(sessimpl.getDialect().toString()))) {
	    idxQuery = " FORCE INDEX (FK_MERCPRESDAUTCONC_AUT)  ";
	}
	sql = sql.replace(idx, idxQuery);
	SQLQuery query = getSession().createSQLQuery(sql).addSynchronizedEntityClass(MercatipresenzeD.class)
		.addSynchronizedEntityClass(Autorizzazioni.class);
	query.setString(0, ORMHelper.getIdcomune());
	int pos = 1;
	query.setInteger(pos++, -10000);
	for (Integer aut : autSenzaPosteggi) {
	    query.setInteger(pos++, aut);
	}
	query.addScalar("idaut", Hibernate.INTEGER);
	query.addScalar("mercato", Hibernate.STRING);
	query.addScalar("uso", Hibernate.STRING);
	query.addScalar("codiceposteggio", Hibernate.STRING);
	query.addScalar("giorno", Hibernate.STRING);
	query.addScalar("tipomanif", Hibernate.STRING);
	query.addScalar("ultimapresenza", Hibernate.DATE);
	return query.setResultTransformer(Transformers.aliasToBean(PosteggioPerAutBean.class)).list();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Object[]> findPresenzeByAutorizzazione(int idautorizzazione) {

	String sql = "SELECT mercatipresenze_t.dataregistrazione, mercatipresenze_t.fkcodicemercato, mercatipresenze_t.fkIDmercaTIUSO, MERCATIPRESENZE_D.CODICEANAGRAFE, MERCATIPRESENZE_D.FKIDPOSTEGGIO FROM MERCATIPRESENZE_D INNER JOIN mercatipresenze_t ON MERCATIPRESENZE_D.idcomune=mercatipresenze_t.idcomune AND MERCATIPRESENZE_D.fkidtestata=mercatipresenze_t.id WHERE MERCATIPRESENZE_D.IDCOMUNE=? AND fk_autorizzazioni_id =? AND numeropresenze=1";
	SQLQuery q = getSession().createSQLQuery(sql);
	q.addScalar("dataregistrazione", Hibernate.DATE);
	q.addScalar("fkcodicemercato", Hibernate.INTEGER);
	q.addScalar("fkIDmercaTIUSO", Hibernate.INTEGER);
	q.addScalar("CODICEANAGRAFE", Hibernate.INTEGER);
	q.addScalar("FKIDPOSTEGGIO", Hibernate.INTEGER);
	q.setString(0, ORMHelper.getIdcomune());
	q.setInteger(1, idautorizzazione);
	return q.list();
    }
}
