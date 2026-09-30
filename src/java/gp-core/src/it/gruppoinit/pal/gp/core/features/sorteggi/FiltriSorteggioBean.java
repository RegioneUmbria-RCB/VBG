package it.gruppoinit.pal.gp.core.features.sorteggi;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import it.gruppoinit.pal.gp.core.dao.helper.AlberoprocSorteggiHelper;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Alberoproc;
import it.gruppoinit.pal.gp.core.domain.Amministrazioni;
import it.gruppoinit.pal.gp.core.domain.Mailtipo;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Responsabili;
import it.gruppoinit.pal.gp.core.domain.Tipimovimento;
import it.gruppoinit.pal.gp.core.domain.util.EntityUtils;
import it.gruppoinit.pal.gp.core.domain.web.SorteggitestataFilter;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.IdentificativoDescrizioneBean;
import it.gruppoinit.pal.gp.core.service.AlberoprocService;

public class FiltriSorteggioBean {

    private Date dataSorteggio;
    private String codiceComune;
    private String software;
    private Integer codiceAlgoritmo;
    private Integer idTipiArchivioIstanza;
    private List<String> scCodiciInterventoProc;
    private List<Integer> idProcedura;
    private String modalitaSelezioneEndo;
    private List<Integer> idEndoprocedimenti;
    private List<String> codiciStatoIstanza;
    private List<Integer> idNatureEndo;
    private String tipoMovimento;
    private Integer tipoRicercaMovimento;
    private Date dataDal;
    private Date dataAl;
    private Boolean esito;
    private Integer percentuale;
    private Boolean salvaSorteggio;
    private String descrizioneSorteggio;
    private Date intervalloCampionamentoDal;
    private Date intervalloCampionamentoAl;
    private Boolean escludiIstanzeSorteggiate;
    private List<Integer> idSorteggiDaEscludere;
    private List<Integer> idCategorieDaEscludere;
    private Integer gruppiDiIstanze;
    private Integer arrotondamento;
    private Integer idCategoriaSorteggio;
    private Amministrazioni amministrazione;
    private Integer idDocumentoTipo;
    private String mailDestinatario;
    private Mailtipo mailTipo;

    public Amministrazioni getAmministrazione() {

	return amministrazione;
    }

    public void setAmministrazione(Amministrazioni amministrazione) {

	this.amministrazione = amministrazione;
    }

    private Responsabili responsabile;
    private Tipimovimento tipoMovimentoDaCreare;

    public Tipimovimento getTipoMovimentoDaCreare() {

	return tipoMovimentoDaCreare;
    }

    public void setTipoMovimentoDaCreare(Tipimovimento tipoMovimentoDaCreare) {

	this.tipoMovimentoDaCreare = tipoMovimentoDaCreare;
    }

    public Integer getIdDocumentoTipo() {

	return idDocumentoTipo;
    }

    public void setIdDocumentoTipo(Integer idDocumentoTipo) {

	this.idDocumentoTipo = idDocumentoTipo;
    }

    public String getMailDestinatario() {

	return mailDestinatario;
    }

    public void setMailDestinatario(String mailDestinatario) {

	this.mailDestinatario = mailDestinatario;
    }

    public Mailtipo getMailTipo() {

	return mailTipo;
    }

    public void setMailTipo(Mailtipo mailTipo) {

	this.mailTipo = mailTipo;
    }

    public Responsabili getResponsabile() {

	return responsabile;
    }

    public void setResponsabile(Responsabili responsabile) {

	this.responsabile = responsabile;
    }

    public void setDataSorteggio(Date dataSorteggio) {

	this.dataSorteggio = dataSorteggio;
    }

    public void setCodiceComune(String codiceComune) {

	this.codiceComune = codiceComune;
    }

    public void setSoftware(String software) {

	this.software = software;
    }

    public void setCodiceAlgoritmo(Integer codiceAlgoritmo) {

	this.codiceAlgoritmo = codiceAlgoritmo;
    }

    public void setIdTipiArchivioIstanza(Integer idTipiArchivioIstanza) {

	this.idTipiArchivioIstanza = idTipiArchivioIstanza;
    }

    public void setScCodiciInterventoProc(List<String> scCodiciInterventoProc) {

	this.scCodiciInterventoProc = scCodiciInterventoProc;
    }

    public void setIdProcedura(List<Integer> idProcedura) {

	this.idProcedura = idProcedura;
    }

    public void setModalitaSelezioneEndo(String modalitaSelezioneEndo) {

	this.modalitaSelezioneEndo = modalitaSelezioneEndo;
    }

    public void setIdEndoprocedimenti(List<Integer> idEndoprocedimenti) {

	this.idEndoprocedimenti = idEndoprocedimenti;
    }

    public void setCodiciStatoIstanza(List<String> codiciStatoIstanza) {

	this.codiciStatoIstanza = codiciStatoIstanza;
    }

    public void setIdNatureEndo(List<Integer> idNatureEndo) {

	this.idNatureEndo = idNatureEndo;
    }

    public void setTipoMovimento(String tipoMovimento) {

	this.tipoMovimento = tipoMovimento;
    }

