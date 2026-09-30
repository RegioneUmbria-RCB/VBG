package it.gruppoinit.pal.gp.core.features.manifestazioni.rest.ambulanti.model;

import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;

import it.gruppoinit.pal.gp.core.domain.BorsellinoMovimenti;
import it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.abbonamento.movimenti.TipoEnum;
import it.gruppoinit.pal.gp.core.features.nodopagamenti.NodoPagamentiService;
import it.gruppoinit.pal.gp.core.features.nodopagamenti.StatiPosizioniDebitorieConverter;
import it.gruppoinit.pal.gp.core.utils.Utilities;

public class BorsellinoAppMovimenti {

    @XmlElement
    private Integer id;
    @XmlElement
    private String tipo;
    @XmlElement
    private String data;
    @XmlElement(name = "importo_attuale")
    private BigDecimal importoAttuale;
    @XmlElement(name = "importo_contabile")
    private BigDecimal importoContabile;
    @XmlElement
    private String infoAggiutive;
    @XmlElement(name = "posizione_debitoria")
    private PosizioneDebitoriaBorsellinoRest posizioneDebitoria;
    @XmlElement(name = "id_movimento_storno")
    private Integer idMovimentoStorno;
    @XmlElement
    private String comune;

    protected BorsellinoAppMovimenti() {

	super();
    }

    public String getTipo() {

	return tipo;
    }

    public String getData() {

	return data;
    }

    public BigDecimal getImportoAttuale() {

	return importoAttuale;
    }

    public BigDecimal getImportoContabile() {

	return importoContabile;
    }

    public String getInfoAggiutive() {

	return infoAggiutive;
    }

    public PosizioneDebitoriaBorsellinoRest getPosizioneDebitoria() {

	return posizioneDebitoria;
    }

    public Integer getId() {

	return id;
    }

    public Integer getIdMovimentoStorno() {

	return idMovimentoStorno;
    }

    public String getComune() {

	return comune;
    }

    public void setComune(String comune) {

	this.comune = comune;
    }

    @XmlTransient
    public static BorsellinoAppMovimenti fromBorsellinoMovimenti(BorsellinoMovimenti bm, NodoPagamentiService nodoPagamentiService) {

	BorsellinoAppMovimenti ret = new BorsellinoAppMovimenti();
	ret.id = bm.getId().getCodice();
	ret.data = Utilities.formatDateISO8601(bm.getData());
	ret.tipo = bm.getTipo();
	ret.idMovimentoStorno = bm.getMovimentoStornoId();
	ret.posizioneDebitoria = nodoPagamentiService.populatePosizioneDebitoriaBorsellino(bm.getDettPosizioneDebitoriaId());
	ret.importoAttuale = bm.getImporto();
	ret.importoContabile = bm.getImporto(); // lo calcolo nel getInfoAggiuntive se ricarica
	ret.infoAggiutive = getInfoAggiuntive(bm, ret);
	ret.comune = populateComune(bm);
	return ret;
    }

    @XmlTransient
    private static String populateComune(BorsellinoMovimenti bm) {

	if (bm.getTipo().equalsIgnoreCase(TipoEnum.RICARICA.name()) && bm.getDettPosizioneDebitoria() != null
		&& bm.getDettPosizioneDebitoria().getComune() != null) {
	    return bm.getDettPosizioneDebitoria().getComune().getComune();
	}
	if (bm.getTipo().equalsIgnoreCase(TipoEnum.USCITA.name()) && bm.getMercatiD().getMercati().getComune() != null) {
	    return bm.getMercatiD().getMercati().getComune().getComune();
	}
	return null;
    }

    @XmlTransient
    private static String getInfoAggiuntive(BorsellinoMovimenti bm, BorsellinoAppMovimenti bean) {

	String ret = bm.toString();
	if (bm.getTipo().equalsIgnoreCase(TipoEnum.RICARICA.name())) {
	    if (bm.getDettPosizioneDebitoria() != null) {
		boolean isPagata = new StatiPosizioniDebitorieConverter().isStatoChiusoPositivamente(bm.getDettPosizioneDebitoria().getStato());
		bean.importoContabile = isPagata ? bm.getImporto() : BigDecimal.ZERO;
	    } else {
		// se non ho posizione debitoria allora è stata fatta da backoffice e la considero pagata
		bean.importoContabile = bm.getImporto();
	    }
	}
	return ret;
    }
}
