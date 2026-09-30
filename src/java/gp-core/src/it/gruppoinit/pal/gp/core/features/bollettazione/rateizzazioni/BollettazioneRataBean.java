package it.gruppoinit.pal.gp.core.features.bollettazione.rateizzazioni;

import java.math.BigDecimal;
import java.util.Date;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import it.gruppoinit.pal.gp.core.domain.BollCfgTipoRate;
import it.gruppoinit.pal.gp.core.domain.RangeRateizzazioni;
import it.gruppoinit.pal.gp.core.features.oneri.rateizzazione.configurazione.tipiscadenze.TipoScadenzaEnum;

@XmlRootElement(name = "bollettazioneRataBean")
public class BollettazioneRataBean {

    @XmlElement(name = "importoMinimo")
    private Integer importoMinimo;
    @XmlElement(name = "importoMassimo")
    private Integer importoMassimo;
    @XmlElement(name = "numeroRate")
    private Integer numeroRate;
    @XmlElement(name = "tipoScadenza")
    private TipoScadenzaEnum tipoScadenza;
    @XmlElement(name = "scadenzePeriodo")
    private String scadenzePeriodo;
    @XmlElement(name = "ripartizione")
    private BigDecimal[] ripartizione;
    @XmlElement(name = "scadenze")
    private Date[] scadenze;

    public Integer getImportoMinimo() {

	return importoMinimo;
    }

    private void setImportoMinimo(Integer importoMinimo) {

	this.importoMinimo = importoMinimo;
    }

    public Integer getImportoMassimo() {

	return importoMassimo;
    }

    private void setImportoMassimo(Integer importoMassimo) {

	this.importoMassimo = importoMassimo;
    }

    public Integer getNumeroRate() {

	return numeroRate;
    }

    private void setNumeroRate(Integer numeroRate) {

	this.numeroRate = numeroRate;
    }

    public BigDecimal[] getRipartizione() {

	return ripartizione;
    }

    private void setRipartizione(BigDecimal[] ripartizione) {

	this.ripartizione = ripartizione;
    }

    public Date[] getScadenze() {

	return scadenze;
    }

    private void setScadenze(Date[] scadenze) {

	this.scadenze = scadenze;
    }

    public TipoScadenzaEnum getTipoScadenza() {

	return tipoScadenza;
    }

    private void setTipoScadenza(TipoScadenzaEnum tipoScadenza) {

	this.tipoScadenza = tipoScadenza;
    }

    public String getScadenzePeriodo() {

	return scadenzePeriodo;
    }

    private void setScadenzePeriodo(String scadenzePeriodo) {

	this.scadenzePeriodo = scadenzePeriodo;
    }

    public static BollettazioneRataBean fromBollCfgTipoRate(BollCfgTipoRate rata) {

	BollettazioneRataBean bean = new BollettazioneRataBean();
	RangeRateizzazioni range = rata.getRangeRateizzazioni();
	bean.setImportoMinimo(range.getRangeBasso());
	bean.setImportoMassimo(range.getRangeAlto());
	bean.setNumeroRate(range.getTiporateizzazione().getNumerorate());
	bean.setRipartizione(range.getTiporateizzazione().getRipartizionerateArray());
	bean.setTipoScadenza(TipoScadenzaEnum.fromValue(range.getTiporateizzazione().getScadenzarate().getId()));
	bean.setScadenzePeriodo(range.getTiporateizzazione().getScadenzePeriodi());
	return bean;
    }
}
