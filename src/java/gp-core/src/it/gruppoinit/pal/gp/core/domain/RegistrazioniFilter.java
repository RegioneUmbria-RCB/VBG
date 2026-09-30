/**
 * 
 */
package it.gruppoinit.pal.gp.core.domain;

import it.gruppoinit.pal.gp.core.dao.helper.RaggruppamentoEnum;
import it.gruppoinit.pal.gp.core.dao.helper.RaggruppamentoRiepiloghiIncassi;
import it.gruppoinit.pal.gp.core.domain.web.ChiaveValoreBean;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author francescop
 * @author gianpaolot
 * 
 *         Classe non appartenete al dominio ( creata come classe di appoggio per visualizzare ricerche filtrate)
 */
public class RegistrazioniFilter {

    private RegistrazioniCausali registrazioniCausali;
    private Conti conti;
    private Date dataInizio;
    private Date dataFine;
    private Anagrafe anagrafe;
    private Mercati mercati;
    private Amministrazioni amministrazioni;
    private Alberoproc alberoproc;
    private BigDecimal saldo;
    private BigDecimal incassato;
    private BigDecimal emesso;
    private Integer iva;
    private BigDecimal imponibile;
    // Aggiunti per la ricerca di registrazioni
    private String descrizione;
    private String progressivo;
    private MercatiUso mercatiUso;
    private MercatiD posteggio;
    private Date dataDistinta;
    private BigDecimal importo;
    private short anno;
    private Oneritipirateizzazione oneritipirateizzazione;
    // Tipi raggruppamenti
    private RaggruppamentoEnum raggruppamentoEnum;
    private RaggruppamentoRiepiloghiIncassi raggruppamentoRiepiloghiIncassi;
    // Utilizzato per il calcolo degli Interessi Legali
    ///    
    private List<ContoInteressiLegali> contoInteressiLegaliList = new ArrayList<ContoInteressiLegali>();
    // RATEIZZAZIONI
    private Conti contoInteressiRat;
    private List<ChiaveValoreBean<Conti, Integer>> ordinamentoContiRat = new ArrayList<ChiaveValoreBean<Conti, Integer>>();
    private Integer tipologiaRipartizioneRat;

    public RegistrazioniFilter() {

	this.registrazioniCausali = new RegistrazioniCausali();
	this.conti = new Conti();
	this.anagrafe = new Anagrafe();
	this.mercati = new Mercati();
	this.mercatiUso = new MercatiUso();
	this.amministrazioni = new Amministrazioni();
	this.alberoproc = new Alberoproc();
	this.mercatiUso = new MercatiUso();
	this.posteggio = new MercatiD();
	this.oneritipirateizzazione = new Oneritipirateizzazione();
    }

    public Oneritipirateizzazione getOneritipirateizzazione() {

	return oneritipirateizzazione;
    }

    public void setOneritipirateizzazione(Oneritipirateizzazione oneritipirateizzazione) {

	this.oneritipirateizzazione = oneritipirateizzazione;
    }

    public RegistrazioniCausali getRegistrazioniCausali() {

	return registrazioniCausali;
    }

    public void setRegistrazioniCausali(RegistrazioniCausali registrazioniCausali) {

	this.registrazioniCausali = registrazioniCausali;
    }

    public Conti getConti() {

	return conti;
    }

    public void setConti(Conti conti) {

	this.conti = conti;
    }

    public Date getDataInizio() {

	return dataInizio;
    }

    public void setDataInizio(Date dataInizio) {

	this.dataInizio = dataInizio;
    }

    public Date getDataFine() {

	return dataFine;
    }

    public void setDataFine(Date dataFine) {

	this.dataFine = dataFine;
    }

    public Anagrafe getAnagrafe() {

	return anagrafe;
    }

    public void setAnagrafe(Anagrafe anagrafe) {

	this.anagrafe = anagrafe;
    }

    public Mercati getMercati() {

	return mercati;
    }

    public void setMercati(Mercati mercati) {

	this.mercati = mercati;
    }

    public Amministrazioni getAmministrazioni() {

	return amministrazioni;
    }

