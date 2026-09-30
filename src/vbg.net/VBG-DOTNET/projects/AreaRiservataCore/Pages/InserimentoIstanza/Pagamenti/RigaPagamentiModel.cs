using Init.Sigepro.FrontEnd.AppLogic.GestioneOneri;
using VBG.BlazorComponentsLibrary.EditFormComponents.DropDown;

namespace AreaRiservataCore.Pages.InserimentoIstanza.Pagamenti
{
    public class RigaPagamentiModel
    {
        public string? Id { get; set; }
        public int? IdOnere { get; set; }
        public int? CodiceEndoOIntervento { get; set; }
        public string? DescrizioneCausale { get; set; }
        public string? DescrizioneIntervento { get; set; }
        public DropDownItem? PagamentoCompletato { get; set; }
        public TipoPagamento? TipoPagamento { get; set; }
        public DateTime? Data { get; set; }
        public string? Riferimento { get; set; }
        public decimal? Importo { get; set; }
        public decimal? ImportoPagato { get; set; }
        public DropDownItem? TipoPagamentoDropDownItem { get; set; }
    }
}
