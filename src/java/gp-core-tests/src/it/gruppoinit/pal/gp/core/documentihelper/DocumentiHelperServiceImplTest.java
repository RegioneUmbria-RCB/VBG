package it.gruppoinit.pal.gp.core.documentihelper;

import it.gruppoinit.pal.gp.core.domain.Oggetti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.oggetti.OggettiServiceFake;
import it.gruppoinit.pal.gp.core.service.impl.DocumentiHelperServiceImpl;

import org.junit.Assert;
import org.junit.Test;

public class DocumentiHelperServiceImplTest {

    @Test(expected = SecurityException.class)
    public void checkDocumentoPerResponsabile_RilanciaEccezioneSeResponsabileNonHaPermessiSoftware() {

	DocumentiHelperServiceImpl service = new DocumentiHelperServiceImpl();
	ResponsabilisoftwareServiceFake responsabilisoftwareServiceFake = new ResponsabilisoftwareServiceFake();
	responsabilisoftwareServiceFake.checkByResponsabileAndSoftwareResult = false;
	service.setResponsabilisoftwareService(responsabilisoftwareServiceFake);
	service.checkDocumentoPerResponsabile(1, "CO", 1000);
    }

    @Test(expected = SecurityException.class)
    public void checkDocumentoPerResponsabile_RilanciaEccezioneSeResponsabileNonHaAutorizzazioneSoftware() {

	DocumentiHelperServiceImpl service = new DocumentiHelperServiceImpl();
	ResponsabilisoftwareServiceFake responsabilisoftwareServiceFake = new ResponsabilisoftwareServiceFake();
	responsabilisoftwareServiceFake.checkByResponsabileAndSoftwareResult = true;
	SoftwareByCodiceOggettoReaderFake reader = new SoftwareByCodiceOggettoReaderFake();
	reader.findSoftwareByCodiceOggettoResult.add("CO");
	service.setResponsabilisoftwareService(responsabilisoftwareServiceFake);
	service.setSoftwareByCodiceOggettoReaderService(reader);
	service.checkDocumentoPerResponsabile(1, "SS", 1000);
    }

    @Test
    public void checkDocumentoPerResponsabile_RestituisceOggettoConCodiceSeListaContieneCodice() {

	DocumentiHelperServiceImpl service = new DocumentiHelperServiceImpl();
	ResponsabilisoftwareServiceFake responsabilisoftwareService = new ResponsabilisoftwareServiceFake();
	responsabilisoftwareService.checkByResponsabileAndSoftwareResult = true;
	service.setResponsabilisoftwareService(responsabilisoftwareService);
	OggettiServiceFake oggettiService = new OggettiServiceFake();
	oggettiService.findByIdResult = new Oggetti(new PkId(1000));
	service.setOggettiService(oggettiService);
	SoftwareByCodiceOggettoReaderFake reader = new SoftwareByCodiceOggettoReaderFake();
	reader.findSoftwareByCodiceOggettoResult.add("CO");
	service.setSoftwareByCodiceOggettoReaderService(reader);
	//service.setSoftwareByCodiceOggettoReader(reader);
	Oggetti o = service.checkDocumentoPerResponsabile(1, "CO", 1000);
	Assert.assertEquals(o.getId().getCodice(), Integer.valueOf(1000));
    }

    @Test
    public void checkDocumentoPerResponsabile_RestituisceOggettoConCodiceSeListaVuota() {

	DocumentiHelperServiceImpl service = new DocumentiHelperServiceImpl();
	ResponsabilisoftwareServiceFake responsabilisoftwareService = new ResponsabilisoftwareServiceFake();
	responsabilisoftwareService.checkByResponsabileAndSoftwareResult = true;
	service.setResponsabilisoftwareService(responsabilisoftwareService);
	OggettiServiceFake oggettiService = new OggettiServiceFake();
	oggettiService.findByIdResult = new Oggetti(new PkId(1000));
	service.setOggettiService(oggettiService);
	SoftwareByCodiceOggettoReaderFake reader = new SoftwareByCodiceOggettoReaderFake();
	//service.setSoftwareByCodiceOggettoReader(reader);
	service.setSoftwareByCodiceOggettoReaderService(reader);
	Oggetti o = service.checkDocumentoPerResponsabile(1, "CO", 1000);
	Assert.assertEquals(o.getId().getCodice(), Integer.valueOf(1000));
    }
}
