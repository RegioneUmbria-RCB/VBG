using Init.SIGePro.Protocollo.WsDataClass;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Init.SIGePro.Protocollo.ApSystems.LeggiProtocollo
{
    public class LeggiProtocolloResponseAdapter
    {
        public LeggiProtocolloResponseAdapter()
        {

        }

        public DatiProtocolloLetto Adatta(protocolli response)
        {
            var factory = LeggiProtocolloFactory.Create(response);

            var r = response.protocollo[0];

            var retVal = new DatiProtocolloLetto
            {
                IdProtocollo = r.codice,
                NumeroProtocollo = r.numero,
                DataProtocollo = DateTime.ParseExact(r.data, "dd/MM/yyyy h.mm.ss", null).ToString("dd/MM/yyyy"),
                AnnoProtocollo = DateTime.ParseExact(r.data, "dd/MM/yyyy h.mm.ss", null).ToString("yyyy"),
                Oggetto = r.oggetto,
                Classifica_Descrizione = r.classificazione,
                Origine = factory.Flusso,
                InCaricoA = factory.InCaricoA,
                InCaricoA_Descrizione = factory.InCaricoADescrizione,
                MittentiDestinatari = factory.GetMittenteDestinatario()
            };

            if (response.allegato.Rows.Count > 0)
                retVal.Allegati = response.allegato.Select(x => new AllOut { IDBase = x.codice, Serial = x.nome }).ToArray();

            return retVal;
        }
    }
}
