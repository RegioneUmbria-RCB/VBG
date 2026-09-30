using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using ProtocolloInsielService2;
using VBG.Backend.Protocollo.AppLogic.Shared.Logs;
using VBG.Backend.Protocollo.AppLogic.Shared.Serialize;
using VBG.Shared.Infrastructure.ServiceModel;

namespace VBG.Backend.Protocollo.AppLogic.Core.Insiel2.Services
{
    public class ProtocolloService : BaseService
    {
        Utente _utente;
        private readonly ProtocollazioneClientServiceCreator _protocollazioneClientServiceCreator;

        public ProtocolloService(string url, ProtocolloLogs logs, ProtocolloSerializer serializer, IBindingFactory bindingFactory, string codiceUtente, string password) : base(url, logs, serializer)
        {
            _utente = new Utente { codice = codiceUtente, password = password };
            this._protocollazioneClientServiceCreator = new ProtocollazioneClientServiceCreator(logs, bindingFactory, url);
        }

        internal ProtocolloResponse Protocolla(InserimentoProtocolloRequest request)
        {
            using (var ws = this._protocollazioneClientServiceCreator.CreateClient())
            {
                try
                {
                    request.utente = _utente;

                    Serializer.LogAndValidate(ProtocolloLogsConstants.ProtocollazioneRequestFileName, request);
                    Logs.Info("Chiamata al web method inserisciProtocollo del web service di Protocollazione");
                    var response = ws.Service.inserisciProtocollo(request);

                    Serializer.LogAndValidate(ProtocolloLogsConstants.ProtocollazioneResponseFileName, response);
                    
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
            using (var ws = this._protocollazioneClientServiceCreator.CreateClient())
            {
                try
                {
                    request.Utente = _utente;

                    Logs.Info("Chiamata al web service leggi protocollo");
                    var response = ws.Service.dettagliProtocolllo(request);
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
            using (var ws = this._protocollazioneClientServiceCreator.CreateClient())
            {
                try
                {
                    request.Utente = _utente;
                    Logs.Info("Chiamata al web service gettipidocumento");

                    var response = ws.Service.getTipiDoc(request);
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