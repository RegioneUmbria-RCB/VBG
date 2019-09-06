using Init.Sigepro.FrontEnd.AppLogic.Common;
using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;
using Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti;
using Init.Sigepro.FrontEnd.AppLogic.GestioneOneri;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneOneri;
using Init.Sigepro.FrontEnd.AppLogic.SalvataggioDomanda;
using Init.Sigepro.FrontEnd.AppLogic.Services.Domanda;
using Init.Sigepro.FrontEnd.Pagamenti;
using Init.Sigepro.FrontEnd.Pagamenti.ENTRANEXT;
using log4net;
using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Text;
using Init.Sigepro.FrontEnd.AppLogic.Utils.SerializationExtensions;
using Init.Sigepro.FrontEnd.Pagamenti.EntraNextService;

namespace Init.Sigepro.FrontEnd.AppLogic.GestionePagamenti.EntraNext
{
    public class PagamentiEntraNextService
    {
        public class PagamentiDatiExtra
        {
            public string IdTransazione { get; set; }
            public string CodicePagamento { get; set; }
            public string Iuv { get; set; }
        }


        public class OnereConPagamentoInSospeso
        {
            public string Causale { get; set; }
            public string NumeroOperazione { get; set; }
            public string Stato { get; set; }
            public string Importo { get; set; }
        }

        private class Constants
        {
            public const string DatiPagamentiExtra = "DatiPagamentiExtra";
        }

        EntraNextPaymentService _entraNextService;
        IAliasSoftwareResolver _aliasSoftwareResolver;
        ISalvataggioDomandaStrategy _salvataggioDomandaStrategy;
        ILog _log = LogManager.GetLogger(typeof(PagamentiEntraNextService));
        //IConfigurazione<ParametriConfigurazionePagamentiMIP> _settings;
        OneriDomandaService _oneriDomandaService;
        AllegatiInterventoService _oggettiService;

        public enum VerificaPagamentoEnum { DIFFERITO, OK, FALLITO }

        public PagamentiEntraNextService(EntraNextPaymentService entraNextService, ISalvataggioDomandaStrategy salvataggioDomandaStrategy, IAliasSoftwareResolver aliasSoftwareResolver, OneriDomandaService oneriDomandaService, AllegatiInterventoService oggettiService)
        {
            this._entraNextService = entraNextService;
            this._aliasSoftwareResolver = aliasSoftwareResolver;
            this._salvataggioDomandaStrategy = salvataggioDomandaStrategy;
            this._oneriDomandaService = oneriDomandaService;
            this._oggettiService = oggettiService;
        }

        public DatiAvvioPagamentiEntraNext InizializzaPagamento(EstremiDomandaEntraNext estremiDomanda)
        {
            var domanda = this._salvataggioDomandaStrategy.GetById(estremiDomanda.IdDomanda);
            var ri = domanda.ReadInterface;
            var numeroOperazione = DateTime.Now.ToString("yyyyMMddhhmmss");
            var oneriPerPagamentoOnline = ri.Oneri.GetOneriProntiPerPagamentoOnline();

            if (oneriPerPagamentoOnline.Count() == 0)
            {
                throw new InvalidOperationException("La domanda non contiene oneri pagabili tramite pagamento online");
            }

            var oneri = oneriPerPagamentoOnline.Select(x => new OneriEntraNextDTO(
                x.Causale.Descrizione,
                x.ImportoPagato,
                1,
                0,
                null,
                null,
                this._oneriDomandaService.GetCodiceCausaleOnereTraslazione(x.Causale.Codice)
                ));
            // var oneri = oneriPerPagamentoOnline.Select(x => new KeyValuePair<string, double>(this._oneriDomandaService.GetCodiceCausaleOnereTraslazione(x.Causale.Codice), x.ImportoPagato));

            //var importo = Convert.ToInt32(oneriPerPagamentoOnline.Sum(x => x.ImportoPagato) * 100.0f);

            var riferimentiDomanda = new RiferimentiDomanda(_aliasSoftwareResolver.AliasComune, _aliasSoftwareResolver.Software, estremiDomanda.IdDomanda, estremiDomanda.StepId);
            var riferimentiUtente = new RiferimentiUtenteEntraNext(estremiDomanda.Email, estremiDomanda.CodiceFiscale, estremiDomanda.CodiceFiscale, estremiDomanda.RagioneSociale, estremiDomanda.Nome, estremiDomanda.Cognome, estremiDomanda.PartitaIva, estremiDomanda.Indirizzo, estremiDomanda.Comune, estremiDomanda.Provincia, estremiDomanda.Cap, estremiDomanda.Localita, estremiDomanda.TipoSoggetto);
            var riferimentiOperazione = new RiferimentiOperazioneEntraNext(numeroOperazione, oneri);

            var request = new IniziaPagamentoEntraNextRequest(riferimentiDomanda, riferimentiUtente, riferimentiOperazione);

            var response = this._entraNextService.IniziaPagamento(request);

            domanda.WriteInterface.DatiExtra.SetValoreDato(Constants.DatiPagamentiExtra, new PagamentiDatiExtra { CodicePagamento = numeroOperazione, IdTransazione = response.IdentificativoTransazione}.ToXmlString());
            this._salvataggioDomandaStrategy.Salva(domanda);

            return new DatiAvvioPagamentiEntraNext(response.Url, numeroOperazione, oneriPerPagamentoOnline);
        }

