package it.gruppoinit.stc.schema.helper;

import java.util.GregorianCalendar;
import java.util.List;

import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.commons.lang.BooleanUtils;

import it.gruppoinit.stc.domain.Attivita;
import it.gruppoinit.stc.domain.Pratiche;
import it.gruppoinit.stc.utils.Utilities;
import it.init.sigepro.rte.InserimentoAttivitaNLARequest;
import it.init.sigepro.rte.InserimentoAttivitaNLAResponse;
import it.init.sigepro.rte.InserimentoPraticaNLAResponse;
import it.init.sigepro.rte.InserimentoPraticaRequest;
import it.init.sigepro.rte.NotificaAttivitaRequest;
import it.init.sigepro.rte.NotificaAttivitaResponse;
import it.init.sigepro.rte.RichiestaPraticaNLAResponse;
import it.init.sigepro.rte.types.DettaglioAttivitaType;
import it.init.sigepro.rte.types.DettaglioPraticaType;
import it.init.sigepro.rte.types.ProcedimentoType;
import it.init.sigepro.rte.types.RiferimentiAttivitaType;
import it.init.sigepro.rte.types.RiferimentiPraticaType;

public class SchemaConversionUtils {

    public Attivita getAttivita(NotificaAttivitaRequest request) {

	Attivita attivita = new Attivita();
	DettaglioAttivitaType dettaglioAttivitaType = request.getDatiAttivita();
	XMLGregorianCalendar dataAttivitaXML = dettaglioAttivitaType.getDataAttivita();
	if (dataAttivitaXML != null) {
	    GregorianCalendar dataAttivita = dataAttivitaXML.toGregorianCalendar();
	    attivita.setDataattivita(dataAttivita.getTime());
	}
	XMLGregorianCalendar dataProtGenXML = dettaglioAttivitaType.getDataProtocolloGenerale();
	if (dataProtGenXML != null) {
	    GregorianCalendar dataprotgen = dataProtGenXML.toGregorianCalendar();
	    attivita.setDataprotgen(dataprotgen.getTime());
	}
	ProcedimentoType procedimentoPrincipale = getProcedimentoPrincipale(dettaglioAttivitaType.getProcedimenti());
	if (procedimentoPrincipale != null) {
	    attivita.setIdprocedimento(procedimentoPrincipale.getCodice());
	}
	attivita.setDatasistema(GregorianCalendar.getInstance().getTime());
	attivita.setIdattivita(dettaglioAttivitaType.getIdAttivita());
	attivita.setNumprotgen(dettaglioAttivitaType.getNumeroProtocolloGenerale());
	attivita.setTipoattivita(dettaglioAttivitaType.getTipoAttivita().getDescrizione());
	return attivita;
    }

    public Attivita getAttivita(InserimentoAttivitaNLAResponse response) {

	Attivita attivita = new Attivita();
	RiferimentiAttivitaType riferimentiAttivitaType = response.getDettaglioAttivita();
	XMLGregorianCalendar dataProtGenXML = riferimentiAttivitaType.getDataProtocolloGenerale();
	if (dataProtGenXML != null) {
	    GregorianCalendar dataprotgen = dataProtGenXML.toGregorianCalendar();
	    attivita.setDataprotgen(dataprotgen.getTime());
	}
	attivita.setIdprocedimento(riferimentiAttivitaType.getIdProcedimento());
	attivita.setDatasistema(GregorianCalendar.getInstance().getTime());
	attivita.setIdattivita(riferimentiAttivitaType.getIdAttivita());
	attivita.setNumprotgen(riferimentiAttivitaType.getNumeroProtocolloGenerale());
	return attivita;
    }

    public Attivita getAttivita(InserimentoAttivitaNLAResponse response, Attivita attivita) {

	RiferimentiAttivitaType riferimentiAttivitaType = response.getDettaglioAttivita();
	XMLGregorianCalendar dataProtGenXML = riferimentiAttivitaType.getDataProtocolloGenerale();
	if (dataProtGenXML != null) {
	    GregorianCalendar dataprotgen = dataProtGenXML.toGregorianCalendar();
	    attivita.setDataprotgen(dataprotgen.getTime());
	}
	attivita.setIdprocedimento(riferimentiAttivitaType.getIdProcedimento());
	attivita.setDatasistema(GregorianCalendar.getInstance().getTime());
	attivita.setIdattivita(riferimentiAttivitaType.getIdAttivita());
	attivita.setNumprotgen(riferimentiAttivitaType.getNumeroProtocolloGenerale());
	return attivita;
    }

    public Attivita getNuovaAttivita() {

	Attivita attivita = new Attivita();
	return attivita;
    }

