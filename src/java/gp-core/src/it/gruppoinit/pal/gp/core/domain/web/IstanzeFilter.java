package it.gruppoinit.pal.gp.core.domain.web;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.domain.Alberoproc;
import it.gruppoinit.pal.gp.core.domain.Amministrazioni;
import it.gruppoinit.pal.gp.core.domain.Anagrafe;
import it.gruppoinit.pal.gp.core.domain.Autorizzazioni;
import it.gruppoinit.pal.gp.core.domain.Bandi;
import it.gruppoinit.pal.gp.core.domain.Comuni;
import it.gruppoinit.pal.gp.core.domain.Graduatoriet;
import it.gruppoinit.pal.gp.core.domain.Inventarioprocedimenti;
import it.gruppoinit.pal.gp.core.domain.Istanzearee;
import it.gruppoinit.pal.gp.core.domain.Istanzeattivita;
import it.gruppoinit.pal.gp.core.domain.Istanzemappali;
import it.gruppoinit.pal.gp.core.domain.Istanzestradario;
import it.gruppoinit.pal.gp.core.domain.Responsabili;
import it.gruppoinit.pal.gp.core.domain.Software;
import it.gruppoinit.pal.gp.core.domain.Statiistanza;
import it.gruppoinit.pal.gp.core.domain.Tipiarchivioistanze;
import it.gruppoinit.pal.gp.core.domain.Tipiendo;
import it.gruppoinit.pal.gp.core.domain.Tipifamiglieendo;
import it.gruppoinit.pal.gp.core.domain.Tipimovimento;
import it.gruppoinit.pal.gp.core.domain.Tipiprocedure;
import it.gruppoinit.pal.gp.core.domain.Tipologiaistanza;
import it.gruppoinit.pal.gp.core.filters.OrderTypeEnum;

/**
 * @author riccardob
 *
 */
public class IstanzeFilter implements Serializable {

    private static final long serialVersionUID = 8941527760958730695L;
    private Integer codiceIstanza;
    private Alberoproc alberoproc;
    private Statiistanza chiusura;
    private Comuni comune;
    private Date dallaData;
    private Date allaData;
    private Date dallaDataValidita;
    private Date allaDataValidita;
    private Date dallaDataProtocollo;
    private Date allaDataProtocollo;
    private Date dallaDataSorteggio;
    private Date allaDataSorteggio;
    private Tipimovimento tipoMovimento;
    private Date dallaDataTipoMov;
    private Date allaDataTipoMov;
    private DAOEnum defaultWhereCondition;
    private String codicedomandapeople;
    private String codicedomandastc;
    private String idNodoStc;
    private boolean cercasolodomandepeople;
    private boolean cercasolodomandestc;
    private boolean cercaprotocolloinmovimenti;
    private Autorizzazioni datiAutorizzazione;
    private Istanzearee istanzearee;
    private Istanzestradario istanzestradario;
    private Istanzemappali istanzemappali;
    private String stradarioCodViario;
    private String stradarioDescrizione;
    private Inventarioprocedimenti inventarioprocedimenti;
    private Istanzeattivita istanzeattivita;
    private String lavori;
    private String lavoriestesa;
    private boolean cercasolodomandeareariservata;
    private BigDecimal daMq;
    private BigDecimal aMq;
    private String orderBy;
    private OrderTypeEnum orderAscDesc = OrderTypeEnum.ASC;
    private boolean cercalocalizzazioneinaltri;
    private Map<String, Object> proprietaModicate;
    private String numeroistanza;
    private String numeroprotocollo;
    private Date dataprotocollo;
    private String posizionearchivio;
    private Tipiprocedure procedura;
    private Anagrafe professionista;
    private Anagrafe richiedente;
    private Responsabili tuttiResponsabili;
    private Responsabili responsabile;
    private Responsabili responsabileProcedimento;
    private Responsabili responsabileIstruttoria;
    private Responsabili utenteLoggato;
    private Tipiarchivioistanze tipiarchivioistanza;
    private Tipologiaistanza tipologiaistanza;
    // Contiene il codice dell'oggetto software
    private Software modulo;
    private String codicepraticatel;
    private String nomeattivita;
    private String soggettiistanza;
    private String soggettiistanzaPivaCF;
    private boolean ricercaVeloce;
    private boolean cercaInAnagrafestorico;
    private Tipimovimento tipoMovimentoFattoPerInserimentoMassivo;
    private String domicilioElettronico;
    private Boolean chkexportanagrafetrib;
    private Bandi bandi;
    private Graduatoriet graduatoriet;
    private String identemitt;
    private String idsportellomitt;
    private String civicoDa;
    private String civicoA;
    private Boolean isPraticheDaAssegnareAdIstruttore;
    private Boolean isPraticheDaAccettareComeIstruttore;
    private Amministrazioni amministrazioni;
    // Il campo permette di aggiungere una particolare condizione AND 
    // per cf. Permettere di mettere in OR la ricerca codice fiscale 
    //per i vari soggetti dell'istanza
    // (condizionzi AND (richiedente.cf =cf OR titolarelegale.cf=cf ...))
    private SoggettiIstanzaFilterCF soggettiIstanzaFilterCF;
    private Integer staticomportamento;
    private String statomovimentoAnomaliaStc;
    private SchedaDinamicaFilter schedaDinamicaFilter;
    private Tipifamiglieendo tipifamiglieendo;
    private Tipiendo tipiendo;
    private Boolean cercarangecivici;
    private Boolean istanzeInWarning;
    private Boolean flagEscludiRisultatiDaRicercaPubblica;
    private boolean ricercaNumPraticaConLike = true;
    private List<String> listaIdcomuneFiltro = new ArrayList<String>();

