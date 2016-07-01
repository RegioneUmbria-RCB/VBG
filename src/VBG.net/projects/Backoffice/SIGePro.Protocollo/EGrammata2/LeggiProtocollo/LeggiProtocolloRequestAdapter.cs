using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.EGrammata2.LeggiProtocollo.SegnaturaRequest;

namespace Init.SIGePro.Protocollo.EGrammata2.LeggiProtocollo
{
    public class LeggiProtocolloRequestAdapter
    {
        public LeggiProtocolloRequestAdapter()
        {

        }

        public RicercaProtocollo Adatta(string numeroProtocollo, string anno)
        {
            return new RicercaProtocollo
            {
                RegPrimaria = new RegPrimaria
                {
                    RangeEstremiReg = new RangeEstremiReg
                    {
                        Sigla = "PG",
                        Anno = anno,
                        Nro = numeroProtocollo,
                        NroA = numeroProtocollo
                    }
                }
            };
        }
    }
}