    public Pratiche getPratica(RichiestaPraticaNLAResponse response) {

	Pratiche pratica = new Pratiche();
	DettaglioPraticaType dettaglioPraticaType = response.getDettaglioPratica().getDettaglioPratica();
	XMLGregorianCalendar datapraticaXML = dettaglioPraticaType.getDataPratica();
	if (datapraticaXML != null) {
	    GregorianCalendar datapratica = datapraticaXML.toGregorianCalendar();
	    pratica.setDatapratica(datapratica.getTime());
	}
	XMLGregorianCalendar dataProtGenXML = dettaglioPraticaType.getDataProtocolloGenerale();
	if (dataProtGenXML != null) {
	    GregorianCalendar dataprotgen = dataProtGenXML.toGregorianCalendar();
	    pratica.setDataprotgen(dataprotgen.getTime());
	}
	pratica.setIdpratica(dettaglioPraticaType.getIdPratica());
	pratica.setNumpratica(dettaglioPraticaType.getNumeroPratica());
	pratica.setNumprotgen(dettaglioPraticaType.getNumeroProtocolloGenerale());
	return pratica;
    }

    public Pratiche getPratica(InserimentoPraticaNLAResponse response) {

	Pratiche pratica = new Pratiche();
	RiferimentiPraticaType rifPraticaType = response.getDettaglioPratica();
	XMLGregorianCalendar datapraticaXML = rifPraticaType.getDataPratica();
	if (datapraticaXML != null) {
	    GregorianCalendar datapratica = datapraticaXML.toGregorianCalendar();
	    pratica.setDatapratica(datapratica.getTime());
	}
	XMLGregorianCalendar dataProtGenXML = rifPraticaType.getDataProtocolloGenerale();
	if (dataProtGenXML != null) {
	    GregorianCalendar dataprotgen = dataProtGenXML.toGregorianCalendar();
	    pratica.setDataprotgen(dataprotgen.getTime());
	}
	pratica.setIdpratica(rifPraticaType.getIdPratica());
	pratica.setNumpratica(rifPraticaType.getNumeroPratica());
	pratica.setNumprotgen(rifPraticaType.getNumeroProtocolloGenerale());
	return pratica;
    }

    public Pratiche getPratica(InserimentoPraticaRequest request) {

	Pratiche pratica = new Pratiche();
	DettaglioPraticaType dettPraticaType = request.getDettaglioPratica();
	XMLGregorianCalendar datapraticaXML = dettPraticaType.getDataPratica();
	if (datapraticaXML != null) {
	    GregorianCalendar datapratica = datapraticaXML.toGregorianCalendar();
	    pratica.setDatapratica(datapratica.getTime());
	}
	XMLGregorianCalendar dataProtGenXML = dettPraticaType.getDataProtocolloGenerale();
	if (dataProtGenXML != null) {
	    GregorianCalendar dataprotgen = dataProtGenXML.toGregorianCalendar();
	    pratica.setDataprotgen(dataprotgen.getTime());
	}
	pratica.setIdpratica(dettPraticaType.getIdPratica());
	pratica.setNumpratica(dettPraticaType.getNumeroPratica());
	pratica.setNumprotgen(dettPraticaType.getNumeroProtocolloGenerale());
	return pratica;
    }

    public InserimentoAttivitaNLARequest getInserimentoAttivitaFromNotificaAttivita(NotificaAttivitaRequest request) {

	InserimentoAttivitaNLARequest inserimentoAttivitaNLARequest = new InserimentoAttivitaNLARequest();
	inserimentoAttivitaNLARequest.setToken(request.getToken());
	inserimentoAttivitaNLARequest.setSportelloDestinatario(request.getSportelloDestinatario());
	inserimentoAttivitaNLARequest.setSportelloMittente(request.getSportelloMittente());
	DettaglioAttivitaType dettaglioAttivitaType = request.getDatiAttivita();
	inserimentoAttivitaNLARequest.setDatiAttivita(dettaglioAttivitaType);
	return inserimentoAttivitaNLARequest;
    }

    public NotificaAttivitaResponse getNotificaAttivitaFromAttivita(Attivita attivita, Pratiche pratica) {

	NotificaAttivitaResponse response = new NotificaAttivitaResponse();
	RiferimentiAttivitaType riferimentiAttivitaType = new RiferimentiAttivitaType();
	riferimentiAttivitaType.setIdProcedimento(attivita.getIdprocedimento());
	riferimentiAttivitaType.setIdAttivita(attivita.getIdattivita());
	riferimentiAttivitaType.setIdPratica(pratica.getIdpratica());
	riferimentiAttivitaType.setNumeroProtocolloGenerale(attivita.getNumprotgen());
	if (attivita.getDataprotgen() != null) {
	    GregorianCalendar dataProtGen = new GregorianCalendar();
	    dataProtGen.setTime(attivita.getDataprotgen());
	    riferimentiAttivitaType.setDataProtocolloGenerale(Utilities.getXMLGregorianCalendar(dataProtGen));
	}
	response.setDettaglioattivita(riferimentiAttivitaType);
	return response;
    }

    public ProcedimentoType getProcedimentoPrincipale(List<ProcedimentoType> list) {

	ProcedimentoType pt = null;
	if (list != null) {
	    for (ProcedimentoType procedimentoType : list) {
		if (BooleanUtils.isTrue(procedimentoType.isPrincipale())) {
		    pt = procedimentoType;
		    break;
		}
	    }
	}
	return pt;
    }
}
