using Init.Sigepro.FrontEnd.AppLogic.GestioneOneri;
using Init.Sigepro.FrontEnd.AppLogic.ObjectSpace.PresentazioneIstanza;
using log4net;
using System;
using System.Collections.Generic;
using System.Linq;

namespace Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneOneri
{
    public class OneriReadInterface : IOneriReadInterface
    {
        private readonly ILog _log = LogManager.GetLogger(typeof(OneriReadInterface));
        private readonly PresentazioneIstanzaDbV2 _database;
        private List<OnereFrontoffice> _oneri;
        private int _importoAttestazionePagamento = 0;

        public OneriReadInterface(PresentazioneIstanzaDbV2 database)
        {
            this._database = database;

            this.PreparaOneri();
            this.PreparaAttestazioneDiPagamento();

            if (((int)(this.Totale * 100.0m)) != this._importoAttestazionePagamento)
                this.AttestazioneDiPagamento = AttestazioneDiPagamento.NonPresente;
        }

        private void PreparaAttestazioneDiPagamento()
        {
            // this._log.DebugFormat("PreparaAttestazioneDiPagamento");

            this.AttestazioneDiPagamento = AttestazioneDiPagamento.NonPresente;

            if (this._database.OneriAttestazionePagamento.Count == 0)
            {
                this._log.DebugFormat("this._database.OneriAttestazionePagamento.Count == 0");

                return;
            }

            var row = this._database.OneriAttestazionePagamento[0];

            this._importoAttestazionePagamento = row.Importo;

            // this._log.DebugFormat("Importo attestazione di pagamento = {0}", this._importoAttestazionePagamento);
            // this._log.DebugFormat("row.IsIdAllegatoNull()  = {0}", row.IsIdAllegatoNull());

            if (!row.IsIdAllegatoNull())
            {
                // this._log.DebugFormat("row.IdAllegato = {0}", row.IdAllegato);

                var idAllegato = row.IdAllegato;
                var allegato = this._database.Allegati.FindById(idAllegato);

                this.AttestazioneDiPagamento = new AttestazioneDiPagamento(true, allegato.NomeFile, allegato.CodiceOggetto, allegato.FirmatoDigitalmente);
            }

            this.DichiaraDiNonAvereOneriDaPagare = false;

            if (!row.IsDichiaraDiNonAvereOneriDaPagareNull())
                this.DichiaraDiNonAvereOneriDaPagare = row.DichiaraDiNonAvereOneriDaPagare;
        }

        private void PreparaOneri()
        {
            this._oneri = new List<OnereFrontoffice>();

            foreach (var onere in this._database.OneriDomanda.Cast<PresentazioneIstanzaDbV2.OneriDomandaRow>())
                this._oneri.Add(OnereFrontoffice.FromOneriRow(onere));

            this.Totale = 0.0m;
            this.TotalePagato = 0.0m;

            this._oneri.ForEach((Action<OnereFrontoffice>)(x =>
            {
                this.Totale += x.Importo;

                if (x.EstremiPagamento != null)
                    this.TotalePagato += x.ImportoPagato;

            }));
        }

        public IEnumerable<OnereFrontoffice> Oneri { get { return this._oneri; } }

        public AttestazioneDiPagamento AttestazioneDiPagamento { get; private set; } = AttestazioneDiPagamento.NonPresente;

        public bool DichiaraDiNonAvereOneriDaPagare { get; private set; } = false;

        public decimal Totale { get; private set; } = 0.0m;

        public decimal TotalePagato { get; private set; } = 0.0m;

        #region IOneriReadInterface Members

        public IEnumerable<OnereFrontoffice> OneriIntervento
        {
            get { return this._oneri.Where(x => x.Provenienza == OnereFrontoffice.ProvenienzaOnereEnum.Intervento); }
        }

        public IEnumerable<OnereFrontoffice> OneriEndoprocedimenti
        {
            get { return this._oneri.Where(x => x.Provenienza == OnereFrontoffice.ProvenienzaOnereEnum.Endoprocedimento); }
        }

        #endregion


        public IEnumerable<OnereFrontoffice> GetOneriConPagamentoOnline()
        {
            return this.Oneri.Where(x => x.ModalitaPagamento == ModalitaPagamentoOnereEnum.Online);
        }

        public IEnumerable<OnereFrontoffice> GetOneriOnlineProntiPerPagamento()
        {
            return this.Oneri.Where(x => x.ModalitaPagamento == ModalitaPagamentoOnereEnum.Online && x.StatoPagamento == StatoPagamentoOnereEnum.ProntoPerPagamentoOnline);
        }

        public IEnumerable<OnereFrontoffice> GetOneriOnlineConPagamentoAvviato()
        {
            return this.Oneri.Where(x => x.ModalitaPagamento == ModalitaPagamentoOnereEnum.Online && x.StatoPagamento == StatoPagamentoOnereEnum.PagamentoIniziato);
        }

        public IEnumerable<OnereFrontoffice> GetOneriOnlineConPagamentoFallito()
        {
            return this.Oneri.Where(x => x.ModalitaPagamento == ModalitaPagamentoOnereEnum.Online && x.StatoPagamento == StatoPagamentoOnereEnum.PagamentoFallito);
        }

        public IEnumerable<OnereFrontoffice> GetOneriOnlineConPagamentoRiuscito()
        {
            return this.Oneri.Where(x => x.ModalitaPagamento == ModalitaPagamentoOnereEnum.Online && x.StatoPagamento == StatoPagamentoOnereEnum.PagamentoRiuscito);
        }

        [Obsolete]
        public IEnumerable<OnereFrontoffice> GetOneriDaIdOperazione(string idOperazione)
        {
            return this.Oneri.Where(x => x.IdOperazionePagamento == idOperazione);
        }

        public IEnumerable<string> GetWarningsPagamenti()
        {
            if (this.GetOneriOnlineConPagamentoAvviato().Any())
                yield return "Operazione di pagamento in sospeso";

            if (this.GetOneriOnlineConPagamentoRiuscito().Any())
                yield return "Pagamento effettuato";
        }
    }
}
