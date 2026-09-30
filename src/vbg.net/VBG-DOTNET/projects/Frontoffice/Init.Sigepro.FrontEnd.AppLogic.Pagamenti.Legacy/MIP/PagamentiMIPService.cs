using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;
using Init.Sigepro.FrontEnd.AppLogic.ConversionePDF;
using Init.Sigepro.FrontEnd.AppLogic.GestioneAllegatiDomanda.Intervento;
using Init.Sigepro.FrontEnd.AppLogic.GestioneOneri;
using Init.Sigepro.FrontEnd.AppLogic.GestioneOneri.EditingSession;
using Init.Sigepro.FrontEnd.AppLogic.GestionePagamenti.EntraNext;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneOneri;
using Init.Sigepro.FrontEnd.AppLogic.Pagamenti.Legacy;
using Init.Sigepro.FrontEnd.AppLogic.Pagamenti.Legacy.Configurazione;
using Init.Sigepro.FrontEnd.Infrastructure.Serialization;
using Init.Sigepro.FrontEnd.Infrastructure.Server;
using log4net;
using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using VBG.Pagamenti.Legacy;
using VBG.Pagamenti.Legacy.MIP;
using VBG.Pagamenti.NodoPagamenti.Shared;

namespace Init.Sigepro.FrontEnd.AppLogic.GestionePagamenti.MIP
{
    public static class MIPEsitoPagamentoExtensions
    {
        public static DateTime GetDataPagamento(this MIPEsitoPagamento datiPagamento)
        {
            ILog _log = LogManager.GetLogger(typeof(MIPEsitoPagamentoExtensions));

            if (datiPagamento == null)
            {
                throw new InvalidOperationException("dati pagamento non validi (null)");
            }

            if (!String.IsNullOrEmpty(datiPagamento.DataOraTransazione))
            {
                return DateTime.ParseExact(datiPagamento.DataOraTransazione, "yyyyMMddHHmmss", null);
            }

            if (!String.IsNullOrEmpty(datiPagamento.DataOraOrdine))
            {
                return DateTime.ParseExact(datiPagamento.DataOraOrdine, "yyyyMMddHHmmss", null);
            }

            _log.ErrorFormat("Impossibile ricavare la data e ora della transazione, esito pagamento: {0}", datiPagamento.ToXmlString());

            throw new Exception(String.Format("Impossibile ricavare la data e ora della transazione, riferimeno operazione: {0}", datiPagamento.NumeroOperazione));
        }
    }


    public class PagamentiMIPService
    {
        public class DatiAvvioPagamento
        {
            public readonly string UrlAvvioPagamento;
            public readonly string NumeroOperazione;
            public readonly OnereFrontoffice[] Oneri;

            public DatiAvvioPagamento(string urlAvvioPagamento, string numeroOperazione, IEnumerable<OnereFrontoffice> oneri)
            {
                this.UrlAvvioPagamento = urlAvvioPagamento;
                this.NumeroOperazione = numeroOperazione;
                this.Oneri = oneri.ToArray();
            }
        }



        private readonly MIPPaymentService _mipService;
        private readonly IHtmlToPdfFileConverter _fileConverter;
        private readonly ILog _log = LogManager.GetLogger(typeof(PagamentiMIPService));
        private readonly IConfigurazione<ParametriConfigurazionePagamentiMIP> _settings;
        private readonly AllegatiInterventoService _oggettiService;
        private readonly OneriDomandaService _oneriDomandaService;
        private readonly IPathMapper _pathMapper;
        private readonly OneriDomandaEditingSessionFactory _oneriDomandaEditingSessionFactory;

        public PagamentiMIPService(MIPPaymentService mipService,
                                     IHtmlToPdfFileConverter fileConverter,
                                    IConfigurazione<ParametriConfigurazionePagamentiMIP> settings, AllegatiInterventoService oggettiService,
                                    OneriDomandaService oneriDomandaService, IPathMapper pathMapper, OneriDomandaEditingSessionFactory oneriDomandaEditingSessionFactory)
        {
            this._mipService = mipService;
            this._fileConverter = fileConverter;
            this._settings = settings;
            this._oggettiService = oggettiService;
            this._oneriDomandaService = oneriDomandaService;
            this._pathMapper = pathMapper;
            this._oneriDomandaEditingSessionFactory = oneriDomandaEditingSessionFactory;
        }

