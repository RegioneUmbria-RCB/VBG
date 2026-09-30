using Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti;
using Init.Sigepro.FrontEnd.AppLogic.GestioneOneri;
using Init.Sigepro.FrontEnd.AppLogic.GestionePagamenti.NODOPAGAMENTI.Configurazione;
using Init.Sigepro.FrontEnd.AppLogic.GestionePagamenti.NODOPAGAMENTI.Conti;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneOneri;
using Init.Sigepro.FrontEnd.AppLogic.SalvataggioDomanda;
using Init.Sigepro.FrontEnd.AppLogic.Utils.SerializationExtensions;
using Init.Sigepro.FrontEnd.AppLogic.Wrappers;
using Init.Sigepro.FrontEnd.Pagamenti;
using Init.Sigepro.FrontEnd.Pagamenti.NODOPAGAMENTI;
using Init.Sigepro.FrontEnd.Pagamenti.NODOPAGAMENTI.Attivazione;
using Init.Sigepro.FrontEnd.Pagamenti.NODOPAGAMENTI.Verifica;
using log4net;
using System;
using System.Collections.Generic;
using System.Linq;

namespace Init.Sigepro.FrontEnd.AppLogic.GestionePagamenti.NODOPAGAMENTI
{

    public class PagamentiNodoPagamentiService : IPagamentiNodoPagamentiService
    {
        private static class Constants
        {
            public const string DatiPagamentiExtra = "DatiPagamentiExtra";
        }

        private readonly INodoPagamentiPaymentService _nodoPagamentiService;
        private readonly ISalvataggioDomandaStrategy _salvataggioDomandaStrategy;
        private readonly ILog _log = LogManager.GetLogger(typeof(PagamentiNodoPagamentiService));
        private readonly IOneriRepository _oneriDomandaService;
        private readonly IGuidWrapperService _guidWrapperService;
        private readonly IConfigurazioneNodoPagamentiRepository _configurazioneRepository;
        private readonly IContiRepository _contiRepository;

