using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.Sigepro.FrontEnd.AppLogic.GenerazioneDocumentiDomanda.GenerazioneRiepilogoDomanda.GestioneSostituzioneSegnapostoRiepilogo.LetturaDatiDinamici;
using Init.SIGePro.DatiDinamici.WebControls;

namespace Init.Sigepro.FrontEnd.AppLogic.GenerazioneDocumentiDomanda.GenerazioneRiepilogoDomanda.GestioneSostituzioneSegnapostoRiepilogo
{
    public class SegnapostoNoteCampi : ISegnapostoRiepilogo
    {
        public string NomeTag => "noteCompilazione";

        public string NomeArgomento => string.Empty;

        public void Inizializza()
        {
            AccumulatoreNoteModello.InitContextInstance();
        }

        public string Elabora(IDatiDinamiciRiepilogoReader reader, string argomento, string espressione)
        {
            // todo: scrive le note accumulate sotto forma di html
            var instance = AccumulatoreNoteModello.GetContextInstance();

            if (instance == null)
            {
                return string.Empty;
            }

            return instance.ToHtml();
        }
    }
}
