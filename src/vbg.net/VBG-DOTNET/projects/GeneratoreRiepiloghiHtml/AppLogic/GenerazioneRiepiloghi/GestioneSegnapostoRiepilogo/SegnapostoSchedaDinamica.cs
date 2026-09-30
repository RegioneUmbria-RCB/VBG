
using GeneratoreRiepiloghiHtml.AppLogic.GenerazioneRiepiloghi.GestioneSegnapostoRiepilogo;
using GeneratoreRiepiloghiHtml.AppLogic.GenerazioneRiepiloghi.RiepiloghiSchede;
using VisuraVbg;

namespace Init.Sigepro.FrontEnd.AppLogic.GenerazioneDocumentiDomanda.GenerazioneRiepilogoDomanda.GestioneSostituzioneSegnapostoRiepilogo
{
    internal class SegnapostoSchedaDinamica : ISegnapostoRiepilogo
    {
        private readonly GeneratoreRiepilogoSingolaSchedaService _generatoreHtml;

        public SegnapostoSchedaDinamica(GeneratoreRiepilogoSingolaSchedaService generatoreHtml)
        {
            this._generatoreHtml = generatoreHtml;
        }

        #region ISegnapostoRiepilogo Members

        public string NomeTag => "schedaDinamica";

        public string NomeArgomento => "id";


        public async Task<string> ElaboraAsync(Istanze istanza, string argomento, string espressione)
        {
            int idScheda = -1;

            if (!int.TryParse(argomento, out idScheda))
                throw new ArgomentoSegnapostoNonValidoException(ArgomentoSegnapostoNonValidoException.TipoSegnaposto.Scheda, espressione);

            return WrapperSegnapostoSchedeDinamiche.Wrap(await this._generatoreHtml.GeneraHtmlSchedaAsync(istanza, idScheda, false));
        }

        #endregion
    }
}
