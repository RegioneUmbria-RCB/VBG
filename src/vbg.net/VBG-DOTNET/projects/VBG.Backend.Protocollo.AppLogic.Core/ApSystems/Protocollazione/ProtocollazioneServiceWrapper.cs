using VBG.Shared.Infrastructure.ServiceModel;
using ProtocolloApSystemsService;
using System.Data;
using VBG.Backend.Protocollo.AppLogic.Core.ApSystems.Protocollazione.InsertProtocollo;
using VBG.Backend.Protocollo.AppLogic.Shared.Logs;
using VBG.Backend.Protocollo.AppLogic.Shared.Serialize;
using VBG.Backend.Protocollo.AppLogic.Shared.WsDataClass;


namespace VBG.Backend.Protocollo.AppLogic.Core.ApSystems.Protocollazione
{
    public class ProtocollazioneServiceWrapper
    {
        private string _formatoData;
        private string _operatore;
        private readonly ProtocolloLogs _log;
        private readonly ProtocolloSerializer _serializer;
        private readonly ProtocolloClientServiceCreator _protocolloClientServiceCreator;
        private readonly AuthenticationDetails _auth;

        public ProtocollazioneServiceWrapper(ProtocolloLogs logs, ProtocolloSerializer serializer, string username, string password, string url, string operatore, string formatoData, IBindingFactory bindingFactory)
        {
            _formatoData = formatoData;
            _operatore = operatore;
            _log = logs;
            _serializer = serializer;
            _protocolloClientServiceCreator = new ProtocolloClientServiceCreator(logs, bindingFactory, url);
            _auth = new AuthenticationDetails { UserName = username, Password = password };
        }

        public DatiProtocolloResponseType ProtocollaArrivo(DataSet request)
        {
            try
            {
                using (var ws = _protocolloClientServiceCreator.CreateClient())
                {
                    _serializer.LogAndValidate(ProtocolloLogsConstants.ProtocollazioneRequestFileName, request);
                    _log.InfoFormat("CHIAMATA A PROTOCOLLAZIONE IN ARRIVO, DATI: {0}", request.GetXml());

                    var response = ws.Service.InsertProtocolloGenerale(_auth, request, _operatore);

                    _serializer.LogAndValidate(ProtocolloLogsConstants.ProtocollazioneResponseFileName, response);

                    var ds = new protocolli();
                    ds.Merge(response);

                    if (ds.ContieneErrori())
                        throw new Exception(ds.GetDescrizioneErrore());

                    _log.InfoFormat("PROTOCOLLAZIONE IN ARRIVO AVVENUTA CON SUCCESSO, PROTOCOLLO NUMERO: {0}, DATA: {1}, CODICE: {2}", ds.protocollo[0].numero_protocollo, ds.protocollo[0].data_protocollo, ds.protocollo[0].codice);

                    var retVal = new DatiProtocolloResponseType
                    {
                        IdProtocollo = ds.protocollo[0].codice,
                        AnnoProtocollo = DateTime.ParseExact(ds.protocollo[0].data_protocollo, this._formatoData, null).ToString("yyyy"),
                        DataProtocollo = DateTime.ParseExact(ds.protocollo[0].data_protocollo, this._formatoData, null).ToString("dd/MM/yyyy"),
                        NumeroProtocollo = ds.protocollo[0].numero_protocollo,
                        Warning = _log.Warnings.WarningMessage
                    };

                    return retVal;

                }
            }
            catch (Exception ex)
            {
                throw new Exception(String.Format("ERRORE GENERATO DURANTE LA PROTOCOLLAZIONE IN ARRIVO, ERRORE {0}", ex.Message), ex);
            }

        }

        public DatiProtocolloResponseType ProtocollaPartenza(DataSet request)
        {
            try
            {
                using (var ws = _protocolloClientServiceCreator.CreateClient())
                {
                    _serializer.LogAndValidate(ProtocolloLogsConstants.ProtocollazioneRequestFileName, request);
                    _log.InfoFormat("CHIAMATA A PROTOCOLLAZIONE IN PARTENZA (), DATI: {0}", request.GetXml());

                    var response = ws.Service.InsertProtocolloGenerale(_auth, request, _operatore);

                    _serializer.LogAndValidate(ProtocolloLogsConstants.ProtocollazioneResponseFileName, response);

                    var ds = new protocolli();
                    ds.Merge(response);

                    if (ds.ContieneErrori())
                        throw new Exception(ds.GetDescrizioneErrore());

                    _log.InfoFormat("PROTOCOLLAZIONE IN PARTENZA AVVENUTA CON SUCCESSO, PROTOCOLLO NUMERO: {0}, DATA: {1}, CODICE: {2}", ds.protocollo[0].numero_protocollo, ds.protocollo[0].data_protocollo, ds.protocollo[0].codice);

                    var retVal = new DatiProtocolloResponseType
                    {
                        IdProtocollo = ds.protocollo[0].codice,
                        AnnoProtocollo = DateTime.ParseExact(ds.protocollo[0].data_protocollo, this._formatoData, null).ToString("yyyy"),
                        DataProtocollo = DateTime.ParseExact(ds.protocollo[0].data_protocollo, this._formatoData, null).ToString("dd/MM/yyyy"),
                        NumeroProtocollo = ds.protocollo[0].numero_protocollo,
                        Warning = _log.Warnings.WarningMessage
                    };

                    return retVal;

                }
            }
            catch (Exception ex)
            {
                throw new Exception(String.Format("ERRORE GENERATO DURANTE LA PROTOCOLLAZIONE IN PARTENZA, ERRORE {0}", ex.Message), ex);
            }
        }

