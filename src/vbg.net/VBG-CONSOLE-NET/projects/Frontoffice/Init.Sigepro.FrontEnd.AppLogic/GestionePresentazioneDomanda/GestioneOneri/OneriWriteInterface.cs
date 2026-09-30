using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.Sigepro.FrontEnd.AppLogic.ObjectSpace.PresentazioneIstanza;
using log4net;
using Init.Sigepro.FrontEnd.AppLogic.GestioneOneri;
using Init.Sigepro.FrontEnd.AppLogic.AreaRiservataService;
using Init.Sigepro.FrontEnd.AppLogic.Wrappers;

namespace Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneOneri
{
    public class OneriWriteInterface : IOneriWriteInterface
    {
        public static class Constants
        {
            public const string TipoOnereIntervento = "I";
            public const string TipoOnereEndo = "E";
            public const string DateTimeFormat = "dd/MM/yyyy HH:mm:ss";
            public static class ModalitaPagamento
            {
                public static readonly string OnLine = ((int)ModalitaPagamentoOnereEnum.Online).ToString();
                public static readonly string GiaPagato = ((int)ModalitaPagamentoOnereEnum.GiaPagato).ToString();
                public static readonly string NonDovuto = ((int)ModalitaPagamentoOnereEnum.NonDovuto).ToString();
            }

            public static class StatiPagamento
            {
                public static readonly string ProntoPerPagamentoOnline = ((int)StatoPagamentoOnereEnum.ProntoPerPagamentoOnline).ToString();
                public static readonly string PagamentoIniziato = ((int)StatoPagamentoOnereEnum.PagamentoIniziato).ToString();
                public static readonly string PagamentoRiuscito = ((int)StatoPagamentoOnereEnum.PagamentoRiuscito).ToString();
                public static readonly string PagamentoNonNecessario = ((int)StatoPagamentoOnereEnum.PagamentoNonNecessario).ToString();
                public static readonly string PagamentoFallito = ((int)StatoPagamentoOnereEnum.PagamentoFallito).ToString();
            }

        }

        private readonly ILog _log = LogManager.GetLogger(typeof(OneriWriteInterface));
        private readonly PresentazioneIstanzaDbV2 _database;

        public OneriWriteInterface(PresentazioneIstanzaDbV2 database)
        {
            this._database = database;
        }

        private int TotaleOneri()
        {
            return (int)(this._database.OneriDomanda.Sum(x => (decimal)x.Importo) * 100.0m);
        }



        #region IOneriWriteInterface Members

        public void SalvaAttestazioneDiPagamento(int codiceOggetto, string nomeFile, bool firmatoDigitalmente)
        {
            EliminaAttestazioneDiPagamento();

            var rigaAllegati = this._database.Allegati.AddAllegatiRow(nomeFile, codiceOggetto, String.Empty, firmatoDigitalmente, String.Empty);

            this._database.OneriAttestazionePagamento
                          .AddOneriAttestazionePagamentoRow(TotaleOneri(), false, rigaAllegati.Id);
        }

        public void EliminaAttestazioneDiPagamento()
        {
            this._log.DebugFormat("EliminaAttestazioneDiPagamento");

            if (this._database.OneriAttestazionePagamento.Count == 0)
                return;

            foreach (var row in this._database.OneriAttestazionePagamento.ToList())
                row.Delete();
        }

        private void SetDichiarazioneAssenzaOneriDaPagare(bool valoreFlag)
        {
            if (valoreFlag)
            {
                EliminaAttestazioneDiPagamento();

                var newrow = this._database.OneriAttestazionePagamento.AddOneriAttestazionePagamentoRow(TotaleOneri(), valoreFlag, -1);
                newrow.SetIdAllegatoNull();
            }
            else
            {
                if (this._database.OneriAttestazionePagamento.Count != 0)
                {
                    this._database.OneriAttestazionePagamento[0].DichiaraDiNonAvereOneriDaPagare = false;
                }
            }
        }

        public void ImpostaDichiarazioneAssenzaOneriDaPagare()
        {
            SetDichiarazioneAssenzaOneriDaPagare(true);
        }

        public void RimuoviDichiarazioneAssenzaOneriDaPagare()
        {
            SetDichiarazioneAssenzaOneriDaPagare(false);
        }


        //public void EliminaOneriIntervento()
        //{
        //    var righeDaEliminare = this._database.OneriDomanda.Where(x => x.TipoOnere == Constants.TipoOnereIntervento);

