package it.gruppoinit.pal.gp.core.service.helper;

import it.gruppoinit.pal.gp.core.domain.helper.CodiceDescrizioneBean;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class PosteggioLiberoHelper {

    private Integer idPosteggio;
    private String codiceposteggio;
    private BigDecimal larghezza;
    private BigDecimal lunghezza;
    private BigDecimal superficie;
    private String note;
    private String tipospazio;
    private String descrizioneStradario;
    private List<CodiceDescrizioneBean> prenotazioni = new ArrayList<CodiceDescrizioneBean>();
    private List<CodiceDescrizioneBean> merceologieConsentite = new ArrayList<CodiceDescrizioneBean>();
    private List<CodiceDescrizioneBean> merceologieVietate = new ArrayList<CodiceDescrizioneBean>();

    public Integer getIdPosteggio() {

	return idPosteggio;
    }

    public void setIdPosteggio(Integer idPosteggio) {

	this.idPosteggio = idPosteggio;
    }

    public String getCodiceposteggio() {

	return codiceposteggio;
    }

    public void setCodiceposteggio(String codiceposteggio) {

	this.codiceposteggio = codiceposteggio;
    }

    public BigDecimal getLarghezza() {

	return larghezza;
    }

    public void setLarghezza(BigDecimal larghezza) {

	this.larghezza = larghezza;
    }

    public BigDecimal getLunghezza() {

	return lunghezza;
    }

    public void setLunghezza(BigDecimal lunghezza) {

	this.lunghezza = lunghezza;
    }

    public BigDecimal getSuperficie() {

	return superficie;
    }

    public void setSuperficie(BigDecimal superficie) {

	this.superficie = superficie;
    }

    public String getNote() {

	return note;
    }

    public void setNote(String note) {

	this.note = note;
    }

    public String getTipospazio() {

	return tipospazio;
    }

    public void setTipospazio(String tipospazio) {

	this.tipospazio = tipospazio;
    }

    public String getDescrizioneStradario() {

	return descrizioneStradario;
    }

    public void setDescrizioneStradario(String descrizioneStradario) {

	this.descrizioneStradario = descrizioneStradario;
    }

    public List<CodiceDescrizioneBean> getPrenotazioni() {

	return prenotazioni;
    }

    public void setPrenotazioni(List<CodiceDescrizioneBean> prenotazioni) {

	this.prenotazioni = prenotazioni;
    }

    public List<CodiceDescrizioneBean> getMerceologieConsentite() {

	return merceologieConsentite;
    }

    public void setMerceologieConsentite(List<CodiceDescrizioneBean> merceologieConsentite) {

	this.merceologieConsentite = merceologieConsentite;
    }

    public List<CodiceDescrizioneBean> getMerceologieVietate() {

	return merceologieVietate;
    }

    public void setMerceologieVietate(List<CodiceDescrizioneBean> merceologieVietate) {

	this.merceologieVietate = merceologieVietate;
    }
}
