package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.domain.helper.DocumentazioneIstanzaHelper;
import it.gruppoinit.pal.gp.core.domain.helper.DocumentiistanzaDTO;
import it.gruppoinit.pal.gp.core.domain.helper.IstanzeallegatiDTO;
import it.gruppoinit.pal.gp.core.domain.helper.IstanzeprocureDTO;
import it.gruppoinit.pal.gp.core.domain.helper.MovimentiallegatiDTO;
import it.gruppoinit.pal.gp.core.service.DocumentazioneIstanzaHelperService;
import it.gruppoinit.pal.gp.core.service.DocumentiistanzaService;
import it.gruppoinit.pal.gp.core.service.ExportCsvService;
import it.gruppoinit.pal.gp.core.service.IstanzeallegatiService;
import it.gruppoinit.pal.gp.core.service.IstanzeprocureService;
import it.gruppoinit.pal.gp.core.service.MovimentiallegatiService;
import it.gruppoinit.pal.gp.core.service.helper.CvsRecordsHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DocumentazioneIstanzaHelperServiceImpl implements DocumentazioneIstanzaHelperService {

    private static final Logger log = LoggerFactory.getLogger(DocumentazioneIstanzaHelperServiceImpl.class);
    private ExportCsvService exportCsvService;
    private MovimentiallegatiService movimentiallegatiService;
    private IstanzeallegatiService istanzeallegatiService;
    private IstanzeprocureService istanzeprocureService;
    private DocumentiistanzaService documentiistanzaService;

    @Autowired
    public void setExportCsvService(ExportCsvService exportCsvService) {

	this.exportCsvService = exportCsvService;
    }

    @Autowired
    public void setMovimentiallegatiService(MovimentiallegatiService movimentiallegatiService) {

	this.movimentiallegatiService = movimentiallegatiService;
    }

    @Autowired
    public void setIstanzeallegatiService(IstanzeallegatiService istanzeallegatiService) {

	this.istanzeallegatiService = istanzeallegatiService;
    }

    @Autowired
    public void setIstanzeprocureService(IstanzeprocureService istanzeprocureService) {

	this.istanzeprocureService = istanzeprocureService;
    }

    @Autowired
    public void setDocumentiistanzaService(DocumentiistanzaService documentiistanzaService) {

	this.documentiistanzaService = documentiistanzaService;
    }

    @Override
    public byte[] exportCsv(Integer codiceIstanza, String nameFileOutput) {

	log.debug("exportCsv# Export documenti per l'istanza con codice {}", codiceIstanza);
	log.debug("exportCsv# Normalizzo tutti di documenti legati all'istanza");
	List<DocumentazioneIstanzaHelper> documentazioneIstanzaHelpers = normalizzaDocumento(codiceIstanza);
	log.debug("exportCsv# Creo la mappa header-pathproperty");
	Map<String, String> headerAndPath = new HashMap<String, String>();
	headerAndPath.put("Nome file", "nomeFile");
	headerAndPath.put("Descrizione", "descrizione");
	headerAndPath.put("Data", "dataInserimentoString");
	headerAndPath.put("Endoprocedimenti", "endoprocedimento");
	headerAndPath.put("Richiesto", "descRichiesto");
	headerAndPath.put("Presente", "descPresente");
	headerAndPath.put("valido", "descValido");
	log.debug("exportCsv# Genero l'oggetoo CvsRecordsHelper");
	CvsRecordsHelper<DocumentazioneIstanzaHelper> cvsRecordsHelper = new CvsRecordsHelper<DocumentazioneIstanzaHelper>(
		documentazioneIstanzaHelpers, headerAndPath);
	byte[] b;
	try {
	    log.debug("exportCsv#Invoke writeCsvFile..");
	    b = exportCsvService.writeCsvFile(nameFileOutput, cvsRecordsHelper);
	    log.debug("exportCsv#Done writeCsvFile..");
	} catch (Exception e) {
	    log.error("exportCsv#Errore durante l'export: {} ", e);
	    throw new RuntimeException(e);
	}
	return b;
    }

    private List<DocumentazioneIstanzaHelper> normalizzaDocumento(Integer codiceIstanza) {

	List<DocumentazioneIstanzaHelper> documentazioneIstanzaHelpers = new ArrayList<DocumentazioneIstanzaHelper>();
	DocumentazioneIstanzaHelper documentazioneIstanzaHelper = null;
	log.debug("normalizzaDocumento# Documenti istanza....");
	List<DocumentiistanzaDTO> docIst = documentiistanzaService.findDocumentiistanzaDTOByIstanza(codiceIstanza, false);
	for (DocumentiistanzaDTO documentiistanzaDTO : docIst) {
	    documentazioneIstanzaHelper = new DocumentazioneIstanzaHelper();
	    documentazioneIstanzaHelper.setDataInserimento(documentiistanzaDTO.getData());
	    documentazioneIstanzaHelper.setDescrizione(documentiistanzaDTO.getDocumento());
	    // documentazioneIstanzaHelper.setEndoprocedimento(endoprocedimento);
	    documentazioneIstanzaHelper.setNomeFile(StringUtils.defaultIfEmpty(documentiistanzaDTO.getNomeFile(), ""));
	    documentazioneIstanzaHelper.setPresente(BooleanUtils.toBoolean(documentiistanzaDTO.getPresente()));
	    documentazioneIstanzaHelper.setRichiesto(BooleanUtils.toBoolean(documentiistanzaDTO.getNecessario()));
	    if (documentiistanzaDTO.getControllook() != null) {
		documentazioneIstanzaHelper.setValido(documentiistanzaDTO.getControllook());
	    }
	    documentazioneIstanzaHelpers.add(documentazioneIstanzaHelper);
	}
	log.debug("normalizzaDocumento# Documenti schede dinamiche....");
	List<DocumentiistanzaDTO> docSchede = documentiistanzaService.findDocumentiistanzaDTOByIstanza(codiceIstanza, true);
	for (DocumentiistanzaDTO documentiistanzaDTO : docSchede) {
	    documentazioneIstanzaHelper = new DocumentazioneIstanzaHelper();
	    documentazioneIstanzaHelper.setDataInserimento(documentiistanzaDTO.getData());
	    documentazioneIstanzaHelper.setDescrizione(documentiistanzaDTO.getDocumento());
	    // documentazioneIstanzaHelper.setEndoprocedimento(endoprocedimento);
	    documentazioneIstanzaHelper.setNomeFile(StringUtils.defaultIfEmpty(documentiistanzaDTO.getNomeFile(), ""));
	    documentazioneIstanzaHelper.setPresente(BooleanUtils.toBoolean(documentiistanzaDTO.getPresente()));
	    documentazioneIstanzaHelper.setRichiesto(BooleanUtils.toBoolean(documentiistanzaDTO.getNecessario()));
	    if (documentiistanzaDTO.getControllook() != null) {
		documentazioneIstanzaHelper.setValido(documentiistanzaDTO.getControllook());
	    }
	    documentazioneIstanzaHelpers.add(documentazioneIstanzaHelper);
	}
	log.debug("normalizzaDocumento# Documenti endo procedimenti....");
	List<IstanzeallegatiDTO> allIst = istanzeallegatiService.findIstanzeallegatiDTOByIstanza(codiceIstanza);
	for (IstanzeallegatiDTO istanzeallegatiDTO : allIst) {
	    documentazioneIstanzaHelper = new DocumentazioneIstanzaHelper();
	    documentazioneIstanzaHelper.setDataInserimento(istanzeallegatiDTO.getDataEndo());
	    documentazioneIstanzaHelper.setDescrizione(istanzeallegatiDTO.getAllegatoextra());
	    documentazioneIstanzaHelper.setEndoprocedimento(StringUtils.defaultIfEmpty(istanzeallegatiDTO.getProcedimento(), ""));
	    documentazioneIstanzaHelper.setNomeFile(StringUtils.defaultIfEmpty(istanzeallegatiDTO.getNomeFile(), ""));
	    documentazioneIstanzaHelper.setPresente(BooleanUtils.toBoolean(istanzeallegatiDTO.getPresente()));
	    documentazioneIstanzaHelper.setRichiesto(BooleanUtils.toBoolean(istanzeallegatiDTO.getNecessario()));
	    if (istanzeallegatiDTO.getControllook() != null) {
		documentazioneIstanzaHelper.setValido(istanzeallegatiDTO.getControllook());
	    }
	    documentazioneIstanzaHelpers.add(documentazioneIstanzaHelper);
	}
	log.debug("normalizzaDocumento# Documenti movimenti....");
	List<MovimentiallegatiDTO> allMov = movimentiallegatiService.findMovimentiallegatiDTOByIstanza(codiceIstanza);
	for (MovimentiallegatiDTO movimentiallegatiDTO : allMov) {
	    documentazioneIstanzaHelper = new DocumentazioneIstanzaHelper();
	    documentazioneIstanzaHelper.setDataInserimento(movimentiallegatiDTO.getDataregistrazione());
	    documentazioneIstanzaHelper.setDescrizione(movimentiallegatiDTO.getDescrizione());
	    // documentazioneIstanzaHelper.setEndoprocedimento(endoprocedimento);
	    documentazioneIstanzaHelper.setNomeFile(StringUtils.defaultIfEmpty(movimentiallegatiDTO.getNomeFile(), ""));
	    documentazioneIstanzaHelper.setPresente(null);
	    documentazioneIstanzaHelper.setRichiesto(null);
	    if (movimentiallegatiDTO.getControllook() != null) {
		documentazioneIstanzaHelper.setValido(movimentiallegatiDTO.getControllook());
	    }
	    documentazioneIstanzaHelpers.add(documentazioneIstanzaHelper);
	}
	log.debug("normalizzaDocumento# Documenti procure....");
	List<IstanzeprocureDTO> allProc = istanzeprocureService.findIstanzeprocureDTOByIstanza(codiceIstanza);
	for (IstanzeprocureDTO istanzeprocureDTO : allProc) {
	    documentazioneIstanzaHelper = new DocumentazioneIstanzaHelper();
	    documentazioneIstanzaHelper.setDataInserimento(istanzeprocureDTO.getDataDocumento());
	    documentazioneIstanzaHelper.setDescrizione(istanzeprocureDTO.getTipoDocumento());
	    // documentazioneIstanzaHelper.setEndoprocedimento(endoprocedimento);
	    documentazioneIstanzaHelper.setNomeFile(StringUtils.defaultIfEmpty(istanzeprocureDTO.getNomeFile(), ""));
	    documentazioneIstanzaHelper.setPresente(BooleanUtils.toBoolean(istanzeprocureDTO.getPresente()));
	    documentazioneIstanzaHelper.setRichiesto(BooleanUtils.toBoolean(istanzeprocureDTO.getPresente()));
	    if (istanzeprocureDTO.getControllook() != null) {
		documentazioneIstanzaHelper.setValido(istanzeprocureDTO.getControllook());
	    }
	    documentazioneIstanzaHelpers.add(documentazioneIstanzaHelper);
	}
	return documentazioneIstanzaHelpers;
    }
}
