package it.gruppoinit.pal.gp.core.service.impl;

import java.io.ByteArrayOutputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;

import it.gruppoinit.pal.gp.core.domain.Alberoproc;
import it.gruppoinit.pal.gp.core.domain.CommissioniedilizieT;
import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.Istanzeprocedimenti;
import it.gruppoinit.pal.gp.core.domain.Mailtipo;
import it.gruppoinit.pal.gp.core.domain.Movimenti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Responsabili;
import it.gruppoinit.pal.gp.core.domain.helper.MovimentiDTO;
import it.gruppoinit.pal.gp.core.domain.helper.MovimentiHelper;
import it.gruppoinit.pal.gp.core.domain.web.ChiaveValoreBean;
import it.gruppoinit.pal.gp.core.exception.FunzioneBusinessRemotaException;
import it.gruppoinit.pal.gp.core.features.movimenti.rest.AggiornaRiferimentiProtocolloMovimentoRequest;
import it.gruppoinit.pal.gp.core.features.protocollazione.logic.exceptions.AggiornamentoProtocolloException;
import it.gruppoinit.pal.gp.core.features.rabbitmq.model.MovimentiRabbitTestoBean;
import it.gruppoinit.pal.gp.core.features.scadenzario.BatchScadenzarioFilter;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.service.MovimentiNoSecurityService;
import it.gruppoinit.pal.gp.core.service.exception.OperazioniAutomaticheException;

public class MovimentiServiceImplAdapter implements MovimentiNoSecurityService {

    @Override
    public List<Movimenti> findMovimentiSimo(Calendar fromDate, Calendar toDate, Alberoproc alberoproc) {

	return null;
    }

    @Override
    public Movimenti findMovimentiByTipoMovimento(Integer codiceistanza, String tipimovimento) {

	return null;
    }

    @Override
    public Mailtipo findProtocolloOggetto(Movimenti movimento) {

	return null;
    }

    @Override
    public String findFascicoloOggetto(Movimenti movimento) {

	return null;
    }

    @Override
    public List<Movimenti> findByFilterTable(FilterTable filterTable) {

	return null;
    }

    @Override
    public List<Movimenti> findContromovimentidaEffettuare(Movimenti movimento) {

	return null;
    }

    @Override
    public List<Movimenti> findContromovimentiEffettuati(Movimenti movimento) {

	return null;
    }

    @Override
    public Movimenti findMovimentoSTCCheHaCreatoIstanza(Istanze istanza) {

	return null;
    }

    @Override
    public Movimenti findMovimentoAvvioIstanza(Istanze istanza) {

	return null;
    }

    @Override
    public Movimenti findMovimentoChiusuraIstanza(Integer codiceIstanza) {

	return null;
    }

    @Override
    public List<Movimenti> findEseguitiByIstanza(Istanze istanza) {

	return null;
    }

    @Override
    public List<Movimenti> findDaEseguireByIstanza(Istanze istanza) {

	return null;
    }

    @Override
    public boolean checkPermessiMovimento(Movimenti entity, Responsabili responsabile, boolean effettuaControllaPerModificaDati) {

	return false;
    }

    @Override
    public MovimentiHelper findCaratteristicheMovimento(Movimenti entity) {

	return null;
    }

    @Override
    public void insert(Movimenti movimenti) {

    }

    @Override
    public void update(Movimenti entity) {

    }

    @Override
    public void delete(Movimenti entity) {

    }

    @Override
    public void insertScadenza(Movimenti entity) {

    }

    @Override
    public void updateScadenza(Movimenti entity) {

    }

    @Override
    public Movimenti findMovimentoTrasmissioneByIstanzeprocedimenti(Istanzeprocedimenti istanzeprocedimenti) {

	return null;
    }

    @Override
    public Movimenti findMovimentoRitornoByIstanzeprocedimenti(Istanzeprocedimenti istanzeprocedimenti) {

	return null;
    }

    @Override
    public List<Movimenti> findMovimentiByIstanzeprocedimenti(Istanzeprocedimenti istanzeprocedimenti, SceltaMovimentiEnum sceltaMovimentiEnum) {

	return null;
    }

    @Override
    public void disabilitaMovimento(Movimenti entity) {

    }

    @Override
    public void flush() {

    }

    @Override
    public void abilitaMovimento(Movimenti entity) {

    }

    @Override
    public List<Movimenti> findDisabilitatiByIstanza(Istanze istanza) {

	return null;
    }

    @Override
    public List<Movimenti> findMovimentiDaAssociareAllaCommissione(Date filtroData, CommissioniedilizieT commissioniedilizieT, Integer firstResult,
	    Integer maxResult) {

	return null;
    }

    @Override
    public List<Movimenti> findMovimentoPerFkIdProtocollo(String fkIdProtocollo) {

	return null;
    }

    @Override
    public boolean isDPR160(Movimenti entity) {

	return false;
    }

    @Override
    public void gestioneAllegatiPerComunicazioniTelematiche(Movimenti entity) {

    }

    @Override
    public boolean checkAllegatoFirmatoPerComunicazioniTelematiche(Movimenti entity) {

	return false;
    }

    @Override
    public boolean isEffettuato(Movimenti entity) {

	return false;
    }

    @Override
    public boolean isMovimentoModificabile(Movimenti entity) {

	return false;
    }

    @Override
    public MovimentoDaNotificare isMovimentoDaNotificareSTC(Integer codiceMovimento) {

	return null;
    }

    @Override
    public void notificaStc(Movimenti entity) {

    }

    @Override
    public void updateAmministrazioniStc(Integer codiceMovimento, Integer codiceAmministrazioneStc) {

    }