        public DatiProtocolloResponseType ProtocollaPartenzaBozza(DataSet request)
        {
            try
            {
                using (var ws = _protocolloClientServiceCreator.CreateClient())
                {
                    _serializer.LogAndValidate(ProtocolloLogsConstants.ProtocollazioneRequestFileName, request);
                    _log.InfoFormat("CHIAMATA A INSERIMENTO BOZZA PROTOCOLLAZIONE IN PARTENZA, DATI: {0}", request.GetXml());

                    var responseBozza = ws.Service.InsertBozzaProtocolloInterno(_auth, request, _operatore);

                    _serializer.LogAndValidate(ProtocolloLogsConstants.InserimentoBozzaResponseFileName, responseBozza);

                    var dsBozza = new VBG.Backend.Protocollo.AppLogic.Core.ApSystems.Protocollazione.InserimentoBozza.protocolli();
                    dsBozza.Merge(responseBozza);

                    if (dsBozza.ContieneErrori())
                        throw new Exception(String.Format("IN INSERIMENTO BOZZA, {0}", dsBozza.GetDescrizioneErrore()));

                    var codice = dsBozza.protocollo[0].codice;

                    _log.InfoFormat("CHIAMATA A INSERIMENTO BOZZA PROTOCOLLAZIONE IN PARTENZA, AVVENUTA CON SUCCESSO, CODICE RESTITUITO {1}", codice);

                    _log.InfoFormat("CHIAMATA A INVIO BOZZA PROTOCOLLAZIONE IN PARTENZA, CODICE {0}", dsBozza.protocollo[0].codice);
                    var response = ws.Service.SendBozzaProtocolloInterno(_auth, codice, _operatore);

                    _serializer.LogAndValidate(ProtocolloLogsConstants.InvioBozzaResponseFileName, responseBozza);

                    var ds = new VBG.Backend.Protocollo.AppLogic.Core.ApSystems.Protocollazione.InvioBozza.protocolli();
                    ds.Merge(response);

                    if (ds.ContieneErrori())
                        throw new Exception(String.Format("IN INVIO BOZZA, {0}", ds.GetDescrizioneErrore()));

                    _log.InfoFormat("CHIAMATA A INVIO BOZZA PROTOCOLLAZIONE IN PARTENZA AVVENUTA CON SUCCESSO, GENERATO PROTOCOLLO NUMERO {0}, DATA {1}, CODICE {2}", ds.protocollo[0].numero_protocollo_generale, ds.protocollo[0].data_protocollo_generale, ds.protocollo[0].codice);

                    var retVal = new DatiProtocolloResponseType
                    {
                        IdProtocollo = ds.protocollo[0].codice,
                        AnnoProtocollo = DateTime.ParseExact(ds.protocollo[0].data_protocollo_generale, this._formatoData, null).ToString("yyyy"),
                        DataProtocollo = DateTime.ParseExact(ds.protocollo[0].data_protocollo_generale, this._formatoData, null).ToString("dd/MM/yyyy"),
                        NumeroProtocollo = ds.protocollo[0].numero_protocollo_generale,
                        Warning = _log.Warnings.WarningMessage
                    };

                    return retVal;
                }
            }
            catch (Exception ex)
            {
                throw new Exception(String.Format("ERRORE GENERATO DURANTE LA PROTOCOLLAZIONE IN PARTENZA, ERRORE {0}", ex.Message), ex);
            }
        }

        public DatiProtocolloResponseType ProtocollaInterno(DataSet request)
        {
            try
            {
                using (var ws = _protocolloClientServiceCreator.CreateClient())
                {
                    _serializer.LogAndValidate(ProtocolloLogsConstants.ProtocollazioneRequestFileName, request);
                    _log.InfoFormat("CHIAMATA A PROTOCOLLAZIONE INTERNA, DATI: {0}", request.GetXml());

                    var response = ws.Service.InsertProtocolloGenerale(_auth, request, _operatore);

                    _serializer.LogAndValidate(ProtocolloLogsConstants.ProtocollazioneResponseFileName, response);

                    var ds = new protocolli();
                    ds.Merge(response);

                    if (ds.ContieneErrori())
                        throw new Exception(ds.GetDescrizioneErrore());

                    _log.InfoFormat("PROTOCOLLAZIONE INTERNA AVVENUTA CON SUCCESSO, PROTOCOLLO NUMERO {0}, DATA {1}, CODICE {2}", ds.protocollo[0].numero_protocollo, ds.protocollo[0].data_protocollo, ds.protocollo[0].codice);

                    var retVal = new DatiProtocolloResponseType
                    {
                        IdProtocollo = ds.protocollo[0].codice,
                        AnnoProtocollo = DateTime.Parse(ds.protocollo[0].data_protocollo).ToString("yyyy"),
                        DataProtocollo = ds.protocollo[0].data_protocollo,
                        NumeroProtocollo = ds.protocollo[0].numero_protocollo,
                        Warning = _log.Warnings.WarningMessage
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
