using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.ProtocolloFoliumService;

namespace Init.SIGePro.Protocollo.Folium.Protocollazione.MittentiDestinatari
{
    public class MittentiDestinatariAdapter
    {
        List<IMittentiDestinatari> _list;
        string _codicePerConoscenza;

        public MittentiDestinatariAdapter(List<IMittentiDestinatari> list, string codicePerConoscenza)
        {
            _list = list;
            _codicePerConoscenza = codicePerConoscenza;
        }

        public MittenteDestinatario[] Adatta()
        { 
            var retVal = new List<MittenteDestinatario>();

            _list.ForEach(x => retVal.Add(new MittenteDestinatario
            {
                citta = x.Citta,
                codiceMezzoSpedizione = x.CodiceMezzoSpedizione,
                cognome = x.Cognome,
                denominazione = x.Denominazione,
                email = x.EMail,
                indirizzo = x.Indirizzo,
                invioPC = x.IsPerConoscenza(_codicePerConoscenza),
                nome = x.Nome,
                tipo = x.Tipo
            }));

            if (retVal.Count == 0)
                throw new Exception("MITTENTE / DESTINATARIO NON PRESENTE");

            return retVal.ToArray();
        }
    }
}
