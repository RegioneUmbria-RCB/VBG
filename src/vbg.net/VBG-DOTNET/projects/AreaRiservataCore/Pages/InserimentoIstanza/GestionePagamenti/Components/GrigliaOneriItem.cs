using Init.Sigepro.FrontEnd.AppLogic.GestioneOneri;
using Init.Sigepro.FrontEnd.AppLogic.GestionePresentazioneDomanda.GestioneOneri;
using System.ComponentModel.DataAnnotations;

namespace AreaRiservataCore.Pages.InserimentoIstanza.GestionePagamenti.Components
{
    public class GrigliaOneriItem
    {
        private decimal _importoPagato = 0;
        private ModalitaPagamentoOnereEnum _modalitaPagamento;

        public static GrigliaOneriItem FromOnereFrontoffice(OnereFrontoffice x)
        {
            return new GrigliaOneriItem
            {
                ModalitaPagamento = x.ModalitaPagamento,
                CodiceCausale = x.Causale.Codice,
                Causale = x.Causale.Descrizione,
                CodiceEndoOInterventoOrigine = x.EndoOInterventoOrigine.Codice,
                EndoOInterventoOrigine = x.EndoOInterventoOrigine?.ToString() ?? "Non definito",
                Note = x.Note,
                DataPagamento = x.EstremiPagamento?.Data,
                RiferimentoPagamento = x.EstremiPagamento == null ? String.Empty : x.EstremiPagamento.Riferimento,
                Importo = x.Importo,
                ImportoPagato = x.ImportoPagato,
                PagamentoCompletato = x.StatoPagamento == StatoPagamentoOnereEnum.PagamentoRiuscito,
                CodiceTipoPagamento = x.EstremiPagamento?.TipoPagamento?.Codice ?? "",
                DescrizioneTipoPagamento = x.EstremiPagamento?.TipoPagamento.Descrizione ?? ""
            };
        }

        [Required]
        public ModalitaPagamentoOnereEnum ModalitaPagamento
        {
            get => this._modalitaPagamento;
            set
            {
                this._modalitaPagamento = value;

                if (this.OnModalitaPagamentoModificata is not null)
                {
                    this.OnModalitaPagamentoModificata(this);
                }
            }
        }

        public int CodiceCausale { get; set; } = -1;
        public string Causale { get; set; } = String.Empty;
        public int CodiceEndoOInterventoOrigine { get; set; } = -1;
        public string EndoOInterventoOrigine { get; set; } = String.Empty;
        public string Note { get; set; } = String.Empty;

        public string DescrizioneTipoPagamento { get; set; } = String.Empty;

        [Required]
        public DateTime? DataPagamento { get; set; }
        [Required]
        [StringLength(100)]
        public string RiferimentoPagamento { get; set; } = String.Empty;
        public decimal Importo { get; set; } = 0;

        [Required]
        public decimal ImportoPagato
        {
            get => this._importoPagato; set
            {
                this._importoPagato = value;

                if (this.OnImportoPagatoModificato is not null)
                {
                    this.OnImportoPagatoModificato();
                }
            }
        }
        public bool PagamentoCompletato { get; set; }
        [Required]
        public string CodiceTipoPagamento { get; set; } = "";

        public Action? OnImportoPagatoModificato { get; set; }
        public Action<GrigliaOneriItem>? OnModalitaPagamentoModificata { get; set; }
    }
}