    public IstanzeFilter() {

	setDefaultWhereCondition(DAOEnum.FIND_BY_IDCOMUNE_AND_SOFTWARE);
	this.alberoproc = new Alberoproc();
	this.chiusura = new Statiistanza();
	this.comune = new Comuni();
	this.datiAutorizzazione = new Autorizzazioni();
	this.inventarioprocedimenti = new Inventarioprocedimenti();
	this.istanzearee = new Istanzearee();
	this.istanzeattivita = new Istanzeattivita();
	this.istanzemappali = new Istanzemappali();
	this.istanzestradario = new Istanzestradario();
	this.procedura = new Tipiprocedure();
	this.professionista = new Anagrafe();
	this.proprietaModicate = new LinkedHashMap<String, Object>();
	this.richiedente = new Anagrafe();
	this.tuttiResponsabili = new Responsabili();
	this.responsabile = new Responsabili();
	this.responsabileIstruttoria = new Responsabili();
	this.responsabileProcedimento = new Responsabili();
	this.tipiarchivioistanza = new Tipiarchivioistanze();
	this.tipologiaistanza = new Tipologiaistanza();
	this.tipoMovimento = new Tipimovimento();
	this.tipoMovimentoFattoPerInserimentoMassivo = new Tipimovimento();
	this.modulo = new Software();
	this.bandi = new Bandi();
	this.graduatoriet = new Graduatoriet();
	this.isPraticheDaAssegnareAdIstruttore = Boolean.valueOf(false);
	this.isPraticheDaAccettareComeIstruttore = Boolean.valueOf(false);
	this.soggettiIstanzaFilterCF = new SoggettiIstanzaFilterCF();
	this.schedaDinamicaFilter = new SchedaDinamicaFilter();
	this.cercarangecivici = Boolean.FALSE;
	this.istanzeInWarning = Boolean.FALSE;
	this.amministrazioni = new Amministrazioni();
    }

    public void setDefaultWhereCondition(DAOEnum defaultWhereCondition) {

	this.defaultWhereCondition = defaultWhereCondition;
    }

    public DAOEnum getDefaultWhereCondition() {

	return defaultWhereCondition;
    }

    public Date getDallaData() {

	return dallaData;
    }

    public void setDallaData(Date dallaData) {

	if (null != dallaData) {
	    SimpleDateFormat sdf = new SimpleDateFormat(WebConstants.DATE_FORMAT_PATTERN);
	    this.firePropertyChange("label.dalla_data", this.dallaData, sdf.format(dallaData));
	}
	this.dallaData = dallaData;
    }

    public Date getAllaData() {

	return allaData;
    }

    public void setAllaData(Date allaData) {

	if (allaData != null) {
	    this.firePropertyChange("label.alla_data", this.allaData, allaData);
	}
	this.allaData = allaData;
    }

    public Date getDallaDataValidita() {

	return dallaDataValidita;
    }

    public void setDallaDataValidita(Date dallaDataValidita) {

	if (dallaDataValidita != null) {
	    this.firePropertyChange("label.alla_data", this.dallaDataValidita, dallaDataValidita);
	}
	this.dallaDataValidita = dallaDataValidita;
    }

    public Date getAllaDataValidita() {

	return allaDataValidita;
    }

    public void setAllaDataValidita(Date allaDataValidita) {

	if (allaDataValidita != null) {
	    this.firePropertyChange("label.alla_data", this.allaDataValidita, allaDataValidita);
	}
	this.allaDataValidita = allaDataValidita;
    }

    public Date getDallaDataProtocollo() {

	return dallaDataProtocollo;
    }

