using ProtocolloApSystemsService;
using System.Data;
using System.ServiceModel;
using VBG.Backend.Protocollo.AppLogic.Shared.Logs;
using VBG.Backend.Protocollo.AppLogic.Shared.Serialize;
using VBG.Shared.Infrastructure.ServiceModel;

namespace VBG.Backend.Protocollo.AppLogic.Core.ApSystems.LeggiProtocollo
{
    public class LeggiProtocolloServiceWrapper
    {
        private readonly ProtocolloLogs _log;
        private readonly ProtocolloSerializer _serializer;
        private readonly ProtocolloClientServiceCreator _protocolloClientServiceCreator;
        private readonly AuthenticationDetails _auth;

        public LeggiProtocolloServiceWrapper(ProtocolloLogs logs, ProtocolloSerializer serializer, string url, string username, string password, IBindingFactory bindingFactory)
        {
            this._log = logs;
            this._serializer = serializer;
            this._protocolloClientServiceCreator = new ProtocolloClientServiceCreator(logs, bindingFactory, url);
            this._auth = new AuthenticationDetails { UserName = username, Password = password };
        }

        public protocolli LeggiProtocollo(string idProtocollo, string numeroProtocollo, string annoProtocollo)
        {
            try
            {
                using (var ws = this._protocolloClientServiceCreator.CreateClient())
                {
                    this._log.InfoFormat("CHIAMATA A LEGGI PROTOCOLLO NUMERO: {0}, ANNO: {1}, ID: {2}", numeroProtocollo, annoProtocollo, idProtocollo);
                    var response = new DataSet();

                    try
                    {
                        response = ws.Service.GetProtocolloGenerale(this._auth, idProtocollo, annoProtocollo, numeroProtocollo, numeroProtocollo, "", "", "", "", "", "", "", "", "", "");
                    }
                    catch (Exception)
                    {
                        throw;
                    }
                    finally
                    {
                        if (ws.Service.State == CommunicationState.Faulted)
                        {
                            ws.Service.Abort();
                        }
                        else
                        {
                            ws.Service.Close();
                        }
                    }

                    var ds = new protocolli();

                    ds.Merge(response);

                    if (ds.ContieneErrori())
                        throw new Exception(ds.GetDescrizioneErrore());

                    var proto = ds.protocollo;

                    if (proto.Rows.Count == 1)
                    {
                        this._log.InfoFormat("CHIAMATA LEGGI PROTOCOLLO NUMERO: {0}, ANNO: {1}, ID: {2} AVVENUTA CON SUCCESSO", numeroProtocollo, annoProtocollo, idProtocollo);
                        this._log.InfoFormat("DATI RESTITUITI DA LEGGI_PROTOCOLLO: {0}", ds.GetXml());
                    }
                    else
                    {
                        if (proto.Rows.Count == 0)
                            throw new Exception("LA RICERCA NON HA PRODOTTO ALCUN RISULTATO");

                        if (proto.Rows.Count > 1)
                            throw new Exception("LA RICERCA HA PRODOTTO PIU' DI UN RISULTATO");
                    }

                    return ds;
                }
            }
            catch (Exception ex)
            {
                throw new Exception(String.Format("ERRORE GENERATO DURANTE LA LETTURA DEL PROTOCOLLO PROTOCOLLO NUMERO: {0}, ANNO: {1}, ID: {2}, {3}", numeroProtocollo, annoProtocollo, idProtocollo, ex.Message), ex);
            }
        }
    }
}
