using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.GenerazioneDocumentiDomanda.GenerazioneRiepilogoDomanda.GestioneSostituzioneSegnapostoRiepilogo.LetturaDatiDinamici.LetturaDaVisuraSigepro
{
    public class VisuraValoreDatoDinamicoRiepilogo : IValoreDatoDinamicoRiepilogo
    {
        public string ValoreDecodificato { get; private set; }

        public VisuraValoreDatoDinamicoRiepilogo(string valoreDecodificato)
        {
            this.ValoreDecodificato = valoreDecodificato;
        }
    }
}
