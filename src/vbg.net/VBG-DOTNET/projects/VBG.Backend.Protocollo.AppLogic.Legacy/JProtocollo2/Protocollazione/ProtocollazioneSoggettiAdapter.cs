using System;
using System.Collections.Generic;
using System.Linq;
using VBG.Backend.Protocollo.AppLogic.Legacy.JProtocollo2.Proxy;
using VBG.Backend.Protocollo.AppLogic.Shared.Interfaces;


namespace VBG.Backend.Protocollo.AppLogic.Legacy.JProtocollo2.Protocollazione
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
            var anagrafica = _datiProto.AnagraficheProtocollo.Select(x => new soggetto { denominazione = x.GetNomeCompleto(), indirizzo = x.PecProtocollazione });

            var soggetti = new List<soggetto>();

            soggetti.AddRange(amministrazioni);
            soggetti.AddRange(anagrafica);

            return soggetti.ToArray();
        }
    }
}