    public void setDallaDataProtocollo(Date dallaDataProtocollo) {

	this.dallaDataProtocollo = dallaDataProtocollo;
    }

    public Date getAllaDataProtocollo() {

	return allaDataProtocollo;
    }

    public void setAllaDataProtocollo(Date allaDataProtocollo) {

	this.allaDataProtocollo = allaDataProtocollo;
    }

    public Date getDallaDataSorteggio() {

	return dallaDataSorteggio;
    }

    public void setDallaDataSorteggio(Date dallaDataSorteggio) {

	this.dallaDataSorteggio = dallaDataSorteggio;
    }

    public Date getAllaDataSorteggio() {

	return allaDataSorteggio;
    }

    public void setAllaDataSorteggio(Date allaDataSorteggio) {

	this.allaDataSorteggio = allaDataSorteggio;
    }

    public Tipimovimento getTipoMovimento() {

	return tipoMovimento;
    }

    public void setTipoMovimento(Tipimovimento tipoMovimento) {

	if (StringUtils.isNotBlank(tipoMovimento.getMovimento())) {
	    this.firePropertyChange("label.tipomovimento", this.tipoMovimento.getMovimento(), tipoMovimento.getMovimento());
	}
	this.tipoMovimento = tipoMovimento;
    }

    public Date getDallaDataTipoMov() {

	return dallaDataTipoMov;
    }

    public void setDallaDataTipoMov(Date dallaDataTipoMov) {

	if (dallaDataTipoMov != null) {
	    this.firePropertyChange("label.alla_data", this.dallaDataTipoMov, dallaDataTipoMov);
	}
	this.dallaDataTipoMov = dallaDataTipoMov;
    }

    public Date getAllaDataTipoMov() {

	return allaDataTipoMov;
    }

    public void setAllaDataTipoMov(Date allaDataTipoMov) {

	if (allaDataTipoMov != null) {
	    this.firePropertyChange("label.alla_data", this.allaDataTipoMov, allaDataTipoMov);
	}
	this.allaDataTipoMov = allaDataTipoMov;
    }

    public String getCodicedomandapeople() {

	return codicedomandapeople;
    }

    public void setCodicedomandapeople(String codicedomandapeople) {

	if (StringUtils.isNotBlank(codicedomandapeople)) {
	    this.firePropertyChange("label.codice_domanda_people", this.codicedomandapeople, codicedomandapeople);
	}
	this.codicedomandapeople = codicedomandapeople;
    }

    public String getCodicedomandastc() {

	return codicedomandastc;
    }

    public void setCodicedomandastc(String codicedomandastc) {

	this.codicedomandastc = codicedomandastc;
    }

    public boolean isCercasolodomandestc() {

	return cercasolodomandestc;
    }

    public void setCercasolodomandestc(boolean cercasolodomandestc) {

	this.cercasolodomandestc = cercasolodomandestc;
    }

    public boolean isCercasolodomandepeople() {

	return cercasolodomandepeople;
    }

    public void setCercasolodomandepeople(boolean cercasolodomandepeople) {

	if (cercasolodomandepeople) {
	    this.firePropertyChange("label.checkbox_mostra_solo_domande_people", this.cercasolodomandepeople, cercasolodomandepeople);
	}
	this.cercasolodomandepeople = cercasolodomandepeople;
    }

    public boolean isCercaprotocolloinmovimenti() {

	return cercaprotocolloinmovimenti;
    }

    public void setCercaprotocolloinmovimenti(boolean cercaprotocolloinmovimenti) {

	if (cercaprotocolloinmovimenti) {
	    this.firePropertyChange("cercaprotocolloinmovimenti", this.cercaprotocolloinmovimenti, cercaprotocolloinmovimenti);
	}
	this.cercaprotocolloinmovimenti = cercaprotocolloinmovimenti;
    }

    public Autorizzazioni getDatiAutorizzazione() {

	return datiAutorizzazione;
    }

    public void setDatiAutorizzazione(Autorizzazioni datiAutorizzazione) {

	if (StringUtils.isNotBlank(datiAutorizzazione.getAutoriznumero())) {
	    this.firePropertyChange("label.numero", this.datiAutorizzazione, datiAutorizzazione.getAutoriznumero());
	}
	if (datiAutorizzazione.getAutorizdata() != null) {
	    SimpleDateFormat sdf = new SimpleDateFormat(WebConstants.DATE_FORMAT_PATTERN);
	    this.firePropertyChange("label.data", this.datiAutorizzazione, sdf.format(datiAutorizzazione.getAutorizdata()));
	}
	if (StringUtils.isNotBlank(datiAutorizzazione.getAutorizcomune().getComune())) {
	    this.firePropertyChange("label.comune", this.datiAutorizzazione, datiAutorizzazione.getAutorizcomune().getComune());
	}
	if (StringUtils.isNotBlank(datiAutorizzazione.getTipologiaregistro().getTrDescrizione())) {
	    this.firePropertyChange("label.registro", this.datiAutorizzazione, datiAutorizzazione.getTipologiaregistro().getTrDescrizione());
	}
	this.datiAutorizzazione = datiAutorizzazione;
    }

