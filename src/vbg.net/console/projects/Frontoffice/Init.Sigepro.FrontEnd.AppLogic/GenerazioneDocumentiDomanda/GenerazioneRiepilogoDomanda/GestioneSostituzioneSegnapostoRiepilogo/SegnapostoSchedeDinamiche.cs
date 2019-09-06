using CuttingEdge.Conditions;
using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;
using Init.Sigepro.FrontEnd.AppLogic.GenerazioneDocumentiDomanda.GenerazioneRiepilogoDomanda.GestioneSostituzioneSegnapostoRiepilogo.LetturaDatiDinamici;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.GenerazioneDocumentiDomanda.GenerazioneRiepilogoDomanda.GestioneSostituzioneSegnapostoRiepilogo
{
    internal class SegnapostoSchedeDinamiche : ISegnapostoRiepilogo
    {
        IGeneratoreHtmlSchedeDinamiche _generatoreHtml;
        IConfigurazione<ParametriGenerazioneRiepilogoDomanda> _parametriRiepilogo;

        internal SegnapostoSchedeDinamiche(IGeneratoreHtmlSchedeDinamiche generatoreHtml, IConfigurazione<ParametriGenerazioneRiepilogoDomanda> parametriRiepilogo)
        {
            this._generatoreHtml = generatoreHtml;
            this._parametriRiepilogo = parametriRiepilogo;
        }

        #region ISegnapostoRiepilogo Members

        public string NomeTag
        {
            get { return "schedeDinamiche"; }
        }

        public string NomeArgomento
        {
            get { return String.Empty; }
        }

        public string Elabora(IDatiDinamiciRiepilogoReader reader, string argomento, string espressione)
        {
            Condition.Requires(reader, "reader").IsNotNull();

            return this._generatoreHtml.GeneraHtmlDelleSchedeDellaDomanda(reader, this._parametriRiepilogo.Parametri.FlagSchedeNelRiepilogo);
        }

        #endregion
    }
}
