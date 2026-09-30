package it.gruppoinit.pal.gp.pay.connector.mip.genova.caricamentomassivo.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.datatype.XMLGregorianCalendar;

import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.pay.domain.PayPosizioniDebitorie;
import it.gruppoinit.pal.gp.pay.domain.PayRegistrazioniContabili;
import it.gruppoinit.pal.gp.pay.domain.PaySoggettiDebitori;
import it.gruppoinit.pal.gp.pay.domain.PayStatoPagamenti.StatiPagamento;
import it.gruppoinit.pal.gp.pay.exception.PayException;
import it.gruppoinit.pal.gp.pay.service.PayPosizioniDebitorieService;
import it.gruppoinit.pal.gp.pay.ws.rest.NuoviPagamentiRequest;
import it.gruppoinit.pal.gp.pay.ws.rest.PagamentiAnnullatiRestRequest;
import it.gruppoinit.pal.gp.pay.ws.rest.PagamentiEffettuatiRestRequest;
import it.gruppoinit.pal.gp.pay.ws.rest.PosizioneDebitoriaInfoRestResponse;
import it.gruppoinit.pal.gp.pay.ws.schema.PosizioneDebitoriaListResponseType;
import it.gruppoinit.pal.gp.pay.ws.schema.PosizioneDebitoriaRequestType;
import it.gruppoinit.pal.gp.pay.ws.schema.PosizioneDebitoriaType;
import it.gruppoinit.pal.gp.pay.ws.schema.RegistraIUVRequestType;
import it.gruppoinit.pal.gp.pay.ws.schema.RegistraIUVResponseType;
import it.gruppoinit.pal.gp.pay.ws.schema.RiferimentoPosizioneDebitoriaType;

public class FakePayPosizioniDebitorieService implements PayPosizioniDebitorieService {

    private List<PayPosizioniDebitorie> elenco;
    private PayPosizioniDebitorie entity;

    public void setElenco(List<PayPosizioniDebitorie> elenco) {

	this.elenco = elenco;
    }

    public void setEntity(PayPosizioniDebitorie entity) {

	this.entity = entity;
    }

    public FakePayPosizioniDebitorieService(PayPosizioniDebitorie entity) {

	this.entity = entity;
	this.elenco = new ArrayList<>();
	if (entity != null) {
	    elenco.add(entity);
	}
    }

    @Override
    public void insert(PayPosizioniDebitorie entity) {

	//non necessario
    }

    @Override
    public void update(PayPosizioniDebitorie entity) {

	//non necessario
    }

    @Override
    public void delete(PayPosizioniDebitorie entity) {

	//non necessario
    }

    @Override
    public List<PayPosizioniDebitorie> findAll(Integer firstResult, Integer maxResult) {

	//non necessario
	return null;
    }

    @Override
    public PayPosizioniDebitorie findById(PkId id) {

	return entity;
    }

    @Override
    public PayPosizioniDebitorie bindDomainObject(PayPosizioniDebitorie entity, Class<?> idClass, String idPath) {

	//non necessario
	return null;
    }

    @Override
    public PkId newIdFromSequencetable(PayPosizioniDebitorie entity) {

	//non necessario
	return null;
    }

    @Override
    public PayPosizioniDebitorie findByIdExtended(Integer idPosizioneDebitoria) throws PayException {

	//non necessario
	return null;
    }

    @Override
    public PosizioneDebitoriaListResponseType findByJsonRequestFilter(PosizioneDebitoriaRequestType richiesta, Integer offset, Integer limit) {

	//non necessario
	return null;
    }

    @Override
    public PayPosizioniDebitorie findByIUV(String iuv) {

	//non necessario
	return null;
    }

    @Override
    public PayPosizioniDebitorie findByIdPosizionePSP(String idPSP) {

	//non necessario
	return null;
    }

    @Override
    public List<PayPosizioniDebitorie> findAllByIdPosizionePSP(String idPSP) {

	//non necessario
	return null;
    }

    @Override
    public PayPosizioniDebitorie findByRiferimentoPosizione(RiferimentoPosizioneDebitoriaType posRef) {

	//non necessario
	return null;
    }

    @Override
    public void aggiornaPosizioneDebitoria(PayPosizioniDebitorie updateValues, StatiPagamento newStatus, String descStato) {

	//non necessario
    }

    @Override
    public PayPosizioniDebitorie inserisciDatiPosizioneDebitoria(PosizioneDebitoriaType posDeb, PaySoggettiDebitori datiSoggetto,
	    PayRegistrazioniContabili regCont, boolean otf) throws PayException {

	//non necessario
	return null;
    }

    @Override
    public PayPosizioniDebitorie findByCodiceAvviso(String codiceAvviso) {

	//non necessario
	return null;
    }

    @Override
    public RegistraIUVResponseType registraIUV(RegistraIUVRequestType richiesta) {

	//non necessario
	return null;
    }

    @Override
    public Date dataScadenza(XMLGregorianCalendar dataScadenza) {

	//non necessario
	return null;
    }

    @Override
    public Date dataFineValidita(XMLGregorianCalendar dataFineValidita) {

	//non necessario
	return null;
    }

    @Override
    public List<PayPosizioniDebitorie> findByIdRegistrazioneContabile(Integer idRegistrazioneContabile) {

	return this.elenco;
    }

    @Override
    public PayPosizioniDebitorie findByUuid(String uuid) throws PayException {

	//non necessario
	return null;
    }

    @Override
    public List<PosizioneDebitoriaInfoRestResponse> findNuoviPagamentiDeiConnettori(NuoviPagamentiRequest richiesta) {

	//non necessario
	return null;
    }

    @Override
    public List<PosizioneDebitoriaInfoRestResponse> findPagamentiAnnullatiDeiConnettori(PagamentiAnnullatiRestRequest richiesta) {

	//non necessario
	return null;
    }

    @Override
    public List<PosizioneDebitoriaInfoRestResponse> findPagamentiEffettuatiDeiConnettori(PagamentiEffettuatiRestRequest richiesta) {

	//non necessario
	return null;
    }
}
