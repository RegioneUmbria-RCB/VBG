using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.ProtocolloServices;

namespace Init.SIGePro.Protocollo.SidUmbria.Protocollazione
{
    public class ProtocollazioneIstanzaMovimentoFactory
    {
        public static IProtocollazioneIstanzaMovimento Create(ResolveDatiProtocollazioneService datiIstanzaMovimentoService)
        {
            if (datiIstanzaMovimentoService.Movimento != null)
                return new ProtocollazioneMovimento(datiIstanzaMovimentoService.Movimento);
            else if (datiIstanzaMovimentoService.Istanza != null)
                return new ProtocollazioneIstanza(datiIstanzaMovimentoService.Istanza);
            else
                return new ProtocollazioneDefault();

        }

    }
}