    public void setTipoRicercaMovimento(Integer tipoRicercaMovimento) {

	this.tipoRicercaMovimento = tipoRicercaMovimento;
    }

    public void setDataDal(Date dataDal) {

	this.dataDal = dataDal;
    }

    public void setDataAl(Date dataAl) {

	this.dataAl = dataAl;
    }

    public void setEsito(Boolean esito) {

	this.esito = esito;
    }

    public void setPercentuale(Integer percentuale) {

	this.percentuale = percentuale;
    }

    public void setSalvaSorteggio(Boolean salvaSorteggio) {

	this.salvaSorteggio = salvaSorteggio;
    }

    public void setDescrizioneSorteggio(String descrizioneSorteggio) {

	this.descrizioneSorteggio = descrizioneSorteggio;
    }

    public void setIntervalloCampionamentoDal(Date intervalloCampionamentoDal) {

	this.intervalloCampionamentoDal = intervalloCampionamentoDal;
    }

    public void setIntervalloCampionamentoAl(Date intervalloCampionamentoAl) {

	this.intervalloCampionamentoAl = intervalloCampionamentoAl;
    }

    public void setEscludiIstanzeSorteggiate(Boolean escludiIstanzeSorteggiate) {

	this.escludiIstanzeSorteggiate = escludiIstanzeSorteggiate;
    }

    public void setIdSorteggiDaEscludere(List<Integer> idSorteggiDaEscludere) {

	this.idSorteggiDaEscludere = idSorteggiDaEscludere;
    }

    public void setIdCategorieDaEscludere(List<Integer> idCategorieDaEscludere) {

	this.idCategorieDaEscludere = idCategorieDaEscludere;
    }

    public void setGruppiDiIstanze(Integer gruppiDiIstanze) {

	this.gruppiDiIstanze = gruppiDiIstanze;
    }

    public void setArrotondamento(Integer arrotondamento) {

	this.arrotondamento = arrotondamento;
    }

    public void setIdCategoriaSorteggio(Integer idCategoriaSorteggio) {

	this.idCategoriaSorteggio = idCategoriaSorteggio;
    }

    public Date getDataSorteggio() {

	return dataSorteggio;
    }

    public String getCodiceComune() {

	return codiceComune;
    }

    public String getSoftware() {

	return software;
    }

    public Integer getCodiceAlgoritmo() {

	return codiceAlgoritmo;
    }

    public Integer getIdTipiArchivioIstanza() {

	return idTipiArchivioIstanza;
    }

    public List<String> getScCodiciInterventoProc() {

	return scCodiciInterventoProc;
    }

    public List<Integer> getIdProcedura() {

	return idProcedura;
    }

    public List<Integer> getIdEndoprocedimenti() {

	return idEndoprocedimenti;
    }

    public String getModalitaSelezioneEndo() {

	return modalitaSelezioneEndo;
    }

    public List<String> getCodiciStatoIstanza() {

	return codiciStatoIstanza;
    }

    public List<Integer> getIdNatureEndo() {

	return idNatureEndo;
    }

    public String getTipoMovimento() {

	return tipoMovimento;
    }

    public Integer getTipoRicercaMovimento() {

	return tipoRicercaMovimento;
    }

    public Date getDataDal() {

	return dataDal;
    }

    public Date getDataAl() {

	return dataAl;
    }

    public Boolean getEsito() {

	return esito;
    }

    public Integer getPercentuale() {

	return percentuale;
    }

    public Boolean getSalvaSorteggio() {

	return salvaSorteggio;
    }

    public String getDescrizioneSorteggio() {

	return descrizioneSorteggio;
    }

    public Date getIntervalloCampionamentoDal() {

	return intervalloCampionamentoDal;
    }

    public Date getIntervalloCampionamentoAl() {

	return intervalloCampionamentoAl;
    }

    public Boolean getEscludiIstanzeSorteggiate() {

	return escludiIstanzeSorteggiate;
    }

    public List<Integer> getIdSorteggiDaEscludere() {

	return idSorteggiDaEscludere;
    }

    public List<Integer> getIdCategorieDaEscludere() {

	return idCategorieDaEscludere;
    }

    public Integer getGruppiDiIstanze() {

	return gruppiDiIstanze;
    }

    public Integer getArrotondamento() {

	return arrotondamento;
    }

    public Integer getIdCategoriaSorteggio() {

	return idCategoriaSorteggio;
    }

