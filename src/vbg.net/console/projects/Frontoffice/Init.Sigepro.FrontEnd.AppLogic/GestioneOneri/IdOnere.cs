using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.GestioneOneri
{
    public class IdOnere
    {
        public readonly ProvenienzaOnere Provenienza;
        public readonly int CodiceCausale;
        public readonly int CodiceEndoOIntervento;

        public IdOnere(ProvenienzaOnere provenienza, int codiceCausale, int codiceEndoOIntervento)
        {
            this.Provenienza = provenienza;
            this.CodiceCausale = codiceCausale;
            this.CodiceEndoOIntervento = codiceEndoOIntervento;
        }
    }
}