    public void setAmministrazioni(Amministrazioni amministrazioni) {

	this.amministrazioni = amministrazioni;
    }

    public Alberoproc getAlberoproc() {

	return alberoproc;
    }

    public void setAlberoproc(Alberoproc alberoproc) {

	this.alberoproc = alberoproc;
    }

    public RaggruppamentoEnum getRaggruppamentoEnum() {

	return raggruppamentoEnum;
    }

    public void setRaggruppamentoEnum(RaggruppamentoEnum raggruppamentoEnum) {

	this.raggruppamentoEnum = raggruppamentoEnum;
    }

    public BigDecimal getSaldo() {

	return saldo;
    }

    public void setSaldo(BigDecimal saldo) {

	this.saldo = saldo;
    }

    public BigDecimal getIncassato() {

	return incassato;
    }

    public void setIncassato(BigDecimal incassato) {

	this.incassato = incassato;
    }

    public BigDecimal getEmesso() {

	return emesso;
    }

    public void setEmesso(BigDecimal emesso) {

	this.emesso = emesso;
    }

    public String getDescrizione() {

	return descrizione;
    }

    public void setDescrizione(String descrizione) {

	this.descrizione = descrizione;
    }

    public String getProgressivo() {

	return progressivo;
    }

    public void setProgressivo(String progressivo) {

	this.progressivo = progressivo;
    }

    public void setMercatiUso(MercatiUso mercatiUso) {

	this.mercatiUso = mercatiUso;
    }

    public MercatiUso getMercatiUso() {

	return mercatiUso;
    }

    public void setImporto(BigDecimal importo) {

	this.importo = importo;
    }

    public BigDecimal getImporto() {

	return importo;
    }

    public void setAnno(short anno) {

	this.anno = anno;
    }

    public short getAnno() {

	return anno;
    }

    public MercatiD getPosteggio() {

	return posteggio;
    }

    public void setPosteggio(MercatiD posteggio) {

	this.posteggio = posteggio;
    }

    public Date getDataDistinta() {

	return dataDistinta;
    }

    public void setDataDistinta(Date dataDistinta) {

	this.dataDistinta = dataDistinta;
    }

    public RaggruppamentoRiepiloghiIncassi getRaggruppamentoRiepiloghiIncassi() {

	return raggruppamentoRiepiloghiIncassi;
    }

    public void setRaggruppamentoRiepiloghiIncassi(RaggruppamentoRiepiloghiIncassi raggruppamentoRiepiloghiIncassi) {

	this.raggruppamentoRiepiloghiIncassi = raggruppamentoRiepiloghiIncassi;
    }

    public List<ContoInteressiLegali> getContoInteressiLegaliList() {

	return contoInteressiLegaliList;
    }

    public void setContoInteressiLegaliList(List<ContoInteressiLegali> contoInteressiLegali) {

	this.contoInteressiLegaliList = contoInteressiLegali;
    }

    public Integer getIva() {

	return iva;
    }

    public void setIva(Integer iva) {

	this.iva = iva;
    }

    public BigDecimal getImponibile() {

	return imponibile;
    }

    public void setImponibile(BigDecimal imponibile) {

	this.imponibile = imponibile;
    }

    public Conti getContoInteressiRat() {

	return contoInteressiRat;
    }

    public void setContoInteressiRat(Conti contoInteressiRat) {

	this.contoInteressiRat = contoInteressiRat;
    }

    public List<ChiaveValoreBean<Conti, Integer>> getOrdinamentoContiRat() {

	return ordinamentoContiRat;
    }

    public void setOrdinamentoContiRat(List<ChiaveValoreBean<Conti, Integer>> ordinamentoContiRat) {

	this.ordinamentoContiRat = ordinamentoContiRat;
    }

    public Integer getTipologiaRipartizioneRat() {

	return tipologiaRipartizioneRat;
    }

    public void setTipologiaRipartizioneRat(Integer tipologiaRipartizioneRat) {

	this.tipologiaRipartizioneRat = tipologiaRipartizioneRat;
    }
}
