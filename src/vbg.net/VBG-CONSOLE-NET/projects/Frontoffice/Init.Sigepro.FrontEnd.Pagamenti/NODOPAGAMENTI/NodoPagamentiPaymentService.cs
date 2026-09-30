using Init.Sigepro.FrontEnd.Infrastructure.Serialization;
using Init.Sigepro.FrontEnd.Pagamenti.NODOPAGAMENTI.Annullamento;
using Init.Sigepro.FrontEnd.Pagamenti.NODOPAGAMENTI.Attivazione;
using Init.Sigepro.FrontEnd.Pagamenti.NODOPAGAMENTI.ServiziRest;
using Init.Sigepro.FrontEnd.Pagamenti.NODOPAGAMENTI.Verifica;
using log4net;
using System;
using System.Collections.Generic;
using System.Linq;
using System.ServiceModel;

namespace Init.Sigepro.FrontEnd.Pagamenti.NODOPAGAMENTI
{
    public class NodoPagamentiPaymentService : INodoPagamentiPaymentService
    {
        private class Constants
        {
            public const string HTTPS = "https";
        }

        private readonly ILog _log = LogManager.GetLogger(typeof(NodoPagamentiPaymentService));
        private readonly NodoPagamentiSettings _settings;

        /// <summary>
        /// Restituisce true se il sistema di pagamenti in uso supporta il pagamento on the fly
        /// </summary>
        /// <returns></returns>
        public bool SupportaPagamentoOnTheFly => this.CallClient(client =>
        {
            var feats = client.InfoConnettore(new PayRequestType
            {
                cfEnteCreditore = this._settings.CodiceFiscaleEnteCreditore
            });

            return feats.supportaPagamentoOTF;
        });

        public bool SupportaPagoDopo => this.CallClient(client =>
        {
            var feats = client.InfoConnettore(new PayRequestType
            {
                cfEnteCreditore = this._settings.CodiceFiscaleEnteCreditore
            });

            return feats.supportaInvioAvviso;
        });

        public NodoPagamentiPaymentService(INodoPagamentiSettingsReader settings)
        {
            this._settings = settings.Read();
        }



        /// <summary>
        /// Attiva una sessione di pagamento on the fly
        /// </summary>
        /// <param name="riferimenti"></param>
        /// <returns></returns>
        public IEsitoAttivazionePagamento AttivaPagamentoOnTheFly(RichiestaDiPagamento riferimenti)
        {
            try
            {
                return this.CallClient(client =>
                {
                    var request = riferimenti.ToAttivaPagamentoOnTheFlyRequest(this._settings);

                    this._log.Debug($"Attivazione pagamento OTF, dati della richiesta: {request.ToXmlString()}");

                    var serverResponse = client.AttivaPagamentoOnTheFly(request.AttivaPagamentoOnTheFlyType);
                    try
                    {
                        this._log.Debug($"Risposta dal sistema di pagamento: {serverResponse.ToXmlString()}");
                    }
                    catch (Exception) { }
                    return new EsitoAttivazionePagamentoOnTheFly(serverResponse);

                });
            }
            catch (Exception ex)
            {
                this._log.Error($"Errore durante l'attivazione del pagamento On The Fly {ex.ToString()}");
                throw;
            }
        }

        public DatiOperazioneSuNodoPagamenti GetDettagliPosizione(IEstremiPosizioneDebitoria estremiPosizione)
        {
            var client = new DatiPosizioneDebitoriaRestClient(this._settings);

            var datiPosizione = client.GetDatiPosizione(this._settings.CodiceFiscaleEnteCreditore, estremiPosizione.IdPosizioneDebitoria);

            return new DatiOperazioneSuNodoPagamenti
            {
                Causale = datiPosizione.Descrizione,
                CfEnteCreditore = this._settings.CodiceFiscaleEnteCreditore,
                NominativoSoggettoDebitore = datiPosizione.NominativoSoggettoDebitore,
                CfSoggettoDebitore = datiPosizione.CfSoggettoDebitore,
                CodiceAvviso = datiPosizione.CodiceAvviso,
                IUV = datiPosizione.IUV,
                OTF = datiPosizione.OTF,
                Scadenza = datiPosizione.DataScadenza.ToString("dd/MM/yyyy"),
                UniqueId = estremiPosizione.UniqueId,
                Importo = (datiPosizione.Importi?.FirstOrDefault()?.Importo).GetValueOrDefault(0.0m),
                Stato = datiPosizione.StatoAttuale?.Descrizione
            };
        }

