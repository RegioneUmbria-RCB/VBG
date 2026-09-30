using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using VBG.Backend.Protocollo.AppLogic.Shared.Data;

namespace VBG.Backend.Protocollo.AppLogic.Core.SidUmbria.Protocollazione
{
    public class AllegatiAdapter
    {
        public static allegato[] Adatta(IEnumerable<ProtocolloAllegati> allegati, bool isFtp, string prefix, VerticalizzazioniConfiguration vert, string username, bool disabilitaControlloP7m)
        {
            if (allegati.Count() == 0)
            {
                if (disabilitaControlloP7m)
                {
                    throw new Exception("NON E' PRESENTE ALCUN FILE, E' OBBLIGATORIO INSERIRNE ALMENO UNO");
                }
                else
                {
                    throw new Exception("NON E' PRESENTE ALCUN FILE ALLEGATO P7M, E' OBBLIGATORIO INSERIRNE ALMENO UNO");
                }

            }

            var res = allegati.Select((x, idx) => x.ToAllegato(idx == 0, isFtp, prefix, vert, username));

            return res.ToArray();
        }
    }
}