        //    foreach (var riga in righeDaEliminare)
        //        riga.Delete();

        //    EliminaAttestazioneDiPagamento();
        //}

        //public void EliminaOneriDaIdEndo(int idEndoProcedimento)
        //{
        //    var righeDaEliminare = this._database.OneriDomanda.Where(x => x.TipoOnere == Constants.TipoOnereEndo && x.CodiceInterventoOEndoOrigine == idEndoProcedimento);

        //    foreach (var riga in righeDaEliminare)
        //        riga.Delete();

        //    EliminaAttestazioneDiPagamento();
        //}

        //public void EliminaOneriWhereCodiceCausaleIn(IEnumerable<int> listaIdDaEliminare)
        //{
        //    var eliminaAttestazioneDiPagamento = false;
        //    var elementiDaEliminare = this._database.OneriDomanda.Where(x => listaIdDaEliminare.Contains(x.CodiceCausale));

        //    foreach (var onereDaEliminare in elementiDaEliminare.ToList())
        //    {
        //        this._database.OneriDomanda.RemoveOneriDomandaRow(onereDaEliminare);

        //        eliminaAttestazioneDiPagamento = true;
        //    }

        //    if (eliminaAttestazioneDiPagamento)
        //        EliminaAttestazioneDiPagamento();
        //}


        //public void AggiungiOSalvaOnereIntervento(int codiceCausale, string causale, int codiceInterventoOEndoOrigine, string interventoOEndoOrigine, ModalitaPagamentoOnereEnum modalitaPagamento, float importo, float importoPagato, string note)
        //{
        //    AggiungiOSalvaOnere(Constants.TipoOnereIntervento, codiceCausale, causale, codiceInterventoOEndoOrigine, interventoOEndoOrigine, modalitaPagamento, importo, importoPagato, note);
        //}

        //public void AggiungiOSalvaOnereEndoprocedimento(int codiceCausale, string causale, int codiceInterventoOEndoOrigine, string interventoOEndoOrigine, ModalitaPagamentoOnereEnum modalitaPagamento, float importo, float importoPagato, string note)
        //{
        //    AggiungiOSalvaOnere(Constants.TipoOnereEndo, codiceCausale, causale, codiceInterventoOEndoOrigine, interventoOEndoOrigine, modalitaPagamento, importo, importoPagato, note);
        //}

        //private void AggiungiOSalvaOnere(string tipoOnere, int codiceCausale, string causale, int codiceInterventoOEndoOrigine, string interventoOEndoOrigine, ModalitaPagamentoOnereEnum modalitaPagamento, float importo, float importoPagato, string note)
        //{
        //    _log.DebugFormat("Aggiunta o solvataggio dell'onere con tipoOnere={0}, codiceCausale={1}, causale={2}, codiceInterventoOEndoOrigine={3}, interventoOEndoOrigine={4}, importo={5}, note={6}",
        //                      tipoOnere, codiceCausale, causale, codiceInterventoOEndoOrigine, interventoOEndoOrigine, importo, note);

        //    var strModalitaPagamento = ((int)modalitaPagamento).ToString();
        //    var statoPagamentoDefault = ((int)StatoPagamentoOnereEnum.ProntoPerPagamentoOnline).ToString();

        //    var row = this._database.OneriDomanda.FindByTipoOnereCodiceCausaleCodiceInterventoOEndoOrigine(tipoOnere, codiceCausale, codiceInterventoOEndoOrigine);

        //    if (row == null)
        //    {
        //        _log.Debug("L'onere non esiste nella datatable OneriDomanda e verrà aggiunto");

        //        this._database.OneriDomanda.AddOneriDomandaRow(
        //            tipoOnere,
        //            codiceCausale,
        //            causale,
        //            codiceInterventoOEndoOrigine,
        //            interventoOEndoOrigine,
        //            importo,
        //            note,
        //            false,
        //            String.Empty,
        //            String.Empty,
        //            string.Empty,
        //            String.Empty,
        //            importo,
        //            strModalitaPagamento,
        //            String.Empty,
        //            statoPagamentoDefault,
        //            String.Empty,
        //            String.Empty,
        //            String.Empty,
        //            String.Empty,
        //            DateTime.Now.ToString(Constants.DateTimeFormat));

