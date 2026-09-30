package it.gruppoinit.pal.gp.core.domain.web;

import it.gruppoinit.pal.gp.core.domain.Anagrafedocumenti;
import it.gruppoinit.pal.gp.core.domain.DocumentiAutorizzazione;
import it.gruppoinit.pal.gp.core.domain.Documentiistanza;
import it.gruppoinit.pal.gp.core.domain.Istanzeallegati;
import it.gruppoinit.pal.gp.core.domain.Istanzeprocure;
import it.gruppoinit.pal.gp.core.domain.Movimentiallegati;
import it.gruppoinit.pal.gp.core.domain.helper.AnagrafedocumentiDTO;
import it.gruppoinit.pal.gp.core.domain.helper.DocumentiistanzaDTO;
import it.gruppoinit.pal.gp.core.domain.helper.IstanzeallegatiDTO;
import it.gruppoinit.pal.gp.core.domain.helper.IstanzeprocureDTO;
import it.gruppoinit.pal.gp.core.domain.helper.MovimentiallegatiDTO;

import java.util.List;

public class DocumentiAutorizzazioneCommand extends BaseCommand {

    private DocumentiAutorizzazione entity;
    private List<DocumentiAutorizzazione> documentiAutorizzaziones;
    private List<Documentiistanza> documentiistanzas;
    private List<Movimentiallegati> movimentiallegatis;
    private List<Istanzeallegati> istanzeallegatis;
    private List<Istanzeprocure> istanzeprocures;
    private List<Anagrafedocumenti> anagrafedocumentis;
    private List<DocumentiistanzaDTO> documentiistanzaListDTO;
    private List<MovimentiallegatiDTO> movimentiallegatiDTOs;
    private List<IstanzeallegatiDTO> istanzeallegatiDTOs;
    private List<IstanzeprocureDTO> istanzeprocureDTOs;
    private List<AnagrafedocumentiDTO> anagrafedocumentiDTOs;

    public DocumentiAutorizzazioneCommand() {

	super();
	this.entity = new DocumentiAutorizzazione();
    }

    public List<DocumentiistanzaDTO> getDocumentiistanzaListDTO() {

	return documentiistanzaListDTO;
    }

    public void setDocumentiistanzaListDTO(List<DocumentiistanzaDTO> documentiistanzaListDTO) {

	this.documentiistanzaListDTO = documentiistanzaListDTO;
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

    public List<IstanzeprocureDTO> getIstanzeprocureDTOs() {

	return istanzeprocureDTOs;
    }

    public void setIstanzeprocureDTOs(List<IstanzeprocureDTO> istanzeprocureDTOs) {

	this.istanzeprocureDTOs = istanzeprocureDTOs;
    }

    public List<AnagrafedocumentiDTO> getAnagrafedocumentiDTOs() {

	return anagrafedocumentiDTOs;
    }

    public void setAnagrafedocumentiDTOs(List<AnagrafedocumentiDTO> anagrafedocumentiDTOs) {

	this.anagrafedocumentiDTOs = anagrafedocumentiDTOs;
    }

    public DocumentiAutorizzazione getEntity() {

	return entity;
    }

    public void setEntity(DocumentiAutorizzazione entity) {

	this.entity = entity;
    }

    public List<DocumentiAutorizzazione> getDocumentiAutorizzaziones() {

	return documentiAutorizzaziones;
    }

    public void setDocumentiAutorizzaziones(List<DocumentiAutorizzazione> documentiAutorizzaziones) {

	this.documentiAutorizzaziones = documentiAutorizzaziones;
    }

    public List<Documentiistanza> getDocumentiistanzas() {

	return documentiistanzas;
    }

    public void setDocumentiistanzas(List<Documentiistanza> documentiistanzas) {

	this.documentiistanzas = documentiistanzas;
    }

    public List<Movimentiallegati> getMovimentiallegatis() {

	return movimentiallegatis;
    }

    public void setMovimentiallegatis(List<Movimentiallegati> movimentiallegatis) {

	this.movimentiallegatis = movimentiallegatis;
    }

    public List<Istanzeallegati> getIstanzeallegatis() {

	return istanzeallegatis;
    }

    public void setIstanzeallegatis(List<Istanzeallegati> istanzeallegatis) {

	this.istanzeallegatis = istanzeallegatis;
    }

    public List<Istanzeprocure> getIstanzeprocures() {

	return istanzeprocures;
    }

    public void setIstanzeprocures(List<Istanzeprocure> istanzeprocures) {

	this.istanzeprocures = istanzeprocures;
    }

    public List<Anagrafedocumenti> getAnagrafedocumentis() {

	return anagrafedocumentis;
    }

    public void setAnagrafedocumentis(List<Anagrafedocumenti> anagrafedocumentis) {

	this.anagrafedocumentis = anagrafedocumentis;
    }
}