        public void AvviaPagamento(int idDomanda, string numeroOperazione, IEnumerable<OnereFrontoffice> oneri, bool debugPagamento = true)
        {
            var domanda = this._salvataggioDomandaStrategy.GetById(idDomanda);

            domanda.WriteInterface.Oneri.AvviaPagamentoOneriOnline(numeroOperazione, oneri);

            this._salvataggioDomandaStrategy.Salva(domanda);
        }

        public void SalvaPagamento(int idDomanda, string codicePagamento, string idTransazione)
        {
            try
            {
                if (VerificaPagamento(idDomanda, idTransazione))
                {
                    _log.Info($"Gli oneri della domanda {idDomanda} risultano essere già pagati");
                    return;
                }

                var domanda = this._salvataggioDomandaStrategy.GetById(idDomanda);
                var verifica = this._entraNextService.VerificaPosizione(codicePagamento);

                var responseRicevutaPagamento = this._entraNextService.GetRicevutaPagamento(verifica.PosizioneDebitoria.IUV);
                if (responseRicevutaPagamento.PagamentiPosizioniDebitorie[0].Documento != null)
                {
                    var dataOraTransazione = verifica.PosizioneDebitoria.DataFinePeriodo.GetValueOrDefault(DateTime.Now);

                    var tipoPagamento = this._oneriDomandaService.GetListaModalitaPagamento().Where(x => x.Codice == this._entraNextService.Settings.TipoPagamento.ToString()).FirstOrDefault();
                    _log.Info($"tipopagamento is null? {tipoPagamento == null}");
                    var binaryFileXml = BinaryFile.FromFileData("RicevutaPagamentoOneri.xml", "application/xml", responseRicevutaPagamento.PagamentiPosizioniDebitorie[0].Documento);

                    var rifPraticaEsterna = verifica.PosizioneDebitoria.RiferimentoPraticaEsterna;
                    var iuv = verifica.PosizioneDebitoria.IUV;

                    //inserisce dati extra
                    domanda.WriteInterface.DatiExtra.SetValoreDato(Constants.DatiPagamentiExtra, new PagamentiDatiExtra { CodicePagamento = rifPraticaEsterna, IdTransazione = idTransazione, Iuv = iuv }.ToXmlString());
                    this._salvataggioDomandaStrategy.Salva(domanda);
                    //restituisce dati extra
                    //domanda.ReadInterface.DatiExtra.GetValoreDato("").DeserializeXML<PagamentiDatiExtra>();

                    _log.Info($"Pagamento per la domanda {idDomanda} riuscito, Id pagamento: {iuv}.");
                    domanda.WriteInterface.Oneri.PagamentoRiuscito(dataOraTransazione, rifPraticaEsterna, iuv, idTransazione, tipoPagamento);

                    _log.Info($"Inserimento dell'allegato della ricevuta di pagamento xml come allegato libero, id domanda: {idDomanda}, id transazione: {idTransazione}");
                    this._oggettiService.AggiungiAllegatoLibero(idDomanda, "Ricevuta pagamento oneri", binaryFileXml);
                    _log.Info($"Inserimento dell'allegato della ricevuta di pagamento xml come allegato libero inserito con successo, id domanda: {idDomanda}, id transazione: {idTransazione}");
                    if (responseRicevutaPagamento.PagamentiPosizioniDebitorie[0].DocumentoQuietanza != null)
                    {
                        var binaryFilePdf = BinaryFile.FromFileData("RicevutaPagamentoOneri.pdf", "application/pdf", responseRicevutaPagamento.PagamentiPosizioniDebitorie[0].DocumentoQuietanza);
                        _log.Info($"Inserimento dell'allegato della ricevuta di pagamento pdf come allegato libero, id domanda: {idDomanda}, id transazione: {idTransazione}");
                        this._oggettiService.AggiungiAllegatoLibero(idDomanda, "Ricevuta pagamento oneri", binaryFilePdf);
                        _log.Info($"Inserimento dell'allegato della ricevuta di pagamento pdf come allegato libero inserito con successo, id domanda: {idDomanda}, id transazione: {idTransazione}");
                    }
                    else
                    {
                        _log.Error($"Documento quietanza non presente, id domanda: {idDomanda}, id transazione: {idTransazione}");
                        //throw new Exception("Documento quietanza non presente");
                    }

                    this._salvataggioDomandaStrategy.Salva(domanda);
                }
            }
            catch (Exception ex)
            {
                _log.Error($"Errore generato durante il salvataggio del pagamento, iddomanda: {idDomanda}, codicepagamento: {codicePagamento}, idtransazione: {idTransazione}, errore: {ex.ToString()}");
            }
        }

