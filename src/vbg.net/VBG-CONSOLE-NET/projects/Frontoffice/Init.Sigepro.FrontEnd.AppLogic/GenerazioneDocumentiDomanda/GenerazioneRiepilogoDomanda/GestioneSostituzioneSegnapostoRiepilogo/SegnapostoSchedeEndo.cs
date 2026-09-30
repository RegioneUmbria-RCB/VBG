using CuttingEdge.Conditions;
using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;
using Init.Sigepro.FrontEnd.AppLogic.GenerazioneDocumentiDomanda.GenerazioneRiepilogoDomanda.GestioneSostituzioneSegnapostoRiepilogo.LetturaDatiDinamici;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.GenerazioneDocumentiDomanda.GenerazioneRiepilogoDomanda.GestioneSostituzioneSegnapostoRiepilogo
{
    public class SegnapostoSchedeEndo : ISegnapostoRiepilogo
    {
        IGeneratoreHtmlSchedeDinamiche _generatoreHtml;

        internal SegnapostoSchedeEndo(IGeneratoreHtmlSchedeDinamiche generatoreHtml)
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

        public string Elabora(IDatiDinamiciRiepilogoReader reader, string argomento, string espressione)
        {
            Condition.Requires(reader, "reader").IsNotNull();

            if (!int.TryParse(argomento, out int idEndo))
                throw new ArgomentoSegnapostoNonValidoException(ArgomentoSegnapostoNonValidoException.TipoSegnaposto.Scheda, espressione);

            return this._generatoreHtml.GeneraHtmlSchedaEndoprocedimento(reader, idEndo);
        }

        #endregion
    }
}
