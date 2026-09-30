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
                int? codTestoMovimento = (int?) null;

                //Inserire la logica di recupero codice mail tipo da movimento
                var tipoMovMgr = new TipiMovimentoMgr(datiProtocollazioneService.Db);
                var tipoMov = tipoMovMgr.GetById(datiProtocollazioneService.Movimento.TIPOMOVIMENTO, datiProtocollazioneService.IdComune);

                if (tipoMov != null && tipoMov.FkMailTipoOggProt.HasValue)
                {
                    codTestoMovimento = tipoMov.FkMailTipoOggProt;
                    log.DebugFormat("CODICE MAIL E TESTO TIPO (OGGETTO DEL PROTOCOLLO) RECUPERATO DAL TIPOMOVIMENTO {0}, IL CODICE MAIL / TESTO TIPO E' {1}", tipoMov.Tipomovimento, tipoMov.FkMailTipoOggProt.Value.ToString());
                }
                else
                {
                    var protoConf = new ProtocolloConfigurazioneMgr(datiProtocollazioneService.Db).GetById(datiProtocollazioneService.IdComune, datiProtocollazioneService.Software);
                    if (protoConf != null)
                        codTestoMovimento = protoConf.Codtestomovimenti;
                }
                
                var param = new ParametriInterventoProtocolloProtocolloService(codTestoMovimento);
                retVal = new ResolveMailTipoMovimentoService(param, datiProtocollazioneService, serializer, log);
            }

            return retVal;
        }
    }
}
