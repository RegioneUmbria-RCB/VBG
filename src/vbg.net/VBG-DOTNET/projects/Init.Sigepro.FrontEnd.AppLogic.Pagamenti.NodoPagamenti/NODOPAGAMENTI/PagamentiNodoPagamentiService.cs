using Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti;
using Init.Sigepro.FrontEnd.AppLogic.GestioneOneri;
using Init.Sigepro.FrontEnd.AppLogic.GestioneOneri.EditingSession;
using Init.Sigepro.FrontEnd.AppLogic.GestionePagamenti;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneOneri;
using Init.Sigepro.FrontEnd.AppLogic.Pagamenti.NodoPagamenti.Configurazione;
using Init.Sigepro.FrontEnd.AppLogic.Pagamenti.NodoPagamenti.Conti;
using Init.Sigepro.FrontEnd.AppLogic.Pagamenti.NodoPagamenti.GestioneMessaggiRabbit.PosizioniDebitorie;
using Init.Sigepro.FrontEnd.AppLogic.Pagamenti.NodoPagamenti.NODOPAGAMENTI;
using Init.Sigepro.FrontEnd.AppLogic.Pagamenti.NodoPagamenti.NODOPAGAMENTI.GenerazioneUrlRitorno;
using Init.Sigepro.FrontEnd.AppLogic.Utils.SerializationExtensions;
using Init.Sigepro.FrontEnd.AppLogic.Wrappers;
using Init.Sigepro.FrontEnd.Infrastructure.UrlsAndPaths;
using log4net;
using System;
using System.Collections.Generic;
using System.Linq;
using VBG.Pagamenti.NodoPagamenti;
using VBG.Pagamenti.NodoPagamenti.Attivazione;
using VBG.Pagamenti.NodoPagamenti.Shared;
using VBG.Pagamenti.NodoPagamenti.Verifica;

namespace Init.Sigepro.FrontEnd.AppLogic.Pagamenti.NodoPagamenti
{

    public class PagamentiNodoPagamentiService : IPagamentiNodoPagamentiService
    {
        private static class Constants
        {
            public const string DatiPagamentiExtra = "DatiPagamentiExtra";
        }

        private readonly INodoPagamentiPaymentService _nodoPagamentiService;
        private readonly ILog _log = LogManager.GetLogger(typeof(PagamentiNodoPagamentiService));
        private readonly IOneriRepository _oneriDomandaService;
        private readonly IGuidWrapperService _guidWrapperService;
        private readonly IConfigurazioneNodoPagamentiRepository _configurazioneRepository;
        private readonly IContiRepository _contiRepository;
        private readonly INodoPagamentiSettingsReader _settingsReader;
        private readonly IResolveUrl _resolveUrl;
        private readonly OneriDomandaEditingSessionFactory _oneriDomandaEditingSessionFactory;
        private readonly IEventiPagamentiService _eventiPagamentiService;

        public PagamentiNodoPagamentiService(
            IConfigurazioneNodoPagamentiRepository configurazioneRepository,
            INodoPagamentiPaymentService nodoPagamentiService,
            IOneriRepository oneriDomandaService,
            IGuidWrapperService guidWrapperService,
            IContiRepository contiRepository,
            INodoPagamentiSettingsReader settingsReader,
            IResolveUrl resolveUrl,
            OneriDomandaEditingSessionFactory oneriDomandaEditingSessionFactory,
            IEventiPagamentiService eventiPagamentiService
            )
        {
            this._configurazioneRepository = configurazioneRepository;
            this._nodoPagamentiService = nodoPagamentiService;
            this._oneriDomandaService = oneriDomandaService;
            this._guidWrapperService = guidWrapperService;
            this._contiRepository = contiRepository;
            this._settingsReader = settingsReader;
            this._resolveUrl = resolveUrl;
            this._oneriDomandaEditingSessionFactory = oneriDomandaEditingSessionFactory;
            this._eventiPagamentiService = eventiPagamentiService;
        }

