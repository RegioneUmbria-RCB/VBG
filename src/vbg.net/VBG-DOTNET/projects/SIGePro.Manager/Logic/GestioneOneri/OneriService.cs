using VBG.Shared.Infrastructure.ServiceModel;
using Init.SIGePro.Data;
using Init.SIGePro.Manager.Authentication;
using Init.SIGePro.Manager.Configuration;
using Init.SIGePro.Manager.IOC;
using Init.SIGePro.Manager.IstanzeOneriService;
using Init.SIGePro.Manager.Logic.GestioneOneri.PosizioniDebitorie;
using log4net;
using PersonalLib2.Data;
using System;
using System.Collections.Generic;
using System.Linq;
using System.ServiceModel;

namespace Init.SIGePro.Manager.Logic.GestioneOneri
{
    public class OneriService : IOneriService
    {
        private readonly ILog _log = LogManager.GetLogger(typeof(OneriService));
        private readonly IVerificaAnnullamentoPosizioniDebitorieService _verificaAnnullamentoPosizioniDebitorieService;
        private readonly AuthenticationInfo _authInfo;
        private readonly IBindingFactory _bindingFactory;

        public class OnereModificabileResponse
        {
            public bool Modificabile { get; internal set; }
            public string Messaggio { get; internal set; }
        }

        public OneriService(AuthenticationInfo authInfo)
        {
            this._verificaAnnullamentoPosizioniDebitorieService = new VerificaAnnullamentoPosizioniDebitorieService(authInfo);
            this._authInfo = authInfo;
            this._bindingFactory = StaticKernelContainer.GetService<IBindingFactory>();
        }

#pragma warning disable RCS1163 // Unused parameter.
        public OneriService(string tokenUtente, string idComune = "" /* lasciato solo per compatibilità se usato dalle formule, in realtà non serve*/)
#pragma warning restore RCS1163 // Unused parameter.
            : this(StaticKernelContainer.GetService<IAuthenticationManager>().CheckToken(tokenUtente))
        {
        }

        public bool OnerePagato(int codiceIstanza, int idCausale)
        {
            //per compatibilità
            return this.OnerePagato(codiceIstanza, idCausale, null);
        }

        public bool OnerePagato(int codiceIstanza, int idCausale, int? annoScadenzaRiferimento)
        {
            return this.AlmenoUnOnerePagato(codiceIstanza, new[] { idCausale }, annoScadenzaRiferimento);
        }

        public bool AlmenoUnOnerePagato(int codiceIstanza, IEnumerable<int> idCausali)
        {
            //per compatibilità
            return this.AlmenoUnOnerePagato(codiceIstanza, idCausali, null);
        }

        public bool AlmenoUnOnerePagato(int codiceIstanza, IEnumerable<int> idCausali, int? annoScadenzaRiferimento)
        {
            using (var db = this._authInfo.CreateDatabase())
            {
                var sql = $@"select 
								count(*) 
							from 
								istanzeoneri 
							where 
								istanzeoneri.idcomune={db.Specifics.QueryParameterName("idComune")} and 
								istanzeoneri.codiceistanza={db.Specifics.QueryParameterName("codiceIstanza")} and 
								istanzeoneri.fkidtipocausale={db.Specifics.QueryParameterName("idCausale")} and
								istanzeoneri.datapagamento is not null";

                if (annoScadenzaRiferimento.HasValue)
                {
                    sql += $" AND istanzeoneri.datascadenza BETWEEN {db.QueryParameter("data1")} AND {db.QueryParameter("data2")}";
                }

                foreach (var id in idCausali)
                {
                    var cnt = db.ExecuteScalar(sql, 0, mp =>
                    {
                        mp.AddParameter("idComune", this._authInfo.IdComune);
                        mp.AddParameter("codiceIstanza", codiceIstanza);
                        mp.AddParameter("idCausale", id);
                        if (annoScadenzaRiferimento.HasValue)
                        {
                            mp.AddParameter("data1", new DateTime(annoScadenzaRiferimento.Value, 1, 1, 0, 0, 0));
                            mp.AddParameter("data2", new DateTime(annoScadenzaRiferimento.Value, 12, 31, 23, 59, 59));
                        }
                    });

                    if (cnt > 0)
                    {
                        return true;
                    }
                }

                return false;
            }
        }

