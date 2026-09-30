using Init.Sigepro.FrontEnd.AppLogic.GestioneAllegatiDomanda.Intervento;
using Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti;
using Init.Sigepro.FrontEnd.AppLogic.GestioneOneri;
using Init.Sigepro.FrontEnd.AppLogic.GestioneOneri.EditingSession;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneOneri;
using Init.Sigepro.FrontEnd.AppLogic.Pagamenti.Legacy;
using Init.Sigepro.FrontEnd.AppLogic.Utils.SerializationExtensions;
using log4net;
using System;
using System.Collections.Generic;
using System.Linq;
using VBG.Pagamenti.Legacy.ENTRANEXT;
using VBG.Pagamenti.Legacy.EntraNextService;
using VBG.Pagamenti.NodoPagamenti.Shared;

namespace Init.Sigepro.FrontEnd.AppLogic.GestionePagamenti.EntraNext
{
    internal class RiferimentiDomandaPerPagamenti : IRiferimentiDomandaPerPagamenti
    {
        public string IdComune { get; set; }

        public string Software { get; set; }

        public int IdPresentazione { get; set; }

        public string CodiceUnivocoDomanda { get; set; }
    }

    public class PagamentiEntraNextService
    {
        public class PagamentiDatiExtra
        {
            public string IdTransazione { get; set; }
            public string CodicePagamento { get; set; }
            public string Iuv { get; set; }
        }


        public static class Constants
        {
            public const string DatiPagamentiExtra = "DatiPagamentiExtra";
        }

        private readonly EntraNextPaymentService _entraNextService;
        private readonly ILog _log = LogManager.GetLogger(typeof(PagamentiEntraNextService));
        private readonly OneriDomandaService _oneriDomandaService;
        private readonly AllegatiInterventoService _oggettiService;
        private readonly OneriDomandaEditingSessionFactory _oneriDomandaEditingSessionFactory;

        public enum VerificaPagamentoEnum { DIFFERITO, OK, FALLITO }

        public PagamentiEntraNextService(EntraNextPaymentService entraNextService, OneriDomandaService oneriDomandaService, AllegatiInterventoService oggettiService, OneriDomandaEditingSessionFactory oneriDomandaEditingSessionFactory)
        {
            this._entraNextService = entraNextService;
            this._oneriDomandaService = oneriDomandaService;
            this._oggettiService = oggettiService;
            this._oneriDomandaEditingSessionFactory = oneriDomandaEditingSessionFactory;
        }

        public DatiAvvioPagamentiEntraNext InizializzaPagamento(EstremiDomandaEntraNext estremiDomanda)
        {
            var domanda = this._oneriDomandaEditingSessionFactory.StartEditingSession(estremiDomanda.IdDomanda);
            var numeroOperazione = DateTime.Now.Ticks.ToString();
            var oneriPerPagamentoOnline = domanda.ReadInterface.GetOneriOnlineProntiPerPagamento();

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

            var riferimentiDomanda = new RiferimentiDomandaEntranext(this.DataKeyToRiferimentiDomanda(domanda.DataKey), estremiDomanda.StepId, domanda.DescrizioneIntervento);
            var riferimentiUtente = new RiferimentiUtenteEntraNext(estremiDomanda.Email, estremiDomanda.CodiceFiscale, estremiDomanda.CodiceFiscale, estremiDomanda.RagioneSociale, estremiDomanda.Nome, estremiDomanda.Cognome, estremiDomanda.PartitaIva, estremiDomanda.Indirizzo, estremiDomanda.Comune, estremiDomanda.Provincia, estremiDomanda.Cap, estremiDomanda.Localita, estremiDomanda.TipoSoggetto);
            var riferimentiOperazione = new RiferimentiOperazioneEntraNext(numeroOperazione, oneri);

            var request = new IniziaPagamentoEntraNextRequest(riferimentiDomanda, riferimentiUtente, riferimentiOperazione);

            var response = this._entraNextService.IniziaPagamento(request);

            // domanda.WriteInterface.DatiExtra.SetValoreDato(Constants.DatiPagamentiExtra, new PagamentiDatiExtra { CodicePagamento = numeroOperazione, IdTransazione = response.IdentificativoTransazione }.ToXmlString());
            domanda.LegacySetValoreDatoExtra(Constants.DatiPagamentiExtra, new PagamentiDatiExtra { CodicePagamento = numeroOperazione, IdTransazione = response.IdentificativoTransazione }.ToXmlString());

            domanda.TerminaSessioneModifica();

            return new DatiAvvioPagamentiEntraNext(response.Url, numeroOperazione, oneriPerPagamentoOnline);
        }

