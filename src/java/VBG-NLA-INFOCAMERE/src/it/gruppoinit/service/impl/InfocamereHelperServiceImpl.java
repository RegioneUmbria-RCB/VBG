package it.gruppoinit.service.impl;

import it.gov.impresainungiorno.schema.suap.pratica.RiepilogoPraticaSUAP;
import it.gruppoinit.constants.TipocooperazioneEnum;
import it.gruppoinit.service.InfocamereHelperService;
import it.gruppoinit.utilities.Utilities;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class InfocamereHelperServiceImpl implements InfocamereHelperService {

    private static final String RICHIESTA_INTEGRAZIONE_DOCUMENTALE = "RICHIESTA_INTEGRAZIONE_DOCUMENTALE";
    private static final String RICHIESTA_VALIDAZIONE_ZONA_PRATICA = "RICHIESTA_VALIDAZIONE_ZONA_PRATICA";
    private static final String MOVIMENTO_SISTEMA_ESTERNO = "MOVIMENTO_SISTEMA_ESTERNO";
    private static final Logger log = LoggerFactory.getLogger(InfocamereHelperServiceImpl.class);

    @Override
    public RiepilogoPraticaSUAP getRiepilogoPraticaSUAP(File allegatoSuap, String nomefile) {

	//byte[] b = Utilities.dataHandlerToBytes(allegatoSuap.getEmbeddedFileRef());
	try {
	    byte[] b = Utilities.getBytesFromFile(allegatoSuap);
	    ByteArrayInputStream bis = new ByteArrayInputStream(b);
	    JAXBContext jaxbContext;
	    jaxbContext = JAXBContext.newInstance(RiepilogoPraticaSUAP.class);
	    Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
	    RiepilogoPraticaSUAP riepilogoPraticaSUAP = (RiepilogoPraticaSUAP) unmarshaller.unmarshal(bis);
	    return riepilogoPraticaSUAP;
	} catch (JAXBException e) {
	    log.error("getRiepilogoPraticaSUAP# Errore nella creazione di RiepilogoPraticaSUAP da {}. Errore =  {}   ", nomefile, e);
	    throw new RuntimeException("Errore nella creazione di RiepilogoPraticaSUAP da " + nomefile + ". Errore = " + e);
	} catch (IOException e) {
	    log.error("getRiepilogoPraticaSUAP# Errore nella trasformazione del file in byte [] da {} . Errore =  {}   ", nomefile, e);
	    throw new RuntimeException("Errore nella creazione di RiepilogoPraticaSUAP da " + nomefile + ". Errore = " + e);
	}
    }

    @Override
    public TipocooperazioneEnum decodeTipoCooperazione(String tipocooperazione) {

	TipocooperazioneEnum _enum = TipocooperazioneEnum.fromValue(tipocooperazione);
	log.debug("decodeTipoCooperazione# Decode TipoCooperazione = {}, Decode TipoCooperazione ", tipocooperazione, _enum.name());
	return _enum;
    }

    @Override
    public TipocooperazioneEnum decodeTipoCooperazioneBO(List<it.init.sigepro.rte.types.ParametroType> listparametroType) {

	String valoreOperazione = "";
	for (it.init.sigepro.rte.types.ParametroType parametroType : listparametroType) {
	    if (parametroType.getNome().equals("TIPO_OPERAZIONE")) {
		valoreOperazione = parametroType.getValore().get(0).getCodice();
	    }
	}
	if (valoreOperazione.equals(RICHIESTA_INTEGRAZIONE_DOCUMENTALE)) {
	    return TipocooperazioneEnum.RICHIESTA_INTEGRAZIONE;
	} else if (valoreOperazione.equals(RICHIESTA_VALIDAZIONE_ZONA_PRATICA)) {
	    return TipocooperazioneEnum.RICHIESTA_VALIDAZIONE_ZONA_PRATICA;
	} else if (valoreOperazione.equals(MOVIMENTO_SISTEMA_ESTERNO)) {
		return TipocooperazioneEnum.MOVIMENTO_SISTEMA_ESTERNO;
	}
	//	else if()
	//	{
	//	}
	else {
	    return TipocooperazioneEnum.NON_COFIFICATO;
	}
    }

    @Override
    public String recuperaTipoOperazioneDaAltriDatiNotifica(List<it.init.sigepro.rte.types.ParametroType> listparametroType) {

	String valoreOperazione = "";
	for (it.init.sigepro.rte.types.ParametroType parametroType : listparametroType) {
	    if (parametroType.getNome().equals("TIPO_OPERAZIONE")) {
		valoreOperazione = parametroType.getValore().get(0).getCodice();
		break;
	    }
	}
	return valoreOperazione;
    }

    @Override
    public String recuperaDescrizioneTipoOperazioneDaAltriDatiNotifica(List<it.init.sigepro.rte.types.ParametroType> listparametroType) {

	String valoreOperazione = "";
	for (it.init.sigepro.rte.types.ParametroType parametroType : listparametroType) {
	    if (parametroType.getNome().equals("DESCRIZIONE_OPERAZIONE")) {
		valoreOperazione = parametroType.getValore().get(0).getCodice();
		break;
	    }
	}
	return valoreOperazione;
    }
}