        public void AggiornaOInserisciByIdCausale(IEnumerable<OnereDaRegistrare> oneri)
        {
            foreach (var onere in oneri)
            {
                this.AggiornaOInserisciByIdCausale(onere);
            }
        }

        //Il metodo ragiona per causale in quanto allo stato attuale utilizzato in una formula di scheda dinamica in cui può
        //esistere un solo onere con quella causale
        public OnereModificabileResponse OnereModificabile(int codiceIstanza, int idCausale)
        {
            //per compatibilità
            return this.OnereModificabile(codiceIstanza, idCausale, null);
        }

        public OnereModificabileResponse OnereModificabile(int codiceIstanza, int idCausale, int? annoScadenzaRiferimento)
        {
            if (this.OnerePagato(codiceIstanza, idCausale, annoScadenzaRiferimento))
            {
                return new OnereModificabileResponse { Modificabile = false, Messaggio = $"Non si può modificare un onere già pagato (CodiceIstanza={codiceIstanza}, IdCausale={idCausale})" };
            }

            if (this.HaPosizioneDebitoria(codiceIstanza, idCausale, annoScadenzaRiferimento))
            {
                return new OnereModificabileResponse { Modificabile = false, Messaggio = $"Non si può modificare una causale che ha già una posizione debitoria (CodiceIstanza={codiceIstanza}, IdCausale={idCausale})" };
            }

            if (this.HaUnaBollettazione(codiceIstanza, idCausale, annoScadenzaRiferimento))
            {
                return new OnereModificabileResponse { Modificabile = false, Messaggio = $"Non si può modificare una causale gestita tramite bollettazione (CodiceIstanza={codiceIstanza}, IdCausale={idCausale})" };
            }

            return new OnereModificabileResponse { Modificabile = true, Messaggio = "" };
        }

        private bool HaPosizioneDebitoria(int codiceIstanza, int idCausale)
        {
            //per compatibilità
            return this.HaPosizioneDebitoria(codiceIstanza, idCausale, null);
        }
        private bool HaPosizioneDebitoria(int codiceIstanza, int idCausale, int? annoScadenzaRiferimento)
        {
            using (var db = this._authInfo.CreateDatabase())
            {
                var sql = $@"select 
								count(*)
							from 
								istanzeoneri 
                                  inner join istoneri_dett_posizioni on istanzeoneri.idcomune = istoneri_dett_posizioni.idcomune and  istanzeoneri.id = istoneri_dett_posizioni.fk_istanzeoneri_id
							where 
								istanzeoneri.idcomune={db.Specifics.QueryParameterName("idComune")} and 
								istanzeoneri.codiceistanza={db.Specifics.QueryParameterName("codiceIstanza")} and 
								istanzeoneri.fkidtipocausale={db.Specifics.QueryParameterName("idCausale")} and
								istoneri_dett_posizioni.fk_dettposdebitoria_id is not null";
                if (annoScadenzaRiferimento.HasValue)
                {
                    sql += $" AND istanzeoneri.datascadenza BETWEEN {db.QueryParameter("data1")} AND {db.QueryParameter("data2")}";
                }


                var cnt = db.ExecuteScalar(sql, 0, mp =>
                {
                    mp.AddParameter("idComune", this._authInfo.IdComune);
                    mp.AddParameter("codiceIstanza", codiceIstanza);
                    mp.AddParameter("idCausale", idCausale);
                    if (annoScadenzaRiferimento.HasValue)
                    {
                        mp.AddParameter("data1", new DateTime(annoScadenzaRiferimento.Value, 1, 1, 0, 0, 0));
                        mp.AddParameter("data2", new DateTime(annoScadenzaRiferimento.Value, 12, 31, 23, 59, 59));
                    }
                });

                return cnt > 0;
            }
        }


        private bool HaUnaBollettazione(int codiceIstanza, int idCausale)
        {
            //per compatibilità
            return this.HaUnaBollettazione(codiceIstanza, idCausale, null);
        }

