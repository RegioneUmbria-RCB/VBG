using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.ProtocolloInterfaces;
using Init.SIGePro.Protocollo.JProtocollo2.Proxy;

namespace Init.SIGePro.Protocollo.JProtocollo2.Protocollazione
{
    public class ProtocollazioneSoggettiAdapter
    {
        IDatiProtocollo _datiProto;

        public ProtocollazioneSoggettiAdapter(IDatiProtocollo datiProto )
        {
            _datiProto = datiProto;
        }

        public object[] Adatta()
        {
            var amministrazioni = _datiProto.AmministrazioniEsterne.Select(x => new soggetto { denominazione = x.AMMINISTRAZIONE, indirizzo = x.PEC });
            var anagrafica = _datiProto.AnagraficheProtocollo.Select(x => new soggetto { denominazione = x.GetNomeCompleto(), indirizzo = x.Pec });

            var soggetti = new List<soggetto>();

            soggetti.AddRange(amministrazioni);
            soggetti.AddRange(anagrafica);

            return soggetti.ToArray();
        }
    }
}
