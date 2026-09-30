using VBG.DatiDinamici.Web;
using VisuraVbg;

namespace Init.Sigepro.FrontEnd.AppLogic.GenerazioneDocumentiDomanda.GenerazioneRiepilogoDomanda.GestioneSostituzioneSegnapostoRiepilogo
{
    public class SegnapostoNoteCampi : ISegnapostoRiepilogo
    {
        private readonly IAccumulatoreNoteModelloService _accumulatoreNoteModelloService;

        public string NomeTag => "noteCompilazione";

        public string NomeArgomento => string.Empty;


        public SegnapostoNoteCampi(IAccumulatoreNoteModelloService accumulatoreNoteModelloService)
        {
            this._accumulatoreNoteModelloService = accumulatoreNoteModelloService;
        }

        public void Inizializza()
        {
            this._accumulatoreNoteModelloService.Inizializza();
        }

        public Task<string> ElaboraAsync(Istanze istanza, string argomento, string espressione)
        {
            return this._accumulatoreNoteModelloService.GetHtmlAsync();
        }
    }
}
