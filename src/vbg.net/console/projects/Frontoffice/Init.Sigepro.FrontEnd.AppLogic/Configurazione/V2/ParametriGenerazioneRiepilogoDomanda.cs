using Init.Sigepro.FrontEnd.AppLogic.GenerazioneDocumentiDomanda.GenerazioneRiepilogoDomanda.GestioneSostituzioneSegnapostoRiepilogo;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2
{
    public class ParametriGenerazioneRiepilogoDomanda : IParametriConfigurazione
    {
        public readonly GenerazioneHtmlSchedeOptions FlagSchedeNelRiepilogo;

        public ParametriGenerazioneRiepilogoDomanda(int flagSchedeNelRiepilogo)
        {
            this.FlagSchedeNelRiepilogo = (GenerazioneHtmlSchedeOptions)flagSchedeNelRiepilogo; 
        }
    }
}
