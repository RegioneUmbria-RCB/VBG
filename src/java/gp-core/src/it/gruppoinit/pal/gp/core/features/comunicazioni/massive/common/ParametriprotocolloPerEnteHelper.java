package it.gruppoinit.pal.gp.core.features.comunicazioni.massive.common;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import it.gruppoinit.pal.gp.core.domain.helper.CodiceDescrizioneBean;
import it.gruppoinit.pal.gp.core.domain.web.MetadatiBean;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.IdentificativoDescrizioneBean;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.web.IParametriProtocolloPerEnteHelper;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "ammMittente", "classifica", "tipodocumento", "tipodocumentoDescrizione", "comune", "listaClassifiche",
	"listaTipiDocumento", "listaAmministrazioni" })
public class ParametriprotocolloPerEnteHelper implements IParametriProtocolloPerEnteHelper {

    @XmlElement(name = "ammMittente")
    private IdentificativoDescrizioneBean ammMittente;
    @XmlElement(name = "classifica")
    private String classifica;
    @XmlElement(name = "tipodocumento")
    private String tipodocumento;
    @XmlElement(name = "tipodocumentoDescrizione")
    private String tipodocumentoDescrizione;
    @XmlElement(name = "comune")
    private CodiceDescrizioneBean comune;
    @XmlElement(name = "listaClassifiche")
    private List<CodiceDescrizioneBean> listaClassifiche;
    @XmlElement(name = "listaTipiDocumento")
    private List<CodiceDescrizioneBean> listaTipiDocumento;
    @XmlElement(name = "listaAmministrazioni")
    private List<IdentificativoDescrizioneBean> listaAmministrazioni;
    @XmlElement(name = "metadati")
    private List<MetadatiBean> metadati;

    public ParametriprotocolloPerEnteHelper() {

	this.ammMittente = new IdentificativoDescrizioneBean();
    }

    @Override
    public IdentificativoDescrizioneBean getAmmMittente() {

	return ammMittente;
    }

    @Override
    public void setAmmMittente(IdentificativoDescrizioneBean ammMittente) {

	this.ammMittente = ammMittente;
    }

    @Override
    public String getClassifica() {

	return classifica;
    }

    @Override
    public void setClassifica(String classifica) {

	this.classifica = classifica;
    }

    @Override
    public String getTipodocumento() {

	return tipodocumento;
    }

    @Override
    public void setTipodocumento(String tipodocumento) {

	this.tipodocumento = tipodocumento;
    }

    public String getTipodocumentoDescrizione() {

	return tipodocumentoDescrizione;
    }

    public void setTipodocumentoDescrizione(String tipodocumentoDescrizione) {

	this.tipodocumentoDescrizione = tipodocumentoDescrizione;
    }

    @Override
    public CodiceDescrizioneBean getComune() {

	return comune;
    }

    @Override
    public void setComune(CodiceDescrizioneBean comune) {

	this.comune = comune;
    }

    @Override
    public List<CodiceDescrizioneBean> getListaClassifiche() {

	return listaClassifiche;
    }

    @Override
    public void setListaClassifiche(List<CodiceDescrizioneBean> listaClassifiche) {

	this.listaClassifiche = listaClassifiche;
    }

    @Override
    public List<CodiceDescrizioneBean> getListaTipiDocumento() {

	return listaTipiDocumento;
    }

    @Override
    public void setListaTipiDocumento(List<CodiceDescrizioneBean> listaTipiDocumento) {

	this.listaTipiDocumento = listaTipiDocumento;
    }

    @Override
    public List<IdentificativoDescrizioneBean> getListaAmministrazioni() {

	return listaAmministrazioni;
    }

    @Override
    public void setListaAmministrazioni(List<IdentificativoDescrizioneBean> listaAmministrazioni) {

	this.listaAmministrazioni = listaAmministrazioni;
    }

    @Override
    public List<MetadatiBean> getMetadati() {

	if (this.metadati == null) {
	    this.metadati = new ArrayList<MetadatiBean>(0);
	}
	return this.metadati;
    }

    @Override
    public void setMetadati(List<MetadatiBean> metadati) {

	this.metadati = metadati;
    }
}
