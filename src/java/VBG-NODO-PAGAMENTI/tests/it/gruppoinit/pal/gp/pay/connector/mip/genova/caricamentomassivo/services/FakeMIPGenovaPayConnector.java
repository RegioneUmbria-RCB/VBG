package it.gruppoinit.pal.gp.pay.connector.mip.genova.caricamentomassivo.services;

import java.util.Date;
import java.util.List;
import java.util.Map;

import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.exceptions.ValidazionePosizioniDebitorieException;
import it.gruppoinit.pal.gp.pay.command.GenerazioneFattureCommand;
import it.gruppoinit.pal.gp.pay.command.PosizioniDebitorieCommand;
import it.gruppoinit.pal.gp.pay.command.RichiestaSuListaPosizioniCommand;
import it.gruppoinit.pal.gp.pay.connector.IPayConnector;
import it.gruppoinit.pal.gp.pay.domain.PayConnectorConfig;
import it.gruppoinit.pal.gp.pay.domain.PayConnectorWsEndpoint;
import it.gruppoinit.pal.gp.pay.domain.PayPosizioniDebitorie;
import it.gruppoinit.pal.gp.pay.domain.PaySessioniPagamento;
import it.gruppoinit.pal.gp.pay.exception.PayException;
import it.gruppoinit.pal.gp.pay.parameters.IParameter;
import it.gruppoinit.pal.gp.pay.scheduler.IServizioSchedulato;
import it.gruppoinit.pal.gp.pay.scheduler.ServiziSchedulatiEnum;
import it.gruppoinit.pal.gp.pay.service.helper.IUVHelper;
import it.gruppoinit.pal.gp.pay.ws.schema.AttivaPagamentoOnTheFlyResponseType;
import it.gruppoinit.pal.gp.pay.ws.schema.AttivaSessionePagamentoResponseType;
import it.gruppoinit.pal.gp.pay.ws.schema.ElencoDocumentiEsitoType;
import it.gruppoinit.pal.gp.pay.ws.schema.ElencoPosizioniDebitorieEsitoType;
import it.gruppoinit.pal.gp.pay.ws.schema.ElencoStatoPosizioniType;
import it.gruppoinit.pal.gp.pay.ws.schema.RegistrazioneContabileType;

public class FakeMIPGenovaPayConnector implements IPayConnector {

    @Override
    public String getWsEndpointUrl() {

	// non necessario
	return null;
    }

    @Override
    public void setWsEndpointUrl(String wsEndpointUrl) {

	// non necessario
    }

    @Override
    public String getWsUser() {

	// non necessario
	return null;
    }

    @Override
    public void setWsUser(String wsUser) {

	// non necessario
    }

    @Override
    public String getWsPassword() {

	// non necessario
	return null;
    }

    @Override
    public void setWsPassword(String wsPassword) {

	// non necessario
    }

    @Override
    public Integer getWsTimeout() {

	// non necessario
	return null;
    }

    @Override
    public void setWsTimeout(Integer wsTimeout) {

	// non necessario
    }

    @Override
    public String getIdInstallazione() {

	// non necessario
	return null;
    }

    @Override
    public void setIdInstallazione(String idInstallazione) {

	// non necessario
    }

    @Override
    public PayConnectorWsEndpoint getWsCaricamentoConfig() {

	// non necessario
	return null;
    }

    @Override
    public void setWsCaricamentoConfig(PayConnectorWsEndpoint config) {

	// non necessario
    }

    @Override
    public void setWsAnnullamentoConfig(PayConnectorWsEndpoint config) {

	// non necessario
    }

    @Override
    public PayConnectorWsEndpoint getWsAnnullamentoConfig() {

	// non necessario
	return null;
    }

    @Override
    public void setWsVerificaConfig(PayConnectorWsEndpoint config) {

	// non necessario
    }

    @Override
    public PayConnectorWsEndpoint getWsVerificaConfig() {

	// non necessario
	return null;
    }