        //        EliminaAttestazioneDiPagamento();
        //    }
        //    else
        //    {
        //        _log.Debug("L'onere esiste nella datatable OneriDomanda e verrà aggiornato");

        //        if (row.Importo != importo)
        //        {
        //            row.Importo = importo;
        //            row.ImportoPagato = importoPagato;

        //            EliminaAttestazioneDiPagamento();
        //        }

        //        if (row.IsImportoPagatoNull())
        //        {
        //            row.ImportoPagato = importo;
        //        }

        //        row.TipoOnere = tipoOnere;
        //        row.CodiceCausale = codiceCausale;
        //        row.Causale = causale;
        //        row.CodiceInterventoOEndoOrigine = codiceInterventoOEndoOrigine;
        //        row.InterventoOEndoOrigine = interventoOEndoOrigine;
        //        row.ModalitaPagamento = strModalitaPagamento;

        //        row.Note = note;

        //    }
        //}

        public void EliminaOneriWhereCodiceCausaleNotIn(IEnumerable<IdentificativoOnereSelezionato> listaId)
        {
            var _listaId = listaId.ToList();

            var oneriDaRimuovere = this._database
                                        .OneriDomanda.Where(item =>
                                            !_listaId.Exists(e =>
                                                    e.IdCausale == item.CodiceCausale &&
                                                    e.TipoOnere == item.TipoOnere &&
                                                    e.IdInterventoOEndo == item.InterventoOEndoOrigine)
                                         ).ToList();

            oneriDaRimuovere.ForEach(x => this._database.OneriDomanda.RemoveOneriDomandaRow(x));

            if (oneriDaRimuovere.Any())
            {
                EliminaAttestazioneDiPagamento();
            }
        }


        //public void ImpostaEstremiPagamentoOnereIntervento(int codiceCausale, int codiceIntervento, string codiceTipoPagamento, string descrizioneTipoPagamento, DateTime? data, string riferimento, float importoPagato)
        //{
        //    ImpostaEstremiPagamento(Constants.TipoOnereIntervento, codiceCausale, codiceIntervento, codiceTipoPagamento, descrizioneTipoPagamento, data, riferimento, importoPagato);
        //}

        //public void ImpostaEstremiPagamentoOnereEndo(int codiceCausale, int codiceEndo, string codiceTipoPagamento, string descrizioneTipoPagamento, DateTime? data, string riferimento, float importoPagato)
        //{
        //    ImpostaEstremiPagamento(Constants.TipoOnereEndo, codiceCausale, codiceEndo, codiceTipoPagamento, descrizioneTipoPagamento, data, riferimento, importoPagato);
        //}


        public void CancellaEstremiPagamentoOneriNonPagatiOnline()
        {
            foreach (var r in this._database.OneriDomanda.Where(x => x.StatoPagamentoOnline != Constants.StatiPagamento.PagamentoRiuscito))
            {
                r.NonPagato = true;
                r.CodiceTipoPagamento = String.Empty;
                r.DescrizioneTipoPagamento = String.Empty;
                r.DataPagmento = String.Empty;
                r.NumeroPagamento = String.Empty;
                r.ImportoPagato = r.Importo;
                r.StatoPagamentoOnline = Constants.StatiPagamento.ProntoPerPagamentoOnline;
                r.IdPagamentoOnline = String.Empty;
                r.UniqueId = String.Empty;
                r.IdPosizioneNodoPagamenti = String.Empty;
                r.IUV = String.Empty;
            }

            this._database.OneriDomanda.AcceptChanges();
        }

        #endregion

        //private void ImpostaEstremiPagamento(string tipoOnere, int codiceCausale, int codiceInterventoOEndoOrigine, string codiceTipoPagamento, string descrizioneTipoPagamento, DateTime? data, string riferimento, float importoPagato)
        //{
        //    var row = this._database.OneriDomanda.FindByTipoOnereCodiceCausaleCodiceInterventoOEndoOrigine(tipoOnere, codiceCausale, codiceInterventoOEndoOrigine);

        //    if (row == null)
        //        throw new InvalidOperationException(String.Format("impossibile trovare l'onere con id causale {1}, tipo onere {0} e codice endo/intervento {2}", tipoOnere, codiceCausale, codiceInterventoOEndoOrigine));

