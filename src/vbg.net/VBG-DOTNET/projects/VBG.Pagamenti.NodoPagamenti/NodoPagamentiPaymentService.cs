using VBG.Shared.Infrastructure.ServiceModel;
using Microsoft.Extensions.Logging;
using System;
using System.Collections.Generic;
using System.Linq;
using System.ServiceModel;
using VBG.Pagamenti.NodoPagamenti.Annullamento;
using VBG.Pagamenti.NodoPagamenti.Attivazione;
using VBG.Pagamenti.NodoPagamenti.LoggingUtils;
using VBG.Pagamenti.NodoPagamenti.ServiziRest;
using VBG.Pagamenti.NodoPagamenti.Verifica;

namespace VBG.Pagamenti.NodoPagamenti
{
    public class NodoPagamentiPaymentService : INodoPagamentiPaymentService
    {
        private class Constants
        {
            public const string HTTPS = "https";
        }

        private readonly ILogger<NodoPagamentiPaymentService> _log;
        private readonly ILoggerFactory _loggerFactory;
        private readonly INodoPagamentiSettingsReader _settingsReader;
        private readonly IBindingFactory _bindingFactory;

        public InfoConnettore GetInfoConnettore(string codiceComune)
        {
            var settings = this._settingsReader.Read(codiceComune);

            return this.CallClient(settings, client =>
            {
                var feats = client.InfoConnettore(new PayRequestType
                {
                    cfEnteCreditore = settings.CodiceFiscaleEnteCreditore
                });

                return new InfoConnettore(feats);
            });
        }

        public NodoPagamentiPaymentService(ILoggerFactory loggerFactory, INodoPagamentiSettingsReader settingsReader, /*IResolveUrl resolveUrl,*/ IBindingFactory bindingFactory)
        {
            this._loggerFactory = loggerFactory;
            this._settingsReader = settingsReader;
            this._bindingFactory = bindingFactory;
            this._log = loggerFactory.CreateLogger<NodoPagamentiPaymentService>();
        }



        /// <summary>
        /// Attiva una sessione di pagamento on the fly
        /// </summary>
        /// <param name="riferimenti"></param>
        /// <returns></returns>
        public IEsitoAttivazionePagamento AttivaPagamentoOnTheFly(NodoPagamentiSettings settings, string urlRitorno, RichiestaDiPagamento riferimenti)
        {
            try
            {
                return this.CallClient(settings, client =>
                {
                    var cfEnteCreditore = settings.CodiceFiscaleEnteCreditore;
                    var urlRedirectEsito = urlRitorno;

                    var request = riferimenti.ToAttivaPagamentoOnTheFlyRequest(cfEnteCreditore, urlRitorno);

                    this._log.LogDebug($"Attivazione pagamento OTF, dati della richiesta: {request.ToJsonString()}");

                    var serverResponse = client.AttivaPagamentoOnTheFly(request.AttivaPagamentoOnTheFlyType);
                    try
                    {
                        this._log.LogDebug($"Risposta dal sistema di pagamento: {serverResponse.ToJsonString()}");
                    }
                    catch (Exception) { }
                    return new EsitoAttivazionePagamentoOnTheFly(settings.CodiceComune, serverResponse);

                });
            }
            catch (Exception ex)
            {
                this._log.LogError($"Errore durante l'attivazione del pagamento On The Fly {ex.ToString()}");
                throw;
            }
        }