        private bool HaUnaBollettazione(int codiceIstanza, int idCausale, int? annoScadenzaRiferimento)
        {
            using (var db = this._authInfo.CreateDatabase())
            {
                var sql = $@"select 
								count(*)
							from 
								istanzeoneri 
                                    inner join boll_gest_istanzeoneri on 
                                        istanzeoneri.idcomune = boll_gest_istanzeoneri.idcomune and
                                        istanzeoneri.id = boll_gest_istanzeoneri.fk_codiceistanzeoneri
							where 
								istanzeoneri.idcomune={db.Specifics.QueryParameterName("idComune")} and 
								istanzeoneri.codiceistanza={db.Specifics.QueryParameterName("codiceIstanza")} and 
								istanzeoneri.fkidtipocausale={db.Specifics.QueryParameterName("idCausale")}";
                if (annoScadenzaRiferimento.HasValue)
                {
                    sql += $" AND istanzeoneri.datascadenza BETWEEN {db.QueryParameter("data1")} AND {db.QueryParameter("data2")}";
                }

                var cnt = db.ExecuteScalar(sql, 0, mp =>
                {
                    mp.AddParameter("idComune", this._authInfo.IdComune);
                    mp.AddParameter("codiceIstanza", codiceIstanza);
                    mp.AddParameter("idCausale", idCausale);
                    if (annoScadenzaRiferimento.HasValue)
                    {
                        mp.AddParameter("data1", new DateTime(annoScadenzaRiferimento.Value, 1, 1, 0, 0, 0));
                        mp.AddParameter("data2", new DateTime(annoScadenzaRiferimento.Value, 12, 31, 23, 59, 59));
                    }
                });

                return cnt > 0;
            }
        }


        public void AggiornaOInserisci(OnereDaRegistrare onere, int annoScadenzaRiferimento)
        {
            //1. Se non presente viene inserito
            var onerePresente = this.EsisteOnereInAnnualita(onere.CodiceIstanza, onere.IdCausale, annoScadenzaRiferimento);
            if (!onerePresente)
            {
                this.Inserisci(onere);
                return;
            }

            //2. Se esistente ma non modificabile, solleva un'eccezione per impredire la duplicazione per l'anno di riferimento
            var modificabile = this.OnereModificabile(onere.CodiceIstanza, onere.IdCausale, annoScadenzaRiferimento);
            if (!modificabile.Modificabile)
            {
                throw new Exception(modificabile.Messaggio);
            }

            //3. Aggiornamento dell'onere
            var listaId = this.GetIdOnereByIdCausale(onere.CodiceIstanza, onere.IdCausale, annoScadenzaRiferimento);

            if (listaId.Count() > 1)
            {
                var messaggioErrore = $"La ricerca di un onere per causale ha restituito più di un risultato (CodiceIstanza={onere.CodiceIstanza}, IdCausale={onere.IdCausale}, Anno della scadenza={annoScadenzaRiferimento})";

                this._log.Error(messaggioErrore);

                throw new InvalidOperationException(messaggioErrore);
            }

            this.AggiornaOnere(listaId.First(), onere);

        }

