using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Init.SIGePro.Protocollo.Logs;
using Init.SIGePro.Protocollo.Serialize;
using System.ServiceModel;
using Init.SIGePro.Protocollo.ProtocolloInsielService2;

namespace Init.SIGePro.Protocollo.Insiel2.Services
{
    public class ProtocolloService : BaseService
    {
        Utente _utente;

        public ProtocolloService(string url, ProtocolloLogs logs, ProtocolloSerializer serializer, string codiceUtente, string password) : base(url, logs, serializer)
        {
            _utente = new Utente { codice = codiceUtente, password = password };
        }

        private ProtocolloPTClient CreaWebService()
        { 
            try
            {
                Logs.Debug("Creazione del webservice di protocollazione INSIEL2");

                var endPointAddress = new EndpointAddress(Url);
                var binding = new BasicHttpBinding("defaultHttpBinding");

                if (String.IsNullOrEmpty(Url))
                    throw new Exception("IL PARAMETRO URL DELLA VERTICALIZZAZIONE PROTOCOLLO_INSIEL NON È STATO VALORIZZATO.");

                var ws = new ProtocolloPTClient(binding, endPointAddress);

                Logs.Debug("Fine creazione del web service di protocollazione INSIEL2");

                return ws;
                
            }
            catch (Exception ex)
            {
                throw new Exception(String.Format("ERRORE DURANTE LA CREAZIONE DEL WEB SERVICE, {0}", ex.Message), ex);
            }
        }

        internal ProtocolloResponse Protocolla(InserimentoProtocolloRequest request)
        {
            using (var ws = CreaWebService())
            {
                try
                {
                    request.utente = _utente;

                    Serializer.Serialize(ProtocolloLogsConstants.ProtocollazioneRequestFileName, request);
                    Logs.Info("Chiamata al web method inserisciProtocollo del web service di Protocollazione");
                    var response = ws.inserisciProtocollo(request);

                    Serializer.Serialize(ProtocolloLogsConstants.ProtocollazioneResponseFileName, response);
                    
                    if (!response.esito)
                    {
                        var err = (Errore)response.Items[0];
                        throw new Exception(String.Format("CODICE: {0}, DESCRIZIONE: {1}", err.codice, err.descrizione));
                    }
                    
                    Logs.Info("PROTOCOLLAZIONE AVVENUTA CORRETTAMENTE");

                    return (ProtocolloResponse)response.Items[0];
                }
                catch (Exception ex)
                {
                    throw new Exception(String.Format("IL WEB SERVICE DI PROTOCOLLAZIONE HA RESTITUITO IL SEGUENTE ERRORE, {0}", ex.Message), ex);
                }
            }
        }

        internal DettagliProtocollo LeggiProtocollo(DettagliProtocolloRequest request)
        {
            using (var ws = CreaWebService())
            {
                try
                {
                    request.Utente = _utente;

                    Logs.Info("Chiamata al web service leggi protocollo");
                    var response = ws.dettagliProtocolllo(request);
                    if (!response.esito)
                    {
                        var err = (Errore)response.Item;
                        throw new Exception(String.Format("CODICE {0}, DESCRIZIONE: {1}", err.codice, err.descrizione));
                    }

                    Logs.Info("LETTURA DEL PROTOCOLLO AVVENUTA CORRETTAMENTE");

                    return (DettagliProtocollo)response.Item;
                }
                catch (Exception ex)
                {
                    throw new Exception(String.Format("IL WEB SERVICE DI PROTOCOLLAZIONE HA RESTITUITO IL SEGUENTE ERRORE DURANTE LA LETTURA DEL PROTOCOLLO, {0}", ex.Message), ex);
                }
            }
        }

        internal getTipiDocResponse GetTipiDocumento(getTipiDocRequest request)
        {
            using (var ws = CreaWebService())
            {
                try
                {
                    request.Utente = _utente;
                    Logs.Info("Chiamata al web service gettipidocumento");

                    var response = ws.getTipiDoc(request);
                    if (!response.esito)
                    {
                        var err = (Errore)response.Items[0];
                        throw new Exception(String.Format("CODICE: {0}, DESCRIZIONE: {1}", err.codice, err.descrizione));
                    }
                    Logs.Info("LETTURA DEL PROTOCOLLO AVVENUTA CORRETTAMENTE");

                    return response;
                }
                catch (Exception ex)
                {
                    throw new Exception(String.Format("IL WEB SERVICE DI PROTOCOLLAZIONE HA RESTITUITO IL SEGUENTE ERRORE DURANTE IL RECUPERO DELLE TIPOLOGIE DI DOCUMENTO, {0}", ex.Message), ex);
                }
            }
        }

    }
}