    @Override
    public void operazioniAutomatiche(Movimenti entity) throws OperazioniAutomaticheException {

    }

    @Override
    public int countByInventarioprocedimento(Integer codiceProcedimento) {

	return 0;
    }

    @Override
    public List<Movimenti> findByAmministrazioni(Integer codiceAmministrazione, Integer firstResult, Integer maxResult) {

	return null;
    }

    @Override
    public Set<Movimenti> findEseguitiByIstanze(List<Istanze> istanzes) {

	return null;
    }

    @Override
    public int countMovimentiDaLeggere(BatchScadenzarioFilter batchScadenzarioFilter) {

	return 0;
    }

    @Override
    public List<Movimenti> findMovimentiDaLeggere(BatchScadenzarioFilter batchScadenzarioFilter, int startRowPage, int endRowPage) {

	return null;
    }

    @Override
    public List<MovimentiDTO> findMovimentiDTODaLeggere(BatchScadenzarioFilter batchScadenzarioFilter, int startRowPage, int endRowPage) {

	return null;
    }

    @Override
    public int countMovimentiSTCNonNotificati(BatchScadenzarioFilter batchScadenzarioFilter) {

	return 0;
    }

    @Override
    public List<Movimenti> findMovimentiSTCNonNotificati(BatchScadenzarioFilter batchScadenzarioFilter, int startRowPage, int endRowPage) {

	return null;
    }

    @Override
    public List<MovimentiDTO> findMovimentiDTOSTCNonNotificati(BatchScadenzarioFilter batchScadenzarioFilter, Integer firstResult,
	    Integer MaxResults) {

	return null;
    }

    @Override
    public void updateBooleanProperty(Integer codice, String propertyToUpdate, Boolean value) {

    }

    @Override
    public void updateIntegerProperty(Integer codice, String propertyToUpdate, Integer value) {

    }

    @Override
    public int countMovimentiDaAssociareAllaCommissione(Date date, CommissioniedilizieT commissioniedilizieT) {

	return 0;
    }

    @Override
    public List<Movimenti> findByFilterTable(FilterTable ft, Integer valueOf, Integer valueOf2) {

	return null;
    }

    @Override
    public List<Movimenti> findMovimentiIstanzaFattiByTipoMovimento(String codTipomovimento, Integer codiceIstanza) {

	return null;
    }

    @Override
    public List<Movimenti> findMovimentiIstanzaDaFareByTipoMovimento(String codTipomovimento, Integer codiceIstanza) {

	return null;
    }

    @Override
    public List<Movimenti> findMovimentiByIstanzeprocedimentiAndAmministrazione(Istanzeprocedimenti entity, Integer codiceAmministrazione,
	    SceltaMovimentiEnum eseguiti) {

	return null;
    }

    @Override
    public List<Movimenti> findByIstanzaAndExcludeMovimento(Integer codiceIstanza, Integer codiceMovimento, SceltaMovimentiEnum sceltaMovimentiEnum) {

	return null;
    }

    @Override
    public void evict(Movimenti movimento) {

    }

    @Override
    public int countByTipimovimento(String tipomovimento) {

	return 0;
    }

    @Override
    public List<ChiaveValoreBean<String, Integer>> countMovimentiSTCConAnomalie() {

	return null;
    }

    @Override
    public void updateRimuoviNotificaConErrore(Integer codiceMovimento) {

    }

    @Override
    public boolean verificaSeNotificareSubEndo(String tipomovimento, Integer codiceAmministrazioneStc) {

	return false;
    }

    @Override
    public ByteArrayOutputStream downloadDocumentiZipLogico(Integer codiceMovimento) {

	return null;
    }

    @Override
    public boolean validateDownloadZipLogico(Integer codiceMovimento, String uuidIstanza) {

	return false;
    }

    @Override
    public Movimenti findMovimentiByTipoMovimentoAndDataAndAmministrazione(Integer codiceIstanzaDestinazione, String tipomovimento, Date data,
	    Integer codiceAmministrazione) {

	return null;
    }

    @Override
    public Movimenti findDataByTipoMovandcodIstanza(String tipomovimento, Integer codiceIstanza) {

	return null;
    }

    @Override
    public List<Movimenti> findAll(Integer firstResult, Integer maxResult) {

	return null;
    }

    @Override
    public Movimenti findById(PkId id) {

	return null;
    }

    @Override
    public Movimenti bindDomainObject(Movimenti entity, Class<?> idClass, String idPath) {

	return null;
    }

    @Override
    public PkId newIdFromSequencetable(Movimenti entity) {

	return null;
    }

    @Override
    public void eseguiFormuleDelleSchedeDinamiche(Integer codicemovimento) throws FunzioneBusinessRemotaException {

    }

    @Override
    public boolean isMovimentoCDS(Movimenti movimento) {

	return false;
    }

    @Override
    public Date getDataMovimentoDaElaborare(Movimenti entity) {

	return null;
    }

    @Override
    public void updateStatoistanza(Movimenti movimento) {

    }

    @Override
    public void updateRiferimentiProtocolloMovimento(AggiornaRiferimentiProtocolloMovimentoRequest request) throws AggiornamentoProtocolloException {

    }

    @Override
    public void updateRiferimentiProtocolloMovimentoAvvio(AggiornaRiferimentiProtocolloMovimentoRequest request)
	    throws AggiornamentoProtocolloException {

    }

    @Override
    public MovimentiRabbitTestoBean replaceTestoPerMovimentoeTopic(Integer codiceMovimento, String topic) {

	return null;
    }

    @Override
    public Movimenti findMovimentoByUuId(String uuid) {

	return null;
    }
}
