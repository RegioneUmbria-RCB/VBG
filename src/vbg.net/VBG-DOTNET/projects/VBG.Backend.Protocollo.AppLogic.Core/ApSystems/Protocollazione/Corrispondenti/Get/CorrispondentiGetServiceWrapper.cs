using VBG.Shared.Infrastructure.ServiceModel;
using ProtocolloApSystemsService;
using VBG.Backend.Protocollo.AppLogic.Shared.Logs;
using VBG.Backend.Protocollo.AppLogic.Shared.Serialize;

namespace VBG.Backend.Protocollo.AppLogic.Core.ApSystems.Protocollazione.Corrispondenti.Get
{
    public class CorrispondentiGetServiceWrapper
    {
        private readonly ProtocolloLogs _log;
        private readonly ProtocolloSerializer _serializer;
        private readonly ProtocolloClientServiceCreator _protocolloClientServiceCreator;
        private readonly AuthenticationDetails _authenticationDetails;

        public CorrispondentiGetServiceWrapper(ProtocolloLogs logs, ProtocolloSerializer serializer, string username, string password, string url, IBindingFactory bindingFactory)
        {
            this._log = logs;
            this._serializer = serializer;
            this._protocolloClientServiceCreator = new ProtocolloClientServiceCreator(logs, bindingFactory, url);
            this._authenticationDetails = new AuthenticationDetails() { UserName = username, Password = password };
        }

        public corrispondenti.corrispondenteRow GetCorrispondenteByCodice(string codiceCorrispondente)
        {
            try
            {
                using (var ws = _protocolloClientServiceCreator.CreateClient())
                {
                    if (String.IsNullOrEmpty(codiceCorrispondente))
                        throw new Exception("CODICE CORRISPONDENTE NON VALORIZZATO");

                    var response = ws.Service.GetCorrispondente(_authenticationDetails, codiceCorrispondente, "", "", "", "", "", "");
                    var ds = new corrispondenti();
                    ds.Merge(response);

                    if (ds.ContieneErrori())
                        throw new Exception(ds.GetDescrizioneErrore());

                    if (ds.corrispondente.Rows.Count > 1)
                    {
                        _log.Warn($"TROVATO PIU' DI UN RISULTATO PER IL CODICE FISCALE / PARTITA IVA {codiceCorrispondente} E' STATO RECUPERATO IL PRIMO CORRISPONDENTE INDIVIDUATO NEL SISTEMA DI PROTOCOLLO");
                    }

                    if (ds.corrispondente.Rows.Count == 0)
                        throw new Exception("NESSUN CORRISPONDENTE TROVATO");


                    return ds.corrispondente[0];
                }
            }
            catch (Exception ex)
            {
                throw new Exception(String.Format("ERRORE GENERATO DALLA LETTURA DEI CORRISPONDENTI PER CODICE {0}, {1}", codiceCorrispondente, ex.Message), ex);
            }
        }

        public corrispondenti.corrispondenteRow GetCorrispondenteByCodiceUfficio(string codiceUfficio)
        {
            try
            {
                using (var ws = _protocolloClientServiceCreator.CreateClient())
                {
                    if (String.IsNullOrEmpty(codiceUfficio))
                        throw new Exception("CODICE UFFICIO NON VALORIZZATO");

                    var response = ws.Service.GetCorrispondente(_authenticationDetails, codiceUfficio, "", "", "", "", "", "");
                    var ds = new corrispondenti();
                    ds.Merge(response);

                    if (ds.ContieneErrori())
                        throw new Exception(ds.GetDescrizioneErrore());

                    if (ds.corrispondente.Rows.Count > 1)
                        throw new Exception("TROVATO PIU' DI UN RISULTATO");

                    if (ds.corrispondente.Rows.Count == 0)
                        throw new Exception("NESSUN CORRISPONDENTE TROVATO");


                    return ds.corrispondente[0];
                }

            }
            catch (Exception ex)
            {
                throw new Exception(String.Format("ERRORE GENERATO DALLA LETTURA DEI CORRISPONDENTI PER CODICE UFFICIO {0}, {1}", codiceUfficio, ex.Message), ex);
            }
        }

        public corrispondenti.corrispondenteDataTable GetCorrispondenteByCodiceFiscale(string codiceFiscale)
        {
            try
            {
                using (var ws = _protocolloClientServiceCreator.CreateClient())
                {
                    if (String.IsNullOrEmpty(codiceFiscale))
                        throw new Exception("CODICE FISCALE (O PARTITA IVA) DEL CORRISPONDENTE NON VALORIZZATO");


                    var response = ws.Service.GetCorrispondente(_authenticationDetails, "", codiceFiscale, "", "", "", "", "");
                    var ds = new corrispondenti();
                    ds.Merge(response);

                    if (ds.ContieneErrori())
                        throw new Exception(ds.GetDescrizioneErrore());

                    if (ds.corrispondente.Rows.Count > 1)
                        throw new Exception("TROVATO PIU' DI UN RISULTATO");

                    return ds.corrispondente;
                }
            }
            catch (Exception ex)
            {
                throw new Exception(String.Format("ERRORE GENERATO DALLA LETTURA DEI CORRISPONDENTI PER CODICE FISCALE {0}, {1}", codiceFiscale, ex.Message), ex);
            }
        }
    }
}