    @Override
    public void setWsAttivaSessioneConfig(PayConnectorWsEndpoint config) {

	// non necessario
    }

    @Override
    public PayConnectorWsEndpoint getWsAttivaSessioneConfig() {

	// non necessario
	return null;
    }

    @Override
    public void setWsAvvisoConfig(PayConnectorWsEndpoint config) {

	// non necessario
    }

    @Override
    public PayConnectorWsEndpoint getWsAvvisoConfig() {

	// non necessario
	return null;
    }

    @Override
    public void setWsSecurityConfig(PayConnectorWsEndpoint config) {

	// non necessario
    }

    @Override
    public PayConnectorWsEndpoint getWsSecurityConfig() {

	// non necessario
	return null;
    }

    @Override
    public void setWsNotificaConfig(PayConnectorWsEndpoint config) {

	// non necessario
    }

    @Override
    public PayConnectorWsEndpoint getWsNotificaConfig() {

	// non necessario
	return null;
    }

    @Override
    public void setWsRicevutaConfig(PayConnectorWsEndpoint config) {

	// non necessario
    }

    @Override
    public PayConnectorWsEndpoint getWsRicevutaConfig() {

	// non necessario
	return null;
    }

    @Override
    public void setWsFatturaConfig(PayConnectorWsEndpoint config) {

	// non necessario
    }

    @Override
    public PayConnectorWsEndpoint getWsFatturaConfig() {

	// non necessario
	return null;
    }

    @Override
    public void setWsIuvConfig(PayConnectorWsEndpoint config) {

	// non necessario
    }

    @Override
    public PayConnectorWsEndpoint getWsIuvConfig() {

	// non necessario
	return null;
    }

    @Override
    public void setWsCaricamentoMassivoConfig(PayConnectorWsEndpoint config) {

	// non necessario
    }

    @Override
    public PayConnectorWsEndpoint getWsCaricamentoMassivoConfig() {

	PayConnectorWsEndpoint endpoint = new PayConnectorWsEndpoint();
	endpoint.setDescrizione("");
	endpoint.setEndpointUrl("file:///C:/temp");
	endpoint.setFlagSoloSchedulato(false);
	endpoint.setFlagSpegniScheduler(false);
	return endpoint;
    }

    @Override
    public ElencoPosizioniDebitorieEsitoType registraPosizioniDebitorie(PosizioniDebitorieCommand datiRegistrazioniCommand) throws PayException {

	// non necessario
	return null;
    }

    @Override
    public ElencoPosizioniDebitorieEsitoType annullaPosizioniDebitorie(PosizioniDebitorieCommand datiRegistrazioniCommand, boolean pagatoOffline)
	    throws PayException {

	// non necessario
	return null;
    }

    @Override
    public ElencoStatoPosizioniType verificaStatoPagamenti(RichiestaSuListaPosizioniCommand cmd) throws PayException {

	// non necessario
	return null;
    }

    @Override
    public ElencoStatoPosizioniType rendicontazionePagamenti(RichiestaSuListaPosizioniCommand cmd) throws PayException {

	// non necessario
	return null;
    }

    @Override
    public ElencoDocumentiEsitoType inviaAvvisiPagamento(PosizioniDebitorieCommand cmd) throws PayException {

	// non necessario
	return null;
    }

    @Override
    public ElencoDocumentiEsitoType generaFatture(GenerazioneFattureCommand cmd) throws PayException {

	// non necessario
	return null;
    }

    @Override
    public ElencoDocumentiEsitoType scaricaRicevuteTelematiche(RichiestaSuListaPosizioniCommand cmd) throws PayException {

	// non necessario
	return null;
    }

    @Override
    public boolean supportaPagamentoOnTheFly() {

	// non necessario
	return false;
    }

    @Override
    public boolean supportaVerificaPagamento() {

	// non necessario
	return false;
    }