        private IRiferimentiDomandaPerPagamenti DataKeyToRiferimentiDomanda(PresentazioneIstanzaDataKey dataKey)
        {
            return new RiferimentiDomandaPerPagamenti
            {
                IdComune = dataKey.IdComune,
                Software = dataKey.Software,
                IdPresentazione = dataKey.IdPresentazione,
                CodiceUnivocoDomanda = dataKey.CodiceUnivocoDomanda
            };
        }

        public void AnnullaPagamento(int idDomanda)
        {
            var domanda = this._oneriDomandaEditingSessionFactory.StartEditingSession(idDomanda);

            domanda.AnnullaPagamenti();

            domanda.TerminaSessioneModifica();
        }

        public void AvviaPagamento(int idDomanda, string numeroOperazione, IEnumerable<OnereFrontoffice> oneri, bool debugPagamento = true)
        {
            var domanda = this._oneriDomandaEditingSessionFactory.StartEditingSession(idDomanda);

            domanda.LegacyAvviaPagamentoOneriOnline(numeroOperazione, oneri);

            domanda.TerminaSessioneModifica();
        }

        private void PagamentoAccettato(int idDomanda, string codicePagamento, string idTransazione)
        {
            try
            {
                if (this.VerificaPagamento(idDomanda, idTransazione))
                {
                    this._log.Info($"Gli oneri della domanda {idDomanda} risultano essere già pagati");
                    return;
                }

                var domanda = this._oneriDomandaEditingSessionFactory.StartEditingSession(idDomanda);
                var verifica = this._entraNextService.VerificaPosizione(codicePagamento);



                if (verifica.Esito == "OK")
                {
                    var dataOraTransazione = verifica.PosizioneDebitoria.DataFinePeriodo.GetValueOrDefault(DateTime.Now);

                    var tipoPagamento = this._oneriDomandaService.GetListaTipiPagamento().Where(x => x.Codice == this._entraNextService.Settings.TipoPagamento).FirstOrDefault();
                    this._log.Info($"tipopagamento is null? {tipoPagamento == null}");

                    var rifPraticaEsterna = verifica.PosizioneDebitoria.RiferimentoPraticaEsterna;
                    var iuv = verifica.PosizioneDebitoria.IUV;

                    //inserisce dati extra
                    domanda.LegacySetValoreDatoExtra(Constants.DatiPagamentiExtra, new PagamentiDatiExtra { CodicePagamento = rifPraticaEsterna, IdTransazione = idTransazione, Iuv = iuv }.ToXmlString());

                    this._log.Info($"Pagamento per la domanda {idDomanda} riuscito, Id pagamento: {iuv}.");
                    // domanda.WriteInterface.Oneri.PagamentoRiuscito(dataOraTransazione, rifPraticaEsterna, iuv, idTransazione, tipoPagamento);
                    domanda.LegacyPagamentoRiuscito(dataOraTransazione, rifPraticaEsterna, iuv, idTransazione, tipoPagamento);

                    domanda.TerminaSessioneModifica();

                    // aggiungo gli eventuali allegati alla domanda
                    var responseRicevutaPagamento = this._entraNextService.GetRicevutaPagamento(verifica.PosizioneDebitoria.IUV);

                    if (responseRicevutaPagamento.PagamentiPosizioniDebitorie?.Length > 0)
                    {
                        // Il pagamento è andato a buon fine ma non esiste una ricevuta. Considero comunque il pagamento come andato a buon fine
                        if (responseRicevutaPagamento.PagamentiPosizioniDebitorie[0].Documento != null)
                        {
                            var binaryFileXml = BinaryFile.FromFileData("RicevutaPagamentoOneri.xml", "application/xml", responseRicevutaPagamento.PagamentiPosizioniDebitorie[0].Documento);
                            this._log.Info($"Inserimento dell'allegato della ricevuta di pagamento xml come allegato libero, id domanda: {idDomanda}, id transazione: {idTransazione}");
                            this._oggettiService.AggiungiAllegatoLibero(idDomanda, "Ricevuta pagamento oneri", binaryFileXml);
                        }
                        else
                        {
                            this._log.Error($"Ricevuta pagamento oneri non presente, id domanda: {idDomanda}, id transazione: {idTransazione}");
                            //throw new Exception("Documento quietanza non presente");
                        }

                        this._log.Info($"Inserimento dell'allegato della ricevuta di pagamento xml come allegato libero inserito con successo, id domanda: {idDomanda}, id transazione: {idTransazione}");
                        if (responseRicevutaPagamento.PagamentiPosizioniDebitorie[0].DocumentoQuietanza != null)
                        {
                            var binaryFilePdf = BinaryFile.FromFileData("RicevutaPagamentoOneri.pdf", "application/pdf", responseRicevutaPagamento.PagamentiPosizioniDebitorie[0].DocumentoQuietanza);
                            this._log.Info($"Inserimento dell'allegato della ricevuta di pagamento pdf come allegato libero, id domanda: {idDomanda}, id transazione: {idTransazione}");
                            this._oggettiService.AggiungiAllegatoLibero(idDomanda, "Ricevuta pagamento oneri", binaryFilePdf);
                            this._log.Info($"Inserimento dell'allegato della ricevuta di pagamento pdf come allegato libero inserito con successo, id domanda: {idDomanda}, id transazione: {idTransazione}");
                        }
                        else
                        {
                            this._log.Error($"Documento quietanza non presente, id domanda: {idDomanda}, id transazione: {idTransazione}");
                            //throw new Exception("Documento quietanza non presente");
                        }
                    }
                }
            }
            catch (Exception ex)
            {
                this._log.Error($"Errore generato durante il salvataggio del pagamento, iddomanda: {idDomanda}, codicepagamento: {codicePagamento}, idtransazione: {idTransazione}, errore: {ex.ToString()}");
            }
        }

