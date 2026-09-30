using VBG.Shared.Infrastructure.ServiceModel;
using Init.SIGePro.Protocollo.ProtocolloDocErPecService;
using System;
using System.ServiceModel;
using VBG.Backend.Protocollo.AppLogic.Shared.Costants;
using VBG.Backend.Protocollo.AppLogic.Shared.Logs;
using VBG.Backend.Protocollo.AppLogic.Shared.Serialize;


namespace VBG.Backend.Protocollo.AppLogic.Legacy.DocEr.Pec
{
    public class PecService
    {
        string _url; 
        ProtocolloLogs _logs;
        ProtocolloSerializer _serializer;
        string _token;
        private readonly ClientPecServiceCreator _clientPecServiceCreator;

        public PecService(string url, ProtocolloLogs logs, ProtocolloSerializer serializer, IBindingFactory bindingFactory, string token)
        {
            _url = url;
            _logs = logs;
            _serializer = serializer;
            _token = token;
            _clientPecServiceCreator = new ClientPecServiceCreator(_logs, bindingFactory, _url);
        }

        public void InvioPec(long idDocumento, SegnaturaPecAdapter.SegnaturaPec datiPec)
        {
            try
            {
                using (var ws = this._clientPecServiceCreator.CreateClient())
                {
                    _logs.InfoFormat("CHIAMATA A INVIO PEC DEL DOCUMENTO {0}, segnatura: {1}", idDocumento.ToString(), datiPec.SegnaturaSerializzata);
                    var response = ws.Service.invioPEC(_token, idDocumento, datiPec.SegnaturaSerializzata);

                    var objResponse = (Esito)_serializer.Deserialize(response, typeof(Esito));
                    _serializer.LogAndValidate(ProtocolloLogsConstants.SegnaturaPecResponseFileName, objResponse);

                    if (objResponse.Codice != "0")
                        throw new Exception(String.Format("CODICE {0}, DESCRIZIONE {1}", objResponse.Codice, objResponse.Descrizione));

                    _logs.InfoFormat("CHIAMATA A INVIO PEC DEL DOCUMENTO {0} ESEGUITA CORRETTAMENTE, IDENTIFICATIVO PEC: {1}", idDocumento, objResponse.Identificativo);
                }
            }
            catch (System.Exception ex)
            {
                throw new Exception(String.Format("ERRORE RESTITUITO DURANTE L'INVIO DELLA PEC TRAMITE WEB SERVICE, ERRORE {0}", ex.Message), ex);
            }
        }
    }
}