        /// <summary>
        /// Verifica lo stato di tutte le posizioni aperte (in corso di pagamento, riuscite o fallite)
        /// ATTENZIONE! La chiamata non causa un aggiornamento dello stato dei pagamenti
        /// Se si vuole aggiornare lo stato del pagamento chiamare il metodo AggiornaStatoPagamenti
        /// Se le operazioni di pagamento non sono state avviate solleva un'eccezione
        /// </summary>
        /// <param name="idDomanda"></param>
        /// <returns></returns>
        public StatoSessionePagamentoOnLine GetStatoPosizioni(int idDomanda)
        {
            var domanda = this._oneriDomandaEditingSessionFactory.StartEditingSession(idDomanda);

            var operazioni = domanda.ReadInterface.GetOneriConPagamentoOnline();

            if (!operazioni.Any())
            {
                this._log.Error($"Si è cercato di efettuare la verifica dello stato di pagamento per una domanda " +
                    $"su cui non sono state avviate operazioni di pagamento. Id domanda={idDomanda}");

                throw new VerificaStatoPosizioniDebitorieException("Impossibile leggere lo stato dei pagamenti per la domanda corrente perché nella domanda non sono state avviate operazioni di pagamento");
            }

            return new StatoSessionePagamentoOnLine(operazioni.Select(x => new OnereConPagamentoInSospeso
            {
                Causale = x.Causale.Descrizione,
                Importo = x.ImportoPagato.ToString("N2"),
                IdPosizioneNodoPagamenti = x.IdPosizioneNodoPagamenti,
                UniqueId = x.UniqueId,
                IUV = x.IUV,
                Stato = x.StatoPagamento,
                StatoNativo = x.StatoPagamentoNativo
            }));
        }




        /// <summary>
        /// Restituisce la lista dei pagamenti che risultano in sospeso per una domanda.
        /// ATTENZIONE! La chiamata non causa un aggiornamento dello stato dei pagamenti
        /// Se si vuole aggiornare lo stato del pagamento chiamare il metodo AggiornaStatoPagamenti
        /// </summary>
        /// <param name="idDomanda"></param>
        /// <returns></returns>
        public StatoSessionePagamentoOnLine GetStatoPagamentiInSospeso(int idDomanda)
        {
            var domanda = this._oneriDomandaEditingSessionFactory.StartEditingSession(idDomanda);
            var operazioni = domanda.ReadInterface.GetOneriOnlineConPagamentoAvviato();
            // var datiExtra = domanda.ReadInterface.DatiExtra.Get<PagamentiDatiExtra>(Constants.DatiPagamentiExtra);

            return new StatoSessionePagamentoOnLine(operazioni.Select(x => new OnereConPagamentoInSospeso
            {
                Causale = x.Causale.Descrizione,
                Importo = x.ImportoPagato.ToString("N2"),
                IdPosizioneNodoPagamenti = x.IdPosizioneNodoPagamenti,
                UniqueId = x.UniqueId,
                IUV = x.IUV,
                Stato = x.StatoPagamento,
                StatoNativo = x.StatoPagamentoNativo
            }));
        }

        /// <summary>
        /// Restituisce true se la domanda corrispondente all'id passato permette il pagamento online degli oneri, altrimenti false.
        /// Una domanda richiede il pagamento online nel caso in cui ci siano oneri pronti per il pagamento oppure oneri con pagamento in sospeso.
        /// </summary>
        /// <param name="idDomanda"></param>
        /// <returns></returns>
        public bool DomandaRichiedePagamentoOnline(int idDomanda)
        {
            var domanda = this._oneriDomandaEditingSessionFactory.StartEditingSession(idDomanda);

            return domanda.RichiedePagamentoOnLine;
        }


        /// <summary>
        /// Avvia le operazioni di pagamento su una domanda sulla quale non siano già state avviate.
        /// Nel caso in cui sulla domanda siano già state avviate delle operazioni di pagamento solleva un'eccezione.
        /// </summary>
        /// <param name="estremiDomanda"></param>
        /// <returns>Url verso cui effettuare il redirect per effettuare il pagamento</returns>
        public IEsitoAttivazionePagamento AvviaPagamentoOnTheFly(EstremiDomandaNodoPagamenti estremiDomanda, IUrlRitornoPagamentiProvider urlRitornoPagamentiProvider)
        {
            if (this.PagamentoAvviato(estremiDomanda.IdDomanda))
            {
                this._log.Error($"Si sta cercando di avviare un pagamento sulla domanda {estremiDomanda.IdDomanda} ma esiste già un'operazione di pagamento avviata");
                throw new PagamentoGiaAvviatoException("Sulla domanda corrente è già stata avviata un'operazione di pagamento");
            }

            try
            {
                var result = this.AttivaPagamentoSuNodo(estremiDomanda, ModelloPagamentoEnum.OnTheFly, urlRitornoPagamentiProvider);

                if (!result.Esito)
                {
                    this._log.Error($"Impossibile attivare una sessione di pagamento sul nodo di pagamenti: {result.DescrizioneErrore}");
                }

                return result;
            }
            catch (Exception ex)
            {
                this._log.Error($"Errore durante l'attivazione della sessione di pagamento: {ex}");

                throw new AttivazionePagamentoException($"Si è verificato un errore durante l'attivazione del pagamento per la pratica corrente. Consultare i logs per ulteriori dettagli");
            }
        }


