using GeneratoreRiepiloghiHtml.AppLogic.GenerazioneRiepiloghi.GestioneSegnapostoRiepilogo;
using GeneratoreRiepiloghiHtml.AppLogic.GenerazioneRiepiloghi.RiepiloghiSchede;
using GeneratoreRiepiloghiHtml.AppLogic.GestioneDatiDinamici;
using System.Text;
using VisuraVbg;

namespace Init.Sigepro.FrontEnd.AppLogic.GenerazioneDocumentiDomanda.GenerazioneRiepilogoDomanda.GestioneSostituzioneSegnapostoRiepilogo
{
    public class SegnapostoSchedeEndo : ISegnapostoRiepilogo
    {
        private readonly GeneratoreRiepilogoSingolaSchedaService _generatoreHtml;
        private readonly IStrutturaSchedeDinamicheService _strutturaSchedeDinamicheService;

        public SegnapostoSchedeEndo(GeneratoreRiepilogoSingolaSchedaService generatoreHtml, IStrutturaSchedeDinamicheService strutturaSchedeDinamicheService)
        {
            this._generatoreHtml = generatoreHtml;
            this._strutturaSchedeDinamicheService = strutturaSchedeDinamicheService;
        }

        #region ISegnapostoRiepilogo Members

        public string NomeTag => "schedeEndo";

        public string NomeArgomento => "id";

        public async Task<string> ElaboraAsync(Istanze istanza, string argomento, string espressione)
        {
            if (!int.TryParse(argomento, out int idEndo))
                throw new ArgomentoSegnapostoNonValidoException(ArgomentoSegnapostoNonValidoException.TipoSegnaposto.Scheda, espressione);


            var schede = await this._strutturaSchedeDinamicheService.GetSchedeDaEndoprocedimentiAsync(new[] { idEndo });

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
