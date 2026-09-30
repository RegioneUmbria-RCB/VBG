package it.gruppoinit.pal.gp.pay.ws.schema;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

import org.apache.commons.lang.BooleanUtils;

import it.gruppoinit.pal.gp.pay.dao.utils.PosizioneDebitoriaFiltrata;
import it.gruppoinit.pal.gp.pay.domain.PayDettaglioImporti;
import it.gruppoinit.pal.gp.pay.domain.PayPagamenti;
import it.gruppoinit.pal.gp.pay.domain.PayPosizioniDebitorie;
import it.gruppoinit.pal.gp.pay.domain.PaySessioniPagamento;
import it.gruppoinit.pal.gp.pay.domain.PayStatoPagamenti;
import it.gruppoinit.pal.gp.pay.service.PaySessioniPagamentoService;

@XmlRootElement(name = "posizione_debitoria")
@XmlSeeAlso({ ImportiResponseType.class, StatoResponseType.class, PagamentiResponseType.class, SessioniPagamentoResponseType.class })
public class PosizioneDebitoriaResponseType {

    @XmlElement(name = "id_posizione_debitoria")
    private Integer idPosizioneDebitoria;
    @XmlElement(name = "uuid")
    private String uuid;
    @XmlElement(name = "iuv")
    private String iuv;
    @XmlElement(name = "codice_avviso")
    private String codiceAvviso;
    @XmlElement(name = "qr_code")
    private String qrCode;
    @XmlElement(name = "descrizione")
    private String descrizione;
    @XmlElement(name = "data_registrazione", type = Date.class)
    private Date dataRegistrazione;
    @XmlElement(name = "importi")
    private Set<ImportiResponseType> importi = new HashSet<>();
    @XmlElement(name = "stato_attuale")
    private StatoResponseType statoAttuale;
    @XmlElement(name = "pagamenti")
    private Set<PagamentiResponseType> pagamenti = new HashSet<>();
    @XmlElement(name = "stati")
    private Set<StatoResponseType> stati = new HashSet<>();
    @XmlElement(name = "otf")
    private boolean otf;
    @XmlElement(name = "nominativo_soggetto_debitore")
    private String nominativoSoggettoDebitore;
    @XmlElement(name = "cf_soggetto_debitore")
    private String cfSoggettoDebitore;
    @XmlElement(name = "data_scadenza", type = Date.class)
    private Date dataScadenza;
    @XmlElement(name = "sessioniPagamento")
    private Set<SessioniPagamentoResponseType> sessioniPagamento = new HashSet<>();

    public PosizioneDebitoriaResponseType() {

	super();
    }

    public PosizioneDebitoriaResponseType(PosizioneDebitoriaFiltrata pdf) {

	if (pdf != null) {
	    this.codiceAvviso = pdf.getCodiceAvviso();
	    this.uuid = pdf.getUuid();
	    this.iuv = pdf.getIuv();
	    this.qrCode = pdf.getQrcode();
	    this.idPosizioneDebitoria = pdf.getIdPosizioneDebitoria();
	    this.dataRegistrazione = pdf.getDataRegistrazione();
	    this.descrizione = pdf.getDescrizione();
	    this.otf = BooleanUtils.isTrue(pdf.getFlagOtf());
	}
    }

    public PosizioneDebitoriaResponseType(PayPosizioniDebitorie posizione, PaySessioniPagamentoService paySessioniPagamentoService) {

	if (posizione != null) {
	    this.idPosizioneDebitoria = posizione.getId().getCodice();
	    this.uuid = posizione.getUuid();
	    this.iuv = posizione.getIuv();
	    this.codiceAvviso = posizione.getCodiceAvviso();
	    this.descrizione = posizione.getDescrizioneCausale();
	    this.qrCode = posizione.getQrCode();
	    this.importi = this.importiToImportiResponseType(posizione.getDettagliImporto());
	    this.statoAttuale = new StatoResponseType(posizione.recuperaStatoCorrente());
	    this.pagamenti = this.pagamentiToPagamentiResponseType(posizione.getPagamenti());
	    this.stati = this.statiToStatoResponseType(posizione.getStati());
	    this.dataScadenza = posizione.getDataScadenza();
	    this.dataRegistrazione = posizione.getDataRegistrazione();
	    this.otf = BooleanUtils.isTrue(posizione.getFlagOTF());
	    popolaSoggettodebitore(posizione);
	    popolaSessioniPagamento(paySessioniPagamentoService, posizione.getId().getCodice());
	}
    }