        public bool AvviaPagamentoDifferito(EstremiDomandaNodoPagamenti estremiDomanda)
        {
            if (this.PagamentoAvviato(estremiDomanda.IdDomanda))
            {
                this._log.Error($"Si sta cercando di avviare un pagamento sulla domanda {estremiDomanda.IdDomanda} ma esiste già un'operazione di pagamento avviata");
                throw new AttivazionePagamentoException("Sulla domanda corrente è già stata avviata un'operazione di pagamento");
            }

            try
            {
                var result = this.AttivaPagamentoSuNodo(estremiDomanda, ModelloPagamentoEnum.PagaDopo, null);

                if (!result.Esito)
                {
                    this._log.Error($"Impossibile attivare una sessione di pagamento sul nodo di pagamenti: {result.DescrizioneErrore}");
                }

                return result.Esito;
            }
            catch (Exception ex)
            {
                this._log.Error($"Errore durante l'attivazione della sessione di pagamento: {ex}");

                throw new AttivazionePagamentoException($"Si è verificato un errore durante l'attivazione del pagamento per la pratica corrente. Consultare i logs per ulteriori dettagli");
            }
        }

        public DatiRicevutaTelematica ScaricaRicevutaTelematica(int idDomanda, string uidOnere)
        {
            var domanda = this._oneriDomandaEditingSessionFactory.StartEditingSession(idDomanda);
            var codiceComune = domanda.CodiceComune;

            var estremiPosizione = domanda.ReadInterface
                                .GetOneriOnlineConPagamentoRiuscito()
                                .Where(x => x.UniqueId == uidOnere)
                                .Select(x => new EstremiPosizioneDebitoriaClient(codiceComune, x.UniqueId, x.IdPosizioneNodoPagamenti, x.IUV))
                                .FirstOrDefault();

            if (estremiPosizione == null)
            {
                return new DatiRicevutaTelematica { Stato = RicevutaTelematica.StatoRicevutaEnum.NON_DISPONIBILE };
            }

            var datiAvviso = this._nodoPagamentiService.ScaricaRicevutaTelematica(estremiPosizione);

            var ricevuta = new DatiRicevutaTelematica
            {
                Stato = datiAvviso.Stato,
                File = datiAvviso.Dati == null ? null : BinaryFile.FromFileData(datiAvviso.Descrizione, "application/pdf", datiAvviso.Dati),
                Descrizione = datiAvviso.Descrizione
            };

            if (ricevuta.Stato == RicevutaTelematica.StatoRicevutaEnum.DISPONIBILE && (ricevuta.File?.FileContent?.Length ?? 0) == 0)
            {
                ricevuta.Stato = RicevutaTelematica.StatoRicevutaEnum.RICHIESTO;
            }

            return ricevuta;
        }

        public DatiAvvisoPagamento ScaricaAvvisoPagamento(int idDomanda, string uidOnere)
        {
            var domanda = this._oneriDomandaEditingSessionFactory.StartEditingSession(idDomanda);
            var codiceComune = domanda.CodiceComune;

            var estremiPosizione = domanda.ReadInterface
                                .GetOneriOnlineConPagamentoAvviato()
                                .Where(x => x.UniqueId == uidOnere)
                                .Select(x => new EstremiPosizioneDebitoriaClient(codiceComune, x.UniqueId, x.IdPosizioneNodoPagamenti, x.IUV))
                                .FirstOrDefault();

            if (estremiPosizione == null)
            {
                return new DatiAvvisoPagamento { Stato = AvvisoDiPagamento.StatoAvvisoEnum.NON_DISPONIBILE };
            }

            var datiAvviso = this._nodoPagamentiService.ScaricaAvvisoPagamento(estremiPosizione);

            var avviso = new DatiAvvisoPagamento
            {
                Stato = datiAvviso.Stato,
                File = datiAvviso.Dati == null ? null : BinaryFile.FromFileData(datiAvviso.NomeFile, "application/pdf", datiAvviso.Dati),
                Descrizione = datiAvviso.Descrizione
            };

            if (avviso.Stato == AvvisoDiPagamento.StatoAvvisoEnum.DISPONIBILE && (avviso.File?.FileContent?.Length ?? 0) == 0)
            {
                avviso.Stato = AvvisoDiPagamento.StatoAvvisoEnum.RICHIESTO;
            }

            return avviso;
        }

