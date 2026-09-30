package it.gruppoinit.pal.gp.core.features.manifestazioni.rest.vigili;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import it.gruppoinit.pal.gp.core.domain.DettPosizioneDebitoria;

@XmlRootElement
public class PosizioneDebitoriaModelEsteso extends PosizioneDebitoriaModel {

    @XmlElement(name = "datiestesi")
    private PosizioneDebitoriaDatiEstesi datiEstesi;

    public PosizioneDebitoriaDatiEstesi getDatiEstesi() {

	return datiEstesi;
    }

    public void setDatiEstesi(PosizioneDebitoriaDatiEstesi datiEstesi) {

	this.datiEstesi = datiEstesi;
    }

    public static PosizioneDebitoriaModelEsteso fromDettPosizioneDebitoria(DettPosizioneDebitoria dettPosizioneDebitoria) {

	PosizioneDebitoriaModel model = PosizioneDebitoriaModel.fromDettPosizioneDebitoria(dettPosizioneDebitoria);
	PosizioneDebitoriaModelEsteso ret = new PosizioneDebitoriaModelEsteso();
	ret.setCodiceAvviso(model.getCodiceAvviso());
	ret.setId(model.getId());
	ret.setImportoIvato(model.getImportoIvato());
	ret.setIuv(model.getIuv());
	ret.setPagata(model.getPagata());
	ret.setQrcode(model.getQrcode());
	ret.setUltimoAggiornamento(model.getUltimoAggiornamento());
	return ret;
    }
}