        public void AggiornaOInserisciByIdCausale(OnereDaRegistrare onere)
        {
            var onereModificabile = this.OnereModificabile(onere.CodiceIstanza, onere.IdCausale);

            if (!onereModificabile.Modificabile)
            {
                // prendo gli oneri per causale e annualità
                // - non ci sono  => creo nuovo onere
                // - ne trovo n
                //      - per ciascuno verifico se annullato (*)
                //          - se se almeno uno non ha posizione debitoria => errore
                //          - se tutti annullati  => creo nuovo onere
                //          - se almeno uno non annullato  => errore

                if (!onere.Scadenza.HasValue)
                {
                    this._log.Error($"Tovato un onere non non modificabile e l'onere passato non ha una data scadenza impostata: {onereModificabile.Messaggio}");

                    throw new InvalidOperationException(onereModificabile.Messaggio);
                }

                var oneri = this.GetIdOneriPerCausaleEAnnualita(onere.CodiceIstanza, onere.IdCausale, onere.Scadenza.Value.Year);

                if (oneri.Any())
                {
                    var oneriNonAnnullati = oneri.Select(idOnere => this._verificaAnnullamentoPosizioniDebitorieService.PosizioniDebitorieOnereAnnullate(idOnere))
                                                 .Where(isAnnullato => !isAnnullato);

                    if (oneriNonAnnullati.Any())
                    {
                        var errore = $"Esiste almeno un onere non annullato per la pratica {onere.CodiceIstanza} nell'annualità {onere.Scadenza.Value.Year}";

                        this._log.Error(errore);

                        throw new InvalidOperationException(onereModificabile.Messaggio);
                    }
                }

                this.Inserisci(onere);

                return;
            }

            var listaId = this.GetIdOnereByIdCausale(onere.CodiceIstanza, onere.IdCausale);

            if (!listaId.Any())
            {
                this.Inserisci(onere);

                return;
            }

            if (listaId.Count() > 1)
            {
                var messaggioErrore = $"La ricerca di un onere per causale ha restituito più di un risultato (CodiceIstanza={onere.CodiceIstanza}, IdCausale={onere.IdCausale})";

                this._log.Error(messaggioErrore);

                throw new InvalidOperationException(messaggioErrore);
            }

            this.AggiornaOnere(listaId.First(), onere);
        }

        // ATTENZIONE! Utilizzato dalle formule a Modena
        public IEnumerable<int> GetIdOneriPerCausaleEAnnualita(int codiceIstanza, int idCausale, int annualita)
        {
            using (var db = this._authInfo.CreateDatabase())
            {
                var sql = "SELECT id FROM istanzeoneri " +
                    $" WHERE idcomune={db.QueryParameter("idcomune")} " +
                    $"   AND codiceistanza={db.QueryParameter("codiceistanza")} " +
                    $"   AND fkidtipocausale={db.QueryParameter("fkidtipocausale")} " +
                    $"   AND datascadenza BETWEEN {db.QueryParameter("data1")} AND {db.QueryParameter("data2")}";

                return db.ExecuteReader(sql,
                    mp => mp.Add("idcomune", this._authInfo.IdComune)
                            .Add("codiceistanza", codiceIstanza)
                            .Add("fkidtipocausale", idCausale)
                            .Add("data1", new DateTime(annualita, 1, 1, 0, 0, 0))
                            .Add("data2", new DateTime(annualita, 12, 31, 23, 59, 59)),
                    dr => dr.GetInt("id").Value);
            }
        }

        private void AggiornaOnere(int idOnere, OnereDaRegistrare onere)
        {
            using (var db = this._authInfo.CreateDatabase())
            {
                var sql = $@"update 
                                istanzeoneri 
                            set 
                                prezzo={db.Specifics.QueryParameterName("prezzo")},
                                datascadenza={db.Specifics.QueryParameterName("dataScadenza")},
                                fkidtipocausale={db.Specifics.QueryParameterName("fkidtipocausale")}
                            where
                                idcomune={db.Specifics.QueryParameterName("idComune")} and
                                id={db.Specifics.QueryParameterName("id")}";

                db.ExecuteNonQuery(sql, mp =>
                {
                    mp.AddParameter("prezzo", onere.Importo);
                    mp.AddParameter("dataScadenza", onere.Scadenza);
                    mp.AddParameter("fkidtipocausale", onere.IdCausale);
                    mp.AddParameter("idComune", this._authInfo.IdComune);
                    mp.AddParameter("id", idOnere);
                });
            }
        }

        private IEnumerable<int> GetIdOnereByIdCausale(int codiceIstanza, int idCausale)
        {
            //per compatibilità
            return this.GetIdOnereByIdCausale(codiceIstanza, idCausale, null);
        }