        public DatiOperazioneSuNodoPagamenti GetDettagliPosizione(IEstremiPosizioneDebitoriaClient estremiPosizione)
        {
            var settings = this._settingsReader.Read(estremiPosizione.CodiceComune);
            var infoConnettore = this.GetInfoConnettore(estremiPosizione.CodiceComune);

            var client = new DatiPosizioneDebitoriaRestClient(this._loggerFactory, settings);

            var datiPosizione = client.GetDatiPosizione(settings.CodiceFiscaleEnteCreditore, estremiPosizione.IdPosizioneDebitoria);

            return new DatiOperazioneSuNodoPagamenti
            {
                ConnettoreSupportaRicevuta = infoConnettore.SupportaDownloadRicevuta,
                Causale = datiPosizione.Descrizione,
                CfEnteCreditore = settings.CodiceFiscaleEnteCreditore,
                NominativoSoggettoDebitore = datiPosizione.NominativoSoggettoDebitore,
                CfSoggettoDebitore = datiPosizione.CfSoggettoDebitore,
                CodiceAvviso = datiPosizione.CodiceAvviso,
                IUV = datiPosizione.IUV,
                OTF = datiPosizione.OTF,
                Scadenza = datiPosizione.DataScadenza.ToString("dd/MM/yyyy"),
                UniqueId = estremiPosizione.RiferimentoClient,
                Importo = (datiPosizione.Importi?.FirstOrDefault()?.Importo).GetValueOrDefault(0.0m),
                Stato = datiPosizione.StatoAttuale?.Descrizione,
                StatoNodoPagamenti = datiPosizione.StatoAttuale?.Stato
            };
        }

        /// <summary>
        /// Effettua la verifica dello stato di una serie di posizioni debitorie
        /// </summary>
        /// <param name="richiestaVerifica"></param>
        /// <returns></returns>
        public IEsitoVerificaPosizioni VerificaPosizioni(IEnumerable<IEstremiPosizioneDebitoriaClient> richiestaVerifica)
        {
            try
            {
                // Do per scontato che tutte le posizioni abbiano un codice comune e software omogeneo

                var settings = this._settingsReader.Read(richiestaVerifica.First().CodiceComune);

                return this.CallClient(settings, client =>
                {
                    var request = new VerificaStatoPosizioniType
                    {
                        cfEnteCreditore = settings.CodiceFiscaleEnteCreditore,
                        posizione = richiestaVerifica.Select(x => x.ToRiferimentoPosizioneDebitoriaType()).ToArray()
                    };

                    if (this._log.IsEnabled(LogLevel.Information))
                    {
                        this._log.LogInformation($"Chiamata a VerificaPosizioni: {request.ToJsonString()}");
                    }

                    var response = client.VerificaStatoPosizioni(request);

                    if (this._log.IsEnabled(LogLevel.Information))
                    {
                        this._log.LogInformation($"Risposta da VerificaPosizioni: {response.ToJsonString()}");
                    }

                    return new EsitoVerificaPosizioni(settings.CodiceFiscaleEnteCreditore, response.statoPosizioni.Select(x => new StatoPagamentoOnere(x)).ToArray());
                });
            }
            catch (Exception ex)
            {
                this._log.LogError($"Errore durante la verifica delle posizioni debitorie {ex.ToString()}");
                throw;
            }
        }

