using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Data;
using Init.SIGePro.Protocollo.ProtocolloSidUmbriaService;

namespace Init.SIGePro.Protocollo.SidUmbria.Protocollazione
{
    public class AllegatiAdapter
    {
        public static allegato[] Adatta(IEnumerable<ProtocolloAllegati> allegati)
        {
            if (allegati.Count() == 0)
                throw new Exception("NON E' PRESENTE ALCUN FILE ALLEGATO P7M, E' OBBLIGATORIO INSERIRNE ALMENO UNO");

            var res = allegati.Select((x, idx) => x.ToAllegato(idx == 0));

            return res.ToArray();
        }
    }
}
