using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.ProtocolloProtInfService;
using Init.SIGePro.Protocollo.Logs;
using Init.SIGePro.Protocollo.Serialize;
using System.ServiceModel;
using Init.SIGePro.Data;
using Init.SIGePro.Protocollo.ProtoInf.Protocollazione;
using Init.SIGePro.Protocollo.Constants;

namespace Init.SIGePro.Protocollo.ProtoInf
{
    public class ProtocolloServiceWrapper
    {
        ProtocolloLogs _logs;
        ProtocolloSerializer _serializer;
        string _url;

        public ProtocolloServiceWrapper(ProtocolloLogs logs, ProtocolloSerializer serializer, string url)
        {
            this._logs = logs;
            this._serializer = serializer;
            this._url = url;
        }

        public ProtocolloWebServiceClient CreaWebService()
        {
            try
            {
                _logs.Debug("Creazione del webservice di protocollazione PROTOINF");
                if (String.IsNullOrEmpty(_url))
                    throw new Exception("IL PARAMETRO URL DELLA VERTICALIZZAZIONE PROTOCOLLO_PROTOINF NON È STATO VALORIZZATO, NON È POSSIBILE CONTATTARE IL WEB SERVICE");

                var endPointAddress = new EndpointAddress(_url);
                var binding = new BasicHttpBinding("defaultHttpBinding");

                var ws = new ProtocolloWebServiceClient(binding, endPointAddress);

                _logs.Debug("Fine creazione del webservice PROTOINF2");

                return ws;
            }
            catch (Exception ex)
            {
                throw new Exception(String.Format("ERRORE AVVENUTO DURANTE LA CREAZIONE DEL WEB SERVICE DI PROTOCOLLAZIONE, {0}", ex.Message), ex);
            }
        }

        public ProtocolloXMLResponse Protocolla(string protocollaXML, string mittenteXML, string destinatarioXML, string assegnatarioXml, string allegatiXml, string dirFtp)
        {
            try
            {
                using (var ws = CreaWebService())
                {
                    var percorso = dirFtp.Replace(@"\", "/");

                    _logs.InfoFormat("DATI DA INVIARE ALLA CHIAMATA A PROTOCOLLA\r\nPROTOCOLLAXML: {0}; \r\nMITTENTEXML: {1}\r\nDESTINATARIOXML: {2}\r\nASSEGNATARIOXML: {3}\r\nALLEGATIXML: {4}\r\nDIRECTORY FTP: {5}", protocollaXML, mittenteXML, destinatarioXML, assegnatarioXml, allegatiXml, percorso);
                    _logs.InfoFormat("CHIAMATA A PROTOCOLLA");
                    var responseXml = ws.protocolla(protocollaXML, mittenteXML, destinatarioXML, assegnatarioXml, allegatiXml, percorso);
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
