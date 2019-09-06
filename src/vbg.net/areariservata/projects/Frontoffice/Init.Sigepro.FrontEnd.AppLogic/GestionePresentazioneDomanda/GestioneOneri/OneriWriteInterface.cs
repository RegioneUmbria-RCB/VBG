using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.Sigepro.FrontEnd.AppLogic.ObjectSpace.PresentazioneIstanza;
using log4net;
using Init.Sigepro.FrontEnd.AppLogic.GestioneOneri;
using Init.Sigepro.FrontEnd.AppLogic.AreaRiservataService;

namespace Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneOneri
{
    public class OneriWriteInterface : IOneriWriteInterface
    {
        public static class Constants
        {
            public const string TipoOnereIntervento = "I";
            public const string TipoOnereEndo = "E";
        }

        ILog _log = LogManager.GetLogger(typeof(OneriWriteInterface));
        PresentazioneIstanzaDbV2 _database;

        public OneriWriteInterface(PresentazioneIstanzaDbV2 database)
        {
            this._database = database;
        }

        private int TotaleOneri()
        {
            return (int)(this._database.OneriDomanda.Sum(x => x.Importo) * 100.0f);
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

        public void ImpostaDichiarazioneAssenzaOneriDaPagare()
        {
            SetDichiarazioneAssenzaOneriDaPagare(true);
        }

        public void RimuoviDichiarazioneAssenzaOneriDaPagare()
        {
            SetDichiarazioneAssenzaOneriDaPagare(false);
        }


        public void EliminaOneriIntervento()
        {
            var righeDaEliminare = this._database.OneriDomanda.Where(x => x.TipoOnere == Constants.TipoOnereIntervento);

            foreach (var riga in righeDaEliminare)
                riga.Delete();

            EliminaAttestazioneDiPagamento();
        }

        public void EliminaOneriDaIdEndo(int idEndoProcedimento)
        {
            var righeDaEliminare = this._database.OneriDomanda.Where(x => x.TipoOnere == Constants.TipoOnereEndo && x.CodiceInterventoOEndoOrigine == idEndoProcedimento);

            foreach (var riga in righeDaEliminare)
                riga.Delete();

            EliminaAttestazioneDiPagamento();
        }

        public void EliminaOneriWhereCodiceCausaleIn(IEnumerable<int> listaIdDaEliminare)
        {
            var eliminaAttestazioneDiPagamento = false;
            var elementiDaEliminare = this._database.OneriDomanda.Where(x => listaIdDaEliminare.Contains(x.CodiceCausale));

            foreach (var onereDaEliminare in elementiDaEliminare.ToList())
            {
                this._database.OneriDomanda.RemoveOneriDomandaRow(onereDaEliminare);

                eliminaAttestazioneDiPagamento = true;
            }

            if (eliminaAttestazioneDiPagamento)
                EliminaAttestazioneDiPagamento();
        }


        public void AggiungiOSalvaOnereIntervento(int codiceCausale, string causale, int codiceInterventoOEndoOrigine, string interventoOEndoOrigine, ModalitaPagamentoOnereEnum modalitaPagamento, float importo, float importoPagato, string note)
        {
            AggiungiOSalvaOnere(Constants.TipoOnereIntervento, codiceCausale, causale, codiceInterventoOEndoOrigine, interventoOEndoOrigine, modalitaPagamento, importo, importoPagato, note);
        }

        public void AggiungiOSalvaOnereEndoprocedimento(int codiceCausale, string causale, int codiceInterventoOEndoOrigine, string interventoOEndoOrigine, ModalitaPagamentoOnereEnum modalitaPagamento, float importo, float importoPagato, string note)
        {
            AggiungiOSalvaOnere(Constants.TipoOnereEndo, codiceCausale, causale, codiceInterventoOEndoOrigine, interventoOEndoOrigine, modalitaPagamento, importo, importoPagato, note);
        }

        private void AggiungiOSalvaOnere(string tipoOnere, int codiceCausale, string causale, int codiceInterventoOEndoOrigine, string interventoOEndoOrigine, ModalitaPagamentoOnereEnum modalitaPagamento, float importo, float importoPagato, string note)
        {
            _log.DebugFormat("Aggiunta o solvataggio dell'onere con tipoOnere={0}, codiceCausale={1}, causale={2}, codiceInterventoOEndoOrigine={3}, interventoOEndoOrigine={4}, importo={5}, note={6}",
                              tipoOnere, codiceCausale, causale, codiceInterventoOEndoOrigine, interventoOEndoOrigine, importo, note);

            var strModalitaPagamento = ((int)modalitaPagamento).ToString();
            var statoPagamentoDefault = ((int)StatoPagamentoOnereEnum.ProntoPerPagamentoOnline).ToString();

            var row = this._database.OneriDomanda.FindByTipoOnereCodiceCausaleCodiceInterventoOEndoOrigine(tipoOnere, codiceCausale, codiceInterventoOEndoOrigine);

            if (row == null)
            {
                _log.Debug("L'onere non esiste nella datatable OneriDomanda e verrà aggiunto");

                this._database.OneriDomanda.AddOneriDomandaRow(tipoOnere, codiceCausale, causale, codiceInterventoOEndoOrigine, interventoOEndoOrigine, importo, note, false, String.Empty, String.Empty, string.Empty, String.Empty, importo, strModalitaPagamento, String.Empty, statoPagamentoDefault);

                EliminaAttestazioneDiPagamento();
            }
            else
            {
                _log.Debug("L'onere esiste nella datatable OneriDomanda e verrà aggiornato");

                if (row.Importo != importo)
                {
                    row.Importo = importo;
                    row.ImportoPagato = importoPagato;

                    EliminaAttestazioneDiPagamento();
                }

                if (row.IsImportoPagatoNull())
                {
                    row.ImportoPagato = importo;
                }

                row.TipoOnere = tipoOnere;
                row.CodiceCausale = codiceCausale;
                row.Causale = causale;
                row.CodiceInterventoOEndoOrigine = codiceInterventoOEndoOrigine;
                row.InterventoOEndoOrigine = interventoOEndoOrigine;
                row.ModalitaPagamento = strModalitaPagamento;

                row.Note = note;

            }
        }

        public void EliminaOneriWhereCodiceCausaleNotIn(IEnumerable<IdentificativoOnereSelezionato> listaId)
        {
            var eliminaAttestazioneDiPagamento = false;
            // var elementiDaEliminare = this._database.OneriDomanda.Where(x => !listaId.Contains(x.CodiceCausale));
            var condition = new ListaIdOneriContiene(listaId);

            var elementiDaEliminare = this._database.OneriDomanda.Where(x => !condition.IsSatisfiedBy(x)).ToList();

            foreach (var onereDaEliminare in elementiDaEliminare)
            {
                this._database.OneriDomanda.RemoveOneriDomandaRow(onereDaEliminare);

                eliminaAttestazioneDiPagamento = true;
            }

            if (eliminaAttestazioneDiPagamento)
                EliminaAttestazioneDiPagamento();
        }


        public void ImpostaEstremiPagamentoOnereIntervento(int codiceCausale, int codiceIntervento, string codiceTipoPagamento, string descrizioneTipoPagamento, DateTime? data, string riferimento, float importoPagato)
        {
            ImpostaEstremiPagamento(Constants.TipoOnereIntervento, codiceCausale, codiceIntervento, codiceTipoPagamento, descrizioneTipoPagamento, data, riferimento, importoPagato);
        }

        public void ImpostaEstremiPagamentoOnereEndo(int codiceCausale, int codiceEndo, string codiceTipoPagamento, string descrizioneTipoPagamento, DateTime? data, string riferimento, float importoPagato)
        {
            ImpostaEstremiPagamento(Constants.TipoOnereEndo, codiceCausale, codiceEndo, codiceTipoPagamento, descrizioneTipoPagamento, data, riferimento, importoPagato);
        }

        public void CancellaEstremiPagamento()
        {
            foreach (var r in this._database.OneriDomanda)
            {
                r.NonPagato = true;
                r.CodiceTipoPagamento = String.Empty;
                r.DescrizioneTipoPagamento = String.Empty;
                r.DataPagmento = String.Empty;
                r.NumeroPagamento = String.Empty;
                r.ImportoPagato = r.Importo;
            }

            this._database.OneriDomanda.AcceptChanges();
        }

        #endregion

        private void ImpostaEstremiPagamento(string tipoOnere, int codiceCausale, int codiceInterventoOEndoOrigine, string codiceTipoPagamento, string descrizioneTipoPagamento, DateTime? data, string riferimento, float importoPagato)
        {
            var row = this._database.OneriDomanda.FindByTipoOnereCodiceCausaleCodiceInterventoOEndoOrigine(tipoOnere, codiceCausale, codiceInterventoOEndoOrigine);

            if (row == null)
                throw new InvalidOperationException(String.Format("impossibile trovare l'onere con id causale {1}, tipo onere {0} e codice endo/intervento {2}", tipoOnere, codiceCausale, codiceInterventoOEndoOrigine));

            row.NonPagato = false;
            row.CodiceTipoPagamento = codiceTipoPagamento;
            row.DescrizioneTipoPagamento = descrizioneTipoPagamento;
            row.DataPagmento = data.HasValue ? data.Value.ToString("dd/MM/yyyy") : String.Empty;
            row.NumeroPagamento = riferimento;
            row.ImportoPagato = importoPagato;

            this._database.OneriDomanda.AcceptChanges();
        }


        public void InizializzaOnere(BaseDtoOfInt32String causale, ProvenienzaOnere provenienza, BaseDtoOfInt32String interventoOEndoOrigine, float importo, string note)
        {
            _log.DebugFormat("Inizializzazione dell'onere con id causale {0}, provenienza {1}, codice origine {2}, importo {3}, note {4}",
                              causale.Codice, provenienza, interventoOEndoOrigine.Codice, importo, note);

            var modalitaPagamentoDefault = ((int)ModalitaPagamentoOnereEnum.GiaPagato).ToString();
            var statoPagamentoDefault = ((int)StatoPagamentoOnereEnum.ProntoPerPagamentoOnline).ToString();
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
                    statoPagamentoDefault);

                EliminaAttestazioneDiPagamento();
            }
            else
            {
                // Se l'importo è già stato pagato online non deve essere possibile modificare l'imoprto e la causale

                _log.Debug("L'onere esiste nella datatable OneriDomanda e verrà aggiornato");
                var modalitaPagamentoOnline = ((int)ModalitaPagamentoOnereEnum.Online).ToString();
                var pagamentoOnlineRiuscito = ((int)StatoPagamentoOnereEnum.PagamentoRiuscito).ToString();
                var pagamentoOnlineIniziato = ((int)StatoPagamentoOnereEnum.PagamentoIniziato).ToString();

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

        public void ImpostaEstremiPagamento(IdOnere id, ModalitaPagamentoOnereEnum modalitaPagamento, EstremiPagamento estremiPagamento)
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
            row.ImportoPagato = estremiPagamento.ImportoPagato;

            this._database.OneriDomanda.AcceptChanges();

        }


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

