using VBG.Backend.Protocollo.AppLogic.Core.Insiel4.Services.Rest;
using VBG.Backend.Protocollo.AppLogic.Core.Insiel4.Services.Rest.Entities;
using VBG.Backend.Protocollo.AppLogic.Shared.Logs;
using VBG.Backend.Protocollo.AppLogic.Shared.WsDataClass;

namespace VBG.Backend.Protocollo.AppLogic.Core.Insiel4.Protocollazione
{
    public class ProtocollazioneOutputAdapter
    {
        InserimentoProtocolloResponse _response;
        string _separatore;
        ProtocolloLogs _log;

        public ProtocollazioneOutputAdapter(InserimentoProtocolloResponse response, string separatore, ProtocolloLogs log)
        {
            _response = response;
            _separatore = separatore;
            _log = log;
        }

        public DatiProtocolloResponseType Adatta()
        {
            DettagliRegistrazioneProtocollo reg = _response.Registrazioni[0];

            return new DatiProtocolloResponseType
            {
                AnnoProtocollo = reg.Anno.ToString(),
                DataProtocollo = reg.Data?.ToString("dd/MM/yyyy"),
                IdProtocollo = String.Concat(reg.ProgDoc, _separatore, reg.ProgMovi),
                NumeroProtocollo = reg.Numero.ToString(),
                Warning = _log.Warnings.WarningMessage
            };
        }
    }
}