        /// <summary>
        /// Effettua la verifica dello stato di una serie di posizioni debitorie
        /// </summary>
        /// <param name="richiestaVerifica"></param>
        /// <returns></returns>
        public IEsitoVerificaPosizioni VerificaPosizioni(IEnumerable<IEstremiPosizioneDebitoria> richiestaVerifica)
        {
            try
            {
                return this.CallClient(client =>
                {
                    var request = new VerificaStatoPosizioniType
                    {
                        cfEnteCreditore = this._settings.CodiceFiscaleEnteCreditore,
                        posizione = richiestaVerifica.Select(x => x.ToRiferimentoPosizioneDebitoriaType()).ToArray()
                    };

                    if (this._log.IsInfoEnabled)
                    {
                        this._log.Info($"Chiamata a VerificaPosizioni: {request.ToXmlString()}");
                    }

                    var response = client.VerificaStatoPosizioni(request);

                    if (this._log.IsInfoEnabled)
                    {
                        this._log.Info($"Risposta da VerificaPosizioni: {response.ToXmlString()}");
                    }

                    return new EsitoVerificaPosizioni(this._settings.CodiceFiscaleEnteCreditore, response.statoPosizioni.Select(x => new StatoPagamentoOnere(x)));
                });
            }
            catch (Exception ex)
            {
                this._log.Error($"Errore durante la verifica delle posizioni debitorie {ex.ToString()}");
                throw;
            }
        }

        /// <summary>
        /// Inserisce una posizione debitoria e ne effettua l'attivazione sul nodo di pagamento
        /// </summary>
        /// <param name="request"></param>
        /// <returns></returns>
        public IEsitoAttivazionePagamento InserisciPosizioniDebitorie(RichiestaDiPagamento request)
        {
            return this.CallClient<IEsitoAttivazionePagamento>(client =>
            {
                var dataScadenza = DateTime.Now.AddDays(this._settings.GgScadenzaPagoDopo);

                var inserisciPosizioneRequest = request.ToInserisciPosizioniDebitorieType(this._settings, dataScadenza);

                this.LogInfo($"Chiamata a InserisciPosizioniDebitorie: {inserisciPosizioneRequest.ToXmlString()}");

                var esitoInserimentoPosizioneDebitoria = client.InserisciPosizioniDebitorie(inserisciPosizioneRequest);

                this.LogInfo($"Risposta da InserisciPosizioniDebitorie: {esitoInserimentoPosizioneDebitoria.ToXmlString()}");

                if (esitoInserimentoPosizioneDebitoria.esito != EsitoType.OK)
                {
                    return EsitoAttivazioneSessionePagamento.AttivazioneFallita(esitoInserimentoPosizioneDebitoria.messaggio);
                }

                if (!request.AttivaSessionePagamento)
                {
                    return new EsitoInserimentoPosizioneDebitoria(esitoInserimentoPosizioneDebitoria);
                }

                var riferimentiPosizioneInserita = esitoInserimentoPosizioneDebitoria.posizioniInserite.First();

                var richiestaAttivazionePagamento = new AttivaSessionePagamentoType
                {
                    cfEnteCreditore = this._settings.CodiceFiscaleEnteCreditore,
                    urlRedirectEsito = new UrlPagamenti(this._settings.UrlRitorno, request.RiferimentiDomanda).ToString(),
                    riferimentoPosizione = riferimentiPosizioneInserita
                };

                this.LogInfo($"Chiamata a AttivaSessionePagamento: {richiestaAttivazionePagamento.ToXmlString()}");

                var result = client.AttivaSessionePagamento(richiestaAttivazionePagamento);

                this.LogInfo($"Risposta da AttivaSessionePagamento: {result.ToXmlString()}");

                return new EsitoAttivazioneSessionePagamento(result, riferimentiPosizioneInserita);
            });

        }

