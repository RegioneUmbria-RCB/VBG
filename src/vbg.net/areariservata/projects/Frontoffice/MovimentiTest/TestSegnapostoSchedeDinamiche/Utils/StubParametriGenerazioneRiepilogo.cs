using Init.Sigepro.FrontEnd.AppLogic.Configurazione.V2;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogicTests.TestSegnapostoSchedeDinamiche.Utils
{
    public class StubParametriGenerazioneRiepilogo : IConfigurazione<ParametriGenerazioneRiepilogoDomanda>
    {
        int _flagIncluisoneSchede;

        public StubParametriGenerazioneRiepilogo(int flagIncluisoneSchede)
        {
            this._flagIncluisoneSchede = flagIncluisoneSchede;
        }
        
        public ParametriGenerazioneRiepilogoDomanda Parametri => new ParametriGenerazioneRiepilogoDomanda(this._flagIncluisoneSchede);
    }
}
