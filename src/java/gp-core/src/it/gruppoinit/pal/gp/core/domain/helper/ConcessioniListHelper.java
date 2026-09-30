package it.gruppoinit.pal.gp.core.domain.helper;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

public class ConcessioniListHelper implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 5676868506769530765L;
    private String origine;
    private BigInteger progressivo;
    private String conc_autorizresponsabile;
    private String idcomune;
    private Short iconc_codicecausale;
    private Date data_storico;
    private Date data_istanza;
    private String software;
    private BigInteger ist_codiceistanza;
    private String ist_numeroistanza;
    private BigInteger ist_codicerichiedente;
    private String ist_codicecomune;
    private BigInteger conc_id;
    private String conc_numero;
    private String conc_codicecomune;
    private Date conc_datavalidita;
    private Date datavalidita;
    private Date conc_datarilascio;
    private Date datarilascio;
    private Date conc_datascadenza;
    private String conc_codicetipo;
    private String conc_tipo;
    private String conc_stagionalea;
    private String conc_stagionaleda;
    private Boolean conc_attiva;
    private BigInteger conc_codicetitolare;
    private BigInteger conc_fkidregistro;
    private String conc_registro;
    private String conc_titolare;
    private String conc_tit_cf;
    private String conc_tit_piva;
    private String conc_tit_tipoanagrafe;
    private String aut_numero;
    private Date aut_data;
    private Date autorizdata;
    private BigInteger aut_codregistro;
    private String aut_registro;
    private String ist_nominativo;
    private String ist_ric_tipoanagrafe;
    private String ist_nominativoazienda;
    private String ist_azi_tipoanagrafe;
    private String ist_codicefiscale;
    private String ist_partitaiva;
    private String iconc_causale;
    private BigInteger iconc_codicecausalestorico;
    private String icon_causalesorico;
    private BigInteger conc_idmercato;
    private String conc_mercato;
    private Boolean merc_attivo;
    private BigInteger conc_idposteggio;
    private String conc_posteggio;
    private BigDecimal conc_pos_larghezza;
    private BigDecimal conc_pos_lunghezza;
    private BigDecimal conc_pos_superficie;
    private BigInteger post_idtipospazio;
    private String post_tipospazio;
    private BigInteger conc_idmercatiuso;
    private String conc_descrizioneuso;
    private BigInteger peso_mercato;
    private BigInteger merc_iduso;
    private BigInteger conc_pos_codicestradario;
    private String stradario_descrizione;
    private String conc_pos_note;
    private String conc_enterilascio;
    private Date datacessazione_aut;
    //
    private BigInteger conc_codiceoccupante;
    private String conc_occupante;
    private String conc_occ_cf;
    private String conc_occ_piva;
    private String conc_occ_tipoanagrafe;

    public String getOrigine() {

	return origine;
    }

    public void setOrigine(String origine) {

	this.origine = origine;
    }

    public BigInteger getProgressivo() {

	return progressivo;
    }

    public void setProgressivo(BigInteger progressivo) {

	this.progressivo = progressivo;
    }

    public String getConc_autorizresponsabile() {

	return conc_autorizresponsabile;
    }

    public void setConc_autorizresponsabile(String conc_autorizresponsabile) {

	this.conc_autorizresponsabile = conc_autorizresponsabile;
    }

    public String getIdcomune() {

	return idcomune;
    }

    public void setIdcomune(String idcomune) {

	this.idcomune = idcomune;
    }

    public Short getIconc_codicecausale() {

	return iconc_codicecausale;
    }

    public void setIconc_codicecausale(Short iconc_codicecausale) {

	this.iconc_codicecausale = iconc_codicecausale;
    }

    public Date getData_storico() {

	return data_storico;
    }

    public void setData_storico(Date data_storico) {

	this.data_storico = data_storico;
    }

    public Date getData_istanza() {

	return data_istanza;
    }

    public void setData_istanza(Date data_istanza) {

	this.data_istanza = data_istanza;
    }

    public String getSoftware() {

	return software;
    }

    public void setSoftware(String software) {

	this.software = software;
    }

    public BigInteger getIst_codiceistanza() {

	return ist_codiceistanza;
    }

    public void setIst_codiceistanza(BigInteger ist_codiceistanza) {

	this.ist_codiceistanza = ist_codiceistanza;
    }

    public String getIst_numeroistanza() {

	return ist_numeroistanza;
    }

    public void setIst_numeroistanza(String ist_numeroistanza) {

	this.ist_numeroistanza = ist_numeroistanza;
    }

    public BigInteger getIst_codicerichiedente() {

	return ist_codicerichiedente;
    }

    public void setIst_codicerichiedente(BigInteger ist_codicerichiedente) {

	this.ist_codicerichiedente = ist_codicerichiedente;
    }

    public String getIst_codicecomune() {

	return ist_codicecomune;
    }

    public void setIst_codicecomune(String ist_codicecomune) {

	this.ist_codicecomune = ist_codicecomune;
    }

    public BigInteger getConc_id() {

	return conc_id;
    }

    public void setConc_id(BigInteger conc_id) {

	this.conc_id = conc_id;
    }

    public String getConc_numero() {

	return conc_numero;
    }

    public void setConc_numero(String conc_numero) {

	this.conc_numero = conc_numero;
    }

    public String getConc_codicecomune() {

	return conc_codicecomune;
    }

    public void setConc_codicecomune(String conc_codicecomune) {

	this.conc_codicecomune = conc_codicecomune;
    }

    public Date getConc_datavalidita() {

	return conc_datavalidita;
    }

    public void setConc_datavalidita(Date conc_datavalidita) {

	this.conc_datavalidita = conc_datavalidita;
    }

    public Date getDatavalidita() {

	return datavalidita;
    }

    public void setDatavalidita(Date datavalidita) {

	this.datavalidita = datavalidita;
    }

    public Date getConc_datarilascio() {

	return conc_datarilascio;
    }

    public void setConc_datarilascio(Date conc_datarilascio) {

	this.conc_datarilascio = conc_datarilascio;
    }

    public Date getDatarilascio() {

	return datarilascio;
    }

    public void setDatarilascio(Date datarilascio) {

	this.datarilascio = datarilascio;
    }

    public Date getConc_datascadenza() {

	return conc_datascadenza;
    }

    public void setConc_datascadenza(Date conc_datascadenza) {

	this.conc_datascadenza = conc_datascadenza;
    }

    public String getConc_codicetipo() {

	return conc_codicetipo;
    }

    public void setConc_codicetipo(String conc_codicetipo) {

	this.conc_codicetipo = conc_codicetipo;
    }

    public String getConc_tipo() {

	return conc_tipo;
    }

    public void setConc_tipo(String conc_tipo) {

	this.conc_tipo = conc_tipo;
    }

    public String getConc_stagionalea() {

	return conc_stagionalea;
    }

    public void setConc_stagionalea(String conc_stagionalea) {

	this.conc_stagionalea = conc_stagionalea;
    }

    public String getConc_stagionaleda() {

	return conc_stagionaleda;
    }

    public void setConc_stagionaleda(String conc_stagionaleda) {

	this.conc_stagionaleda = conc_stagionaleda;
    }

    public Boolean getConc_attiva() {

	return conc_attiva;
    }

    public void setConc_attiva(Boolean conc_attiva) {

	this.conc_attiva = conc_attiva;
    }

    public BigInteger getConc_codicetitolare() {

	return conc_codicetitolare;
    }

    public void setConc_codicetitolare(BigInteger conc_codicetitolare) {

	this.conc_codicetitolare = conc_codicetitolare;
    }

    public BigInteger getConc_fkidregistro() {

	return conc_fkidregistro;
    }

    public void setConc_fkidregistro(BigInteger conc_fkidregistro) {

	this.conc_fkidregistro = conc_fkidregistro;
    }

    public String getConc_registro() {

	return conc_registro;
    }

    public void setConc_registro(String conc_registro) {

	this.conc_registro = conc_registro;
    }

    public String getConc_titolare() {

	return conc_titolare;
    }

    public void setConc_titolare(String conc_titolare) {

	this.conc_titolare = conc_titolare;
    }

    public String getConc_tit_cf() {

	return conc_tit_cf;
    }

    public void setConc_tit_cf(String conc_tit_cf) {

	this.conc_tit_cf = conc_tit_cf;
    }

    public String getConc_tit_piva() {

	return conc_tit_piva;
    }

    public void setConc_tit_piva(String conc_tit_piva) {

	this.conc_tit_piva = conc_tit_piva;
    }

    public String getConc_tit_tipoanagrafe() {

	return conc_tit_tipoanagrafe;
    }

    public void setConc_tit_tipoanagrafe(String conc_tit_tipoanagrafe) {

	this.conc_tit_tipoanagrafe = conc_tit_tipoanagrafe;
    }

    public String getAut_numero() {

	return aut_numero;
    }

    public void setAut_numero(String aut_numero) {

	this.aut_numero = aut_numero;
    }

    public Date getAut_data() {

	return aut_data;
    }

    public void setAut_data(Date aut_data) {

	this.aut_data = aut_data;
    }

    public Date getAutorizdata() {

	return autorizdata;
    }

    public void setAutorizdata(Date autorizdata) {

	this.autorizdata = autorizdata;
    }

    public BigInteger getAut_codregistro() {

	return aut_codregistro;
    }

    public void setAut_codregistro(BigInteger aut_codregistro) {

	this.aut_codregistro = aut_codregistro;
    }

    public String getAut_registro() {

	return aut_registro;
    }

    public void setAut_registro(String aut_registro) {

	this.aut_registro = aut_registro;
    }

    public String getIst_nominativo() {

	return ist_nominativo;
    }

    public void setIst_nominativo(String ist_nominativo) {

	this.ist_nominativo = ist_nominativo;
    }

    public String getIst_ric_tipoanagrafe() {

	return ist_ric_tipoanagrafe;
    }

    public void setIst_ric_tipoanagrafe(String ist_ric_tipoanagrafe) {

	this.ist_ric_tipoanagrafe = ist_ric_tipoanagrafe;
    }

    public String getIst_nominativoazienda() {

	return ist_nominativoazienda;
    }

    public void setIst_nominativoazienda(String ist_nominativoazienda) {

	this.ist_nominativoazienda = ist_nominativoazienda;
    }

    public String getIst_azi_tipoanagrafe() {

	return ist_azi_tipoanagrafe;
    }

    public void setIst_azi_tipoanagrafe(String ist_azi_tipoanagrafe) {

	this.ist_azi_tipoanagrafe = ist_azi_tipoanagrafe;
    }

    public String getIst_codicefiscale() {

	return ist_codicefiscale;
    }

    public void setIst_codicefiscale(String ist_codicefiscale) {

	this.ist_codicefiscale = ist_codicefiscale;
    }

    public String getIst_partitaiva() {

	return ist_partitaiva;
    }

    public void setIst_partitaiva(String ist_partitaiva) {

	this.ist_partitaiva = ist_partitaiva;
    }

    public String getIconc_causale() {

	return iconc_causale;
    }

    public void setIconc_causale(String iconc_causale) {

	this.iconc_causale = iconc_causale;
    }

    public BigInteger getIconc_codicecausalestorico() {

	return iconc_codicecausalestorico;
    }

    public void setIconc_codicecausalestorico(BigInteger iconc_codicecausalestorico) {

	this.iconc_codicecausalestorico = iconc_codicecausalestorico;
    }

    public String getIcon_causalesorico() {

	return icon_causalesorico;
    }

    public void setIcon_causalesorico(String icon_causalesorico) {

	this.icon_causalesorico = icon_causalesorico;
    }

    public BigInteger getConc_idmercato() {

	return conc_idmercato;
    }

    public void setConc_idmercato(BigInteger conc_idmercato) {

	this.conc_idmercato = conc_idmercato;
    }

    public String getConc_mercato() {

	return conc_mercato;
    }

    public void setConc_mercato(String conc_mercato) {

	this.conc_mercato = conc_mercato;
    }

    public Boolean getMerc_attivo() {

	return merc_attivo;
    }

    public void setMerc_attivo(Boolean merc_attivo) {

	this.merc_attivo = merc_attivo;
    }

    public BigInteger getConc_idposteggio() {

	return conc_idposteggio;
    }

    public void setConc_idposteggio(BigInteger conc_idposteggio) {

	this.conc_idposteggio = conc_idposteggio;
    }

    public String getConc_posteggio() {

	return conc_posteggio;
    }

    public void setConc_posteggio(String conc_posteggio) {

	this.conc_posteggio = conc_posteggio;
    }

    public BigDecimal getConc_pos_larghezza() {

	return conc_pos_larghezza;
    }

    public void setConc_pos_larghezza(BigDecimal conc_pos_larghezza) {

	this.conc_pos_larghezza = conc_pos_larghezza;
    }

    public BigDecimal getConc_pos_lunghezza() {

	return conc_pos_lunghezza;
    }

    public void setConc_pos_lunghezza(BigDecimal conc_pos_lunghezza) {

	this.conc_pos_lunghezza = conc_pos_lunghezza;
    }

    public BigDecimal getConc_pos_superficie() {

	return conc_pos_superficie;
    }

    public void setConc_pos_superficie(BigDecimal conc_pos_superficie) {

	this.conc_pos_superficie = conc_pos_superficie;
    }

    public BigInteger getPost_idtipospazio() {

	return post_idtipospazio;
    }

    public void setPost_idtipospazio(BigInteger post_idtipospazio) {

	this.post_idtipospazio = post_idtipospazio;
    }

    public String getPost_tipospazio() {

	return post_tipospazio;
    }

    public void setPost_tipospazio(String post_tipospazio) {

	this.post_tipospazio = post_tipospazio;
    }

    public BigInteger getConc_idmercatiuso() {

	return conc_idmercatiuso;
    }

    public void setConc_idmercatiuso(BigInteger conc_idmercatiuso) {

	this.conc_idmercatiuso = conc_idmercatiuso;
    }

    public String getConc_descrizioneuso() {

	return conc_descrizioneuso;
    }

    public void setConc_descrizioneuso(String conc_descrizioneuso) {

	this.conc_descrizioneuso = conc_descrizioneuso;
    }

    public BigInteger getPeso_mercato() {

	return peso_mercato;
    }

    public void setPeso_mercato(BigInteger peso_mercato) {

	this.peso_mercato = peso_mercato;
    }

    public BigInteger getMerc_iduso() {

	return merc_iduso;
    }

    public void setMerc_iduso(BigInteger merc_iduso) {

	this.merc_iduso = merc_iduso;
    }

    public BigInteger getConc_pos_codicestradario() {

	return conc_pos_codicestradario;
    }

    public void setConc_pos_codicestradario(BigInteger conc_pos_codicestradario) {

	this.conc_pos_codicestradario = conc_pos_codicestradario;
    }

    public String getStradario_descrizione() {

	return stradario_descrizione;
    }

    public void setStradario_descrizione(String stradario_descrizione) {

	this.stradario_descrizione = stradario_descrizione;
    }

    public String getConc_pos_note() {

	return conc_pos_note;
    }

    public void setConc_pos_note(String conc_pos_note) {

	this.conc_pos_note = conc_pos_note;
    }

    public String getConc_enterilascio() {

	return conc_enterilascio;
    }

    public void setConc_enterilascio(String conc_enterilascio) {

	this.conc_enterilascio = conc_enterilascio;
    }

    public Date getDatacessazione_aut() {

	return datacessazione_aut;
    }

    public void setDatacessazione_aut(Date datacessazione_aut) {

	this.datacessazione_aut = datacessazione_aut;
    }

    public BigInteger getConc_codiceoccupante() {

	return conc_codiceoccupante;
    }

    public void setConc_codiceoccupante(BigInteger conc_codiceoccupante) {

	this.conc_codiceoccupante = conc_codiceoccupante;
    }

    public String getConc_occupante() {

	return conc_occupante;
    }

    public void setConc_occupante(String conc_occupante) {

	this.conc_occupante = conc_occupante;
    }

    public String getConc_occ_cf() {

	return conc_occ_cf;
    }

    public void setConc_occ_cf(String conc_occ_cf) {

	this.conc_occ_cf = conc_occ_cf;
    }

    public String getConc_occ_piva() {

	return conc_occ_piva;
    }

    public void setConc_occ_piva(String conc_occ_piva) {

	this.conc_occ_piva = conc_occ_piva;
    }

    public String getConc_occ_tipoanagrafe() {

	return conc_occ_tipoanagrafe;
    }

    public void setConc_occ_tipoanagrafe(String conc_occ_tipoanagrafe) {

	this.conc_occ_tipoanagrafe = conc_occ_tipoanagrafe;
    }
}