        public void SalvaPagamentoNotificato(int idDomanda, string codicePagamento, string idTransazione)
        {
            try
            {
                var stato = GetEsitoTransazione(idTransazione);
                if (stato != StatoPagamentoPagoPA.PagamentoAccettato)
                {
                    return;
                }

                SalvaPagamento(idDomanda, codicePagamento, idTransazione);
            }
            catch (Exception ex)
            {
                _log.Info($"Errore nella notifica del Pagamento per la domanda {idDomanda}, Codice pagamento: {codicePagamento}, errore: {ex.ToString()}");
            }
        }

        public StatoPagamentoPagoPA GetEsitoTransazione(string idTransazione)
        {
            var stato = this._entraNextService.GetEsitoTransazione(idTransazione);
            return stato.EsitoTransazione.Stato;
        }

        public bool VerificaPagamento(int idDomanda, string idTransazione)
        {
            try
            {
                var domanda = this._salvataggioDomandaStrategy.GetById(idDomanda);
                if (domanda.ReadInterface.DatiExtra == null)
                {
                    var operazioniInSospeso = domanda.ReadInterface.Oneri.GetOperazioniConPagamentoInSospeso();

                    if (operazioniInSospeso != null)
                    {
                        domanda.WriteInterface.DatiExtra.SetValoreDato(Constants.DatiPagamentiExtra, new PagamentiDatiExtra { CodicePagamento = operazioniInSospeso.ToList()[0].IdOperazionePagamento, IdTransazione = idTransazione }.ToXmlString());
                        this._salvataggioDomandaStrategy.Salva(domanda);
                    }
                }

                return domanda.ReadInterface.Oneri.TotalePagato > 0;
            }
            catch (Exception)
            {
                return false;
            }
        }

        public void AggiornaStatoPagamentiInSospeso(int idDomanda)
        {
            var domanda = this._salvataggioDomandaStrategy.GetById(idDomanda);
            var datiExtra = domanda.ReadInterface.DatiExtra.GetValoreDato(Constants.DatiPagamentiExtra).DeserializeXML<PagamentiDatiExtra>();

            if (datiExtra == null)
            {
                _log.Info($"Non è stato possibile recuperare i dati extra della domanda {idDomanda}");
                return;
            }

            var stato = GetEsitoTransazione(datiExtra.IdTransazione);

            if (stato == StatoPagamentoPagoPA.PagamentoAccettato)
            {
                _log.Info($"Pagamento con iuv {datiExtra.Iuv}, codice {datiExtra.CodicePagamento} in stato: {stato}");
                SalvaPagamento(idDomanda, datiExtra.CodicePagamento, datiExtra.IdTransazione);
            }
            else if (stato == StatoPagamentoPagoPA.PagamentoAnnullato || stato == StatoPagamentoPagoPA.PagamentoRifiutato)
            {
                _log.Info($"Pagamento con iuv {datiExtra.Iuv}, codice {datiExtra.CodicePagamento} risulta in stato: {stato}, l'onere sarà annullato");
                domanda.WriteInterface.Oneri.AnnullaPagamento(datiExtra.CodicePagamento);
                this._salvataggioDomandaStrategy.Salva(domanda);
            }
            else
            {
                _log.Info($"Il pagamento con iuv {datiExtra.Iuv}, codice {datiExtra.CodicePagamento} risulta essere ancora in sospeso, stato: {stato}");
            }
        }

        public IEnumerable<OnereConPagamentoInSospeso> GetPagamentiInSospeso(int idDomanda)
        {
            var domanda = this._salvataggioDomandaStrategy.GetById(idDomanda);
            var operazioni = domanda.ReadInterface.Oneri.GetOperazioniConPagamentoInSospeso();

            var result = new List<OnereConPagamentoInSospeso>();

            var datiExtra = domanda.ReadInterface.DatiExtra.GetValoreDato(Constants.DatiPagamentiExtra).DeserializeXML<PagamentiDatiExtra>();

            foreach (var operazione in operazioni)
            {
                var o = new OnereConPagamentoInSospeso
                {
                    Causale = operazione.Causale.Descrizione,
                    Importo = operazione.ImportoPagato.ToString("N2"),
                    NumeroOperazione = operazione.IdOperazionePagamento,
                    Stato = ""
                };

                try
                {
                    var esito = this.GetEsitoTransazione(datiExtra.IdTransazione);
                    if (esito == StatoPagamentoPagoPA.PagamentoAccettato)
                    {
                        return null;
                    }

                    o.Stato = this.GetEsitoTransazione(datiExtra.IdTransazione).ToString();
                }
                catch (Exception)
                {
                    o.Stato = "Impossibile verificare lo stato del pagamento";
                }

                result.Add(o);
            }

            return result;
        }
    }
}
