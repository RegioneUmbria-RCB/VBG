using VBG.DatiDinamici.Interfaces;

namespace GeneratoreRiepiloghiHtml.Controllers
{
    public class ValoreCampoModel : IValoreCampo
    {
        public int? Indice { get; set; }
        public int? IndiceMolteplicita { get; set; }
        public string Valore { get; set; } = "";

        public string Valoredecodificato { get; set; } = "";
    }
}