        public PagamentiNodoPagamentiService(
            IConfigurazioneNodoPagamentiRepository configurazioneRepository,
            INodoPagamentiPaymentService nodoPagamentiService,
            ISalvataggioDomandaStrategy salvataggioDomandaStrategy,
            IOneriRepository oneriDomandaService,
            IGuidWrapperService guidWrapperService,
            IContiRepository contiRepository)
        {
            this._configurazioneRepository = configurazioneRepository;
            this._nodoPagamentiService = nodoPagamentiService;
            this._salvataggioDomandaStrategy = salvataggioDomandaStrategy;
            this._oneriDomandaService = oneriDomandaService;
            this._guidWrapperService = guidWrapperService;
            this._contiRepository = contiRepository;
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
            var domanda = this._salvataggioDomandaStrategy.GetById(idDomanda);

            var operazioni = domanda.ReadInterface.Oneri.GetOneriConPagamentoOnline();

            if (!operazioni.Any())
            {
                this._log.ErrorFormat("Si è cercato di efettuare la verifica dello stato di pagamento per una domanda su cui non sono state avviate operazioni di pagamento. Id domanda={0}", idDomanda);

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
            var domanda = this._salvataggioDomandaStrategy.GetById(idDomanda);
            var operazioni = domanda.ReadInterface.Oneri.GetOneriOnlineConPagamentoAvviato();
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
            var domanda = this._salvataggioDomandaStrategy.GetById(idDomanda);

            return domanda.ReadInterface.Oneri.GetOneriConPagamentoOnline().Any();
        }


        /// <summary>
        /// Avvia le operazioni di pagamento su una domanda sulla quale non siano già state avviate.
        /// Nel caso in cui sulla domanda siano già state avviate delle operazioni di pagamento solleva un'eccezione.
        /// </summary>
        /// <param name="estremiDomanda"></param>
        /// <returns>Url verso cui effettuare il redirect per effettuare il pagamento</returns>
        public string InizializzaPagamento(EstremiDomandaNodoPagamenti estremiDomanda)
        {
            if (this.PagamentoAvviato(estremiDomanda.IdDomanda))
            {
                this._log.ErrorFormat("Si sta cercando di avviare un pagamento sulla domanda {0} ma esiste già un'operazione di pagamento avviata", estremiDomanda.IdDomanda);
                throw new AttivazionePagamentoException("Sulla domanda corrente è già stata avviata un'operazione di pagamento");
            }

            try
            {
                var result = this.AttivaPagamentoSuNodo(estremiDomanda, ModelloPagamentoEnum.OnTheFly);

                if (!result.Esito)
                {
                    this._log.ErrorFormat("Impossibile attivare una sessione di pagamento sul nodo di pagamenti: {0}", result.DescrizioneErrore);
                }

                return (result.Esito) ? result.UrlSistemaPagamenti : String.Empty;
            }
            catch (Exception ex)
            {
                this._log.Error("Errore durante l'attivazione della sessione di pagamento", ex);

                throw new AttivazionePagamentoException($"Si è verificato un errore durante l'attivazione del pagamento per la pratica corrente. Consultare i logs per ulteriori dettagli");
            }
        }


        public bool AttivaPagaDopo(EstremiDomandaNodoPagamenti estremiDomanda)
        {
            if (this.PagamentoAvviato(estremiDomanda.IdDomanda))
            {
                this._log.ErrorFormat("Si sta cercando di avviare un pagamento sulla domanda {0} ma esiste già un'operazione di pagamento avviata", estremiDomanda.IdDomanda);
                throw new AttivazionePagamentoException("Sulla domanda corrente è già stata avviata un'operazione di pagamento");
            }

            try
            {
                var result = this.AttivaPagamentoSuNodo(estremiDomanda, ModelloPagamentoEnum.PagaDopo);

                if (!result.Esito)
                {
                    this._log.ErrorFormat("Impossibile attivare una sessione di pagamento sul nodo di pagamenti: {0}", result.DescrizioneErrore);
                }

                return result.Esito;
            }
            catch (Exception ex)
            {
                this._log.Error("Errore durante l'attivazione della sessione di pagamento", ex);

                throw new AttivazionePagamentoException($"Si è verificato un errore durante l'attivazione del pagamento per la pratica corrente. Consultare i logs per ulteriori dettagli");
            }
        }


        public DatiAvvisoPagamento GetAvvisoPagamento(int idDomanda, string uidOnere)
        {
            var domanda = this._salvataggioDomandaStrategy.GetById(idDomanda);

            var estremiPosizione = domanda.ReadInterface
                                .Oneri
                                .GetOneriOnlineConPagamentoAvviato()
                                .Where(x => x.UniqueId == uidOnere)
                                .Select(x => new EstremiPosizioneDebitoria(x.UniqueId, x.IdPosizioneNodoPagamenti, x.IUV))
                                .FirstOrDefault();

            if (estremiPosizione == null)
            {
                return new DatiAvvisoPagamento { Stato = AvvisoDiPagamento.StatoAvvisoEnum.NON_DISPONIBILE };
            }

            var datiAvviso = this._nodoPagamentiService.GetAvvisoPagamento(estremiPosizione);

            var avviso = new DatiAvvisoPagamento
            {
                Stato = datiAvviso.Stato,
                File = datiAvviso.Dati == null ? null : new BinaryFile("avviso-di-pagamento.pdf", "application/pdf", datiAvviso.Dati),
                Descrizione = datiAvviso.Desctrizione
            };

            if (avviso.Stato == AvvisoDiPagamento.StatoAvvisoEnum.DISPONIBILE && (avviso.File?.FileContent?.Length ?? 0) == 0)
            {
                avviso.Stato = AvvisoDiPagamento.StatoAvvisoEnum.RICHIESTO;
            }

            return avviso;
        }

        public IEnumerable<DatiOperazioneSuNodoPagamenti> GetDatiPosizioniDebitorie(int idDomanda)
        {
            var domanda = this._salvataggioDomandaStrategy.GetById(idDomanda);

            try
            {
                return domanda.ReadInterface
                                .Oneri
                                .GetOneriOnlineConPagamentoAvviato()
                                .Select(x => new EstremiPosizioneDebitoria(x.UniqueId, x.IdPosizioneNodoPagamenti, x.IUV))
                                .Select(x => this._nodoPagamentiService.GetDettagliPosizione(x));
            }
            catch (Exception ex)
            {
                var uid = Guid.NewGuid().ToString();
                this._log.ErrorFormat("Errore nella chiamata a GetStatoPosizioniDebitorie ({0}): {1}", uid, ex);
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
            var domanda = this._salvataggioDomandaStrategy.GetById(idDomanda);
            var configurazione = this._configurazioneRepository.GetConfigurazione(domanda.DataKey.Software, domanda.ReadInterface.AltriDati.CodiceComune);

            try
            {
                var estremiPosizioniAppese = domanda.ReadInterface
                                                     .Oneri
                                                     .GetOneriOnlineConPagamentoAvviato()
                                                     .Select(x => new EstremiPosizioneDebitoria(x.UniqueId, x.IdPosizioneNodoPagamenti, x.IUV));

                if (!estremiPosizioniAppese.Any())
                {
                    return;
                }

                var verifica = this._nodoPagamentiService.VerificaPosizioni(estremiPosizioniAppese);

                foreach (var stato in verifica.StatoOneri)
                {
                    var onere = estremiPosizioniAppese.FirstOrDefault(x => stato.RiferimentiClient.Contains(x.UniqueId));

                    this._log.InfoFormat("Pagamento con UniqueId={0}, IUV={1}, IdPosizioneDebitoria={2} in stato: {3} (stato nativo: {4})",
                        onere.UniqueId, onere.IUV, onere.IdPosizioneDebitoria, stato.Stato, stato.StatoPagamentoNativo);

                    domanda.WriteInterface.Oneri.AggiornaStatoPagamentoNativo(onere.UniqueId, stato.StatoPagamentoNativo);

                    if (stato.Stato == StatoPagamentoEnum.PagamentoRiuscito)
                    {
                        var tipoPagamento = this._oneriDomandaService.GetModalitaPagamentoById(configurazione.IdModalitaPagamento.ToString());

                        this._log.InfoFormat("Il pagamento con UniqueId={0}, IUV={1}, IdPosizioneDebitoria={2} risulta pagato con la modalità di pagamento \"{3}\"",
                            onere.UniqueId, onere.IUV, onere.IdPosizioneDebitoria, tipoPagamento.Descrizione);

                        domanda.WriteInterface.Oneri.PagamentoRiuscitoDaNodoPagamenti(onere.UniqueId, verifica.CodiceFiscaleEnteCreditore, stato.DataOraPagamento.Value, tipoPagamento);
                    }

                    if (stato.Stato == StatoPagamentoEnum.PagamentoFallito)
                    {
                        this._log.ErrorFormat("Il pagamento con UniqueId={0}, IUV={1}, IdPosizioneDebitoria={2} risulta fallito",
                            onere.UniqueId, onere.IUV, onere.IdPosizioneDebitoria);

                        domanda.WriteInterface.Oneri.PagamentoFallitoDaNodoPagamenti(onere.UniqueId);
                    }
                }

                this._salvataggioDomandaStrategy.Salva(domanda);
            }
            catch (Exception ex)
            {
                this._log.Info("Errore durante l'aggiornamento dello stato del pagamento", ex);
            }
        }


        /// <summary>
        /// Restituisce true se à stata avviata una procedura di pagamento per la domanda corrispondente all'id passato
        /// </summary>
        /// <param name="idDomanda"></param>
        /// <returns></returns>
        public bool PagamentoAvviato(int idDomanda)
        {
            var domanda = this._salvataggioDomandaStrategy.GetById(idDomanda);

            return this.PagamentoAvviato(domanda);
        }

        private IEsitoAttivazionePagamento AttivaPagamentoSuNodo(EstremiDomandaNodoPagamenti estremiDomanda, ModelloPagamentoEnum modelloPagamento)
        {
            var domanda = this._salvataggioDomandaStrategy.GetById(estremiDomanda.IdDomanda);

            try
            {
                // Inizializza le operazioni di pagamento e resetta gli uniqueId di tutti gli oneri pagabili online
                var oneriPerPagamentoOnline = domanda.WriteInterface.Oneri.AvviaOperazioneDiPagamento(this._guidWrapperService);

                var oneri = oneriPerPagamentoOnline.Select(x =>
                {
                    var conto = this._contiRepository.GetDatiContoDaCausaleOnere(domanda.ReadInterface.AltriDati.CodiceComune, x.Causale.Codice);
                    var importo = x.Importo;

                    if (importo == 0 && x.ImportoPagato != importo)
                    {
                        importo = x.ImportoPagato;
                    }

                    if (conto == null)
                    {
                        this._log.ErrorFormat("Non è stato trovato un conto configurato per la causale {0}", x.Causale.Codice);
                    }

                    return new OnereNodoPagamentiDTO(x.UniqueId, conto.CodiceMappaturaNodoPagamenti, x.Causale.Descrizione, (decimal)importo);
                }).ToArray();

                var riferimentiDomanda = new RiferimentiDomanda(domanda.DataKey, estremiDomanda.StepId);
                var riferimentiOperazione = new CausaliDaPagare(oneri);
                var richiestaPagamento = new RichiestaDiPagamento(riferimentiDomanda, estremiDomanda.RiferimentiUtenteNodoPagamenti, riferimentiOperazione, modelloPagamento == ModelloPagamentoEnum.OnTheFly);
                var pagamentoOTF = modelloPagamento == ModelloPagamentoEnum.OnTheFly && this._nodoPagamentiService.SupportaPagamentoOnTheFly;

                var esito = pagamentoOTF ?
                    this._nodoPagamentiService.AttivaPagamentoOnTheFly(richiestaPagamento) :
                    this._nodoPagamentiService.InserisciPosizioniDebitorie(richiestaPagamento);

                if (!esito.Esito)
                {
                    domanda.WriteInterface.Oneri.AnnullaPagamentiFalliti();
                    domanda.WriteInterface.Oneri.AnnullaPagamentiInCorso();
                }
                else
                {
                    foreach (var posizione in esito.PosizioniAttivate)
                    {
                        domanda.WriteInterface.Oneri.ImpostaRiferimentiNodoPagamenti(posizione.UniqueId, posizione.IdPosizioneDebitoria, posizione.IUV);
                    }

                    this._salvataggioDomandaStrategy.Salva(domanda);
                }

                return esito;
            }
            catch (Exception ex)
            {
                domanda.WriteInterface.Oneri.AnnullaPagamentiFalliti();
                domanda.WriteInterface.Oneri.AnnullaPagamentiInCorso();
                this._log.Error("Errore durante l'attivazione delle richieste di pagamento", ex);

                throw;
            }
        }

        private bool PagamentoAvviato(DomandaOnline domanda)
        {
            if (domanda.ReadInterface.Oneri.GetOneriOnlineProntiPerPagamento().Any())
            {
                return false;
            }

            return domanda.ReadInterface.Oneri.GetOneriOnlineConPagamentoAvviato().Any() ||
                   domanda.ReadInterface.Oneri.GetOneriOnlineConPagamentoRiuscito().Any() ||
                   domanda.ReadInterface.Oneri.GetOneriOnlineConPagamentoFallito().Any();
        }

        public void AnnullaPagamenti(int idDomanda, FlagEliminazionePagamenti flagEliminazione)
        {
            var domanda = this._salvataggioDomandaStrategy.GetById(idDomanda);

            if (!this.PagamentoAvviato(domanda))
            {
                return;
            }

            var oneri = new List<OnereFrontoffice>();

            if (flagEliminazione == FlagEliminazionePagamenti.PagamentiFalliti || flagEliminazione == FlagEliminazionePagamenti.PagamentiFallitiOInAttesaDiRisposta)
            {
                oneri.AddRange(domanda.ReadInterface.Oneri.GetOneriOnlineConPagamentoFallito().ToList());
            }

            if (flagEliminazione == FlagEliminazionePagamenti.PagamentiInAttesaDiRisposta || flagEliminazione == FlagEliminazionePagamenti.PagamentiFallitiOInAttesaDiRisposta)
            {
                oneri.AddRange(domanda.ReadInterface.Oneri.GetOneriOnlineConPagamentoAvviato().ToList());
            }

            var estremiPosizioniAppese = oneri.Select(x => new EstremiPosizioneDebitoria(x.UniqueId, x.IdPosizioneNodoPagamenti, x.IUV)).ToList();

            var esitoAnnullamento = this._nodoPagamentiService.AnnullaPosizioneDebitoria(estremiPosizioniAppese);

            if (!esitoAnnullamento.OperazioneRiuscita)
            {
                this._log.ErrorFormat("Impossibile annullare la posizione debitoria con riferimenti: {0}. Ragione dell'errore: {1}",
                    estremiPosizioniAppese.ToList().ToXmlString(), esitoAnnullamento.MessaggioErrore);
            }

            domanda.WriteInterface.Oneri.AnnullaPagamentiByUniqueId(estremiPosizioniAppese.Select(x => x.UniqueId));

            this._salvataggioDomandaStrategy.Salva(domanda);
        }


        public bool NodoPagamentoAttivo(int idDomanda)
        {
            var domanda = this._salvataggioDomandaStrategy.GetById(idDomanda);

            var configurazione = this._configurazioneRepository.GetConfigurazione(domanda.DataKey.Software, domanda.ReadInterface.AltriDati.CodiceComune);

            return configurazione.NodoPagamentiAttivo;
        }

        public bool PagoDopoAttivo(int idDomanda)
        {
            var domanda = this._salvataggioDomandaStrategy.GetById(idDomanda);

            var configurazione = this._configurazioneRepository.GetConfigurazione(domanda.DataKey.Software, domanda.ReadInterface.AltriDati.CodiceComune);

            return configurazione.NodoPagamentiAttivo &&
                    configurazione.PagoDopoAttivo &&
                    this._nodoPagamentiService.SupportaPagoDopo;
        }
    }
}
