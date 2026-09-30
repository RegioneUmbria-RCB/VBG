using Init.SIGePro.Manager.Utils;
using Init.SIGePro.Protocollo.EGrammata2.Anagrafiche.Request;
using Init.SIGePro.Protocollo.EGrammata2.Anagrafiche.Response;
using Init.SIGePro.Protocollo.EGrammata2.Verticalizzazioni;
using Init.SIGePro.Protocollo.LeggiAnagraficheEGrammata2Service;
using Init.SIGePro.Protocollo.Logs;
using Init.SIGePro.Protocollo.Serialize;
using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.ServiceModel;
using System.Text;

namespace Init.SIGePro.Protocollo.EGrammata2.Anagrafiche
{
    public class AnagraficheService : BaseService
    {
        
        public AnagraficheService (ProtocolloLogs logs, ProtocolloSerializer serializer, VerticalizzazioniConfiguration conf) : base(logs, serializer, conf)
	    {

	    }

        private WSRicercaAnagraficaClient CreaWebService()
        {
            try
            {
                Logs.Debug("Creazione del webservice di leggi protocollo E-Grammata");
                if (String.IsNullOrEmpty(Parametri.UrlLeggiAnagrafiche))
                    throw new Exception("IL PARAMETRO URL_LEGGIANAGRAFICHE DELLA VERTICALIZZAZIONE PROTOCOLLO_EGRAMMATA2 NON È STATO VALORIZZATO, NON È POSSIBILE CONTATTARE IL WEB SERVICE");

                var endPointAddress = new EndpointAddress(Parametri.UrlLeggiAnagrafiche);
                var binding = new BasicHttpBinding("defaultHttpBinding");
                var ws = new WSRicercaAnagraficaClient(binding, endPointAddress);

                Logs.Debug("Fine creazione del webservice E-Grammata");

                return ws;
            }
            catch (Exception ex)
            {
                throw new Exception(String.Format("ERRORE AVVENUTO DURANTE LA CREAZIONE DEL WEB SERVICE DI LETTURA ANAGRAFICA, ERRORE: {0}", ex.Message), ex);
            }
        }

        public Anagrafica[] LeggiAnagrafica(RicercaAnagrafica request)
        {
            try
            {
                using (var ws = CreaWebService())
                {
                    string xml = Serializer.Serialize(ProtocolloLogsConstants.LeggiAnagraficheRequest, request, Validation.ProtocolloValidation.TipiValidazione.DTD_EGRAMMATA2);
                    Logs.InfoFormat("CHIAMATA A LEGGI ANAGRAFICA DECODIFICATA, xml: {0}", xml);
                    var buffer = File.ReadAllBytes(Path.Combine(Logs.Folder, ProtocolloLogsConstants.LeggiAnagraficheRequest));
                    string xml64 = Base64Utils.Base64Encode(buffer);
                    Logs.InfoFormat("CHIAMATA A LEGGI ANAGRAFICA, xml: {0}", xml64);
                    var response64 = ws.ricercaNS(Parametri.CodiceEnte, Parametri.Username, Parametri.Password, IndirizzoIp, xml64, "", Parametri.UserApp, Parametri.Postazione);
                    Logs.InfoFormat("RISPOSTA DA LEGGI ANAGRAFICA, xml: {0}", response64);
                    string responseString = Base64Utils.Base64Decode(response64);
                    Logs.InfoFormat("RISPOSTA DA LEGGI ANAGRAFICA DECODIFICATA, xml: {0}", responseString);
                    var response = (RisultatoRicercaAnagrafica)Serializer.Deserialize(responseString, typeof(RisultatoRicercaAnagrafica));

                    if (response.Stato != null && !String.IsNullOrEmpty(response.Stato.Codice) && response.Stato.Codice != "0")
                        throw new Exception(String.Format("ERRORE RESTITUITO DAL WEB SERVICE, CODICE: {0}, DESCRIZIONE: {1}", response.Stato.Codice, response.Stato.Messaggio));

                    if (response.Anagrafica != null && response.Anagrafica.Length > 1)
                        Logs.WarnFormat("LA RICERCA DELL'ANAGRAFICA CON CODICE FISCALE / PARTITA IVA {0} HA RESTITUITO PIU' DI UN VALORE, NON E' STATO QUINDI RECUPERATO IL CODICE DELL'ANAGRAFICA", ((DatiAnag)request.Item).Codice.Item);

                    return response.Anagrafica;
                }
            }
            catch (Exception ex)
            {
                Logs.WarnFormat(String.Format("ERRORE GENERATO DURANTE LA LETTURA DELL'ANAGRAFICA, {0}", ex.Message), ex);
                return null;
            }
        }
    }
}
