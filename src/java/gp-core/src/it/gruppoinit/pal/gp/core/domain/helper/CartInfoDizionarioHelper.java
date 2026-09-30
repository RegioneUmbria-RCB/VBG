package it.gruppoinit.pal.gp.core.domain.helper;

import java.util.ArrayList;
import java.util.List;

import it.gruppoinit.pal.gp.core.domain.Amministrazioni;
import it.gruppoinit.pal.gp.core.domain.StpTipologieEndo2;
import it.gruppoinit.pal.gp.core.domain.Tipimovimento;

public class CartInfoDizionarioHelper {

    // Campi di appoggio per creare i vari campi di ricerca ajax 
    private Amministrazioni amministrazioniTransient;
    private Tipimovimento tipimovimentoTransient;
    private Tipimovimento tipimovimentoOrdinarioTransient;
    private Tipimovimento tipimovimentoComunicazioneTransient;
    // Utilizato per recuperare le informazioni per fare le configurazioni 
    private List<EndoTipo1Helper> endoTipo1Helpers = new ArrayList<EndoTipo1Helper>();
    private List<StpTipologieEndo2> stpTipologieEndo2s = new ArrayList<StpTipologieEndo2>();
    // Liste che verranno popolate con i dati presenti nel file xml del dizionazio e verranno utilizzati
    // fare gli inserimenti in Inventario procedimenti,Albero proc
    //    private List<TipologiaEndoTipo1> tipologiaEndoTipo1s = new ArrayList<TipologiaEndoTipo1>();
    //    private List<CategoriaEndoTipo1> categoriaEndoTipo1s = new ArrayList<CategoriaEndoTipo1>();
    //    private List<EndoTipo1> endoTipo1s = new ArrayList<EndoTipo1>();
    private List<Object> listDaDizionario = new ArrayList<Object>();
    // campi di appoggio per recuperare i codici per le configurazioni
    private String codiciAmministrazioniCart;
    private String codiciAmministrazioni;
    private String codiciTipimovimento;
    private String codiciTipimovimentoOrdinario;
    private String codiciTipimovimentoComunicazione;
    private String codiciAzioni;
    private String codiceTipologieEndo2;
    private String descrizioneTipologieEndo2;
    private boolean isSovrascriviConfigurazioneEndo;
    private boolean isSovrascriviConfigurazioneAlberoproc;
    private String idEgov;
    private boolean escludiDisabilitati = false;
    private Integer totEndoTipo1 = null;
    private Integer numEndoTipo1StpCodiceValido = null;
    private Integer totAttivita = null;
    private Integer numAttivitaStpCodiceValido = null;
    // private String trustStpCodiceVert = WebConstants.VERTICALIZZAZIONE_CART_VALORE_N_INVENTARIO;
    private String stpAttivitaNonValidi;
    private String stpEndo1NonValidi;
    private String codiciRegStpAttivitaNonValidi;
    private boolean trustStpCodice;

    public boolean isTrustStpCodice() {

	return trustStpCodice;
    }

    public void setTrustStpCodice(boolean trustStpCodice) {

	this.trustStpCodice = trustStpCodice;
    }

    public CartInfoDizionarioHelper() {

	this.amministrazioniTransient = new Amministrazioni();
	this.tipimovimentoTransient = new Tipimovimento();
	this.tipimovimentoOrdinarioTransient = new Tipimovimento();
	this.tipimovimentoComunicazioneTransient = new Tipimovimento();
    }

    public Tipimovimento getTipimovimentoTransient() {

	return tipimovimentoTransient;
    }

    public Tipimovimento getTipimovimentoOrdinarioTransient() {

	return tipimovimentoOrdinarioTransient;
    }

    public void setTipimovimentoOrdinarioTransient(Tipimovimento tipimovimentoOrdinarioTransient) {

	this.tipimovimentoOrdinarioTransient = tipimovimentoOrdinarioTransient;
    }

    public Tipimovimento getTipimovimentoComunicazioneTransient() {

	return tipimovimentoComunicazioneTransient;
    }

    public void setTipimovimentoComunicazioneTransient(Tipimovimento tipimovimentoComunicazioneTransient) {

	this.tipimovimentoComunicazioneTransient = tipimovimentoComunicazioneTransient;
    }

