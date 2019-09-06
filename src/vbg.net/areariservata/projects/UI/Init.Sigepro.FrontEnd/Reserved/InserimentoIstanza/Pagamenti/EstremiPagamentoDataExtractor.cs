using Init.Sigepro.FrontEnd.AppLogic.GestioneOneri;
using Init.Utils.Web.UI;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI.WebControls;

namespace Init.Sigepro.FrontEnd.Reserved.InserimentoIstanza.Pagamenti
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

        Repeater _repeaterIntervento;
        Repeater _repeaterEndo;
        List<string> _errori = new List<string>();
        List<OnerePagato> _estremi = new List<OnerePagato>();

        public EstremiPagamentoDataExtractor(Repeater repeaterIntervento, Repeater repeaterEndo)
        {
            this._repeaterIntervento = repeaterIntervento;
            this._repeaterEndo = repeaterEndo;
        }

        public ExtractionResult EstraiDati(bool ignoraErrori)
        {
            this._errori = new List<string>();
            this._estremi = new List<OnerePagato>();

            EstraiDatiDaRepeater(this._repeaterIntervento, ProvenienzaOnere.Intervento, ignoraErrori);
            EstraiDatiDaRepeater(this._repeaterEndo, ProvenienzaOnere.Endo, ignoraErrori);

            return new ExtractionResult(this._estremi, this._errori);
        }

        private void EstraiDatiDaRepeater(Repeater repeater, ProvenienzaOnere provenienza, bool ignoraErrori)
        {
            foreach (var item in repeater.Items.Cast<RepeaterItem>())
            {
                var errori = new List<string>();

                var hidIdOnere = (HiddenField)item.FindControl("hidIdOnere");
                var hidCodiceEndoOIntervento = (HiddenField)item.FindControl("hidCodiceEndoOIntervento");
                var ddlTipoPagamento = (DropDownList)item.FindControl("ddlTipoPagamento");
                var txtDataPagamento = (DateTextBox)item.FindControl("txtDataPagamento");
                var txtNumeroOperazione = (TextBox)item.FindControl("txtNumeroOperazione");
                var lblNomeOnere = (Literal)item.FindControl("lblNomeOnere");
                var txtImporto = (FloatTextBox)item.FindControl("txtImporto");
                var ddlModalitaPagamento = (DropDownList)item.FindControl("ddlPagamento");
                
                if (String.IsNullOrEmpty(ddlModalitaPagamento.SelectedValue))
                {
                    this._errori.Add("Specificare una modalità di pagamento per l'onere \"" + lblNomeOnere.Text + "\"");
                    continue;
                }

                var modalitaPagamento = ddlModalitaPagamento == null ? ModalitaPagamentoOnereEnum.GiaPagato : (ModalitaPagamentoOnereEnum)Convert.ToInt32(ddlModalitaPagamento.SelectedValue);

                if (modalitaPagamento == ModalitaPagamentoOnereEnum.GiaPagato)
                {

                    if (String.IsNullOrEmpty(ddlTipoPagamento.SelectedValue.Trim()))
                        errori.Add("Specificare un tipo di pagamento per l'onere \"" + lblNomeOnere.Text + "\"");

                    if (String.IsNullOrEmpty(txtDataPagamento.Text.Trim()))
                        errori.Add("Specificare una data di pagamento per l'onere \"" + lblNomeOnere.Text + "\"");

                    if (String.IsNullOrEmpty(txtNumeroOperazione.Text.Trim()))
                        errori.Add("Specificare i riferimenti del pagamento per l'onere \"" + lblNomeOnere.Text + "\"");
                }

                if (errori.Count > 0)
                {
                    this._errori.AddRange(errori);

                    if (!ignoraErrori)
                        continue;
                }

                var idEndoOIntervento = Convert.ToInt32(hidCodiceEndoOIntervento.Value);
                var idOnere = Convert.ToInt32(hidIdOnere.Value);
                var tipoPagamento = new TipoPagamento(ddlTipoPagamento.SelectedValue, ddlTipoPagamento.SelectedItem.Text);
                var data = txtDataPagamento.DateValue;
                var numero = txtNumeroOperazione.Text;
                var importo = txtImporto.ValoreFloat;

                var o = new OnerePagato(
                        new IdOnere(provenienza, idOnere, idEndoOIntervento),
                        modalitaPagamento,
                        new EstremiPagamento(tipoPagamento, data, numero, importo)
                );

                this._estremi.Add(o);
            }
        }
    }

}