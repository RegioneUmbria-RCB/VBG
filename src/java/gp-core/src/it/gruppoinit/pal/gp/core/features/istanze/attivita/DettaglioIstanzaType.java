package it.gruppoinit.pal.gp.core.features.istanze.attivita;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.Istanzestradario;

@XmlRootElement(name = "istanza")
public class DettaglioIstanzaType {

    @XmlElement(name = "numero_istanza")
    private String numeroIstanza;
    @XmlElement(name = "data_istanza")
    private Date dataIstanza;
    @XmlElement(name = "numero_protocollo")
    private String numeroProtocollo;
    @XmlElement(name = "data_protocollo")
    private Date dataProtocollo;
    @XmlElement(name = "stato")
    private String stato;
    @XmlElement(name = "intervento")
    private String intervento;
    @XmlElement(name = "oggetto")
    private String oggetto;
    @XmlElement(name = "note")
    private String note;
    @XmlElement(name = "operatore")
    private String operatore;
    @XmlElement(name = "richiedente")
    private String richiedente;
    @XmlElement(name = "legale")
    private String legale;
    @XmlElement(name = "intermediario")
    private String intermediario;
    @XmlElement(name = "stradario")
    private List<StradarioType> stradario;

    public DettaglioIstanzaType() {

	super();
    }

    public DettaglioIstanzaType(Istanze istanza) {

	super();
	this.numeroIstanza = istanza.getNumeroistanza();
	this.dataIstanza = istanza.getData();
	this.numeroProtocollo = istanza.getNumeroprotocollo();
	this.dataProtocollo = istanza.getDataprotocollo();
	this.stato = istanza.getChiusura().getStato();
	this.intervento = istanza.getAlberoproc().getDescrizioneCompleta();
	this.oggetto = istanza.getLavori();
	this.note = istanza.getLavoriestesa();
	this.operatore = istanza.getResponsabile().getResponsabile();
	this.richiedente = istanza.getTransientRichiedenteQualitaAzienda();
	//	if (istanza.getTipisoggetto() != null) {
	//	    this.richiedente += istanza.getTipisoggetto().getTiposoggetto();
	//	}
	//	if (istanza.getTitolarelegale() != null) {
	//	    this.richiedente += istanza.getTitolarelegale().getDescrizioneRichiedenteBreve();
	//	}
	if (istanza.getProfessionista() != null) {
	    this.intermediario = istanza.getProfessionista().getDescrizioneRichiedenteBreve();
	}
	this.stradario = popolaStradario(istanza);
    }

    private List<StradarioType> popolaStradario(Istanze istanza) {

	List<StradarioType> ret = new ArrayList<StradarioType>();
	Set<Istanzestradario> istanzestradarios = istanza.getIstanzestradarios();
	for (Istanzestradario istanzestradario : istanzestradarios) {
	    StradarioType st = new StradarioType(istanzestradario);
	    ret.add(st);
	}
	return ret;
    }
}
