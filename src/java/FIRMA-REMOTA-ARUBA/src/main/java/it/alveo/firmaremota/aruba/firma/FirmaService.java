package it.alveo.firmaremota.aruba.firma;

import java.io.FileWriter;
import java.nio.file.Paths;

import org.slf4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;

import it.alveo.firmaremota.aruba.client.ArubaClient;
import it.alveo.firmaremota.aruba.client.ArubaDocumentoBean;
import it.alveo.firmaremota.aruba.client.DocumentoFirmatoBean;
import it.alveo.firmaremota.aruba.client.EsitoEnum;
import it.alveo.firmaremota.aruba.client.FirmaRequest;
import it.alveo.firmaremota.aruba.configurazione.ArubaParams;
import it.alveo.firmaremota.aruba.configurazione.ConfigurazioneBean;
import it.alveo.firmaremota.aruba.exception.GenericException;

public class FirmaService {

    private ArubaParams arubaParams;
    private Logger logger;

    public FirmaService(Logger logger, ArubaParams arubaParams) {

	this.logger = logger;
	this.arubaParams = arubaParams;
    }

    public FirmaCompletataBean firma(String sessionid) {

	var esitoFirma = FirmaCompletataBean.fromOk();
	var processFile = Paths.get(this.arubaParams.getTempPath(), sessionid, this.arubaParams.getProcessFileName()).toFile();
	var mapper = new ObjectMapper();
	ProcessoBean infoProcesso;
	try {
	    infoProcesso = mapper.readValue(processFile, ProcessoBean.class);
	} catch (Exception e) {
	    throw new GenericException(e.getMessage(), e.getCause());
	}
	ConfigurazioneBean cfg = infoProcesso.getConfigurazione();
	cfg.setSessionId(sessionid);
	ArubaClient client = new ArubaClient(this.arubaParams, cfg);
	var request = new FirmaRequest();
	request.setCertId(cfg.getCertId());
	request.setDetached(cfg.getDetached());
	request.setEmailNotifica(cfg.getEmailNotifica());
	request.setFirmaCongiuntaCADES(cfg.getFirmaCongiuntaCades());
	request.setMarcaTemporaleRichiesta(cfg.getMarcaTemporaleRichiesta());
	request.setMotivoFirmaPades(cfg.getMotivoFirmaPades());
	request.setNumeroPaginaFirma(cfg.getNumPaginaFirmaPades());
	request.setPosRettFirmaLeftPades(cfg.getPosRettFirmLeftPades());
	request.setPosRettFirmaRightPades(cfg.getPosRettFirmRightPades());
	request.setProfiloFirmaPades(cfg.getProfiloFirmaPades());
	request.setReturnDER(cfg.getReturnDer());
	request.setTestoFirmaPades(cfg.getTestoFirmaPades());
	request.setTipoFirma(cfg.getTipoFirma());
	request.setInfoProcesso(infoProcesso);
	request.setFilesPath(Paths.get(this.arubaParams.getTempPath(), infoProcesso.getSessionId()));
	logger.debug("Inizio aggiunta file alla request di firma di Aruba");
	infoProcesso.getDocumenti().forEach(doc -> {
	    ArubaDocumentoBean documento = new ArubaDocumentoBean();
	    documento.setId(doc.getGuid());
	    documento.setNomeFile(doc.getNuovoNome());
	    request.getDocumenti().add(documento);
	    logger.debug("Aggiunto documento {}", doc.getGuid());
	});
	logger.debug("Invio request di firma ad Aruba");
	var response = client.firma(request);
	logger.debug("Ricezione response di firma da Aruba con esito {}", response.getEsito().getEsito());
	try {
	    if (EsitoEnum.KO.equals(response.getEsito().getEsito())) {
		logger.error("Errore {}: {}", response.getEsito().getReturnCode(), response.getEsito().getDescription());
		throw new GenericException(response.getEsito().getReturnCode() + ": " + response.getEsito().getDescription());
	    }
	    //1. Aggiorno il file di processo
	    for (DocumentoFirmatoBean documentoFirmato : response.getDocumentiFirmati()) {
		DocumentoBean documento = infoProcesso.getDocumenti().stream().filter(d -> d.getGuid().equalsIgnoreCase(documentoFirmato.getGuid()))
			.findFirst().get();
		documento.setNomeFileFirmato(documentoFirmato.getNomeFile());
		documento.setFirmato(true);
		logger.debug("Aggiorno il file di processo");
		try (var fileWriter = new FileWriter(processFile, false)) {
		    var json = mapper.writeValueAsString(infoProcesso);
		    fileWriter.write(json);
		    fileWriter.flush();
		}
	    }
	    logger.debug("Fine processo di firma");
	    return esitoFirma;
	} catch (Exception e) {
	    throw new GenericException(e);
	}
    }
}
