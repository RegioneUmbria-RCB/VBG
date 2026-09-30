using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.AppLogic.VerificaSoggettiFirmatari
{
    public class TipoSoggettoFirmatario
    {
        public readonly int Codice;
        public readonly string Descrizione;

        public TipoSoggettoFirmatario(int codice, string descrizione)
        {
            this.Codice = codice;
            this.Descrizione = descrizione;
        }
    }
}
