using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.ProtocolloServices;
using Init.SIGePro.Data;
using Init.SIGePro.Protocollo.Logs;
using Init.SIGePro.Protocollo.Serialize;
using Init.SIGePro.Manager;
using Init.SIGePro.Protocollo.Halley.ImpresaInUnGiorno.Sue;
using Init.SIGePro.Protocollo.Halley.ImpresaInUnGiorno.Suap;

namespace Init.SIGePro.Protocollo.Halley.ImpresaInUnGiorno
{
    public class SuapSueGeneratorFactory
    {
        public static ISuapSueGenerator Create(ResolveDatiProtocollazioneService datiProtocollazioneService, List<ProtocolloAllegati> allegati, string radiceEdilizia, ProtocolloLogs logs, ProtocolloSerializer serializer)
        {
            var alberoProcMgr = new AlberoProcMgr(datiProtocollazioneService.Db);
            var albero = alberoProcMgr.GetById(Convert.ToInt32(datiProtocollazioneService.Istanza.CODICEINTERVENTOPROC), datiProtocollazioneService.IdComune);

            if (albero.SC_CODICE.StartsWith(radiceEdilizia))
                return new SueGenerator(datiProtocollazioneService, logs, allegati, serializer);
            else
                return new SuapGenerator(datiProtocollazioneService, logs, allegati, serializer);
        }
    }
}