        private IEnumerable<int> GetIdOnereByIdCausale(int codiceIstanza, int idCausale, int? annoScadenzaRiferimento)
        {
            using (var db = this._authInfo.CreateDatabase())
            {
                var sql = $@"select 
                                id 
                            from 
                                istanzeoneri 
                            where 
                                idcomune={db.Specifics.QueryParameterName("idComune")} and
                                codiceistanza={db.Specifics.QueryParameterName("codiceIstanza")} and 
                                fkidtipocausale={db.Specifics.QueryParameterName("fkidtipocausale")}";

                if (annoScadenzaRiferimento.HasValue)
                {
                    sql += $" AND datascadenza BETWEEN {db.QueryParameter("data1")} AND {db.QueryParameter("data2")}";
                }

                return db.ExecuteReader(sql,
                    mp =>
                    {
                        mp.AddParameter("idComune", this._authInfo.IdComune);
                        mp.AddParameter("codiceIstanza", codiceIstanza);
                        mp.AddParameter("fkidtipocausale", idCausale);
                        if (annoScadenzaRiferimento.HasValue)
                        {
                            mp.AddParameter("data1", new DateTime(annoScadenzaRiferimento.Value, 1, 1, 0, 0, 0));
                            mp.AddParameter("data2", new DateTime(annoScadenzaRiferimento.Value, 12, 31, 23, 59, 59));
                        }
                    },
                    dr => dr.GetInt32(0)
                    );
            }
        }

        public void Inserisci(IEnumerable<OnereDaRegistrare> oneri)
        {
            if (oneri == null)
            {
                throw new ArgumentException("L'argomento non può essere null", nameof(oneri));
            }

            foreach (var onere in oneri)
            {
                this.Inserisci(onere);
            }
        }

        public int Inserisci(OnereDaRegistrare onere)
        {
            if (onere == null)
            {
                throw new ArgumentException("L'argomento non può essere null", nameof(onere));
            }

            using (var ws = this.CreaServizio())
            {
                var esito = ws.InsertOnere(new InsertOnereRequest
                {
                    importo = Convert.ToDecimal(onere.Importo),
                    riferimentoIstanza = onere.CodiceIstanza.ToString(),
                    riferimentoCausale = onere.IdCausale.ToString(),
                    datascadenza = onere.Scadenza ?? DateTime.MinValue,
                    datascadenzaSpecified = onere.Scadenza.HasValue,
                    numerorata = onere.NumeroRata.ToString(),
                    token = this._authInfo.Token
                });

                if (esito.esitoOperazione == null)
                {
                    var messaggioErrore = $"Si è verificato un errore inaspettato durante la creazione dell'onere con tipo causale {onere.IdCausale} e importo {onere.Importo}";
                    this._log.Error(messaggioErrore);
                    throw new Exception(messaggioErrore);
                }

                if (esito.esitoOperazione.esito == 0)
                {
                    var arrayErrori = esito.esitoOperazione
                                            .listaErrori
                                            .Select(x => $"{x.codice} - {x.descrizione}")
                                            .ToArray();

                    var messaggioErrore = $"Si è verificato un errore durante la creazione dell'onere con tipo causale {onere.IdCausale} e importo {onere.Importo}: {String.Join(", ", arrayErrori)}";

                    this._log.Error(messaggioErrore);

                    throw new Exception(messaggioErrore);
                }

                return Convert.ToInt32(esito.codiceonere);
            }
        }

        public int Inserisci(int codiceIstanza, int idTipoCausale, double valore)
        {
            return this.Inserisci(new OnereDaRegistrare(codiceIstanza, idTipoCausale, valore));
        }

        public int Inserisci(int codiceIstanza, int idTipoCausale, decimal valore)
        {
            return this.Inserisci(new OnereDaRegistrare(codiceIstanza, idTipoCausale, (double)valore));
        }

        public int Inserisci(int codiceIstanza, int idTipoCausale, double valore, DateTime dataScadenza, int numeroRata = 1)
        {
            return this.Inserisci(new OnereDaRegistrare(codiceIstanza, idTipoCausale, valore, dataScadenza, numeroRata));
        }

        public IEnumerable<IstanzeOneri> Inserisci(IEnumerable<IstanzeOneri> oneri)
        {
            using (var ws = this.CreaServizio())
            {
                try
                {
                    return oneri.Select(x => this.Inserisci(ws, x)).ToList();
                }
                catch (Exception)
                {
                    ws.Abort();

                    throw;
                }
            }
        }

