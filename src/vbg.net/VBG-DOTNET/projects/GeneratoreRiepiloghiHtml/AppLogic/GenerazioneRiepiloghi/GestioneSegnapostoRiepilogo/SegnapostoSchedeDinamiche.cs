
using GeneratoreRiepiloghiHtml.AppLogic.GenerazioneRiepiloghi.GestioneSegnapostoRiepilogo;
using GeneratoreRiepiloghiHtml.AppLogic.GenerazioneRiepiloghi.RiepiloghiSchede;
using GeneratoreRiepiloghiHtml.AppLogic.GestioneDatiDinamici;
using System.Text;
using VisuraVbg;

namespace Init.Sigepro.FrontEnd.AppLogic.GenerazioneDocumentiDomanda.GenerazioneRiepilogoDomanda.GestioneSostituzioneSegnapostoRiepilogo
{
    internal class SegnapostoSchedeDinamiche : ISegnapostoRiepilogo
    {
        private readonly GeneratoreRiepilogoSingolaSchedaService _generatoreHtml;
        private readonly IStrutturaSchedeDinamicheService _strutturaSchedeDinamicheService;

        public SegnapostoSchedeDinamiche(GeneratoreRiepilogoSingolaSchedaService generatoreHtml, IStrutturaSchedeDinamicheService strutturaSchedeDinamicheService)
        {
            this._generatoreHtml = generatoreHtml;
            this._strutturaSchedeDinamicheService = strutturaSchedeDinamicheService;
        }

        #region ISegnapostoRiepilogo Members

        public string NomeTag => "schedeDinamiche";

        public string NomeArgomento => String.Empty;

        public async Task<string> ElaboraAsync(Istanze istanza, string argomento, string espressione)
        {
            var idIntervento = Convert.ToInt32(istanza.CODICEINTERVENTOPROC);
            var endoSelezionati = istanza.EndoProcedimenti.Select(x => Convert.ToInt32(x.CODICEINVENTARIO));

            var schede = await this._strutturaSchedeDinamicheService.GetSchedeDaInterventoEEndoAsync(idIntervento, endoSelezionati);

            var sb = new StringBuilder();

            foreach (var scheda in schede)
            {
                sb.Append(await this._generatoreHtml.GeneraHtmlSchedaAsync(istanza, scheda.IdModello, false));
            }

            return WrapperSegnapostoSchedeDinamiche.Wrap(sb.ToString());
        }

        #endregion
    }
}