        public void SalvaPagamentoNotificato(int idDomanda, string codicePagamento, string idTransazione)
        {
            try
            {
                var stato = this.GetEsitoTransazione(idTransazione);
                if (stato != StatoPagamentoPagoPA.PagamentoAccettato)
                {
                    return;
                }

                this.PagamentoAccettato(idDomanda, codicePagamento, idTransazione);
            }
            catch (Exception ex)
            {
                this._log.Info($"Errore nella notifica del Pagamento per la domanda {idDomanda}, Codice pagamento: {codicePagamento}, errore: {ex.ToString()}");
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
                var domanda = this._oneriDomandaEditingSessionFactory.StartEditingSession(idDomanda);
                var datiExtra = domanda.LegacyGetDatoExtra<PagamentiDatiExtra>(Constants.DatiPagamentiExtra);

                if (datiExtra == null)
                {
                    // ? se non sono presenti dati extra contenenti i riferimenti dell'operazione allora li crea... perché???
                    var operazioniInSospeso = domanda.ReadInterface.GetOneriOnlineConPagamentoAvviato();

                    if (operazioniInSospeso != null && operazioniInSospeso.Any())
                    {
                        domanda.LegacySetValoreDatoExtra(Constants.DatiPagamentiExtra,
                            new PagamentiDatiExtra
                            {
                                CodicePagamento = operazioniInSospeso.ElementAt(0).IdOperazionePagamento,
                                IdTransazione = idTransazione
                            }.ToXmlString());

                        domanda.TerminaSessioneModifica();
                        domanda = this._oneriDomandaEditingSessionFactory.StartEditingSession(idDomanda);
                    }
                }

                this._log.Info($"VerificaPagamento per la domanda={idDomanda} e idTransazione={idTransazione}, TotalePagato={domanda.ReadInterface.TotalePagato}");

                return domanda.ReadInterface.TotalePagato > 0;
            }
            catch (Exception ex)
            {
                this._log.Error($"Errore in VerificaPagamento per idDomanda={idDomanda} e idTransazione={idTransazione}: {ex.ToString()}");
                return false;
            }
        }

