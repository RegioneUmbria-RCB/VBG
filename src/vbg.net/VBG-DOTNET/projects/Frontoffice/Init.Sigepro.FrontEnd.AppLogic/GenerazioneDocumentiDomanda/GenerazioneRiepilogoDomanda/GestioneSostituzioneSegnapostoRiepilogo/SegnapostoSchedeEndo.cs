using Init.Sigepro.FrontEnd.AppLogic.GenerazioneDocumentiDomanda.GenerazioneRiepilogoDomanda.GestioneSostituzioneSegnapostoRiepilogo.LetturaDatiDinamici;
using System.Threading.Tasks;

namespace Init.Sigepro.FrontEnd.AppLogic.GenerazioneDocumentiDomanda.GenerazioneRiepilogoDomanda.GestioneSostituzioneSegnapostoRiepilogo
{
    public class SegnapostoSchedeEndo : ISegnapostoRiepilogo
    {
        private readonly IGeneratoreHtmlSchedeDinamiche _generatoreHtml;

        public SegnapostoSchedeEndo(IGeneratoreHtmlSchedeDinamiche generatoreHtml)
        {
            this._generatoreHtml = generatoreHtml;
        }

        #region ISegnapostoRiepilogo Members

        public string NomeTag
        {
            get { return "schedeEndo"; }
        }

        public string NomeArgomento
        {
            get { return "id"; }
        }
        public bool SupportaOperazioniAsincrone =>
#if NET48
            false;
#else
true;
#endif

        public string Elabora(ISchedeDinamicheDomandaAlRiepilogoService reader, string argomento, string espressione)
        {
            if (reader == null)
                throw new System.ArgumentNullException(nameof(reader));

            if (!int.TryParse(argomento, out var idEndo))
                throw new ArgomentoSegnapostoNonValidoException(ArgomentoSegnapostoNonValidoException.TipoSegnaposto.Scheda, espressione);

            return this._generatoreHtml.GeneraHtmlSchedaEndoprocedimento(reader, idEndo);
        }

        public async Task<string> ElaboraAsync(ISchedeDinamicheDomandaAlRiepilogoService reader, string argomento, string espressione)
        {
            if (reader == null)
                throw new System.ArgumentNullException(nameof(reader));

            if (!int.TryParse(argomento, out var idEndo))
                throw new ArgomentoSegnapostoNonValidoException(ArgomentoSegnapostoNonValidoException.TipoSegnaposto.Scheda, espressione);

            return await this._generatoreHtml.GeneraHtmlSchedaEndoprocedimentoAsync(reader, idEndo);
        }

        #endregion
    }
}