                rigaOnere.StatoPagamentoOnline = ((int)StatoPagamentoOnereEnum.PagamentoIniziato).ToString();
                rigaOnere.IdPagamentoOnline = idNuovaOperazione;
            }

            this._database.AcceptChanges();
        }


        public void AnnullaPagamento(string numeroOperazione)
        {
            var statoProntoPerPagamento = ((int)StatoPagamentoOnereEnum.ProntoPerPagamentoOnline).ToString();
            var statoPagamentoInCorso = ((int)StatoPagamentoOnereEnum.PagamentoIniziato).ToString();

            var righe = this._database.OneriDomanda.Where(x => (x.StatoPagamentoOnline == statoProntoPerPagamento || x.StatoPagamentoOnline == statoPagamentoInCorso) && x.IdPagamentoOnline == numeroOperazione);

            foreach (var r in righe)
            {
                r.StatoPagamentoOnline = statoProntoPerPagamento;
                r.IdPagamentoOnline = String.Empty;
            }

            this._database.AcceptChanges();
        }


        public void PagamentoRiuscito(DateTime dataOraTransazione, string numeroOperazione, string idOrdine, string idTransazione, TipoPagamento tipoPagamento)
        {
            var statoProntoPerPagamento = ((int)StatoPagamentoOnereEnum.ProntoPerPagamentoOnline).ToString();
            var statoPagamentoInCorso = ((int)StatoPagamentoOnereEnum.PagamentoIniziato).ToString();
            var statoPagamentoRiuscito = ((int)StatoPagamentoOnereEnum.PagamentoRiuscito).ToString();
            

            var righe = this._database.OneriDomanda.Where(x => (x.StatoPagamentoOnline == statoProntoPerPagamento || x.StatoPagamentoOnline == statoPagamentoInCorso) && x.IdPagamentoOnline == numeroOperazione);

            foreach (var r in righe)
            {
                r.CodiceTipoPagamento = tipoPagamento.Codice;
                r.DescrizioneTipoPagamento = tipoPagamento.Descrizione;
                r.StatoPagamentoOnline = statoPagamentoRiuscito;
                r.IdPagamentoOnline = numeroOperazione;
                r.NumeroPagamento = idOrdine + "_" + idTransazione;
                r.DataPagmento = dataOraTransazione.ToString("dd/MM/yyyy");
            }

            this._database.AcceptChanges();
        }


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
    }
}
