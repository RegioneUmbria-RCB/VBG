package it.gruppoinit.pal.gp.core.features.documenticondivisi.metadati.lettura;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.Movimenti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.features.documenticondivisi.DocumentiCondivisiHelper;
import it.gruppoinit.pal.gp.core.features.documenticondivisi.metadati.DocumentiCondivisiMetadato;
import it.gruppoinit.pal.gp.core.service.DocumentiHelperService;
import it.gruppoinit.pal.gp.core.service.IstanzeService;
import it.gruppoinit.pal.gp.core.service.MovimentiService;

@Service
public class LetturaMetadatiServiceImpl implements ILetturaMetadatiService {

    private IstanzeService istanzeService;
    private MovimentiService movimentiService;
    private DocumentiHelperService documentiHelperService;

    @Autowired
    public void setIstanzeService(IstanzeService istanzeService) {

	this.istanzeService = istanzeService;
    }

    @Autowired
    public void setMovimentiService(MovimentiService movimentiService) {

	this.movimentiService = movimentiService;
    }

    @Autowired
    public void setDocumentiHelperService(DocumentiHelperService documentiHelperService) {

	this.documentiHelperService = documentiHelperService;
    }

    public LetturaMetadatiServiceImpl() {

    }

    @Override
    public List<DocumentiCondivisiMetadato> read(DocumentiCondivisiHelper documento) {

	if (documento == null) {
	    throw new IllegalArgumentException(
		    "Impossibile chiamare LetturaMetadatiServiceImpl(DocumentiCondivisiHelper documento) senza passare un documento valorizzato");
	}
	Istanze istanza = null;
	Movimenti movimento = null;
	if (documento.getCodiceIstanza() != null) {
	    istanza = this.istanzeService.findById(new PkId(documento.getCodiceIstanza()));
	}
	if (documento.getCodiceMovimento() != null) {
	    movimento = this.movimentiService.findById(new PkId(documento.getCodiceMovimento()));
	}
	List<DocumentiCondivisiMetadato> metadati = new ArrayList<DocumentiCondivisiMetadato>();
	Set<IMetadatiReaderCompositoService> complexServices = this.getComplexServices(documento, istanza, movimento);
	for (Iterator<IMetadatiReaderCompositoService> service = complexServices.iterator(); service.hasNext();) {
	    metadati.addAll(service.next().get());
	}
	Set<IMetadatiReader> simpleServices = this.getSimpleServices(documento, istanza, movimento);
	for (Iterator<IMetadatiReader> service = simpleServices.iterator(); service.hasNext();) {
	    metadati.add(service.next().get());
	}
	Collections.sort(metadati);
	return metadati;
    }

    private Set<IMetadatiReaderCompositoService> getComplexServices(DocumentiCondivisiHelper documento, Istanze istanza, Movimenti movimento) {

	Set<IMetadatiReaderCompositoService> complexServices = new HashSet<IMetadatiReaderCompositoService>(0);
	if (istanza != null) {
	    complexServices.add(new DatiLocalizzazionePrimariaReaderService(istanza));
	    complexServices.add(new DatiCatastaliPrimariReaderService(istanza));
	    complexServices.add(new DatiIstanzaReaderService(istanza));
	    complexServices.add(new DatiRichiedenteReaderService(istanza));
	}
	if (movimento != null) {
	    movimento = this.movimentiService.findById(new PkId(documento.getCodiceMovimento()));
	    complexServices.add(new DatiCommisioneEdiliziaReaderService(movimento));
	}
	complexServices.add(new DatiProtocolloReaderService(istanza, movimento));
	return complexServices;
    }

    private Set<IMetadatiReader> getSimpleServices(DocumentiCondivisiHelper documento, Istanze istanza, Movimenti movimento) {

	Set<IMetadatiReader> simpleServices = new HashSet<IMetadatiReader>(0);
	simpleServices.add(new TipoAllegatoReader(this.documentiHelperService, istanza, movimento, documento.getCodiceOggetto()));
	return simpleServices;
    }
}