        public IEnumerable<DatiOperazioneSuNodoPagamenti> GetDatiPosizioniDebitorie(int idDomanda)
        {
            var domanda = this._oneriDomandaEditingSessionFactory.StartEditingSession(idDomanda);
            var codiceComune = domanda.CodiceComune;

            try
            {
                var oneriOnline = domanda.ReadInterface.GetOneriConPagamentoOnline();
                var estremi = oneriOnline.Select(x => new EstremiPosizioneDebitoriaClient(codiceComune, x.UniqueId, x.IdPosizioneNodoPagamenti, x.IUV));

                var list = new List<DatiOperazioneSuNodoPagamenti>();

                foreach (var x in estremi)
                {
                    var dettagli = this._nodoPagamentiService.GetDettagliPosizione(x);

                    list.Add(dettagli);
                }

                return list;
            }
            catch (Exception ex)
            {
                var uid = Guid.NewGuid().ToString();
                this._log.Error($"Errore nella chiamata a GetStatoPosizioniDebitorie ({uid}): {ex}");
                throw new Exception($"Si è verificato un errore durante la verifica della posizione debitoria (Rif.errore {uid})");
            }
        }

        /// <summary>
        /// Aggiorna lo stato di tutte le operazioni di pagamento della domanda corrispondente all'id passato.
        /// Se non sono state avviate operazioni di pagamento esce in silenzio
        /// </summary>
        /// <param name="idDomanda"></param>
        public void AggiornaStatoPagamenti(int idDomanda)
        {
            var oneriDomanda = this._oneriDomandaEditingSessionFactory.StartEditingSession(idDomanda);
            var configurazione = this._configurazioneRepository.GetConfigurazione(oneriDomanda.CodiceComune);

            try
            {
                var estremiPosizioniAppese = oneriDomanda.ReadInterface.GetOneriOnlineConPagamentoAvviato()
                                                         .Select(x => new EstremiPosizioneDebitoriaClient(oneriDomanda.CodiceComune, x.UniqueId, x.IdPosizioneNodoPagamenti, x.IUV));


                if (!estremiPosizioniAppese.Any())
                {
                    return;
                }

                var verifica = this._nodoPagamentiService.VerificaPosizioni(estremiPosizioniAppese);

                foreach (var stato in verifica.StatoOneri)
                {
                    var onere = estremiPosizioniAppese.FirstOrDefault(x => stato.RiferimentiClient.Contains(x.RiferimentoClient));

                    this._log.Info($"Pagamento con RiferimentoClient={onere.RiferimentoClient}, IUV={onere.IUV}, IdPosizioneDebitoria={onere.IdPosizioneDebitoria} in stato: {stato.Stato} (stato nativo: {stato.StatoPagamentoNativo})");

                    // domanda.WriteInterface.Oneri.AggiornaStatoPagamentoNativo(onere.UniqueId, stato.StatoPagamentoNativo);
                    oneriDomanda.AggiornaStatoPagamentoNativo(onere.RiferimentoClient, stato.StatoPagamentoNativo);

                    if (stato.Stato == StatoPagamentoEnum.PagamentoRiuscito)
                    {
                        var tipoPagamento = this._oneriDomandaService.GetModalitaPagamentoById(configurazione.IdModalitaPagamento.ToString());

                        this._log.Info($"Il pagamento con RiferimentoClient={onere.RiferimentoClient}, IUV={onere.IUV}, IdPosizioneDebitoria={onere.IdPosizioneDebitoria} risulta pagato con la modalità di pagamento \"{tipoPagamento.Descrizione}\"");

                        // domanda.WriteInterface.Oneri.PagamentoRiuscitoDaNodoPagamenti(onere.UniqueId, verifica.CodiceFiscaleEnteCreditore, stato.DataOraPagamento.Value, tipoPagamento);
                        oneriDomanda.PagamentoRiuscitoDaNodoPagamenti(onere.RiferimentoClient, verifica.CodiceFiscaleEnteCreditore, stato.DataOraPagamento.Value, tipoPagamento);
                    }

                    if (stato.Stato == StatoPagamentoEnum.PagamentoFallito)
                    {
                        this._log.Error($"Il pagamento con RiferimentoClient={onere.RiferimentoClient}, IUV={onere.IUV}, IdPosizioneDebitoria={onere.IdPosizioneDebitoria} risulta fallito");

                        // domanda.WriteInterface.Oneri.PagamentoFallitoDaNodoPagamenti(onere.UniqueId);
                        oneriDomanda.PagamentoFallitoDaNodoPagamenti(onere.RiferimentoClient);
                    }
                }

                oneriDomanda.TerminaSessioneModifica();
            }
            catch (Exception ex)
            {
                this._log.Info($"Errore durante l'aggiornamento dello stato del pagamento, {ex.Message}");
            }
        }