    public void setTipimovimentoTransient(Tipimovimento tipimovimentoTransient) {

	this.tipimovimentoTransient = tipimovimentoTransient;
    }

    public Amministrazioni getAmministrazioniTransient() {

	return amministrazioniTransient;
    }

    public void setAmministrazioniTransient(Amministrazioni amministrazioniTransient) {

	this.amministrazioniTransient = amministrazioniTransient;
    }

    public String getCodiciAmministrazioniCart() {

	return codiciAmministrazioniCart;
    }

    public void setCodiciAmministrazioniCart(String codiciAmministrazioniCart) {

	this.codiciAmministrazioniCart = codiciAmministrazioniCart;
    }

    public List<EndoTipo1Helper> getEndoTipo1Helpers() {

	return endoTipo1Helpers;
    }

    public void setEndoTipo1Helpers(List<EndoTipo1Helper> endoTipo1Helpers) {

	this.endoTipo1Helpers = endoTipo1Helpers;
    }

    public List<StpTipologieEndo2> getStpTipologieEndo2s() {

	return stpTipologieEndo2s;
    }

    public void setStpTipologieEndo2s(List<StpTipologieEndo2> stpTipologieEndo2s) {

	this.stpTipologieEndo2s = stpTipologieEndo2s;
    }

    //    public List<TipologiaEndoTipo1> getTipologiaEndoTipo1s() {
    //
    //	return tipologiaEndoTipo1s;
    //    }
    //
    //    public void setTipologiaEndoTipo1s(List<TipologiaEndoTipo1> tipologiaEndoTipo1s) {
    //
    //	this.tipologiaEndoTipo1s = tipologiaEndoTipo1s;
    //    }
    //
    //    public List<CategoriaEndoTipo1> getCategoriaEndoTipo1s() {
    //
    //	return categoriaEndoTipo1s;
    //    }
    //
    //    public void setCategoriaEndoTipo1s(List<CategoriaEndoTipo1> categoriaEndoTipo1s) {
    //
    //	this.categoriaEndoTipo1s = categoriaEndoTipo1s;
    //    }
    //
    //    public List<EndoTipo1> getEndoTipo1s() {
    //
    //	return endoTipo1s;
    //    }
    //
    //    public void setEndoTipo1s(List<EndoTipo1> endoTipo1s) {
    //
    //	this.endoTipo1s = endoTipo1s;
    //    }
    public List<Object> getListDaDizionario() {

	return listDaDizionario;
    }

    public void setListDaDizionario(List<Object> listDaDizionario) {

	this.listDaDizionario = listDaDizionario;
    }

    //    public List<StpTipologieendo2Helper> getStpTipologieendo2Helpers() {
    //
    //	return stpTipologieendo2Helpers;
    //    }
    //
    //    public void setStpTipologieendo2Helpers(List<StpTipologieendo2Helper> stpTipologieendo2Helpers) {
    //
    //	this.stpTipologieendo2Helpers = stpTipologieendo2Helpers;
    //    }
    public String getCodiciAmministrazioni() {

	return codiciAmministrazioni;
    }

    public void setCodiciAmministrazioni(String codiciAmministrazioni) {

	this.codiciAmministrazioni = codiciAmministrazioni;
    }

    public String getCodiciTipimovimento() {

	return codiciTipimovimento;
    }

    public void setCodiciTipimovimento(String codiciTipimovimento) {

	this.codiciTipimovimento = codiciTipimovimento;
    }

    public String getCodiciTipimovimentoOrdinario() {

	return codiciTipimovimentoOrdinario;
    }

    public void setCodiciTipimovimentoOrdinario(String codiciTipimovimentoOrdinario) {

	this.codiciTipimovimentoOrdinario = codiciTipimovimentoOrdinario;
    }

    public String getCodiciTipimovimentoComunicazione() {

	return codiciTipimovimentoComunicazione;
    }

    public void setCodiciTipimovimentoComunicazione(String codiciTipimovimentoComunicazione) {

	this.codiciTipimovimentoComunicazione = codiciTipimovimentoComunicazione;
    }

    public String getCodiciAzioni() {

	return codiciAzioni;
    }

    public void setCodiciAzioni(String codiciAzioni) {

	this.codiciAzioni = codiciAzioni;
    }

    public String getCodiceTipologieEndo2() {

	return codiceTipologieEndo2;
    }