    public static FiltriSorteggioBean fromSorteggitestataFilter(AlberoprocService alberoprocService, SorteggitestataFilter filter) {

	if (filter == null) {
	    return null;
	}
	FiltriSorteggioBean filtro = new FiltriSorteggioBean();
	filtro.dataSorteggio = filter.getDataSorteggio();
	if (EntityUtils.getNestedProperty(filter, "comune.codicecomune") != null) {
	    filtro.codiceComune = filter.getComune().getCodicecomune();
	}
	filtro.software = ORMHelper.getSoftware();
	if (EntityUtils.getNestedProperty(filter, "tipiarchivioistanza.id.codice") != null) {
	    filtro.idTipiArchivioIstanza = filter.getTipiarchivioistanza().getId().getCodice();
	}
	filtro.scCodiciInterventoProc = new ArrayList<String>();
	if (EntityUtils.getNestedProperty(filter, "alberoproc.id.codice") != null) {
	    Alberoproc alberoproc = alberoprocService.bindDomainObject(filter.getAlberoproc(), PkId.class, "id.codice");
	    filtro.scCodiciInterventoProc.add(alberoproc.getScCodice());
	}
	if (filter.getAlberoprocSorteggiHelpers() != null && !filter.getAlberoprocSorteggiHelpers().isEmpty()) {
	    for (AlberoprocSorteggiHelper alberoprocSorteggiHelper : filter.getAlberoprocSorteggiHelpers()) {
		Alberoproc alberoproc = alberoprocService.findById(new PkId(alberoprocSorteggiHelper.getCodiceAlberoproc()));
		filtro.scCodiciInterventoProc.add(alberoproc.getScCodice());
	    }
	}
	if (EntityUtils.getNestedProperty(filter, "procedura.id.codice") != null) {
	    filtro.idProcedura = new ArrayList<Integer>(filter.getProcedura().getId().getCodice());
	}
	if (!filter.getEndoSelezionati().isEmpty()) {
	    filtro.idEndoprocedimenti = new ArrayList<Integer>();
	    for (IdentificativoDescrizioneBean id : filter.getEndoSelezionati()) {
		filtro.idEndoprocedimenti.add(id.getId());
	    }
	}
	filtro.modalitaSelezioneEndo = filter.getAndOrSelezioneEndo();
	if (EntityUtils.getNestedProperty(filter, "chiusura.id.codicestato") != null) {
	    filtro.codiciStatoIstanza = new ArrayList<String>();
	    filtro.codiciStatoIstanza.add(filter.getChiusura().getId().getCodicestato());
	}
	filtro.idNatureEndo = filter.getNaturaendo();
	if (StringUtils.isNotBlank((String) EntityUtils.getNestedProperty(filter, "tipoMovimento.id.tipomovimento"))) {
	    filtro.tipoMovimento = filter.getTipoMovimento().getId().getTipomovimento();
	}
	filtro.tipoRicercaMovimento = filter.getTipoRicercaMovimento();
	if (EntityUtils.getNestedProperty(filter, "dataDal") != null) {
	    Calendar t = Calendar.getInstance();
	    t.setTime(filter.getDataDal());
	    t.set(Calendar.HOUR_OF_DAY, 0);
	    t.set(Calendar.MINUTE, 0);
	    t.set(Calendar.SECOND, 0);
	    filtro.dataDal = t.getTime();
	}
	if (EntityUtils.getNestedProperty(filter, "dataAl") != null) {
	    Calendar t = Calendar.getInstance();
	    t.setTime(filter.getDataAl());
	    t.set(Calendar.HOUR_OF_DAY, 23);
	    t.set(Calendar.MINUTE, 59);
	    t.set(Calendar.SECOND, 59);
	    filtro.dataAl = t.getTime();
	}
	if (filter.getEsito() == 1) {
	    filtro.esito = Boolean.FALSE;
	}
	// Esito Positivo 
	if (filter.getEsito() == 2) {
	    filtro.esito = Boolean.TRUE;
	}
	filtro.percentuale = filter.getPercentuale();
	filtro.salvaSorteggio = filter.getSalva();
	filtro.descrizioneSorteggio = filter.getDescrizione();
	if (EntityUtils.getNestedProperty(filter, "intervalloCampionamentoDal") != null) {
	    Calendar t = Calendar.getInstance();
	    t.setTime(filter.getIntervalloCampionamentoDal());
	    t.set(Calendar.HOUR_OF_DAY, 0);
	    t.set(Calendar.MINUTE, 0);
	    t.set(Calendar.SECOND, 0);
	    filtro.intervalloCampionamentoDal = t.getTime();
	}
	if (EntityUtils.getNestedProperty(filter, "intervalloCampionamentoAl") != null) {
	    Calendar t = Calendar.getInstance();
	    t.setTime(filter.getIntervalloCampionamentoAl());
	    t.set(Calendar.HOUR_OF_DAY, 23);
	    t.set(Calendar.MINUTE, 59);
	    t.set(Calendar.SECOND, 59);
	    filtro.intervalloCampionamentoAl = t.getTime();
	}
	filtro.escludiIstanzeSorteggiate = filter.getEscludeIstanze();
	filtro.idSorteggiDaEscludere = filter.getListaEstrazioni();
	filtro.idCategorieDaEscludere = filter.getListaCategorieEstrazioni();
	filtro.gruppiDiIstanze = filter.getGruppiIstanze();
	filtro.arrotondamento = filter.getArrotondamento();
	if (EntityUtils.getNestedProperty(filter, "categoria.id.codice") != null) {
	    filtro.idCategoriaSorteggio = filter.getCategoria().getId().getCodice();
	}
	return filtro;
    }
}
