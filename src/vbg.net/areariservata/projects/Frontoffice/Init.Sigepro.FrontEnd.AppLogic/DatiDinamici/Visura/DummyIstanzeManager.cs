using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.DatiDinamici.Interfaces;

namespace Init.Sigepro.FrontEnd.AppLogic.DatiDinamici.Visura
{
    public class DummyIstanzeManager : SIGePro.DatiDinamici.Interfaces.Istanze.IIstanzeManager
    {
        public class DummyIstanza : SIGePro.DatiDinamici.Interfaces.IClasseContestoModelloDinamico
        {

        }

        public SIGePro.DatiDinamici.Interfaces.IClasseContestoModelloDinamico LeggiIstanza(string idComune, int codiceIstanza)
        {
            return new DummyIstanza();
        }
    }
}
