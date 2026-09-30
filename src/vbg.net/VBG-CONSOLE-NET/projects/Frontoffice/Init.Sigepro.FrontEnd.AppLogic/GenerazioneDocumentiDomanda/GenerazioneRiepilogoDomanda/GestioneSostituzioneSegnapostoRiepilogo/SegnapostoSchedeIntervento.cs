using CuttingEdge.Conditions;
using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;
using Init.Sigepro.FrontEnd.AppLogic.GenerazioneDocumentiDomanda.GenerazioneRiepilogoDomanda.GestioneSostituzioneSegnapostoRiepilogo.LetturaDatiDinamici;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.GenerazioneDocumentiDomanda.GenerazioneRiepilogoDomanda.GestioneSostituzioneSegnapostoRiepilogo
{
    public class SegnapostoSchedeIntervento : ISegnapostoRiepilogo
    {
        IGeneratoreHtmlSchedeDinamiche _generatoreHtml;
        IConfigurazione<ParametriGenerazioneRiepilogoDomanda> _parametriRiepilogo;

        internal SegnapostoSchedeIntervento(IGeneratoreHtmlSchedeDinamiche generatoreHtml, IConfigurazione<ParametriGenerazioneRiepilogoDomanda> parametriRiepilogo)
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

        public string Elabora(IDatiDinamiciRiepilogoReader reader, string argomento, string espressione)
        {
            Condition.Requires(reader, "reader").IsNotNull();

            return this._generatoreHtml.GeneraHtmlSchedeIntervento(reader, this._parametriRiepilogo.Parametri.FlagSchedeNelRiepilogo);
        }

        #endregion
    }
}