        //    row.NonPagato = false;
        //    row.CodiceTipoPagamento = codiceTipoPagamento;
        //    row.DescrizioneTipoPagamento = descrizioneTipoPagamento;
        //    row.DataPagmento = data.HasValue ? data.Value.ToString("dd/MM/yyyy") : String.Empty;
        //    row.NumeroPagamento = riferimento;
        //    row.ImportoPagato = importoPagato;

        //    this._database.OneriDomanda.AcceptChanges();
        //}


        public void CreaOAggiornaDatiOnere(BaseDtoOfInt32String causale, ProvenienzaOnere provenienza, BaseDtoOfInt32String interventoOEndoOrigine, float importo, string note)
        {
            _log.DebugFormat("Inizializzazione dell'onere con id causale {0}, provenienza {1}, codice origine {2}, importo {3}, note {4}",
                              causale.Codice, provenienza, interventoOEndoOrigine.Codice, importo, note);

            var modalitaPagamentoDefault = Constants.ModalitaPagamento.GiaPagato;
            var statoPagamentoDefault = Constants.StatiPagamento.ProntoPerPagamentoOnline;
            var tipoOnere = provenienza == ProvenienzaOnere.Intervento ? "I" : "E";

            var row = this._database.OneriDomanda.FindByTipoOnereCodiceCausaleCodiceInterventoOEndoOrigine(tipoOnere, causale.Codice, interventoOEndoOrigine.Codice);

            if (row == null)
            {
                _log.Debug("L'onere non esiste nella datatable OneriDomanda e verrà aggiunto");

                this._database.OneriDomanda.AddOneriDomandaRow(
                    tipoOnere,
                    causale.Codice,
                    causale.Descrizione,
                    interventoOEndoOrigine.Codice,
                    interventoOEndoOrigine.Descrizione,
                    importo,
                    note,
                    false,
                    String.Empty,
                    String.Empty,
                    String.Empty,
                    String.Empty,
                    importo,
                    modalitaPagamentoDefault,
                    String.Empty,
                    statoPagamentoDefault,
                    String.Empty,
                    String.Empty,
                    String.Empty,
                    String.Empty,
                    DateTime.Now.ToString(Constants.DateTimeFormat));

                EliminaAttestazioneDiPagamento();
            }
            else
            {
                // Se l'importo è già stato pagato online non deve essere possibile modificare l'imoprto e la causale

                _log.Debug("L'onere esiste nella datatable OneriDomanda e verrà aggiornato");
                var modalitaPagamentoOnline = Constants.ModalitaPagamento.OnLine;
                var pagamentoOnlineRiuscito = Constants.StatiPagamento.PagamentoRiuscito;
                var pagamentoOnlineIniziato = Constants.StatiPagamento.PagamentoIniziato;

                var pagatoOnline = row.ModalitaPagamento == modalitaPagamentoOnline && (row.StatoPagamentoOnline == pagamentoOnlineIniziato || row.StatoPagamentoOnline == pagamentoOnlineRiuscito);

                if (!pagatoOnline && row.Importo != importo)
                {
                    // Se l'importo è cambiato elimino l'attestazione di pagamento esistente e
                    // annullo il pagamento già effettuato :P
                    row.Importo = importo;
                    row.ImportoPagato = importo;

                    EliminaAttestazioneDiPagamento();
                }

                if (row.IsImportoPagatoNull())
                {
                    row.ImportoPagato = importo;
                }

                row.TipoOnere = tipoOnere;
                row.CodiceCausale = causale.Codice;
                row.Causale = causale.Descrizione;
                row.CodiceInterventoOEndoOrigine = interventoOEndoOrigine.Codice;
                row.InterventoOEndoOrigine = interventoOEndoOrigine.Descrizione;
                if (row.IsModalitaPagamentoNull() || String.IsNullOrEmpty(row.ModalitaPagamento))
                {
                    row.ModalitaPagamento = modalitaPagamentoDefault;
                }

                row.Note = note;
            }
        }