        /// <summary>
        /// Annulla una posizione debitoria precedentemente inserita.
        /// Se non sono presenti posizioni da annullare ritorna al chiamante senza generare errori
        /// </summary>
        /// <param name="posizioniDaAnnullare"></param>
        /// <returns></returns>
        public EsitoAnnullamentoPosizioneDebitoria AnnullaPosizioneDebitoria(IEnumerable<IEstremiPosizioneDebitoria> posizioniDaAnnullare)
        {
            try
            {
                return this.CallClient(client =>
                {
                    var request = new AnnullaPosizioniDebitorieType
                    {
                        cfEnteCreditore = this._settings.CodiceFiscaleEnteCreditore,
                        posizioniAnnullate = posizioniDaAnnullare.Select(x => new RiferimentoPosizioneDebitoriaType
                        {
                            IUV = x.IUV,
                            idPosizione = x.IdPosizioneDebitoria,
                            riferimentoClient = new[] { x.UniqueId }
                        }).ToArray()
                    };

                    if (request.posizioniAnnullate.Length == 0)
                    {
                        return new EsitoAnnullamentoPosizioneDebitoria(true, "Nessuna posizione da annullare");
                    }

                    if (this._log.IsInfoEnabled)
                    {
                        this._log.Info($"Chiamata a AnnullaPosizioniDebitorie: {request.ToXmlString()}");
                    }

                    var result = client.AnnullaPosizioniDebitorie(request);

                    if (this._log.IsInfoEnabled)
                    {
                        this._log.Info($"Risposta da AnnullaPosizioniDebitorie: {result.ToXmlString()}");
                    }

                    return new EsitoAnnullamentoPosizioneDebitoria(result.esito == EsitoType.OK, result.messaggio);
                });
            }
            catch (Exception ex)
            {
                this._log.Error($"Errore durante l'attivazione della Sessione di Pagamento: {ex}");
                throw;
            }
        }


        #region creazione del client per l'invocazione degli web services del nodo di pagamento
        private T CallClient<T>(Func<pagamentiServiceClient, T> callback)
        {
            using (var client = this.CreaWebService())
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

        private pagamentiServiceClient CreaWebService()
        {
            try
            {
                var endPointAddress = new EndpointAddress(this._settings.UrlWs);
                var binding = new BasicHttpBinding("defaultServiceBinding");

                if (endPointAddress.Uri.Scheme.ToLower() == Constants.HTTPS)
                {
                    binding.Security = new BasicHttpSecurity { Mode = BasicHttpSecurityMode.Transport };
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
            if (this._log.IsInfoEnabled)
            {
                this._log.Info(text);
            }
        }

        public AvvisoDiPagamento GetAvvisoPagamento(IEstremiPosizioneDebitoria estremiPosizioneDebitoria)
        {
            var res = this.CallClient(ws =>
            {
                var req = new InviaAvvisiPagamentoType
                {
                    cfEnteCreditore = this._settings.CodiceFiscaleEnteCreditore,
                    riferimentoPosizione = new[] { estremiPosizioneDebitoria.ToRiferimentoPosizioneDebitoriaType() }
                };

                this._log.Info($"Richiesta a InviaAvvisoPagamento: {req.ToXmlString()} ");

                var result = ws.InviaAvvisoPagamento(req);

                this._log.Info($"Risposta da InviaAvvisoPagamento: {result.ToXmlString()} ");

                return result;
            })?
            .Where(x => x.tipoDocumento == TipoDocumentoType.AVVISO)
            .FirstOrDefault();

            if (res == null)
            {
                this._log.Error($"La chiamata a InviaAvvisoPagamento ha restituito null");
                // TODO: log...
                return null;
            }

            return new AvvisoDiPagamento
            {
                Stato = (AvvisoDiPagamento.StatoAvvisoEnum)Enum.Parse(typeof(AvvisoDiPagamento.StatoAvvisoEnum), res.statoDocumento.ToString()),
                Dati = res.documento,
                Desctrizione = res.nomeDocumento
            };

        }

    }
}
