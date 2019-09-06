using Init.SIGePro.Protocollo.ApSystems.Protocollazione.InsertProtocollo;
using Init.SIGePro.Protocollo.Logs;
using Init.SIGePro.Protocollo.ProtocolloApSystemsService;
using Init.SIGePro.Protocollo.Serialize;
using Init.SIGePro.Protocollo.WsDataClass;
using System;
using System.Collections.Generic;
using System.Data;
using System.Linq;
using System.ServiceModel;
using System.Text;

namespace Init.SIGePro.Protocollo.ApSystems.Protocollazione
{
    public class ProtocollazioneServiceWrapper : BaseServiceWrapper
    {
        string _formatoData;

        public ProtocollazioneServiceWrapper(ProtocolloLogs logs, ProtocolloSerializer serializer, string username, string password, string url, string operatore, string formatoData)
            : base(logs, serializer, username, password, url, operatore)
        {
            this._formatoData = formatoData;
        }

        public DatiProtocolloRes ProtocollaArrivo(DataSet request)
        {
            try
            {
                using (var ws = CreaWebService())
                {
                    Serializer.Serialize(ProtocolloLogsConstants.ProtocollazioneRequestFileName, request);
                    Logs.InfoFormat("CHIAMATA A PROTOCOLLAZIONE IN ARRIVO, DATI: {0}", request.GetXml());

                    var response = ws.InsertProtocolloGenerale(Auth, request, Operatore);

                    Serializer.Serialize(ProtocolloLogsConstants.ProtocollazioneResponseFileName, response);

                    var ds = new protocolli();
                    ds.Merge(response);

                    if (ds.ContieneErrori())
                        throw new Exception(ds.GetDescrizioneErrore());

                    Logs.InfoFormat("PROTOCOLLAZIONE IN ARRIVO AVVENUTA CON SUCCESSO, PROTOCOLLO NUMERO: {0}, DATA: {1}, CODICE: {2}", ds.protocollo[0].numero_protocollo, ds.protocollo[0].data_protocollo, ds.protocollo[0].codice);

                    var retVal = new DatiProtocolloRes
                    {
                        IdProtocollo = ds.protocollo[0].codice,
                        AnnoProtocollo = DateTime.ParseExact(ds.protocollo[0].data_protocollo, this._formatoData, null).ToString("yyyy"),
                        DataProtocollo = DateTime.ParseExact(ds.protocollo[0].data_protocollo, this._formatoData, null).ToString("dd/MM/yyyy"),
                        NumeroProtocollo = ds.protocollo[0].numero_protocollo,
                        Warning = Logs.Warnings.WarningMessage
                    };

                    return retVal;

                }
            }
            catch (Exception ex)
            {
                throw new Exception(String.Format("ERRORE GENERATO DURANTE LA PROTOCOLLAZIONE IN ARRIVO, ERRORE {0}", ex.Message), ex);
            }

        }

        public DatiProtocolloRes ProtocollaPartenza(DataSet request)
        {
            try
            {
                using (var ws = CreaWebService())
                {
                    Serializer.Serialize(ProtocolloLogsConstants.ProtocollazioneRequestFileName, request);
                    Logs.InfoFormat("CHIAMATA A PROTOCOLLAZIONE IN PARTENZA (), DATI: {0}", request.GetXml());

                    var response = ws.InsertProtocolloGenerale(Auth, request, Operatore);

                    Serializer.Serialize(ProtocolloLogsConstants.ProtocollazioneResponseFileName, response);

                    var ds = new protocolli();
                    ds.Merge(response);

                    if (ds.ContieneErrori())
                        throw new Exception(ds.GetDescrizioneErrore());

                    Logs.InfoFormat("PROTOCOLLAZIONE IN PARTENZA AVVENUTA CON SUCCESSO, PROTOCOLLO NUMERO: {0}, DATA: {1}, CODICE: {2}", ds.protocollo[0].numero_protocollo, ds.protocollo[0].data_protocollo, ds.protocollo[0].codice);

                    var retVal = new DatiProtocolloRes
                    {
                        IdProtocollo = ds.protocollo[0].codice,
                        AnnoProtocollo = DateTime.ParseExact(ds.protocollo[0].data_protocollo, this._formatoData, null).ToString("yyyy"),
                        DataProtocollo = DateTime.ParseExact(ds.protocollo[0].data_protocollo, this._formatoData, null).ToString("dd/MM/yyyy"),
                        NumeroProtocollo = ds.protocollo[0].numero_protocollo,
                        Warning = Logs.Warnings.WarningMessage
                    };

                    return retVal;

                }
            }
            catch (Exception ex)
            {
                throw new Exception(String.Format("ERRORE GENERATO DURANTE LA PROTOCOLLAZIONE IN PARTENZA, ERRORE {0}", ex.Message), ex);
            }
        }

