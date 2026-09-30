package it.gruppoinit.pal.gp.core.features.bollettazione.gestione;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang.StringUtils;

import it.gruppoinit.pal.gp.core.dao.helper.ImplementazioniEnum;
import it.gruppoinit.pal.gp.core.domain.BollGestTestata;
import it.gruppoinit.pal.gp.core.features.bollettazione.gestione.model.BollGestDettaglioDTO;
import it.gruppoinit.pal.gp.core.features.bollettazione.rateizzazioni.BollettazioneRataBean;

@XmlRootElement(name = "dettaglioBollettazione")
public class DettaglioBollettazione {

    private Integer id;
    private String descrizione;
    private Date dallaData;
    private Date allaData;
    private StatoBollettazioneEnum statoBollettazione;
    private List<AnagraficaBollettazione> anagraficaBollettazioneList = new ArrayList<AnagraficaBollettazione>();
    private Boolean raggruppaPerUtenza;
    private Boolean bollettazioneChiusa;
    private boolean flagRichiestaFattura;
    private boolean presenteLetteraAvvisatura;
    private boolean flagCaricamentoMassivo;
    private Date dataScadenza;
    private ImplementazioniEnum implementazione;
    private Set<BollettazioneRataBean> rateizzazione = new TreeSet<BollettazioneRataBean>();

    public DettaglioBollettazione(Set<BollettazioneRataBean> rateizzazione, BollGestTestata bollGestTestata,
	    List<BollGestDettaglioDTO> bollGestDettaglios) {

	this.id = bollGestTestata.getId().getCodice();
	this.descrizione = bollGestTestata.getDescrizione();
	this.allaData = bollGestTestata.getAllaData();
	this.dallaData = bollGestTestata.getDallaData();
	this.statoBollettazione = StatoBollettazioneEnum.fromValue(bollGestTestata.getStato());
	this.raggruppaPerUtenza = bollGestTestata.getFlagRaggruppaUtenza();
	this.rateizzazione = rateizzazione;
	this.adattaRigheAnagrafiche(this.rateizzazione, bollGestDettaglios);
	this.bollettazioneChiusa = (bollGestTestata.getStato().equals(StatoBollettazioneEnum.CHIUSA.getValore()));
	this.flagRichiestaFattura = bollGestTestata.getBollCfgTipo().getFlagRichiestaFattura() == null ? false
		: bollGestTestata.getBollCfgTipo().getFlagRichiestaFattura().booleanValue();
	this.flagCaricamentoMassivo = bollGestTestata.getBollCfgTipo().getFlagCaricamentoMassivo() == null ? false
		: bollGestTestata.getBollCfgTipo().getFlagCaricamentoMassivo().booleanValue();
	this.dataScadenza = bollGestTestata.getDataScadenza();
	this.implementazione = ImplementazioniEnum.fromValore(bollGestTestata.getImplementazione());
	this.presenteLetteraAvvisatura = bollGestTestata.getBollCfgTipo().getLetteraAccompagnamento() != null;
    }

    public DettaglioBollettazione() {

    }

    public void setId(Integer id) {

	this.id = id;
    }

    public Integer getId() {

	return id;
    }

    public String getDescrizione() {

	return descrizione;
    }

    public Date getDallaData() {

	return dallaData;
    }

    public Date getAllaData() {

	return allaData;
    }

    public StatoBollettazioneEnum getStatoBollettazione() {

	return statoBollettazione;
    }

    public Boolean getRaggruppaPerUtenza() {

	return raggruppaPerUtenza;
    }

    public List<AnagraficaBollettazione> getAnagraficaBollettazioneList() {

	return anagraficaBollettazioneList;
    }