    private void popolaSessioniPagamento(PaySessioniPagamentoService paySessioniPagamentoService, Integer idPosizioneDebitoria) {

	List<PaySessioniPagamento> sessioni = paySessioniPagamentoService.findSessioniPerPosizioneDebitoria(idPosizioneDebitoria);
	for (PaySessioniPagamento sessione : sessioni) {
	    this.getSessioniPagamento().add(SessioniPagamentoResponseType.fromSessione(sessione));
	}
    }

    private void popolaSoggettodebitore(PayPosizioniDebitorie posizione) {

	if (posizione.getSoggettoDebitore() != null) {
	    this.cfSoggettoDebitore = posizione.getSoggettoDebitore().getCfPi();
	    this.nominativoSoggettoDebitore = posizione.getSoggettoDebitore().getDenominazioneCompleta();
	}
    }

    private Set<ImportiResponseType> importiToImportiResponseType(Set<PayDettaglioImporti> importi) {

	Set<ImportiResponseType> retval = new HashSet<>();
	if (importi != null) {
	    for (PayDettaglioImporti importo : importi) {
		retval.add(new ImportiResponseType(importo));
	    }
	}
	return retval;
    }

    private Set<PagamentiResponseType> pagamentiToPagamentiResponseType(Set<PayPagamenti> pagamenti) {

	Set<PagamentiResponseType> retval = new HashSet<>();
	if (importi != null) {
	    for (PayPagamenti pagamento : pagamenti) {
		retval.add(new PagamentiResponseType(pagamento));
	    }
	}
	return retval;
    }

    private Set<StatoResponseType> statiToStatoResponseType(Set<PayStatoPagamenti> stati) {

	Set<StatoResponseType> retval = new HashSet<>();
	if (importi != null) {
	    for (PayStatoPagamenti stato : stati) {
		retval.add(new StatoResponseType(stato));
	    }
	}
	return retval;
    }

    public Integer getIdPosizioneDebitoria() {

	return idPosizioneDebitoria;
    }

    public String getUuid() {

	return uuid;
    }

    public void setIuv(String iuv) {

	this.iuv = iuv;
    }

    public void setQrCode(String qrCode) {

	this.qrCode = qrCode;
    }

    public void setImporti(Set<ImportiResponseType> importi) {

	if (importi == null) {
	    importi = new HashSet<>();
	}
	this.importi = importi;
    }

    public void setStatoAttuale(StatoResponseType statoAttuale) {

	this.statoAttuale = statoAttuale;
    }

    public void setPagamenti(Set<PagamentiResponseType> pagamenti) {

	this.pagamenti = pagamenti;
    }

    public void setStati(Set<StatoResponseType> stati) {

	if (stati == null) {
	    stati = new HashSet<>();
	}
	this.stati = stati;
    }

    public Set<SessioniPagamentoResponseType> getSessioniPagamento() {

	if (this.sessioniPagamento == null) {
	    this.sessioniPagamento = new HashSet<>();
	}
	return sessioniPagamento;
    }

    public void setSessioniPagamento(Set<SessioniPagamentoResponseType> sessioniPagamento) {

	this.sessioniPagamento = sessioniPagamento;
    }

    public void impostaStatoAttuale(PosizioneDebitoriaFiltrata posizione) {

	this.statoAttuale = new StatoResponseType(posizione);
    }

    private boolean esisteImporto(PosizioneDebitoriaFiltrata posizione) {

	for (ImportiResponseType irt : this.importi) {
	    if (posizione.getIdDettaglioImporti().equals(irt.getId())) {
		return true;
	    }
	}
	return false;
    }

    private boolean esisteStato(PosizioneDebitoriaFiltrata posizione) {

	for (StatoResponseType irt : this.stati) {
	    if (posizione.getIdStatoPagamenti().equals(irt.getId())) {
		return true;
	    }
	}
	return false;
    }

    public void aggiorna(PosizioneDebitoriaFiltrata posizione) {

	this.impostaStatoAttuale(posizione);
	if (!esisteImporto(posizione)) {
	    this.importi.add(new ImportiResponseType(posizione));
	}
	if (!esisteStato(posizione)) {
	    this.stati.add(new StatoResponseType(posizione));
	}
    }
}