        /// <summary>
        /// Inserisce una posizione debitoria e ne effettua l'attivazione sul nodo di pagamento
        /// </summary>
        /// <param name="request"></param>
        /// <returns></returns>
        public IEsitoAttivazionePagamento AttivaPagamentoOffline(NodoPagamentiSettings settings, string urlRitorno, RichiestaDiPagamento request)
        {
            return this.CallClient<IEsitoAttivazionePagamento>(settings, client =>
            {
                var dataScadenza = DateTime.Now.AddDays(settings.GgScadenzaPagoDopo);

                var inserisciPosizioneRequest = request.ToInserisciPosizioniDebitorieType(settings, dataScadenza);

                this.LogInfo($"Chiamata a InserisciPosizioniDebitorie: {inserisciPosizioneRequest.ToJsonString()}");

                var esitoInserimentoPosizioneDebitoria = client.InserisciPosizioniDebitorie(inserisciPosizioneRequest);

                this.LogInfo($"Risposta da InserisciPosizioniDebitorie: {esitoInserimentoPosizioneDebitoria.ToJsonString()}");

                if (esitoInserimentoPosizioneDebitoria.esito != EsitoType.OK)
                {
                    return EsitoAttivazioneSessionePagamento.AttivazioneFallita(esitoInserimentoPosizioneDebitoria.messaggio);
                }

                if (!request.IsPagamentoOtf)
                {
                    return new EsitoInserimentoPosizioneDebitoria(settings.CodiceComune, esitoInserimentoPosizioneDebitoria);
                }

                var riferimentiPosizioneInserita = esitoInserimentoPosizioneDebitoria.posizioniInserite.First();

                var richiestaAttivazionePagamento = new AttivaSessionePagamentoType
                {
                    cfEnteCreditore = settings.CodiceFiscaleEnteCreditore,
                    urlRedirectEsito = urlRitorno,
                    riferimentoPosizione = riferimentiPosizioneInserita
                };

                this.LogInfo($"Chiamata a AttivaSessionePagamento: {richiestaAttivazionePagamento.ToJsonString()}");

                var result = client.AttivaSessionePagamento(richiestaAttivazionePagamento);

                this.LogInfo($"Risposta da AttivaSessionePagamento: {result.ToJsonString()}");

                return new EsitoAttivazioneSessionePagamento(settings.CodiceComune, result, riferimentiPosizioneInserita);
            });

        }



        /// <summary>
        /// Annulla una posizione debitoria precedentemente inserita.
        /// Se non sono presenti posizioni da annullare ritorna al chiamante senza generare errori
        /// </summary>
        /// <param name="posizioniDaAnnullare"></param>
        /// <returns></returns>
        public EsitoAnnullamentoPosizioneDebitoria AnnullaPosizioneDebitoria(IEnumerable<IEstremiPosizioneDebitoriaClient> posizioniDaAnnullare)
        {
            if (!posizioniDaAnnullare.Any())
            {
                return new EsitoAnnullamentoPosizioneDebitoria(true, "Nessuna posizione da annullare");
            }

            try
            {
                var settings = this._settingsReader.Read(posizioniDaAnnullare.First().CodiceComune);

                return this.CallClient(settings, client =>
                {
                    var request = new AnnullaPosizioniDebitorieType
                    {
                        cfEnteCreditore = settings.CodiceFiscaleEnteCreditore,
                        posizioniAnnullate = posizioniDaAnnullare.Select(x => new RiferimentoPosizioneDebitoriaType
                        {
                            IUV = x.IUV,
                            idPosizione = x.IdPosizioneDebitoria,
                            riferimentoClient = new[] { x.RiferimentoClient }
                        }).ToArray()
                    };

                    if (request.posizioniAnnullate.Length == 0)
                    {
                        return new EsitoAnnullamentoPosizioneDebitoria(true, "Nessuna posizione da annullare");
                    }

                    if (this._log.IsEnabled(LogLevel.Information))
                    {
                        this._log.LogInformation($"Chiamata a AnnullaPosizioniDebitorie: {request.ToJsonString()}");
                    }

                    var result = client.AnnullaPosizioniDebitorie(request);

                    if (this._log.IsEnabled(LogLevel.Information))
                    {
                        this._log.LogInformation($"Risposta da AnnullaPosizioniDebitorie: {result.ToJsonString()}");
                    }

                    return new EsitoAnnullamentoPosizioneDebitoria(result.esito == EsitoType.OK, result.messaggio);
                });
            }
            catch (Exception ex)
            {
                this._log.LogError($"Errore durante l'attivazione della Sessione di Pagamento: {ex}");
                throw;
            }
        }


        #region creazione del client per l'invocazione degli web services del nodo di pagamento
        private T CallClient<T>(NodoPagamentiSettings settings, Func<pagamentiServiceClient, T> callback)
        {
            using (var client = this.CreaWebService(settings))
            {
                try
                {
                    return callback(client);
                }
                catch (Exception)
                {
                    client.Abort();
                    throw;
                }
            }
        }