        /// <summary>
        /// Restituisce true se à stata avviata una procedura di pagamento per la domanda corrispondente all'id passato
        /// </summary>
        /// <param name="idDomanda"></param>
        /// <returns></returns>
        public bool PagamentoAvviato(int idDomanda)
        {
            var editingSession = this._oneriDomandaEditingSessionFactory.StartEditingSession(idDomanda);

            return editingSession.IsPagamentoAvviato;
        }

        public bool IsPagamentoModello3(int idDomanda)
        {
            var editingSession = this._oneriDomandaEditingSessionFactory.StartEditingSession(idDomanda);

            return editingSession.ModelloPagamento == ModelloPagamentoEnum.PagaDopo;
        }

        private IEsitoAttivazionePagamento AttivaPagamentoSuNodo(EstremiDomandaNodoPagamenti estremiDomanda, ModelloPagamentoEnum modelloPagamento, IUrlRitornoPagamentiProvider urlRitornoPagamentiProvider = null)
        {
            var oneriDomanda = this._oneriDomandaEditingSessionFactory.StartEditingSession(estremiDomanda.IdDomanda);

            try
            {
                // Inizializza le operazioni di pagamento e resetta gli uniqueId di tutti gli oneri pagabili online
                var oneriPerPagamentoOnline = oneriDomanda.AvviaOperazioneDiPagamento(this._guidWrapperService, modelloPagamento);

                var oneri = oneriPerPagamentoOnline.Select(x =>
                {
                    var conto = this._contiRepository.GetDatiContoDaCausaleOnere(oneriDomanda.CodiceComune, x.Causale.Codice);

                    var importo = x.Importo;

                    if (importo == 0 && x.ImportoPagato != importo)
                    {
                        importo = x.ImportoPagato;
                    }

                    if (conto == null)
                    {
                        var errMsg = $"Non è stato trovato un conto configurato per la causale {x.Causale.Codice} - {x.Causale.Descrizione}";
                        this._log.Error(errMsg);

                        throw new Exception(errMsg);
                    }

                    return new OnereNodoPagamentiDTO(x.UniqueId, conto.CodiceMappaturaNodoPagamenti, x.Causale.Descrizione, importo);
                }).ToArray();

                var codiceComune = oneriDomanda.CodiceComune;
                var infoConnettore = this._nodoPagamentiService.GetInfoConnettore(codiceComune);

                var riferimentiDomanda = new RiferimentiDomanda(this.DataKeyToRiferimentiDomanda(oneriDomanda.DataKey), estremiDomanda.StepId);
                var riferimentiOperazione = new CausaliDaPagare(oneri);
                var soggettoDebitore = estremiDomanda.SoggettoDebitore.ToSoggettoDebitoreType();
                var richiestaPagamento = new RichiestaDiPagamento(riferimentiDomanda, soggettoDebitore, riferimentiOperazione, modelloPagamento == ModelloPagamentoEnum.OnTheFly);
                var pagamentoOTF = modelloPagamento == ModelloPagamentoEnum.OnTheFly && infoConnettore.SupportaPagamentoOtf;
                var settings = this._settingsReader.Read(codiceComune);
                var urlRitorno = urlRitornoPagamentiProvider?.GeneraUrlRitorno(riferimentiDomanda, settings) ?? this.GeneraUrlRitorno(riferimentiDomanda, settings);

                var esito = pagamentoOTF ?
                    this._nodoPagamentiService.AttivaPagamentoOnTheFly(settings, urlRitorno, richiestaPagamento) :
                    this._nodoPagamentiService.AttivaPagamentoOffline(settings, urlRitorno, richiestaPagamento);

                if (!esito.Esito)
                {
                    // domanda.WriteInterface.Oneri.AnnullaPagamentiFalliti();
                    // domanda.WriteInterface.Oneri.AnnullaPagamentiInCorso();
                    oneriDomanda.AnnullaPagamenti();

                }
                else
                {
                    foreach (var posizione in esito.PosizioniAttivate)
                    {
                        // domanda.WriteInterface.Oneri.ImpostaRiferimentiNodoPagamenti(posizione.UniqueId, posizione.IdPosizioneDebitoria, posizione.IUV);
                        oneriDomanda.ImpostaRiferimentiNodoPagamenti(posizione.RiferimentoClient, posizione.IdPosizioneDebitoria, posizione.IUV);
                    }



                    // Invio tramite rabbit il messaggio di destinatari pendenza modificati (in teoria andrebbe mandato solo se la pendenza è intestata ad una partita IVA ma al momento la mando comunque)
                    foreach (var posizione in esito.PosizioniAttivate)
                    {
                        this._eventiPagamentiService.DestinatariPendenzaAggiornati(oneriDomanda.DataKey, settings.CodiceFiscaleEnteCreditore, posizione, estremiDomanda);
                    }

                    oneriDomanda.TerminaSessioneModifica();

                }

                return esito;
            }
            catch (Exception ex)
            {
                oneriDomanda.AnnullaPagamenti();

                this._log.Error($"Errore durante l'attivazione delle richieste di pagamento: {ex}");

                throw;
            }
        }


#if NET48
        private string GeneraUrlRitorno(RiferimentiDomanda riferimentiDomanda, NodoPagamentiSettings settings)
        {
            return new UrlPagamenti(settings.UrlRitorno, riferimentiDomanda, this._resolveUrl).ToString();
        }
#endif

#if NET9_0_OR_GREATER
        private string GeneraUrlRitorno(RiferimentiDomanda domanda, NodoPagamentiSettings settings)
        {
            var relative = $"~/{domanda.IdComune}/{domanda.Software}/pagamento-completato/{domanda.IdDomanda}";

            if ((domanda.StepId ?? -1) != -1)
            {
                relative += $"/{domanda.StepId}";
            }
            return this._resolveUrl.ToAbsoluteUrl(relative);
        }
#endif


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

