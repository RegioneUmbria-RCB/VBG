using Init.Sigepro.FrontEnd.AppLogic.Autenticazione.Vbg.TraduzioneIdComune;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogicTests.Adapters.IstanzaSigeproAdapterTests.Mocks
{
    public class AliasToIdComuneTranslatorFake : IAliasToIdComuneTranslator
    {
        public string Translate(string aliasComune)
        {
            return aliasComune;
        }
    }
}
