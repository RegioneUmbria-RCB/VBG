package it.gruppoinit.dss.api;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.MediaTypeFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import it.gruppoinit.dss.validation.EsitoFirmaDTO;
import it.gruppoinit.dss.validation.SignatureValidationService;
import it.gruppoinit.dss.validation.ValidationCfResultDTO;
import it.gruppoinit.dss.validation.ValidationResultDTO;

@RestController
@RequestMapping("/checkfirma")
public class FirmaController {
	
	private Logger log = Logger.getLogger(this.getClass().getName());
	
	@Autowired
	private SignatureValidationService signatureValidationService;
	
	
	@PostMapping(value = "/validaFirmatari", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ValidationCfResultDTO validaFirmatari(
            @RequestPart("documento") MultipartFile documento,
            @RequestPart("cf") List<String> codiciFiscali,
            @RequestPart("verificaAllaData") Boolean verificaAllaData) {
				
    	byte[] contenuto;
		try {
			contenuto = documento.getBytes();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
				
        log.fine(() -> "Dimensione: " + contenuto.length);
        log.fine(() -> "Nome file: " + documento.getOriginalFilename());
        log.fine(() -> "MIME: " + documento.getContentType());    	
        log.fine(() -> codiciFiscali + "");
        
        return signatureValidationService.checkCodiciFiscali(contenuto, documento.getOriginalFilename(), codiciFiscali, verificaAllaData);

    }
	
	@PostMapping(value = "/verificaFirma", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public EsitoFirmaDTO verificaFirma(
            @RequestPart("documento") MultipartFile documento,
            @RequestPart("verificaAllaData") Boolean verificaAllaData) {
				
    	byte[] contenuto;
		try {
			contenuto = documento.getBytes();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
				
        log.fine(() -> "Dimensione: " + contenuto.length);
        log.fine(() -> "Nome file: " + documento.getOriginalFilename());
        log.fine(() -> "MIME: " + documento.getContentType());    	
        
        return signatureValidationService.checkValidFirma(contenuto, documento.getOriginalFilename(), verificaAllaData);

    }
	
	@PostMapping(value = "/report", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ValidationResultDTO report(
            @RequestPart("documento") MultipartFile documento,
            @RequestPart("verificaAllaData") Boolean verificaAllaData,
            @RequestPart("estraiFileNonFirmato") Boolean estraiFileNonFirmato) {
				
    	byte[] contenuto;
		try {
			contenuto = documento.getBytes();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
				
        log.fine(() -> "Dimensione: " + contenuto.length);
        log.fine(() -> "Nome file: " + documento.getOriginalFilename());
        log.fine(() -> "MIME: " + documento.getContentType());    	
        
        return signatureValidationService.validate(contenuto, documento.getOriginalFilename(), estraiFileNonFirmato, verificaAllaData);

    }
	
	@PostMapping(value = "/scaricaFileNonFirmato", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<byte[]> scaricaFileNonFirmato(@RequestPart("documento") MultipartFile documento) throws IOException {

		String estensione = FilenameUtils.getExtension(documento.getOriginalFilename());
		if (!"p7m".equalsIgnoreCase(estensione)) {
			throw new RuntimeException("il file non risulta essere un p7m");
		}

		byte[] contenuto;
		try {
			contenuto = documento.getBytes();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		String fileOriginaleName = FilenameUtils.removeExtension(documento.getOriginalFilename());
		MediaType mediaType = MediaTypeFactory
		        .getMediaType(fileOriginaleName)
		        .orElse(MediaType.APPLICATION_OCTET_STREAM);

		log.fine(() -> "Dimensione: " + contenuto.length);
		log.fine(() -> "Nome file: " + documento.getOriginalFilename());
		log.fine(() -> "MIME: " + documento.getContentType());

		byte[] fileoriginalebytes = signatureValidationService.estraiFile(contenuto, documento.getOriginalFilename());
		if (fileoriginalebytes == null || fileoriginalebytes.length == 0) {
			log.warning("File originale estratto vuoto");
		}

		return ResponseEntity.ok().contentType(mediaType)
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileOriginaleName + "\"")
				.contentLength(fileoriginalebytes != null ? fileoriginalebytes.length : 0).body(fileoriginalebytes);
	}
	
}