        public IstanzeOneri Inserisci(IstanzeoneriClient ws, IstanzeOneri onere)
        {
            var req = new InsertOnereRequest
            {
                codiceamministrazioni = onere.CODICEAMMINISTRAZIONE,
                codiceresponsabile = onere.CODICEUTENTE,
                dataPagamento = onere.DATAPAGAMENTO ?? DateTime.MinValue,
                dataPagamentoSpecified = onere.DATAPAGAMENTO.HasValue,
                datascadenza = onere.DATASCADENZA ?? DateTime.MinValue,
                datascadenzaSpecified = onere.DATASCADENZA.HasValue,
                importo = Convert.ToDecimal(onere.PREZZO ?? 0.0d),
                importopagato = Convert.ToDecimal(onere.ImportoPagato ?? 0),
                importopagatoSpecified = onere.ImportoPagato.HasValue,
                note = onere.NOTE,
                numerorata = onere.NUMERORATA,
                riferimentiPagamento = onere.NR_DOCUMENTO,
                riferimentoCausale = onere.FKIDTIPOCAUSALE,
                riferimentoEndoprocedimento = onere.CODICEINVENTARIO,
                riferimentoIstanza = onere.CODICEISTANZA,
                token = this._authInfo.Token,
                importoInteresse = onere.ImportoInteressi ?? 0,
                importoInteresseSpecified = true,
                onereRateizzato = onere.FlagOnereRateizzato == "1",
                onereRateizzatoSpecified = true
            };

            if (!String.IsNullOrEmpty(onere.FKMODALITAPAGAMENTO))
            {
                req.modalitaPagamento = new ModalitaPagamentoType
                {
                    codice = onere.FKMODALITAPAGAMENTO
                };
            }

            var esito = ws.InsertOnere(req);

            if (esito.esitoOperazione == null)
            {
                throw new Exception("Si è verificato un errore inaspettato durante la creazione dell'onere, impossibile capire l'esito dell'operazione (esito.esitoOperazione == null)");
            }

            if (esito.esitoOperazione.esito == 0)
            {
                var arrayErrori = esito.esitoOperazione.listaErrori.Select(x => String.Format("{0} - {1}", x.codice, x.descrizione)).ToArray();
                var messaggioErrore = $"Si è verificato un errore durante la creazione dell'onere: {String.Join(", ", arrayErrori)}";

                this._log.Error(messaggioErrore);

                throw new Exception(messaggioErrore);
            }

            this._log.Info($"Creato onere con id {esito.codiceonere}");

            onere.ID = esito.codiceonere;

            return onere;
        }

        private IstanzeOneriService.IstanzeoneriClient CreaServizio()
        {
            var endpoint = new EndpointAddress(ParametriConfigurazione.Get.WsIstanzeOneriServiceUrl);
            // var endpoint = new EndpointAddress("http://devdesk103:8080/backend/services/istanzeoneri");
            var binding = this._bindingFactory.CreateAndConfigure("oneriHttpBinding");

            return new IstanzeoneriClient(binding, endpoint);
        }

        public void Elimina(IEnumerable<int> idOneri)
        {
            foreach (var id in idOneri)
            {
                this.Elimina(id);
            }
        }

        public void Elimina(int onereId)
        {
            this.EffettuaCancellazioneACascata(onereId);

            using (var ws = this.CreaServizio())
            {
                var esito = ws.EliminaOnere(new EliminaOnereRequest
                {
                    token = this._authInfo.Token,
                    codiceonere = onereId.ToString()
                });

                if (esito.esitoOperazione.esito == 0)
                {
                    var listaErrori = String.Format(Environment.NewLine, esito.esitoOperazione.listaErrori.Select(x => x.descrizione));
                    var testoErrore = $"Impossibile eliminare l'onere con id {onereId}: {listaErrori}";
                    this._log.Error(testoErrore);

                    throw new Exception(testoErrore);
                }

                this._log.Info($"Eliminato onere con id {onereId}");
            }
        }