    public void setCodiceTipologieEndo2(String codiceTipologieEndo2) {

	this.codiceTipologieEndo2 = codiceTipologieEndo2;
    }

    public String getDescrizioneTipologieEndo2() {

	return descrizioneTipologieEndo2;
    }

    public void setDescrizioneTipologieEndo2(String descrizioneTipologieEndo2) {

	this.descrizioneTipologieEndo2 = descrizioneTipologieEndo2;
    }

    public boolean getIsSovrascriviConfigurazioneEndo() {

	return isSovrascriviConfigurazioneEndo;
    }

    public void setIsSovrascriviConfigurazioneEndo(boolean isSovrascriviConfigurazioneEndo) {

	this.isSovrascriviConfigurazioneEndo = isSovrascriviConfigurazioneEndo;
    }

    public boolean getIsSovrascriviConfigurazioneAlberoproc() {

	return isSovrascriviConfigurazioneAlberoproc;
    }

    public void setIsSovrascriviConfigurazioneAlberoproc(boolean isSovrascriviConfigurazioneAlberoproc) {

	this.isSovrascriviConfigurazioneAlberoproc = isSovrascriviConfigurazioneAlberoproc;
    }

    public String getIdEgov() {

	return idEgov;
    }

    public void setIdEgov(String idEgov) {

	this.idEgov = idEgov;
    }

    public boolean isEscludiDisabilitati() {

	return escludiDisabilitati;
    }

    public void setEscludiDisabilitati(boolean escludiDisabilitati) {

	this.escludiDisabilitati = escludiDisabilitati;
    }

    public Integer getTotEndoTipo1() {

	return this.totEndoTipo1;
    }

    public void setTotEndoTipo1(Integer totEndoTipo1) {

	this.totEndoTipo1 = totEndoTipo1;
    }

    public Integer getNumEndoTipo1StpCodiceValido() {

	return this.numEndoTipo1StpCodiceValido;
    }

    public void setNumEndoTipo1StpCodiceValido(Integer numEndoTipo1StpCodiceValido) {

	this.numEndoTipo1StpCodiceValido = numEndoTipo1StpCodiceValido;
    }

    public Integer getTotAttivita() {

	return this.totAttivita;
    }

    public void setTotAttivita(Integer totAttivita) {

	this.totAttivita = totAttivita;
    }

    public Integer getNumAttivitaStpCodiceValido() {

	return this.numAttivitaStpCodiceValido;
    }

    public void setNumAttivitaStpCodiceValido(Integer numAttivitaStpCodiceValido) {

	this.numAttivitaStpCodiceValido = numAttivitaStpCodiceValido;
    }
    //    public boolean isTrustStpCodice() {
    //
    //	return BooleanUtils.toBoolean(StringUtils.defaultString(this.trustStpCodiceVert, WebConstants.VERTICALIZZAZIONE_CART_VALORE_S_INVENTARIO),
    //		WebConstants.VERTICALIZZAZIONE_CART_VALORE_S_INVENTARIO, WebConstants.VERTICALIZZAZIONE_CART_VALORE_N_INVENTARIO);
    //    }
    //
    //    public String getTrustStpCodiceVert() {
    //
    //	return this.trustStpCodiceVert;
    //    }
    //
    //    public void setTrustStpCodiceVert(String trustStpCodiceVert) {
    //
    //	this.trustStpCodiceVert = trustStpCodiceVert;
    //    }

    public String getStpAttivitaNonValidi() {

	return this.stpAttivitaNonValidi;
    }

    public void setStpAttivitaNonValidi(String stpAttivitaNonValidi) {

	this.stpAttivitaNonValidi = stpAttivitaNonValidi;
    }

    public String getStpEndo1NonValidi() {

	return this.stpEndo1NonValidi;
    }

    public void setStpEndo1NonValidi(String stpEndo1NonValidi) {

	this.stpEndo1NonValidi = stpEndo1NonValidi;
    }

    public String getCodiciRegStpAttivitaNonValidi() {

	return this.codiciRegStpAttivitaNonValidi;
    }

    public void setCodiciRegStpAttivitaNonValidi(String codiciRegStpAttivitaNonValidi) {

	this.codiciRegStpAttivitaNonValidi = codiciRegStpAttivitaNonValidi;
    }
}
