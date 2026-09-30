package it.gruppoinit.pal.gp.core.features.comunicazioni.massive.gen;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Hibernate;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.hibernate.type.TimestampType;
import org.springframework.stereotype.Repository;

import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.dao.impl.BaseDAOImpl;
import it.gruppoinit.pal.gp.core.domain.Amministrazioni;
import it.gruppoinit.pal.gp.core.domain.Anagrafe;
import it.gruppoinit.pal.gp.core.domain.Autorizzazioni;
import it.gruppoinit.pal.gp.core.domain.AutorizzazioniConcessioni;
import it.gruppoinit.pal.gp.core.domain.BollMassiveT;
import it.gruppoinit.pal.gp.core.domain.Comuni;
import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.IstanzeMassiveD;
import it.gruppoinit.pal.gp.core.domain.IstanzeMassiveDIstanze;
import it.gruppoinit.pal.gp.core.domain.IstanzeMassiveT;
import it.gruppoinit.pal.gp.core.domain.MassiveDettaglio;
import it.gruppoinit.pal.gp.core.domain.MassiveTestata;
import it.gruppoinit.pal.gp.core.domain.MercatiMassiveD;
import it.gruppoinit.pal.gp.core.domain.MercatiMassiveDAut;
import it.gruppoinit.pal.gp.core.domain.MercatiMassiveDAutId;
import it.gruppoinit.pal.gp.core.domain.MercatiMassiveT;
import it.gruppoinit.pal.gp.core.domain.Movimenti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Responsabili;
import it.gruppoinit.pal.gp.core.domain.Software;
import it.gruppoinit.pal.gp.core.domain.Tipimovimento;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.common.ISoftwareComuneData;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.common.SoftwareComuneDataBean;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.configurazione.SceltaTipoMailAnagrafeEnum;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.eventi.IEventoMassiva.ContestoComunicazioneEnum;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.gen.custom.IstanzeGroupEnum;

@SuppressWarnings("rawtypes")
@Repository
public class ComunicazioniMassiveGenDAOImpl extends BaseDAOImpl implements IComunicazioniMassiveGenDAO {

    @Override
    public Class getEntityClass() {

	// TODO Auto-generated method stub
	return null;
    }
    /*
     * MERCATI
     */