    public Istanzearee getIstanzearee() {

	return istanzearee;
    }

    public void setIstanzearee(Istanzearee istanzearee) {

	if (StringUtils.isNotBlank(istanzearee.getArea().getDenominazione())) {
	    this.firePropertyChange("label.area", this.istanzearee.getArea().getDenominazione(), istanzearee.getArea().getDenominazione());
	}
	this.istanzearee = istanzearee;
    }

    public Istanzestradario getIstanzestradario() {

	return istanzestradario;
    }

    public void setIstanzestradario(Istanzestradario istanzestradario) {

	if (StringUtils.isNotBlank(istanzestradario.getStradario().getDescrizione())) {
	    this.firePropertyChange("label.indirizzo", this.istanzestradario.getStradario().getDescrizione(),
		    istanzestradario.getStradario().getDescrizione());
	}
	if (StringUtils.isNotBlank(istanzestradario.getCivico())) {
	    this.firePropertyChange("label.civico", this.istanzestradario.getCivico(), istanzestradario.getCivico());
	}
	if (StringUtils.isNotBlank(istanzestradario.getNote())) {
	    this.firePropertyChange("label.note", this.istanzestradario.getNote(), istanzestradario.getNote());
	}
	if (StringUtils.isNotBlank(istanzestradario.getCircoscrizione())) {
	    this.firePropertyChange("label.circoscrizione", this.istanzestradario.getCircoscrizione(), istanzestradario.getCircoscrizione());
	}
	if (StringUtils.isNotBlank(istanzestradario.getStradariocolore().getColore())) {
	    this.firePropertyChange("label.colore", this.istanzestradario.getStradariocolore().getColore(),
		    istanzestradario.getStradariocolore().getColore());
	}
	this.istanzestradario = istanzestradario;
    }

    public Istanzemappali getIstanzemappali() {

	return istanzemappali;
    }

    public void setIstanzemappali(Istanzemappali istanzemappali) {

	if (StringUtils.isNotBlank(istanzemappali.getCatasto().getDescrizione())) {
	    this.firePropertyChange("label.catasto", this.istanzemappali.getCatasto().getDescrizione(), istanzemappali.getCatasto().getDescrizione());
	}
	if (StringUtils.isNotBlank(istanzemappali.getFoglio())) {
	    this.firePropertyChange("label.foglio", this.istanzemappali.getFoglio(), istanzemappali.getFoglio());
	}
	if (StringUtils.isNotBlank(istanzemappali.getParticella())) {
	    this.firePropertyChange("label.particella", this.istanzemappali.getParticella(), istanzemappali.getParticella());
	}
	if (StringUtils.isNotBlank(istanzemappali.getSub())) {
	    this.firePropertyChange("label.sub", this.istanzemappali.getSub(), istanzemappali.getSub());
	}
	this.istanzemappali = istanzemappali;
    }

    public String getStradarioCodViario() {

	return stradarioCodViario;
    }

    public void setStradarioCodViario(String stradarioCodViario) {

	this.stradarioCodViario = stradarioCodViario;
    }

    public String getStradarioDescrizione() {

	return stradarioDescrizione;
    }

    public void setStradarioDescrizione(String stradarioDescrizione) {

	this.stradarioDescrizione = stradarioDescrizione;
    }

    public Inventarioprocedimenti getInventarioprocedimenti() {

	return inventarioprocedimenti;
    }

    public void setInventarioprocedimenti(Inventarioprocedimenti inventarioprocedimenti) {

	if (inventarioprocedimenti != null) {
	    if (StringUtils.isNotBlank(inventarioprocedimenti.getTipoendo().getTipifamiglieendo().getTipo())) {
		this.firePropertyChange("alberoproc.label.alberoprocEndo_famigliendo",
			this.inventarioprocedimenti.getTipoendo().getTipifamiglieendo().getTipo(),
			inventarioprocedimenti.getTipoendo().getTipifamiglieendo().getTipo());
	    }
	    if (StringUtils.isNotBlank(inventarioprocedimenti.getTipoendo().getTipo())) {
		this.firePropertyChange("alberoproc.label.alberoprocEndo_tipiendo", this.inventarioprocedimenti.getTipoendo().getTipo(),
			inventarioprocedimenti.getTipoendo().getTipo());
	    }
	    if (StringUtils.isNotBlank(inventarioprocedimenti.getProcedimento())) {
		this.firePropertyChange("alberoproc.label.alberoprocEndo_inventarioprocedimento", this.inventarioprocedimenti.getProcedimento(),
			inventarioprocedimenti.getProcedimento());
	    }
	}
	this.inventarioprocedimenti = inventarioprocedimenti;
    }

