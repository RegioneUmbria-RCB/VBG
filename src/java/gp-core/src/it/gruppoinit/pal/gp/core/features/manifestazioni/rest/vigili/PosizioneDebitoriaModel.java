package it.gruppoinit.pal.gp.core.features.manifestazioni.rest.vigili;

import java.math.BigDecimal;
import java.util.Date;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import it.gruppoinit.pal.gp.core.domain.DettPosizioneDebitoria;

@XmlRootElement
public class PosizioneDebitoriaModel {

    @XmlElement
    private boolean pagata;
    @XmlElement
    private BigDecimal importoIvato;
    @XmlElement
    private Date ultimoAggiornamento;
    @XmlElement
    private Integer id;
    @XmlElement
    private String iuv;
    @XmlElement
    private String codiceAvviso;
    @XmlElement
    private String qrcode;

    public PosizioneDebitoriaModel() {

	super();
    }

    public boolean getPagata() {

	return pagata;
    }

    public void setPagata(boolean pagata) {

	this.pagata = pagata;
    }

    public Date getUltimoAggiornamento() {

	return ultimoAggiornamento;
    }

    public void setUltimoAggiornamento(Date ultimoAggiornamento) {

	this.ultimoAggiornamento = ultimoAggiornamento;
    }

    public Integer getId() {

	return id;
    }

    public void setId(Integer id) {

	this.id = id;
    }

    public BigDecimal getImportoIvato() {

	return importoIvato;
    }

    public void setImportoIvato(BigDecimal importoIvato) {

	this.importoIvato = importoIvato;
    }

    public String getIuv() {

	return iuv;
    }

    public void setIuv(String iuv) {

	this.iuv = iuv;
    }

    public String getCodiceAvviso() {

	return codiceAvviso;
    }

    public void setCodiceAvviso(String codiceAvviso) {

	this.codiceAvviso = codiceAvviso;
    }

    public String getQrcode() {

	return qrcode;
    }

    public void setQrcode(String qrcode) {

	this.qrcode = qrcode;
    }

    public static PosizioneDebitoriaModel fromDettPosizioneDebitoria(DettPosizioneDebitoria dettPosizioneDebitoria) {

	PosizioneDebitoriaModel ret = new PosizioneDebitoriaModel();
	ret.setId(dettPosizioneDebitoria.getId().getCodice());
	ret.setPagata(dettPosizioneDebitoria.mostraComePagatoSuAppVigili());
	ret.setUltimoAggiornamento(dettPosizioneDebitoria.getDataUltimoStato());
	ret.setImportoIvato(dettPosizioneDebitoria.getImportoIvato());
	ret.setIuv(dettPosizioneDebitoria.getIuv());
	ret.setCodiceAvviso(dettPosizioneDebitoria.getCodiceAvviso());
	ret.setQrcode(dettPosizioneDebitoria.getQrcode());
	return ret;
    }
}
