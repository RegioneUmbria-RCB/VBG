using DocumentFormat.OpenXml.Bibliography;
using DocumentFormat.OpenXml.Vml;
using Init.Sigepro.FrontEnd.AppLogic.GestioneOneri;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneOneri;

namespace AreaRiservataCore.Pages.InserimentoIstanza.Pagamenti
{
    public class EstremiPagamentoDataExtractor
    {
        public class ExtractionResult
        {
            public IEnumerable<OnerePagato> Estremi { get; private set; }
            public IEnumerable<string> Errori { get; private set; }
            public ExtractionResult(IEnumerable<OnerePagato> estremi, IEnumerable<string> errori)
            {
                this.Estremi = estremi;
                this.Errori = errori;
            }
        }

        IEnumerable<RigaPagamentiModel> _righeIntervento;
        IEnumerable<RigaPagamentiModel> _righeEndo;
        List<string> _errori = new List<string>();
        List<OnerePagato> _estremi = new List<OnerePagato>();

        public EstremiPagamentoDataExtractor(PagamentiModel model)
        {
            this._righeIntervento = model.RowsInterventi;
            this._righeEndo = model.RowsEndo;
        }

        public ExtractionResult EstraiDati(bool ignoraErrori)
        {
            this._errori = new List<string>();
            this._estremi = new List<OnerePagato>();

            EstraiDatiDaRepeater(this._righeIntervento, ProvenienzaOnere.Intervento, ignoraErrori);
            EstraiDatiDaRepeater(this._righeEndo, ProvenienzaOnere.Endo, ignoraErrori);

            return new ExtractionResult(this._estremi, this._errori);
        }

        private void EstraiDatiDaRepeater(IEnumerable<RigaPagamentiModel> rows, ProvenienzaOnere provenienza, bool ignoraErrori)
        {
            if (rows == null || rows.Count() == 0)
                return;

            foreach (var r in rows)
            {
                var errori = new List<string>();

                var hidIdOnere = r.IdOnere;
                var hidCodiceEndoOIntervento = r.CodiceEndoOIntervento;
                var ddlTipoPagamento = r.TipoPagamento;
                var txtDataPagamento = r.Data;
                var txtNumeroOperazione = r.Riferimento;
                var lblNomeOnere = r.DescrizioneCausale;
                var txtImporto = r.ImportoPagato.GetValueOrDefault(0);
                var ddlModalitaPagamento = r.PagamentoCompletato;

                if (String.IsNullOrEmpty(ddlModalitaPagamento?.Value))
                {
                    this._errori.Add($"Specificare una modalità di pagamento per l'onere \" {lblNomeOnere}\"");
                    continue;
                }

                var modalitaPagamento = ddlModalitaPagamento == null ? ModalitaPagamentoOnereEnum.GiaPagato : (ModalitaPagamentoOnereEnum)Convert.ToInt32(ddlModalitaPagamento.Value);

                if (modalitaPagamento == ModalitaPagamentoOnereEnum.GiaPagato)
                {

                    if (String.IsNullOrEmpty(ddlTipoPagamento?.Codice))
                        errori.Add($"Specificare un tipo di pagamento per l'onere \"{lblNomeOnere}\"");

                    if (txtDataPagamento == null)
                        errori.Add($"Specificare una data di pagamento per l'onere \"{lblNomeOnere}\"");

                    if (String.IsNullOrEmpty(txtNumeroOperazione?.Trim()))
                        errori.Add($"Specificare i riferimenti del pagamento per l'onere \"{lblNomeOnere}\"");
                }

                if (errori.Count > 0)
                {
                    this._errori.AddRange(errori);

                    if (!ignoraErrori)
                        continue;
                }

                var idEndoOIntervento = hidCodiceEndoOIntervento != null ? hidCodiceEndoOIntervento.Value : 0;
                var idOnere = hidIdOnere != null ? hidIdOnere.Value : 0;
                var tipoPagamento = new TipoPagamento(ddlTipoPagamento?.Codice, ddlTipoPagamento?.Descrizione);
                //var data = txtDataPagamento == null ?
                //            (DateTime?)null :
                //            DateTime.ParseExact(txtDataPagamento, "yyyy-MM-dd", null);
                //var numero = txtNumeroOperazione?.Text;
                //var importo = (decimal)txtImporto.ValoreFloat;

                var o = new OnerePagato(
                        new IdOnere(provenienza, idOnere, idEndoOIntervento),
                        lblNomeOnere,
                        modalitaPagamento,
                        new EstremiPagamento(tipoPagamento, txtDataPagamento, txtNumeroOperazione, txtImporto)
                );

                this._estremi.Add(o);
            }
        }
    }
}
