package it.gruppoinit.pal.gp.core.domain.web;

import it.gruppoinit.pal.gp.core.domain.Documentiistanza;
import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.Istanzeallegati;
import it.gruppoinit.pal.gp.core.domain.Movimentiallegati;
import it.gruppoinit.pal.gp.core.domain.helper.AnagrafedocumentiDTO;
import it.gruppoinit.pal.gp.core.domain.helper.CdsattiDTO;
import it.gruppoinit.pal.gp.core.domain.helper.DocumentiHelper;
import it.gruppoinit.pal.gp.core.domain.helper.DocumentiistanzaDTO;
import it.gruppoinit.pal.gp.core.domain.helper.IstanzeallegatiDTO;
import it.gruppoinit.pal.gp.core.domain.helper.MovimentiallegatiDTO;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.documenti.DocumentiAutorizzazioneDTO;

import java.util.List;

public class DocumentiistanzaCommand extends BaseCommand {

    private Documentiistanza entity;
    private List<Documentiistanza> documentiistanzaList;
    private Istanze istanza;
    private List<Movimentiallegati> movimentiallegatiList;
    private List<Istanzeallegati> istanzeallegatiList;
    private List<Documentiistanza> documentiistanzaDynList;
    private List<DocumentiistanzaDTO> documentiistanzaListDTO;
    private List<DocumentiistanzaDTO> documentiistanzaDynListDTO;
    private List<MovimentiallegatiDTO> movimentiallegatiDTOs;
    private List<IstanzeallegatiDTO> istanzeallegatiDTOs;
    private List<AnagrafedocumentiDTO> anagrafedocumentiDTOs;
    private List<DocumentiAutorizzazioneDTO> autorizzazionedocumentiDTOs;
    private List<CdsattiDTO> cdsattiDTOs;
    private DocumentiHelper documentiHelper;

    public DocumentiistanzaCommand() {

	super();
	this.entity = new Documentiistanza();
    }

    public Documentiistanza getEntity() {

	return entity;
    }

    public void setEntity(Documentiistanza entity) {

	this.entity = entity;
    }

    public List<Documentiistanza> getDocumentiistanzaList() {

	return documentiistanzaList;
    }

    public void setDocumentiistanzaList(List<Documentiistanza> documentiistanzaList) {

	this.documentiistanzaList = documentiistanzaList;
    }

    public Istanze getIstanza() {

	return istanza;
    }

    public void setIstanza(Istanze istanza) {

	this.istanza = istanza;
    }

    public List<Movimentiallegati> getMovimentiallegatiList() {

	return movimentiallegatiList;
    }

    public void setMovimentiallegatiList(List<Movimentiallegati> movimentiallegatiList) {

	this.movimentiallegatiList = movimentiallegatiList;
    }

    public List<Istanzeallegati> getIstanzeallegatiList() {

	return istanzeallegatiList;
    }

    public void setIstanzeallegatiList(List<Istanzeallegati> istanzeallegatiList) {

	this.istanzeallegatiList = istanzeallegatiList;
    }

    public List<Documentiistanza> getDocumentiistanzaDynList() {

	return documentiistanzaDynList;
    }

    public void setDocumentiistanzaDynList(List<Documentiistanza> documentiistanzaDynList) {

	this.documentiistanzaDynList = documentiistanzaDynList;
    }

    public List<DocumentiistanzaDTO> getDocumentiistanzaListDTO() {

	return documentiistanzaListDTO;
    }

    public void setDocumentiistanzaListDTO(List<DocumentiistanzaDTO> documentiistanzaListDTO) {

	this.documentiistanzaListDTO = documentiistanzaListDTO;
    }

    public List<DocumentiistanzaDTO> getDocumentiistanzaDynListDTO() {

	return documentiistanzaDynListDTO;
    }

    public void setDocumentiistanzaDynListDTO(List<DocumentiistanzaDTO> documentiistanzaDynListDTO) {

	this.documentiistanzaDynListDTO = documentiistanzaDynListDTO;
    }

    public List<MovimentiallegatiDTO> getMovimentiallegatiDTOs() {

	return movimentiallegatiDTOs;
    }

    public void setMovimentiallegatiDTOs(List<MovimentiallegatiDTO> movimentiallegatiDTOs) {

	this.movimentiallegatiDTOs = movimentiallegatiDTOs;
    }

    public List<IstanzeallegatiDTO> getIstanzeallegatiDTOs() {

	return istanzeallegatiDTOs;
    }

    public void setIstanzeallegatiDTOs(List<IstanzeallegatiDTO> istanzeallegatiDTOs) {

	this.istanzeallegatiDTOs = istanzeallegatiDTOs;
    }

    public List<AnagrafedocumentiDTO> getAnagrafedocumentiDTOs() {

	return anagrafedocumentiDTOs;
    }

    public void setAnagrafedocumentiDTOs(List<AnagrafedocumentiDTO> anagrafedocumentiDTOs) {

	this.anagrafedocumentiDTOs = anagrafedocumentiDTOs;
    }

    public DocumentiHelper getDocumentiHelper() {

	return documentiHelper;
    }

    public void setDocumentiHelper(DocumentiHelper documentiHelper) {

	this.documentiHelper = documentiHelper;
    }

    public List<DocumentiAutorizzazioneDTO> getAutorizzazionedocumentiDTOs() {

	return autorizzazionedocumentiDTOs;
    }

    public void setAutorizzazionedocumentiDTOs(List<DocumentiAutorizzazioneDTO> autorizzazionedocumentiDTOs) {

	this.autorizzazionedocumentiDTOs = autorizzazionedocumentiDTOs;
    }

    public List<CdsattiDTO> getCdsattiDTOs() {

	return cdsattiDTOs;
    }

    public void setCdsattiDTOs(List<CdsattiDTO> cdsattiDTOs) {

	this.cdsattiDTOs = cdsattiDTOs;
    }
}