        // TODO: Eliminare da qui, va fatto lato java
        private void EffettuaCancellazioneACascata(int onereId)
        {
            using (var db = this._authInfo.CreateDatabase())
            {
                var lIstanzeOneri_Canoni = new IstanzeOneri_CanoniMgr(db).GetList(new IstanzeOneri_Canoni
                {
                    Idcomune = this._authInfo.IdComune,
                    FkIdIstoneri = onereId
                });

                foreach (IstanzeOneri_Canoni tioc in lIstanzeOneri_Canoni)
                {
                    IstanzeOneri_CanoniMgr mgr = new IstanzeOneri_CanoniMgr(db);
                    mgr.Delete(tioc);
                }

                var lIstanzeCalcoloCanoniO = new IstanzeCalcoloCanoniOMgr(db).GetList(new IstanzeCalcoloCanoniO
                {
                    Idcomune = this._authInfo.IdComune,
                    FkIdistoneri = onereId
                });
                foreach (IstanzeCalcoloCanoniO ticco in lIstanzeCalcoloCanoniO)
                {
                    IstanzeCalcoloCanoniOMgr mgr = new IstanzeCalcoloCanoniOMgr(db);
                    mgr.Delete(ticco);
                }
            }
        }

        public bool EsisteOnere(int codiceIstanza, int idCausale)
        {
            using (var db = this._authInfo.CreateDatabase())
            {
                var sql = "SELECT Count(*) FROM istanzeoneri " +
                    $" WHERE idcomune={db.QueryParameter("idcomune")} " +
                    $"   AND codiceistanza={db.QueryParameter("codiceistanza")} " +
                    $"   AND fkidtipocausale={db.QueryParameter("fkidtipocausale")} ";

                var count = db.ExecuteScalar(sql, 0,
                    mp => mp.Add("idcomune", this._authInfo.IdComune)
                            .Add("codiceistanza", codiceIstanza)
                            .Add("fkidtipocausale", idCausale));

                return count > 0;
            }
        }

        public bool EsisteOnereInAnnualita(int codiceIstanza, int idCausale, int annoRiferimento)
        {
            using (var db = this._authInfo.CreateDatabase())
            {
                var sql = "SELECT Count(*) FROM istanzeoneri " +
                    $" WHERE idcomune={db.QueryParameter("idcomune")} " +
                    $"   AND codiceistanza={db.QueryParameter("codiceistanza")} " +
                    $"   AND fkidtipocausale={db.QueryParameter("fkidtipocausale")} " +
                    $"   AND datascadenza BETWEEN {db.QueryParameter("data1")} AND {db.QueryParameter("data2")}";

                var count = db.ExecuteScalar(sql, 0,
                    mp => mp.Add("idcomune", this._authInfo.IdComune)
                            .Add("codiceistanza", codiceIstanza)
                            .Add("fkidtipocausale", idCausale)
                            .Add("data1", new DateTime(annoRiferimento, 1, 1, 0, 0, 0))
                            .Add("data2", new DateTime(annoRiferimento, 12, 31, 23, 59, 59)));

                return count > 0;
            }
        }

        public bool EsisteOnereRegistratoInAnnualita(int codiceIstanza, int idCausale, int annoRiferimento)
        {
            using (var db = this._authInfo.CreateDatabase())
            {
                var sql = "SELECT Count(*) FROM istanzeoneri " +
                    $" WHERE idcomune={db.QueryParameter("idcomune")} " +
                    $"   AND codiceistanza={db.QueryParameter("codiceistanza")} " +
                    $"   AND fkidtipocausale={db.QueryParameter("fkidtipocausale")} " +
                    $"   AND data BETWEEN {db.QueryParameter("data1")} AND {db.QueryParameter("data2")}";

                var count = db.ExecuteScalar(sql, 0,
                    mp => mp.Add("idcomune", this._authInfo.IdComune)
                            .Add("codiceistanza", codiceIstanza)
                            .Add("fkidtipocausale", idCausale)
                            .Add("data1", new DateTime(annoRiferimento, 1, 1, 0, 0, 0))
                            .Add("data2", new DateTime(annoRiferimento, 12, 31, 23, 59, 59)));

                return count > 0;
            }
        }
    }
}