        public void AnnullaPagamentiFalliti(int idDomanda)
        {
            this.AnnullaPagamenti(idDomanda, FlagEliminazionePagamenti.PagamentiFalliti);
        }

        public void AnnullaPagamenti(int idDomanda, FlagEliminazionePagamenti flagEliminazione)
        {
            var oneriDomanda = this._oneriDomandaEditingSessionFactory.StartEditingSession(idDomanda);

            if (!oneriDomanda.IsPagamentoAvviato)
            {
                return;
            }

            var oneri = new List<OnereFrontoffice>();

            if (flagEliminazione == FlagEliminazionePagamenti.PagamentiFalliti || flagEliminazione == FlagEliminazionePagamenti.PagamentiFallitiOInAttesaDiRisposta)
            {
                oneri.AddRange(oneriDomanda.GetOneriOnlineConPagamentoFallito());
            }

            if (flagEliminazione == FlagEliminazionePagamenti.PagamentiInAttesaDiRisposta || flagEliminazione == FlagEliminazionePagamenti.PagamentiFallitiOInAttesaDiRisposta)
            {
                oneri.AddRange(oneriDomanda.GetOneriOnlineConPagamentoAvviato());
            }

            var estremiPosizioniAppese = oneri.Select(x => new EstremiPosizioneDebitoriaClient(oneriDomanda.CodiceComune, x.UniqueId, x.IdPosizioneNodoPagamenti, x.IUV)).ToList();

            var esitoAnnullamento = this._nodoPagamentiService.AnnullaPosizioneDebitoria(estremiPosizioniAppese);

            if (!esitoAnnullamento.OperazioneRiuscita)
            {
                this._log.Error($"Impossibile annullare la posizione debitoria con riferimenti: {estremiPosizioniAppese.ToList().ToXmlString()}. Risposta da NODO_PAGAMENTI: {esitoAnnullamento.MessaggioErrore}");
            }

            oneriDomanda.AnnullaPagamentiByUniqueId(estremiPosizioniAppese.Select(x => x.RiferimentoClient));

            oneriDomanda.TerminaSessioneModifica();
        }

