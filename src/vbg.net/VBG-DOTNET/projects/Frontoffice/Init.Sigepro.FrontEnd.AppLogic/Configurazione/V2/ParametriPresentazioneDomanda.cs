using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2
{

    public class ParametriPresentazioneDomanda: IParametriConfigurazione
    {
        public readonly bool RichiedenteSoloPersoneFisiche;
        public readonly bool AbilitaTemplateDomanda;
        public readonly bool VerificaFirmaSoggettiRiepilogo;

        public ParametriPresentazioneDomanda( bool richiedenteSoloPersoneFisiche, bool abilitaTemplateDomanda, bool verificaFirmaSoggettiRiepilogo)
        {
            this.RichiedenteSoloPersoneFisiche = richiedenteSoloPersoneFisiche;
            this.AbilitaTemplateDomanda = abilitaTemplateDomanda;
            this.VerificaFirmaSoggettiRiepilogo = verificaFirmaSoggettiRiepilogo;
        }
    }
}