        public void AggiornaStatoPagamentiInSospeso(int idDomanda)
        {
            // var domanda = this._salvataggioDomandaStrategy.GetById(idDomanda);
            var domanda = this._oneriDomandaEditingSessionFactory.StartEditingSession(idDomanda);

            //domanda.ReadInterface.DatiExtra.Get<PagamentiDatiExtra>(Constants.DatiPagamentiExtra); 
            var datiExtra = domanda.LegacyGetDatoExtra<PagamentiDatiExtra>(Constants.DatiPagamentiExtra);

            if (datiExtra == null)
            {
                this._log.Info($"Id domanda {idDomanda}: Non è stato possibile recuperare i dati extra della domanda {idDomanda}");
                return;
            }

            var stato = this.GetEsitoTransazione(datiExtra.IdTransazione);

            if (stato == StatoPagamentoPagoPA.PagamentoAccettato)
            {
                this._log.Info($"Id domanda {idDomanda}: pagamento con iuv {datiExtra.Iuv}, codice {datiExtra.CodicePagamento} in stato {stato}");
                this.PagamentoAccettato(idDomanda, datiExtra.CodicePagamento, datiExtra.IdTransazione);

                return;
            }

            if (stato == StatoPagamentoPagoPA.PagamentoAnnullato || stato == StatoPagamentoPagoPA.PagamentoRifiutato)
            {
                this._log.Info($"Id domanda {idDomanda}: Pagamento con iuv {datiExtra.Iuv}, codice {datiExtra.CodicePagamento} risulta in stato: {stato}, l'onere sarà annullato");
                domanda.AnnullaPagamenti();
                domanda.TerminaSessioneModifica();

                return;
            }

            this._log.Info($"Id domanda {idDomanda}: Il pagamento con iuv {datiExtra.Iuv}, codice {datiExtra.CodicePagamento} risulta essere ancora in sospeso, stato {stato}");
        }

        public IEnumerable<OnereConPagamentoInSospeso> GetPagamentiInSospeso(int idDomanda)
        {
            var domanda = this._oneriDomandaEditingSessionFactory.StartEditingSession(idDomanda);
            var operazioni = domanda.ReadInterface.GetOneriOnlineConPagamentoAvviato();

            var result = new List<OnereConPagamentoInSospeso>();

            var datiExtra = domanda.LegacyGetDatoExtra<PagamentiDatiExtra>(Constants.DatiPagamentiExtra);

            foreach (var operazione in operazioni)
            {
                var o = new OnereConPagamentoInSospeso
                {
                    Causale = operazione.Causale.Descrizione,
                    Importo = operazione.ImportoPagato.ToString("N2"),
                    IdPosizioneNodoPagamenti = operazione.IdOperazionePagamento,
                    StatoNativo = ""
                };

                try
                {
                    var esito = this.GetEsitoTransazione(datiExtra.IdTransazione);
                    if (esito == StatoPagamentoPagoPA.PagamentoAccettato)
                    {
                        return null;
                    }

                    o.StatoNativo = esito.ToString();
                }
                catch (Exception ex)
                {
                    this._log.Error($"PagamentiEntraNextService.GetPagamentiInSospeso: Non è stato possibile verificare lo stato" +
                                    $" dell'onere con id transazione={datiExtra.IdTransazione}, id operazione={operazione.IdOperazionePagamento}. Errore: {ex}");

                    o.StatoNativo = "Impossibile verificare lo stato del pagamento";
                }

                result.Add(o);
            }

            return result;
        }
    }
}