        /// <summary>
        /// Il nodo pagamenti è considerato attivo se:
        /// - in configurazione il nodo pagamenti è attivo
        /// - il nodo pagamenti supporta il pagamento OTF oppure:
        /// - in configurazione è attivo il pago dopo e il nodo supporta il pago dopo
        /// </summary>
        /// <param name="idDomanda"></param>
        /// <returns></returns>
        public bool NodoPagamentoAttivo(int idDomanda)
        {
            var flags = this.GetFlagsNodoPagamenti(idDomanda);

            return flags.NodoPagamentiAttivo;
        }

        public FlagsAttivazioneNodoPagamenti GetFlagsNodoPagamenti(int idDomanda)
        {
            var domanda = this._oneriDomandaEditingSessionFactory.StartEditingSession(idDomanda); // this._salvataggioDomandaStrategy.GetById(idDomanda);
            var codiceComune = domanda.CodiceComune;

            var configurazione = this._configurazioneRepository.GetConfigurazione(codiceComune);

            if (!configurazione.NodoPagamentiAttivo)
            {
                return new FlagsAttivazioneNodoPagamenti
                {
                    NodoPagamentiAttivo = false,
                    PagamentoOTFAttivo = false,
                    PagamentoOfflineAttivo = false
                };
            }

            var infoConnettore = this._nodoPagamentiService.GetInfoConnettore(codiceComune);

            var nodoSupportaPagamentoOTF = infoConnettore.SupportaPagamentoOtf;
            var nodoSupportaPagoDopo = infoConnettore.SupportaPagoDopo;

            var nodoPagamentoAttivo = configurazione.NodoPagamentiAttivo &&
                    (nodoSupportaPagamentoOTF || (configurazione.PagoDopoAttivo && nodoSupportaPagoDopo));

            var pagamentoOtfAttivo = configurazione.NodoPagamentiAttivo && nodoSupportaPagamentoOTF;

            var pagamentoOfflineAttivo = configurazione.NodoPagamentiAttivo && configurazione.PagoDopoAttivo && nodoSupportaPagoDopo;

            return new FlagsAttivazioneNodoPagamenti
            {
                NodoPagamentiAttivo = nodoPagamentoAttivo,
                PagamentoOTFAttivo = pagamentoOtfAttivo,
                PagamentoOfflineAttivo = pagamentoOfflineAttivo
            };
        }

        public bool PagamentoOnlineAttivo(int idDomanda)
        {
            var flags = this.GetFlagsNodoPagamenti(idDomanda);

            return flags.PagamentoOTFAttivo;
        }

        public bool PagoDopoAttivo(int idDomanda)
        {
            // var domanda = this._salvataggioDomandaStrategy.GetById(idDomanda);

            var flags = this.GetFlagsNodoPagamenti(idDomanda);

            return flags.PagamentoOfflineAttivo;
        }

        public IEnumerable<ModalitaPagamentoNodoPagamenti> GetModalitaPagamentoSupportate(int idDomanda, bool permettiGiaPagatoConPagaDopoAttivo, bool disbilitaNonDovuto)
        {
            var l = new List<ModalitaPagamentoNodoPagamenti>
            {
                new ModalitaPagamentoNodoPagamenti(ModalitaPagamentoOnereEnum.Online, "Online")
            };

            if (this.PermettiGiaPagato(idDomanda, permettiGiaPagatoConPagaDopoAttivo))   // Spostato su metodo a parte per migliorare la leggibilità
            {
                l.Add(new ModalitaPagamentoNodoPagamenti(ModalitaPagamentoOnereEnum.GiaPagato, "Effettuato"));
            }

            if (!disbilitaNonDovuto)
            {
                l.Add(new ModalitaPagamentoNodoPagamenti(ModalitaPagamentoOnereEnum.NonDovuto, "Non dovuto"));
            }

            return l;
        }

        private bool PermettiGiaPagato(int idDomanda, bool permettiGiaPagatoConPagaDopoAttivo)
        {
            if (!this.PagoDopoAttivo(idDomanda))
            {
                return true;
            }

            if (permettiGiaPagatoConPagaDopoAttivo)
            {
                return true;
            }

            return false;
        }
    }
}