    public Istanzeattivita getIstanzeattivita() {

	return istanzeattivita;
    }

    public void setIstanzeattivita(Istanzeattivita istanzeattivita) {

	if (StringUtils.isNotBlank(istanzeattivita.getAttivita().getSettori().getSettore())) {
	    this.firePropertyChange("label.tipo_informazione", this.istanzeattivita.getAttivita().getSettori().getSettore(),
		    istanzeattivita.getAttivita().getSettori().getSettore());
	}
	if (StringUtils.isNotBlank(istanzeattivita.getAttivita().getIstat())) {
	    this.firePropertyChange("label.dettaglio_informazione", this.istanzeattivita.getAttivita().getIstat(),
		    istanzeattivita.getAttivita().getIstat());
	}
	this.istanzeattivita = istanzeattivita;
    }

    public boolean isCercasolodomandeareariservata() {

	return cercasolodomandeareariservata;
    }

    public void setCercasolodomandeareariservata(boolean cercasolodomandeareariservata) {

	if (cercasolodomandeareariservata) {
	    this.firePropertyChange("label.solo_istanze_da_area_riservata", this.cercasolodomandeareariservata, cercasolodomandeareariservata);
	}
	this.cercasolodomandeareariservata = cercasolodomandeareariservata;
    }

    public BigDecimal getDaMq() {

	return daMq;
    }

    public void setDaMq(BigDecimal daMq) {

	if (daMq != null) {
	    this.firePropertyChange("label.da", this.daMq, daMq);
	}
	this.daMq = daMq;
    }

    public BigDecimal getaMq() {

	return aMq;
    }

    public void setaMq(BigDecimal aMq) {

	if (aMq != null) {
	    this.firePropertyChange("label.a", this.aMq, aMq);
	}
	this.aMq = aMq;
    }

    public boolean isCercalocalizzazioneinaltri() {

	return cercalocalizzazioneinaltri;
    }

    public void setCercalocalizzazioneinaltri(boolean cercalocalizzazioneinaltri) {

	if (cercalocalizzazioneinaltri) {
	    this.firePropertyChange("label.cerca_localizzazione_in_altri_indirizzi", this.cercalocalizzazioneinaltri, cercalocalizzazioneinaltri);
	}
	this.cercalocalizzazioneinaltri = cercalocalizzazioneinaltri;
    }

    public Alberoproc getAlberoproc() {

	return alberoproc;
    }

    public void setAlberoproc(Alberoproc alberoproc) {

	if (StringUtils.isNotBlank(alberoproc.getVwAlberoproc().getScDescrizione())) {
	    this.firePropertyChange("label.alberoproc", this.alberoproc, alberoproc.getVwAlberoproc().getScDescrizione());
	}
	this.alberoproc = alberoproc;
    }

    public Statiistanza getChiusura() {

	return chiusura;
    }

    public void setChiusura(Statiistanza chiusura) {

	if (StringUtils.isNotBlank(chiusura.getStato())) {
	    this.firePropertyChange("label.stato_istanza", this.chiusura.getStato(), chiusura.getStato());
	}
	this.chiusura = chiusura;
    }

    public Comuni getComune() {

	return comune;
    }

    public void setComune(Comuni comune) {

	if (StringUtils.isNotBlank(comune.getComune())) {
	    this.firePropertyChange("label.comune", this.comune, comune);
	}
	this.comune = comune;
    }

    public String getLavori() {

	return lavori;
    }

    public void setLavori(String lavori) {

	if (StringUtils.isNotBlank(lavori)) {
	    this.firePropertyChange("label.lavori", this.lavori, lavori);
	}
	this.lavori = lavori;
    }

    public String getDomicilioElettronico() {

	return domicilioElettronico;
    }

    public void setDomicilioElettronico(String domicilioElettronico) {

	if (StringUtils.isNotBlank(domicilioElettronico)) {
	    this.firePropertyChange("label.domicilio_elettronico", this.domicilioElettronico, domicilioElettronico);
	}
	this.domicilioElettronico = domicilioElettronico;
    }

    public String getLavoriestesa() {

	return lavoriestesa;
    }

