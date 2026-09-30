
using Init.Sigepro.FrontEnd.AppLogic.GenerazioneDocumentiDomanda.GenerazioneRiepilogoDomanda.GestioneSostituzioneSegnapostoRiepilogo.LetturaDatiDinamici;
using System.Threading.Tasks;

namespace Init.Sigepro.FrontEnd.AppLogic.GenerazioneDocumentiDomanda.GenerazioneRiepilogoDomanda.GestioneSostituzioneSegnapostoRiepilogo
{
    internal class SegnapostoSchedaDinamica : ISegnapostoRiepilogo
    {
        private readonly IGeneratoreHtmlSchedeDinamiche _generatoreHtml;

        public SegnapostoSchedaDinamica(IGeneratoreHtmlSchedeDinamiche generatoreHtml)
        {
            this._generatoreHtml = generatoreHtml;
        }

        #region ISegnapostoRiepilogo Members

        public string NomeTag
        {
            get { return "schedaDinamica"; }
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

            var idScheda = -1;

            if (!int.TryParse(argomento, out idScheda))
                throw new ArgomentoSegnapostoNonValidoException(ArgomentoSegnapostoNonValidoException.TipoSegnaposto.Scheda, espressione);

            return this._generatoreHtml.GeneraHtml(reader, idScheda);
        }

        public async Task<string> ElaboraAsync(ISchedeDinamicheDomandaAlRiepilogoService reader, string argomento, string espressione)
        {
            if (reader == null)
                throw new System.ArgumentNullException(nameof(reader));

            var idScheda = -1;

            if (!int.TryParse(argomento, out idScheda))
                throw new ArgomentoSegnapostoNonValidoException(ArgomentoSegnapostoNonValidoException.TipoSegnaposto.Scheda, espressione);

            return await this._generatoreHtml.GeneraHtmlAsync(reader, idScheda);
        }

        #endregion
    }
}