    @SuppressWarnings("unchecked")
    @Override
    public void collegaRigheMercatiAComunicazioni(int idTestata, ConfigurazioniComunicazioneGen configurazioneComunicazione) {

	MercatiMassiveT entity = new MercatiMassiveT();
	entity.setMassiveTestata((MassiveTestata) getById(MassiveTestata.class, new PkId(idTestata)));
	entity.setSoftware(ORMHelper.getSoftware());
	saveEntity(entity);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void collegaDettaglioMercatoADettaglioComunicazioni(Integer codice, Map<Integer, List<DettaglioRigaGen>> m) {

	Set<Integer> allAut = m.keySet(); //Così mi tengo traccia di tutte le autorizzazioni
	Set<Integer> autgiainserite = new HashSet<Integer>(0);
	for (Map.Entry<Integer, List<DettaglioRigaGen>> entry : m.entrySet()) {
	    MercatiMassiveD entity = new MercatiMassiveD();
	    entity.setMassiveDettaglio((MassiveDettaglio) getById(MassiveDettaglio.class, new PkId(codice)));
	    Autorizzazioni autorizzazione = (Autorizzazioni) getById(Autorizzazioni.class, new PkId(entry.getKey()));
	    Integer codiceautorizzazione = entry.getKey();
	    if (autorizzazione.getAutorizzazioniConcessionisForFkAutconcAutcoll() != null
		    && !autorizzazione.getAutorizzazioniConcessionisForFkAutconcAutcoll().isEmpty()) {
		Autorizzazioni temp = null;
		for (AutorizzazioniConcessioni concessioni : autorizzazione.getAutorizzazioniConcessionisForFkAutconcAutcoll()) {
		    temp = concessioni.getAutorizzazioniByFkAutconcAutatt();
		    break;
		}
		if (temp != null) {
		    codiceautorizzazione = temp.getId().getCodice();
		    if (codiceautorizzazione == null || !allAut.contains(codiceautorizzazione)) {
			codiceautorizzazione = entry.getKey(); //se non è tra i codici presenti resettiamo
		    }
		}
	    } else {
		codiceautorizzazione = entry.getKey();
	    }
	    if (autgiainserite.contains(codiceautorizzazione)) {
		continue; //Se già inserito allora non inserisco di nuovo
	    }
	    entity.setAutorizzazione((Autorizzazioni) getById(Autorizzazioni.class, new PkId(codiceautorizzazione)));
	    saveEntity(entity);
	    autgiainserite.add(codiceautorizzazione);
	    for (DettaglioRigaGen dettaglio : entry.getValue()) {
		MercatiMassiveDAut mmdaut = new MercatiMassiveDAut();
		MercatiMassiveDAutId id = new MercatiMassiveDAutId(UUID.randomUUID().toString());
		mmdaut.setId(id);
		mmdaut.setMercatiMassiveD(entity);
		mmdaut.setFkcodicemercato(dettaglio.getCodiceMercato());
		saveEntity(mmdaut);
	    }
	}
    }

    @Override
    public boolean existsTestata(String sql, Integer idTestata) {

	if (idTestata == null) {
	    throw new IllegalArgumentException(
		    "Impossibile verificare se esiste una comunicazione per la bollettazione senza passare l'id della comunicazione");
	}
	SQLQuery query = getSession().createSQLQuery(sql).addSynchronizedEntityClass(BollMassiveT.class);
	query.setString(0, ORMHelper.getIdcomune());
	query.setInteger(1, idTestata);
	query.addScalar("conteggio", Hibernate.INTEGER);
	return new Integer(query.uniqueResult().toString()) > 0;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<SoftwareComuneDataBean> getMercatoDettaglioDByIdDett(Integer idmassivedettaglio) {

	SQLQuery q = getSession().createSQLQuery(QueriesConstants.SELECTSOFTWAREANDCODICECOMUNEMERCATI);
	q.addScalar("software", Hibernate.STRING);
	q.addScalar("codiceComune", Hibernate.STRING);
	q.setString(0, ORMHelper.getIdcomune());
	q.setInteger(1, idmassivedettaglio);
	q.setResultTransformer(Transformers.aliasToBean(SoftwareComuneDataBean.class));
	return q.list();
    }

    @Override
    public List<ISoftwareComuneData> getSoftwareAndComunePerDettaglioComunicazione(ConfigurazioniComunicazioneGen configurazioniComunicazioneGen) {

	if (configurazioniComunicazioneGen.getContesto() == ContestoComunicazioneEnum.MERCATI) {
	    return getSoftwareAndComunePerDettComMercati(configurazioniComunicazioneGen);
	} else {
	    throw new RuntimeException("No valid contesto found");
	}
    }

    @SuppressWarnings("unchecked")
    private List<ISoftwareComuneData> getSoftwareAndComunePerDettComMercati(ConfigurazioniComunicazioneGen configurazioniComunicazioneGen) {

	SQLQuery q = getSession().createSQLQuery(QueriesUtil.buildQueryMercatiForSoftwareAndComune(configurazioniComunicazioneGen.isConcessionario(),
		configurazioniComunicazioneGen.isSpuntisti(), configurazioniComunicazioneGen.getDataInizio(),
		configurazioniComunicazioneGen.getDataFine(), configurazioniComunicazioneGen.getIdsmercati()));
	q.addScalar("software", Hibernate.STRING);
	q.addScalar("codiceComune", Hibernate.STRING);
	q.setParameter("idcomune", ORMHelper.getIdcomune());
	q.setParameter("software", ORMHelper.getSoftware());
	if (configurazioniComunicazioneGen.getDataInizio() != null) {
	    q.setParameter("dalladata", configurazioniComunicazioneGen.getDataInizio(), new TimestampType());
	}
	if (configurazioniComunicazioneGen.getDataFine() != null) {
	    q.setParameter("alladata", configurazioniComunicazioneGen.getDataFine(), new TimestampType());
	}
	if (configurazioniComunicazioneGen.getIdsmercati() != null && configurazioniComunicazioneGen.getIdsmercati().length > 0) {
	    Set<Integer> idsset = new HashSet<Integer>();
	    for (int idmercato : configurazioniComunicazioneGen.getIdsmercati()) {
		idsset.add(idmercato);
	    }
	    q.setParameterList("idsmercati", idsset);
	}
	q.setResultTransformer(Transformers.aliasToBean(SoftwareComuneDataBean.class));
	return q.list();
    }

    @Override
    public List<DettaglioRigaGen> getDettagli(ConfigurazioniComunicazioneGen configurazioniComunicazioneGen) {

	if (configurazioniComunicazioneGen.getContesto() == ContestoComunicazioneEnum.MERCATI) {
	    return getDettagliForMercati(configurazioniComunicazioneGen);
	} else if (configurazioniComunicazioneGen.getContesto() == ContestoComunicazioneEnum.ISTANZE) {
	    return getDettagliForIstanze(configurazioniComunicazioneGen);
	} else {
	    throw new RuntimeException("no valid Contesto");
	}
    }

    @SuppressWarnings("unchecked")
    private List<DettaglioRigaGen> getDettagliForMercati(ConfigurazioniComunicazioneGen configurazioniComunicazioneGen) {

	SQLQuery q = getSession().createSQLQuery(QueriesUtil.buildQueryMercatiForAutorizzazioni(configurazioniComunicazioneGen.isConcessionario(),
		configurazioniComunicazioneGen.isSpuntisti(), configurazioniComunicazioneGen.getDataInizio(),
		configurazioniComunicazioneGen.getDataFine(), configurazioniComunicazioneGen.getIdsmercati()));
	q.addScalar("codiceAutorizzazione", Hibernate.INTEGER);
	q.addScalar("titolare", Hibernate.INTEGER);
	q.addScalar("occupante", Hibernate.INTEGER);
	q.addScalar("codiceMercato", Hibernate.INTEGER);
	q.setParameter("idcomune", ORMHelper.getIdcomune());
	q.setParameter("software", ORMHelper.getSoftware());
	if (configurazioniComunicazioneGen.getDataInizio() != null) {
	    q.setParameter("dalladata", configurazioniComunicazioneGen.getDataInizio(), new TimestampType());
	}
	if (configurazioniComunicazioneGen.getDataFine() != null) {
	    q.setParameter("alladata", configurazioniComunicazioneGen.getDataFine(), new TimestampType());
	}
	if (configurazioniComunicazioneGen.getIdsmercati() != null && configurazioniComunicazioneGen.getIdsmercati().length > 0) {
	    Set<Integer> idsset = new HashSet<Integer>();
	    for (int idmercato : configurazioniComunicazioneGen.getIdsmercati()) {
		idsset.add(idmercato);
	    }
	    q.setParameterList("idsmercati", idsset);
	}
	q.setResultTransformer(Transformers.aliasToBean(DettaglioRigaGen.class));
	return q.list();
    }

    private String getMailOPec(SceltaTipoMailAnagrafeEnum sceltaTipoMailAnagrafeEnum, String email, String pec) {

	if (sceltaTipoMailAnagrafeEnum == null) {
	    return null;
	}
	switch (sceltaTipoMailAnagrafeEnum) {
	case PEC_O_MAIL:
	    return StringUtils.defaultString(pec, email);
	case SOLO_MAIL:
	    return email;
	case SOLO_PEC:
	    return pec;
	default:
	    break;
	}
	return null;
    }

    @Override
    public Integer getIdMercatoByTestata(Integer idTestata) {

	SQLQuery q = getSession().createSQLQuery(QueriesConstants.SELECTMERCATOBYTESTATA);
	q.addScalar("fkcodicemercato", Hibernate.INTEGER);
	q.setString(0, ORMHelper.getIdcomune());
	q.setInteger(1, idTestata);
	List<Integer> resultList = q.list();
	if (resultList != null && !resultList.isEmpty()) {
	    return resultList.get(0);
	} else {
	    return null;
	}
    }
    /*
     * ISTANZE
     */

    @SuppressWarnings("unchecked")
    @Override
    public void collegaRigheIstanzeAComunicazioni(int idTestata, ConfigurazioniComunicazioneGen configurazioneComunicazione) {

	IstanzeMassiveT entity = new IstanzeMassiveT();
	entity.setMassiveTestata((MassiveTestata) getById(MassiveTestata.class, new PkId(idTestata)));
	entity.setGruppoType(configurazioneComunicazione.isIstanzeGroup() ? IstanzeGroupEnum.ISTANZE.name() : IstanzeGroupEnum.ANAGRAFE.name());
	entity.setSoftware(ORMHelper.getSoftware());
	saveEntity(entity);
    }

    @SuppressWarnings("unchecked")
    private List<DettaglioRigaGen> getDettagliForIstanze(ConfigurazioniComunicazioneGen configurazioniComunicazioneGen) {

	List<DettaglioRigaGen> returnList = new ArrayList<DettaglioRigaGen>();
	for (int idistanza : configurazioniComunicazioneGen.getIdsistanze()) {
	    Istanze istanza = (Istanze) getById(Istanze.class, new PkId(idistanza)); //per ora la facciamo così
	    DettaglioRigaGen dettaglioRigaGen = new DettaglioRigaGen();
	    dettaglioRigaGen.setCodiceIstanza(istanza.getId().getCodice());
	    dettaglioRigaGen.setCodiceAutorizzazione(istanza.getRichiedente().getId().getCodice());
	    //INIZIO
	    String mail = null;
	    if (dettaglioRigaGen.getCodiceAutorizzazione() != null) {
		Anagrafe an = (Anagrafe) this.getById(Anagrafe.class, new PkId(dettaglioRigaGen.getCodiceAutorizzazione()));
		mail = getMailOPec(configurazioniComunicazioneGen.getSceltaTipoMailAnagrafe(), an.getEmail(), an.getPec());
	    } else {
		throw new RuntimeException("Riga non valida, non sono presenti anagrafiche, amministrazioni o responsabili");
	    }
	    if (StringUtils.isBlank(mail) && configurazioniComunicazioneGen.isEscludiDestinatariSenzaMail()
		    && !configurazioniComunicazioneGen.isAppioInvio()) {
		continue;
	    }
	    //FINE
	    dettaglioRigaGen.setMail(mail);
	    returnList.add(dettaglioRigaGen);
	}
	return returnList;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void collegaDettaglioIstanzeADettaglioComunicazioni(Integer codice, Set<Integer> idistanze, boolean isMovimenti, String tipiMovimento,
	    Integer codiceAmministrazione) {

	IstanzeMassiveD entity = new IstanzeMassiveD();
	entity.setMassiveDettaglio((MassiveDettaglio) getById(MassiveDettaglio.class, new PkId(codice)));
	entity.setFlagMovimento(isMovimenti);
	entity.setFktipimovimento(tipiMovimento);
	entity.setFkcodiceamministrazione(codiceAmministrazione);
	saveEntity(entity);
	for (Integer idistanza : idistanze) {
	    IstanzeMassiveDIstanze newentity = new IstanzeMassiveDIstanze();
	    newentity.setIstanzeMassiveD(entity);
	    newentity.setFkcodiceistanza(idistanza);
	    saveEntity(newentity);
	}
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<IstanzeMassiveDIstanze> findIstanzeMassiveDIstanze(Integer idmassivedettaglio) {

	SQLQuery query = getSession().createSQLQuery(QueriesConstants.COMUNICAZIONISELECTISTANZEFORMOVIMENTI);
	query.addEntity(IstanzeMassiveDIstanze.class);
	query.setString(0, ORMHelper.getIdcomune());
	query.setInteger(1, idmassivedettaglio);
	return query.list();
    }

    @SuppressWarnings("unchecked")
    @Override
    public void saveMovimento(IstanzeMassiveDIstanze istanzeMassiveDIstanza, Tipimovimento tipomovimento, Amministrazioni amministrazioni,
	    Responsabili responsabile, Istanze istanza) {

	Movimenti movimentoTemp = new Movimenti();
	movimentoTemp.setData(new java.util.Date());
	movimentoTemp.setTipomovimento(tipomovimento);
	movimentoTemp.setAmministrazioni(amministrazioni);
	movimentoTemp.setResponsabile(responsabile);
	movimentoTemp.setIstanza(istanza);
	saveEntity(movimentoTemp);
	istanzeMassiveDIstanza.setFkmovimento(movimentoTemp.getId().getCodice());
	saveEntity(istanzeMassiveDIstanza);
	this.flush();
	this.commit();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Istanze> getIstanzeDettaglioDByIdDett(Integer idmassivedettaglio) {

	SQLQuery query = getSession().createSQLQuery(QueriesConstants.SELECTSOFTWAREANDCODICECOMUNEISTANZEAPPIO);
	query.addScalar("software", Hibernate.STRING);
	query.addScalar("codiceComune", Hibernate.STRING);
	query.setString(0, ORMHelper.getIdcomune());
	query.setInteger(1, idmassivedettaglio);
	List<Object[]> tempList = query.list();
	if (tempList == null || tempList.isEmpty()) {
	    return null;
	}
	List<Istanze> resultList = new ArrayList<Istanze>();
	for (Object[] temp : tempList) {
	    Istanze istanza = new Istanze();
	    Software software = new Software((String) temp[0]);
	    Comuni comune = new Comuni((String) temp[1]);
	    istanza.setSoftware(software);
	    istanza.setComune(comune);
	    resultList.add(istanza);
	}
	return resultList;
    }

    @Override
    public String getGroupTypeForIstanze(int idtestata) {

	SQLQuery query = getSession().createSQLQuery(QueriesConstants.SELECTGRUPPOTYPEISTANZE);
	query.addScalar("gruppotype", Hibernate.STRING);
	query.setString(0, ORMHelper.getIdcomune());
	query.setInteger(1, idtestata);
	return (String) query.uniqueResult();
    }

    @SuppressWarnings("unchecked")
    @Override
    public Istanze getIstanzaFromDettaglio(int idDettaglioComunicazione) {

	SQLQuery query = getSession().createSQLQuery(QueriesConstants.SELECTISTANZABYIDDETTAGLIO);
	query.addEntity(Istanze.class);
	query.setString(0, ORMHelper.getIdcomune());
	query.setInteger(1, idDettaglioComunicazione);
	List<Istanze> resultTemp = query.list();
	if (resultTemp != null && !resultTemp.isEmpty()) {
	    return resultTemp.get(0);
	}
	return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Integer> getAutorizzazioniFromDettaglio(int idDettaglioComunicazione) {

	SQLQuery query = getSession().createSQLQuery(QueriesConstants.SELECTAUTORIZZAZIONIFROMMERCATID);
	query.addScalar("id", Hibernate.INTEGER);
	query.setString(0, ORMHelper.getIdcomune());
	query.setInteger(1, idDettaglioComunicazione);
	return query.list();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Istanze> getIstanzeListFromDettaglio(int idDettaglioComunicazione) {

	SQLQuery query = getSession().createSQLQuery(QueriesConstants.SELECTISTANZABYIDDETTAGLIO);
	query.addEntity(Istanze.class);
	query.setString(0, ORMHelper.getIdcomune());
	query.setInteger(1, idDettaglioComunicazione);
	List<Istanze> resultTemp = query.list();
	if (resultTemp != null && !resultTemp.isEmpty()) {
	    return resultTemp;
	}
	return null;
    }
}