        private pagamentiServiceClient CreaWebService(NodoPagamentiSettings settings)
        {
            try
            {
                var endPointAddress = new EndpointAddress(settings.UrlWs);
                var binding = this._bindingFactory.CreateAndConfigure("defaultServiceBinding");

                if (endPointAddress.Uri.Scheme.ToLower() == Constants.HTTPS)
                {
                    binding.Security.Mode = BasicHttpSecurityMode.Transport;
                }

                return new pagamentiServiceClient(binding, endPointAddress);
            }
            catch (Exception ex)
            {
                throw new Exception(string.Format("ERRORE DURANTE LA CREAZIONE DEL WEB SERVICE pagamentiServiceClient, {0}", ex.Message), ex);
            }
        }

        #endregion

        private void LogInfo(string text)
        {
            if (this._log.IsEnabled(LogLevel.Information))
            {
                this._log.LogInformation(text);
            }
        }

        public AvvisoDiPagamento ScaricaAvvisoPagamento(IEstremiPosizioneDebitoriaClient estremiPosizioneDebitoria)
        {
            var codiceComune = estremiPosizioneDebitoria.CodiceComune;
            var settings = this._settingsReader.Read(codiceComune);

            var res = this.CallClient(settings, ws =>
            {
                var req = new InviaAvvisiPagamentoType
                {
                    cfEnteCreditore = settings.CodiceFiscaleEnteCreditore,
                    riferimentoPosizione = new[] { estremiPosizioneDebitoria.ToRiferimentoPosizioneDebitoriaType() }
                };

                this._log.LogInformation($"Richiesta a InviaAvvisoPagamento: {req.ToJsonString()} ");

                var result = ws.InviaAvvisoPagamento(req);

                this._log.LogInformation($"Risposta da InviaAvvisoPagamento: {result.ToJsonString()} ");

                return result;
            })?
            .Where(x => x.tipoDocumento == TipoDocumentoType.AVVISO)
            .FirstOrDefault();

            if (res == null)
            {
                this._log.LogError($"La chiamata a InviaAvvisoPagamento ha restituito null");
                // TODO: log...
                return null;
            }

            return new AvvisoDiPagamento
            {
                Stato = (AvvisoDiPagamento.StatoAvvisoEnum)Enum.Parse(typeof(AvvisoDiPagamento.StatoAvvisoEnum), res.statoDocumento.ToString()),
                Dati = res.documento,
                Descrizione = res.nomeDocumento,
                NomeFile = res.nomeDocumento,
            };

        }

        public RicevutaTelematica ScaricaRicevutaTelematica(IEstremiPosizioneDebitoriaClient estremiPosizioneDebitoria)
        {
            var codiceComune = estremiPosizioneDebitoria.CodiceComune;
            var settings = this._settingsReader.Read(codiceComune);

            var res = this.CallClient(settings, ws =>
            {
                var req = new ScaricaRicevuteTelematicheType
                {
                    cfEnteCreditore = settings.CodiceFiscaleEnteCreditore,
                    riferimentoPosizione = new[] { estremiPosizioneDebitoria.ToRiferimentoPosizioneDebitoriaType() }
                };

                this._log.LogDebug($"Richiesta a ScaricaRicevutaTelematica: {req.ToJsonString()} ");

                var result = ws.ScaricaRicevutaTelematica(req);

                this._log.LogDebug($"Ricevuta risposta da ScaricaRicevutaTelematica");

                return result;
            })?
            .Where(x => x.tipoDocumento == TipoDocumentoType.RICEVUTA)
            .FirstOrDefault();

            if (res == null)
            {
                this._log.LogError($"La chiamata a ScaricaRicevutaTelematica ha restituito null");
                // TODO: log...
                return null;
            }

            return new RicevutaTelematica
            {
                Stato = (RicevutaTelematica.StatoRicevutaEnum)Enum.Parse(typeof(RicevutaTelematica.StatoRicevutaEnum), res.statoDocumento.ToString()),
                Dati = res.documento,
                Descrizione = res.nomeDocumento
            };

        }

    }
}