    public void setLavoriestesa(String lavoriestesa) {

	if (StringUtils.isNotBlank(lavoriestesa)) {
	    this.firePropertyChange("label.lavoriestesa", this.lavoriestesa, lavoriestesa);
	}
	this.lavoriestesa = lavoriestesa;
    }

    public Map<String, Object> getProprietaModicate() {

	return proprietaModicate;
    }

    public String getNumeroistanza() {

	return numeroistanza;
    }

    public void setNumeroistanza(String numeroistanza) {

	if (StringUtils.isNotBlank(numeroistanza)) {
	    this.firePropertyChange("label.numeroistanza", this.numeroistanza, numeroistanza);
	}
	this.numeroistanza = numeroistanza;
    }

    public String getNumeroprotocollo() {

	return numeroprotocollo;
    }

    public void setNumeroprotocollo(String numeroprotocollo) {

	if (StringUtils.isNotBlank(numeroprotocollo)) {
	    this.firePropertyChange("label.numero_protocollo", this.numeroprotocollo, numeroprotocollo);
	}
	this.numeroprotocollo = numeroprotocollo;
    }

    public void setDataprotocollo(Date dataprotocollo) {

	if (StringUtils.isNotBlank(numeroprotocollo)) {
	    this.firePropertyChange("label.data_protocollo", this.dataprotocollo, dataprotocollo);
	}
	this.dataprotocollo = dataprotocollo;
    }

    public Date getDataprotocollo() {

	return dataprotocollo;
    }

    public String getPosizionearchivio() {

	return posizionearchivio;
    }

    public void setPosizionearchivio(String posizionearchivio) {

	if (StringUtils.isNotBlank(posizionearchivio)) {
	    this.firePropertyChange("label.posizione_in_archivio", this.posizionearchivio, posizionearchivio);
	}
	this.posizionearchivio = posizionearchivio;
    }

    public Tipiprocedure getProcedura() {

	return procedura;
    }

    public void setProcedura(Tipiprocedure procedura) {

	if (StringUtils.isNotBlank(procedura.getProcedura())) {
	    this.firePropertyChange("label.tipiprocedure.procedura", this.procedura.getProcedura(), procedura.getProcedura());
	}
	this.procedura = procedura;
    }

    public Anagrafe getProfessionista() {

	return professionista;
    }

    public void setProfessionista(Anagrafe professionista) {

	if (StringUtils.isNotBlank(professionista.getDescrizioneRichiedente())) {
	    this.firePropertyChange("label.tecnico", this.professionista.getDescrizioneRichiedente(), professionista.getDescrizioneRichiedente());
	}
	this.professionista = professionista;
    }

    public Anagrafe getRichiedente() {

	return richiedente;
    }

    public void setRichiedente(Anagrafe richiedente) {

	if (StringUtils.isNotBlank(richiedente.getDescrizioneRichiedente())) {
	    this.firePropertyChange("label.richiedente_soggetti_collegati", this.richiedente.getDescrizioneRichiedente(),
		    richiedente.getDescrizioneRichiedente());
	}
	this.richiedente = richiedente;
    }

    public Responsabili getResponsabile() {

	return responsabile;
    }

    public void setResponsabile(Responsabili responsabile) {

	if (StringUtils.isNotBlank(responsabile.getResponsabile())) {
	    this.firePropertyChange("label.operatore_responsabile_istruttore", this.responsabile.getResponsabile(), responsabile.getResponsabile());
	}
	this.responsabile = responsabile;
    }

    public Responsabili getTuttiResponsabili() {

	return tuttiResponsabili;
    }

    public void setTuttiResponsabili(Responsabili tuttiResponsabili) {

	this.tuttiResponsabili = tuttiResponsabili;
    }

    public Responsabili getResponsabileProcedimento() {

	return responsabileProcedimento;
    }

    public void setResponsabileProcedimento(Responsabili responsabileProcedimento) {

	this.responsabileProcedimento = responsabileProcedimento;
    }

    public Responsabili getResponsabileIstruttoria() {

	return responsabileIstruttoria;
    }

    public void setResponsabileIstruttoria(Responsabili responsabileIstruttoria) {

	this.responsabileIstruttoria = responsabileIstruttoria;
    }

    public Tipiarchivioistanze getTipiarchivioistanza() {

	return tipiarchivioistanza;
    }

    public void setTipiarchivioistanza(Tipiarchivioistanze tipiarchivioistanza) {

	if (StringUtils.isNotBlank(tipiarchivioistanza.getArchivio())) {
	    this.firePropertyChange("label.archivio_pratiche", this.tipiarchivioistanza.getArchivio(), tipiarchivioistanza.getArchivio());
	}
	this.tipiarchivioistanza = tipiarchivioistanza;
    }

