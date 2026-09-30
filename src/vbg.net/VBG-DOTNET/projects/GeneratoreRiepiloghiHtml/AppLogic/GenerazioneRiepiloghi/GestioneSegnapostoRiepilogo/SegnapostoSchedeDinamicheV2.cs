using GeneratoreRiepiloghiHtml.AppLogic.GenerazioneRiepiloghi.RiepiloghiSchede;
using GeneratoreRiepiloghiHtml.AppLogic.GestioneDatiDinamici;
using VisuraVbg;

namespace Init.Sigepro.FrontEnd.AppLogic.GenerazioneDocumentiDomanda.GenerazioneRiepilogoDomanda.GestioneSostituzioneSegnapostoRiepilogo
{
    internal class SegnapostoSchedeDinamicheV2 : ISegnapostoRiepilogo
    {
        private readonly GeneratoreRiepilogoSingolaSchedaService _generatoreHtml;
        private readonly IStrutturaSchedeDinamicheService _strutturaSchedeDinamicheService;

        public SegnapostoSchedeDinamicheV2(GeneratoreRiepilogoSingolaSchedaService generatoreHtml, IStrutturaSchedeDinamicheService strutturaSchedeDinamicheService)
        {
            this._generatoreHtml = generatoreHtml;
            this._strutturaSchedeDinamicheService = strutturaSchedeDinamicheService;
        }

        #region ISegnapostoRiepilogo Members

        public string NomeTag => "schedeDinamiche";

        public string NomeArgomento => "versione-renderer";

        public async Task<string> ElaboraAsync(Istanze istanza, string argomento, string espressione)
        {
            // Per ora sono la stessa cosa, nella vecchia area riservata serviva a distinguere il tipo di renderer in modo da mantenere due path separati per la gestione degli stili
            return await new SegnapostoSchedeDinamiche(this._generatoreHtml, this._strutturaSchedeDinamicheService).ElaboraAsync(istanza, argomento, espressione);
        }

        #endregion
    }
}
