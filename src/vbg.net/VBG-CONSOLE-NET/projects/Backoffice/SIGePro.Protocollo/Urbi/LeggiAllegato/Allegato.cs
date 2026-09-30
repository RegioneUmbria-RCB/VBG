using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Protocollo.Urbi.LeggiAllegato
{
    public class Allegato
    {
        public string Id { get; private set; }
        public string Prg { get; private set; }
        public string Versione { get; private set; }

        public Allegato(string idBase)
        {
            var split = idBase.Split('.');
            this.Id = split[0];
            this.Prg = split[1];
            this.Versione = split[2];
        }
    }
}
