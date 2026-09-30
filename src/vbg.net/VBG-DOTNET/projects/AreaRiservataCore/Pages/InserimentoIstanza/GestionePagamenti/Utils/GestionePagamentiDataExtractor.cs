using AreaRiservataCore.Pages.InserimentoIstanza.GestionePagamenti.Components;
using Init.Sigepro.FrontEnd.AppLogic.GestioneOneri;
using static AreaRiservataCore.Pages.InserimentoIstanza.Pagamenti.EstremiPagamentoDataExtractor;

namespace AreaRiservataCore.Pages.InserimentoIstanza.GestionePagamenti.Utils
{
    public class GestionePagamentiDataExtractor
    {
        private readonly IEnumerable<GrigliaOneriItem> _oneriIntervento;
        private readonly IEnumerable<GrigliaOneriItem> _oneriEndo;
        private readonly ITipiPagamentoService _tipiPagamentoService;
        private List<string> _errori = new List<string>();
        private List<OnerePagato> _estremi = new List<OnerePagato>();

        public GestionePagamentiDataExtractor(IEnumerable<GrigliaOneriItem> oneriIntervento, IEnumerable<GrigliaOneriItem> oneriEndo, ITipiPagamentoService tipiPagamentoService)
        {
            this._oneriIntervento = oneriIntervento ?? throw new ArgumentNullException(nameof(oneriIntervento));
            this._oneriEndo = oneriEndo ?? throw new ArgumentNullException(nameof(oneriEndo));
            this._tipiPagamentoService = tipiPagamentoService;
        }

        public ExtractionResult EstraiDati(bool ignoraErrori)
        {
            this._errori = new List<string>();
            this._estremi = new List<OnerePagato>();

            this.EstraiDatiDaRepeater(this._oneriIntervento, ProvenienzaOnere.Intervento, ignoraErrori);
            this.EstraiDatiDaRepeater(this._oneriEndo, ProvenienzaOnere.Endo, ignoraErrori);

            return new ExtractionResult(this._estremi, this._errori);
        }

        private void EstraiDatiDaRepeater(IEnumerable<GrigliaOneriItem> oneri, ProvenienzaOnere provenienza, bool ignoraErrori)
        {
            if (!oneri?.Any() ?? true)
                return;

            foreach (var r in oneri)
            {
                var errori = new List<string>();

                var codiceCausale = r.CodiceCausale;
                var idEndoOIntervento = r.CodiceEndoOInterventoOrigine;
                var idTipoPagamento = r.CodiceTipoPagamento;
                var dataPagamento = r.DataPagamento;
                var riferimentoPagamento = r.RiferimentoPagamento;
                var causale = r.Causale;
                var importoPagato = r.ImportoPagato;
                var modalitaPagamento = r.ModalitaPagamento;
                /*
                if (String.IsNullOrEmpty(idModalitaPagamento))
                {
                    this._errori.Add($"Specificare una modalità di pagamento per l'onere \" {causale}\"");
                    continue;
                }
                */
                // var modalitaPagamento = (ModalitaPagamentoOnereEnum)Convert.ToInt32(idModalitaPagamento);//.GetValueOrDefault() == null ? ModalitaPagamentoOnereEnum.GiaPagato : (ModalitaPagamentoOnereEnum)Convert.ToInt32(idModalitaPagamento);

                if (modalitaPagamento == ModalitaPagamentoOnereEnum.GiaPagato)
                {

                    if (String.IsNullOrEmpty(idTipoPagamento))
                        errori.Add($"Specificare un tipo di pagamento per l'onere \"{causale}\"");

                    if (!dataPagamento.HasValue)
                        errori.Add($"Specificare una data di pagamento per l'onere \"{causale}\"");

                    if (String.IsNullOrEmpty(riferimentoPagamento?.Trim()))
                        errori.Add($"Specificare i riferimenti del pagamento per l'onere \"{causale}\"");
                }

                if (errori.Count > 0)
                {
                    this._errori.AddRange(errori);

                    if (!ignoraErrori)
                        continue;
                }

                // var idEndoOIntervento = idEndoOIntervento != null ? idEndoOIntervento.Value : 0;
                // var idOnere = codiceCausale != null ? codiceCausale.Value : 0;
                var tipoPagamento = this._tipiPagamentoService.GetTipoPagamentoById(idTipoPagamento) ?? new TipoPagamento();
                //var data = txtDataPagamento == null ?
                //            (DateTime?)null :
                //            DateTime.ParseExact(txtDataPagamento, "yyyy-MM-dd", null);
                //var numero = txtNumeroOperazione?.Text;
                //var importo = (decimal)txtImporto.ValoreFloat;

                var o = new OnerePagato(
                        new IdOnere(provenienza, codiceCausale, idEndoOIntervento),
                        causale,
                        modalitaPagamento,
                        new EstremiPagamento(tipoPagamento, dataPagamento, riferimentoPagamento, importoPagato)
                );

                this._estremi.Add(o);
            }
        }
    }
}
