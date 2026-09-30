using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.Logs;
using Init.SIGePro.Protocollo.Serialize;
using Init.SIGePro.Protocollo.EGrammata2.Verticalizzazioni;
using Init.SIGePro.Protocollo.EGrammata2.LeggiProtocollo.SegnaturaRequest;
using Init.SIGePro.Protocollo.EGrammata2.LeggiProtocollo.SegnaturaResponse;
using System.ServiceModel;
using Init.SIGePro.Protocollo.LeggiProtocolloEGrammata2Service;
using Init.SIGePro.Manager.Utils;
using System.IO;

namespace Init.SIGePro.Protocollo.EGrammata2.LeggiProtocollo
{
    public class LeggiProtocolloService : BaseService
    {
        public LeggiProtocolloService(ProtocolloLogs logs, ProtocolloSerializer serializer, VerticalizzazioniConfiguration conf) : base(logs, serializer, conf)
        {

        }

        private WSRicercaProtocolloClient CreaWebService()
        {
            try
            {
                Logs.Debug("Creazione del webservice di leggi protocollo E-Grammata");
                if (String.IsNullOrEmpty(Parametri.UrlLeggiProto))
                    throw new Exception("IL PARAMETRO URL_LEGGIPROTO DELLA VERTICALIZZAZIONE PROTOCOLLO_EGRAMMATA2 NON È STATO VALORIZZATO, NON È POSSIBILE CONTATTARE IL WEB SERVICE");


                var endPointAddress = new EndpointAddress(Parametri.UrlLeggiProto);
                var binding = new BasicHttpBinding("defaultHttpBinding");
                var ws = new WSRicercaProtocolloClient(binding, endPointAddress);

                Logs.Debug("Fine creazione del webservice E-Grammata");

                return ws;
            }
            catch (Exception ex)
            {
                throw new Exception(String.Format("ERRORE AVVENUTO DURANTE LA CREAZIONE DEL WEB SERVICE DI PROTOCOLLAZIONE, ERRORE: {0}", ex.Message));
            }
        }

        public RisultatoRicerca LeggiProtocollo(RicercaProtocollo segnatura)
        {
            try
            {
                using (var ws = CreaWebService())
                {
                    string xml = Serializer.Serialize(ProtocolloLogsConstants.LeggiProtocolloRequestFileName, segnatura, Validation.ProtocolloValidation.TipiValidazione.DTD_EGRAMMATA2);
                    var buffer = File.ReadAllBytes(Path.Combine(Logs.Folder, ProtocolloLogsConstants.LeggiProtocolloRequestFileName));
                    string xml64 = Base64Utils.Base64Encode(buffer);

                    //string xml64 = Base64Utils.Base64Encode( ConvertXmlToBase64(segnatura, ProtocolloLogsConstants.LeggiProtocolloRequestFileName);
                    var response64 = ws.ricerca(Parametri.CodiceEnte, Parametri.Username, Parametri.Password, IndirizzoIp, xml64, "", Parametri.UserApp, Parametri.Postazione);
                    string responseString = Base64Utils.Base64Decode(response64);

                    var response = (RisultatoRicerca)Serializer.Deserialize(responseString, typeof(RisultatoRicerca));

                    if (response.Stato != null && !String.IsNullOrEmpty(response.Stato.Codice) && response.Stato.Codice != "0")
                        throw new Exception(String.Format("ERRORE RESTITUITO DAL WEB SERVICE, CODICE: {0}, DESCRIZIONE: {1}", response.Stato.Codice, response.Stato.Messaggio));

                    return response;
                }
            }
            catch (Exception ex)
            {

                throw new Exception(String.Format("ERRORE DURANTE LA CHIAMATA AL WEB SERVICE, ERRORE: {0}", ex.Message));
            }


        }

    }
}
