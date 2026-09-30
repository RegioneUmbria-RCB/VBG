using Init.Sigepro.FrontEnd.AppLogic.GestioneOggetti;
using Init.SIGePro.Manager.DTO.Configurazione;
using VBG.BlazorComponentsLibrary.EditFormComponents.DropDown;


namespace AreaRiservataCore.Pages.InserimentoIstanza.Allegati.AllegatoLibero
{
    public class NuovoAllegatoLibero
    {
        public NuovoAllegatoLibero(CategoriaBindingItem? tipoAllegato, DropDownItem? numeroPagine)
        {
            this.TipoAllegato = tipoAllegato;
            this.NumeroPagine = numeroPagine;
        }

        public int IdDomanda { get; set; }
        public CategoriaBindingItem? TipoAllegato { get; set; }
        public string NomeAllegato { get; set; } = "";
        public BinaryFile? FileAllegato { get; set; }
        public int? CodiceOggetto { get; set; }
        public bool ModalitaInserimento { get; set; } = true;
        public string Descrizione { get; set; } = "";
        public bool RichiedeFirma { get; set; }
        public DropDownItem? NumeroPagine { get; set; }
        public FormatoAllegatoLiberoDto? Formato { get; set; }
    }
}