        public void ImpostaEstremiPagamentoOneriNonPagatiOnline(IdOnere id, ModalitaPagamentoOnereEnum modalitaPagamento, EstremiPagamento estremiPagamento)
        {
            var nuovoStatoPagamento = Constants.StatiPagamento.PagamentoNonNecessario;
            var pagamentoOnlineRiuscito = Constants.StatiPagamento.PagamentoRiuscito;

            if (modalitaPagamento == ModalitaPagamentoOnereEnum.Online)
            {
                nuovoStatoPagamento = Constants.StatiPagamento.ProntoPerPagamentoOnline;
            }

            var tipoOnere = id.Provenienza == ProvenienzaOnere.Intervento ? "I" : "E";

            var row = this._database.OneriDomanda.FindByTipoOnereCodiceCausaleCodiceInterventoOEndoOrigine(tipoOnere, id.CodiceCausale, id.CodiceEndoOIntervento);

            // Se l'onere è già stato pagato online non posso alterare lo stato 
            if (row.StatoPagamentoOnline == pagamentoOnlineRiuscito)
            {
                return;
            }

            row.NonPagato = false;
            row.ModalitaPagamento = ((int)modalitaPagamento).ToString();
            row.CodiceTipoPagamento = estremiPagamento.TipoPagamento.Codice;
            row.DescrizioneTipoPagamento = estremiPagamento.TipoPagamento.Descrizione;
            row.DataPagmento = estremiPagamento.Data.HasValue ? estremiPagamento.Data.Value.ToString("dd/MM/yyyy HH:mm:ss") : String.Empty;
            row.NumeroPagamento = estremiPagamento.Riferimento;
            row.ImportoPagato = (float)estremiPagamento.ImportoPagato;
            row.StatoPagamentoOnline = nuovoStatoPagamento;

            this._database.OneriDomanda.AcceptChanges();
        }

        [Obsolete]
        public void AvviaPagamentoOneriOnline(string idNuovaOperazione, IEnumerable<OnereFrontoffice> oneriPerPagamentoOnline)
        {
            foreach (var onere in oneriPerPagamentoOnline)
            {
                var tipoOnere = onere.Provenienza == OnereFrontoffice.ProvenienzaOnereEnum.Endoprocedimento ? "E" : "I";
                var codiceCausale = onere.Causale.Codice;
                var endoOInt = onere.EndoOInterventoOrigine.Codice;

                var rigaOnere = this._database.OneriDomanda.FindByTipoOnereCodiceCausaleCodiceInterventoOEndoOrigine(tipoOnere, codiceCausale, endoOInt);

                if (rigaOnere == null)
                {
                    var errMsg = String.Format("Onere non trovato per tipoOnere: {0}, causale: {1}, endo o intervento: {2}", tipoOnere, codiceCausale, endoOInt);
                    throw new ArgumentException(errMsg);
                }

                rigaOnere.StatoPagamentoOnline = Constants.StatiPagamento.PagamentoIniziato;
                rigaOnere.IdPagamentoOnline = idNuovaOperazione;
                rigaOnere.IdPosizioneNodoPagamenti = String.Empty;
                rigaOnere.IUV = String.Empty;
            }

            this._database.AcceptChanges();
        }

        public void AnnullaPagamentiFalliti()
        {
            var righe = this._database
                            .OneriDomanda
                            .Where(x => x.StatoPagamentoOnline == Constants.StatiPagamento.ProntoPerPagamentoOnline ||
                                        x.StatoPagamentoOnline == Constants.StatiPagamento.PagamentoFallito);

            foreach (var r in righe)
            {
                r.StatoPagamentoOnline = Constants.StatiPagamento.ProntoPerPagamentoOnline;
                r.IdPagamentoOnline = String.Empty;
                r.UniqueId = String.Empty;
                r.IdPosizioneNodoPagamenti = String.Empty;
                r.IUV = String.Empty;
            }

            this._database.AcceptChanges();
        }

        public void AnnullaPagamentiByUniqueId(IEnumerable<string> uniqueIds)
        {
            var righe = this._database.OneriDomanda.Where(x => uniqueIds.Contains(x.UniqueId));

            foreach (var r in righe)
            {
                r.StatoPagamentoOnline = Constants.StatiPagamento.ProntoPerPagamentoOnline;
                r.IdPagamentoOnline = String.Empty;
                r.UniqueId = String.Empty;
                r.IdPosizioneNodoPagamenti = String.Empty;
                r.IUV = String.Empty;
            }

            this._database.AcceptChanges();
        }

        public void AnnullaPagamentiInCorso()
        {
            var righe = this._database.OneriDomanda
                                      .Where(x => x.StatoPagamentoOnline == Constants.StatiPagamento.ProntoPerPagamentoOnline ||
                                                    x.StatoPagamentoOnline == Constants.StatiPagamento.PagamentoIniziato);

            foreach (var r in righe)
            {
                r.StatoPagamentoOnline = Constants.StatiPagamento.ProntoPerPagamentoOnline;
                r.IdPagamentoOnline = String.Empty;
                r.UniqueId = String.Empty;
                r.IdPosizioneNodoPagamenti = String.Empty;
                r.IUV = String.Empty;
            }

            this._database.AcceptChanges();
        }

