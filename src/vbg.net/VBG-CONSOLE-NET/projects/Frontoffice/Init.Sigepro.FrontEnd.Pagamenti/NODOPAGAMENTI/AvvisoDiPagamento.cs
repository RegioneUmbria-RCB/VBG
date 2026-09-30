using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.Sigepro.FrontEnd.Pagamenti.NODOPAGAMENTI
{
    public class AvvisoDiPagamento
    {
        public enum StatoAvvisoEnum
        {
            RICHIESTO,
            DISPONIBILE,
            NON_DISPONIBILE,
        }

        public StatoAvvisoEnum Stato { get; internal set; }
        public byte[] Dati { get; internal set; }
        public string Desctrizione { get; internal set; }
    }
}