    public Tipologiaistanza getTipologiaistanza() {

	return tipologiaistanza;
    }

    public void setTipologiaistanza(Tipologiaistanza tipologiaistanza) {

	if (StringUtils.isNotBlank(tipologiaistanza.getTiDescrizione())) {
	    this.firePropertyChange("label.tipologia_istanza", this.tipologiaistanza.getTiDescrizione(), tipologiaistanza.getTiDescrizione());
	}
	this.tipologiaistanza = tipologiaistanza;
    }

    public void firePropertyChange(String label, Object oldvalue, Object newValue) {

	if (proprietaModicate == null) {
	    proprietaModicate = new HashMap<String, Object>();
	}
	proprietaModicate.put(label, newValue);
    }

    public String getOrderBy() {

	return orderBy;
    }

    public void setOrderBy(String orderBy) {

	this.firePropertyChange("label.ordinare_la_lista_per", this.orderBy, orderBy);
	this.orderBy = orderBy;
    }

    public void setOrderAscDesc(OrderTypeEnum orderAscDesc) {

	this.firePropertyChange("label.ordinamento", this.orderAscDesc, orderAscDesc);
	this.orderAscDesc = orderAscDesc;
    }

    public OrderTypeEnum getOrderAscDesc() {

	return orderAscDesc;
    }

    public Software getModulo() {

	return modulo;
    }

    public void setModulo(Software modulo) {

	this.modulo = modulo;
    }

    public String getCodicepraticatel() {

	return codicepraticatel;
    }

    public void setCodicepraticatel(String codicepraticatel) {

	this.codicepraticatel = codicepraticatel;
    }

    public String getNomeattivita() {

	return nomeattivita;
    }

    public void setNomeattivita(String nomeattivita) {

	this.nomeattivita = nomeattivita;
    }

    public String getSoggettiistanza() {

	return soggettiistanza;
    }

    public void setSoggettiistanza(String soggettiistanza) {

	this.soggettiistanza = soggettiistanza;
    }

    public String getSoggettiistanzaPivaCF() {

	return soggettiistanzaPivaCF;
    }

    public void setSoggettiistanzaPivaCF(String soggettiistanzaPivaCF) {

	this.soggettiistanzaPivaCF = soggettiistanzaPivaCF;
    }

    public void setIdNodoStc(String idNodoStc) {

	this.idNodoStc = idNodoStc;
    }

    public String getIdNodoStc() {

	return idNodoStc;
    }

    public boolean isRicercaVeloce() {

	return ricercaVeloce;
    }

    public void setRicercaVeloce(boolean ricercaVeloce) {

	this.ricercaVeloce = ricercaVeloce;
    }

    public boolean isCercaInAnagrafestorico() {

	return cercaInAnagrafestorico;
    }

    public void setCercaInAnagrafestorico(boolean cercaInAnagrafestorico) {

	this.cercaInAnagrafestorico = cercaInAnagrafestorico;
    }

    public Responsabili getUtenteLoggato() {

	return utenteLoggato;
    }

    public void setUtenteLoggato(Responsabili utenteLoggato) {

	this.utenteLoggato = utenteLoggato;
    }

    public Tipimovimento getTipoMovimentoFattoPerInserimentoMassivo() {

	return tipoMovimentoFattoPerInserimentoMassivo;
    }

    public void setTipoMovimentoFattoPerInserimentoMassivo(Tipimovimento tipoMovimentoFattoPerInserimentoMassivo) {

	this.tipoMovimentoFattoPerInserimentoMassivo = tipoMovimentoFattoPerInserimentoMassivo;
    }

    public Integer getCodiceIstanza() {

	return codiceIstanza;
    }

    public void setCodiceIstanza(Integer codiceIstanza) {

	this.codiceIstanza = codiceIstanza;
    }

    public Boolean getChkexportanagrafetrib() {

	return chkexportanagrafetrib;
    }

    public void setChkexportanagrafetrib(Boolean chkexportanagrafetrib) {

	this.chkexportanagrafetrib = chkexportanagrafetrib;
    }

    public Bandi getBandi() {

	return bandi;
    }

    public void setBandi(Bandi bandi) {

	this.bandi = bandi;
    }

    public Graduatoriet getGraduatoriet() {

	return graduatoriet;
    }

    public void setGraduatoriet(Graduatoriet graduatoriet) {

	this.graduatoriet = graduatoriet;
    }

    public String getIdentemitt() {

	return identemitt;
    }

    public void setIdentemitt(String identemitt) {

	this.identemitt = identemitt;
    }

