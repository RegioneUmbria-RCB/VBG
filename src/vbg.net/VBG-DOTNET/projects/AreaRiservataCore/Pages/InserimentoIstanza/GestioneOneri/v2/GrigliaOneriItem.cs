using Init.Sigepro.FrontEnd.AppLogic.GestioneOneri;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneOneri;
using System.ComponentModel.DataAnnotations;
using VBG.BlazorComponentsLibrary.EditFormComponents.DropDown;

namespace AreaRiservataCore.Pages.InserimentoIstanza.GestioneOneri.v2
{
    public class GrigliaOneriItem
    {
        private decimal _importoPagato = 0;
        private DropDownItem? _modalitaPagamento;

        required public OnereFrontoffice.ProvenienzaOnereEnum Provenienza { get; init; }

        [Required]
        public DropDownItem? ModalitaPagamento
        {
            get => this._modalitaPagamento;
            set
            {
                this.AggiornaModalitaPagamento(value, true);
            }
        }

        public ModalitaPagamentoOnereEnum ModalitaPagamentoAsEnum { get; private set; }

        public bool ModalitaPagamentoNonImpostata => this.ModalitaPagamentoAsEnum == ModalitaPagamentoOnereEnum.NonImpostata;

        public int CodiceCausale { get; set; } = -1;
        public string Causale { get; set; } = String.Empty;
        public int CodiceEndoOInterventoOrigine { get; set; } = -1;
        public string EndoOInterventoOrigine { get; set; } = String.Empty;
        public string Note { get; set; } = String.Empty;

        public DateTime? DataPagamento { get; set; }
        [StringLength(100)]
        public string RiferimentoPagamento { get; set; } = String.Empty;
        public decimal Importo { get; init; } = 0;

        public decimal ImportoPagato
        {
            get => this._importoPagato;
            set
            {
                value = Math.Max(value, 0.0m);

                this._importoPagato = value;

                this.OnImportoPagatoModificato?.Invoke();
            }
        }
        public bool PagamentoCompletato { get; set; }
        public TipoPagamento? TipoPagamento { get; set; }

        public Action? OnImportoPagatoModificato { get; set; }
        public Action<GrigliaOneriItem>? OnModalitaPagamentoModificata { get; set; }

        internal void SetModalitaPagamentoNoCallback(DropDownItem? modalitaPagamento)
        {
            this.AggiornaModalitaPagamento(modalitaPagamento, false);
        }

        private void AggiornaModalitaPagamento(DropDownItem? value, bool invocaChangedCallback)
        {
            var oldVal = this._modalitaPagamento?.Value ?? "";
            this._modalitaPagamento = value;

            var newVal = this._modalitaPagamento?.Value ?? "";

            this.ModalitaPagamentoAsEnum = ModalitaPagamentoOnereEnum.NonImpostata;

            if (Enum.TryParse<ModalitaPagamentoOnereEnum>(newVal, out var modalitaEnum))
            {
                this.ModalitaPagamentoAsEnum = modalitaEnum;
            }

            if (newVal != oldVal)
            {

                if (this.ModalitaPagamentoAsEnum == ModalitaPagamentoOnereEnum.NonDovuto)
                {
                    this.ImportoPagato = 0.0m;
                    this.TipoPagamento = null;
                    this.RiferimentoPagamento = String.Empty;
                    this.DataPagamento = null;
                }

                //if (this.ModalitaPagamentoAsEnum == ModalitaPagamentoOnereEnum.Online)
                //{
                //    this.ImportoPagato = this.Importo;
                //    this.TipoPagamento = null;
                //    this.RiferimentoPagamento = String.Empty;
                //    this.DataPagamento = null;
                //}

                if (this.ModalitaPagamentoAsEnum == ModalitaPagamentoOnereEnum.GiaPagato)
                {
                    if (this.ImportoPagato == 0.0m)
                    {
                        this.ImportoPagato = this.Importo;
                    }
                }

                if (invocaChangedCallback)
                {
                    this.OnModalitaPagamentoModificata?.Invoke(this);
                }
            }
        }

        public IEnumerable<string> Errori
        {
            get
            {
                if (this.ModalitaPagamentoAsEnum == ModalitaPagamentoOnereEnum.NonImpostata)
                {
                    yield return "Selezionare una modalità di pagamento";
                }

                if (this.ModalitaPagamentoAsEnum == ModalitaPagamentoOnereEnum.GiaPagato)
                {
                    if (this.TipoPagamento is null || !this.DataPagamento.HasValue || String.IsNullOrEmpty(this.RiferimentoPagamento))
                    {
                        yield return "Specificare gli estremi del pagamento";
                    }
                }

                if (this.ModalitaPagamentoAsEnum != ModalitaPagamentoOnereEnum.NonDovuto && this.ImportoPagato == 0.0m)
                {
                    yield return "Verificare l'importo da pagare";
                }
            }
        }
    }
}
