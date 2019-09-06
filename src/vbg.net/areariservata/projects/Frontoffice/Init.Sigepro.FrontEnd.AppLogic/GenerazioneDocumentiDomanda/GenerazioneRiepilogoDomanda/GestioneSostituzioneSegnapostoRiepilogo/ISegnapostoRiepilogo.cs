using Init.Sigepro.FrontEnd.AppLogic.GenerazioneDocumentiDomanda.GenerazioneRiepilogoDomanda.GestioneSostituzioneSegnapostoRiepilogo.LetturaDatiDinamici;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.GenerazioneDocumentiDomanda.GenerazioneRiepilogoDomanda.GestioneSostituzioneSegnapostoRiepilogo
{
    internal interface ISegnapostoRiepilogo
    {
        string NomeTag { get; }
        string NomeArgomento { get; }

        string Elabora(IDatiDinamiciRiepilogoReader reader, string argomento, string espressione);
    }
}