    public String getIdsportellomitt() {

	return idsportellomitt;
    }

    public void setIdsportellomitt(String idsportellomitt) {

	this.idsportellomitt = idsportellomitt;
    }

    public String getCivicoDa() {

	return civicoDa;
    }

    public void setCivicoDa(String civicoDa) {

	this.civicoDa = civicoDa;
    }

    public String getCivicoA() {

	return civicoA;
    }

    public void setCivicoA(String civicoA) {

	this.civicoA = civicoA;
    }

    public Boolean getIsPraticheDaAssegnareAdIstruttore() {

	return isPraticheDaAssegnareAdIstruttore;
    }

    public void setIsPraticheDaAssegnareAdIstruttore(Boolean isPraticheDaAssegnareAdIstruttore) {

	this.isPraticheDaAssegnareAdIstruttore = isPraticheDaAssegnareAdIstruttore;
    }

    public Boolean getIsPraticheDaAccettareComeIstruttore() {

	return isPraticheDaAccettareComeIstruttore;
    }

    public void setIsPraticheDaAccettareComeIstruttore(Boolean isPraticheDaAccettareComeIstruttore) {

	this.isPraticheDaAccettareComeIstruttore = isPraticheDaAccettareComeIstruttore;
    }

    public SoggettiIstanzaFilterCF getSoggettiIstanzaFilterCF() {

	return soggettiIstanzaFilterCF;
    }

    public void setSoggettiIstanzaFilterCF(SoggettiIstanzaFilterCF soggettiIstanzaFilterCF) {

	this.soggettiIstanzaFilterCF = soggettiIstanzaFilterCF;
    }

    public Integer getStaticomportamento() {

	return staticomportamento;
    }

    public void setStaticomportamento(Integer staticomportamento) {

	this.staticomportamento = staticomportamento;
    }

    public String getStatomovimentoAnomaliaStc() {

	return statomovimentoAnomaliaStc;
    }

    public void setStatomovimentoAnomaliaStc(String statomovimentoAnomaliaStc) {

	this.statomovimentoAnomaliaStc = statomovimentoAnomaliaStc;
    }

    public SchedaDinamicaFilter getSchedaDinamicaFilter() {

	return schedaDinamicaFilter;
    }

    public void setSchedaDinamicaFilter(SchedaDinamicaFilter schedaDinamicaFilter) {

	this.schedaDinamicaFilter = schedaDinamicaFilter;
    }

    public Tipifamiglieendo getTipifamiglieendo() {

	return tipifamiglieendo;
    }

    public void setTipifamiglieendo(Tipifamiglieendo tipifamiglieendo) {

	this.tipifamiglieendo = tipifamiglieendo;
    }

    public Tipiendo getTipiendo() {

	return tipiendo;
    }

    public void setTipiendo(Tipiendo tipiendo) {

	this.tipiendo = tipiendo;
    }

    public Boolean getCercarangecivici() {

	return cercarangecivici;
    }

    public void setCercarangecivici(Boolean cercarangecivici) {

	this.cercarangecivici = cercarangecivici;
    }

    public Boolean getIstanzeInWarning() {

	return istanzeInWarning;
    }

    public void setIstanzeInWarning(Boolean istanzeInWarning) {

	this.istanzeInWarning = istanzeInWarning;
    }

    public void setAmministrazioni(Amministrazioni amministrazioni) {

	this.amministrazioni = amministrazioni;
    }

    public Amministrazioni getAmministrazioni() {

	return amministrazioni;
    }

    public Boolean getFlagEscludiRisultatiDaRicercaPubblica() {

	return flagEscludiRisultatiDaRicercaPubblica;
    }

    public void setFlagEscludiRisultatiDaRicercaPubblica(Boolean flagEscludiRisultatiDaRicercaPubblica) {

	this.flagEscludiRisultatiDaRicercaPubblica = flagEscludiRisultatiDaRicercaPubblica;
    }

    public boolean isRicercaNumPraticaConLike() {

	return ricercaNumPraticaConLike;
    }

    public void setRicercaNumPraticaConLike(boolean ricercaNumPraticaConLike) {

	this.ricercaNumPraticaConLike = ricercaNumPraticaConLike;
    }

    public List<String> getListaIdcomuneFiltro() {

	if (this.listaIdcomuneFiltro == null) {
	    this.listaIdcomuneFiltro = new ArrayList<String>();
	}
	return listaIdcomuneFiltro;
    }

    public void setListaIdcomuneFiltro(List<String> listaIdcomuneFiltro) {

	this.listaIdcomuneFiltro = listaIdcomuneFiltro;
    }
}