    private void adattaRigheAnagrafiche(Set<BollettazioneRataBean> rateizzazione, List<BollGestDettaglioDTO> gestDettaglio) {

	Map<Integer, AnagraficaBollettazione> mappaAnagrafiche = new HashMap<Integer, AnagraficaBollettazione>();
	Map<Integer, RigaBollettazione> rigaDettaglioB = new HashMap<Integer, RigaBollettazione>();
	for (BollGestDettaglioDTO bollGestDettaglio : gestDettaglio) {
	    Integer codiceAnagrafe = bollGestDettaglio.getCodiceanagrafe();
	    AnagraficaBollettazione anagraficaBollettazione = mappaAnagrafiche.get(codiceAnagrafe);
	    if (anagraficaBollettazione == null) {
		String cfPiva = bollGestDettaglio.getCodicefiscale();
		if (StringUtils.isEmpty(cfPiva)) {
		    cfPiva = bollGestDettaglio.getPartitaiva();
		}
		anagraficaBollettazione = new AnagraficaBollettazione(codiceAnagrafe, bollGestDettaglio.getDescrizioneRichiedente(),
			StringUtils.isNotEmpty(cfPiva), this.raggruppaPerUtenza, rateizzazione);
		anagraficaBollettazioneList.add(anagraficaBollettazione);
		mappaAnagrafiche.put(codiceAnagrafe, anagraficaBollettazione);
	    }
	    RigaBollettazione rigaBollettazione = rigaDettaglioB.get(bollGestDettaglio.getId());
	    if (rigaBollettazione == null) {
		rigaBollettazione = new RigaBollettazione(bollGestDettaglio, rateizzazione, this.raggruppaPerUtenza);
		anagraficaBollettazione.aggiungiRiga(rigaBollettazione);
		rigaDettaglioB.put(bollGestDettaglio.getId(), rigaBollettazione);
	    } else {
		rigaBollettazione.aggiungiPosizioneDebitoria(bollGestDettaglio);
	    }
	}
	if (anagraficaBollettazioneList != null) {
	    Collections.sort(anagraficaBollettazioneList);
	}
    }

    public Boolean getBollettazioneChiusa() {

	return bollettazioneChiusa;
    }

    public boolean isFlagRichiestaFattura() {

	return flagRichiestaFattura;
    }

    public Date getDataScadenza() {

	return dataScadenza;
    }

    public ImplementazioniEnum getImplementazione() {

	return implementazione;
    }

    public Set<BollettazioneRataBean> getRateizzazione() {

	return rateizzazione;
    }

    public boolean getPresenteLetteraAvvisatura() {

	return presenteLetteraAvvisatura;
    }

    public void setPresenteLetteraAvvisatura(boolean presenteLetteraAvvisatura) {

	this.presenteLetteraAvvisatura = presenteLetteraAvvisatura;
    }

    public void setDescrizione(String descrizione) {

	this.descrizione = descrizione;
    }

    public void setDallaData(Date dallaData) {

	this.dallaData = dallaData;
    }

    public void setAllaData(Date allaData) {

	this.allaData = allaData;
    }

    public void setStatoBollettazione(StatoBollettazioneEnum statoBollettazione) {

	this.statoBollettazione = statoBollettazione;
    }

    public void setAnagraficaBollettazioneList(List<AnagraficaBollettazione> anagraficaBollettazioneList) {

	this.anagraficaBollettazioneList = anagraficaBollettazioneList;
    }

    public void setRaggruppaPerUtenza(Boolean raggruppaPerUtenza) {

	this.raggruppaPerUtenza = raggruppaPerUtenza;
    }

    public void setBollettazioneChiusa(Boolean bollettazioneChiusa) {

	this.bollettazioneChiusa = bollettazioneChiusa;
    }

    public void setFlagRichiestaFattura(boolean flagRichiestaFattura) {

	this.flagRichiestaFattura = flagRichiestaFattura;
    }

    public void setDataScadenza(Date dataScadenza) {

	this.dataScadenza = dataScadenza;
    }

    public void setImplementazione(ImplementazioniEnum implementazione) {

	this.implementazione = implementazione;
    }

    public void setRateizzazione(Set<BollettazioneRataBean> rateizzazione) {

	this.rateizzazione = rateizzazione;
    }

    public boolean isFlagCaricamentoMassivo() {

	return flagCaricamentoMassivo;
    }

    public void setFlagCaricamentoMassivo(boolean flagCaricamentoMassivo) {

	this.flagCaricamentoMassivo = flagCaricamentoMassivo;
    }
    
    public boolean isVisualizzaDettaglioBollettazione(){
	return this.implementazione.equals(ImplementazioniEnum.MERCATI);
    }
}