        public EsitoPagamentoMip VerificaStatoPagamento(string mipBuffer)
        {
            var datiPagamento = this._mipService.DatiPagamento(mipBuffer, this._settings.Parametri.ClientType);

            return new EsitoPagamentoMip(datiPagamento);
        }

        public EsitoPagamentoMip VerificaStatoPagamento(int idDomanda)
        {
            var domanda = this._oneriDomandaEditingSessionFactory.StartEditingSession(idDomanda);

            var idOperazione = domanda.ReadInterface.GetOneriOnlineConPagamentoAvviato()
                                        .Select(x => x.IdOperazionePagamento)
                                        .Distinct()
                                        .FirstOrDefault();

            var datiPagamento = this._mipService.GetStatoPagamento(idOperazione, this._settings.Parametri.ClientType);

            return new EsitoPagamentoMip(datiPagamento);
        }

        public DatiAvvioPagamento InizializzaPagamento(EstremiDomanda estremiDomanda)
        {
            var domanda = this._oneriDomandaEditingSessionFactory.StartEditingSession(estremiDomanda.IdDomanda);
            var numeroOperazione = Guid.NewGuid().ToString();
            var oneriPerPagamentoOnline = domanda.ReadInterface.GetOneriOnlineProntiPerPagamento();

            if (oneriPerPagamentoOnline.Count() == 0)
            {
                throw new InvalidOperationException("La domanda non contiene oneri pagabili tramite pagamento online");
            }

            var importo = this.GetImporto(oneriPerPagamentoOnline);

            var riferimentiDomanda = new RiferimentiDomanda(this.DataKeyToRiferimentiDomanda(domanda.DataKey), estremiDomanda.StepId);
            var riferimentiUtente = new RiferimentiUtente(estremiDomanda.EmailUtente, estremiDomanda.IdentificativoUtente, estremiDomanda.IdentificativoUtente);
            var riferimentiOperazione = new RiferimentiOperazione(numeroOperazione, importo);

            var request = new IniziaPagamentoRequest(riferimentiDomanda, riferimentiUtente, riferimentiOperazione, this._settings.Parametri.ClientType);

            var url = this._mipService.IniziaPagamento(request);

            return new DatiAvvioPagamento(url, numeroOperazione, oneriPerPagamentoOnline);
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


        public void AvviaPagamento(int idDomanda, string numeroOperazione, IEnumerable<OnereFrontoffice> oneri, bool debugPagamento = true)
        {
            var domanda = this._oneriDomandaEditingSessionFactory.StartEditingSession(idDomanda);

            domanda.LegacyAvviaPagamentoOneriOnline(numeroOperazione, oneri);

            domanda.TerminaSessioneModifica();
        }


        public void AnnullaPagamento(int idDomanda, string mipBuffer)
        {
            try
            {
                var domanda = this._oneriDomandaEditingSessionFactory.StartEditingSession(idDomanda);

                try
                {
                    var datiErrore = this._mipService.GetRagioneAnnullamentoPagamento(mipBuffer, this._settings.Parametri.ClientType);

                    this._log.ErrorFormat("Annullamento del pagamento per la domanda {0}, Id pagamento: {1}. Dati dell'errore: {2}", idDomanda, datiErrore.NumeroOperazione, datiErrore.ToXmlString());
                }
                catch (Exception ex)
                {
                    this._log.Error($"Non è stato possibile reperire informazioni sulla ragione di annulllamento del pagamento con id buffer {mipBuffer}, tutti i pagamenti verranno comunque annullati: {ex}");
                }

                domanda.AnnullaPagamenti();

                domanda.TerminaSessioneModifica();
            }
            catch (Exception ex)
            {
                this._log.ErrorFormat("Errore durante l'annullamento del pagamento per la domanda {0}, id buffer {1}: {2}", idDomanda, mipBuffer, ex.ToString());

                throw;
            }


        }

        public IEnumerable<OnereConPagamentoInSospeso> GetPagamentiInSospeso(int idDomanda)
        {
            var domanda = this._oneriDomandaEditingSessionFactory.StartEditingSession(idDomanda);

            var operazioni = domanda.ReadInterface.GetOneriOnlineConPagamentoAvviato();
            var result = new List<OnereConPagamentoInSospeso>();

            foreach (var operazione in operazioni)
            {
                var o = new OnereConPagamentoInSospeso
                {
                    Causale = operazione.Causale.Descrizione,
                    Importo = operazione.ImportoPagato.ToString("N2"),
                    IdPosizioneNodoPagamenti = operazione.IdOperazionePagamento,
                    StatoNativo = "PagamentoInCorso"
                };

                try
                {
                    var esito = this._mipService.GetStatoPagamento(o.IdPosizioneNodoPagamenti, this._settings.Parametri.ClientType);

                    o.StatoNativo = esito.EsitoD;
                }
                catch (Exception ex)
                {
                    this._log.ErrorFormat(ex.ToString());
                    o.StatoNativo = "Impossibile verificare lo stato del pagamento";
                }

                result.Add(o);
            }

            return result;
        }

        public void AggiornaStatoPagamentiInSospeso(int idDomanda)
        {
            var domanda = this._oneriDomandaEditingSessionFactory.StartEditingSession(idDomanda);
            var operazioni = domanda.ReadInterface.GetOneriOnlineConPagamentoAvviato().Select(x => x.IdOperazionePagamento).Distinct();

            foreach (var o in operazioni)
            {
                try
                {
                    var esito = this._mipService.GetStatoPagamento(o, this._settings.Parametri.ClientType);

                    if (esito.Esito == "OK")
                    {
                        this.PagamentoRiuscito(idDomanda, esito);
                    }

                    if (esito.Esito == "KO")
                    {
                        domanda.AnnullaPagamenti();

                        domanda.TerminaSessioneModifica();
                    }
                }
                catch (Exception ex)
                {
                    this._log.ErrorFormat("Impossibile verificare lo stato del pagamento {0} per la domanda {1}: {2}", o, idDomanda, ex.ToString());
                }
            }

        }

        public void AnnullaPagamentiInSospeso(int idDomanda)
        {
            try
            {
                var domanda = this._oneriDomandaEditingSessionFactory.StartEditingSession(idDomanda);

                domanda.AnnullaPagamenti();

                domanda.TerminaSessioneModifica();
            }
            catch (Exception ex)
            {
                this._log.ErrorFormat("Errore durante l'annullamento del pagamento per la domanda {0}: {1}", idDomanda, ex.ToString());

                throw;
            }
        }

        private int GetImporto(IEnumerable<OnereFrontoffice> oneriPerPagamentoOnline)
        {
            var importo = 0.0m;

            foreach (var o in oneriPerPagamentoOnline)
            {
                importo += o.ImportoPagato;
            }

            return Convert.ToInt32(importo * 100.0m);
        }



        public string PagamentoFallito(int idDomanda, MIPEsitoPagamento datiPagamento)
        {
            try
            {
                var domanda = this._oneriDomandaEditingSessionFactory.StartEditingSession(idDomanda);
                // var datiErrore = this._mipService.GetRagioneAnnullamentoPagamento(mipBuffer);

                this._log.Error($"Pagamento per la domanda {idDomanda} fallito, Id pagamento: {datiPagamento.NumeroOperazione}, id transazione: {datiPagamento.IDTransazione}, numero operazione: {datiPagamento.NumeroOperazione}. Dati esito pagamento: {datiPagamento.ToXmlString()}");

                domanda.AnnullaPagamenti();

                domanda.TerminaSessioneModifica();

                return datiPagamento.EsitoD;
            }
            catch (Exception ex)
            {
                this._log.Error($"Errore durante l'annullamento del pagamento per la domanda {idDomanda}, id ordine {datiPagamento.IDOrdine}, id transazione: {datiPagamento.IDTransazione}, numero operazione: {datiPagamento.NumeroOperazione}: {ex}");

                throw;
            }
        }
        /*
        public void PagamentoRiuscito(int idDomanda, string mipBuffer)
        {
            try
            {
                var datiPagamento = this._mipService.DatiPagamento(mipBuffer);
                
                _log.InfoFormat("Pagamento per la domanda {0} riuscito, Id pagamento: {1}. Dati del pagamento: {2}", idDomanda, datiPagamento.NumeroOperazione, datiPagamento.ToXmlString());

                PagamentoRiuscito(idDomanda, datiPagamento);
            }
            catch (Exception ex)
            {
                _log.ErrorFormat("Errore durante la conferma del pagamento per la domanda {0}, id buffer {1}: {2}", idDomanda, mipBuffer, ex.ToString());

                throw;
            }
        }
        */
        public void PagamentoRiuscito(int idDomanda, MIPEsitoPagamento datiPagamento)
        {
            this._log.ErrorFormat("Pagamento riuscito, id domanda {0}, numero operazione: {1}, DataOraTransazione: {2}", idDomanda, datiPagamento.NumeroOperazione, datiPagamento.DataOraTransazione);

            var domanda = this._oneriDomandaEditingSessionFactory.StartEditingSession(idDomanda);
            var numeroOperazione = datiPagamento.NumeroOperazione;
            var dataOraTransazione = datiPagamento.GetDataPagamento();
            var numeroTransazione = datiPagamento.NumeroOperazione;
            var idOrdine = datiPagamento.IDOrdine;
            var idTransazione = datiPagamento.IDTransazione;
            var tipoPagamento = this.GetTipoPagamentoDefault();

            domanda.LegacyPagamentoRiuscito(dataOraTransazione, numeroOperazione, idOrdine, idTransazione, tipoPagamento);



            // Se possibile genero una ricevuta e la allego alla domanda
            var xml = new RicevutaType(domanda.LegacyFullReadInterface, datiPagamento, this._settings.Parametri.IntestazioneRicevuta).ToXmlString();
            var xsl = this.GetXslRicevutaDiPagamento();

            try
            {
                this._log.InfoFormat("Dati della ricevuta di pagamento: {0}", xml);
                this._log.InfoFormat("Formato della ricevuta di pagamento: {0}", xsl);

                // Attenzione, la conversione potrebbe fallire per qualunque ragione
                var ricevuta = this._fileConverter.TrasformaEConverti("RicevutaPagamentoOneri.pdf", xml, xsl);

                //ricevuta.FileName = "RicevutaPagamentoOneri.pdf";

                this._oggettiService.AggiungiAllegatoLibero(idDomanda, "Ricevuta pagamento oneri", ricevuta);

                this._log.DebugFormat("Ricevuta di pagamento allegata con successo");
            }
            catch (Exception ex)
            {
                this._log.ErrorFormat("Non è stato pssibile allegare la ricevuta di pagamento oneri alla domanda {0}, dettagli dell'errore: {1}", idDomanda, ex.ToString());
            }
            finally
            {
                domanda.TerminaSessioneModifica();
            }
        }

        public TipoPagamento GetTipoPagamentoDefault()
        {
            return this._oneriDomandaService.GetListaTipiPagamento().Where(x => x.Codice == this._settings.Parametri.TipoPagamento.ToString()).FirstOrDefault();
        }

        private string GetXslRicevutaDiPagamento()
        {
            var path = this._pathMapper.MapPath(this._settings.Parametri.XslRicevutaPagamento);

            return File.ReadAllText(path);
        }

        public void AnnullaTuttiIPagamenti(int idDomanda)
        {
            var domanda = this._oneriDomandaEditingSessionFactory.StartEditingSession(idDomanda);

            domanda.AnnullaPagamenti();

            domanda.TerminaSessioneModifica();
        }

        public bool VerticalizzazioneAttiva()
        {
            return this._settings.Parametri.VerticalizzazioneAttiva;
        }
    }
}
