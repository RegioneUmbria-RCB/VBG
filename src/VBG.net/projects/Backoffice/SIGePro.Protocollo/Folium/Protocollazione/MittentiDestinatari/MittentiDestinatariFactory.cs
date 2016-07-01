using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.ProtocolloInterfaces;
using Init.SIGePro.Protocollo.Constants;

namespace Init.SIGePro.Protocollo.Folium.Protocollazione.MittentiDestinatari
{
    public static class MittentiDestinatariFactory
    {
        public static List<IMittentiDestinatari> Create(IDatiProtocollo datiProto)
        {
            var list = new List<IMittentiDestinatari>();

            if (datiProto.Flusso == ProtocolloConstants.COD_INTERNO)
                list.Add(new MittentiDestinatariAmministrazione(datiProto.Amministrazione));
            else
            {
                datiProto.AnagraficheProtocollo.ForEach(x => list.Add(new MittentiDestinatariAnagrafe(x)));
                datiProto.AmministrazioniEsterne.ForEach(x => list.Add(new MittentiDestinatariAmministrazione(x)));
            }

            return list;
        }
    }
}
