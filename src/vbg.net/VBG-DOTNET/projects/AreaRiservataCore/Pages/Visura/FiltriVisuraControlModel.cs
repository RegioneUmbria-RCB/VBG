using Init.SIGePro.Manager.DTO.Comuni;
using Init.SIGePro.Manager.DTO.Visura;
using VBG.BlazorComponentsLibrary.EditFormComponents;
using VBG.BlazorComponentsLibrary.EditFormComponents.DropDown;


namespace AreaRiservataCore.Pages.Visura
{
    public class FiltriVisuraControlModel
    {
        public int? Anno { get; set; }
        public DropDownItem Mese { get; set; }
        public string Oggetto { get; set; }
        public string Civico { get; set; }
        public string CodiceIstanza { get; set; }
        public string NumeroAutorizzazione { get; set; }
        public string NumeroProtocollo { get; set; }
        public AutocompleteFormResult Stradario { get; set; }
        public DatiComuneCompatto CodiceComune { get; set; }
        public string Fabbricato { get; set; }
        public StatoIstanzaDto StatoIstanza { get; set; }
        public DateTime? DataProtocollo { get; set; }
        public DatiCatasto DatiCatasto { get; set; } = new DatiCatasto();
        public string Richiedente { get; set; }
        public AutocompleteFormResult TipoIntervento { get; set; }
        public string PosizioneArchivio { get; set; }
    }

    public class DatiCatasto
    {
        public DropDownItem TipoCatasto { get; set; }
        public string Foglio { get; set; }
        public string Particella { get; set; }
        public string Subalterno { get; set; }
    }
}
