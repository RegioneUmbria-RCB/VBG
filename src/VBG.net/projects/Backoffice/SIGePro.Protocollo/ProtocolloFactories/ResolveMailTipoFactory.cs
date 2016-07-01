using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.Logs;
using PersonalLib2.Data;
using Init.SIGePro.Protocollo.Serialize;
using Init.SIGePro.Protocollo.ProtocolloInterfaces;
using Init.SIGePro.Protocollo.ProtocolloServices;
using Init.SIGePro.Manager;
using Init.SIGePro.Data;

namespace Init.SIGePro.Protocollo.ProtocolloFactories
{
    public class ResolveMailTipoFactory
    {
        public static IResolveMailTipoService Create(ResolveDatiProtocollazioneService datiProtocollazioneService, ProtocolloLogs log, ProtocolloSerializer serializer)
        {
            IResolveMailTipoService retVal = null;

            if (datiProtocollazioneService.TipoAmbito == ProtocolloEnumerators.ProtocolloEnum.AmbitoProtocollazioneEnum.DA_ISTANZA)
            {
                var param = new ParametriInterventoProtocolloProtocolloService(datiProtocollazioneService);
                retVal = new ResolveMailTipoIstanzaService(param, datiProtocollazioneService, log, serializer);
            }
            else if (datiProtocollazioneService.TipoAmbito == ProtocolloEnumerators.ProtocolloEnum.AmbitoProtocollazioneEnum.DA_MOVIMENTO)
            {
                var protoConf = new ProtocolloConfigurazioneMgr(datiProtocollazioneService.Db).GetById(datiProtocollazioneService.IdComune, datiProtocollazioneService.Software);
                int? codTestoMovimento = protoConf == null ? (int?)null : protoConf.Codtestomovimenti;
                var param = new ParametriInterventoProtocolloProtocolloService(codTestoMovimento);

                retVal = new ResolveMailTipoMovimentoService(param, datiProtocollazioneService, serializer, log);
            }

            return retVal;
        }
    }
}
