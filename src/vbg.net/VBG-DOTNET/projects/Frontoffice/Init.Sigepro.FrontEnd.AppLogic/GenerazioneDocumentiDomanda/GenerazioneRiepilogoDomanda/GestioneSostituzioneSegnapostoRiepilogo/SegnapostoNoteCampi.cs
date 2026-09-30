using Init.Sigepro.FrontEnd.AppLogic.GenerazioneDocumentiDomanda.GenerazioneRiepilogoDomanda.GestioneSostituzioneSegnapostoRiepilogo.LetturaDatiDinamici;
using VBG.DatiDinamici.Web;
using System.Threading.Tasks;

namespace Init.Sigepro.FrontEnd.AppLogic.GenerazioneDocumentiDomanda.GenerazioneRiepilogoDomanda.GestioneSostituzioneSegnapostoRiepilogo
{
    public class SegnapostoNoteCampi : ISegnapostoRiepilogo
    {
        private readonly IAccumulatoreNoteModelloService _accumulatoreNoteModelloService;

        public string NomeTag => "noteCompilazione";

        public string NomeArgomento => string.Empty;

        public bool SupportaOperazioniAsincrone =>
#if NET48
            false;
#else
            true;
#endif

        public SegnapostoNoteCampi(IAccumulatoreNoteModelloService accumulatoreNoteModelloService)
        {
            this._accumulatoreNoteModelloService = accumulatoreNoteModelloService;
        }

        public void Inizializza()
        {
            this._accumulatoreNoteModelloService.Inizializza();
        }

        public string Elabora(ISchedeDinamicheDomandaAlRiepilogoService reader, string argomento, string espressione)
        {
            return this._accumulatoreNoteModelloService.GetHtml();
        }

        public Task<string> ElaboraAsync(ISchedeDinamicheDomandaAlRiepilogoService reader, string argomento, string espressione)
        {
            return this._accumulatoreNoteModelloService.GetHtmlAsync();
        }
    }
}