        public void ImpostaRiferimentiNodoPagamenti(string uniqueId, string idPosizioneNodoPagamenti, string iuv)
        {
            var onere = this._database.OneriDomanda.Where(x => x.UniqueId == uniqueId).FirstOrDefault();

            if (onere == null)
            {
                throw new ArgumentException($"Non è stato trovato nessun onere con uniqueId {uniqueId}");
            }

            onere.StatoPagamentoOnline = Constants.StatiPagamento.PagamentoIniziato;
            onere.IdPosizioneNodoPagamenti = idPosizioneNodoPagamenti;
            onere.IUV = iuv;

            this._database.AcceptChanges();
        }

        public IEnumerable<OnereFrontoffice> AvviaOperazioneDiPagamento(IGuidWrapperService guidService)
        {
            var oneriPronti = this._database
                      .OneriDomanda
                      .Where(x => x.ModalitaPagamento == Constants.ModalitaPagamento.OnLine &&
                                  x.StatoPagamentoOnline == Constants.StatiPagamento.ProntoPerPagamentoOnline)
                      .ToArray();

            if (!oneriPronti.Any())
            {
                throw new InvalidOperationException("La domanda non contiene oneri pagabili tramite pagamento online");
            }

            foreach (var r in oneriPronti)
            {
                r.StatoPagamentoOnline = Constants.StatiPagamento.PagamentoIniziato;
                r.IdPagamentoOnline = String.Empty;
                r.UniqueId = guidService.NewGuid().ToString();
                r.IdPosizioneNodoPagamenti = String.Empty;
                r.IUV = String.Empty;
            }

            this._database.AcceptChanges();

            return oneriPronti.Select(x => OnereFrontoffice.FromOneriRow(x));
        }



        public void PagamentoRiuscito(DateTime dataOraTransazione, string numeroOperazione, string idOrdine, string idTransazione, TipoPagamento tipoPagamento)
        {
            var righe = this._database
                 .OneriDomanda
                 .Where(x => (x.StatoPagamentoOnline == Constants.StatiPagamento.ProntoPerPagamentoOnline ||
                              x.StatoPagamentoOnline == Constants.StatiPagamento.PagamentoIniziato) &&
                              x.IdPagamentoOnline == numeroOperazione);

            foreach (var r in righe)
            {
                r.CodiceTipoPagamento = tipoPagamento.Codice;
                r.DescrizioneTipoPagamento = tipoPagamento.Descrizione;
                r.StatoPagamentoOnline = Constants.StatiPagamento.PagamentoRiuscito;
                r.IdPagamentoOnline = numeroOperazione;
                r.NumeroPagamento = idOrdine + "_" + idTransazione;
                r.DataPagmento = dataOraTransazione.ToString("dd/MM/yyyy");
            }

            this._database.AcceptChanges();
        }

        public void PagamentoRiuscitoDaNodoPagamenti(string uniqueId, string cfEnteCreditore, DateTime dataOraTransazione, TipoPagamento tipoPagamento)
        {
            var r = this._database.OneriDomanda.FirstOrDefault(x => x.UniqueId == uniqueId);

            if (r == null)
            {
                throw new ArgumentException($"Impossibile trovare una riga con uniqueId={uniqueId}");
            }

            r.CodiceTipoPagamento = tipoPagamento.Codice;
            r.DescrizioneTipoPagamento = tipoPagamento.Descrizione;
            r.StatoPagamentoOnline = Constants.StatiPagamento.PagamentoRiuscito;
            r.IdPagamentoOnline = "Non usare";
            r.NumeroPagamento = $"$RIF:{cfEnteCreditore}:{r.IdPosizioneNodoPagamenti}";
            r.DataPagmento = dataOraTransazione.ToString(Constants.DateTimeFormat);

            this._database.AcceptChanges();
        }