    @Override
    public boolean supportaRendicontazionePagamenti() {

	// non necessario
	return false;
    }

    @Override
    public boolean supportaAvvisoPagamento() {

	// non necessario
	return false;
    }

    @Override
    public boolean supportaRicevutaTelematica() {

	// non necessario
	return false;
    }

    @Override
    public boolean supportaGenerazioneFattura() {

	// non necessario
	return false;
    }

    @Override
    public boolean isGenerazioneFatturaObbligatoria() {

	// non necessario
	return false;
    }

    @Override
    public AttivaPagamentoOnTheFlyResponseType attivaPagamentoOnTheFly(PosizioniDebitorieCommand cmd) throws PayException {

	// non necessario
	return null;
    }

    @Override
    public AttivaSessionePagamentoResponseType attivaSessionePagamento(PayPosizioniDebitorie payPosizioneDebitoria) throws PayException {

	// non necessario
	return null;
    }

    @Override
    public String generaIdMessaggio() {

	// non necessario
	return null;
    }

    @Override
    public String generaIdPosizioneDebitoria(PayPosizioniDebitorie pos) {

	// non necessario
	return null;
    }

    @Override
    public IUVHelper generaIUV(PayPosizioniDebitorie pos, String identificativoCausalePerCalcolo) {

	// non necessario
	return null;
    }

    @Override
    public PkId parseIdPosizioneDebitoria(String posId) {

	// non necessario
	return null;
    }

    @Override
    public PaySessioniPagamento gestisciEsitoSessione(Map<String, String[]> reqParams) {

	// non necessario
	return null;
    }

    @Override
    public String getConnectorName() {

	// non necessario
	return null;
    }

    @Override
    public void setConnectorName(String conName) {

	// non necessario
    }

    @Override
    public String getConnectorCode() {

	// non necessario
	return null;
    }

    @Override
    public void setConnectorCode(String code) {

	// non necessario
    }

    @Override
    public void validateRichiestaInserimentoPosizioniDebitorie(RegistrazioneContabileType registrazioneContabile)
	    throws ValidazionePosizioniDebitorieException {

	// non necessario
    }

    @Override
    public void refreshWs(PayConnectorConfig cfg) {

	// non necessario
    }

    @Override
    public boolean supportaAttivaSessionePagamento() {

	// non necessario
	return false;
    }

    @Override
    public boolean supportaModificaDataScadenza() {

	// non necessario
	return false;
    }

    @Override
    public boolean supportaModificaDataFineValidita() {

	// non necessario
	return false;
    }

    @Override
    public boolean supportaPagamentoOffLine() {

	// non necessario
	return false;
    }

    @Override
    public void modificaDataScadenzaPosizioneDebitoria(Integer idPosizioneDebitoria, Date nuovaDataScadenza) throws PayException {

	// non necessario
    }

    @Override
    public void modificaDataFineValiditaPosizioneDebitoria(Integer idPosizioneDebitoria, Date nuovaDataFineValidita) throws PayException {

	// non necessario
    }

    @Override
    public boolean supportaRataUnica() {

	// non necessario
	return false;
    }

    @Override
    public boolean supportaMolteCausaliRaggruppate() {

	// non necessario
	return false;
    }

    @Override
    public List<IParameter> getListaParametriRichiesti() {

	// non necessario
	return null;
    }

    @Override
    public String getIUV(PayPosizioniDebitorie payPos, String identificativoCausalePerCalcolo) {

	// non necessario
	return null;
    }

    @Override
    public List<ServiziSchedulatiEnum> getListaServiziSchedulatiSupportati() {

	// non necessario
	return null;
    }

    @Override
    public IServizioSchedulato getServizioSchedulatoPerTipo(ServiziSchedulatiEnum tipoServizio) {

	// non necessario
	return null;
    }

    @Override
    public boolean supportaCaricamentoMassivo() {

	// non necessario
	return false;
    }
}
