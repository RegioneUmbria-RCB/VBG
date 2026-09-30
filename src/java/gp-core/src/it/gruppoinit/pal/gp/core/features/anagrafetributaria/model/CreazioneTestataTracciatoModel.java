package it.gruppoinit.pal.gp.core.features.anagrafetributaria.model;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.multipart.MultipartFile;

public class CreazioneTestataTracciatoModel {

    private String descrizione;
    private MultipartFile fileTracciato;
    private MultipartFile fileEsito;

    public String getDescrizione() {

	return descrizione;
    }

    public void setDescrizione(String descrizione) {

	this.descrizione = descrizione;
    }

    public MultipartFile getFileTracciato() {

	return fileTracciato;
    }

    public void setFileTracciato(MultipartFile fileTracciato) {

	this.fileTracciato = fileTracciato;
    }

    public MultipartFile getFileEsito() {

	return fileEsito;
    }

    public void setFileEsito(MultipartFile fileEsito) {

	this.fileEsito = fileEsito;
    }

    public void valida() throws IllegalArgumentException {

	List<String> errori = new ArrayList<String>();
	if (StringUtils.isBlank(descrizione)) {
	    errori.add("Il campo descrizione è obbligatorio");
	}
	if (fileTracciato == null || fileTracciato.isEmpty()) {
	    errori.add("Il campo file di tracciato è obbligatorio");
	}
	if (fileEsito == null || fileEsito.isEmpty()) {
	    errori.add("Il campo file di Esiti è obbligatorio");
	}
    }
}
