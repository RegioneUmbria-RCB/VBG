using VBG.Shared.Infrastructure.ServiceModel;
using ProtocolloProtInfService;
using System.ServiceModel;
using VBG.Backend.Protocollo.AppLogic.Core.ProtoInf.Protocollazione;
using VBG.Backend.Protocollo.AppLogic.Shared.Logs;
using VBG.Backend.Protocollo.AppLogic.Shared.Serialize;

namespace VBG.Backend.Protocollo.AppLogic.Core.ProtoInf
{
    public class ProtocolloServiceWrapper
    {
        ProtocolloLogs _logs;
        ProtocolloSerializer _serializer;
        private readonly ProtocollazioneClientServiceCreator _protocollazioneClientServiceCreator;
        string _url;

        public ProtocolloServiceWrapper(ProtocolloLogs logs, ProtocolloSerializer serializer, IBindingFactory bindingFactory, string url)
        {
            this._logs = logs;
            this._serializer = serializer;
            this._url = url;
            this._protocollazioneClientServiceCreator = new ProtocollazioneClientServiceCreator(_logs, bindingFactory, _url);
        }

        public ProtocolloXMLResponse Protocolla(string protocollaXML, string mittenteXML, string destinatarioXML, string assegnatarioXml, string allegatiXml, string dirFtp)
        {
            try
            {
                using (var ws = _protocollazioneClientServiceCreator.CreateClient())
                {
                    var percorso = dirFtp.Replace(@"\", "/");

                    _logs.InfoFormat("DATI DA INVIARE ALLA CHIAMATA A PROTOCOLLA\r\nPROTOCOLLAXML: {0}; \r\nMITTENTEXML: {1}\r\nDESTINATARIOXML: {2}\r\nASSEGNATARIOXML: {3}\r\nALLEGATIXML: {4}\r\nDIRECTORY FTP: {5}", protocollaXML, mittenteXML, destinatarioXML, assegnatarioXml, allegatiXml, percorso);
                    _logs.InfoFormat("CHIAMATA A PROTOCOLLA");
                    var responseXml = ws.Service.protocolla(protocollaXML, mittenteXML, destinatarioXML, assegnatarioXml, allegatiXml, percorso);
                    _logs.InfoFormat("RISPOSTA DEL METODO PROTOCOLLA: {0}", responseXml);
                    var response = _serializer.Deserialize<ProtocolloXMLResponse>(responseXml);
                    if (response.Esito != "OK")
                    {
                        throw new Exception(response.Esito);
                    }

                    _logs.InfoFormat("CHIAMATA A PROTOCOLLA AVVENUTA CON SUCCESSO, RISPOSTA {0}", responseXml);

                    return response;
                }
            }
            catch (Exception ex)
            {
                throw new Exception($"ERRORE GENERATO NELLA FUNZIONALITA' DI PROTOCOLLAZIONE, {ex.Message}", ex);
            }
        }
    }
}