        public DatiProtocolloRes ProtocollaPartenzaBozza(DataSet request)
        {
            try
            {
                using (var ws = CreaWebService())
                {
                    Serializer.Serialize(ProtocolloLogsConstants.ProtocollazioneRequestFileName, request);
                    Logs.InfoFormat("CHIAMATA A INSERIMENTO BOZZA PROTOCOLLAZIONE IN PARTENZA, DATI: {0}", request.GetXml());

                    var responseBozza = ws.InsertBozzaProtocolloInterno(Auth, request, Operatore);

                    Serializer.Serialize(ProtocolloLogsConstants.InserimentoBozzaResponseFileName, responseBozza);

                    var dsBozza = new InserimentoBozza.protocolli();
                    dsBozza.Merge(responseBozza);

                    if (dsBozza.ContieneErrori())
                        throw new Exception(String.Format("IN INSERIMENTO BOZZA, {0}", dsBozza.GetDescrizioneErrore()));

                    var codice = dsBozza.protocollo[0].codice;

                    Logs.InfoFormat("CHIAMATA A INSERIMENTO BOZZA PROTOCOLLAZIONE IN PARTENZA, AVVENUTA CON SUCCESSO, CODICE RESTITUITO {1}", codice);

                    Logs.InfoFormat("CHIAMATA A INVIO BOZZA PROTOCOLLAZIONE IN PARTENZA, CODICE {0}", dsBozza.protocollo[0].codice);
                    var response = ws.SendBozzaProtocolloInterno(Auth, codice, Operatore);

                    Serializer.Serialize(ProtocolloLogsConstants.InvioBozzaResponseFileName, responseBozza);

                    var ds = new InvioBozza.protocolli();
                    ds.Merge(response);

                    if (ds.ContieneErrori())
                        throw new Exception(String.Format("IN INVIO BOZZA, {0}", ds.GetDescrizioneErrore()));

                    Logs.InfoFormat("CHIAMATA A INVIO BOZZA PROTOCOLLAZIONE IN PARTENZA AVVENUTA CON SUCCESSO, GENERATO PROTOCOLLO NUMERO {0}, DATA {1}, CODICE {2}", ds.protocollo[0].numero_protocollo_generale, ds.protocollo[0].data_protocollo_generale, ds.protocollo[0].codice);

                    var retVal = new DatiProtocolloRes
                    {
                        IdProtocollo = ds.protocollo[0].codice,
                        AnnoProtocollo = DateTime.ParseExact(ds.protocollo[0].data_protocollo_generale, this._formatoData, null).ToString("yyyy"),
                        DataProtocollo = DateTime.ParseExact(ds.protocollo[0].data_protocollo_generale, this._formatoData, null).ToString("dd/MM/yyyy"),
                        NumeroProtocollo = ds.protocollo[0].numero_protocollo_generale,
                        Warning = Logs.Warnings.WarningMessage
                    };

                    return retVal;
                }
            }
            catch (Exception ex)
            {
                throw new Exception(String.Format("ERRORE GENERATO DURANTE LA PROTOCOLLAZIONE IN PARTENZA, ERRORE {0}", ex.Message), ex);
            }
        }

        public DatiProtocolloRes ProtocollaInterno(DataSet request)
        {
            try
            {
                using (var ws = CreaWebService())
                {
                    Serializer.Serialize(ProtocolloLogsConstants.ProtocollazioneRequestFileName, request);
                    Logs.InfoFormat("CHIAMATA A PROTOCOLLAZIONE INTERNA, DATI: {0}", request.GetXml());

                    var response = ws.InsertProtocolloGenerale(Auth, request, Operatore);

                    Serializer.Serialize(ProtocolloLogsConstants.ProtocollazioneResponseFileName, response);

                    var ds = new protocolli();
                    ds.Merge(response);

                    if (ds.ContieneErrori())
                        throw new Exception(ds.GetDescrizioneErrore());

                    Logs.InfoFormat("PROTOCOLLAZIONE INTERNA AVVENUTA CON SUCCESSO, PROTOCOLLO NUMERO {0}, DATA {1}, CODICE {2}", ds.protocollo[0].numero_protocollo, ds.protocollo[0].data_protocollo, ds.protocollo[0].codice);

                    var retVal = new DatiProtocolloRes
                    {
                        IdProtocollo = ds.protocollo[0].codice,
                        AnnoProtocollo = DateTime.Parse(ds.protocollo[0].data_protocollo).ToString("yyyy"),
                        DataProtocollo = ds.protocollo[0].data_protocollo,
                        NumeroProtocollo = ds.protocollo[0].numero_protocollo,
                        Warning = Logs.Warnings.WarningMessage
                    };

                    return retVal;
                }
            }
            catch (Exception ex)
            {
                throw new Exception(String.Format("ERRORE GENERATO DURANTE LA PROTOCOLLAZIONE INTERNA, ERRORE {0}", ex.Message), ex);
            }
        }
    }
}