        public void PagamentoFallitoDaNodoPagamenti(string uniqueId)
        {
            var r = this._database.OneriDomanda.FirstOrDefault(x => x.UniqueId == uniqueId);

            if (r == null)
            {
                throw new ArgumentException($"Impossibile trovare una riga con uniqueId={uniqueId}");
            }

            r.CodiceTipoPagamento = String.Empty;
            r.DescrizioneTipoPagamento = String.Empty;
            r.StatoPagamentoOnline = Constants.StatiPagamento.PagamentoFallito;
            r.IdPagamentoOnline = "Non usare";
            r.NumeroPagamento = String.Empty;
            r.DataPagmento = String.Empty;

            this._database.AcceptChanges();
        }

        public void AggiornaStatoPagamentoNativo(string uniqueId, string statoPagamentoNativo)
        {
            var onere = this._database.OneriDomanda.FirstOrDefault(x => x.UniqueId == uniqueId);

            if (onere == null)
            {
                throw new ArgumentException($"Impossibile trovare una riga con uniqueId={uniqueId}");
            }

            onere.StatoPagamentoNativo = statoPagamentoNativo;
            onere.DataUltimaVerifica = DateTime.Now.ToString(Constants.DateTimeFormat);

            this._database.AcceptChanges();
        }

        /*
        public void AnnullaTuttiIPagamenti()
        {
            var statoProntoPerPagamento = ((int)StatoPagamentoOnereEnum.ProntoPerPagamentoOnline).ToString();

            var righe = this._database.OneriDomanda.Where(x => !String.IsNullOrEmpty(x.IdPagamentoOnline));

            foreach (var r in righe)
            {
                r.CodiceTipoPagamento = String.Empty;
                r.DescrizioneTipoPagamento = String.Empty;
                r.StatoPagamentoOnline = statoProntoPerPagamento;
                r.IdPagamentoOnline = String.Empty;
                r.NumeroPagamento = String.Empty;
                r.DataPagmento = String.Empty;
            }

            this._database.AcceptChanges();
        }
        */

        public void ForzaModificaIdPagamentoOnline(string numeroOperazione)
        {
            // var statoPagamentoRiuscito = Constants.StatiPagamento.PagamentoRiuscito;

            this._database
                .OneriDomanda
                .Where(x => x.StatoPagamentoOnline == Constants.StatiPagamento.ProntoPerPagamentoOnline ||
                            x.StatoPagamentoOnline == Constants.StatiPagamento.PagamentoIniziato)
                .ToList()
                .ForEach(x => x.IdPagamentoOnline = numeroOperazione);

            this._database.AcceptChanges();
        }

        public void ForzaEstremiPagamento(IdOnere id, ModalitaPagamentoOnereEnum modalitaPagamento, EstremiPagamento estremiPagamento)
        {
            var tipoOnere = id.Provenienza == ProvenienzaOnere.Intervento ? "I" : "E";

            var row = this._database.OneriDomanda.FindByTipoOnereCodiceCausaleCodiceInterventoOEndoOrigine(tipoOnere, id.CodiceCausale, id.CodiceEndoOIntervento);

            if (row == null)
                throw new InvalidOperationException(String.Format("impossibile trovare l'onere con id causale {1}, tipo onere {0} e codice endo/intervento {2}", tipoOnere, id.CodiceCausale, id.CodiceEndoOIntervento));

            row.NonPagato = false;
            row.ModalitaPagamento = ((int)modalitaPagamento).ToString();
            row.CodiceTipoPagamento = estremiPagamento.TipoPagamento.Codice;
            row.DescrizioneTipoPagamento = estremiPagamento.TipoPagamento.Descrizione;
            row.DataPagmento = estremiPagamento.Data.HasValue ? estremiPagamento.Data.Value.ToString("dd/MM/yyyy") : String.Empty;
            row.NumeroPagamento = estremiPagamento.Riferimento;
            row.ImportoPagato = (float)estremiPagamento.ImportoPagato;

            if (!String.IsNullOrEmpty(estremiPagamento.Riferimento))
            {
                if (modalitaPagamento == ModalitaPagamentoOnereEnum.GiaPagato || modalitaPagamento == ModalitaPagamentoOnereEnum.NonDovuto)
                {
                    row.StatoPagamentoOnline = Constants.StatiPagamento.PagamentoNonNecessario;
                }

                if (modalitaPagamento == ModalitaPagamentoOnereEnum.Online)
                {
                    row.StatoPagamentoOnline = Constants.StatiPagamento.PagamentoRiuscito;
                }
            }

            this._database.OneriDomanda.AcceptChanges();
        }
    }
}
