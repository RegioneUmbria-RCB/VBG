
using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;
using Init.Sigepro.FrontEnd.AppLogic.GenerazioneDocumentiDomanda.GenerazioneRiepilogoDomanda.GestioneSostituzioneSegnapostoRiepilogo.LetturaDatiDinamici;
using System;
using System.Threading.Tasks;

namespace Init.Sigepro.FrontEnd.AppLogic.GenerazioneDocumentiDomanda.GenerazioneRiepilogoDomanda.GestioneSostituzioneSegnapostoRiepilogo
{
    public class SegnapostoSchedeIntervento : ISegnapostoRiepilogo
    {
        private readonly IGeneratoreHtmlSchedeDinamiche _generatoreHtml;
        private readonly IConfigurazione<ParametriGenerazioneRiepilogoDomanda> _parametriRiepilogo;

        public SegnapostoSchedeIntervento(IGeneratoreHtmlSchedeDinamiche generatoreHtml, IConfigurazione<ParametriGenerazioneRiepilogoDomanda> parametriRiepilogo)
        {
            this._generatoreHtml = generatoreHtml;
            this._parametriRiepilogo = parametriRiepilogo;
        }

        #region ISegnapostoRiepilogo Members

        public string NomeTag
        {
            get { return "schedeIntervento"; }
        }

        public string NomeArgomento
        {
            get { return String.Empty; }
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
                throw new ArgumentNullException(nameof(reader));

            return this._generatoreHtml.GeneraHtmlSchedeIntervento(reader, this._parametriRiepilogo.Parametri.FlagSchedeNelRiepilogo);
        }

        public async Task<string> ElaboraAsync(ISchedeDinamicheDomandaAlRiepilogoService reader, string argomento, string espressione)
        {
            if (reader == null)
                throw new ArgumentNullException(nameof(reader));

            return await this._generatoreHtml.GeneraHtmlSchedeInterventoAsync(reader, this._parametriRiepilogo.Parametri.FlagSchedeNelRiepilogo);
        }

        #endregion
    }
}
