using VBG.Shared.Infrastructure.ServiceModel;
using Init.SIGePro.Manager;
using VBG.Backend.Protocollo.AppLogic.Shared.Enums;
using VBG.Backend.Protocollo.AppLogic.Shared.Logs;
using VBG.Backend.Protocollo.AppLogic.Shared.Managers;
using VBG.Backend.Protocollo.AppLogic.Shared.Serialize;

namespace VBG.Backend.Protocollo.AppLogic.Shared.Services.MailTipo
{
    public static class MailTipoServiceFactory
    {
        public static IMailTipoService? Create(ResolveDatiProtocollazioneService datiProtocollazioneService, ProtocolloLogs log, ProtocolloSerializer serializer, IBindingFactory bindingFactory, bool isFascicolazione)
        {
            if (datiProtocollazioneService.TipoAmbito == AmbitoProtocollazioneEnum.DA_ISTANZA)
            {
                var param = new ParametriInterventoProtocolloProtocolloService(datiProtocollazioneService, isFascicolazione);
                return new MailTipoIstanzaService(param, datiProtocollazioneService, log, serializer, bindingFactory);
            }

            if (datiProtocollazioneService.TipoAmbito == AmbitoProtocollazioneEnum.DA_MOVIMENTO)
            {
                int? codTestoMovimento = null;

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
                return new MailTipoMovimentoService(param, datiProtocollazioneService, serializer, bindingFactory, log);
            }

            return null;
        }
    }
}
